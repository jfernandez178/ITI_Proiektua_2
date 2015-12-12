package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
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
        ArrayList<SongInfo> songInfos = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM INFOKANTA", null);
        while(c.moveToNext()){
            SongInfo info = new SongInfo();
            info.setName(c.getString(0));
            songInfos.add(info);
        }
        return songInfos;
    }

}
