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
import android.widget.EditText;

public class SongsActivity extends Fragment implements TextWatcher {

    private SongsActivityLogika logika;
    private SongsAdapter adapter;

    private EditText searchEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs, container, false);

        logika = new SongsActivityLogika(getActivity());

        // Zerrenda
        RecyclerView recyclerView = ((RecyclerView) v.findViewById(R.id.recycler_view));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SongsAdapter(logika.getSongs(null));
        recyclerView.setAdapter(adapter);

        // Bilaketa
        searchEditText = (EditText) v.findViewById(R.id.songs_search_edit_text);
        searchEditText.addTextChangedListener(this);

        return v;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.setSongs(logika.getSongs(searchEditText.getText().toString()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
