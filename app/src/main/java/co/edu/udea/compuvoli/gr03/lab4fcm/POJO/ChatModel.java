package co.edu.udea.compuvoli.gr03.lab4fcm.POJO;

import android.net.Uri;

/**
 * Created by Lina on 17/10/2016.
 */
public  class ChatModel {
    private String text;
    private String name;
    private String photoUrl;
  private String messageKey;

    public ChatModel() {
    }

    public ChatModel(String text, String name, String photoUrl) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public ChatModel( String text, String name, String photoUrl, String messageKey) {

        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.messageKey = messageKey;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
