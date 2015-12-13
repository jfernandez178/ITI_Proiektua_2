package com.example.javier.ukelelearrospi;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SongBerriaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SongBerriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongBerriaFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongBerriaFragment.
     */
    public static SongBerriaFragment newInstance(String param1, String param2) {
        SongBerriaFragment fragment = new SongBerriaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SongBerriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_song_berria, container, false);


        buttonSortu = (Button) v.findViewById(R.id.buttonKantuaSortu);
        kantuaIzenaText = (EditText) v.findViewById(R.id.kantuIzena);
        autoreaText = (EditText) v.findViewById(R.id.autorea);
        youtubeText = (EditText) v.findViewById(R.id.youtube);
        mp3Text = (EditText) v.findViewById(R.id.mp3);
        textAukeratutakoAkordeak = (TextView) v.findViewById(R.id.textAukeratutakoAkordeak);
        buttonAkordeakAukeratu = (Button) v.findViewById(R.id.buttonAkordeakAukeratu);
        buttonAkordeakAukeratu.setOnClickListener(this);

        buttonSortu.setOnClickListener(this);


        return inflater.inflate(R.layout.fragment_song_berria, container, false);
    }




    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.buttonAkordeakAukeratu:

                //Se crea una lista de acordes y se muestra el dialog

                ArrayList<String> akordeArraylist = songberrialogika.getSongBerriaLogika(getActivity().getApplicationContext()).lortuAkordeGuztiak();
                String[] akordeakArray = new String[akordeArraylist.size()];
                akordeakArray = akordeArraylist.toArray(akordeakArray);


                //Dialog-a sortzen da
                final CharSequence[] items = akordeakArray;
                // arraylist to keep the selected items
                final ArrayList seletedItems=new ArrayList();

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
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
                                Iterator<String> it = seletedItems.iterator();
                                while(it.hasNext()){
                                    chords = chords + it.next() + ";";

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
                if(!youtube.isEmpty() && !mp3.isEmpty() && !kantuIzena.isEmpty() && !autoreIzena.isEmpty() && !akordeak.isEmpty()){
                    boolean ondoSortuDa = songberrialogika.getSongBerriaLogika(getActivity().getApplicationContext()).abestiaSortu(kantuIzena, autoreIzena, youtube, mp3, akordeak);

                    //ez bada ondo sortu, abestia existitzen delako da, eta alert bat erakutsiko da
                    //bestela, ondo sortu dela adieraziko da, eta zerrendara bueltatuko da erabiltzailea
                    String mezua = "Kantu hori existitzen da; aukeratu ezazu beste bat";
                    if(ondoSortuDa){
                        mezua = "Kantu berria ondo sortu da!";
                        Toast.makeText(getActivity().getApplicationContext(), mezua,
                                Toast.LENGTH_SHORT).show();
                        //TODO: FRAGMENT HAU ITXI ETA SONGSACTIVITY() KARGATZEN DA

                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), mezua,
                                Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "kaka",
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
