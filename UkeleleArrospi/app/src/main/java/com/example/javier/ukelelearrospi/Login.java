package com.example.javier.ukelelearrospi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Login extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private EditText usernametext;
    private EditText passwordtext;
    private Button botonlogin;
    private Button botonRegistrar;

    private LoginLogika nireLoginLogika;




    private TextToSpeech myTTS;
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // ...
        }
        else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void berbaEgin(String speech){
        // Get API version:
        int currentapiVersion = Build.VERSION.SDK_INT;
        // API 21 edo altuago
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
            myTTS = new TextToSpeech(this, this); // Como tercer parámetro, se puede pasar el nombre del paquete del sintetizador que queramos usar.

        }
        else {
            // Hay que instalar un motor de TTS
            Intent installTTSIntent = new Intent();
            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installTTSIntent);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, 0);

        botonlogin = (Button) findViewById(R.id.botonlogin);
        usernametext = (EditText) findViewById(R.id.usernametxt);
        passwordtext = (EditText) findViewById(R.id.passwordtext);

        botonlogin = (Button) findViewById(R.id.botonlogin);
        botonlogin.setOnClickListener(this);

        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        botonRegistrar.setOnClickListener(this);

        //Loginaren logikare klasea instantziatzen da
        nireLoginLogika = new LoginLogika(this);

        //Erabiltzaileren batek sesioa irekita duen edo ez konprobatzen da
        boolean sesioaIrekita = nireLoginLogika.sesioaIrekita();

        //sesioa irekita baldin badauka, menu nagusiaren pantaila irekitzen da
        if(sesioaIrekita){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }





}

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.botonlogin:

                //Konprobatzen da ea logina zuzena izan den
                if(nireLoginLogika.loginZuzena(usernametext.getText().toString(), passwordtext.getText().toString())){
                    //Login-a zuzena bada, datu-basean sartzen da logeatuta dagoenaren informazioa
                    nireLoginLogika.loguearUsuario(usernametext.getText().toString());

                    //Menu nagusiaren aktibitatea zabaltzen da
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Erabiltzaile-izena edo pasahitza ez dira zuzenak",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.botonRegistrar:
                //Erregistratzeko pantaila irekiko da.
                //finish();
                Intent i = new Intent(getBaseContext(), ActivityErregistratu.class);
                startActivity(i);
                break;
        }

    }
}
