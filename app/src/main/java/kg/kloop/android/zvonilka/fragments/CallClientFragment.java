package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.kloop.android.zvonilka.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallClientFragment extends Fragment {

    RecyclerView clientsToCallRecyclerView;
    FloatingActionButton addClientFloatingActionButton;

    public CallClientFragment() {
        // Required empty public constructor
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

            }
        });

        return view;
    }

}
