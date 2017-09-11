package kg.kloop.android.zvonilka.zvonilka;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexwalker on 08.09.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Campaign> campaignArrayList;

    public RecyclerViewAdapter(ArrayList<Campaign> campaignArrayList){
        this.campaignArrayList = campaignArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(campaignArrayList.get(position).getTitle());
        holder.description.setText(campaignArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return campaignArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;
        private ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.campaign_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
        }
    }
}


