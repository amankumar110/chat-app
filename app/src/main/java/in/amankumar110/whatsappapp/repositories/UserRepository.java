package in.amankumar110.whatsappapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.viewmodels.UserViewModel;

public class UserRepository {

    @Inject
    public UserRepository() {

    }
    public void signupAnonymously(OnCompleteListener<AuthResult> callback) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(callback);
    }

    public String getUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
