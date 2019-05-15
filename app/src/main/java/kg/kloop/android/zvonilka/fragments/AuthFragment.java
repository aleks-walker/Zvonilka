package kg.kloop.android.zvonilka.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kg.kloop.android.zvonilka.AuthViewModel;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.activities.MainActivity;

public class AuthFragment extends Fragment {

    private AuthViewModel viewModel;
    private boolean hasAccess;

    public AuthFragment() {

    }

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_auth, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);

        Button kloopButton = view.findViewById(R.id.kloop_auth_button);
        final TextView userTextView = view.findViewById(R.id.auth_user_text_view);
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    userTextView.setText(firebaseUser.getDisplayName());
                }
            }
        });
        viewModel.getHasAccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    hasAccess = true;
                }
            }
        });
        kloopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasAccess) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.log_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                viewModel.getUser().setValue(null);
                viewModel.getHasAccess().setValue(false);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

}
