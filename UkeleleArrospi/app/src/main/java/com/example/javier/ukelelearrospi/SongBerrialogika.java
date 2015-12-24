package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Javier on 12/12/2015.
 */
public class SongBerrialogika {

    private static SongBerrialogika nireSongBerriaLogika = null;
    private SQLiteDatabase db;
    private static Context context;

    private SongBerrialogika(Context pContext){
        context = pContext;
        DatuBasea dbHelper = new DatuBasea(pContext);
        db = dbHelper.getWritableDatabase();
    }


    public static synchronized SongBerrialogika getSongBerriaLogika(Context pContext){
        if(nireSongBerriaLogika == null){
            nireSongBerriaLogika = new SongBerrialogika(pContext);
        }
        return nireSongBerriaLogika;
    }

    //Akordeen zerrenda lortzeko metodoa
    private String[] lortuAkordeak(String akordeak){
        String[] akordeakLista = akordeak.split(";");
        return akordeakLista;


    }


    //Datu basean erregistratuko du kantua
    //Ondo sortu bada true bueltatuko du, eta existitzen bada lehendik eta ezin bada gehitu, false
    public boolean abestiaSortu(String kantuIzena, String autorea, String youtube, String mp3, String akordeak){

        boolean ondoSortuDa = false;

        //Begiratu behar da ea existitzen den jada kantua
        Cursor c = db.rawQuery("SELECT * from INFOKANTA where kantaIzena='" + kantuIzena + "';", null);

        if(!c.moveToFirst()){
            //Ez baldin bada existitzen kanturik izen horrekin sortu egingo da
            db.execSQL("INSERT INTO INFOKANTA (kantaIzena, egilea, pathKanta, pathYoutube) values('" + kantuIzena + "', '" + autorea + "', '" + mp3 + "', '" + youtube + "')");
            //Gainera, akordeak eta kantuak lotu behar dira
            String[] akordeakLista = lortuAkordeak(akordeak);
            for(int i = 0; i < akordeakLista.length; i++){
                db.execSQL("INSERT INTO KANTAAKORDE values('" + kantuIzena + "', '" + akordeakLista[i] + "');");
                Log.i("query", "INSERT INTO KANTAAKORDE values('" + kantuIzena + "', '" + akordeakLista[i] + "');");

            }
            ondoSortuDa = true;
        }

        return ondoSortuDa;
    }

    public ArrayList<String> lortuAkordeGuztiak(){

        ArrayList<String> akordeakLista = new ArrayList<String>();

        //Datu basetik lortuko dira kantuaren akorde zerrenda
        Cursor c = db.rawQuery("SELECT akordeIzena from INFOAKORDE;", null);

        //Akorderik baldin badauka, akordeak arraylist batetan sartzen dira
        while(c.moveToNext()){
            akordeakLista.add(c.getString(0));

        }


        return akordeakLista;

    }



}
