package com.example.alent.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Dogodek;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlenT on 2.12.2016.
 */

public class ActivityPregledDogodkovMainAdapter extends ArrayAdapter {
    List dogodek = new ArrayList<Dogodek>();
    public ActivityPregledDogodkovMainAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        dogodek.add(object);
    }

    static class DataHandler {
        ImageView slika;
        TextView txtN;
        TextView ura;
        TextView locate;
        TextView Weka;
    }

    @Override
    public Object getItem(int position) {
        return this.dogodek.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_items, parent, false);
            handler = new DataHandler();
            handler.slika = (ImageView) row.findViewById(R.id.idSlika);
            handler.txtN = (TextView) row.findViewById(R.id.idDogodek);
            handler.ura = (TextView) row.findViewById(R.id.urca);
            handler.locate = (TextView) row.findViewById(R.id.location);
            handler.Weka = (TextView)row.findViewById(R.id.weka);
            row.setTag(handler);
            }
            else{
                handler = (DataHandler)row.getTag();
            }

            Dogodek dog;
            dog = (Dogodek)this.getItem(position);
            handler.txtN.setText(dog.getNaziv());
            handler.ura.setText(dog.getCas());
            handler.locate.setText(dog.getKraj());
            handler.Weka.setText(dog.getKlASS());
            return row;
        }

}
