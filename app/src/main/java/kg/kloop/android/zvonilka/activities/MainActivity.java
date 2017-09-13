package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.objects.Campaign;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE_CAMPAIGN = 100;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Campaign> campaignArrayList;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button);
        campaignArrayList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Campaigns");
        getCampaignsFromFirebase();

        adapter = new RecyclerViewAdapter(campaignArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCampaign();
            }
        });

    }

    private void getCampaignsFromFirebase() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Campaign campaign = dataSnapshot.getValue(Campaign.class);
                //zero is needed to show new items on top
                campaignArrayList.add(0, campaign);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
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

    private void createNewCampaign() {
        Intent intent = new Intent(MainActivity.this, CreateCampaignActivity.class);
        startActivity(intent);
    }


}
