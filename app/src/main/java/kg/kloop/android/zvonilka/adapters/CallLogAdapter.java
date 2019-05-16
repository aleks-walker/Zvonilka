package kg.kloop.android.zvonilka.adapters;

import android.content.Context;
import android.provider.CallLog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.CallLogAdapter.ViewHolder;
import kg.kloop.android.zvonilka.objects.Call;

/**
 * Created by alexwalker on 23.09.17.
 */

public class CallLogAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<Call> callArrayList;
    private String callType;
    private String callDate;
    private String callDuration;
    private String callResult;

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
        callType = decodeCallType(currentCall.getType());
        callDate = String.valueOf(
                        DateUtils.getRelativeTimeSpanString(
                        Long.valueOf(currentCall.getDate()),
                        System.currentTimeMillis(),
                        DateUtils.SECOND_IN_MILLIS));
        callDuration = currentCall.getDuration() + " sec";
        switch (currentCall.getCallResult()){
            case 0:
                callResult = context.getString(R.string.successful_call);
                break;
            case 1:
                callResult = context.getString(R.string.call_back);
                break;
            case 2:
                callResult = context.getString(R.string.dont_call);
                break;
        }

    }

    private String decodeCallType(String type) {
        switch (Integer.valueOf(type)) {
            case CallLog.Calls.INCOMING_TYPE:
                return context.getString(R.string.incoming_call);
            case CallLog.Calls.OUTGOING_TYPE:
                return context.getString(R.string.outgoing_call);
            case CallLog.Calls.MISSED_TYPE:
                return context.getString(R.string.missed_call);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return callArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView clientTextView;
        TextView callTypeTextView;
        TextView callDateTextView;
        TextView callDurationTextView;
        TextView callDescriptionTextView;
        TextView callResultTextView;

        ViewHolder(View itemView) {
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
