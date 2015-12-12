package com.example.javier.ukelelearrospi;

import java.io.Serializable;

public class SongInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int index;
    private String songName;
    private String songMp3;
    private String songYoutube;
    private int zailtasuna;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public int getZailtasuna() {
        return zailtasuna;
    }

    public void setZailtasuna(int zailtasuna) {
        this.zailtasuna = zailtasuna;
    }
}