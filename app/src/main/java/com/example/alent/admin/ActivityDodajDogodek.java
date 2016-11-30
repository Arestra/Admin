package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpRequest;

public class ActivityDodajDogodek extends AppCompatActivity {

    private EditText nzv;

    private EditText dan;
    private EditText mesec;
    private EditText leto;

    private EditText cena;

    private EditText ura;
    private EditText minuta;

    private EditText naslov;
    private EditText lokacija;

    final String myTag = "DocsUpload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_dogodek);



        nzv = (EditText) findViewById(R.id.idNaziv);

        dan = (EditText)findViewById(R.id.idDan);
        mesec = (EditText)findViewById(R.id.idMesec);
        leto = (EditText)findViewById(R.id.idLeto);

        cena = (EditText) findViewById(R.id.editText4);

        ura = (EditText) findViewById(R.id.idUraPricetka);
        minuta = (EditText)findViewById(R.id.idMinutaPricetka);

        naslov = (EditText) findViewById(R.id.idLokal);
        lokacija = (EditText) findViewById(R.id.idLokacija);


        final Button shrani = (Button) findViewById(R.id.idShraniOdlocitev);
        shrani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strNaziv = nzv.getText().toString();

                final String strDan = dan.getText().toString();
                final String strMesec = mesec.getText().toString();
                final String strLeto = leto.getText().toString();

                final String strCena = cena.getText().toString();

                final String strUra = ura.getText().toString();
                final String strMinuta = minuta.getText().toString();

                final String strNaslov = naslov.getText().toString();
                final String strLokacija = lokacija.getText().toString();


                Log.i(myTag, "OnCreate()");
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PostDataToFile(strNaziv,strDan,strMesec,strLeto,strCena,strUra,strMinuta,strNaslov,strLokacija);
                    }
                });
                t.start();

                Toast.makeText(ActivityDodajDogodek.this,"Podatki so bili vnešeni in shranjeni!",Toast.LENGTH_SHORT);
            }
        });

    }

    public void DodajIzvajalce(View v) {
        Intent oknoIzv = new Intent(ActivityDodajDogodek.this, ActivityDodajVsebino.class);
        startActivity(oknoIzv);
    }

    public void PostDataToFile(String nzv,String dan, String mesec, String leto, String price,String clock,String minute, String naslov, String lokacija ) {
        String URL = "https://docs.google.com/forms/d/e/1FAIpQLSduR3EnYwJMJYMnUYfN2kKg16B5-jQq98Pmdn8cgMV7WULrzA/formResponse";
        com.example.alent.admin.HttpRequest httpRequest = new com.example.alent.admin.HttpRequest();

        String data = "entry.2049629797=" + URLEncoder.encode(nzv.toString()) + "&" + "entry.779257088_year=" + URLEncoder.encode(leto.toString()) + "&" + "entry.779257088_month=" + URLEncoder.encode(mesec.toString()) + "&" + "entry.779257088_day=" + URLEncoder.encode(dan.toString()) + "&" + "entry.2118766818=" + URLEncoder.encode(price.toString()) + "&" + "entry.1168084730_hour=" + URLEncoder.encode(clock.toString()) + "&" + "entry.1168084730_minute=" + URLEncoder.encode(minute.toString()) + "&" + "entry.1104322596=" + URLEncoder.encode(naslov.toString())+ "&" + "entry.588434556=" + URLEncoder.encode(lokacija.toString());
        String response = httpRequest.sendPost(URL, data);
        Log.i(myTag, response);
    }
}
