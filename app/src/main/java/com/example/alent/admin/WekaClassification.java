package com.example.alent.admin;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

public class WekaClassification extends AppCompatActivity {
    private TextView Eval;
    private TextView tipDog;
    private TextView lokalDog;
    private TextView pricetekDog;
    private TextView cenaDog;
    private TextView lokacijaDog;
    private TextView udelezbaDog;
    private TextView ocenaDog;
    private TextView clasDog;
    Context context;


    private File mojFile;
    private String mojFileIme = "data.arff";
    private File poizkus;
    private String poizkusIme ="test.arff";

    public String mojaVsebina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weka_classification);
        Eval = (TextView)findViewById(R.id.idEval);
        tipDog = (EditText)findViewById(R.id.tipD);
        lokalDog = (EditText)findViewById(R.id.lokalD);
        pricetekDog = (EditText)findViewById(R.id.pricetekD);
        cenaDog = (EditText)findViewById(R.id.cenaD);
        lokacijaDog = (EditText)findViewById(R.id.lokacijaD);
        udelezbaDog = (EditText)findViewById(R.id.udelezbaD);
        ocenaDog = (EditText)findViewById(R.id.ocenaD);
        clasDog = (EditText)findViewById(R.id.klasD);

        context = getApplicationContext();
        mojFile = new File(context.getFilesDir(), mojFileIme);
        poizkus = new File(context.getFilesDir(), poizkusIme);
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

    public void Result(View v) throws Exception {

        String t = tipDog.getText().toString();
        String l = lokalDog.getText().toString();
        String p = pricetekDog.getText().toString();
        String c = cenaDog.getText().toString();
        String lo = lokacijaDog.getText().toString();
        String ude = udelezbaDog.getText().toString();
        String oc = ocenaDog.getText().toString(); // je numericna, lahko jo kar das tako noter, ne rabis diskretizirat
        String k = clasDog.getText().toString();

        if(t.equals("")||l.equals("")||p.equals("")||c.equals("")||lo.equals("")||ude.equals("")||oc.equals("")||k.equals("")){
            mojaVsebina ="@relation DogodkiNaSlovenskem\n" +
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
                    "Pop,' Disco_Planet',' Dopoldan',' Brezplacno',' Celje',' 1x',5,' Povprecen'\n" +
                    "Rock,' Disco_Planet',' Dopoldan',' 3€',' Celje',' 1x',4,' Povprecen'\n" +
                    "Pop,' Na_odprtem',' Popoldan',' 5€',' Celje',' 3x',2,' Dober'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Popoldan',' Brezplacno',' Sentjur',' 3x',7,' Priporocljiv'\n" +
                    "Hip-Hop,' Stuk',' Dopoldan',' 10€',' Maribor',' 2x',8,' Priporocljiv'\n" +
                    "Classic,' Stuk',' Popoldan',' 10€',' Maribor',' 3x',9,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Pub_Beli_Konj',' Zvecer',' Brezplacno',' Slovenske_Konjice',' 3x',4,' Povprecen'\n" +
                    "Hip-Hop,' Pub_Beli_Konj',' Dopoldan',' 3€',' Slovenske_Konjice',' 3x',1,' Dober'\n" +
                    "Classic,' Disco_Planet',' Popoldan',' Brezplacno',' Celje',' 2x',6,' Povprecen'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Zvecer',' 5€',' Celje',' 2x',7,' Priporocljiv'\n" +
                    "Pop,' Trust',' Dopoldan',' 15€',' Maribor',' 3x',8,' Priporocljiv'\n" +
                    "Hip-Hop,' Na_odprtem',' Zvecer',' Brezplacno',' Celje',' 3x',10,' Priporocljiv'\n" +
                    "Classic,' Bar_Lunca',' Popoldan',' 3€',' Slovenska_Bistrica',' 3x',10,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Stuk',' Zvecer',' Brezplacno',' Maribor',' 2x',9,' Priporocljiv '\n" +
                    "Pop,' Plus-Minus',' Dopoldan',' 3€',' Maribor',' 2x',7,' Priporocljiv'\n" +
                    "Hip-Hop,' Pub_Beli_Konj',' Dopoldan',' 3€',' Slovenske_Konjice',' 2x',3,' Dober'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' veckrat',4,' Povprecen'\n" +
                    "Classic,' Disco_Planet',' Zvecer',' 15€',' Celje',' 3x',6,' Povprecen '\n" +
                    "Hip-Hop,' Na_odprtem',' Popoldan',' 3€',' Celje',' 2x',7,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Popoldan',' Brezplacno',' Celje',' 2x',3,' Dober'\n" +
                    "Narodno-zabavni,' Stuk',' Dopoldan',' 10€',' Maribor',' 3x',4,' Povprecen'\n" +
                    "Hip-Hop,' Stuk',' Zvecer',' 3€',' Maribor',' 1x',9,' Priporocljiv'\n" +
                    "Classic,' Pub_Beli_Konj',' Dopoldan',' Brezplacno',' Slovenske_Konjice',' 2x',2,' Dober'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Popoldan',' 5€',' Maribor',' 3x',8,' Priporocljiv'\n" +
                    "Pop,' Disco_Planet',' Zvecer',' 3€',' Celje',' 2x',9,' Priporocljiv'\n" +
                    "Hip-Hop,' Disco_Planet',' Dopoldan',' 10€',' Celje',' 1x',8,' Priporocljiv '\n" +
                    "Narodno-zabavni,' Trust',' Dopoldan',' Brezplacno',' Maribor',' 1x',5,' Povprecen'\n" +
                    "Classic,' Trust',' Zvecer',' 3€',' Maribor',' veckrat',4,' Povprecen'\n" +
                    "Hip-Hop,' Stuk',' Zvecer',' 15€',' Maribor',' 2x',6,' Povprecen'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Zvecer',' Brezplacno',' Maribor',' 2x',2,' Dober '\n" +
                    "Hip-Hop,' Plus-Minus',' Dopoldan',' 3€',' Maribor',' 3x',1,' Dober'\n" +
                    "Rock,' Pub_Beli_Konj',' Dopoldan',' 10€',' Slovenske_Konjice',' 3x',9,' Priporocljiv'\n" +
                    "Pop,' Disco_Planet',' Dopoldan',' 15€',' Celje',' 1x',7,' Priporocljiv'\n" +
                    "Classic,' Disco_Planet',' Popoldan',' Brezplacno',' Celje',' 2x',10,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Trust',' Popoldan',' 3€',' Maribor',' veckrat',10,' Priporocljiv'\n" +
                    "Hip-Hop,' Na_odprtem',' Dopoldan',' 15€',' Sentjur',' 2x',6,' Povprecen'\n" +
                    "Pop,' Stuk',' Zvecer',' 15€',' Maribor',' 3x',4,' Povprecen'\n" +
                    "Rock,' Bar_Lunca',' Zvecer',' 3€',' Slovenska_Bistrica',' 2x',5,' Povprecen'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Dopoldan',' Brezplacno',' Sentjur',' veckrat',7,' Priporocljiv'\n" +
                    "Hip-Hop,' Pub_Beli_Konj',' Popoldan',' 5€',' Slovenske_Konjice',' veckrat',9,' Priporocljiv'\n" +
                    "Rock,' Disco_Planet',' Zvecer',' 15€',' Celje',' 2x',7,' Priporocljiv '\n" +
                    "Classic,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' 2x',6,' Povprecen'\n" +
                    "Narodno-zabavni,' Bar_Lunca',' Dopoldan',' 10€',' Slovenska_Bistrica',' 3x',2,' Dober'\n" +
                    "Pop,' Na_odprtem',' Popoldan',' 20€',' Sentjur',' veckrat',1,' Dober'\n" +
                    "Hip-Hop,' Stuk',' Dopoldan',' Brezplacno',' Maribor',' 2x',8,' Priporocljiv'\n" +
                    "Rock,' Stuk',' Popoldan',' 20€',' Maribor',' 2x',6,' Povprecen'\n" +
                    "Classic,' Pub_Beli_Konj',' Popoldan',' 3€',' Slovenske_Konjice',' 1x',2,' Dober'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' 3x',4,' Povprecen'\n" +
                    "Classic,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' veckrat',7,' Priporocljiv'\n" +
                    "Rock,' Disco_Planet',' Popoldan',' 20€',' Maribor',' 2x',8,' Priporocljiv '\n" +
                    "Classic,' Na_odprtem',' Dopoldan',' Brezplacno',' Maribor',' 2x',9,' Priporocljiv'\n" +
                    "Rock,' Trust',' Zvecer',' 3€',' Maribor',' 2x',9,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Zvecer',' 20€',' Celje',' 3x',4,' Povprecen'\n" +
                    "Pop,' Bar_Lunca',' Popoldan',' 20€',' Slovenska_Bistrica',' 2x',2,' Dober '\n" +
                    "Pop,' Pub_Beli_Konj',' Zvecer',' Brezplacno',' Slovenske_Konjice',' 2x',6,' Povprecen'\n" +
                    "Hip-Hop,' Pub_Beli_Konj',' Popoldan',' 5€',' Slovenske_Konjice',' 2x',7,' Priporocljiv'\n" +
                    "Classic,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' veckrat',9,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Pub_Beli_Konj',' Zvecer',' 10€',' Slovenske_Konjice',' 2x',9,' Priporocljiv'\n" +
                    "Rock,' Trust',' Dopoldan',' 20€',' Maribor',' 2x',7,' Priporocljiv'\n" +
                    "Classic,' Na_odprtem',' Dopoldan',' Brezplacno',' Sentjur',' 3x',2,' Dober'\n" +
                    "Pop,' Stuk',' Popoldan',' 3€',' Maribor',' veckrat',3,' Dober'\n" +
                    "Narodno-zabavni,' Stuk',' Zvecer',' 10€',' Maribor',' 3x',10,' Priporocljiv'\n" +
                    "Hip-Hop,' Plus-Minus',' Popoldan',' Brezplacno',' Maribor',' 2x',9,' Priporocljiv'\n" +
                    "Rock,' Pub_Beli_Konj',' Popoldan',' 3€',' Slovenske_Konjice',' 3x',9,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Bar_Lunca',' Dopoldan',' Brezplacno',' Slovenska_Bistrica',' 3x',3,' Dober'\n" +
                    "Pop,' Disco_Planet',' Dopoldan',' 20€',' Celje',' 2x',1,' Dober '\n" +
                    "Rock,' Na_odprtem',' Popoldan',' 20€',' Sentjur',' 1x',5,' Povprecen'\n" +
                    "Hip-Hop,' Na_odprtem',' Zvecer',' 5€',' Celje',' 2x',5,' Povprecen'\n" +
                    "Classic,' Bar_Lunca',' Zvecer',' Brezplacno',' Slovenska_Bistrica',' veckrat',6,' Povprecen'\n" +
                    "Pop,' Stuk',' Popoldan',' 10€',' Maribor',' veckrat',7,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Trust',' Zvecer',' 3€',' Maribor',' 2x',8,' Priporocljiv'\n" +
                    "Pop,' Pub_Beli_Konj',' Zvecer',' Brezplacno',' Slovenske_Konjice',' 3x',3,' Dober'\n" +
                    "Rock,' Disco_Planet',' Popoldan',' 20€',' Celje',' 3x',9,' Priporocljiv'\n" +
                    "Classic,' Disco_Planet',' Zvecer',' 3€',' Celje',' 3x',2,' Dober'\n" +
                    "Narodno-zabavni,' Bar_Lunca',' Dopoldan',' Brezplacno',' Slovenska_Bistrica',' 1x',9,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Popoldan',' 3€',' Celje',' 2x',8,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Pub_Beli_Konj',' Zvecer',' 15€',' Slovenske_Konjice',' 2x',9,' Priporocljiv '\n" +
                    "Pop,' Bar_Lunca',' Popoldan',' 5€',' Slovenska_Bistrica',' 3x',7,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Plus-Minus',' Zvecer',' Brezplacno',' Maribor',' veckrat',7,' Priporocljiv'\n" +
                    "Classic,' Pub_Beli_Konj',' Dopoldan',' 20€',' Slovenske_Konjice',' 3x',5,' Povprecen'\n" +
                    "Hip-Hop,' Disco_Planet',' Popoldan',' 3€',' Maribor',' 3x',8,' Priporocljiv'\n" +
                    "Pop,' Disco_Planet',' Popoldan',' 10€',' Celje',' 2x',10,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Trust',' Zvecer',' Brezplacno',' Maribor',' veckrat',2,' Dober'\n" +
                    "Pop,' Na_odprtem',' Zvecer',' 3€',' Sentjur',' 2x',4,' Povprecen'\n" +
                    "Classic,' Stuk',' Dopoldan',' 5€',' Maribor',' 2x',5,' Povprecen'\n" +
                    "Narodno-zabavni,' Stuk',' Zvecer',' Brezplacno',' Maribor',' 3x',4,' Povprecen '\n" +
                    "Hip-Hop,' Pub_Beli_Konj',' Zvecer',' 10€',' Slovenske_Konjice',' veckrat',3,' Dober'\n" +
                    "Rock,' Plus-Minus',' Popoldan',' 3€',' Maribor',' 2x',2,' Dober'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Zvecer',' Brezplacno',' Celje',' 2x',8,' Priporocljiv'\n" +
                    "Classic,' Disco_Planet',' Popoldan',' 10€',' Celje',' 2x',10,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Na_odprtem',' Popoldan',' Brezplacno',' Maribor',' 3x',2,' Dober'\n" +
                    "Pop,' Trust',' Popoldan',' 20€',' Maribor',' 2x',6,' Povprecen'\n" +
                    "Hip-Hop,' Bar_Lunca',' Dopoldan',' 5€',' Slovenska_Bistrica',' veckrat',5,' Povprecen '\n" +
                    "Narodno-zabavni,' Stuk',' Zvecer',' Brezplacno',' Maribor',' 3x',3,' Dober'\n" +
                    "Classic,' Pub_Beli_Konj',' Popoldan',' 5€',' Slovenske_Konjice',' 2x',7,' Priporocljiv'\n" +
                    "Narodno-zabavni,' Plus-Minus',' Popoldan',' Brezplacno',' Maribor',' 3x',9,' Priporocljiv'\n" +
                    "Hip-Hop,' Disco_Planet',' Popoldan',' 20€',' Celje',' 3x',4,' Povprecen'\n" +
                    "Narodno-zabavni,' Disco_Planet',' Dopoldan',' Brezplacno',' Celje',' 1x',6,' Povprecen '\n" +
                    "Narodno-zabavni,' Bar_Lunca',' Zvecer',' 3€',' Slovenska_Bistrica',' veckrat',5,' Povprecen'\n";

            preveriDat(mojFile,mojaVsebina);
            Toast.makeText(WekaClassification.this,"Atributi niso bili vnešeni ali pa niso bili vsi!",Toast.LENGTH_LONG).show();
            KlasifikacijaJ481();

        }else {

            FastVector tipL = new FastVector(1); // za tip lokala
            tipL.addElement(t);
            Attribute a1 = new Attribute("Tip _dogodka", tipL);

            FastVector lokalD = new FastVector(1);
            lokalD.addElement(l);
            Attribute a2 = new Attribute("Naslov_lokala", lokalD);

            FastVector pricD = new FastVector(1); // za tip lokala
            pricD.addElement(p);
            Attribute a3 = new Attribute("Pricetek", pricD);

            FastVector cenaD = new FastVector(1);
            cenaD.addElement(c);
            Attribute a4 = new Attribute("Cena", cenaD);

            FastVector lokacijaD = new FastVector(1); // za tip lokala
            lokacijaD.addElement(lo);
            Attribute a5 = new Attribute("Lokacija", lokacijaD);

            FastVector udelezbaD = new FastVector(1);
            udelezbaD.addElement(ude);
            Attribute a6 = new Attribute("Udelezba", udelezbaD);

            Attribute a7 = new Attribute("Ocena_dogodka", oc); //numeričen

            FastVector klassD = new FastVector(1);
            klassD.addElement(k);
            Attribute a8 = new Attribute("Razred", klassD);

            FastVector DogodkiAttributes = new FastVector(8);
            DogodkiAttributes.addElement(a1);
            DogodkiAttributes.addElement(a2);
            DogodkiAttributes.addElement(a3);
            DogodkiAttributes.addElement(a4);
            DogodkiAttributes.addElement(a5);
            DogodkiAttributes.addElement(a6);
            DogodkiAttributes.addElement(a7);
            DogodkiAttributes.addElement(a8);

            Instances dataset = new Instances("Pregled_dogodka", DogodkiAttributes, 8);

            Instance i1 = new DenseInstance(8);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(0), t);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(1), l);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(2), p);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(3), c);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(4), lo);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(5), ude);
            i1.setValue((Attribute) DogodkiAttributes.elementAt(6), Integer.parseInt(oc));
            i1.setValue((Attribute) DogodkiAttributes.elementAt(7), k);

            dataset.add(i1);
            String newDataString = "";

            for (int i = 0; i < dataset.size(); i++)
                newDataString += dataset.instance(i).toString() + '\n';

            mojaVsebina = "@relation DogodkiNaSlovenskem\n" +
                    "\n" +
                    "@attribute Tip {Pop,Rock,Narodno-zabavni,Hip-Hop,Classic}\n" +
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
                    "Narodno-zabavni,Bar_Lunca,Zvecer,3€,Slovenska_Bistrica,veckrat,5,Povprecen\n"
                    + newDataString;

            preveriDat(mojFile, mojaVsebina);
            Toast.makeText(WekaClassification.this,"Atributi so bili vnešeni in klasificirani",Toast.LENGTH_SHORT).show();
            KlasifikacijaJ482();
        }
    }

    public void KlasifikacijaJ481(){
        Instances ins = result(mojFile);
        ins.setClassIndex(ins.numAttributes()-1);
        J48 drevo = new J48();
        drevo.setNumFolds(10);
        try {
            drevo.buildClassifier(ins);
            Evaluation eval = new Evaluation(ins);
            eval.crossValidateModel(drevo, ins, 10, new Random(1));
            Eval.setText(eval.toSummaryString("-- Rezultat --", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void KlasifikacijaJ482(){
        Instances nonDiscretisizedInstances = result(mojFile);
        Instances ins = null;

        Discretize disc = new Discretize();
        try {
            disc.setInputFormat(nonDiscretisizedInstances);
            ins = Filter.useFilter(nonDiscretisizedInstances, disc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ins.setClassIndex(ins.numAttributes()-1);
        J48 drevo = new J48();
        drevo.setNumFolds(10);
        try {
            drevo.buildClassifier(ins);
            Evaluation eval = new Evaluation(ins);
            eval.crossValidateModel(drevo, ins, 10, new Random(1));
            Eval.setText(eval.toSummaryString("-- Rezultat --", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
