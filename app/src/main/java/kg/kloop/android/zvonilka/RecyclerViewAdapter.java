package kg.kloop.android.zvonilka;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

        holder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement button to edit campaign
            }
        });
        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement delete campaign button (don't forgot to show dialog before deleting)
            }
        });
    }

    @Override
    public int getItemCount() {
        return campaignArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;
        ImageButton editImageButton;
        ImageButton deleteImageButton;
        private ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.campaign_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            editImageButton = (ImageButton)itemView.findViewById(R.id.edit_image_button);
            deleteImageButton = (ImageButton)itemView.findViewById(R.id.delete_image_button);
        }
    }
}


