package com.example;

public class MyClass {
    public static void main(String arg[])
    {
        System.out.println("Pozdravljeni v spletni aplikaciji!!");
        Dogodek novD = new Dogodek(1,"Znana harmonika ima svoj glas","Maribor","Stuk","Sobota(1.4.2017)","Koncert","7€","Dober");

        System.out.println(novD);

        Seznam szn = new Seznam();

        szn.dodaj(novD);
        szn.dodaj(new Dogodek(2,"Vsaka nota nekaj zapoje","Slovenska Bistrica","Kava bar Furci","Petek(2.5.2018)","Party z harmoniko","7€","sadsd"));
        System.out.println(szn);
    }
}
