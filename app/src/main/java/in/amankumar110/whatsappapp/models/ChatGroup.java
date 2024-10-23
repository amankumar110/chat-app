package in.amankumar110.whatsappapp.models;

import androidx.databinding.BindingAdapter;

import in.amankumar110.whatsappapp.BR;

public class ChatGroup {

    private String groupName;

    private boolean isPrivacyRoom;

    public ChatGroup(String groupName,boolean isPrivacyRoom) {

        this.groupName = groupName;
        this.isPrivacyRoom = isPrivacyRoom;
    }

    public ChatGroup() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isPrivacyRoom() {
        return isPrivacyRoom;
    }

    public void setPrivacyRoom(boolean privacyRoom) {
        isPrivacyRoom = privacyRoom;
    }


}
