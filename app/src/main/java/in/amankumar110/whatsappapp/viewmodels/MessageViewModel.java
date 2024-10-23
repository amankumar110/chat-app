package in.amankumar110.whatsappapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.repositories.MessageRepository;

@HiltViewModel
public class MessageViewModel extends ViewModel {

    private MessageRepository repository;

    @Inject
    MessageViewModel(MessageRepository messageRepository) {
        this.repository = messageRepository;
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
