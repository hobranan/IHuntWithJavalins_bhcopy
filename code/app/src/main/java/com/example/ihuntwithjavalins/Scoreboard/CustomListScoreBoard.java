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
/**
 * The CustomListScoreBoard class extends ArrayAdapter of type Player to display a custom list of Players on a scoreboard.
 * It contains a constructor and a getView method to set up and return the view for each item in the list.
 */
public class CustomListScoreBoard extends ArrayAdapter<Player> {

    private ArrayList<Player> cities;
    private Context context;

    public CustomListScoreBoard(Context context, ArrayList<Player> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.context = context;
    }
    /**
     * Method to get the view for each item in the list.
     * @param position the position of the item in the list
     * @param convertView the view to be converted, if possible
     * @param parent the parent ViewGroup that the view will be attached to
     * @return the view for the item at the specified position
     */
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
        TextView player_numcodes = view.findViewById(R.id.num_of_codes);

        player_pos.setText(Integer.toString(position+1));
        player_name.setText(player_details.getUsername());
        player_points.setText(Integer.toString(player_details.getSumOfCodePoints()) + " pts");
        player_numcodes.setText(Integer.toString(player_details.getCodes().size()) + " codes");

        return view;

    }
}
