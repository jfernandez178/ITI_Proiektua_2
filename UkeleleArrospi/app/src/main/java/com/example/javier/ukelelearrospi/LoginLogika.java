package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by Javier on 11/12/2015.
 */
public class LoginLogika {

    private static LoginLogika nireLoginLogika = null;
    private SQLiteDatabase db;
    private static Context context;

    public LoginLogika(Context pContext){
        //datu-base kudeatzailea instantziatzen da
        context = pContext;
        DatuBasea dbHelper = new DatuBasea(pContext);
        db = dbHelper.getWritableDatabase();
    }

    /*
    public static synchronized LoginLogika getNireLoginLogika(){
        if(nireLoginLogika == null){
            nireLoginLogika = new LoginLogika(context);
        }
        return nireLoginLogika;
    }
    */

    //Erabiltzaileren bat baldin badago sesioa irekita jakiteko
    public boolean sesioaIrekita(){
        boolean irekita = false;

        //Erabiltzailerik logeatuta dagoen edo ez ikusten da, menu nagusia bistaratzeko edo login pantaila bistaratzeko
        Cursor c = db.rawQuery("SELECT * from LOGIN_DONE", null);

        //Elementurik baldin badauka, logeatuta dago erabiltzailea lehendik
        if(c.moveToFirst()){
            irekita = true;
        }

        return irekita;
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

    public boolean loginZuzena(String username, String password){

        boolean zuzena = false;

        //Pasahitzaren hash-a lortzeko
        String passwordHash = null;
        try {
            passwordHash = md5(password);


            //Erabiltzailerik logeatuta dagoen edo ez ikusten da, menu nagusia bistaratzeko edo login pantaila bistaratzeko
            Cursor c = db.rawQuery("SELECT username from LOGIN where username='" + username + "' and password='" + passwordHash + "';", null);

            //Elementurik baldin badauka, logeatuta dago erabiltzailea lehendik
            if(c.moveToFirst()){
                 zuzena = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zuzena;
    }

    //Erabiltzailea logeatuta dagoela adierazteko metodoa
    public void loguearUsuario(String username){
        Log.d("PRoba", username);
        db.execSQL("INSERT INTO LOGIN_DONE values('" + username + "');");
        Cursor cursor = db.rawQuery("SELECT * FROM LOGIN_DONE", null);
        cursor.moveToFirst();
        Log.d("Proba 2 ", cursor.getString(0));
    }

}
