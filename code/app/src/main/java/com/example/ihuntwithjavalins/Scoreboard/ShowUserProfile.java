package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.R;

public class ShowUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);
        ImageButton go_back = findViewById(R.id.go_back_btn_);

        Intent myIntent = getIntent();
        UserProfileDetails player_details = (UserProfileDetails) myIntent.getSerializableExtra("PlayerDetails");
        TextView p_name = findViewById(R.id.player_name_);
        p_name.setText(player_details.getPlayerName());

        TextView p_date = findViewById(R.id.date_joined_);
        p_date.setText(player_details.getDateJoined());

        TextView p_email = findViewById(R.id.player_email);
        p_email.setText(player_details.getEmail());

        TextView p_region = findViewById(R.id.player_region);
        p_region.setText(player_details.getRegion());

        TextView p_total_points = findViewById(R.id.player_tot_points);
        p_total_points.setText(player_details.getTotalPoints());

        TextView p_total_points_placing = findViewById(R.id.total_points_placing);
        p_total_points_placing.setText(player_details.getTotalPointsPlacing());

        TextView p_total_codes = findViewById(R.id.player_total_codes);
        p_total_codes.setText(player_details.getTotalCodes());


        TextView p_total_codes_placing = findViewById(R.id.player_tot_codes_placing);
        p_total_codes_placing.setText(player_details.getTotalCodesPlacing());

        TextView p_highest_code = findViewById(R.id.player_highest_code_val);
        p_highest_code.setText(player_details.getHighestCodeValue());

        TextView p_highest_code_val = findViewById(R.id.player_highest_code_val_placing);
        p_highest_code_val.setText(player_details.getHighestCodeValuePlacing());




        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}