package kg.kloop.android.zvonilka.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.fragments.SortingDialogFragment;
import kg.kloop.android.zvonilka.objects.Client;

public class AllClientsActivity extends AppCompatActivity implements SortingDialogFragment.SortingDialogListener {

    public static final String TAG = AllClientsActivity.class.getSimpleName();
    private ArrayList<Client> allClientsArrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ClientsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.add_client_to_company_floating_action_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Clients");

        recyclerView = (RecyclerView)findViewById(R.id.all_clients_recycler_view);
        recyclerView.setHasFixedSize(true);
        allClientsArrayList = new ArrayList<>();
        getDataFromFirebase();
        adapter = new ClientsRecyclerViewAdapter(this, allClientsArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllClientsActivity.this, AddClientActivity.class);
                intent.putExtra("activity", TAG);
                startActivity(intent);
            }
        });
    }

    private void getDataFromFirebase() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allClientsArrayList.add(0, dataSnapshot.getValue(Client.class));
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_clients, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_clients_item:
                showSortingDialog();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void showSortingDialog() {
        DialogFragment dialogFragment = new SortingDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "SortingDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(AppCompatDialogFragment dialog) {
        String key = "";
        String value = "";
        TextView cityParamTextView = dialog.getDialog().findViewById(R.id.city_sorting_dialog_autocomplete_text_view);
        if (cityParamTextView.getText().length() > 0) {
            key = "city";
            value = cityParamTextView.getText().toString();
        }
        TextView interestParamTextView = dialog.getDialog().findViewById(R.id.interest_sorting_dialog_autocomplete_text_view);
        if (interestParamTextView.getText().length() > 0) {
            key = "interests";
            value = interestParamTextView.getText().toString();
        }
        sortClients(key, value);
    }

    @Override
    public void onDialogNegativeClick(AppCompatDialogFragment dialog) {
        dialog.dismiss();
    }

    private void sortClients(String key, String value) {
        Query query = null;
        if (!allClientsArrayList.isEmpty()) {
            allClientsArrayList.clear();
            adapter.notifyDataSetChanged();
        }
        if (key == "city") {
            query = databaseReference
                    .orderByChild(key)
                    .equalTo(value);
        } else if (key == "interests") {
            query = databaseReference
                    .orderByChild(key + "/" + value)
                    .equalTo(1);
        }
        Log.v(TAG, "key: " + key + "\n" + "value: " + value);
        if (query != null) {
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    allClientsArrayList.add(0, dataSnapshot.getValue(Client.class));
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    Log.v(TAG, "sorting result: " + allClientsArrayList.get(0).getName());
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



    }

}
