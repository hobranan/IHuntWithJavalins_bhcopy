package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;

public class CustomListScoreBoard extends ArrayAdapter<Player> {

    private ArrayList<Player> cities;
    private Context context;

    public CustomListScoreBoard(Context context, ArrayList<Player> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.scoreboard_content, parent,false);
        }

        Player player_details = cities.get(position);

        TextView player_pos = view.findViewById(R.id.place);
        TextView player_name = view.findViewById(R.id.name);
        TextView player_points = view.findViewById(R.id.points);

        player_pos.setText(Integer.toString(position+1));
        player_name.setText(player_details.getUsername());
        player_points.setText(Integer.toString(player_details.getTotalCodes()));

        return view;

    }
}
