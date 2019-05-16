package kg.kloop.android.zvonilka.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.helpers.CampaignInfo;
import kg.kloop.android.zvonilka.objects.Call;
import kg.kloop.android.zvonilka.objects.Client;

public class CallResultActivity extends AppCompatActivity {

    private static final String TAG = "CallResultActivity";
    private EditText callDescriptionEditText;
    private EditText callToDoEditText;
    private RadioGroup callResulRadioGroup;
    private RadioButton successfulCallRadioButton;
    private RadioButton callBackRadioButton;
    private RadioButton dontCallRadioButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference callsDatabaseReference;
    private String currentCampaignId = CampaignInfo.getCurrentCampaignId();
    private Call call;
    private String phoneNumber;
    private String callType;
    private String callDate;
    private String callDuration;
    private String callDescription;
    private String callTodo;
    int callResult;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_result);

        callDescriptionEditText = findViewById(R.id.call_description_edit_text);
        callToDoEditText = findViewById(R.id.call_todo_edit_text);
        callResulRadioGroup = findViewById(R.id.call_result_radio_group);
        successfulCallRadioButton = findViewById(R.id.successful_call_radio_button);
        callBackRadioButton =  findViewById(R.id.call_back_radio_button);
        dontCallRadioButton =  findViewById(R.id.dont_call_radio_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        call = new Call();
        firebaseDatabase = FirebaseDatabase.getInstance();
        callsDatabaseReference = firebaseDatabase.getReference("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Calls");
        if(isPermissionToReadCallLogGranted()){
            getCallDetails();
        } else askForReadCallLogPermission();

        successfulCallRadioButton.setChecked(true);
        getCallResult();

    }

    private void setClientCategory() {
        final ArrayList<Client> clientArrayList = new ArrayList<>();
        Query clientsInCampaignQuery = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients")
                .orderByChild("phoneNumber")
                .equalTo(phoneNumber);
        final DatabaseReference clientsInCampaignDatabaseReference = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients");
        clientsInCampaignQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clientArrayList.add(dataSnapshot.getValue(Client.class));
                callTodo = callToDoEditText.getText().toString();
                for (Client client : clientArrayList){
                    client.setCategory(callResult);
                    client.setToDoInfo(callTodo);
                    if (client.getCallArrayList() != null) {
                        ArrayList<Call> callArrayList = client.getCallArrayList();
                        callArrayList.add(call);
                        client.setCallArrayList(callArrayList);
                    } else {
                        ArrayList<Call> callArrayList = new ArrayList<>();
                        callArrayList.add(call);
                        client.setCallArrayList(callArrayList);
                    }
                    clientsInCampaignDatabaseReference.child(client.getId()).setValue(client);
                }
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

    private void getCallDetails() {
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        //get only last call
        managedCursor.moveToLast();
        phoneNumber = managedCursor.getString(number);
        callType = managedCursor.getString(type);
        callDate = managedCursor.getString(date);
        callDuration = managedCursor.getString(duration);
        managedCursor.close();

    }

    private void getCallResult(){
        callResulRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.successful_call_radio_button:
                        callResult = 0; // successful call
                        break;
                    case R.id.call_back_radio_button:
                        callResult = 1;
                        break;
                    case R.id.dont_call_radio_button:
                        callResult = 2;
                        break;
                }
            }
        });
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
                saveCallResultToFirebase();
                setClientCategory();
                setResult(RESULT_OK);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveCallResultToFirebase() {
        String callId = callsDatabaseReference.push().getKey();
        callDescription = callDescriptionEditText.getText().toString();
        call = new Call(callId, phoneNumber, callType, callDate, callDuration, callDescription, callResult);
        callsDatabaseReference.child(callId).setValue(call);
    }

    private void askForReadCallLogPermission(){
        ActivityCompat.requestPermissions(CallResultActivity.this,
                new String[]{Manifest.permission.READ_CALL_LOG},
                MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
    }
    private boolean isPermissionToReadCallLogGranted(){
        return ActivityCompat.checkSelfPermission(CallResultActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALL_LOG: {
                if (ActivityCompat.checkSelfPermission(CallResultActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    getCallDetails();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_to_call_is_required, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //way to get latest call's data
        if(isPermissionToReadCallLogGranted()){
            getCallDetails();
        } else askForReadCallLogPermission();
    }
}
