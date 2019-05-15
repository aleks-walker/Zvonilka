package kg.kloop.android.zvonilka.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import kg.kloop.android.zvonilka.AuthViewModel;
import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.fragments.AuthFragment;

public class AuthActivity extends AppCompatActivity implements LifecycleOwner {

    private static final int RC_SIGN_IN = 100;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //FirebaseAuth.getInstance().signOut();
        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.auth_constraint_layout, AuthFragment.newInstance())
                            .commit();
                } else {
                    startAuthActivity();
                }
            }
        });

    }

    private void startAuthActivity() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                viewModel.checkAccess();
                viewModel.getHasAccess().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.auth_constraint_layout, AuthFragment.newInstance())
                                    .commit();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                startAuthActivity();
            }
        }
    }

}


