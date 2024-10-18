package in.amankumar110.whatsappapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import in.amankumar110.whatsappapp.adapters.GroupsAdapter;
import in.amankumar110.whatsappapp.databinding.AddGroupDialogueBinding;
import in.amankumar110.whatsappapp.databinding.FragmentMainBinding;
import in.amankumar110.whatsappapp.models.ChatGroup;
import in.amankumar110.whatsappapp.utils.GroupCompartor;
import in.amankumar110.whatsappapp.viewmodels.UserViewModel;


@AndroidEntryPoint
public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private UserViewModel viewModel;
    private GroupsAdapter adapter;
    private AlertDialog addGroupDialog;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        navController = Navigation.findNavController(view);

        adapter = new GroupsAdapter(new GroupCompartor(), group -> {

            Bundle data = new Bundle();
            String json = new Gson().toJson(group);
            data.putString("group",json);
            navController.navigate(R.id.action_mainFragment_to_chatFragment,data);
        });

        binding.rvGroupList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvGroupList.setAdapter(adapter);

        viewModel.getChatGroups().observe(getViewLifecycleOwner(), groups -> {

            if(groups==null)
                groups = new ArrayList<>();

            adapter.submitList(groups);
            adapter.notifyDataSetChanged();

        });

        binding.floatingActionButton.setOnClickListener(this::onAddGroupButtonClicked);

    }

    private void onAddGroupButtonClicked(View v) {

        addGroupDialog = new AlertDialog.Builder(requireContext())
                .create();

        AddGroupDialogueBinding addGroupBinding = AddGroupDialogueBinding.inflate(getLayoutInflater());
        addGroupDialog.setView(addGroupBinding.getRoot());
        addGroupDialog.create();
        addGroupDialog.show();

        addGroupBinding.btnCreate.setOnClickListener(view -> {

            String groupName = addGroupBinding.etGroupName.getText().toString();
            boolean isPrivacyRoom = addGroupBinding.switchPrivacyRoom.isChecked();

            if (groupName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter A Group Name", Toast.LENGTH_SHORT).show();
                return;
            }

            ChatGroup group = new ChatGroup(groupName,isPrivacyRoom);
            viewModel.addChatGroups(group);
            addGroupDialog.dismiss();
        });


    }
}