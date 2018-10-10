package kg.kloop.android.zvonilka.helpers;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;

public class MyItemLookup extends ItemDetailsLookup {

    private final RecyclerView recyclerView;

    public MyItemLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof ClientsRecyclerViewAdapter.ViewHolder) {
                return ((ClientsRecyclerViewAdapter.ViewHolder) viewHolder).getItemDetails();
            }
        }

        return null;
    }
}
