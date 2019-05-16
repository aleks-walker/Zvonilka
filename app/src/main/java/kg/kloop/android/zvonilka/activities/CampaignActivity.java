package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.CustomFragmentPagerAdapter;
import kg.kloop.android.zvonilka.helpers.CampaignInfo;

public class CampaignActivity extends AppCompatActivity {

    public static final String TAG = "CampaignActivity";
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
        viewPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        CampaignInfo.setCurrentCampaignId(getIntent().getStringExtra("currentCampaignId"));
        currentCampaignId = CampaignInfo.getCurrentCampaignId();
        FloatingActionButton addClientFloatingActionButton = findViewById(R.id.add_client_floating_action_button);
        addClientFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignActivity.this, AllClientsActivity.class);
                intent.putExtra("currentCampaignId", currentCampaignId);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_campaign, menu);
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
