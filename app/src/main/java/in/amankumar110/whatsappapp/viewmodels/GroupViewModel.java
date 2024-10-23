package in.amankumar110.whatsappapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.repositories.GroupRepository;
import in.amankumar110.whatsappapp.repositories.MessageRepository;

@HiltViewModel
public class GroupViewModel extends ViewModel {


    private final GroupRepository repository;

    @Inject
    GroupViewModel(GroupRepository groupRepository) {
        this.repository = groupRepository;
    }

    public LiveData<List<ChatGroup>> getChatGroups() {
        return repository.getChatGroups();
    }

    public void addChatGroups(ChatGroup group) {
        if(!group.getGroupName().isEmpty())
            repository.addChatGroup(group);

    }
}
