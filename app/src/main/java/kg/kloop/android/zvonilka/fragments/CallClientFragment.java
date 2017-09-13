package kg.kloop.android.zvonilka.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import kg.kloop.android.zvonilka.activities.AddClientActivity;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.objects.Client;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallClientFragment extends Fragment {

    private static final int REQUEST_CODE_ADD_CLIENT = 101;
    private RecyclerView clientsToCallRecyclerView;
    private FloatingActionButton addClientFloatingActionButton;
    private ArrayList<Client> clientArrayList;
    private ClientsRecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String currentCampaignId;

    public CallClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_client, container, false);

        clientsToCallRecyclerView = view.findViewById(R.id.clients_to_call_recycler_view);
        addClientFloatingActionButton = view.findViewById(R.id.add_client_floating_action_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentCampaignId = getActivity().getIntent().getStringExtra("currentCampaignId");
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Campaigns").child(currentCampaignId).child("Clients");
        clientArrayList = new ArrayList<>();
        getDataFromFirebase();

        addClientFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddClientActivity.class);
                intent.putExtra("currentCampaignId", currentCampaignId);
                startActivity(intent);
            }
        });
        adapter = new ClientsRecyclerViewAdapter(getContext(), clientArrayList);
        clientsToCallRecyclerView.setAdapter(adapter);
        clientsToCallRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientsToCallRecyclerView.setHasFixedSize(true);

        return view;
    }


    private void getDataFromFirebase() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clientArrayList.add(0, dataSnapshot.getValue(Client.class));
                adapter.notifyItemInserted(0);
                clientsToCallRecyclerView.scrollToPosition(0);
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
