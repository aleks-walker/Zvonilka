package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.CustomFragmetPagerAdapter;

public class CampaignActivity extends AppCompatActivity {

    private FloatingActionButton addClientFloatingActionButton;
    private String currentCampaignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("currentCampaignTitle"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new CustomFragmetPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        currentCampaignId = getIntent().getStringExtra("currentCampaignId");
        addClientFloatingActionButton = (FloatingActionButton)findViewById(R.id.add_client_floating_action_button);
        addClientFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignActivity.this, AddClientActivity.class);
                intent.putExtra("currentCampaignId", currentCampaignId);
                startActivity(intent);
            }
        });

    }
}