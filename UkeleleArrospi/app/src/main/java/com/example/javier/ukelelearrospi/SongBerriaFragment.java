package com.example.javier.ukelelearrospi;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;



public class SongBerriaFragment extends AppCompatActivity implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private EditText kantuaIzenaText;
    private EditText autoreaText;
    private EditText youtubeText;
    private EditText mp3Text;
    private Button buttonSortu;
    private Button buttonAkordeakAukeratu;
    private TextView textAukeratutakoAkordeak;

    private String kantuIzena;
    private String autoreIzena;
    private String youtube;
    private String mp3;
    private String akordeak;

    private SongBerrialogika songberrialogika;




    public SongBerriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_song_berria);


        buttonSortu = (Button) findViewById(R.id.buttonKantuaSortu);
        kantuaIzenaText = (EditText) findViewById(R.id.kantuIzena);
        autoreaText = (EditText) findViewById(R.id.autorea);
        youtubeText = (EditText) findViewById(R.id.youtube);
        mp3Text = (EditText) findViewById(R.id.mp3);
        textAukeratutakoAkordeak = (TextView) findViewById(R.id.textAukeratutakoAkordeak);
        buttonAkordeakAukeratu = (Button) findViewById(R.id.buttonAkordeakAukeratu);
        buttonAkordeakAukeratu.setOnClickListener(this);

        buttonSortu.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {

        Log.i("id", view.getId() +"");

        switch(view.getId()){

            case R.id.buttonAkordeakAukeratu:

                //Se crea una lista de acordes y se muestra el dialog

                ArrayList<String> akordeArraylist = songberrialogika.getSongBerriaLogika(getApplicationContext()).lortuAkordeGuztiak();
                Log.i("akordeakArrayList", "luzeera: " + akordeArraylist.size());
                String[] akordeakArray = new String[akordeArraylist.size()];
                akordeakArray = akordeArraylist.toArray(akordeakArray);

                Log.i("akordeak", akordeakArray.toString() + "; luzeera: " + akordeakArray.length);
                //Dialog-a sortzen da
                final CharSequence[] items = akordeakArray;
                // arraylist to keep the selected items
                final ArrayList seletedItems=new ArrayList();

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Aukeratu nahi dituzun Akordeak")
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                //Aukeratutako akordeen label-a eguneratzen da

                                String chords = "";
                                Iterator<Integer> it = seletedItems.iterator();
                                while(it.hasNext()){
                                    chords = chords + items[it.next()] + ";";

                                }
                                textAukeratutakoAkordeak.setText(chords);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Atzera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

                break;

            case R.id.buttonKantuaSortu:

                //Elementu guztien testua lortzen da
                youtube = youtubeText.getText().toString();
                mp3 = mp3Text.getText().toString();
                kantuIzena = kantuaIzenaText.getText().toString();
                autoreIzena = autoreaText.getText().toString();
                akordeak = textAukeratutakoAkordeak.getText().toString();


                //Eremu guztiak badaude beteta, ikusi behar da ea kantua sortu daitekeen ala existitzen den, eta azkena ez bada, sortu egiten da
                if(!youtube.isEmpty() && !mp3.isEmpty() && !kantuIzena.isEmpty() && !autoreIzena.isEmpty() && !akordeak.equalsIgnoreCase("Acordes Seleccionados")){
                    Log.i("txatxan", "you: " + youtube + "mp3: " + mp3 + "kantua: " + kantuIzena + "autorea: " + autoreIzena + "akordeak: " + akordeak);
                    boolean ondoSortuDa = songberrialogika.getSongBerriaLogika(getApplicationContext()).abestiaSortu(kantuIzena, autoreIzena, youtube, mp3, akordeak);

                    //ez bada ondo sortu, abestia existitzen delako da, eta alert bat erakutsiko da
                    //bestela, ondo sortu dela adieraziko da, eta zerrendara bueltatuko da erabiltzailea
                    String mezua = "Kantu hori existitzen da; aukeratu ezazu beste bat";
                    if(ondoSortuDa){
                        mezua = "Kantu berria ondo sortu da!";
                        Toast.makeText(this.getApplicationContext(), mezua,
                                Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else{
                        Toast.makeText(this.getApplicationContext(), mezua,
                                Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(this.getApplicationContext(), "Bete ezazu eremu guztiak!",
                            Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
