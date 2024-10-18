package in.amankumar110.whatsappapp.utils;


import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import in.amankumar110.whatsappapp.R;

public class UiHelper {

    // Method to set default status bar color based on the theme (light or dark mode)
    public static void setDefaultStatusBarColor(Activity activity) {
        int colorResId;
        // Check if the device is in night mode
        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            // Apply the dark mode status bar color
            colorResId = R.color.statusBarColorNight;
        } else {
            // Apply the light mode status bar color
            colorResId = R.color.statusBarColor;
        }

        // Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
        }
    }

    // Method to set a custom status bar color (for example, from a color resource)
    public static void setCustomStatusBarColor(Activity activity, int color) {
        // Set the status bar color to a custom value
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(color);  // Apply the color directly
        }
    }


    // Method to set custom navigation bar color
    public static void setCustomNavigationBarColor(Activity activity, int colorResId) {
        // Set the navigation bar color to a custom value
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setNavigationBarColor(ContextCompat.getColor(activity, colorResId));
        }
    }

    public static void showMessage(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isDarkModeEnabled(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

}
