package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.objects.Client;

public class SuccessClientFragment extends Fragment {

    private static final String TAG = SuccessClientFragment.class.getSimpleName();
    private RecyclerView clientSuccessRecyclerView;
    private ArrayList<Client> clientArrayList;
    private ClientsRecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private String currentCampaignId;
    private Query callQuery;


    public SuccessClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_success_client, container, false);

        clientSuccessRecyclerView = view.findViewById(R.id.clients_success_recycler_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentCampaignId = getActivity().getIntent().getStringExtra("currentCampaignId");
        callQuery = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients")
                .orderByChild("category")
                .equalTo(0); // 0 == successful call
        clientArrayList = new ArrayList<>();
        getDataFromFirebase();

        adapter = new ClientsRecyclerViewAdapter(getContext(), clientArrayList);
        clientSuccessRecyclerView.setAdapter(adapter);
        clientSuccessRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientSuccessRecyclerView.setHasFixedSize(true);

        return view;
    }

    private void getDataFromFirebase() {
        callQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clientArrayList.add(0, dataSnapshot.getValue(Client.class));
                adapter.notifyItemInserted(0);
                clientSuccessRecyclerView.scrollToPosition(0);
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
