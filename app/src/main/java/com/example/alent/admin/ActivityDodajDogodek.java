package com.example.alent.admin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Dogodek;
import com.example.Seznam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.HttpRequest;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

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

    private EditText udelezba;
    private EditText ocena;
    private EditText cas;
    private EditText tip;

    private TextView lastnost;

    final String myTag = "DocsUpload";
    ApplicationMy app;
    Context context;

    private File mojFile;
    private String mojFileIme = "data.arff";
    private File poizkus;
    private String poizkusIme ="test.arff";

    Seznam seznam;

    public String mojaVsebina;
    public String testniPodatki;

    J48 drevo = new J48();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_dogodek);

        context = getApplicationContext();
        mojFile = new File(context.getFilesDir(), mojFileIme);
        poizkus = new File(context.getFilesDir(), poizkusIme);

        app = (ApplicationMy)getApplication();
        seznam=app.getAllData();

        nzv = (EditText) findViewById(R.id.idNaziv);

        dan = (EditText)findViewById(R.id.idDan);
        mesec = (EditText)findViewById(R.id.idMesec);
        leto = (EditText)findViewById(R.id.idLeto);

        cena = (EditText) findViewById(R.id.editText4);

        ura = (EditText) findViewById(R.id.idUraPricetka);
        minuta = (EditText)findViewById(R.id.idMinutaPricetka);

        naslov = (EditText) findViewById(R.id.idLokal);
        lokacija = (EditText) findViewById(R.id.idLokacija);

        udelezba = (EditText)findViewById(R.id.idUde);
        ocena = (EditText)findViewById(R.id.idOcena);
        cas = (EditText)findViewById(R.id.idCas);
        tip = (EditText)findViewById(R.id.idTip);

        lastnost = (TextView)findViewById(R.id.idLastnost);

    }

    public void preveriDat(File dat,String vsebina){
        try{
            FileOutputStream ven = new FileOutputStream(dat);
            ven.write(vsebina.getBytes());
            ven.flush();
            ven.close();
        }catch (IOException es){
            es.printStackTrace();
        }
    }

    public Instances result(File dat){
        try{
            BufferedReader bralec = new BufferedReader(new FileReader(dat));
            return new Instances(bralec);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }


    public void DodajIzvajalce(View v) throws IOException {
        Intent oknoIzv = new Intent(ActivityDodajDogodek.this, ActivityDodajVsebino.class);
        String strNaziv = nzv.getText().toString();
        String strDan = dan.getText().toString();
        String strMesec = mesec.getText().toString();
        String strLeto = leto.getText().toString();

        String strCena = cena.getText().toString();

        String strUra = ura.getText().toString();
        String strMinuta = minuta.getText().toString();

        String strNaslov = naslov.getText().toString();
        String strLokacija = lokacija.getText().toString();

        String strUdelezba = udelezba.getText().toString();
        String strOcena = ocena.getText().toString();
        String strCas = cas.getText().toString();
        String strTip = tip.getText().toString();

        oknoIzv.putExtra("Naziv",strNaziv);
        oknoIzv.putExtra("Dan",strDan);
        oknoIzv.putExtra("Mesec",strMesec);
        oknoIzv.putExtra("Leto",strLeto);
        oknoIzv.putExtra("Cena",strCena);
        oknoIzv.putExtra("Ura",strUra);
        oknoIzv.putExtra("Minuta",strMinuta);
        oknoIzv.putExtra("Naslov",strNaslov);
        oknoIzv.putExtra("Lokacija",strLokacija);

        app.save();


        testniPodatki ="@relation DogodkiNaSlovenskem\n" +
                "\n" +
                "@attribute Tip{Pop,Rock,Narodno-zabavni,Hip-Hop,Classic}\n" +
                "@attribute Naslov-lokala{Disco_Planet,Na_odprtem,Stuk,Pub_Beli_Konj,Trust,Bar_Lunca,Plus-Minus}\n" +
                "@attribute Pricetek{Dopoldan,Popoldan,Zvecer}\n" +
                "@attribute Cena{Brezplacno,3€,5€,10€,15€,20€}\n" +
                "@attribute Lokacija{Celje,Sentjur,Maribor,Slovenske_Konjice,Slovenska_Bistrica}\n" +
                "@attribute Udelezba{1x,3x,2x,veckrat}\n" +
                "@attribute Ocena_dogodka numeric\n" +
                "@attribute Class{Povprecen,Dober,Priporocljiv}\n" +
                "\n" +
                "@data\n" +
                "Pop,Disco_Planet,Dopoldan,Brezplacno,Celje,1x,5,Povprecen\n" +
                "Rock,Disco_Planet,Dopoldan,3€,Celje,1x,4,Povprecen\n" +
                "Pop,Na_odprtem,Popoldan,5€,Celje,3x,2,Dober\n" +
                "Narodno-zabavni,Na_odprtem,Popoldan,Brezplacno,Sentjur,3x,7,Priporocljiv\n" +
                "Hip-Hop,Stuk,Dopoldan,10€,Maribor,2x,8,Priporocljiv\n" +
                "Classic,Stuk,Popoldan,10€,Maribor,3x,9,Priporocljiv\n" +
                "Narodno-zabavni,Pub_Beli_Konj,Zvecer,Brezplacno,Slovenske_Konjice,3x,4,Povprecen\n" +
                "Hip-Hop,Pub_Beli_Konj,Dopoldan,3€,Slovenske_Konjice,3x,1,Dober\n" +
                "Classic,Disco_Planet,Popoldan,Brezplacno,Celje,2x,6,Povprecen\n" +
                "Narodno-zabavni,Disco_Planet,Zvecer,5€,Celje,2x,7,Priporocljiv\n" +
                "Pop,Trust,Dopoldan,15€,Maribor,3x,8,Priporocljiv\n" +
                "Hip-Hop,Na_odprtem,Zvecer,Brezplacno,Celje,3x,10,Priporocljiv\n" +
                "Classic,Bar_Lunca,Popoldan,3€,Slovenska_Bistrica,3x,10,Priporocljiv\n" +
                "Narodno-zabavni,Stuk,Zvecer,Brezplacno,Maribor,2x,9,Priporocljiv \n" +
                "Pop,Plus-Minus,Dopoldan,3€,Maribor,2x,7,Priporocljiv\n" +
                "Hip-Hop,Pub_Beli_Konj,Dopoldan,3€,Slovenske_Konjice,2x,3,Dober\n" +
                "Narodno-zabavni,Disco_Planet,Zvecer,Brezplacno,Celje,veckrat,4,Povprecen\n" +
                "Classic,Disco_Planet,Zvecer,15€,Celje,3x,6,Povprecen \n" +
                "Hip-Hop,Na_odprtem,Popoldan,3€,Celje,2x,7,Priporocljiv\n" +
                "Narodno-zabavni,Na_odprtem,Popoldan,Brezplacno,Celje,2x,3,Dober\n" +
                "Narodno-zabavni,Stuk,Dopoldan,10€,Maribor,3x,4,Povprecen\n" +
                "Hip-Hop,Stuk,Zvecer,3€,Maribor,1x,9,Priporocljiv\n" +
                "Classic,Pub_Beli_Konj,Dopoldan,Brezplacno,Slovenske_Konjice,2x,2,Dober\n" +
                "Narodno-zabavni,Na_odprtem,Popoldan,5€,Maribor,3x,8,Priporocljiv\n" +
                "Pop,Disco_Planet,Zvecer,3€,Celje,2x,9,Priporocljiv\n" +
                "Hip-Hop,Disco_Planet,Dopoldan,10€,Celje,1x,8,Priporocljiv \n" +
                "Narodno-zabavni,Trust,Dopoldan,Brezplacno,Maribor,1x,5,Povprecen\n" +
                "Classic,Trust,Zvecer,3€,Maribor,veckrat,4,Povprecen\n" +
                "Hip-Hop,Stuk,Zvecer,15€,Maribor,2x,6,Povprecen\n" +
                "Narodno-zabavni,Disco_Planet,Zvecer,Brezplacno,Maribor,2x,2,Dober \n" +
                "Hip-Hop,Plus-Minus,Dopoldan,3€,Maribor,3x,1,Dober\n" +
                "Rock,Pub_Beli_Konj,Dopoldan,10€,Slovenske_Konjice,3x,9,Priporocljiv\n" +
                "Pop,Disco_Planet,Dopoldan,15€,Celje,1x,7,Priporocljiv\n" +
                "Classic,Disco_Planet,Popoldan,Brezplacno,Celje,2x,10,Priporocljiv\n" +
                "Narodno-zabavni,Trust,Popoldan,3€,Maribor,veckrat,10,Priporocljiv\n" +
                "Hip-Hop,Na_odprtem,Dopoldan,15€,Sentjur,2x,6,Povprecen\n" +
                "Pop,Stuk,Zvecer,15€,Maribor,3x,4,Povprecen\n" +
                "Rock,Bar_Lunca,Zvecer,3€,Slovenska_Bistrica,2x,5,Povprecen\n" +
                "Narodno-zabavni,Na_odprtem,Dopoldan,Brezplacno,Sentjur,veckrat,7,Priporocljiv\n" +
                "Hip-Hop,Pub_Beli_Konj,Popoldan,5€,Slovenske_Konjice,veckrat,9,Priporocljiv\n" +
                "Rock,Disco_Planet,Zvecer,15€,Celje,2x,7,Priporocljiv \n" +
                "Classic,Disco_Planet,Zvecer,Brezplacno,Celje,2x,6,Povprecen\n" +
                "Narodno-zabavni,Bar_Lunca,Dopoldan,10€,Slovenska_Bistrica,3x,2,Dober\n" +
                "Pop,Na_odprtem,Popoldan,20€,Sentjur,veckrat,1,Dober\n" +
                "Hip-Hop,Stuk,Dopoldan,Brezplacno,Maribor,2x,8,Priporocljiv\n" +
                "Rock,Stuk,Popoldan,20€,Maribor,2x,6,Povprecen\n" +
                "Classic,Pub_Beli_Konj,Popoldan,3€,Slovenske_Konjice,1x,2,Dober\n" +
                "Narodno-zabavni,Disco_Planet,Zvecer,Brezplacno,Celje,3x,4,Povprecen\n" +
                "Classic,Disco_Planet,Zvecer,Brezplacno,Celje,veckrat,7,Priporocljiv\n" +
                "Rock,Disco_Planet,Popoldan,20€,Maribor,2x,8,Priporocljiv \n" +
                "Classic,Na_odprtem,Dopoldan,Brezplacno,Maribor,2x,9,Priporocljiv\n" +
                "Rock,Trust,Zvecer,3€,Maribor,2x,9,Priporocljiv\n" +
                "Narodno-zabavni,Na_odprtem,Zvecer,20€,Celje,3x,4,Povprecen\n" +
                "Pop,Bar_Lunca,Popoldan,20€,Slovenska_Bistrica,2x,2,Dober \n" +
                "Pop,Pub_Beli_Konj,Zvecer,Brezplacno,Slovenske_Konjice,2x,6,Povprecen\n" +
                "Hip-Hop,Pub_Beli_Konj,Popoldan,5€,Slovenske_Konjice,2x,7,Priporocljiv\n" +
                "Classic,Disco_Planet,Zvecer,Brezplacno,Celje,veckrat,9,Priporocljiv\n" +
                "Narodno-zabavni,Pub_Beli_Konj,Zvecer,10€,Slovenske_Konjice,2x,9,Priporocljiv\n" +
                "Rock,Trust,Dopoldan,20€,Maribor,2x,7,Priporocljiv\n" +
                "Classic,Na_odprtem,Dopoldan,Brezplacno,Sentjur,3x,2,Dober\n" +
                "Pop,Stuk,Popoldan,3€,Maribor,veckrat,3,Dober\n" +
                "Narodno-zabavni,Stuk,Zvecer,10€,Maribor,3x,10,Priporocljiv\n" +
                "Hip-Hop,Plus-Minus,Popoldan,Brezplacno,Maribor,2x,9,Priporocljiv\n" +
                "Rock,Pub_Beli_Konj,Popoldan,3€,Slovenske_Konjice,3x,9,Priporocljiv\n" +
                "Narodno-zabavni,Bar_Lunca,Dopoldan,Brezplacno,Slovenska_Bistrica,3x,3,Dober\n" +
                "Pop,Disco_Planet,Dopoldan,20€,Celje,2x,1,Dober \n" +
                "Rock,Na_odprtem,Popoldan,20€,Sentjur,1x,5,Povprecen\n" +
                "Hip-Hop,Na_odprtem,Zvecer,5€,Celje,2x,5,Povprecen\n" +
                "Classic,Bar_Lunca,Zvecer,Brezplacno,Slovenska_Bistrica,veckrat,6,Povprecen\n" +
                "Pop,Stuk,Popoldan,10€,Maribor,veckrat,7,Priporocljiv\n" +
                "Narodno-zabavni,Trust,Zvecer,3€,Maribor,2x,8,Priporocljiv\n" +
                "Pop,Pub_Beli_Konj,Zvecer,Brezplacno,Slovenske_Konjice,3x,3,Dober\n" +
                "Rock,Disco_Planet,Popoldan,20€,Celje,3x,9,Priporocljiv\n" +
                "Classic,Disco_Planet,Zvecer,3€,Celje,3x,2,Dober\n" +
                "Narodno-zabavni,Bar_Lunca,Dopoldan,Brezplacno,Slovenska_Bistrica,1x,9,Priporocljiv\n" +
                "Narodno-zabavni,Disco_Planet,Popoldan,3€,Celje,2x,8,Priporocljiv\n" +
                "Narodno-zabavni,Pub_Beli_Konj,Zvecer,15€,Slovenske_Konjice,2x,9,Priporocljiv \n" +
                "Pop,Bar_Lunca,Popoldan,5€,Slovenska_Bistrica,3x,7,Priporocljiv\n" +
                "Narodno-zabavni,Plus-Minus,Zvecer,Brezplacno,Maribor,veckrat,7,Priporocljiv\n" +
                "Classic,Pub_Beli_Konj,Dopoldan,20€,Slovenske_Konjice,3x,5,Povprecen\n" +
                "Hip-Hop,Disco_Planet,Popoldan,3€,Maribor,3x,8,Priporocljiv\n" +
                "Pop,Disco_Planet,Popoldan,10€,Celje,2x,10,Priporocljiv\n" +
                "Narodno-zabavni,Trust,Zvecer,Brezplacno,Maribor,veckrat,2,Dober\n" +
                "Pop,Na_odprtem,Zvecer,3€,Sentjur,2x,4,Povprecen\n" +
                "Classic,Stuk,Dopoldan,5€,Maribor,2x,5,Povprecen\n" +
                "Narodno-zabavni,Stuk,Zvecer,Brezplacno,Maribor,3x,4,Povprecen \n" +
                "Hip-Hop,Pub_Beli_Konj,Zvecer,10€,Slovenske_Konjice,veckrat,3,Dober\n" +
                "Rock,Plus-Minus,Popoldan,3€,Maribor,2x,2,Dober\n" +
                "Narodno-zabavni,Disco_Planet,Zvecer,Brezplacno,Celje,2x,8,Priporocljiv\n" +
                "Classic,Disco_Planet,Popoldan,10€,Celje,2x,10,Priporocljiv\n" +
                "Narodno-zabavni,Na_odprtem,Popoldan,Brezplacno,Maribor,3x,2,Dober\n" +
                "Pop,Trust,Popoldan,20€,Maribor,2x,6,Povprecen\n" +
                "Hip-Hop,Bar_Lunca,Dopoldan,5€,Slovenska_Bistrica,veckrat,5,Povprecen \n" +
                "Narodno-zabavni,Stuk,Zvecer,Brezplacno,Maribor,3x,3,Dober\n" +
                "Classic,Pub_Beli_Konj,Popoldan,5€,Slovenske_Konjice,2x,7,Priporocljiv\n" +
                "Narodno-zabavni,Plus-Minus,Popoldan,Brezplacno,Maribor,3x,9,Priporocljiv\n" +
                "Hip-Hop,Disco_Planet,Popoldan,20€,Celje,3x,4,Povprecen\n" +
                "Narodno-zabavni,Disco_Planet,Dopoldan,Brezplacno,Celje,1x,6,Povprecen \n" +
                "Narodno-zabavni,Bar_Lunca,Zvecer,3€,Slovenska_Bistrica,veckrat,5,Povprecen\n";

        preveriDat(mojFile,testniPodatki);
        KlasifikacijaJ481();

        String noviPodatki="@relation DogodkiNaSlovenskem\n" +
                "\n" +
                "@attribute Tip{Pop,Rock,Narodno-zabavni,Hip-Hop,Classic}\n" +
                "@attribute Naslov-lokala{Disco_Planet,Na_odprtem,Stuk,Pub_Beli_Konj,Trust,Bar_Lunca,Plus-Minus}\n" +
                "@attribute Pricetek{Dopoldan,Popoldan,Zvecer}\n" +
                "@attribute Cena{Brezplacno,3€,5€,10€,15€,20€}\n" +
                "@attribute Lokacija{Celje,Sentjur,Maribor,Slovenske_Konjice,Slovenska_Bistrica}\n" +
                "@attribute Udelezba{1x,3x,2x,veckrat}\n" +
                "@attribute Ocena_dogodka numeric\n" +
                "@attribute Class{Povprecen,Dober,Priporocljiv}\n" +
                "\n" +
                "@data"+
                "\n"
                + strTip +"," +  strNaslov +"," + strCas +"," + strCena +"," + strLokacija +"," + strUdelezba +"," + strOcena.toString() +"," + "?" +"\n";


            File file = new File(context.getFilesDir(),"data2.arff");
            FileOutputStream izven = new FileOutputStream(file);
            izven.write(noviPodatki.getBytes());
            izven.flush();
            izven.close();

            BufferedReader bralec = null;
            try{
                bralec = new BufferedReader(new FileReader(file));
            }catch(FileNotFoundException ex){
                ex.printStackTrace();
            }

        Instances dataset = null;
        dataset = new Instances(bralec);

        bralec.close();

        dataset.setClassIndex(dataset.numAttributes()-1);

        try{
            String[]opcije = new String[6];
            opcije[0]="-B";
            opcije[1]="10";
            opcije[2]="-M";
            opcije[3]="-1.0";
            opcije[4]="-R";
            opcije[5]="first-last";

            Discretize disc = new Discretize();
            disc.setOptions(opcije);
            disc.setInputFormat(dataset);
            Instances noveInstance = Filter.useFilter(dataset,disc);

            for(int i = 0; i<noveInstance.numInstances();i++){
                double score;
                score = drevo.classifyInstance(dataset.instance(i));
                dataset.instance(i).setClassValue(score);
            }

            for(int j = 0; j<noveInstance.size();j++){
                lastnost.setText(noveInstance.get(j).stringValue(noveInstance.get(j).classAttribute()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //startActivity(oknoIzv);
    }

     public void KlasifikacijaJ481(){
        Instances ins = result(mojFile);
        ins.setClassIndex(ins.numAttributes()-1);
        try {
            String opcije[]=new String[1];
            opcije[0]="-U";
            drevo.setOptions(opcije);
            drevo.buildClassifier(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PostDataToFile(String nzv,String dan, String mesec, String leto, String price,String clock,String minute, String naslov, String lokacija ) {
        String URL = "https://docs.google.com/forms/d/e/1FAIpQLSduR3EnYwJMJYMnUYfN2kKg16B5-jQq98Pmdn8cgMV7WULrzA/formResponse";
        com.example.alent.admin.HttpRequest httpRequest = new com.example.alent.admin.HttpRequest();

        String data = "entry.2049629797=" + URLEncoder.encode(nzv.toString()) + "&" + "entry.779257088_year=" + URLEncoder.encode(leto.toString()) + "&" + "entry.779257088_month=" + URLEncoder.encode(mesec.toString()) + "&" + "entry.779257088_day=" + URLEncoder.encode(dan.toString()) + "&" + "entry.2118766818=" + URLEncoder.encode(price.toString()) + "&" + "entry.1168084730_hour=" + URLEncoder.encode(clock.toString()) + "&" + "entry.1168084730_minute=" + URLEncoder.encode(minute.toString()) + "&" + "entry.1104322596=" + URLEncoder.encode(naslov.toString())+ "&" + "entry.588434556=" + URLEncoder.encode(lokacija.toString());
        String response = httpRequest.sendPost(URL, data);
        Log.i(myTag, response);
        app.save();
    }
}
