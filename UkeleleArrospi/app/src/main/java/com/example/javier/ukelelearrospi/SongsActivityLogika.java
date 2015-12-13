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
            db.execSQL("DELETE FROM FAVORITOS WHERE username='"+username+"' AND kantaIzena='"+song.getName()+"'");
            song.setFavorito(false);
        }else{
            db.execSQL("INSERT INTO FAVORITOS VALUES('"+username+"','"+song.getName()+"')");
            song.setFavorito(true);
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
                if (isSongValid(kantaGuztiak.get(i), search, null, -1, false)){
                    kantuak.add(kantaGuztiak.get(i));
                }
            }
            return kantuak;
        }
    }

    /**
     *
     * @param song
     * @param title
     * @param author
     * @param zailtasuna
     * @param favorito
     * @return
     */
    private boolean isSongValid(SongInfo song, String title, String author, int zailtasuna, boolean favorito){
        if (title != null && !title.trim().isEmpty()){
            if (!song.getName().contains(title)){
                return false;
            }
        }
        if (author != null && !author.trim().isEmpty()){
            if (!song.getAuthor().contains(author)){
                return false;
            }
        }
        if (zailtasuna >= 0){
            if (zailtasuna != song.getZailtasuna()){
                return false;
            }
        }
        if (favorito && !song.isFavorito()){
            return false;
        }
        return true;
    }

}
