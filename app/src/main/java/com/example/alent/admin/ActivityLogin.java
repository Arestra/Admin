package com.example.alent.admin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ActivityLogin extends AppCompatActivity {

    private Button prijava;
    EditText Username;
    EditText Eposta;
    EditText Geslo;
    EditText PonoviGeslo;
    Button dct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText)findViewById(R.id.idUporabnisko);
        Eposta = (EditText)findViewById(R.id.idEmail);
        Geslo = (EditText)findViewById(R.id.idGeslo);
        PonoviGeslo = (EditText)findViewById(R.id.idPonoviGeslo);
        dct = (Button)findViewById(R.id.idStisni);

        dct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityLogin.this,DCT_stiskanjeSlik.class);
                startActivity(i);
            }
        });

        final Button prijava = (Button)findViewById(R.id.idUporabniskoPrijava);

        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Username.getText().toString().trim().length()<=0 && Eposta.getText().toString().trim().length()<=0 && Geslo.getText().toString().trim().length()<=0 && PonoviGeslo.getText().toString().trim().length()<=0) {
                    Toast.makeText(ActivityLogin.this, "Prosimo, izpolnite vsa potrebna polja!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String namestr = Username.getText().toString();
                    String EpostaStr = Eposta.getText().toString();
                    String Geslostr = Geslo.getText().toString();
                    String Geslo2str = PonoviGeslo.getText().toString();


                    if (!Geslostr.equals(Geslo2str)) {
                        Toast pas = Toast.makeText(ActivityLogin.this, "Gesli se ne ujemata!", Toast.LENGTH_SHORT);
                        pas.show();

                    } else if (!validate()) {
                        Intent novoOkno = new Intent(ActivityLogin.this, ActivityAdmin.class);
                        novoOkno.putExtra("Uporabnisko", namestr);
                        novoOkno.putExtra("E-posta", EpostaStr);
                        startActivity(novoOkno);
                        Toast.makeText(ActivityLogin.this, "Prijava uspešna", Toast.LENGTH_SHORT);

                        Username.setText("");
                        Eposta.setText("");
                        Geslo.setText("");
                        PonoviGeslo.setText("");

                    }
                }
            }
        });
    }

    private boolean validate(){
        if(Username.getText().toString().trim().length()<=0){
            Toast.makeText(ActivityLogin.this,"Prosimo, vnesite uporabniško ime!",Toast.LENGTH_SHORT);
            return true;
        }
        else if(Eposta.getText().toString().trim().length()<=0){
            Toast.makeText(ActivityLogin.this,"Prosimo, vnesite e-mail!",Toast.LENGTH_SHORT);
            return true;
        }
        else if(Geslo.getText().toString().trim().length()<=0){
            Toast.makeText(ActivityLogin.this,"Prosimo, vnesite geslo!",Toast.LENGTH_SHORT);
            return true;
        }
        else if(PonoviGeslo.getText().toString().trim().length()<=0){
            Toast.makeText(ActivityLogin.this,"Prosimo, ponovno vnesite geslo!",Toast.LENGTH_SHORT);
            return true;
        }
        else if(Username.getText().toString().trim().length()<=0 && Eposta.getText().toString().trim().length()<=0 && Geslo.getText().toString().trim().length()<=0 && PonoviGeslo.getText().toString().trim().length()<=0){
            Toast.makeText(ActivityLogin.this,"Polja so prazna. Prosimo, vnesite podatke!",Toast.LENGTH_SHORT);
            return true;
        }
        return false;
    }
}
