package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Dogodek;
import com.example.Seznam;

import org.json.JSONArray;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class ActivityPredgledDogodkov extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    ListView listView;
    ApplicationMy app;
    Seznam s;
    Dogodek d;
    ActivityPregledDogodkovMainAdapter adap;
    //test
    private ArrayList<String>nekaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predgled_dogodkov);

        app = (ApplicationMy)getApplication();
        s=app.getAllData();
        listView = (ListView)findViewById(R.id.idListDogodkov);

        adap = new ActivityPregledDogodkovMainAdapter(getBaseContext(),R.layout.listview_items);
        for(Dogodek d:app.getAllData().getDog()){
            adap.add(d);
        }
        listView.setAdapter(adap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                app.getAllData().getDog().remove(position);
                app.save();
                Intent i = new Intent(getApplication(),ActivityAdmin.class);
                startActivity(i);
                listView.invalidate();
                //listView.getAdapter().notifyAll();
                //adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.invalidate();
        adap.notifyDataSetChanged();
    }
}
