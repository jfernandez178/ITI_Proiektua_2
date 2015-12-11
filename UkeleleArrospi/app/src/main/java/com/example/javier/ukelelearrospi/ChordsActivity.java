package com.example.javier.ukelelearrospi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChordsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chords);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chords, menu);
		return true;
	}

}
