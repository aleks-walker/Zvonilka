package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

public class AddClientActivity extends AppCompatActivity {

    EditText clientNameEditText;
    EditText clientPhoneNumberEditText;
    Client client;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String currentCampaignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        clientNameEditText = (EditText)findViewById(R.id.client_name_edit_text);
        clientPhoneNumberEditText = (EditText)findViewById(R.id.client_phone_number_edit_text);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client = new Client();
        currentCampaignId = getIntent().getStringExtra("currentCampaignId");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Campaigns").child(currentCampaignId).child("Clients");

        //TODO: implement dynamically adding views for client's properties
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_client_item:
                String name = clientNameEditText.getText().toString();
                String phoneNumber = clientPhoneNumberEditText.getText().toString();
                client.setId(databaseReference.push().getKey());
                client.setName(name);
                client.setPhoneNumber(phoneNumber);
                if(isDataEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.enter_some_data, Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(client.getId()).setValue(client);
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private boolean isDataEmpty() {
        return !(clientNameEditText.getText().length() > 0
                    && clientPhoneNumberEditText.getText().length() > 0);
    }

}
