package com.jazter.myappportfolio.spotify.utils;

/**
 * Created by Jazter on 18/06/2015.
 */
public class ArtistItem {
    private String id;
    private String name;
    private String imgURL;

    public ArtistItem(String id, String name, String image){
        this.id = id;
        this.name = name;
        this.imgURL = image;
    }

    public String getArtistId(){
        return id;
    }

    public String getArtistName(){
        return name;
    }

    public String getArtistImageURL(){
        return imgURL;
    }
}
