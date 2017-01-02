package com.example.alent.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityDodajVsebino extends AppCompatActivity {
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private EditText izvajalec;
    private EditText skladba;
    private TextView test;

    final String myTag = "DocsUpload";
    ApplicationMy app;

    String izv="";
    String skl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_vsebino);

        izvajalec = (EditText) findViewById(R.id.txtItem);
        skladba = (EditText) findViewById(R.id.Skladba);

        ListView listView1 = (ListView) findViewById(R.id.izvList);
        arrayList1 = new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, R.layout.item_view, R.id.txtItem, arrayList1);
        listView1.setAdapter(adapter1);

        ListView listView2 = (ListView) findViewById(R.id.sklList);
        arrayList2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, R.layout.item_view, R.id.txtItem, arrayList2);
        listView2.setAdapter(adapter2);

        final Button dodaj = (Button) findViewById(R.id.idDodaj);
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

        final Button shraniOdlocitev = (Button) findViewById(R.id.idShrani);
        shraniOdlocitev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < arrayList1.size(); i++) {
                    izv += arrayList1.get(i).toString() + "\n";
                }

                for (int i = 0; i < arrayList2.size(); i++) {
                    skl += arrayList2.get(i).toString() + "\n";
                }

                Toast.makeText(ActivityDodajVsebino.this, "Podatki so bili vnešeni in shranjeni!", Toast.LENGTH_SHORT);

                Log.i(myTag, "OnCreate()");
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PostDataToFile(izv, skl);
                    }
                });
                t.start();
                finish();

                Intent i = new Intent(ActivityDodajVsebino.this,ActivityAdmin.class);
                startActivity(i);
            }
        });
    }

    public void PostDataToFile(String IzvNzv, String SklNzv) {
        String URL = "https://docs.google.com/forms/d/e/1FAIpQLSduR3EnYwJMJYMnUYfN2kKg16B5-jQq98Pmdn8cgMV7WULrzA/formResponse";
        com.example.alent.admin.HttpRequest httpRequest = new com.example.alent.admin.HttpRequest();

        String data = "entry.614812540=" + URLEncoder.encode(IzvNzv.toString()) + "&" + "entry.2055276325=" + URLEncoder.encode(SklNzv.toString());
        String response = httpRequest.sendPost(URL, data);
        Log.i(myTag, response);
    }

}
