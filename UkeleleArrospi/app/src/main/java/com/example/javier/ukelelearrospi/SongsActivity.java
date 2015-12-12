package com.example.javier.ukelelearrospi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SongsActivity extends Fragment {

    private SongsActivityLogika logika;
    private SongsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs, container, false);
        RecyclerView recyclerView = ((RecyclerView) v.findViewById(R.id.recycler_view));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        logika = new SongsActivityLogika(getActivity());
        adapter = new SongsAdapter(logika.getSongs());
        recyclerView.setAdapter(adapter);
        return v;
    }
    




}
