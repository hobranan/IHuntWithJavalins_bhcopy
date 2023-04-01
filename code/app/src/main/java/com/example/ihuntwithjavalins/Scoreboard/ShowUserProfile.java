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
import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowUserProfile extends AppCompatActivity {
    private Player player_details = new Player();
    private ArrayList<Player> playerList = new ArrayList<>();
    private String TAG = "Sample"; // used as starter string for debug-log messaging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_user_profile);
        setContentView(R.layout.my_profile_page);

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

        username.setText(player_details.getUsername());
        userdateJoined.setText(player_details.getDateJoined());
        String date = player_details.getDateJoined();
                String date_joined = "";
                if (date!=null){
                    String[] months = {
                            "January",
                            "February",
                            "March",
                            "April",
                            "May",
                            "June",
                            "July",
                            "August",
                            "September",
                            "October",
                            "November",
                            "December"
                    };
                    String year = date.substring(0,4);
                    String month = date.substring(4,6);
                    String day = date.substring(6,8);
                    // Convert the day from a string to an integer
                    int dayInt = Integer.parseInt(day);
                    // Get the day suffix
                    String daySuffix;
                    if (dayInt % 10 == 1 && dayInt != 11) {
                        daySuffix = "st";
                    } else if (dayInt % 10 == 2 && dayInt != 12) {
                        daySuffix = "nd";
                    } else if (dayInt % 10 == 3 && dayInt != 13) {
                        daySuffix = "rd";
                    } else {
                        daySuffix = "th";
                    }
                    // Get the month name from the array
                    int monthInt = Integer.parseInt(month);
                    String monthName = months[monthInt - 1];
                    // Build the final date string
                    date_joined = dayInt + daySuffix + " " + monthName + ", " + year;
                }
                else{
                    date_joined="No date";
                }
        userdateJoined.setText(date_joined);

        userEmail.setText(player_details.getEmail());
        userregion.setText(player_details.getRegion());

        totalPoints.setText(String.valueOf(player_details.getSumOfCodePoints()));
        totalCodes.setText(String.valueOf(player_details.getSumOfCodes()));
        highestCodeValue.setText(String.valueOf(player_details.getHighestCode()));

//        p_total_points_placing.setText(player_details.getTotalPointsPlacing());
//        p_total_codes_placing.setText(player_details.getTotalCodesPlacing());
//        p_highest_code_val.setText(player_details.getHighestCodeValuePlacing());

        // add regional list
//        ArrayList<Player> all_players = playerList;
        ArrayList<Player> regional_players = new ArrayList<>();
        Log.d(TAG, "profile : regional_players.add(plr): NEW");
        for (Player plr : playerList) {
            if ((player_details.getRegion()).equals(plr.getRegion())) {
                regional_players.add(plr);
                Log.d(TAG, "profile : regional_players.add(plr): " + plr.getUsername() + " "+ plr.getRegion() + " " + plr.getSumOfCodePoints() + " " + plr.getSumOfCodes() + " " + plr.getHighestCode());
            }
        }
        float goldLevel = 0.05f;
        float silverLevel = 0.10f;
        float bronzeLevel = 0.25f;

        // sort both list by points
        String pointsString = "";
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                pointsString = pointsString + rankString;
                totalPointsPlacing.setText(pointsString);
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = pointsString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                pointsString = pointsString + rankString;
                totalPointsPlacing.setText(pointsString);
            }
        }

        // sort both list by number of codes
        String numCodesString = "";
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodes();
                int p2size = p2.getSumOfCodes();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodes();
                int p2size = p2.getSumOfCodes();
                return Integer.compare(p2size, p1size);
            }
        });
        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                numCodesString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                numCodesString = numCodesString + rankString;
                totalCodesPlacing.setText(numCodesString);
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                numCodesString = numCodesString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                numCodesString = numCodesString + rankString;
                totalCodesPlacing.setText(numCodesString);
            }
        }

        // sort both list by highest code
        String highestCodeString = "";
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getHighestCode();
                int p2size = p2.getHighestCode();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getHighestCode();
                int p2size = p2.getHighestCode();
                return Integer.compare(p2size, p1size);
            }
        });
        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                highestCodeString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                highestCodeString = highestCodeString + rankString;
                highestCodeValuePlacing.setText(highestCodeString);
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                highestCodeString = highestCodeString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                highestCodeString = highestCodeString + rankString;
                highestCodeValuePlacing.setText(highestCodeString);
            }
        }



    }
}