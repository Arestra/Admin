package com.example.alent.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClassification extends AppCompatActivity {

    private TextView primer;
    private TextView Eval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weka_classification);


    }
    public void Weka(View v) throws Exception {

        primer = (TextView) findViewById(R.id.idWeka);
        Eval = (TextView)findViewById(R.id.idEval);

        Attribute a1 = new Attribute("Tip avta", 0);
        Attribute a2 = new Attribute("Teza", 1);
        Attribute a3 = new Attribute("Moc", 2);
        Attribute a4 = new Attribute("Dolzina", 3);
        Attribute a5 = new Attribute("Sirina", 4);
        Attribute a6 = new Attribute("Prodajna cena", 5);

        FastVector attrs = new FastVector();
        attrs.addElement(a1);
        attrs.addElement(a2);
        attrs.addElement(a3);
        attrs.addElement(a4);
        attrs.addElement(a5);
        attrs.addElement(a6);

        Instance i1  = new Instance(6);
        i1.setValue(a1, 5);
        i1.setValue(a2, 2530);
        i1.setValue(a3, 250);
        i1.setValue(a4, 4.50);
        i1.setValue(a5, 2.30);
        i1.setValue(a6, 140000);

        Instance i2  = new Instance(6);
        i2.setValue(a1, 4);
        i2.setValue(a2, 1990);
        i2.setValue(a3, 290);
        i2.setValue(a4, 5);
        i2.setValue(a5, 2.40);
        i2.setValue(a6, 98000);

        Instance i3  = new Instance(6);
        i3.setValue(a1, 220);
        i3.setValue(a2, 2110);
        i3.setValue(a3, 270);
        i3.setValue(a4, 3.90);
        i3.setValue(a5, 2);
        i3.setValue(a6, 77000);

        Instance i4  = new Instance(6);
        i4.setValue(a1, 70);
        i4.setValue(a2, 1670);
        i4.setValue(a3, 170);
        i4.setValue(a4, 4.50);
        i4.setValue(a5, 1.90);
        i4.setValue(a6, 68000);

        Instances dataset = new Instances("Car Dataset",attrs,4);
        dataset.add(i1);
        dataset.add(i2);
        dataset.add(i3);
        dataset.add(i4);

        dataset.setClassIndex(dataset.numAttributes() - 1);

        LinearRegression lin = new LinearRegression();
        lin.buildClassifier(dataset);

        /*J48 tree = new J48();
        tree.buildClassifier(dataset);*/
        primer.setText(dataset.toSummaryString());

        Evaluation eval = new Evaluation(dataset);
        eval.evaluateModel(lin,dataset);
        Eval.setText(eval.toSummaryString());
    }
}
