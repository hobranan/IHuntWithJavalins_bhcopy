package com.example.ihuntwithjavalins.QRCode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<QRCode> {

    private ArrayList<QRCode> cities;
    private Context context;

    public CustomList(Context context, ArrayList<QRCode> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        QRCode city = cities.get(position);

        TextView code_name = view.findViewById(R.id.place);
        TextView code_points = view.findViewById(R.id.name);
        TextView code_date = view.findViewById(R.id.points);

        code_name.setText(city.getCodeName());
        code_points.setText(city.getPoints());
        code_date.setText(city.getDate_scanned());

        return view;

    }
}
