package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ActivityAdmin extends AppCompatActivity {


    ProgressBar krog;
    Button odjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final TextView Username = (TextView) findViewById(R.id.idUporabniskoAdmin);
        final TextView Email = (TextView) findViewById(R.id.idEposta);
        //krog = (ProgressBar)findViewById(R.id.idProgress);
        //krog.setVisibility(View.INVISIBLE);
        odjava = (Button)findViewById(R.id.idOdjava);

        String namestr = getIntent().getStringExtra("Uporabnisko");
        String Epostastr = getIntent().getStringExtra("E-posta");

        Username.setText(namestr);
        Email.setText(Epostastr);

    }

    public void DodajDogodek(View v){
        Intent oknoDogodek = new Intent(this, ActivityDodajDogodek.class);
        startActivity(oknoDogodek);
    }

    public void PregledDogodkov(View v){
        Intent oknoPregledDogodkov = new Intent(this, ActivityPredgledDogodkov.class);
        startActivity(oknoPregledDogodkov);
    }

    public void Odjava(View v){

        Toast.makeText(ActivityAdmin.this,"Odjavljanje",Toast.LENGTH_SHORT);

        if(odjava.isPressed()) {
            krog.setVisibility(View.VISIBLE);
            Intent oknoLogin = new Intent(this, ActivityLogin.class);
            startActivity(oknoLogin);
        }

    }

    public void WekaKlik(View v){
        Intent i = new Intent(this,WekaClassification.class);
        startActivity(i);
    }
}
