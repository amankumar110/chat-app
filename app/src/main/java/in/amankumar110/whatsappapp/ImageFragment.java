package in.amankumar110.whatsappapp;

import android.net.Uri;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;
import in.amankumar110.whatsappapp.databinding.FragmentImageBinding;
import in.amankumar110.whatsappapp.models.Message;
import in.amankumar110.whatsappapp.utils.NetworkManager;
import in.amankumar110.whatsappapp.utils.UiHelper;
import in.amankumar110.whatsappapp.viewmodels.ImageViewModel;
import in.amankumar110.whatsappapp.viewmodels.MessageViewModel;
import in.amankumar110.whatsappapp.viewmodels.UserViewModel;

@AndroidEntryPoint
public class ImageFragment extends Fragment {

    private Uri uri;
    private String groupName;
    private FragmentImageBinding binding;
    private ImageViewModel imageViewModel;
    private NavController navController;
    private MessageViewModel messageViewModel;
    private String imageName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initialize components like arguments and Firebase

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        navController = Navigation.findNavController(view);

        initializeArguments();
        initializeUI();

        // Set up the button click listener
        setupPostButtonListener();
    }

    // 1. Extract URI passed in arguments
    private void initializeArguments() {
        if (getArguments() != null) {
            String uriString = getArguments().getString("Uri");
            groupName = getArguments().getString("groupName");
            this.uri = Uri.parse(uriString);
        }
    }

    // 3. Initialize UI components, like setting the image
    private void initializeUI() {
        if (uri != null) {
            binding.ivImage.setImageURI(uri);
        }
    }

    // 4. Set up the button click listener to start image upload when clicked
    private void setupPostButtonListener() {
        binding.btnPost.setOnClickListener(v -> {

            if (!NetworkManager.isNetworkAvailable(requireContext())) {
                UiHelper.showMessage(getString(R.string.image_upload_fail_network_error),
                        requireContext());
                return;
            }

            if (uri == null) {
                UiHelper.showMessage(getString(R.string.no_image_selected_message),
                        requireContext());
                return;
            }

            showLoader();
            uploadImageToFirebase(uri);
        });
    }

    // 5. Upload the image to Firebase Storage
    private void uploadImageToFirebase(Uri uri) {

        String uniqueFileName = "images/" + UUID.randomUUID().toString() + ".jpg";
        this.imageName = uniqueFileName;

        try {
            imageViewModel.addImage(uniqueFileName, uri)
                    .addOnCompleteListener(this::handleUploadComplete)
                    .addOnFailureListener(this::handleUploadFailure);

        } catch (IllegalArgumentException e) {
            UiHelper.showMessage("Image Couldn't be uploaded!",requireContext());
        }
    }

    // 6. Handle failure during file upload
    private void handleUploadFailure(Exception e) {
        UiHelper.showMessage("Upload Failed: " + e.getMessage(),requireContext());
        hideLoader();
    }

    // 7. Handle completion of file upload
    private void handleUploadComplete(Task<UploadTask.TaskSnapshot> task) {
        if (task.isSuccessful()) {
            // If upload is successful, get the download URL
            StorageReference storageRef = task.getResult().getStorage();
            storageRef.getDownloadUrl().addOnSuccessListener(this::handleImageUploadSuccess);
        } else {
            UiHelper.showMessage("Upload not successful",requireContext());
        }
    }

    private void handleImageUploadSuccess(Uri downloadUri) {

        Message message = new Message();
        message.setImage(true);
        message.setImageName(imageName);
        message.setDownloadUrl(downloadUri.toString());
        message.setSenderId(FirebaseAuth.getInstance().getUid());
        message.setTime(System.currentTimeMillis());

        if (groupName != null && !groupName.isEmpty()) {
            messageViewModel.addMessage(message, groupName);
        }

        hideLoader();

        navController.popBackStack();
    }

    private void showLoader() {
        binding.overlay.setVisibility(View.VISIBLE);
        binding.spinKit.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        binding.overlay.setVisibility(View.GONE);
        binding.spinKit.setVisibility(View.GONE);
    }
}
