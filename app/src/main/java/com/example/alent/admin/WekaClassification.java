package com.example.alent.admin;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Remove;


public class WekaClassification extends AppCompatActivity {

    Instances dataset;
    Evaluation eval;
    private TextView primer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weka_classification);
        //primer = (TextView) findViewById(R.id.idWeka);
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

    }
    public void Result(View v) throws Exception {

        //Button rezultat = (Button)findViewById(R.id.idRes);

        Attribute a1 = new Attribute("Tip_dogodka",0); //1 -> pop, 2-> rock, 3->Narodno-zabavni, 4-> Hip-Hop, 5-> Classic
        Attribute a2 = new Attribute("Lokal",1); // 1-> Disco_Planet, 2--> Stuk, 3-> Pub_Beli_Konj, 4-> Na odprtem-> 6 -> Plus_Minus, 7-> Stuk, 8-> Trust
        Attribute a3 = new Attribute("Pricetek",2);//1 -> Dopoldan, 2-> Popoldan, 3-> Zvecer
        Attribute a4 = new Attribute("Cena",3);// 1-> Brezplacno, 2--> 3€, 3-> 5€, 4-> 10€-> 6 -> 15€, 7-> 20€
        Attribute a5 = new Attribute("Lokacija",4); // 1-> Celje, 2--> Maribor, 3-> Slovenske Konjice, 4-> Slovenska Bistrica-> 6 -> Šentjur
        Attribute a6 = new Attribute("Udelezba",5); // 1-> 1x, 2-> 2x, 3 -> 3x, 4-> večkrat
        Attribute a7 = new Attribute("Ocena dogodka",6); // 1-> 1, 2--> 2, 3-> 3, 4->4, 5-> 5, 6 -> 6, 7-> 7, 7->7
        Attribute a8 = new Attribute("Klas",7); // 1-> Dober, 2--> Povprecen, 3-> Priporocljiv

        FastVector attrs = new FastVector();
        attrs.addElement(a1);
        attrs.addElement(a2);
        attrs.addElement(a3);
        attrs.addElement(a4);
        attrs.addElement(a5);
        attrs.addElement(a6);
        attrs.addElement(a7);
        attrs.addElement(a8);

        Instance i1  = new Instance(8);
        i1.setValue(a1, 5);
        i1.setValue(a2, 2);
        i1.setValue(a3, 3);
        i1.setValue(a4, 7);
        i1.setValue(a5, 4);
        i1.setValue(a6, 1);
        i1.setValue(a7, 6);
        i1.setValue(a8, 2);

        Instance i2  = new Instance(8);
        i2.setValue(a1, 4);
        i2.setValue(a2, 3);
        i2.setValue(a3, 6);
        i2.setValue(a4, 1);
        i2.setValue(a5, 2);
        i2.setValue(a6, 4);
        i2.setValue(a7, 3);
        i2.setValue(a8, 5);

        Instance i3  = new Instance(8);
        i3.setValue(a1, 4);
        i3.setValue(a2, 3);
        i3.setValue(a3, 6);
        i3.setValue(a4, 1);
        i3.setValue(a5, 2);
        i3.setValue(a6, 4);
        i3.setValue(a7, 3);
        i3.setValue(a8, 5);

        Instance i4  = new Instance(8);
        i4.setValue(a1, 4);
        i4.setValue(a2, 3);
        i4.setValue(a3, 6);
        i4.setValue(a4, 1);
        i4.setValue(a5, 2);
        i4.setValue(a6, 4);
        i4.setValue(a7, 3);
        i4.setValue(a8, 5);

        Instance i5  = new Instance(8);
        i5.setValue(a1, 4);
        i5.setValue(a2, 3);
        i5.setValue(a3, 6);
        i5.setValue(a4, 1);
        i5.setValue(a5, 2);
        i5.setValue(a6, 4);
        i5.setValue(a7, 3);
        i5.setValue(a8, 5);

        Instance i6  = new Instance(8);
        i6.setValue(a1, 4);
        i6.setValue(a2, 3);
        i6.setValue(a3, 6);
        i6.setValue(a4, 1);
        i6.setValue(a5, 2);
        i6.setValue(a6, 4);
        i6.setValue(a7, 3);
        i6.setValue(a8, 5);

        Instance i7  = new Instance(8);
        i7.setValue(a1, 4);
        i7.setValue(a2, 3);
        i7.setValue(a3, 6);
        i7.setValue(a4, 1);
        i7.setValue(a5, 2);
        i7.setValue(a6, 4);
        i7.setValue(a7, 3);
        i7.setValue(a8, 5);

        Instance i8  = new Instance(8);
        i8.setValue(a1, 4);
        i8.setValue(a2, 3);
        i8.setValue(a3, 6);
        i8.setValue(a4, 1);
        i8.setValue(a5, 2);
        i8.setValue(a6, 4);
        i8.setValue(a7, 3);
        i8.setValue(a8, 5);

        Attribute aa1 = new Attribute(tipDog.getText().toString());
        Attribute aa2 = new Attribute(lokalDog.getText().toString());
        Attribute aa3 = new Attribute(pricetekDog.getText().toString());
        Attribute aa4 = new Attribute(cenaDog.getText().toString());
        Attribute aa5 = new Attribute(lokacijaDog.getText().toString());
        Attribute aa6 = new Attribute(udelezbaDog.getText().toString());
        Attribute aa7 = new Attribute(ocenaDog.getText().toString());
        Attribute aa8 = new Attribute(clasDog.getText().toString());

        FastVector attrs2 = new FastVector();
        attrs2.addElement(aa1);
        attrs2.addElement(aa2);
        attrs2.addElement(aa3);
        attrs2.addElement(aa4);
        attrs2.addElement(aa5);
        attrs2.addElement(aa6);
        attrs2.addElement(aa7);
        attrs2.addElement(aa8);


        /*Instance i10 = new Instance(8);
        i10.setValue();*/

        //if(tipDog.getText().toString() && lokalDog.getText().toString() && pricetekDog.getText().toString() && cenaDog.getText().toString() && lokacijaDog.getText().toString() && udelezbaDog.getText().toString() && ocenaDog.getText().toString() && clasDog.getText().toString().equals()  )


        dataset = new Instances("Dogodki na Slovenskem",attrs,0);
        dataset.add(i1);
        dataset.add(i2);
        dataset.add(i3);
        dataset.add(i4);
        dataset.add(i5);
        dataset.add(i6);
        dataset.add(i7);
        dataset.add(i8);

        dataset.setClassIndex(dataset.numAttributes() - 1);

        String[] options = new String[]{"-B", "10-M-1.0-R"};
        Discretize dis = new Discretize();
        dis.setOptions(options);
        dis.setInputFormat(dataset);
        Instances newData = Filter.useFilter(dataset, dis);
        J48 drevo = new J48();

        drevo.setOptions(options);
        drevo.buildClassifier(newData);

        //primer.setText(dataset.toSummaryString());*/

        /*LinearRegression lin = new LinearRegression();
        lin.buildClassifier(newData);*/

        eval = new Evaluation(newData);
        eval.crossValidateModel(drevo,newData,8,new Random(7));
        Eval.setText(eval.toSummaryString("\n -- Rezultat -- \n", false));

        /*rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WekaClassification.this,ResultWeka.class);
                i.putExtra("Result",dataset);
                startActivity(i);
            }
        });*/

        /*ConverterUtils.DataSource source = new ConverterUtils.DataSource("C:\\Users\\AlenT\\Desktop\\Admin\\app\\src\\main\\assets\\Motorbike Dataset Original.arff");
        Instances dataset2 = source.getDataSet();

        dataset2.setClassIndex(dataset2.numAttributes()-1);

        try{
            J48 tree = new J48();
            tree.buildClassifier(dataset2);
            Evaluation evaluation = new Evaluation(dataset2);
            evaluation.evaluateModel(tree,dataset2);
            primer.setText(tree.toSummaryString());
            Eval.setText(evaluation.toSummaryString());
        }catch(IOException d){
            d.printStackTrace();
        }*/

        /*String input = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ARFF Android/OrganizacijaDogodkov.arff";
        BufferedReader bralec = new BufferedReader(new FileReader(input));

        /*ArffLoader loader = new ArffLoader();
        loader.setFile(new File("/some/where/data.arff"));

        //ConverterUtils.DataSource source = new ConverterUtils.DataSource("D:/3.LETNIK/1.SEMESTER/STROJNO UČENJE IN ISKANJE NOVEGA ZNANJA/Projektna naloga/100 INSTANC/DogodkiNaSlovenskem.arff");


        /*ArffLoader loader = new ArffLoader();
        loader.setFile(new File("D:/3.LETNIK/1.SEMESTER/STROJNO UČENJE IN ISKANJE NOVEGA ZNANJA/Projektna naloga/100 INSTANC/DogodkiNaSlovenskem.arff"));

        Instances dataset2 = new Instances(bralec);
        dataset2.setClassIndex(dataset2.numAttributes()-1);

        J48 tree = new J48();
        tree.buildClassifier(dataset2);
        Evaluation evaluation = new Evaluation(dataset2);
        evaluation.evaluateModel(tree,dataset2);
        primer.setText(tree.toSummaryString());
        Eval.setText(evaluation.toSummaryString());*/


        /*String pot = Environment.getDataDirectory()+"/storage/emulated/0/storage/emulated/0/data.arff";
        Log.d("Pot: ",pot);

        /*String pot2 = context.getFilesDir()+"/storage/emulated/0/storage/emulated/0/" + "data.arff";
        File dat = new File(pot2);

        //FileInputStream fis = context.openFileInput("DogodkiNaSlovenskem.arff",Context.MODE_PRIVATE);
        Log.d("Pot ",pot);

        BufferedReader bralec = new BufferedReader(new FileReader(pot));

        String imeD = "/storage/emulated/0/storage/emulated/0/data.arff";

        FileInputStream fis = context.openFileInput(imeD);
        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
        BufferedReader bralec = new BufferedReader(isr);

        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(bralec);
        Instances data = arff.getData();
        bralec.close();

        data.setClassIndex(data.numAttributes()-1);

        String[] opcije = new String[1];
        opcije[0] = "-U";
        J48 drevo = new J48();
        drevo.setOptions(opcije);

        drevo.buildClassifier(data);

        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(drevo, data, 10, new Random(1));

        primer.setText(eval.toSummaryString());*/
    }
}
