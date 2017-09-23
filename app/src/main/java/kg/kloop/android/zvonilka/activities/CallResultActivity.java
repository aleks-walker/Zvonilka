package kg.kloop.android.zvonilka.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Call;

public class CallResultActivity extends AppCompatActivity {

    private static final String TAG = "CallResultActivity";
    EditText callDescriptionEditText;
    RadioGroup callResulRadioGroup;
    RadioButton successfulCallRadioButton;
    RadioButton callBackRadioButton;
    RadioButton dontCallRadioButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentCampaign = CampaignActivity.getCurrentCampaignId();
    Call call;
    String phoneNumber;
    String callType;
    String callDate;
    String callDuration;
    String callDescription;
    int callResult;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_result);

        callDescriptionEditText = (EditText)findViewById(R.id.call_description_edit_text);
        callResulRadioGroup = (RadioGroup)findViewById(R.id.call_result_radio_group);
        successfulCallRadioButton = (RadioButton)findViewById(R.id.successful_call_radio_button);
        callBackRadioButton = (RadioButton) findViewById(R.id.call_back_radio_button);
        dontCallRadioButton = (RadioButton) findViewById(R.id.dont_call_radio_button);
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

        successfulCallRadioButton.setChecked(true);
        getCallResult();

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
                        callResult = 0;
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
                String callId = databaseReference.push().getKey();
                callDescription = callDescriptionEditText.getText().toString();
                call = new Call(callId, phoneNumber, callType, callDate, callDuration, callDescription, callResult);
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
        //way to get latest call's data
        if(isPermissionToReadCallLogGranted()){
            getCallDetails();
        } else askForReadCallLogPermission();
    }
}
