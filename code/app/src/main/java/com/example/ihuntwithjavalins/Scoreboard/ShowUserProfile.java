package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerController;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowUserProfile extends AppCompatActivity {
    private Player player_details = new Player();
    private ArrayList<Player> playerList = new ArrayList<>();
    private String TAG = "Sample"; // used as starter string for debug-log messaging
    private PlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_user_profile);
        setContentView(R.layout.my_profile_page);
        playerController = new PlayerController(this);

//        ImageButton go_back = findViewById(R.id.go_back_btn_);
//        go_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        Intent myIntent = getIntent();
//        UserProfileDetails player_details = (UserProfileDetails) myIntent.getSerializableExtra("PlayerDetails");
        player_details = (Player) myIntent.getSerializableExtra("PlayerDetails");
        playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");

//        TextView p_name = findViewById(R.id.player_name_);
//        TextView p_date = findViewById(R.id.date_joined_);
//        TextView p_email = findViewById(R.id.player_email);
//        TextView p_region = findViewById(R.id.player_region);
//        TextView p_total_points = findViewById(R.id.player_tot_points);
//        TextView p_total_points_placing = findViewById(R.id.total_points_placing);
//        TextView p_total_codes = findViewById(R.id.player_total_codes);
//        TextView p_total_codes_placing = findViewById(R.id.player_tot_codes_placing);
//        TextView p_highest_code = findViewById(R.id.player_highest_code_val);
//        TextView p_highest_code_val = findViewById(R.id.player_highest_code_val_placing);

        Button quickNavButton = findViewById(R.id.button_prf_quicknav);
        Button logoutButton = findViewById(R.id.button_prf_logout);
        quickNavButton.setVisibility(View.INVISIBLE);
        logoutButton.setVisibility(View.INVISIBLE);
        Button backButton = findViewById(R.id.prf_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView username = findViewById(R.id.prf_username_data);
        TextView userEmail = findViewById(R.id.prf_email_data);
        TextView userdateJoined = findViewById(R.id.prf_datejoined_data);
        TextView userregion = findViewById(R.id.prf_region_data);
        TextView totalPoints = findViewById(R.id.prf_totalpoints_data);
        TextView totalPointsPlacing = findViewById(R.id.prf_totalpointsplacing_data);
        TextView totalCodes = findViewById(R.id.prf_totalcodes_data);
        TextView totalCodesPlacing = findViewById(R.id.prf_totalcodesplacing_data);
        TextView highestCodeValue = findViewById(R.id.prf_highestcodevalue_data);
        TextView highestCodeValuePlacing = findViewById(R.id.prf_highestcodevalueplacing_data);

//        p_total_points_placing.setText(player_details.getTotalPointsPlacing());
//        p_total_codes_placing.setText(player_details.getTotalCodesPlacing());
//        p_highest_code_val.setText(player_details.getHighestCodeValuePlacing());

        // add regional list
//        ArrayList<Player> all_players = playerList;
        username.setText(player_details.getUsername());
        userEmail.setText(player_details.getEmail());
        userregion.setText(player_details.getRegion());
        userdateJoined.setText(playerController.getNiceDateFormat(player_details.getDateJoined()));
        totalPoints.setText(String.valueOf(playerController.calculateTotalPoints(player_details)));
        totalCodes.setText(String.valueOf(playerController.getTotalCodes(player_details)));
        highestCodeValue.setText(String.valueOf(playerController.calculateHighestValue(player_details)));

        ArrayList<Player> regionalPlayers = playerController.getRegionalPlayers(player_details, playerList);
        String rankString = playerController.getRanking(player_details, playerList, "Everywhere: #", "points");
        rankString = playerController.getRanking(player_details, regionalPlayers, rankString + "\n" + "Regional: #", "points");
        totalPointsPlacing.setText(rankString);
        rankString = playerController.getRanking(player_details, playerList, "Everywhere: #", "sum");
        rankString = playerController.getRanking(player_details, regionalPlayers, rankString + "\n" + "Regional: #", "sum");
        totalCodesPlacing.setText(rankString);
        rankString = playerController.getRanking(player_details, playerList, "Everywhere: #", "high");
        rankString = playerController.getRanking(player_details, regionalPlayers, rankString + "\n" + "Regional: #", "high");
        highestCodeValuePlacing.setText(rankString);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}