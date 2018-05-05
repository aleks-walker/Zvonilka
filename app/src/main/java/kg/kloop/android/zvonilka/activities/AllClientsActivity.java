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
        HashMap<String, String> paramsHashMap = new HashMap<>();
        TextView cityParamTextView = dialog.getDialog().findViewById(R.id.city_sorting_dialog_autocomplete_text_view);
        paramsHashMap.put("city", cityParamTextView.getText().toString());
        TextView interestParamTextView = dialog.getDialog().findViewById(R.id.interest_sorting_dialog_autocomplete_text_view);
        paramsHashMap.put("interests", interestParamTextView.getText().toString());
        sortClients(paramsHashMap);
    }

    @Override
    public void onDialogNegativeClick(AppCompatDialogFragment dialog) {
        dialog.dismiss();
    }

    private void sortClients(HashMap<String, String> paramsHashMap) {
        if (!allClientsArrayList.isEmpty()) {
            allClientsArrayList.clear();
            adapter.notifyDataSetChanged();
        }
        //String key = paramsHashMap.keySet().toArray()[0].toString();

        for (String key : paramsHashMap.keySet()) {
            sortByKeys(key, paramsHashMap);

        }

    }

    private void sortByKeys(String key, HashMap<String, String> paramsHashMap) {
        Query cityQuery = databaseReference
                .orderByChild(key)
                .equalTo(paramsHashMap.get(key));
        cityQuery.addChildEventListener(new ChildEventListener() {
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
