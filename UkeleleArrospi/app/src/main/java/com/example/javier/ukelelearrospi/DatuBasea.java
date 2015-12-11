package com.example.javier.ukelelearrospi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        db.execSQL( "CREATE TABLE LOGIN(" +
                "username VARCHAR(120) PRIMARY KEY, " +
                "password VARCHAR(300) NOT NULL);");
        Log.i(this.getClass().toString(), "LOGIN Taula sortuta");

        db.execSQL( "CREATE TABLE INFOKANTA(" +
                " kantaIzena TEXT PRIMARY KEY," +
                " pathIrudia TEXT NOT NULL," +
                " pathKanta TEXT NOT NULL," +
                " pathYoutube TEXT NOT NULL," +
                " zailtasunMaila INTEGER NOT NULL," +
                " egilea TEXT NOT NULL," +
                " iraupena INTEGER NOT NULL);");
        Log.i(this.getClass().toString(), "KANTAINFO Taula sortuta");

        db.execSQL( "CREATE TABLE INFOAKORDE(" +
                " akordeIzena TEXT PRIMARY KEY," +
                " pathIrudia TEXT NOT NULL);");
        Log.i(this.getClass().toString(), "AKORDEINFO Taula sortuta");

        db.execSQL( "CREATE TABLE KANTAAKORDE(" +
                " kantaIzena TEXT PRIMARY KEY," +
                " akordeIzena TEXT PRIMARY KEY," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(akordeIzena) REFERENCES INFOAKORDE(akordeIzena));");
        Log.i(this.getClass().toString(), "KANTAAKORDE Taula sortuta");


        db.execSQL( "CREATE TABLE FAVORITOS(" +
                " username VARCHAR(300) PRIMARY KEY," +
                " kantaIzena TEXT PRIMARY KEY," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username));");
        Log.i(this.getClass().toString(), "FAVORITOS Taula sortuta");


        db.execSQL( "CREATE TABLE PENDIENTEAK(" +
                " username VARCHAR(300) PRIMARY KEY," +
                " kantaIzena TEXT PRIMARY KEY," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username));");

        Log.i(this.getClass().toString(), "PENDIENTEAK Taula sortuta");

        db.execSQL( "CREATE TABLE IKASIAK(" +
                " username VARCHAR(300) PRIMARY KEY," +
                " kantaIzena TEXT PRIMARY KEY," +
                " FOREIGN KEY(kantaIzena) REFERENCES INFOKANTA(kantaIzena)," +
                " FOREIGN KEY(username) REFERENCES USER(username));");

        Log.i(this.getClass().toString(), "IKASIAK Taula sortuta");

        db.execSQL( "CREATE TABLE LOGIN(" +
                " username VARCHAR(300) PRIMARY KEY);");

        Log.i(this.getClass().toString(), "LOGIN Taula sortuta");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
