package kg.kloop.android.zvonilka.helpers;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import kg.kloop.android.zvonilka.objects.Client;

public class MyItemKeyProvider extends ItemKeyProvider {
    /**
     * Creates a new provider with the given scope.
     *
     * @param scope Scope can't be changed at runtime.
     */
    private List<Client> clientArrayList;
    public MyItemKeyProvider(int scope, List<Client> clientArrayList) {
        super(scope);
        this.clientArrayList = clientArrayList;
    }

    @Nullable
    @Override
    public Object getKey(int position) {
        return clientArrayList.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key) {
        return clientArrayList.indexOf(key);
    }
}
