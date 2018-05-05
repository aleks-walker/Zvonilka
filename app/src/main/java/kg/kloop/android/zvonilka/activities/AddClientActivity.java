package kg.kloop.android.zvonilka.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

public class AddClientActivity extends AppCompatActivity {

    private static final String TAG = "AddClientActivity";
    private EditText clientNameEditText;
    private EditText clientPhoneNumberEditText;
    private EditText clientCityEditText;
    private EditText clientSalonEditText;
    private EditText clientPositionEditText;
    private EditText clientEmailEditText;
    private EditText clientOtherInfoEditText;
    private EditText clientToDoInfoEditText;
    private Client client;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference companyClientsDatabaseReference;
    private String currentCampaignId;
    private LinearLayout propertiesLinearLayout;
    private ImageButton addPropertyImageButton;
    private EditText propertyDataEditText;
    private AutoCompleteTextView propertiesAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        clientNameEditText = (EditText) findViewById(R.id.client_name_edit_text);
        clientPhoneNumberEditText = (EditText) findViewById(R.id.client_phone_number_edit_text);
        propertiesLinearLayout = (LinearLayout) findViewById(R.id.properties_linear_layout);
        addPropertyImageButton = (ImageButton) findViewById(R.id.add_property_image_button);
        clientCityEditText = findViewById(R.id.client_city_edit_text);
        clientSalonEditText = findViewById(R.id.client_salon_edit_text);
        clientPositionEditText = findViewById(R.id.client_position_edit_text);
        clientEmailEditText = findViewById(R.id.client_email_edit_text);
        clientOtherInfoEditText = findViewById(R.id.client_other_edit_text);
        clientToDoInfoEditText = findViewById(R.id.client_todo_edit_text);
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
        propertiesArrayList.add("Метод Stabiliti");
        propertiesArrayList.add("Ломи-ломи");
        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(AddClientActivity.this, android.R.layout.simple_dropdown_item_1line, propertiesArrayList);
        addPropertyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertiesLinearLayout.addView(propertyView(AddClientActivity.this, arrayAdapter));
            }
        });

    }

    private View propertyView(Context context, ArrayAdapter arrayAdapter) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_add_properties_item, null);
        propertiesAutoCompleteTextView = view.findViewById(R.id.client_property_autocomplete_text_view);
        propertiesAutoCompleteTextView.setAdapter(arrayAdapter);
        propertiesAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertiesAutoCompleteTextView.showDropDown();
            }
        });
        propertiesAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) propertiesAutoCompleteTextView.showDropDown();
            }
        });
        ImageButton removePropertyImageButton = view.findViewById(R.id.remove_property_image_button);
        removePropertyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View imageButton) {
                propertiesAutoCompleteTextView.requestFocus();
                propertiesLinearLayout.removeViewInLayout(propertiesLinearLayout.getFocusedChild());
                propertiesLinearLayout.requestLayout();
            }
        });

        return view;
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
                String city = clientCityEditText.getText().toString();
                String salon = clientSalonEditText.getText().toString();
                String position = clientPositionEditText.getText().toString();
                String email = clientEmailEditText.getText().toString();
                String other = clientOtherInfoEditText.getText().toString();
                String todo = clientToDoInfoEditText.getText().toString();
                client.setId(companyClientsDatabaseReference.push().getKey());
                client.setName(name);
                client.setPhoneNumber(phoneNumber);
                client.setCity(city);
                client.setSalon(salon);
                client.setPosition(position);
                client.setEmail(email);
                client.setOtherInfo(other);
                client.setToDoInfo(todo);
                client.setInterests(getProperties());
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

    private Map<String, String> getProperties() {
        String title;
        String body;
        Map<String, String> clientPropertiesHashMap = new HashMap<>();
        for (int i = 0; i < propertiesLinearLayout.getChildCount(); i++){
            View view = propertiesLinearLayout.getChildAt(i);
            propertiesAutoCompleteTextView = view.findViewById(R.id.client_property_autocomplete_text_view);
            propertyDataEditText = view.findViewById(R.id.client_property_edit_text);
            title = propertiesAutoCompleteTextView.getText().toString();
            body = propertyDataEditText.getText().toString();
            clientPropertiesHashMap.put(title, body);
        }
        return clientPropertiesHashMap;
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
