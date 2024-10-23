package in.amankumar110.whatsappapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.compose.ui.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import in.amankumar110.whatsappapp.R;
import in.amankumar110.whatsappapp.databinding.RowChatBinding;
import in.amankumar110.whatsappapp.databinding.RowNoItemsBinding;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.utils.ColorUtil;
import in.amankumar110.whatsappapp.utils.UiHelper;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE = 1;
    private static final int VIEW_TYPE_NO_ITEMS = 0;

    private final Context context;
    private List<Message> messageList = new ArrayList<>();
    private OnImageMessageClicked imageCallback;

    public MessageAdapter(Context context, OnImageMessageClicked onImageClicked) {
        this.context = context;
        this.imageCallback = onImageClicked;
    }

    public interface OnImageMessageClicked {
        void onClick(String downloadUrl);
    }

    @Override
    public int getItemViewType(int position) {
        // Return VIEW_TYPE_NO_ITEMS if messageList is empty, otherwise return VIEW_TYPE_MESSAGE
        if (messageList == null || messageList.isEmpty()) {
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
            int headerColor = ColorUtil.getHeaderColor(context);

            if(!UiHelper.isDarkModeEnabled(context))
               setColors(headerColor, ((MessageViewHolder) holder).binding);

            // Bind message data to the view
            ((MessageViewHolder) holder).binding.setMessage(message);

            if(message.isImageSentByMe()) {
                ((MessageViewHolder) holder).binding.senderImageItem.tvImageName
                        .setText(message.getImageName());

                ((MessageViewHolder) holder).binding.senderImageItem.imageView.setOnClickListener(v ->
                        imageCallback.onClick(message.getDownloadUrl()));

            } else if(message.isImageReceived()) {
                ((MessageViewHolder) holder).binding.receiverImageItem.tvImageName
                        .setText(message.getImageName());

                ((MessageViewHolder) holder).binding.receiverImageItem.imageView.setOnClickListener(v ->
                        imageCallback.onClick(message.getDownloadUrl()));
            }

        } else if (holder instanceof NoItemsViewHolder) {
            // Handle the case when there are no items (if needed)
        }
    }

    @Override
    public int getItemCount() {
        // Return the size of the messageList or 1 if no items are available
        return (messageList == null || messageList.isEmpty()) ? 1 : messageList.size();
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

    public OnImageMessageClicked getImageCallback() {
        return imageCallback;
    }

    public void setImageCallback(OnImageMessageClicked imageCallback) {
        this.imageCallback = imageCallback;
    }

    /**
     * Set the colors for the message layout components
     */
    private void setColors(int color, RowChatBinding view) {
        ((GradientDrawable)view.receiverImageItem.getRoot().getBackground()).setColor(color);
        ((GradientDrawable)view.senderImageItem.getRoot().getBackground()).setColor(color);
        ((GradientDrawable)view.senderMessageContainer.getBackground()).setColor(color);
        ((GradientDrawable)view.receiverMessageContainer.getBackground()).setColor(color);
        view.senderImageItem.tvImageName.setTextColor(ColorUtil.getTextColor(context));
        view.receiverImageItem.tvImageName.setTextColor(ColorUtil.getTextColor(context));
        view.tvReceiverMessage.setTextColor(ColorUtil.getTextColor(context));
        view.tvSenderMessage.setTextColor(ColorUtil.getTextColor(context));
        view.receiverImageItem.imageView.setColorFilter(ColorUtil.getTextColor(context));
        view.senderImageItem.imageView.setColorFilter(ColorUtil.getTextColor(context));
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
