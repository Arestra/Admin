package com.example.alent.admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;


import com.google.common.io.Files;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class DCT_stiskanjeSlik extends AppCompatActivity {

    TextView kompresija;
    TextView razmerje;
    ImageView slika;
    Context context;
    EditText faktorS;


    Button stisni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dct_stiskanje_slik);

        slika = (ImageView) findViewById(R.id.idSlika);
        kompresija = (TextView) findViewById(R.id.idKompresija);
        razmerje = (TextView) findViewById(R.id.idRazmerje);
        stisni = (Button) findViewById(R.id.idStisni);
        faktorS = (EditText)findViewById(R.id.idFaktor);
        context = getApplicationContext();

    }

       public void Stisni(View V) throws IOException {
           int fact = 0; // faktor je na začetku nastavljen na 0
           List<Boolean> field; //ustvarimo polje, kamor shranjujemo vrednosti blokov pred zapisom v datoteko
           List<Boolean> allValues; //definiramo vse vrednosti blokov
           List<List<Boolean>> values; // definiramo polje vseh vrednosti
           List<Byte> byteValues; //definiramo vse vrednosti blokov za byte
           List<short[][]> fieldChunks; // definiramo polje blokov
           short[][] factors; //definiramo faktorje
           short[][][] Blok; //definiramo blok
           short[][] thisBlok; // definiramo trenutni blok
           byte[] visinaSlike; //visina za sliko, preden ji določimo razmerje slike
           byte[] sirinaSlike; //sirina za sliko, preden ji določimo razmerje slike
           int v1 = 0; //spremenljivka za v zanko visine
           int s1 = 0; //spremenljivka za v zanko sirine
           int visina = 8; //visina bloka
           int sirina = 8; // sirina bloka
           int RGB = 3; //barve red, green, blue
           int VS = 64; //sirina * visina bloka -> vrednost skupaj

           int faktorStiskanja = Integer.parseInt(faktorS.getText().toString());
           fact = faktorStiskanja;


                Bitmap picture;
                slika.buildDrawingCache();
                picture = slika.getDrawingCache();
                List<Color> barvniPiksli;
                barvniPiksli = new ArrayList<Color>();
                int i = 0;
                while (i < picture.getWidth())
                { // najprej gremo skozi širino slike
                    for (int z = 0; z < picture.getHeight(); z++)
                    {                   // nato skozi višino
                        int visinas = picture.getHeight();
                        int sirinas = picture.getWidth();
                        //Toast.makeText(DCT_stiskanjeSlik.this, "Visina: " + visinas + "Sirina: " + sirinas, Toast.LENGTH_SHORT).show();
                        int barva = picture.getPixel(i, z);
                        int redValue = Color.red(barva);
                        int blueValue = Color.blue(barva);
                        int greenValue = Color.green(barva);
                        Color barva2 = new Color();
                        barva2.rgb(redValue, greenValue, blueValue);
                        barvniPiksli.add(barva2); //nato pa vse vrednosti pikslov zapišemo v listo
                    }
                    i++;
                }

               for(;barvniPiksli.size() % 64 != 0;){
                   Color crna = new Color();
                   barvniPiksli.add(crna);
               }
               //////////////////////////////////////////////DOLOČIMO FAKTOR STISKANJA (STEP 5)

               short zacasni, zacetek = 15;
               factors = new short[visina][sirina]; //faktorji

               int st = 0;
               while (st < 8)
               { //gremo skozi sirino bloka
                   zacasni = zacetek;
                   for (int x = 0; x < 8; x++)
                   { //skozi visino bloka
                       factors[st][x] = zacasni;
                       zacasni--; // potem pa za vsak blok zmanjšujemo vrednosti
                   }
                   zacetek--; //gremo na naslednjega
                   st++;
               }
               ////////////////////////////////////////////////////////////////////ZA VSAKO BARVO BLOK, DODANO ŠE ODŠTEVANJE VREDNOSTI (STEP 2 and 3)

               //ZA BLOKE ZA POSAMEZNO BARVO
               List<short[][][]> chunks = new ArrayList<short[][][]>();
               //int vrednost = 64;
               int stolpec = 0;
               int vrstica = 0;
               int velikostBloka = 7;
               Blok = new short[visina][sirina][RGB]; // sirina, visina in barva posameznega bloka

               int j = 0;
               while (j < barvniPiksli.size()) {

                   if (j % 64 == 0 && j!=0)
                   {

                       chunks.add(Blok); //shranmo prvi blok
                       Blok = new short[visina][sirina][RGB]; // nato naredimo oz. delamo novega
                       stolpec = 0; vrstica = 0;


                       Blok[stolpec][vrstica][0] = (short)(barvniPiksli.get(j).RED - 128); //RED
                       Blok[stolpec][vrstica][1] = (short)(barvniPiksli.get(j).GREEN - 128); //GREEN
                       Blok[stolpec][vrstica][2] = (short)(barvniPiksli.get(j).BLUE - 128); //BLUE

                       if (vrstica < velikostBloka) { //pomeni da je nov v vrsti
                           vrstica++;
                       }
                       else{ // pomeni da je nov V stolpec
                           vrstica = 0;
                           stolpec++;
                       }

                   }
                   else
                   {   //če se blok ne shrani, potem ga ustvarimo, hkrati pa mu odštejemo tudi vrednosti - 128
                       Blok[stolpec][vrstica][0] = (short)(barvniPiksli.get(j).RED - 128); //RED
                       Blok[stolpec][vrstica][1] = (short)(barvniPiksli.get(j).GREEN - 128); //GREEN
                       Blok[stolpec][vrstica][2] = (short)(barvniPiksli.get(j).BLUE - 128); //BLUE

                       if (vrstica < velikostBloka){ //pomeni da je nov v vrsti
                           vrstica++;
                       }
                       else{ // pomeni da je nov v stolpcu
                           vrstica = 0;
                           stolpec++;
                       }
                   }
                   j++;
               }
               chunks.add(Blok); //tukaj pa v listo dodamo še tazadni blok

               ////////////////////////////////////////////////////////// FDCT (DISKRETNA KOSINUSNA TRANSFORMACIJA) (STEP 4)
               for (int x = 0; x < chunks.size(); x++)
               {
                   Blok = chunks.get(x);
                   short[][][] newChunk = new short[visina][sirina][RGB];
                   double rez;
                   rez = 1 / Math.sqrt(2);

                   for (int y = 0; y < 8; y++) //sirina bloka
                   {
                       for (int z = 0; z < 8; z++) //visina bloka
                       {
                           if (factors[y][z] < fact) //če so zgoraj določeni faktorji posameznega bloka manjši od faktorja, katerega podamo sami
                           {
                               //postavimo vrednosti vseh blokov na 0 (5. KORAK)
                               newChunk[y][ z][ 0] = (short)0;
                               newChunk[y][ z][ 0] = (short)0;
                               newChunk[y][ z][ 0] = (short)0;
                           }
                           else { //če ne pa (IZPOLNIMO 4 KORAK)
                           double kosinusna1;
                           double kosinusna2;

                           if (y!=0) {
                               kosinusna1 = 1;
                           }
                           else
                               kosinusna1 = rez;

                           if (z!=0){
                               kosinusna2 = 1;
                           }
                           else
                               kosinusna2 = rez;

                           double getRed = 0;
                           double getGreen = 0;
                           double getBlue = 0;


                           for (int u=0;u < 8;u++) {

                               for (int v=0;v < 8;v++) { // za vsako vrednost posameznega bloka izračunamo vrednost barve oz. pikslov bloka po formuli FDCT, ki je podana na spletni strani

                                   getRed = getRed + Blok[u][ v][ 2] * Math.cos(((2 * u + 1) * y * Math.PI) / 16) *
                                           Math.cos(((2 * v + 1) * z * Math.PI) / 16); // za modro barvo

                                   getGreen = getGreen + Blok[u][ v][ 2] * Math.cos(((2 * u + 1) * y * Math.PI) / 16) *
                                           Math.cos(((2 * v + 1) * z * Math.PI) / 16); // za modro barvo

                                   getBlue = getBlue + Blok[u][ v][ 2] * Math.cos(((2 * u + 1) * y * Math.PI) / 16) *
                                           Math.cos(((2 * v + 1) * z * Math.PI) / 16); // za modro barvo

                               }
                           }//nato vrednosti shranimo v posamezen blok -> pomnožimo z vsotami, ki smo jih izračunali zgoraj
                           double stev = 0.25;
                           newChunk[y][z][0] = (short)Math.round((stev * kosinusna1 * kosinusna2 * getRed)); newChunk[y][ z][ 1] = (short)Math.round((stev * kosinusna1 * kosinusna2 * getGreen)); newChunk[y][ z][ 2] = (short)Math.round((stev * kosinusna1 * kosinusna2 * getBlue));
                       }
                       }
                   }
                   chunks.set(x, newChunk);
               }
               ////////////////////////////////////////////////////CIK - CAK ------> (STEP 6)

               fieldChunks = new ArrayList<short[][]>();
               short OVERFLOW = 2033; // vrednost overflow je nastavljena na 2033
               int N = 7;
               for (int x = 0; x < chunks.size(); x++) // gremo skozi celotno listo blokov, v katere smo sproti shranjevali vrednosti
               {
                   Blok = chunks.get(x); // nastavimo vrednosti posameznega bloka
                   boolean end = false;
                   int st1 = 0;
                   int st2 = 0;
                   int ind = 0;
                   short[][] newField = new short[VS][RGB]; // blok ima 8*8 = 64 vrednosti, hkrati pa 3 vrednosti vsote barv (R,G,B)


                   for (; end==false;)
                   {
                       newField[ind][0] = Blok[st2][ st1][ 0]; //0 -> rdeca
                       newField[ind][1] = Blok[st2][ st1][ 1]; //0 -> rdeca
                       newField[ind][2] = Blok[st2][ st1][ 2]; //0 -> rdeca

                       Blok[st2] [st1][ 0] = OVERFLOW; // vse vrednosti bloka damo na 2033 (R)
                       Blok[st2] [st1] [1] = OVERFLOW; // vse vrednosti bloka damo na 2033 (G)
                       Blok[st2] [st1] [2] = OVERFLOW; // vse vrednosti bloka damo na 2033 (B)



                       if (st1 > 0 && st2 < N && Blok[st2 + 1][st1 - 1][0] < OVERFLOW) { // lahko gre levo dol
                       st1--; st2++;
                   }
                       else
                       if (st1 < N && st2 > 0 && Blok[st2 - 1][st1 + 1][0] < OVERFLOW){ // lahko gre desno gor
                       st1++; st2--;
                   }
                       else
                       if (st1 > 0 && st1 < N){// lahko gre desno in ni v 1. stolpcu
                           st1++;
                       }
                       else
                       if (st2 > 0 && st2 < N){// lahko gre dol in ni v 1. vrstici
                           st2++;
                       }
                       else
                       if (st1 < N){// lahko gre desno (in je v 1. stolpcu)
                           st1++;
                       }
                       else{
                           end = true;
                       }
                       ind++;
                   }
                   fieldChunks.add(newField); //dodamo vrednosti v listo
               }
               ///////////////////////////////////////////////RUN LENGHT ENCODE -> TEKOČA DOLŽINA (STEP 7)

               values = new ArrayList<List<Boolean>>();
               int dolzina = 4;
               int tekocaDolzina = 6;


               for (int x = 0; x < fieldChunks.size(); x++) // skozi vsak blok (posamezen)
               {
                   thisBlok = fieldChunks.get(x); //imamo 64 vrednosti (8*8, +3 barve)

                   List<Boolean> thisValues = new ArrayList<Boolean>();
                   Boolean ENICE = true;
                   Boolean NICLE = false;

                   int value = 3;

                   for (int y = 0; y < value; y++)
                   {
                       int zeroes = 0;
                       for (int z = 0; z < 64; z++)
                       {
                           if (z==(0)) ///tip D
                           {

                               if (thisBlok[z][y] <= 0) //če je negativen blok, ima 10 bitov +1
                               {
                                   String binary;
                                   int value2 = -1;
                                   binary = Integer.toString((value2) * thisBlok[z][y], 2);

                                   for (; binary.length() < 10;){
                                       binary = new StringBuilder(binary).insert(0, "0").toString();
                                   }

                                   thisValues.add(ENICE);

                                   for (int c = 0; c < 10; c++)
                                   {
                                       if (binary.charAt(c)=='0'){ // če je binarna vrednost ennaka vrednosti 1
                                           thisValues.add(NICLE);//dodamo vrednost 0

                                       }
                                       else
                                           thisValues.add(ENICE); //dodamo vrednost 1
                                   }
                               }
                               else //pozitiven blok
                               {
                                   String binary;
                                   binary = Integer.toString(thisBlok[z][y], 2);

                                   for (; binary.length() < 11;)
                                   {
                                       binary = new StringBuilder(binary).insert(0, "0").toString();
                                   }

                                   for (int c = 0; c < 11; c++)
                                   {
                                       if (binary.charAt(c)=='0'){ // če je binarna vrednost ennaka vrednosti 1
                                           thisValues.add(NICLE);//dodamo vrednost 1
                                       }
                                       else
                                           thisValues.add(ENICE);//dodamo vrednost 0
                                   }
                               }
                           }
                           else
                           {
                               if (thisBlok[z][y]==0){ // tukaj štejemo vsoto vseh ničel
                               zeroes++;
                           }
                               else
                               {
                                   if (zeroes==0) /////////tip C
                                   {
                                       thisValues.add(ENICE);// dodamo true -> pomeni da dodamo 1 bit za tip
                                       int bitov = 1;

                                       for (; true;)
                                       {
                                           int maksimalno;                   //////////POTREBNO ŠTEVILO BITOV
                                           maksimalno = ((int)Math.pow(2, bitov) - 1) / 2;
                                           int minimalno;
                                           minimalno = -((int)Math.pow(2, bitov) / 2);

                                           if (thisBlok[z][y] < maksimalno && thisBlok[z][y] > minimalno){
                                           break;
                                       }
                                           else
                                           bitov++;
                                       }


                                       String neededBites;
                                       neededBites = Integer.toString(bitov, 2);

                                       for (; neededBites.length() < dolzina;){ // dolžina -> 4 biti
                                           neededBites = new StringBuilder(neededBites).insert(0, "0").toString();
                                       }

                                       for (int w = 0; w < dolzina; w++)
                                       {
                                           if (neededBites.charAt(w)=='0'){
                                               thisValues.add(NICLE); //dodamo vrednost 1
                                           }
                                           else
                                               thisValues.add(ENICE); //dodamo vrednost 0
                                       }

                                       if (thisBlok[z][y] <= 0) //negativno stevilo potrebnih bitov
                                       {
                                           String binary;
                                           int vrednost2 = -1;
                                           binary = Integer.toString((vrednost2) * thisBlok[z][y], 2);

                                           for (; binary.length() < bitov - 1;){
                                               binary = new StringBuilder(binary).insert(0, "0").toString();
                                           }
                                           thisValues.add(ENICE);

                                           for (int w = 0; w < bitov - 1; w++)
                                           {
                                               if (binary.charAt(w)=='0'){
                                                   thisValues.add(NICLE);
                                               }
                                               else
                                                   thisValues.add(ENICE);
                                           }
                                       }
                                       else // pozitivno stevilo bitov -> dolžina bitov
                                       {
                                           String binary;
                                           binary = Integer.toString(thisBlok[z][y], 2);

                                           for (; binary.length() < bitov;){
                                               binary = new StringBuilder(binary).insert(0, "0").toString();
                                           }

                                           for (int w = 0; w < bitov; w++)
                                           {
                                               if (binary.charAt(w)=='0'){
                                                   thisValues.add(NICLE);
                                               }
                                               else
                                                   thisValues.add(ENICE);
                                           }
                                       }
                                   }
                                   else //tukaj se preverjamo za tekočo dolžino (6 bitov za vrednosti)
                                   //nič (0) bitov za en tip
                                   {
                                       /////////////// tip A
                                       thisValues.add(NICLE);
                                       String binary;
                                       binary = Integer.toString(zeroes, 2);

                                       for (; binary.length() < tekocaDolzina;) {
                                           binary = new StringBuilder(binary).insert(0, "0").toString();
                                       }

                                       for (int w = 0; w < tekocaDolzina; w++)
                                       {
                                           if (binary.charAt(w)=='0'){
                                               thisValues.add(NICLE);
                                           }
                                           else
                                               thisValues.add(ENICE);
                                       }

                                       int bitov = 1; //št. bitov za zapisovanje vrednosti
                                       for (; true;)
                                       {
                                           int minimalno;
                                           int maksimalno;                     //////////POTREBNO ŠTEVILO BITOV
                                           maksimalno = ((int)Math.pow(2, bitov) - 1) / 2;
                                           minimalno = -((int)Math.pow(2, bitov) / 2);

                                           if (thisBlok[z][y] < maksimalno && thisBlok[z][y] > minimalno){
                                           break;
                                       }
                                           else
                                           bitov++;
                                       }

                                       String neededBites;
                                       neededBites = Integer.toString(bitov, 2);
                                       //(dolžina-> 4 biti)
                                       for (; neededBites.length() < dolzina;){
                                           binary = new StringBuilder(binary).insert(0, "0").toString();
                                       }

                                       for (int w = 0; w < dolzina; w++)
                                       {
                                           if (neededBites.charAt(w)=='0'){
                                               thisValues.add(NICLE);
                                           }
                                           else
                                               thisValues.add(ENICE);
                                       }

                                       if (thisBlok[z][y] <= 0) //negativno število bitov
                                       {
                                           int vrednost2 = -1;
                                           binary = Integer.toString((vrednost2) * thisBlok[z][y], 2);

                                           for (; binary.length() < bitov - 1;){
                                               binary = new StringBuilder(binary).insert(0, "0").toString();
                                           }

                                           thisValues.add(ENICE);
                                           for (int w = 0; w < bitov - 1; w++)
                                           {
                                               if (binary.charAt(w)=='0'){
                                                   thisValues.add(NICLE);
                                               }
                                               else
                                                   thisValues.add(ENICE);
                                           }
                                       }
                                       else //pozitivno število bitov
                                       {
                                           binary = Integer.toString(thisBlok[z][y], 2);

                                           for (; binary.length() < bitov;){
                                               binary = new StringBuilder(binary).insert(0, "0").toString();
                                           }

                                           for (int w = 0; w < bitov; w++)
                                           {
                                               if (binary.charAt(w)=='0'){
                                                   thisValues.add(NICLE);
                                               }
                                               else
                                                   thisValues.add(ENICE);
                                           }
                                       }
                                       zeroes = 0;
                                   }
                               }
                           }
                       }
                       /////////////////////za tip B
                       if (zeroes > 0) // tukaj smo prišli na konec enega (oz. posameznega) bloka.
                       {
                           thisValues.add(NICLE);
                           String binary;
                           binary = Integer.toString(zeroes, 2);

                           for (; binary.length() < tekocaDolzina;) { // 6-> bitov -> računanje vrednosti tekoče dolžine
                               binary = new StringBuilder(binary).insert(0, "0").toString();
                           }

                           for (int w = 0; w < tekocaDolzina; w++)
                           {
                               if (binary.charAt(w)=='0'){
                                   thisValues.add(NICLE);
                               }
                               else
                                   thisValues.add(ENICE);
                           }
                       }
                   }
                   values.add(thisValues); // dodamo vse vrednosti v listo (tukaj se konča posamezen blok z posameznimi barvami)
               }

               field = new ArrayList<Boolean>();
               allValues = new ArrayList<Boolean>(); //ustvarimo listo, kamor bomo shranjevali vse vrednosti oz. podatke blokov
               byteValues = new ArrayList<Byte>();
               int vrednostBloka = 0;
               short leftOver = 0;



               byte BMP;
               int stevilka= 0;
               BMP = (byte)stevilka;
               byteValues.add(BMP);

                   byte[] writeBuffer = new byte[4];
                   writeBuffer[3] = (byte) ((picture.getHeight() >>> 24) & 0xFF);
                   writeBuffer[2] = (byte) ((picture.getHeight() >>> 16) & 0xFF);
                   writeBuffer[1] = (byte) ((picture.getHeight() >>> 8) & 0xFF);
                   writeBuffer[0] = (byte) ((picture.getHeight() >>> 0) & 0xFF);


               visinaSlike = writeBuffer;


               byte[] writeBuffer2 = new byte[4];
               writeBuffer2[3] = (byte) ((picture.getWidth() >>> 24) & 0xFF);
               writeBuffer2[2] = (byte) ((picture.getWidth() >>> 16) & 0xFF);
               writeBuffer2[1] = (byte) ((picture.getWidth() >>> 8) & 0xFF);
               writeBuffer2[0] = (byte) ((picture.getWidth() >>> 0) & 0xFF);

               sirinaSlike = writeBuffer2;//ter sirino posamezne slike

               while (v1 < visinaSlike.length) {
                   byteValues.add(visinaSlike[v1]);//zapisemo vrednosti po visini slike (torej, vse ki so navpicno)
                   v1++;
               }

               while (s1 < sirinaSlike.length){
                   byteValues.add(sirinaSlike[s1]);//zapisemo vrednosti po sirini slike (torej, vse ki so horizontalno)
                   s1++;
               }

               for (int w = 0; w < values.size(); w++) {

                   for (int y = 0; y < values.get(w).size(); y++) {
                       allValues.add(values.get(w).get(y));//dodamo
                   }
               }

               for (;allValues.size() % 8 != 0;) { //dodamo same ničle, če vrednosti niso deljive ali množljive z 8 (če je drugačno od vrednosti 64)
                   allValues.add(false); //same 0

                   leftOver++;
               }

               byteValues.add((byte)leftOver); // nato to pretvorimo in hkrati tudi dodamo v listo k vsem vrednostim

               for (int w = 0; w < allValues.size(); w++){

                   if (vrednostBloka >= 8){ /////////////////če je vrednost večja od potrebnega, nato uporabimo funkcijo ki pretvori stringe v BYTE

                       String n = "";
                       byte rezultat;
                       for (int y = 0; y < field.size(); y++) {

                           if (field.get(y)){
                               n = n + "1";
                           }
                           else{
                               n = n + "0";
                           }
                       }
                       rezultat = Byte.parseByte(n,2);
                       byteValues.add(rezultat);

                       field = new ArrayList<Boolean>();
                       vrednostBloka = 0;
                   }
                   else
                   {
                       field.add(allValues.get(w)); //če ne pa dodamo k ostalim vrednostim
                       vrednostBloka++;
                   }
               }
                  File file = new File(context.getFilesDir(),"DCT.bin");
                  FileOutputStream izven = new FileOutputStream(file);
                  izven.write(byteValues.size());
                  izven.flush();

                  kompresija.setText("Najdena je bila: " + izven.getChannel().size()+ " datoteka (DCT.bin)");
                  double prva = 31009;
                  double ratio;
                  ratio =((izven.getChannel().size()*100) / prva );
                  razmerje.setText("Razmerje je: " + ratio + "%");

                  izven.close();

           Toast.makeText(DCT_stiskanjeSlik.this,"Zapisovanje je končano!",Toast.LENGTH_SHORT).show();
       }
}
