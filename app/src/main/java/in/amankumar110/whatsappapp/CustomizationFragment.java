package in.amankumar110.whatsappapp;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.ui.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;

import java.util.Objects;

import in.amankumar110.whatsappapp.databinding.ColorPickerDialogLayoutBinding;
import in.amankumar110.whatsappapp.databinding.FragmentCustomizationBinding;
import in.amankumar110.whatsappapp.utils.ColorUtil;
import in.amankumar110.whatsappapp.utils.UiHelper;

import static android.content.Context.MODE_PRIVATE;
import static in.amankumar110.whatsappapp.utils.Constants.HEADER_COLOR_PREFERENCE_KEY;

public class CustomizationFragment extends Fragment {

    FragmentCustomizationBinding binding;
    Dialog dialog;
    SharedPreferences preferences;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCustomizationBinding.inflate(inflater,container,false);
        preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dialog = new Dialog(requireContext());

        navController = Navigation.findNavController(view);

        // Set default or previously saved colors

        UiHelper.setDefaultStatusBarColor(requireActivity());

        int headerColor = ColorUtil.getHeaderColor(requireContext());
        int mainColor = ColorUtil.getMainColor(requireContext());
        int textColor = ColorUtil.getTextColor(requireContext());


        binding.switchLightText.setChecked(ColorUtil.isUsingLightColor(requireContext()));

        setColors(headerColor,binding.vHeaderColor);
        setColors(mainColor,binding.vMainColor);

        binding.btnHeaderColor.setOnClickListener(v -> {
            showColorPicker(binding.vHeaderColor);
        });

        binding.btnMainScreenColor.setOnClickListener(v -> {
            showColorPicker(binding.vMainColor);
        });

        binding.btnApplyChanges.setOnClickListener(v -> {

            // Get the background of the header color view
            Drawable headerBackground = binding.vHeaderColor.getBackground();
            Drawable mainBackground = binding.vMainColor.getBackground();
            boolean useLightText = binding.switchLightText.isChecked();

            ColorUtil.saveGroupColors(mainBackground,headerBackground,useLightText,requireContext());
            navController.navigate(R.id.action_customizationFragment_to_mainFragment);
        });
    }

    private void showColorPicker(View view) {
        ColorPickerDialogLayoutBinding dialogBinding = ColorPickerDialogLayoutBinding.inflate(getLayoutInflater());

        dialogBinding.btnApply.setOnClickListener(v -> {

            // Get selected color from ColorPicker
            int color = dialogBinding.colorPicker.getColor();
            setColors(color,view);
            dialog.dismiss();
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.create();
        dialog.show();
    }

    private void setColors(int color,View v) {

        if(v.getId() == binding.vHeaderColor.getId()) {
            Drawable headerBackground = binding.vHeaderColor.getBackground();
            ((GradientDrawable) headerBackground).setColor(color);
            binding.line1.setColorFilter(color);
            Drawable btnHeaderBackground = binding.btnHeaderColor.getBackground();
            ((GradientDrawable) btnHeaderBackground).setColor(color);
        } else if(v.getId() == binding.vMainColor.getId()) {
            Drawable mainBackground = binding.vMainColor.getBackground();
            ((GradientDrawable) mainBackground).setColor(color);
            binding.line2.setColorFilter(color);
            Drawable btnMainBackground = binding.btnMainScreenColor.getBackground();
            ((GradientDrawable) btnMainBackground).setColor(color);
        }
    }
}
