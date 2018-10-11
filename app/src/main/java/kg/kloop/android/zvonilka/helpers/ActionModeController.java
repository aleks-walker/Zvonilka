package kg.kloop.android.zvonilka.helpers;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

public class ActionModeController implements ActionMode.Callback {
    private final Context context;
    private final SelectionTracker selectionTracker;
    private final ArrayList<Client> allClientsArrayList;
    private ArrayList<Client> selectedClients;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ActionModeController(Context context, SelectionTracker selectionTracker, ArrayList<Client> allClientsArrayList) {
        this.context = context;
        this.selectionTracker = selectionTracker;
        this.allClientsArrayList = allClientsArrayList;
        selectedClients = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(CampaignInfo.getCurrentCampaignId())
                .child("Clients");
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.menu_selection, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.select_all_menu_item:
                if (selectionTracker.getSelection().size() == allClientsArrayList.size()) {
                    selectionTracker.clearSelection();
                } else {
                    for (Client client : allClientsArrayList) {
                        selectionTracker.select(client);
                    }
                }
                break;
            case R.id.upload_selected_menu_item:
                for (Client client : allClientsArrayList) {
                    if (selectionTracker.isSelected(client)) {
                        selectedClients.add(client);
                    } else if (selectedClients.contains(client) && !selectionTracker.isSelected(client)){
                        selectedClients.remove(client);
                    }
                }
                for (Client client : selectedClients) {
                    databaseReference.push().setValue(client);
                }
                actionMode.finish();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        selectionTracker.clearSelection();
    }
}
