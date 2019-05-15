package kg.kloop.android.zvonilka;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kg.kloop.android.zvonilka.objects.User;

public class AuthViewModel extends ViewModel {

    private static final String TAG = AuthViewModel.class.getSimpleName();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private MutableLiveData<Boolean> hasAccess;
    private MutableLiveData<FirebaseUser> user;

    public AuthViewModel() {
        hasAccess = new MutableLiveData<>();
        user = new MutableLiveData<>();
        user.setValue(FirebaseAuth.getInstance().getCurrentUser());
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (user.getValue() != null) {
            databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Users").child(user.getValue().getUid());
            /*User myUser = new User(user.getValue().getUid(), user.getValue().getDisplayName(), user.getValue().getPhoneNumber(), user.getValue().getEmail(), user.getValue().getPhotoUrl());
            databaseReference.setValue(myUser);*/
            checkAccess();
        }


    }

    public void checkAccess() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getValue() != null) {
                    hasAccess.setValue(true);
                } else {
                    hasAccess.setValue(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public MutableLiveData<Boolean> getHasAccess() {
        return hasAccess;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

}
