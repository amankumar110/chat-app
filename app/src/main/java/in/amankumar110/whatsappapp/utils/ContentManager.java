package in.amankumar110.whatsappapp.utils;

import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class ContentManager {

    private ActivityResultLauncher<String> launcher;

    public void setupLauncher(Fragment fragment, ActivityResultCallback<Uri> callback) {
        launcher = fragment.registerForActivityResult(new ActivityResultContracts.GetContent(), callback);
    }

    public void requestImage() {
        if (launcher != null) {
            launcher.launch("image/*");
        } else {
            // Handle the error: launcher not initialized
            throw new IllegalStateException("Launcher is not set up. Call setupLauncher() first.");
        }
    }
}
