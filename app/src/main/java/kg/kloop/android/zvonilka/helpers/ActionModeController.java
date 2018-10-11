package kg.kloop.android.zvonilka.helpers;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

public class ActionModeController implements ActionMode.Callback {
    private final Context context;
    private final SelectionTracker selectionTracker;
    private final ArrayList<Client> allClientsArrayList;

    public ActionModeController(Context context, SelectionTracker selectionTracker, ArrayList<Client> allClientsArrayList) {
        this.context = context;
        this.selectionTracker = selectionTracker;
        this.allClientsArrayList = allClientsArrayList;
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
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        selectionTracker.clearSelection();
    }
}
