package com.example.javier.ukelelearrospi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SongsActivity extends Fragment implements TextWatcher, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

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

        zailtasuna.setOnItemClickListener(this);

        favorito.setOnCheckedChangeListener(this);
        pendiente.setOnCheckedChangeListener(this);
        ikasia.setOnCheckedChangeListener(this);

        v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new SongBerriaFragment())
                        .addToBackStack("lista_songs")
                        .commit();
            }
        });

        // Zerrenda
        RecyclerView recyclerView = ((RecyclerView) v.findViewById(R.id.recycler_view));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SongsAdapter(logika);
        recyclerView.setAdapter(adapter);

        // Bilaketa
        searchEditText = (EditText) v.findViewById(R.id.songs_search_edit_text);
        searchEditText.addTextChangedListener(this);
        autor = (EditText) v.findViewById(R.id.autor_edit_text);
        autor.addTextChangedListener(this);

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
        adapter.setSongs(logika.getSongs(searchEditText.getText().toString()));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        bilaketa();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        bilaketa();
    }
}
