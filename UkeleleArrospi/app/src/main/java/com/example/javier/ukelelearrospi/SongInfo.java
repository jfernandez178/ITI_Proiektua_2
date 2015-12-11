package com.example.javier.ukelelearrospi;

import java.io.Serializable;

public class SongInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    int index;
    String songName;
    String songMp3;
    String songYoutube;
    
    public void setIndex(int ind) {
        index= ind;    
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setName(String name) {
        songName= name;    
    }
    
    public String getName() {
        return songName;
    }
    
    public void setMp3(String songMp) {
        songMp3= songMp;    
    }
    
    public String getMp3() {
        return songMp3;
    }
   public void setYoutube(String songY) {
        songYoutube= songY;    
    }
    
    public String getYoutube() {
        return songYoutube;
    }
}