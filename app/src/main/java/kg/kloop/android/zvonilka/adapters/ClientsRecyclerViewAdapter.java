package kg.kloop.android.zvonilka.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.activities.ClientActivity;
import kg.kloop.android.zvonilka.helpers.MyItemDetails;
import kg.kloop.android.zvonilka.objects.Client;

/**
 * Created by alexwalker on 13.09.17.
 */

public class ClientsRecyclerViewAdapter extends RecyclerView.Adapter<ClientsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = ClientsRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<Client> clientArrayList;
    private SelectionTracker selectionTracker;
    public ClientsRecyclerViewAdapter(Context context, ArrayList<Client> clientArrayList) {
        this.context = context;
        this.clientArrayList = clientArrayList;
    }

    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View clientView = inflater.inflate(R.layout.client_item, parent, false);
        return new ViewHolder(clientView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(clientArrayList.get(position).getName());
        if (selectionTracker != null) {
            holder.activate(selectionTracker.isSelected(clientArrayList.get(position)));
        }
    }


    @Override
    public int getItemCount() {
        return clientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameTextView;
        ImageView showMoreImageView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            showMoreImageView = itemView.findViewById(R.id.show_more_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ClientActivity.class);
            intent.putExtra("clientId", clientArrayList.get(getAdapterPosition()).getId());
            intent.putExtra("activity", context.getClass().getSimpleName());
            Log.v(TAG, "source activity: " + context.getClass().getSimpleName());
            context.startActivity(intent);
        }

        public ItemDetailsLookup.ItemDetails getItemDetails() {
            return new MyItemDetails(getAdapterPosition(), clientArrayList.get(getAdapterPosition()));
        }

        void activate(boolean isActive) {
            itemView.setActivated(isActive);
            if (itemView.isActivated()) {
                itemView.setBackgroundColor(context.getColor(android.R.color.darker_gray));
            } else itemView.setBackgroundColor(context.getColor(android.R.color.transparent));
        }
    }
}
