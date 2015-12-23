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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LearnSongActivity extends Fragment implements View.OnClickListener {


	int i=0;

    private Button buttonEguneratu;
    private Button buttonYoutube;
    private Button buttonMp3;
    private ImageView favoritos;
    private ImageView pendiente;
    private ImageView ikasiak;
    private TextView kantuIzenaText;
    private EditText autoreaText;
    private WebView youtube;
    private WebView mp3;

    private String youtubeBerria;
    private SongInfo abestia;
    private String mp3Berria;



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
        favoritos = (ImageView) v.findViewById(R.id.imageFavritos);
        favoritos.setOnClickListener(this);
        pendiente = (ImageView) v.findViewById(R.id.imagePendienteak);
        pendiente.setOnClickListener(this);
        ikasiak = (ImageView) v.findViewById(R.id.imageIkasiak);
        ikasiak.setOnClickListener(this);
        kantuIzenaText = (TextView) v.findViewById(R.id.abestiEditatu);
        autoreaText = (EditText) v.findViewById(R.id.autoreEditatu);
        youtube = (WebView) v.findViewById(R.id.imageYoutube);
        mp3 = (WebView) v.findViewById(R.id.imageMp3);

        abestia = LearnSongLogika.getLearnSongLogika(getActivity().getApplicationContext()).kantarenInfoLortu(intent);

        //Defektuz, balio zaharrak izango ditu kargatuta
        youtubeBerria = abestia.getYoutube();
        mp3Berria = abestia.getMp3();

        kantuIzenaText.setText(abestia.getName());
        autoreaText.setText(abestia.getAuthor());
        openYOUTUBE();
        openMp3();

        if (abestia.isFavorito()){
            favoritos.setImageResource(R.drawable.ic_star_24dp);
        }else{
            favoritos.setImageResource(R.drawable.ic_star_outline_24dp);
        }

        if (abestia.isPendiente()){
            pendiente.setImageResource(R.drawable.ic_query_builder_select);
        }else{
            pendiente.setImageResource(R.drawable.ic_query_builder_24dp);
        }

        if (abestia.isIkasia()){
            ikasiak.setImageResource(R.drawable.ic_done_all_aukeratuta);
        }else{
            ikasiak.setImageResource(R.drawable.ic_done_all_24dp);
        }

		return v;
	}




    public void openYOUTUBE() {
        baseOpenYoutube(youtube, youtubeBerria);
    }

    public void openMp3(){
        baseOpenYoutube(mp3, mp3Berria);
    }

    private void baseOpenYoutube(WebView webView, String youtubeBerria){
        String BASE_URL_YOUTUBE = "https://www.youtube.com/embed/";
        String loadUrl;
        if (youtubeBerria != null && !youtubeBerria.isEmpty() && youtubeBerria.contains("=")){
            loadUrl = BASE_URL_YOUTUBE + youtubeBerria.split("=")[1];
            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(loadUrl);
        }
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
                                openMp3();
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
                                openYOUTUBE();
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
                LearnSongLogika.getLearnSongLogika(getActivity()).cambiarInformación(kantuIzenaText.getText().toString(), autoreaText.getText().toString(), mp3Berria, youtubeBerria);
                break;

            case R.id.imageFavritos:
                LearnSongLogika.getLearnSongLogika(getActivity()).changeFavorito(abestia);
                if (abestia.isFavorito()){
                    favoritos.setImageResource(R.drawable.ic_star_24dp);
                }else{
                    favoritos.setImageResource(R.drawable.ic_star_outline_24dp);
                }
                break;


            case R.id.imagePendienteak:
                LearnSongLogika.getLearnSongLogika(getActivity()).changePendiente(abestia);
                if (abestia.isPendiente()){
                    pendiente.setImageResource(R.drawable.ic_query_builder_select);
                }else{
                    pendiente.setImageResource(R.drawable.ic_query_builder_24dp);
                }
                break;


            case R.id.imageIkasiak:
                LearnSongLogika.getLearnSongLogika(getActivity()).changeIkasia(abestia);
                if (abestia.isIkasia()){
                    ikasiak.setImageResource(R.drawable.ic_done_all_aukeratuta);
                }else{
                    ikasiak.setImageResource(R.drawable.ic_done_all_24dp);
                }
                break;

        }



    }
}
