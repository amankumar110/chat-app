package in.amankumar110.whatsappapp.repositories;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

public class ImageRepository {

    @Inject
    public ImageRepository() {

    }
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    public UploadTask addImage(String filename, Uri uri) {
        if(uri != null) {
            storageReference = storage.getReference().child(filename);
            return storageReference.putFile(uri);
        }
        return null;
    }
}
