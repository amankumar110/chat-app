package in.amankumar110.whatsappapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        setGroupText();
        animateHeading();

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

    public void animateHeading() {
        Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.move_up_down_and_fade_in);
        binding.tvHeading.startAnimation(animation);
        binding.btnSignup.startAnimation(animation);
        binding.tvAppName.startAnimation(animation);

        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in);
        binding.view1.startAnimation(fadeIn);
        binding.view2.startAnimation(fadeIn);
        binding.view3.startAnimation(fadeIn);
        binding.view4.startAnimation(fadeIn);
    }

    public void setGroupText() {
        SpannableString string = new SpannableString(getString(R.string.app_name));

        int primaryColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary);
        int textColor = ContextCompat.getColor(requireContext(),R.color.colorText);
        string.setSpan(new ForegroundColorSpan(primaryColor),0,6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(textColor),7,string.length()-1,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        binding.tvAppName.setText(string);
    }

}