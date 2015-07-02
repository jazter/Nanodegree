package com.jazter.myappportfolio.spotify.utils;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jazter on 18/06/2015.
 */
public class TrackItem implements Serializable {
    private String id;
    private String trackName;
    private String albumName;
    private String imgURL;

    public TrackItem(String id, String track, String album, String image){
        this.id = id;
        this.trackName = track;
        this.albumName = album;
        this.imgURL = image;
    }

    public String getTrackId(){
        return id;
    }

    public String getTrackName(){
        return trackName;
    }

    public String getAlbumName(){
        return albumName;
    }

    public String getAlbumImageURL(){
        return imgURL;
    }
}
