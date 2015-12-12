package com.example.javier.ukelelearrospi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ander on 2015/12/12.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private ArrayList<SongInfo> kantuak;

    public SongsAdapter(ArrayList<SongInfo> pKantuak){
        kantuak = pKantuak;
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

    public static class SongViewHolder extends RecyclerView.ViewHolder{

        private View zailtasunaView;
        private TextView songName;
        private TextView authorName;

        public SongViewHolder(View itemView) {
            super(itemView);
            zailtasunaView = itemView.findViewById(R.id.song_zailtasuna);
            songName = (TextView) itemView.findViewById(R.id.song_item_name);
            authorName = (TextView) itemView.findViewById(R.id.song_item_author);
        }

        public void setSong(SongInfo info){
            songName.setText(info.getName());
            authorName.setText(info.getName());
            if (info.getZailtasuna()<3){
                zailtasunaView.setBackgroundColor(zailtasunaView.getResources().getColor(R.color.zailtasuna_erreza));
            }else{
                zailtasunaView.setBackgroundColor(zailtasunaView.getResources().getColor(R.color.zailtasuna_zaila));
            }
        }
    }

}
