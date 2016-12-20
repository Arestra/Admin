package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predgled_dogodkov);

        app = (ApplicationMy)getApplication();
        //s=app.getAllData();
        listView = (ListView)findViewById(R.id.idListDogodkov);

        adap = new ActivityPregledDogodkovMainAdapter(getBaseContext(),R.layout.listview_items);
        for(Dogodek d:app.getAllData().getDog()){
            adap.add(d);
        }
        listView.setAdapter(adap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                app.getAllData().brisi(position);
                //Log.d("dogodek",app.getAllData().getDog().toString());
                app.save();
                listView.invalidate();
                adap.notifyDataSetChanged();
                Log.d("dogodek",app.getAllData().getDog().toString());
                //s.getDog().remove(position);
                //Log.d("dogodek",app.getAllData().getDog().toString());
                /*if(app.save()==true){
                    Log.d("dogodek",app.getAllData().getDog().toString());
                    listView.invalidate();
                    adap.notifyDataSetChanged();
                    Toast.makeText(ActivityPredgledDogodkov.this,"Podatki so bili spremenjeni in shranjeni",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ActivityPredgledDogodkov.this,"Podatki niso bili shranjeni",Toast.LENGTH_SHORT).show();*/

                listView.invalidate();
                Intent i = new Intent(getApplication(),ActivityAdmin.class);
                startActivity(i);

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
