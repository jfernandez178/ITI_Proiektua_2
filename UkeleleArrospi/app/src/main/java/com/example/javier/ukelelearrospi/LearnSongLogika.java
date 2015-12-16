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

    private LearnSongLogika(Context pContext){
        context = pContext;
        DatuBasea dbHelper = new DatuBasea(pContext);
        db = dbHelper.getWritableDatabase();

    }

    public static synchronized LearnSongLogika getLearnSongLogika(Context pContext){
        if(nireLearn == null){
            nireLearn = new LearnSongLogika(pContext);
        }
        return nireLearn;
    }

    //Abesti baten informazioa eguneratzen duen metodoa
    public boolean cambiarInformación(String cancionNueva, String cancionVieja, String autorNuevo, String mp3Nuevo, String youtubeNuevo){
        boolean ondoJoanDa = true;

        //Eremuren bat ez baldin badago beteta ezin da eguneratu
        if(cancionNueva.isEmpty() || autorNuevo.isEmpty() || mp3Nuevo.isEmpty() || youtubeNuevo.isEmpty()){
            ondoJoanDa = false;

        }
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
        //Ondo badago dena, eguneratu egiten da
        if(ondoJoanDa){
            db.execSQL("UPDATE INFOKANTA SET kantaIzena='" + cancionNueva +"', pathKanta='" +mp3Nuevo +"', pathYoutube='" +youtubeNuevo +"', egilea='" + autorNuevo +"' WHERE kantaIzena='" + cancionVieja +"';");
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
            kanta.setMp3(c.getString(2));
            kanta.setYoutube(c.getString(3));
            kanta.setAuthor(c.getString(5));
        }


        return kanta;
    }
}
