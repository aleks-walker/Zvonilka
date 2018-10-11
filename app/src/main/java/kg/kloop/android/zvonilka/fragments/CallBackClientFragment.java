package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.selection.OnDragInitiatedListener;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.helpers.CampaignInfo;
import kg.kloop.android.zvonilka.helpers.MyItemKeyProvider;
import kg.kloop.android.zvonilka.helpers.MyItemLookup;
import kg.kloop.android.zvonilka.objects.Client;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallBackClientFragment extends Fragment {

    private static final String TAG = "CallBackClientFragment";
    RecyclerView callBackClientsRecyclerView;
    private ArrayList<Client> clientArrayList;
    private ClientsRecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private String currentCampaignId;
    private Query callBackQuery;


    public CallBackClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_back_client, container, false);

        callBackClientsRecyclerView = view.findViewById(R.id.call_back_clients_recycler_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentCampaignId = CampaignInfo.getCurrentCampaignId();
        callBackQuery = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients")
                .orderByChild("category")
                .equalTo(1); // 1 == call back
        clientArrayList = new ArrayList<>();
        getDataFromFirebase();

        adapter = new ClientsRecyclerViewAdapter(getContext(), clientArrayList);
        callBackClientsRecyclerView.setAdapter(adapter);
        callBackClientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callBackClientsRecyclerView.setHasFixedSize(true);

        return view;
    }

    private void getDataFromFirebase() {
        callBackQuery.addChildEventListener(new ChildEventListener() {
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
