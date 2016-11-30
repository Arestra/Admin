package com.example.alent.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityPredgledDogodkov extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predgled_dogodkov);



        ListView listView = (ListView)findViewById(R.id.idListDogodkov);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.listview_items,R.id.idDogodek,arrayList);
        listView.setAdapter(adapter);

        String nazivStr = getIntent().getStringExtra("NazivDogodka");


        arrayList.add(nazivStr);


    }
}
