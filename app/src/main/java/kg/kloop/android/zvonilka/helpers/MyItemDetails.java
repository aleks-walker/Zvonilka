package kg.kloop.android.zvonilka.helpers;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import kg.kloop.android.zvonilka.objects.Client;

public class MyItemDetails extends ItemDetailsLookup.ItemDetails {
    private final int adapterPosition;
    private final Client selectionKey;

    public MyItemDetails(int adapterPosition, Client selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}
