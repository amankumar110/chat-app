package in.amankumar110.whatsappapp.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import in.amankumar110.whatsappapp.models.Message;

public class MessageComparator extends DiffUtil.ItemCallback<Message> {
    @Override
    public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
        return oldItem.getMessage().equals(newItem.getMessage()) &&
                oldItem.getSenderId().equals(newItem.getSenderId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
        return oldItem.getMessage().equals(newItem.getMessage()) &&
                oldItem.getSenderId().equals(newItem.getSenderId());
    }
}
