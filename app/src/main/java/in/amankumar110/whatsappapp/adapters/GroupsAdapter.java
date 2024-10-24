package in.amankumar110.whatsappapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import in.amankumar110.whatsappapp.R;
import in.amankumar110.whatsappapp.databinding.GroupItemBinding;
import in.amankumar110.whatsappapp.databinding.EmptyListItemBinding;
import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.utils.GroupCompartor;

public class GroupsAdapter extends ListAdapter<ChatGroup, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_GROUP = 0;
    private static final int VIEW_TYPE_EMPTY = 1;
    private final OnGroupItemClicked callback;
    private final Context context;

    public interface OnGroupItemClicked {
        void onClick(ChatGroup group);
    }

    public GroupsAdapter(Context context,OnGroupItemClicked callback) {
        super(new GroupCompartor());
        this.callback = callback;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // Show empty view if the list is empty
        return getCurrentList().isEmpty() ? VIEW_TYPE_EMPTY : VIEW_TYPE_GROUP;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            EmptyListItemBinding emptyBinding = EmptyListItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new EmptyViewHolder(emptyBinding);
        } else {

            GroupItemBinding binding = GroupItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );

            return new GroupViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GroupViewHolder) {
            ChatGroup group = getItem(position);
            ((GroupViewHolder) holder).binding.setGroup(group);


            if(group.isPrivacyRoom())
                ((GroupViewHolder) holder).binding.ivPrivacy.setImageResource(R.drawable.lock);
            else
                ((GroupViewHolder) holder).binding.ivPrivacy.setImageResource(R.drawable.unlock);

            startAnimation(((GroupViewHolder) holder).binding.getRoot(),position);

            ((GroupViewHolder) holder).binding.getRoot().setOnClickListener(v ->
                    callback.onClick(group));
        }
    }

    @Override
    public int getItemCount() {
        // If the list is empty, return 1 to show the empty view
        return getCurrentList().isEmpty() ? 1 : super.getItemCount();
    }

    private void startAnimation(View v,int position) {

        Animation animation = null;

        if(position%2==0)
            animation= AnimationUtils.loadAnimation(context,R.anim.move_right_to_left_fade_in);
        else
            animation= AnimationUtils.loadAnimation(context,R.anim.move_left_to_right_fade_in);

        v.startAnimation(animation);

    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        private final GroupItemBinding binding;

        public GroupViewHolder(@NonNull GroupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull EmptyListItemBinding binding) {
            super(binding.getRoot());
        }
    }
}
