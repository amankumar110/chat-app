package in.amankumar110.whatsappapp.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Message {

    @SerializedName("message")
    private String message;

    @SerializedName("time")
    private Long time;

    @SerializedName("sender_id")
    private String senderId;

    @SerializedName("sent_by_me")
    private boolean sentByMe;

    @SerializedName("is_image")
    private boolean isImage;

    @SerializedName("download_url")
    private String downloadUrl;

    @SerializedName(("image_name"))
    private String imageName;

    // Constructor for text message
    public Message(String message, Long time, String senderId) {
        this.message = message;
        this.time = time;
        setSenderId(senderId);
        this.isImage = false;  // Text message by default
    }

    // Constructor for image message
    public Message(boolean isImage, String imageUri, Long time, String senderId) {
        this.isImage = isImage;
        this.downloadUrl = imageUri;
        this.time = time;
        setSenderId(senderId);
    }

    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public String getFormattedTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        dateFormatter.setTimeZone(TimeZone.getDefault());
        return dateFormatter.format(date);
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
        // Determine if the message is sent by the current user
        sentByMe = senderId != null && FirebaseAuth.getInstance().getUid() != null
                && senderId.trim().equals(FirebaseAuth.getInstance().getUid().trim());
    }

    public boolean isMessageSentByMe() {
        return sentByMe && !isImage;
    }

    public boolean isImageSentByMe() {
        return isImage && sentByMe;
    }

    public boolean isMessageReceived() {
        return !sentByMe && !isImage;
    }

    public boolean isImageReceived() {
        return !sentByMe && isImage;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Message)) return false;

        Message otherMessage = (Message) obj;

        return message != null && message.equals(otherMessage.message) &&
                time != null && time.equals(otherMessage.time) &&
                senderId != null && senderId.equals(otherMessage.senderId);
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        return result;
    }
}
