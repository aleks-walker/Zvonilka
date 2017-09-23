package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.CustomFragmetPagerAdapter;

public class CampaignActivity extends AppCompatActivity {

    private FloatingActionButton addClientFloatingActionButton;
    private static String currentCampaignId;

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

    public static String getCurrentCampaignId(){
        return currentCampaignId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_clients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_all_clients_item:
                startActivity(new Intent(CampaignActivity.this, AllClientsActivity.class));
                break;
            case R.id.call_log_item:
                startActivity(new Intent(CampaignActivity.this, CallLogActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
