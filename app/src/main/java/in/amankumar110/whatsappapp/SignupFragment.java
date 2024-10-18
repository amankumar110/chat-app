package in.amankumar110.whatsappapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;
import in.amankumar110.whatsappapp.databinding.FragmentSignupBinding;
import in.amankumar110.whatsappapp.enums.SignupState;
import in.amankumar110.whatsappapp.viewmodels.UserViewModel;

@AndroidEntryPoint
public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private NavController navController;

    private UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        navController = Navigation.findNavController(view);
        binding.btnSignup.setOnClickListener(this::onSignupButtonClicked);
    }

    private void onSignupButtonClicked(View v) {
        viewModel.signupAnonymously();
        viewModel.getSignupState().observe(getViewLifecycleOwner(), signupState -> {

            if (signupState == SignupState.SUCCESS) {
                navController.navigate(R.id.action_signupFragment_to_mainFragment);
            } else if(signupState == SignupState.ERROR) {
                Toast.makeText(requireContext(), "Couldn't Sign In, Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

}