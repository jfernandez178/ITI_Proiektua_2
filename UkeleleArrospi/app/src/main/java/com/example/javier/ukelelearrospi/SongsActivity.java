package com.example.javier.ukelelearrospi;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;

public class SongsActivity extends Fragment implements TextWatcher, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private SongsActivityLogika logika;
    private SongsAdapter adapter;

    private EditText searchEditText;
    private EditText autor;
    private View advancedView;
    private ImageView zabalduItxiAdvanced;

    private Spinner zailtasuna;
    private CheckBox favorito;
    private CheckBox pendiente;
    private CheckBox ikasia;
    private SpeechRecognizer mikrofonoa;
    private Intent mikrofIntent;
    private ArrayList<String> mezua;
    private boolean mikroTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs, container, false);

        logika = new SongsActivityLogika(getActivity());
        advancedView = v.findViewById(R.id.advanced_search);
        zabalduItxiAdvanced = (ImageView) v.findViewById(R.id.zabalduItxiAdvanced);


        zailtasuna = (Spinner) v.findViewById(R.id.spinnerZailtasuna);
        favorito = (CheckBox) v.findViewById(R.id.checkBoxFavorito);
        pendiente = (CheckBox) v.findViewById(R.id.checkBoxPendiente);
        ikasia = (CheckBox) v.findViewById(R.id.checkBoxIkasia);

        zailtasuna.setOnItemSelectedListener(this);

        favorito.setOnCheckedChangeListener(this);
        pendiente.setOnCheckedChangeListener(this);
        ikasia.setOnCheckedChangeListener(this);

        /*v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new SongBerriaFragment())
                        .addToBackStack("lista_songs")
                        .commit();
            }
        });
*/
        v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("context", "" + getActivity().getBaseContext());
                Intent i = new Intent(getActivity().getBaseContext(), SongBerriaFragment.class);
                startActivity(i);
            }
        });


        // Zerrenda
        RecyclerView recyclerView = ((RecyclerView) v.findViewById(R.id.recycler_view));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SongsAdapter((AppCompatActivity) getActivity(), logika);
        recyclerView.setAdapter(adapter);

        // Bilaketa
        searchEditText = (EditText) v.findViewById(R.id.songs_search_edit_text);
        searchEditText.addTextChangedListener(this);
        autor = (EditText) v.findViewById(R.id.autor_edit_text);
        autor.addTextChangedListener(this);

        //mikrofonoa
        mikrofonoa=SpeechRecognizer.createSpeechRecognizer(getActivity());
        mikrofIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mikrofIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mikrofIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName());
        final SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mikrofonoa.setRecognitionListener(listener);

        v.findViewById(R.id.songs_search_edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    mikrofonoa.startListening(mikrofIntent);
                    mikroTitle = true;
                }
            }
        });

        v.findViewById(R.id.autor_edit_text).setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                {
                    mikrofonoa.startListening(mikrofIntent);
                    mikroTitle = false;
                }
            }
        });



        v.findViewById(R.id.zabalduItxiAdvanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erakutsiEdoEzkutatuAdvanced();
            }
        });

        erakutsiEdoEzkutatuAdvanced();

        return v;
    }

    private void erakutsiEdoEzkutatuAdvanced(){
        if (advancedView.getVisibility() == View.GONE){
            advancedView.setVisibility(View.VISIBLE);
            zabalduItxiAdvanced.setImageResource(R.drawable.ic_unfold_less_24dp);
        }else{
            advancedView.setVisibility(View.GONE);
            zabalduItxiAdvanced.setImageResource(R.drawable.ic_unfold_more_24dp);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        bilaketa();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void bilaketa(){
        String titulo = searchEditText.getText().toString();
        String autorText = autor.getText().toString();
        adapter.setSongs(logika.getSongs(titulo, autorText, zailtasuna.getSelectedItemPosition() - 1, favorito.isChecked(), pendiente.isChecked(), ikasia.isChecked()));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        bilaketa();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        bilaketa();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        bilaketa();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //recognition klasea
    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            //Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            //Log.d(TAG, "onEndOfSpeech");
            mikrofonoa.stopListening();
        }

        @Override
        public void onError(int error)
        {
            mikrofonoa.startListening(mikrofIntent);

            //Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            //Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results)
        {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            int max=0;
            ArrayList<SongInfo> aux;
            String auxString;
            String maxString="";
            mezua = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Iterator<String> itr = mezua.iterator();

            if(mikroTitle){
                while(itr.hasNext()){
                    auxString = itr.next();
                    aux = logika.getSongs(auxString, "", zailtasuna.getSelectedItemPosition() - 1, favorito.isChecked(), pendiente.isChecked(), ikasia.isChecked());
                    if(aux.size()>=max){
                        max = aux.size();
                        maxString = auxString;
                    }
                }
                searchEditText.setText(maxString);
            }else{
                while(itr.hasNext()){
                    auxString = itr.next();
                    aux = logika.getSongs("", auxString, zailtasuna.getSelectedItemPosition() - 1, favorito.isChecked(), pendiente.isChecked(), ikasia.isChecked());
                    if(aux.size()>=max){
                        max = aux.size();
                        maxString = auxString;
                    }
                }
                autor.setText(maxString);
            }

            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }

}
