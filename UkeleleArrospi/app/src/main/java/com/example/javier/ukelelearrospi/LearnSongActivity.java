package com.example.javier.ukelelearrospi;



import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class LearnSongActivity extends Activity implements View.OnClickListener {

	private String songName;
	private String songMP3;
	private String songYOUTUBE;
	int indexSong;
	int i=0;

    private Button buttonEguneratu;
    private Button buttonYoutube;
    private Button buttonMp3;
    private ImageButton favoritos;
    private ImageButton pendiente;
    private ImageButton ikasiak;
    private EditText kantuIzenaText;
    private EditText autoreaText;
    private ImageButton youtube;
    private ImageButton mp3;

    private String youtubeBerria;
    private SongInfo abestia;
    private String mp3Berria;

	
	ImageView image;
	
	MediaPlayer playSong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_song);
		
		String intent = getIntent().getStringExtra("kantuIzena");

        buttonEguneratu = (Button) findViewById(R.id.buttonEguneratu);
        buttonEguneratu.setOnClickListener(this);
        buttonMp3 = (Button) findViewById(R.id.buttonMp3);
        buttonMp3.setOnClickListener(this);
        buttonYoutube = (Button) findViewById(R.id.buttonYoutube);
        buttonYoutube.setOnClickListener(this);
        favoritos = (ImageButton) findViewById(R.id.imageFavritos);
        favoritos.setOnClickListener(this);
        pendiente = (ImageButton) findViewById(R.id.imagePendienteak);
        pendiente.setOnClickListener(this);
        ikasiak = (ImageButton) findViewById(R.id.imageIkasiak);
        ikasiak.setOnClickListener(this);
        kantuIzenaText = (EditText) findViewById(R.id.abestiEditatu);
        autoreaText = (EditText) findViewById(R.id.autoreEditatu);
        youtube = (ImageButton) findViewById(R.id.imageYoutube);
        youtube.setOnClickListener(this);
        mp3 = (ImageButton) findViewById(R.id.imageMp3);
        mp3.setOnClickListener(this);

        abestia = LearnSongLogika.getLearnSongLogika(getApplicationContext()).kantarenInfoLortu(intent);

        //Defektuz, balio zaharrak izango ditu kargatuta
        youtubeBerria = abestia.getYoutube();
        mp3Berria = abestia.getMp3();


                
		
	}
	
public void openMP3(View v) {
	
	switch (indexSong) {
    case 0:  playSong = MediaPlayer.create(this,
			R.raw.hallelujah_txiki); break;
    case 1:  playSong = MediaPlayer.create(this,
			R.raw.i_shot_the_sheriff_txiki); break;
    case 2:  playSong = MediaPlayer.create(this,
			R.raw.i_am_yours_txiki); break;
    case 3:  playSong = MediaPlayer.create(this,
			R.raw.let_it_be_txiki); break;
    case 4:  playSong = MediaPlayer.create(this,
			R.raw.somewhere_txiki); break;
    case 5:  playSong = MediaPlayer.create(this,
			R.raw.sweet_home_txiki); break;   
}		
		playSong.start();
		i=1;
				
	}

public void openYOUTUBE(View v) {
	
	String url = songYOUTUBE;
	
	if (i==1){
		i=0;
		playSong.stop();
		}
	
	Intent i = new Intent(Intent.ACTION_VIEW);
	i.setData(Uri.parse(url));
	startActivity(i);
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.learn_song, menu);
		return true;
	}

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonMp3:

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
                alertDialog2.setTitle("Abestia");
                alertDialog2.setMessage("URL berria:");

                final EditText input2 = new EditText(this);
                input2.setText(abestia.getYoutube());
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input2.setLayoutParams(lp2);
                alertDialog2.setView(input2);


                alertDialog2.setPositiveButton("EGUNERATU",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mp3Berria = input2.getText().toString();
                                dialog.cancel();
                            }
                        });

                alertDialog2.setNegativeButton("ATZERA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog2.show();

                break;

            case R.id.buttonYoutube:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Tutorial");
                alertDialog.setMessage("URL berria:");

                final EditText input = new EditText(this);
                input.setText(abestia.getYoutube());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);


                alertDialog.setPositiveButton("EGUNERATU",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                youtubeBerria = input.getText().toString();
                                dialog.cancel();
                            }
                        });

                alertDialog.setNegativeButton("ATZERA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();



                break;

            case R.id.buttonEguneratu:
                //TODO: Informazioa eguneratuko da: ANDER

                break;


            case R.id.imageFavritos:
                //TODO:ANDER
                break;


            case R.id.imagePendienteak:
                //TODO:ANDER
                break;


            case R.id.imageIkasiak:
                //TODO:ANDER
                break;

            case R.id.imageYoutube:
                openYOUTUBE(null);
                break;

            case R.id.imageMp3:
                openMP3(null);
                break;





        }



    }
}
