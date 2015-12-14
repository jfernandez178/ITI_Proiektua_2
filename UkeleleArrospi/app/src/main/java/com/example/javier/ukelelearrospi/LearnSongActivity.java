package com.example.javier.ukelelearrospi;



import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class LearnSongActivity extends Fragment implements View.OnClickListener {

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
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_learn_song, container, false);
		
		String intent = getArguments().getString("kantuIzena");

        buttonEguneratu = (Button) v.findViewById(R.id.buttonEguneratu);
        buttonEguneratu.setOnClickListener(this);
        buttonMp3 = (Button) v.findViewById(R.id.buttonMp3);
        buttonMp3.setOnClickListener(this);
        buttonYoutube = (Button) v.findViewById(R.id.buttonYoutube);
        buttonYoutube.setOnClickListener(this);
        favoritos = (ImageButton) v.findViewById(R.id.imageFavritos);
        favoritos.setOnClickListener(this);
        pendiente = (ImageButton) v.findViewById(R.id.imagePendienteak);
        pendiente.setOnClickListener(this);
        ikasiak = (ImageButton) v.findViewById(R.id.imageIkasiak);
        ikasiak.setOnClickListener(this);
        kantuIzenaText = (EditText) v.findViewById(R.id.abestiEditatu);
        autoreaText = (EditText) v.findViewById(R.id.autoreEditatu);
        youtube = (ImageButton) v.findViewById(R.id.imageYoutube);
        youtube.setOnClickListener(this);
        mp3 = (ImageButton) v.findViewById(R.id.imageMp3);
        mp3.setOnClickListener(this);

        abestia = LearnSongLogika.getLearnSongLogika(getActivity().getApplicationContext()).kantarenInfoLortu(intent);

        //Defektuz, balio zaharrak izango ditu kargatuta
        youtubeBerria = abestia.getYoutube();
        mp3Berria = abestia.getMp3();

		return v;
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
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonMp3:

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                alertDialog2.setTitle("Abestia");
                alertDialog2.setMessage("URL berria:");

                final EditText input2 = new EditText(getActivity());
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Tutorial");
                alertDialog.setMessage("URL berria:");

                final EditText input = new EditText(getActivity());
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
                //TODO
                // openMP3(null);
                break;





        }



    }
}
