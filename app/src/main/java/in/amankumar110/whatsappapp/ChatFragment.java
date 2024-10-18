package in.amankumar110.whatsappapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import in.amankumar110.whatsappapp.adapters.MessageAdapter;
import in.amankumar110.whatsappapp.databinding.FragmentChatBinding;
import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.utils.ColorUtil;
import in.amankumar110.whatsappapp.utils.ContentManager;
import in.amankumar110.whatsappapp.utils.UiHelper;
import in.amankumar110.whatsappapp.viewmodels.UserViewModel;

@AndroidEntryPoint
public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private NavController navController;
    private ChatGroup group;
    private MessageAdapter adapter;
    private UserViewModel viewModel;
    private ContentManager contentManager = new ContentManager();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contentManager.setupLauncher(this,
                uri -> {
                    if(uri != null) {
                        Bundle data = new Bundle();
                        data.putString("Uri", uri.toString());
                        data.putString("groupName", group.getGroupName());
                        navController.navigate(R.id.action_chatFragment_to_imageFragment, data);
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        UiHelper.setCustomStatusBarColor(requireActivity(),R.color.colorPrimary);

        // Initialize ViewBinding
        binding = FragmentChatBinding.inflate(inflater, container, false);

        initializeColors();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Navigation Controller
        navController = Navigation.findNavController(view);

        adapter = new MessageAdapter(requireContext());

        // Initialize RecyclerView and its adapter
        binding.rvMessagesList.setAdapter(adapter);
        binding.rvMessagesList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve group name from arguments if available
        if (getArguments() != null) {
            String json = getArguments().getString("group");
            group = new Gson().fromJson(json, ChatGroup.class);
        }

        binding.setGroupName(group.getGroupName()); // Bind group name to layout

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe messages and update UI accordingly
        viewModel.getMessages(group.getGroupName()).observe(getViewLifecycleOwner(), messageList -> {
            if (messageList == null || messageList.isEmpty()) {
                messageList = new ArrayList<>(); // Prevent NullPointerException
            }

            // Update adapter and scroll to the latest message
            adapter.setMessages(messageList);
            adapter.notifyDataSetChanged();

            binding.rvMessagesList.post(() -> {
                int lastPosition = binding.rvMessagesList.getAdapter().getItemCount() - 1;
                if (lastPosition >= 0) {
                    binding.rvMessagesList.smoothScrollToPosition(lastPosition); // Scroll to last item
                }
            });
        });

        // Send button click listener
        binding.btnSend.setOnClickListener(this::onSendButtonClicked);
        binding.btnPic.setOnClickListener(this::onImageButtonClicked);
        binding.btnEdit.setOnClickListener(this::onEditButtonClicked);
    }

    // Handle send button click
    private void onSendButtonClicked(View v) {
        String text = binding.etMessage.getText().toString().trim();
        String senderId = FirebaseAuth.getInstance().getUid();
        long currentTime = System.currentTimeMillis();

        // Validate message input
        if (senderId != null && !text.isEmpty()) {
            Message message = new Message(text, currentTime, senderId);
            viewModel.addMessage(message, group.getGroupName()); // Add message through ViewModel
        } else {
            Toast.makeText(getContext(), "Enter a Message to Send!", Toast.LENGTH_SHORT).show();
        }

        // Refresh EditText field
        refreshEditText();
    }

    // Clear the input field and hide the keyboard
    private void refreshEditText() {
        binding.etMessage.setText(""); // Clear the input
        binding.etMessage.clearFocus(); // Remove focus from the input field

        // Hide the soft keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(binding.etMessage.getWindowToken(), 0);
        }
    }

    private void onImageButtonClicked(View v) {
        contentManager.requestImage();
    }

    private void onEditButtonClicked(View v) {
        navController.navigate(R.id.action_chatFragment_to_customizationFragment);
    }

    private void initializeColors() {

        if(UiHelper.isDarkModeEnabled(requireContext())) {

            int color = requireContext().getResources().getColor(R.color.colorPrimary,requireContext().getTheme());
            UiHelper.setCustomStatusBarColor(requireActivity(),color);

        } else {
            binding.textView.setBackgroundColor(ColorUtil.getHeaderColor(requireContext()));
            binding.btnEdit.setBackgroundColor(ColorUtil.getHeaderColor(requireContext()));
            UiHelper.setCustomStatusBarColor(requireActivity(), ColorUtil.getHeaderColor(requireContext()));
            binding.getRoot().setBackgroundColor(ColorUtil.getMainColor(requireContext()));
            binding.btnSend.setBackgroundColor(ColorUtil.getHeaderColor(requireContext()));

            ColorStateList list = ColorStateList.valueOf(ColorUtil.getHeaderColor(requireContext()));
            binding.btnPic.setBackgroundTintList(list);
        }
    }

    @Override
    public void onDestroy() {

        if (group.isPrivacyRoom()) {
            viewModel.deleteAllMessages(group.getGroupName());
        }
        UiHelper.setDefaultStatusBarColor(requireActivity());
        super.onDestroy();
    }


}
