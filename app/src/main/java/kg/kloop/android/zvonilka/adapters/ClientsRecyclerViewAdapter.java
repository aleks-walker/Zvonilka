package kg.kloop.android.zvonilka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.objects.Client;

/**
 * Created by alexwalker on 13.09.17.
 */

public class ClientsRecyclerViewAdapter extends RecyclerView.Adapter<ClientsRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Client> clientArrayList;
    public ClientsRecyclerViewAdapter(Context context, ArrayList<Client> clientArrayList) {
        this.context = context;
        this.clientArrayList = clientArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
       // View clientView = inflater.inflate(R.layout.client_item, parent);
        return new ViewHolder(inflater.inflate(R.layout.client_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(clientArrayList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return clientArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
        }
    }
}
