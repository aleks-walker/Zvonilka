package kg.kloop.android.zvonilka.helpers;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;
import kg.kloop.android.zvonilka.R;

public class ActionModeController implements ActionMode.Callback {
    private final Context context;
    private final SelectionTracker selectionTracker;

    public ActionModeController(Context context, SelectionTracker selectionTracker) {
        this.context = context;
        this.selectionTracker = selectionTracker;
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
            case R.id.close_action_mode_menu_item:
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
