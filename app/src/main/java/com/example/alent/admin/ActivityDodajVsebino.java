package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityDodajVsebino extends AppCompatActivity {
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private EditText izvajalec;
    private EditText skladba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_vsebino);

        izvajalec = (EditText)findViewById(R.id.txtItem);
        skladba = (EditText)findViewById(R.id.Skladba) ;

        ListView listView1 = (ListView)findViewById(R.id.izvList);
        arrayList1 = new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this,R.layout.item_view,R.id.txtItem,arrayList1);
        listView1.setAdapter(adapter1);

        ListView listView2 = (ListView)findViewById(R.id.sklList);
        arrayList2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this,R.layout.item_view,R.id.txtItem,arrayList2);
        listView2.setAdapter(adapter2);

        final Button dodaj = (Button)findViewById(R.id.idDodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////////////////IZVAJALCI
                String str = izvajalec.getText().toString();
                arrayList1.add(str);
                adapter1.notifyDataSetChanged();
                izvajalec.setText("");

                ////////////////SKLADBE
                String str2 = skladba.getText().toString();
                arrayList2.add(str2);
                adapter2.notifyDataSetChanged();
                skladba.setText("");
            }
        });

        final Button shraniOdlocitev = (Button)findViewById(R.id.idShrani);
        shraniOdlocitev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String izvStr = izvajalec.getText().toString();
                String sklStr = skladba.getText().toString();
                Intent i = new Intent(ActivityDodajVsebino.this,ActivityPredgledDogodkov.class);
                i.putExtra("Naziv",izvStr);
                i.putExtra("Skladba",sklStr);
                startActivity(i);
            }
        });
    }

}
