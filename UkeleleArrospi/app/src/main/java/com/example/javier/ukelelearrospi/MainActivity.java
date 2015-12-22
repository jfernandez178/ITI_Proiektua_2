package com.example.javier.ukelelearrospi;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private EditText meditTExt;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //Toolbarra kargatu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation drawerra polita jarri lollipopen
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        meditTExt = (EditText) findViewById(R.id.editOngiEtorri);
        meditTExt.setKeyListener(null);


        drawerraKudeatu();

	}

    //TODO Hemen doiaz tokatzen dien fragmentak drawerretik. Berri bat gehitzeko menu/drawer.xml gehitu eta jarritako id-akin fragmentua aukeratu
    private void onNavigationMenuItemSelected(MenuItem menuItem){
        Fragment fragment = null;
        Bundle b = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                //TODO tokatzen dan fragmentua jarri kasu bakoitzean
                fragment = null; // fragment = new homeFragment()
                break;
            case R.id.drawer_main_chords:
                fragment = new ChordsActivity();
                break;
            case R.id.drawer_main_wikipedia:
                fragment = new WebViewFragement();
                b.putString(WebViewFragement.WEB_URL, "http://en.wikipedia.org/wiki/Ukulele");
                break;
            case R.id.drawer_main_learn:
                fragment = new SongsActivity();
                break;
            case R.id.drawer_sign_out:
                LoginLogika l = new LoginLogika(getApplicationContext());
                l.sesioaItxi();
                fragment = null;
                break;
        }

        //TODO Fragmentu bezela jartzean deskomentatu


        //Konprobatzen da ea fragment-a null den, eta horrela bada, Login pantaila irekiko da eta hau itxiko da
        if(fragment == null){
            finish();
            Intent i = new Intent(getBaseContext(), Login.class);
            startActivity(i);
        }
        else {
            fragment.setArguments(b);
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
        }

    }

    private void drawerraKudeatu(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                onNavigationMenuItemSelected(menuItem);
                return true;
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    // http://blog.raffaeu.com/archive/2015/04/11/android-and-the-transparent-status-bar.aspx
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openChords(View v) {
		Intent i = new Intent("assig.app2.learnukulele.ChordsActivity");
		startActivity(i);
	}
	
	public void openLearn(View v) {
		Intent i = new Intent("assig.app2.learnukulele.SongsActivity");
		startActivity(i);
	}
	
	
}
