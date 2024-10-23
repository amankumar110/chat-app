package in.amankumar110.whatsappapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class ImageDownloader {

    public static long downloadImage(Uri uri, Context context) {
        // Get the system's DownloadManager service
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // Create a DownloadManager request with the provided Uri
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle("Downloading Image")  // Set a title for the download notification
                .setMimeType("image/jpeg")  // Specify the MIME type of the file
                .setDescription("Image is being downloaded...")  // Set a description for the notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)  // Show notification when download completes
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)  // Allow downloads over Wi-Fi and mobile data
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image_"+System.currentTimeMillis()+".jpg")  // Save the image in the Downloads folder
                .setAllowedOverMetered(true)  // Allow download over metered networks like mobile data
                .setAllowedOverRoaming(false);  // Disallow download over roaming

        // Enqueue the request to the download manager
        long id = downloadManager.enqueue(request);


        // Optionally, you can add code to track the download progress using the ID `id`
        // ...

        return id;
    }




}
