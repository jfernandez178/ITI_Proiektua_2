package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            info.setAuthor(c.getString(4));
            info.setZailtasuna(c.getInt(3));
            Cursor c2 = db.rawQuery("SELECT * FROM FAVORITOS WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
            if (c2.moveToFirst()){
                info.setFavorito(true);
            }else{
                info.setFavorito(false);
            }
            c2 = db.rawQuery("SELECT * FROM PENDIENTEAK WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
            if (c2.moveToFirst()){
                info.setPendiente(true);
            }else{
                info.setPendiente(false);
            }
            c2 = db.rawQuery("SELECT * FROM IKASIAK WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
            if (c2.moveToFirst()){
                info.setIkasia(true);
            }else{
                info.setIkasia(false);
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
     * @param titulo izenean izan behar duen textua. Hutsik edo null bada guztiak bueltatzen dira
     * @return Kantu zerrenda
     */
    public ArrayList<SongInfo> getSongs(String titulo, String autor, int zailtasuna, boolean favorito, boolean pendiente, boolean ikasia){
            ArrayList<SongInfo> kantuak = new ArrayList<>();
            for (int i=0; i<kantaGuztiak.size(); i++){
                if (isSongValid(kantaGuztiak.get(i), titulo, autor, zailtasuna, favorito, pendiente, ikasia)){
                    kantuak.add(kantaGuztiak.get(i));
                }
            }
            return kantuak;

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
    private boolean isSongValid(SongInfo song, String title, String author, int zailtasuna, boolean favorito, boolean pendiente, boolean ikasia){
        if (title != null && !title.isEmpty()){
            if (!song.getName().toLowerCase().contains(title.toLowerCase())){
                return false;
            }
        }
        if (author != null && !author.isEmpty()){
            if (!song.getAuthor().toLowerCase().contains(author.toLowerCase())){
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
        if (pendiente && !song.isPendiente()){
            return false;
        }
        if (ikasia && !song.isIkasia()){
            return false;
        }
        return true;
    }

}
