package com.example.appdev.appdev2018.pojos;

/**
 * Created by aguatno on 3/23/18.
 */

public class Song {
    private String songID;
    private String songTitle;

    public Song() {
    }

    public Song(String songID, String songTitle) {
        this.songID = songID;
        this.songTitle = songTitle;
    }

    public String getSongID() {
        return songID;
    }

    public String getSongTitle() {
        return songTitle;
    }
}
