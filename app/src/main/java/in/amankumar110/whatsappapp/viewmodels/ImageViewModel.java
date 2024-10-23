package in.amankumar110.whatsappapp.viewmodels;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import in.amankumar110.whatsappapp.repositories.ImageRepository;

@HiltViewModel
public class ImageViewModel extends ViewModel {

    private final ImageRepository repository;

    @Inject
    public ImageViewModel(ImageRepository repository) {
        this.repository = repository;
    }

    // Method to add image
    public UploadTask addImage(String filename, Uri uri) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename must not be null or empty");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null");
        }
        return repository.addImage(filename, uri);
    }
}
