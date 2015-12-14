package com.example.javier.ukelelearrospi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ander on 2015/12/12.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private ArrayList<SongInfo> kantuak;
    private SongsActivityLogika logika;
    private AppCompatActivity activity;

    public SongsAdapter(AppCompatActivity pActivity, SongsActivityLogika pLogika){
        logika = pLogika;
        activity = pActivity;
        kantuak = logika.getSongs(null, null, -1, false, false, false);
        setHasStableIds(true);
    }

    public void setSongs(ArrayList<SongInfo> kantuBerriak){
        kantuak = kantuBerriak;
        notifyDataSetChanged();
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.setSong(kantuak.get(position));
    }

    @Override
    public int getItemCount() {
        return kantuak.size();
    }

    @Override
    public long getItemId(int position) {
        return kantuak.get(position).getName().hashCode();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SongInfo songInfo;

        private View zailtasunaView;
        private TextView songName;
        private TextView authorName;
        private ImageView starView;
        private View klikView;

        public SongViewHolder(View itemView) {
            super(itemView);
            klikView = itemView.findViewById(R.id.item_klikatu);
            klikView.setOnClickListener(this);
            zailtasunaView = itemView.findViewById(R.id.song_zailtasuna);
            songName = (TextView) itemView.findViewById(R.id.song_item_name);
            authorName = (TextView) itemView.findViewById(R.id.song_item_author);
            starView = (ImageView) itemView.findViewById(R.id.song_star);
            starView.setOnClickListener(this);
        }

        public void setSong(SongInfo info){
            songInfo = info;
            songName.setText(info.getName());
            authorName.setText(info.getName());
            if (info.isFavorito()){
                starView.setImageResource(R.drawable.ic_star_24dp);
            }else{
                starView.setImageResource(R.drawable.ic_star_outline_24dp);
            }
            int color;
            switch (info.getZailtasuna()){
                case 0:
                    color = zailtasunaView.getResources().getColor(R.color.zailtasuna_erreza);
                    break;
                case 1:
                    color = zailtasunaView.getResources().getColor(R.color.zailtasuna_ertaina);
                    break;
                case 2:
                    color = zailtasunaView.getResources().getColor(R.color.zailtasuna_zaila);
                    break;
                case 3:
                    color = zailtasunaView.getResources().getColor(R.color.zailtasuna_oso_zaila);
                    break;
                default:
                    color = zailtasunaView.getResources().getColor(R.color.background_material_dark);
                    break;
            }
            zailtasunaView.setBackgroundColor(color);
        }

        @Override
        public void onClick(View view) {
            if (view == starView){
                logika.changeFavorito(songInfo);
                setSong(songInfo);
            }else{
                Fragment fragment = new LearnSongActivity();
                Bundle b = new Bundle();
                b.putString("kantuIzena", songInfo.getName());
                fragment.setArguments(b);
                android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        }
    }

}
