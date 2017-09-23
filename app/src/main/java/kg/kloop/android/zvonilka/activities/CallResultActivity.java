package kg.kloop.android.zvonilka.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ToggleButton;

import kg.kloop.android.zvonilka.R;

public class CallResultActivity extends AppCompatActivity {

    EditText callDescriptionEditText;
    ToggleButton callBackToggleButton;
    ToggleButton dontCallToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_result);

        callDescriptionEditText = (EditText)findViewById(R.id.call_description_edit_text);
        callBackToggleButton = (ToggleButton)findViewById(R.id.call_back_toggle_button);
        dontCallToggleButton = (ToggleButton)findViewById(R.id.dont_call_toggle_button);



    }
}
