package in.amankumar110.whatsappapp.broadcastreceivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);

        if(id != -1) {
            Toast.makeText(context, "Download Completed!", Toast.LENGTH_SHORT).show();
            openDownloadedFile(context, id);

        } else {
            Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    private static void openDownloadedFile(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // Use DownloadManager's openDownloadedFile to open the file
        try {
            Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
            openFileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Required for non-Activity context
            Uri uri = downloadManager.getUriForDownloadedFile(downloadId);

            if (uri != null) {
                openFileIntent.setDataAndType(uri, "image/jpeg");  // Use the correct MIME type
                openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (openFileIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(openFileIntent);
                } else {
                    Toast.makeText(context, "No app found to open the image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Download failed or file not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error opening file", Toast.LENGTH_SHORT).show();
        }
    }
}


