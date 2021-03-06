package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Javier on 10/03/2015.
 */
public class DatuBasea extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "Ukelele database" ;
    private static SQLiteDatabase.CursorFactory factory = null;


    public DatuBasea(Context context) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Datu basea sortzen");

        db.execSQL("CREATE TABLE LOGIN(" +
                "username VARCHAR(120) PRIMARY KEY, " +
                "password VARCHAR(300) NOT NULL);");
        Log.i(this.getClass().toString(), "LOGIN Taula sortuta");

        //TODO Gero kendu hau
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update("orcodelmal".getBytes());
            String pass =  bytesToHex(md.digest());
            db.execSQL("INSERT INTO LOGIN VALUES('arrospi','"+pass+"')");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //TODO KENDU GOIKOA


        db.execSQL( "CREATE TABLE INFOKANTA(" +
                " kantaIzena TEXT PRIMARY KEY," +
                " pathKanta TEXT NOT NULL," +
                " pathYoutube TEXT NOT NULL," +
                " zailtasunMaila INTEGER," +
                " egilea TEXT NOT NULL," +
                " iraupena INTEGER);");
        Log.i(this.getClass().toString(), "KANTAINFO Taula sortuta");

        db.execSQL( "CREATE TABLE INFOAKORDE(" +
                " akordeIzena TEXT PRIMARY KEY);");
        Log.i(this.getClass().toString(), "AKORDEINFO Taula sortuta");


        db.execSQL( "CREATE TABLE KANTAAKORDE(" +
                " kantaIzena TEXT NOT NULL," +
                " akordeIzena TEXT NOT NULL," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(akordeIzena) REFERENCES INFOAKORDE(akordeIzena)," +
                " PRIMARY KEY(kantaIzena, akordeIzena));");
        Log.i(this.getClass().toString(), "KANTAAKORDE Taula sortuta");


        db.execSQL( "CREATE TABLE FAVORITOS(" +
                " username VARCHAR(300) NOT NULL," +
                " kantaIzena TEXT NOT NULL," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username)," +
                " PRIMARY KEY(username, kantaIzena));");
        Log.i(this.getClass().toString(), "FAVORITOS Taula sortuta");


        db.execSQL( "CREATE TABLE PENDIENTEAK(" +
                " username VARCHAR(300) NOT NULL," +
                " kantaIzena TEXT NOT NULL," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username)," +
                " PRIMARY KEY(username, kantaIzena));");

        Log.i(this.getClass().toString(), "PENDIENTEAK Taula sortuta");

        db.execSQL( "CREATE TABLE IKASIAK(" +
                " username VARCHAR(300) NOT NULL," +
                " kantaIzena TEXT NOT NULL," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username)," +
                " PRIMARY KEY(kantaIzena, username));");

        Log.i(this.getClass().toString(), "IKASIAK Taula sortuta");

        db.execSQL( "CREATE TABLE LOGIN_DONE(" +
                " username VARCHAR(300) PRIMARY KEY);");

        Log.i(this.getClass().toString(), "LOGIN_DONE Taula sortuta");

        datuBaseanKantuakSartu(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    String[] songs = {
            "Livin la Vida Loca",
            "Holding out for a hero",
            "Accidentally In Love",
            "I am a beleiver",
            "Hallelujah",
            "I shot the sheriff",
            "I am yours",
            "Let it be",
            "Somewhere over the rainbow",
            "Sweet home Alabama",
    };

    String[] songsYoutube = {
            "https://www.youtube.com/watch?v=dSeJiVeG360",
            "https://www.youtube.com/watch?v=p3kuqTagIXo",
            "https://www.youtube.com/watch?v=RS8b2BFPLoM",
            "https://www.youtube.com/watch?v=Un7PDHXBovk",
            "http://www.youtube.com/watch?v=XzOdXhywIbo",
            "http://www.youtube.com/watch?v=IzTgYrnhYhs",
            "http://www.youtube.com/watch?v=1VyPSzbe_x0",
            "http://www.youtube.com/watch?v=LoVvrLPX4eQ",
            "http://www.youtube.com/watch?v=1PiscVZSuEE",
            "http://www.youtube.com/watch?v=LDCzpvvSvjY",
                     };

    String[] songsMP3 = {
            "https://www.youtube.com/watch?v=SXUG9zTpI_w",
            "https://www.youtube.com/watch?v=puTtnqoqHLQ",
            "https://www.youtube.com/watch?v=-04HvEXXlfo",
            "https://www.youtube.com/watch?v=SK919-tsPCw",
            "https://www.youtube.com/watch?v=v4tYJdDj3_8",
            "https://www.youtube.com/watch?v=10qLYy6hiFQ",
            "https://www.youtube.com/watch?v=Qr6Ty3C3aMs", 
            "https://www.youtube.com/watch?v=ybb6HPDENnE",
            "https://www.youtube.com/watch?v=V1bFr2SWP1I",
            "https://www.youtube.com/watch?v=li1Rqo2UC88",
    };

    String[] authors = {
            "Ricky Martin",
            "Bonnie Tyler",
            "Counting Crows",
            "The Monkees",
            "Leonard Cohen",
            "Eric Clapton",
            "Jason Mraz",
            "The Beatles",
            "Israel Kamakawiwo",
            "Lynyrd Skynyrd",
    };
    String akordeak[] = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Dom", "Rem", "Mim", "Fam", "Solm", "Lam", "Sim"};



    private void datuBaseanKantuakSartu(SQLiteDatabase db){
        for (int i=0; i < songs.length; i++){
            db.execSQL("INSERT INTO INFOKANTA VALUES ('"+songs[i]+"', '"+songsMP3[i]+"', '"+songsYoutube[i]+"', "+(i % 4)+", '"+authors[i]+"', 138)");
        }

        for(int i=0; i<akordeak.length; i++){
            db.execSQL("INSERT INTO INFOAKORDE (akordeIzena) values ('" + akordeak[i] + "')");
        }
    }

    //TODO KEndu hau gero
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
