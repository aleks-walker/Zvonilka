package kg.kloop.android.zvonilka.zvonilka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class CreateCampaign extends AppCompatActivity {

    EditText titleEditText;
    EditText descriptionEditText;
    String title;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = (EditText)findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText)findViewById(R.id.description_edit_text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_campaign_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_item:
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
