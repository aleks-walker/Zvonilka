package kg.kloop.android.zvonilka.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Call;

public class CallResultActivity extends AppCompatActivity {

    EditText callDescriptionEditText;
    ToggleButton callBackToggleButton;
    ToggleButton dontCallToggleButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentCampaign = CampaignActivity.getCurrentCampaignId();
    Call call;
    String phoneNumber;
    String callType;
    String callDate;
    String callDuration;
    String callDescription;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_result);

        callDescriptionEditText = (EditText)findViewById(R.id.call_description_edit_text);
        callBackToggleButton = (ToggleButton)findViewById(R.id.call_back_toggle_button);
        dontCallToggleButton = (ToggleButton)findViewById(R.id.dont_call_toggle_button);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        call = new Call();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaign)
                .child("Calls");
        if(isPermissionToReadCallLogGranted()){
            getCallDetails();
        } else askForReadCallLogPermission();



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_client_item:
                String callId = databaseReference.push().getKey();
                callDescription = callDescriptionEditText.getText().toString();
                call = new Call(callId, phoneNumber, callType, callDate, callDuration, callDescription);
                databaseReference.child(callId).setValue(call);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        //way to get latest call data
        if(isPermissionToReadCallLogGranted()){
            getCallDetails();
        } else askForReadCallLogPermission();
    }
}
