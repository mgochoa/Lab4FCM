package co.edu.udea.compuvoli.gr03.lab4fcm.POJO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Lina on 19/10/2016.
 */
@IgnoreExtraProperties
public class user {
    public String uid;
    public String username;
    public String email;
    public String photoUrl;
    public boolean  state;

    public user() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public user(String uid, String username, String email, String photoUrl, boolean state) {
        this.uid=uid;
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
        this.state=state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
