package com.example.appdev.appdev2018.pojos;

import android.net.Uri;

import java.util.Date;

/**
 * Created by aguatno on 3/23/18.
 */

public class User {
    private String displayName;
    private String email;
    private String photoUrl;
    private String logProvider;
    private String uid;
    private Date joinDate;
    private Date lastLogin;


    public User(String displayName, String email, String photoUrl, String logProvider, String uid, Date joinDate, Date lastLogin) {
        this.displayName = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.logProvider = logProvider;
        this.uid = uid;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }

    public User(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getLogProvider() {
        return logProvider;
    }

    public String getUid() {
        return uid;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }
}
