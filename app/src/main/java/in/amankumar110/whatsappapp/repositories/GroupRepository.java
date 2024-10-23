package in.amankumar110.whatsappapp.repositories;

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

import in.amankumar110.whatsappapp.models.ChatGroup;

public class GroupRepository {

    @Inject
    public GroupRepository() {

    }

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final String CHAT_GROUPS_REFERENCE_KEY = "Chat-Groups";
    private final DatabaseReference chatGroupsReference = database.getReference(CHAT_GROUPS_REFERENCE_KEY);


    public LiveData<List<ChatGroup>> getChatGroups() {
        final MutableLiveData<List<ChatGroup>> groupsLiveData = new MutableLiveData<>();
        this.chatGroupsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ChatGroup> groups = new ArrayList<>();
                for (DataSnapshot groupData : snapshot.getChildren()) {
                    ChatGroup group = groupData.getValue(ChatGroup.class);
                    groups.add(group);
                }
                groupsLiveData.setValue(groups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return groupsLiveData;
    }

    public void addChatGroup(ChatGroup group) {
        this.chatGroupsReference.push().setValue(group);
    }
}
