package kg.kloop.android.zvonilka.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

public class AddClientActivity extends AppCompatActivity {

    EditText clientNameEditText;
    EditText clientPhoneNumberEditText;
    Client client;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference companyClientsDatabaseReference;
    String currentCampaignId;
    LinearLayout propertiesLinearLayout;
    ImageButton addPropertyImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        clientNameEditText = (EditText) findViewById(R.id.client_name_edit_text);
        clientPhoneNumberEditText = (EditText) findViewById(R.id.client_phone_number_edit_text);
        propertiesLinearLayout = (LinearLayout) findViewById(R.id.properties_linear_layout);
        addPropertyImageButton = (ImageButton) findViewById(R.id.add_property_image_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client = new Client();
        firebaseDatabase = FirebaseDatabase.getInstance();
        companyClientsDatabaseReference = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Clients");

        ArrayList<String> propertiesArrayList = new ArrayList<>();
        propertiesArrayList.add("property");
        propertiesArrayList.add("another");
        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(AddClientActivity.this, android.R.layout.simple_dropdown_item_1line, propertiesArrayList);
        addPropertyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertiesLinearLayout.addView(propertyView(AddClientActivity.this, arrayAdapter));
            }
        });

    }

    private View propertyView(Context context, ArrayAdapter arrayAdapter) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(context);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setHint(R.string.enter_property_title);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(autoCompleteTextView, layoutParams);

        EditText bodyEditText = new EditText(context);
        linearLayout.addView(bodyEditText);

        return linearLayout;
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
                client.setId(companyClientsDatabaseReference.push().getKey());
                client.setName(name);
                client.setPhoneNumber(phoneNumber);
                if (isDataEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.enter_some_data, Toast.LENGTH_SHORT).show();
                } else if (isClientForCampaign()) {
                    addClientToCampaign();
                    addClientToCompany();
                    finish();
                } else {
                    addClientToCompany();
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void addClientToCompany() {
        companyClientsDatabaseReference.child(client.getId()).setValue(client);

    }

    private void addClientToCampaign() {
        DatabaseReference campaignClientsDatabaseReference = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients");
        campaignClientsDatabaseReference.child(client.getId()).setValue(client);
    }

    private boolean isDataEmpty() {
        return !(clientNameEditText.getText().length() > 0
                    && clientPhoneNumberEditText.getText().length() > 0);
    }

    public boolean isClientForCampaign() {
        Intent intent = getIntent();
        if(intent.hasExtra("currentCampaignId")){
            currentCampaignId = intent.getStringExtra("currentCampaignId");
            return true;
        }
        return false;
    }
}
