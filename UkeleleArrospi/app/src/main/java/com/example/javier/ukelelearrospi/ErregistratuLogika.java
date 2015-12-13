package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by Javier on 13/12/2015.
 */
public class ErregistratuLogika {

    private static ErregistratuLogika nireErregistratuLogika = null;
    private SQLiteDatabase db;
    private static Context context;

    private ErregistratuLogika(Context pContext){
        //datu-base kudeatzailea instantziatzen da
        context = pContext;
        DatuBasea dbHelper = new DatuBasea(context);
        Log.i("context", context + "");
        Log.i("dbHelper", "" + dbHelper);
        db = dbHelper.getWritableDatabase();
    }

    public static synchronized ErregistratuLogika getErregistratuLogika(Context pContext){
        if(nireErregistratuLogika == null){
            nireErregistratuLogika = new ErregistratuLogika(pContext);
        }
        return nireErregistratuLogika;
    }

    //Metodo honek erabiltzailea ondo erregistratu den edo username horrekin erabiltzaile bat existitzen den adierazten du
    public boolean ondoErregistratuDa(String username, String password){
        boolean ondoJoanDa = true;

        //Erabiltzailea existitzen den edo ez konprobatzen du
        Cursor c = db.rawQuery("SELECT * from LOGIN WHERE username='" + username + "';", null);
        if(c.moveToFirst()){
            ondoJoanDa = false;
        }
        //Bestela gehitu egingo da
        else{

            //Password-aren Hash-a lortzen da
            try {
                String passwordHash = md5(password);
                db.execSQL("INSERT INTO LOGIN (username, password) values ('" + username + "', '" + passwordHash + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ondoJoanDa;

    }

    //funciones que convierten a md5 un string
    public static String md5(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        return bytesToHex(md.digest());
    }

    //Método de ayuda para hash-ear la contraseña
    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

}
