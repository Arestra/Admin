package com.example.alent.admin;

import android.database.DataSetObservable;
import android.provider.CalendarContract;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;

/**
 * Created by AlenT on 2.12.2016.
 */

public class WekaLoadSaveData {
    public static void main(String args[])throws Exception{
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("D:/3.LETNIK/1.SEMESTER/STROJNO UČENJE IN ISKANJE NOVEGA ZNANJA/Projektna naloga/100 INSTANC/DogodkiNaSlovenskem.arff");
        Instances dataset = source.getDataSet();

        System.out.println(dataset.toSummaryString());

        dataset.setClassIndex(dataset.numAttributes()-1);
        J48 drevo = new J48();
        drevo.buildClassifier(dataset);
        Evaluation eval = new Evaluation(dataset);


        /*ArffSaver saver = new ArffSaver();
        saver.setInstances(dataset);
        saver.setFile(new File("D:/3.LETNIK/1.SEMESTER/STROJNO UČENJE IN ISKANJE NOVEGA ZNANJA/Projektna naloga/ARFF Android/OrganizacijaDogodkov.arff"));
        saver.writeBatch();*/

        ConverterUtils.DataSource vir = new ConverterUtils.DataSource("D:/3.LETNIK/1.SEMESTER/STROJNO UČENJE IN ISKANJE NOVEGA ZNANJA/Projektna naloga/ARFF Android/OrganizacijaDogodkov.arff");
        Instances dataset2 = vir.getDataSet();
        dataset2.setClassIndex(dataset2.numAttributes()-1);
        eval.evaluateModel(drevo,dataset2);

        System.out.println(eval.toSummaryString("Evaluation results:/n",false));

        System.out.println("Correct % = "+eval.pctCorrect());
        System.out.println("Incorrect % = "+eval.pctIncorrect());
        System.out.println("AUC % = "+eval.areaUnderROC(1));
        System.out.println("kappa % = "+eval.kappa());
        System.out.println("MAE % = "+eval.meanAbsoluteError());
        System.out.println("RMSE % = "+eval.rootMeanSquaredError());
        System.out.println("RAE % = "+eval.relativeAbsoluteError());
        System.out.println("RRSE % = "+eval.rootRelativeSquaredError());
        System.out.println("Precission % = "+eval.precision(1));
        System.out.println("Recall % = "+eval.recall(1));
        System.out.println("fMeasure % = "+eval.fMeasure(1));
        System.out.println("Error Rate % = "+eval.errorRate());

        System.out.println(eval.toMatrixString("=== Overall Confusion Matrix ===/n"));
    }
}
