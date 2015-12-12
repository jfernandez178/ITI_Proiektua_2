package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by ander on 2015/12/12.
 */
public class SongsActivityLogika {

    SQLiteDatabase db;

    public SongsActivityLogika(Context c){
        DatuBasea datuBasea = new DatuBasea(c);
        db = datuBasea.getWritableDatabase();
    }

    public ArrayList<SongInfo> getSongs(){
        Object result = db.execSQL("SELECT * FROM INFOKANTA");
    }

}
