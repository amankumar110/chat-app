package in.amankumar110.whatsappapp.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import in.amankumar110.whatsappapp.models.ChatGroup;

public class GroupCompartor extends DiffUtil.ItemCallback<ChatGroup> {

    @Override
    public boolean areItemsTheSame(@NonNull ChatGroup oldItem, @NonNull ChatGroup newItem) {
        return oldItem.getGroupName().equals(newItem.getGroupName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ChatGroup oldItem, @NonNull ChatGroup newItem) {
        return oldItem.getGroupName().equals(newItem.getGroupName());
    }
}
