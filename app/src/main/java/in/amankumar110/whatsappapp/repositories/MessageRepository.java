package in.amankumar110.whatsappapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.amankumar110.whatsappapp.models.Message;

public class MessageRepository {

    @Inject
    public MessageRepository() {

    }

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final String MESSAGES_REFERENCE_KEY = "Messages";
    private final DatabaseReference messagesReference = database.getReference(MESSAGES_REFERENCE_KEY);

    public LiveData<List<Message>> getMessages(String groupName) {
        MutableLiveData<List<Message>> messagesLiveData = new MutableLiveData<>();
        DatabaseReference groupReference = this.messagesReference.child(groupName);

        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Message> messages = new ArrayList<>();
                for (DataSnapshot messageReference : snapshot.getChildren()) {
                    Message message = messageReference.getValue(Message.class);
                    messages.add(message);
                }
                messagesLiveData.postValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messagesLiveData;
    }

    public void addMessage(Message message, String groupName) {
        messagesReference.child(groupName).push().setValue(message);
    }

    public void deleteMessage(Message message, String groupName) {
        // Retrieve messages from the group
        messagesReference.child(groupName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message existingMessage = snapshot.getValue(Message.class);

                    // Check if the existing message matches the one to delete
                    // You can adjust the condition based on your message properties
                    if (existingMessage != null && existingMessage.equals(message)) {
                        // Get the key of the message to delete
                        String key = snapshot.getKey();
                        // Delete the message using its key
                        messagesReference.child(groupName).child(key).removeValue();
                        break; // Exit after deleting the message
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }

    public void deleteAllMessages(String groupName) {
        messagesReference.child(groupName).removeValue();
    }
}
