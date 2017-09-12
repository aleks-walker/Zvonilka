package kg.kloop.android.zvonilka.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.activities.AddClientActivity;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallClientFragment extends Fragment {

    private static final int REQUEST_CODE_ADD_CLIENT = 101;
    RecyclerView clientsToCallRecyclerView;
    FloatingActionButton addClientFloatingActionButton;

    public CallClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_client, container, false);

        clientsToCallRecyclerView = view.findViewById(R.id.clients_to_call_recycler_view);
        addClientFloatingActionButton = view.findViewById(R.id.add_client_floating_action_button);

        addClientFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddClientActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CLIENT);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_CLIENT:

                    break;
            }
        }
    }
}
