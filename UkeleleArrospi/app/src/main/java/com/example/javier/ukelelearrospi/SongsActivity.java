package com.example.javier.ukelelearrospi;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SongsActivity extends Activity {

	int index;
	ListView l1;
	
	SongInfo mySong;
	
    String[] songs = {
            "HALLELUJAH",
            "I SHOT THE SHERIFF",
            "I AM YOURS",
            "LET IT BE",
            "SOMEWHERE OVER THE RAINBOW",
            "SWEET HOME ALABAMA",
                     };
    
    String[] songsMp3 = {
            "HALLELUJAH-MP3",
            "I SHOT THE SHERIFF",
            "I AM YOURS",
            "LET IT BE",
            "SOMEWHERE OVER THE RAINBOW",
            "SWEET HOME ALABAMA",
                     };
    
    String[] songsYoutube = {
            "http://www.youtube.com/watch?v=XzOdXhywIbo",
            "http://www.youtube.com/watch?v=IzTgYrnhYhs",
            "http://www.youtube.com/watch?v=1VyPSzbe_x0",
            "http://www.youtube.com/watch?v=LoVvrLPX4eQ",
            "http://www.youtube.com/watch?v=1PiscVZSuEE",
            "http://www.youtube.com/watch?v=LDCzpvvSvjY",
                     };
    
    Intent i = new Intent("assig.app2.learnukulele.LearnSongActivity");
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_songs);
		
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, songs);

        //---List View---
        l1 = (ListView) findViewById(R.id.ListView1);
        l1.setAdapter(adapter);
        l1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                index = arg2;
                
                Toast.makeText(getBaseContext(),
                        "You have selected song : " + songs[index], 
                        Toast.LENGTH_SHORT).show();
                
                mySong = new SongInfo();
                
                mySong.setIndex(index);
                mySong.setName(songs[index]);
                mySong.setMp3(songsMp3[index]);
                mySong.setYoutube(songsYoutube[index]);
                
                i.putExtra("MySong", mySong);
        	    
        		startActivity(i);
        		         
            }
        } );
	
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.songs, menu);
		return true;
	}

}
