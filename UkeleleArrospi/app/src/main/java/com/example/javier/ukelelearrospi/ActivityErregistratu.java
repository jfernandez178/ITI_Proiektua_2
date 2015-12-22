package com.example.javier.ukelelearrospi;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActivityErregistratu extends AppCompatActivity implements View.OnClickListener {

    private Button botonErregistratu;
    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmPasswordText;

    private String username;
    private String password;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_erregistratu);

        botonErregistratu = (Button) findViewById(R.id.buttonErregistratu);
        botonErregistratu.setOnClickListener(this);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPassword);

    }



    public void onStop() {
        Intent i = new Intent(getBaseContext(), Login.class);
        startActivity(i);
        super.onStop();
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.buttonErregistratu:

                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                confirmPassword = confirmPasswordText.getText().toString();


                //Balioak espezifikatu baldin baditu
                if(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
                    //Pasahitzaren konfirmazioa eta pasahitza berdinak izan behar dira
                    if(password.equals(confirmPassword)){
                        //Erregistratzen saiatzen da eta erregistroa zuzena bada fragmenta ixten da eta Login-era bueltatzen da.
                        boolean ondoErregistratuDa = ErregistratuLogika.getErregistratuLogika(getApplicationContext()).ondoErregistratuDa(username, password);
                        if(ondoErregistratuDa){
                            Toast.makeText(getApplicationContext(), "Ondo erregistratu zara!",
                                    Toast.LENGTH_SHORT).show();

                            //Pantaila ixten da eta Login-a bistaratzen da
                            finish();

                            Intent i = new Intent(getBaseContext(), Login.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Erabiltzaile hori existitzen da",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Pasahitza eta konfirmazioa ez dira zuzenak",
                                Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Konprobatu eremu guztiak bete dituzula",
                            Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
