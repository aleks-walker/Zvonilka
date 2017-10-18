package kg.kloop.android.zvonilka.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.CallLogAdapter.ViewHolder;
import kg.kloop.android.zvonilka.objects.Call;

/**
 * Created by alexwalker on 23.09.17.
 */

public class CallLogAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    ArrayList<Call> callArrayList;
    String callType;
    String callDate;
    String callDuration;
    String callResult;

    public CallLogAdapter(Context context, ArrayList<Call> callArrayList) {
        this.context = context;
        this.callArrayList = callArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Call currentCall =  callArrayList.get(position);
        formatData(currentCall);
        holder.clientTextView.setText(currentCall.getPhoneNumber());
        holder.callTypeTextView.setText(callType);
        holder.callDateTextView.setText(callDate);
        holder.callDurationTextView.setText(callDuration);
        holder.callDescriptionTextView.setText(currentCall.getDescription());
        holder.callResultTextView.setText(callResult);
    }

    private void formatData(Call currentCall) {
        callType = currentCall.getType();
        callDate = String.valueOf(
                        DateUtils.getRelativeTimeSpanString(
                        Long.valueOf(currentCall.getDate()),
                        System.currentTimeMillis(),
                        DateUtils.SECOND_IN_MILLIS));
        callDuration = currentCall.getDuration() + " sec";
        switch (currentCall.getCallResult()){
            case 0:
                callResult = "Successful call";
                break;
            case 1:
                callResult = "Call back";
                break;
            case 2:
                callResult = "Don't call";
                break;
        }

    }

    @Override
    public int getItemCount() {
        return callArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView clientTextView;
        TextView callTypeTextView;
        TextView callDateTextView;
        TextView callDurationTextView;
        TextView callDescriptionTextView;
        TextView callResultTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            clientTextView = itemView.findViewById(R.id.client_call_log_text_view);
            callTypeTextView = itemView.findViewById(R.id.call_type_text_view);
            callDateTextView = itemView.findViewById(R.id.call_date_text_view);
            callDurationTextView = itemView.findViewById(R.id.call_duration_text_view);
            callDescriptionTextView = itemView.findViewById(R.id.call_description_text_view);
            callResultTextView = itemView.findViewById(R.id.call_result_text_view);
        }
    }
}
