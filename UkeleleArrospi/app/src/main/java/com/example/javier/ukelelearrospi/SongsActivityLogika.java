package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by ander on 2015/12/12.
 */
public class SongsActivityLogika {

    private SQLiteDatabase db;
    private ArrayList<SongInfo> kantaGuztiak;
    private String username;

    public SongsActivityLogika(Context c){
        DatuBasea datuBasea = new DatuBasea(c);
        db = datuBasea.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOGIN_DONE", null);
        cursor.moveToFirst();
        username = cursor.getString(0);
        loadSongs();
    }

    private void loadSongs(){
        kantaGuztiak = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM INFOKANTA", null);
        while(c.moveToNext()){
            SongInfo info = new SongInfo();
            info.setName(c.getString(0));
            info.setZailtasuna(c.getInt(4));
            Cursor c2 = db.rawQuery("SELECT * FROM FAVORITOS WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
            if (c2.moveToFirst()){
                info.setFavorito(true);
            }else{
                info.setFavorito(false);
            }
            kantaGuztiak.add(info);
        }
    }

    public void changeFavorito(SongInfo song){
        if (song.isFavorito()){
            db.execSQL("INSERT INTO FAVORITOS VALUES('"+username+"','"+song.getName()+"')");
            song.setFavorito(true);
        }else{
            db.execSQL("DELETE FROM FAVORITOS WHERE username='"+username+"' AND kantaIzena='"+song.getName()+"'");
            song.setFavorito(false);
        }

    }

    /**
     * Kantu zerrenda bueltatzen du
     * @param search izenean izan behar duen textua. Hutsik edo null bada guztiak bueltatzen dira
     * @return Kantu zerrenda
     */
    public ArrayList<SongInfo> getSongs(String search){
        if (search == null || search.isEmpty() || search.trim().length() == 0){
            return kantaGuztiak;
        }else{
            ArrayList<SongInfo> kantuak = new ArrayList<>();
            for (int i=0; i<kantaGuztiak.size(); i++){
                if (kantaGuztiak.get(i).getName().toLowerCase().contains(search.toLowerCase())){
                    kantuak.add(kantaGuztiak.get(i));
                }
            }
            return kantuak;
        }
    }

}
