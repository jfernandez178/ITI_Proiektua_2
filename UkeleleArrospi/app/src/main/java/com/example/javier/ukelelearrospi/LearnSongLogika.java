package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Javier on 14/12/2015.
 */
public class LearnSongLogika {

    private static LearnSongLogika nireLearn;
    private SQLiteDatabase db;
    private static Context context;

    private String username;

    private LearnSongLogika(Context pContext){
        context = pContext;
        DatuBasea dbHelper = new DatuBasea(pContext);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOGIN_DONE", null);
        cursor.moveToFirst();
        username = cursor.getString(0);

    }

    public static synchronized LearnSongLogika getLearnSongLogika(Context pContext){
        if(nireLearn == null){
            nireLearn = new LearnSongLogika(pContext);
        }
        return nireLearn;
    }

    //Abesti baten informazioa eguneratzen duen metodoa
    public boolean cambiarInformación(String cancionVieja, String autorNuevo, String mp3Nuevo, String youtubeNuevo){
        boolean ondoJoanDa = true;

        //Eremuren bat ez baldin badago beteta ezin da eguneratu
        if(cancionVieja.isEmpty() || autorNuevo.isEmpty() || mp3Nuevo.isEmpty() || youtubeNuevo.isEmpty()){
            ondoJoanDa = false;

        }
        /*
        //Bestela, konprobatu behar da ea aldatu daitekeen tituluaren arabera
        else{
            //Titulu zaharra eta berria ez baldin badira berdinak
            if(!cancionNueva.equalsIgnoreCase(cancionVieja)){
                Cursor c = db.rawQuery("SELECT kantaIzena FROM INFOKANTA WHERE kantaIzena='" + cancionNueva + "';", null);
                if(c.moveToFirst()){
                    ondoJoanDa = false;
                }
            }

        }
        */
        //Ondo badago dena, eguneratu egiten da
        if(ondoJoanDa){
            db.execSQL("UPDATE INFOKANTA SET  pathKanta='" +mp3Nuevo +"', pathYoutube='" +youtubeNuevo +"', egilea='" + autorNuevo +"' WHERE kantaIzena='" + cancionVieja +"';");
        }

        return ondoJoanDa;

    }


    //Kantaren informazioa lortzeko metodoa
    public SongInfo kantarenInfoLortu(String kantaIzena){
        SongInfo kanta = new SongInfo();

        //Datu basetik lortzen da informazioa kantarena
        Cursor c = db.rawQuery("SELECT * FROM INFOKANTA WHERE kantaIzena='" + kantaIzena + "';", null);

        if(c.moveToFirst()){
            kanta.setName(kantaIzena);
            kanta.setMp3(c.getString(1));
            kanta.setYoutube(c.getString(2));
            kanta.setAuthor(c.getString(4));
        }

        Cursor c2 = db.rawQuery("SELECT * FROM FAVORITOS WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
        if (c2.moveToFirst()){
            kanta.setFavorito(true);
        }else{
            kanta.setFavorito(false);
        }
        c2 = db.rawQuery("SELECT * FROM PENDIENTEAK WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
        if (c2.moveToFirst()){
            kanta.setPendiente(true);
        }else{
            kanta.setPendiente(false);
        }
        c2 = db.rawQuery("SELECT * FROM IKASIAK WHERE username='"+username+"' AND kantaIzena='"+c.getString(0)+"'" , null);
        if (c2.moveToFirst()){
            kanta.setIkasia(true);
        }else{
            kanta.setIkasia(false);
        }


        return kanta;
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

    public void changePendiente(SongInfo song){
        if (song.isPendiente()){
            db.execSQL("DELETE FROM PENDIENTEAK WHERE username='"+username+"' AND kantaIzena='"+song.getName()+"'");
            song.setPendiente(false);
        }else{
            db.execSQL("INSERT INTO PENDIENTEAK VALUES('"+username+"','"+song.getName()+"')");
            song.setPendiente(true);
        }
    }

    public void changeIkasia(SongInfo song){
        if (song.isIkasia()){
            db.execSQL("DELETE FROM IKASIAK WHERE username='"+username+"' AND kantaIzena='"+song.getName()+"'");
            song.setIkasia(false);
        }else{
            db.execSQL("INSERT INTO IKASIAK VALUES('"+username+"','"+song.getName()+"')");
            song.setIkasia(true);
        }
    }
}
