package in.amankumar110.whatsappapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.method.Touch;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import in.amankumar110.whatsappapp.R;
import in.amankumar110.whatsappapp.databinding.RowChatBinding;
import in.amankumar110.whatsappapp.databinding.RowNoItemsBinding;
import in.amankumar110.whatsappapp.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE = 1;
    private static final int VIEW_TYPE_NO_ITEMS = 0;

    private final Context context;
    private List<Message> messageList = new ArrayList<>();

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList==null || messageList.isEmpty()) {
            return VIEW_TYPE_NO_ITEMS;
        } else {
            return VIEW_TYPE_MESSAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE) {
            // Inflate the message row layout
            RowChatBinding binding = RowChatBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
            );
            return new MessageViewHolder(binding);
        } else {
            // Inflate the no items view layout
            RowNoItemsBinding binding = RowNoItemsBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
            );
            return new NoItemsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageViewHolder) {
            Message message = messageList.get(position);

            // Bind the message to the view
            ((MessageViewHolder) holder).binding.setMessage(message);

            if(message.isImage()) {

                Uri uri = Uri.parse(message.getDownloadUrl());

                if(message.isImageSentByMe()) {
                    Glide.with(context).load(uri).placeholder(R.drawable.ic_image).into(((MessageViewHolder) holder).binding.ivImageSentByMe);
                } else {
                    Glide.with(context).load(uri).placeholder(R.drawable.ic_image).into(((MessageViewHolder) holder).binding.ivImageNotSentByMe);
                }
            }

        } else {
            // Handle the No items view (if needed, customize the message)
        }
    }

    @Override
    public int getItemCount() {
        // If no items are available, we still want to show one item, which is the "No items" view
        if (messageList == null || messageList.isEmpty()) {
            return 1;
        } else {
            return messageList.size();
        }
    }

    /**
     * Update the message list and notify the adapter
     */
    public void setMessages(List<Message> newMessages) {
        this.messageList = newMessages;
        notifyDataSetChanged(); // Or use more specific notify methods for better performance
    }

    /**
     * Add a single message to the list and notify the adapter
     */
    public void addMessage(Message message) {
        this.messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private final RowChatBinding binding;

        public MessageViewHolder(RowChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class NoItemsViewHolder extends RecyclerView.ViewHolder {

        private final RowNoItemsBinding binding;

        public NoItemsViewHolder(RowNoItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
