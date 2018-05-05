package kg.kloop.android.zvonilka.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.helpers.CampaignInfo;
import kg.kloop.android.zvonilka.objects.Client;

public class ClientActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CALL_CLIENT = 102;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 103;
    private static final String TAG = ClientActivity.class.getSimpleName();
    private TextView nameTextView;
    private TextView cityTextView;
    private String clientId;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String currentCampaignId;
    private ImageButton callImageButton;
    private Client client;
    private LinearLayout clientPropertiesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView = (TextView) findViewById(R.id.property_name_text_view);
        cityTextView = findViewById(R.id.property_city_text_view);
        callImageButton = (ImageButton) findViewById(R.id.call_image_button);
        Intent intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        currentCampaignId = CampaignInfo.getCurrentCampaignId();
        clientPropertiesLinearLayout = (LinearLayout)findViewById(R.id.client_properties_linear_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        String sourceActivity = intent.getStringExtra("activity");
        Log.v(TAG, "source activity: " + sourceActivity);
        if (sourceActivity == CampaignActivity.TAG) {
            databaseReference = firebaseDatabase.getReference()
                    .child("Companies")
                    .child("TestCompany")
                    .child("Campaigns")
                    .child(currentCampaignId)
                    .child("Clients")
                    .child(clientId);
            addValueEventListener(databaseReference);

        } else {
            databaseReference = firebaseDatabase.getReference()
                    .child("Companies")
                    .child("TestCompany")
                    .child("Clients")
                    .child(clientId);
            addValueEventListener(databaseReference);
        }
        callImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: deal with permissions
                if(android.os.Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(ClientActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        callClient();
                    } else {
                        askForCallPhonePermission();
                    }
                } else callClient();
            }
        });

    }

    private void addValueEventListener(DatabaseReference databaseReference) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client = dataSnapshot.getValue(Client.class);
                if (client != null) {
                    nameTextView.setText(client.getName());
                    cityTextView.setText(client.getCity());
                    showInterests(client);
                    Log.v(TAG, "client's name: " + client.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showInterests(Client client) {
        clientPropertiesLinearLayout.removeAllViews();
        try {
            for (String interest : client.getInterests().keySet()) {
                clientPropertiesLinearLayout.addView(setUpPropertyView(interest));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View setUpPropertyView(String interest) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.client_info_property_item, null);
        TextView propertyTextView = view.findViewById(R.id.client_info_property_text_view);
        Log.v(TAG, "client's interests: " + interest);
        propertyTextView.setText(interest);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //ACTION_CALL does not return any result
        //if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_CALL_CLIENT:
                    startActivity(new Intent(ClientActivity.this, CallResultActivity.class));
                    break;
            }
        //}
    }

    private void callClient() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + client.getPhoneNumber()));
        startActivityForResult(intent, REQUEST_CODE_CALL_CLIENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_item:
                //TODO: implement edit client button
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void askForCallPhonePermission() {
        ActivityCompat.requestPermissions(ClientActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_REQUEST_CALL_PHONE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (ActivityCompat.checkSelfPermission(ClientActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                   callClient();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_to_call_is_required, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
