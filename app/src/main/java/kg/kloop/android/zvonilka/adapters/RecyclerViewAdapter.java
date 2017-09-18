package kg.kloop.android.zvonilka.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.activities.CampaignActivity;
import kg.kloop.android.zvonilka.objects.Campaign;

/**
 * Created by alexwalker on 08.09.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Campaign> campaignArrayList;

    public RecyclerViewAdapter(ArrayList<Campaign> campaignArrayList) {
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
                LinearLayout layout = new LinearLayout(view.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText companyName = new EditText(view.getContext());
                companyName.setHint("Company name");
                layout.addView(companyName);
                final EditText companyDescription = new EditText(view.getContext());
                companyDescription.setHint("Description");
                layout.addView(companyDescription);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Change company")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String companyNameText = companyName.getText().toString();
                                String companyDescriptionText = companyDescription.getText().toString();
                                // TODO implement action to change campaign
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setView(layout)
                        .show();
            }
        });

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true)
                        .setTitle(R.string.delete_group)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO implement action to delete campaign
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return campaignArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        ImageButton editImageButton;
        ImageButton deleteImageButton;

        private ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.campaign_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            editImageButton = (ImageButton) itemView.findViewById(R.id.edit_image_button);
            deleteImageButton = (ImageButton) itemView.findViewById(R.id.delete_image_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, CampaignActivity.class);
            Campaign currentCampaign = campaignArrayList.get(getAdapterPosition());
            //get campaign's id to save clients into that campaign
            intent.putExtra("currentCampaignId", currentCampaign.getId());
            //get campaign's title to show in toolbar
            intent.putExtra("currentCampaignTitle", currentCampaign.getTitle());
            context.startActivity(intent);
        }
    }
}


