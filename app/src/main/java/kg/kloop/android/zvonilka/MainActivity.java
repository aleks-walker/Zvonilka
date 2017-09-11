package kg.kloop.android.zvonilka;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE_CAMPAIGN = 100;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Campaign> campaignArrayList;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button);
        campaignArrayList = new ArrayList<>();

        adapter = new RecyclerViewAdapter(campaignArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCampaign();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CREATE_CAMPAIGN:
                    Campaign campaign = new Campaign();
                    campaign.setTitle(data.getStringExtra("title"));
                    campaign.setDescription(data.getStringExtra("description"));
                    campaignArrayList.add(campaign);
                    break;
            }
        }
    }

    private void createNewCampaign() {
        Intent intent = new Intent(MainActivity.this, CreateCampaign.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE_CAMPAIGN);
    }


}
