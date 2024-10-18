package in.amankumar110.whatsappapp.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import in.amankumar110.whatsappapp.enums.SignupState;
import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.repositories.UserRepository;

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final UserRepository repository;
    private final MutableLiveData<SignupState> signupState = new MutableLiveData<>();
    private final MutableLiveData<String> signupError = new MutableLiveData<>();

    @Inject
    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    // Expose LiveData for signup state and error
    public LiveData<SignupState> getSignupState() {
        return signupState;
    }

    public LiveData<String> getSignupError() {
        return signupError;
    }

    // Handle the task and update LiveData for loading, success, and error
    public void signupAnonymously() {
        signupState.setValue(SignupState.LOADING); // Set state to loading
        repository.signupAnonymously(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signupState.setValue(SignupState.SUCCESS);  // Set state to success
                } else {
                    signupState.setValue(SignupState.ERROR);    // Set state to error
                    signupError.setValue(Objects.requireNonNull(task.getException()).getMessage()); // Capture error message
                }
            }
        });
    }

    public String getUId() {
        return repository.getUserId();
    }

    public void signOut() {
        repository.signOut();
    }

    public LiveData<List<ChatGroup>> getChatGroups() {
        return repository.getChatGroups();
    }

    public void addChatGroups(ChatGroup group) {
        if(!group.getGroupName().isEmpty())
            repository.addChatGroup(group);

    }

    public LiveData<List<Message>> getMessages(String groupName) {
        return repository.getMessages(groupName);
    }

    public void addMessage(Message message, String groupName) {
        if(message!=null && !groupName.isEmpty())
            repository.addMessage(message,groupName);
    }

    public void deleteMessage(Message message,String groupName) {
        if(message!=null && !groupName.isEmpty()) {
            repository.deleteMessage(message,groupName);
        }
    }

    public void deleteAllMessages(String groupName) {
        if(!groupName.isEmpty())
            repository.deleteAllMessages(groupName);
    }
}
