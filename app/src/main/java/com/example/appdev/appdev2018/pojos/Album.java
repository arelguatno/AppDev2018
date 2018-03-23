package com.example.appdev.appdev2018.pojos;

/**
 * Created by aguatno on 3/11/18.
 */

public class Album {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String songID;

    public Album() {
    }

    public Album(String name, int numOfSongs, int thumbnail, String songID) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.songID = songID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getSongID() {
        return songID;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
