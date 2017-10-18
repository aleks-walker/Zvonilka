package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.objects.Client;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallBackClientFragment extends Fragment {

    RecyclerView callBackClientsRecyclerView;
    private ArrayList<Client> clientArrayList;
    private ClientsRecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String currentCampaignId;


    public CallBackClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_back_client, container, false);

        callBackClientsRecyclerView = view.findViewById(R.id.call_back_clients_recycler_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentCampaignId = getActivity().getIntent().getStringExtra("currentCampaignId");
        //TODO: fix this path
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Campaigns").child(currentCampaignId).child("Clients");
        clientArrayList = new ArrayList<>();
        getDataFromFirebase();

        adapter = new ClientsRecyclerViewAdapter(getContext(), clientArrayList);
        callBackClientsRecyclerView.setAdapter(adapter);
        callBackClientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callBackClientsRecyclerView.setHasFixedSize(true);

        return view;
    }

    private void getDataFromFirebase() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clientArrayList.add(0, dataSnapshot.getValue(Client.class));
                adapter.notifyItemInserted(0);
                callBackClientsRecyclerView.scrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
