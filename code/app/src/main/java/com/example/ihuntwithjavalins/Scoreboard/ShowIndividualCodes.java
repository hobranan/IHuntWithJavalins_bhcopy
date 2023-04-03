package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerController;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewOtherActivity;
import com.example.ihuntwithjavalins.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ShowIndividualCodes is responsible for showing the details of each QRCode
 */
public class ShowIndividualCodes extends AppCompatActivity {
    boolean sortPointAmountAscend = false;
    boolean sortHaveAscend = false;
    boolean sortNameAscend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // viraj version
        setContentView(R.layout.scoreboard_main_individual);

        TextView user_name = findViewById(R.id.userName);

        String total_points_placing = "";
        Button see_user_profile = findViewById(R.id.player_profile_btn);

        Button btn_have = findViewById(R.id.have_btn);
        Button btn_points = findViewById(R.id.ou_points);
        Button btn_name = findViewById(R.id.ou_name);

//        String UserName = getIntent().getStringExtra("USER");
        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        Player thisPlayer = (Player) myIntent.getSerializableExtra("focusedPlayer");
        ArrayList<Player> playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");
//        Toast.makeText(this, thisPlayer.getUsername(), Toast.LENGTH_SHORT).show();

        ArrayList<StoreNamePoints> storageList = (ArrayList<StoreNamePoints>) getIntent().getSerializableExtra("StorageList");
        CustomListForQRCodeCustomAdapter storageAdapter = new CustomListForQRCodeCustomAdapter(this, storageList);
        ListView listView = findViewById(R.id.code_list);
        listView.setAdapter(storageAdapter);

        user_name.setText(thisPlayer.getUsername());

        String btn_txt = "See " + thisPlayer.getUsername() + "'s Profile";

        Button go_back = findViewById(R.id.go_back_btn);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {
                        return (c1.getCodeName()).compareTo(c2.getCodeName());
                    }
                });
                if (sortNameAscend) {
                    Collections.reverse(storageList);
                }
                sortNameAscend = !sortNameAscend;
                // Update the adapter with the sorted list
//                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
////                listViewPlayerList.setAdapter(playerArrayAdapter);
                storageAdapter.notifyDataSetChanged();
            }
        });

        btn_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {

                        return Boolean.compare(c1.isScanned(), c2.isScanned());
                    }
                });
                if (sortHaveAscend) {
                    Collections.reverse(storageList);
                }
                sortHaveAscend = !sortHaveAscend;
                // Update the adapter with the sorted list
//                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
////                listViewPlayerList.setAdapter(playerArrayAdapter);
                storageAdapter.notifyDataSetChanged();
            }
        });

        btn_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {
                        return Integer.compare(Integer.parseInt(c1.getCodePoints()), Integer.parseInt(c2.getCodePoints()));
                    }
                });
                if (sortPointAmountAscend) {
                    Collections.reverse(storageList);
                }
                sortPointAmountAscend = !sortPointAmountAscend;
                // Update the adapter with the sorted list
//                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
////                listViewPlayerList.setAdapter(playerArrayAdapter);
                storageAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreNamePoints thisCodeTemp = storageAdapter.getItem(position);
                QRCode thisCode = null;
                for (QRCode mycode : thisPlayer.getCodes()) {
                    if ((mycode.getCodeName()).equals(thisCodeTemp.getCodeName())) {
                        thisCode = mycode;
                        break;
                    }
                }
                Intent intent = new Intent(ShowIndividualCodes.this, QRCodeViewOtherActivity.class);
                intent.putExtra("focusedCode", (Serializable) thisCode);
                startActivity(intent);
            }
        });

        see_user_profile.setText(btn_txt);
        see_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_view_user_activity = new Intent(ShowIndividualCodes.this, ShowUserProfile.class);
                start_view_user_activity.putExtra("PlayerDetails", (Serializable) thisPlayer);
                start_view_user_activity.putExtra("playerList", (Serializable) playerList);
                startActivity(start_view_user_activity);
            }
        });


//        see_user_profile.setText(btn_txt);
//        see_user_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<Player> player_details = new ArrayList<Player>();
//                Intent start_view_user_activity = new Intent(ShowIndividualCodes.this,ShowUserProfile.class);
//                String date = thisPlayer.getDateJoined();
//                String date_joined = "";
//                if (date!=null){
//                    String[] months = {
//                            "January",
//                            "February",
//                            "March",
//                            "April",
//                            "May",
//                            "June",
//                            "July",
//                            "August",
//                            "September",
//                            "October",
//                            "November",
//                            "December"
//                    };
//
//
//                    String year = date.substring(0,4);
//                    String month = date.substring(4,6);
//                    String day = date.substring(6,8);
//
//                    // Convert the day from a string to an integer
//                    int dayInt = Integer.parseInt(day);
//
//                    // Get the day suffix
//                    String daySuffix;
//                    if (dayInt % 10 == 1 && dayInt != 11) {
//                        daySuffix = "st";
//                    } else if (dayInt % 10 == 2 && dayInt != 12) {
//                        daySuffix = "nd";
//                    } else if (dayInt % 10 == 3 && dayInt != 13) {
//                        daySuffix = "rd";
//                    } else {
//                        daySuffix = "th";
//                    }
//
//                    // Get the month name from the array
//                    int monthInt = Integer.parseInt(month);
//                    String monthName = months[monthInt - 1];
//
//                    // Build the final date string
//                    date_joined = dayInt + daySuffix + " " + monthName + ", " + year;
//                }
//                else{
//                    date_joined="No date";
//                }
//
//                List<QRCode> allCodes = thisPlayer.getCodes();
//                int highestCode = 0;
//
//                for (QRCode code : allCodes) {
//                    int value = Integer.parseInt(code.getCodePoints());
//                    if (value > highestCode) {
//                        highestCode = value;
//                    }
//                }
//
//                UserProfileDetails user = new UserProfileDetails(thisPlayer.getUsername(), date_joined, thisPlayer.getEmail(),thisPlayer.getRegion(),Integer.toString(thisPlayer.getSumOfCodePoints()),getTotalPointsPlacing(),Integer.toString((thisPlayer.getCodes().size())),getTotalCodesPlacing(),Integer.toString(highestCode),getHighestCodePlacing());
//                start_view_user_activity.putExtra("PlayerDetails",(Serializable) user);
//                startActivity(start_view_user_activity);
//            }
//        });

    }

//    public String getTotalPointsPlacing() {
//        String total_points_placing = "";
//        Intent myIntent = getIntent();
//        Player thisPlayer = (Player) myIntent.getSerializableExtra("focusedPlayer");
//
//        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("playerList");
//        // Find the top 5%, 10%, and 15% of the list
//        int top5Percent = (int) Math.round(players.size() * 0.05);
//        int top10Percent = (int) Math.round(players.size() * 0.1);
//        int top15Percent = (int) Math.round(players.size() * 0.15);
//        Collections.sort(players, new Comparator<Player>() {
//            @Override
//            public int compare(Player p1, Player p2) {
//                return Integer.compare(p2.getSumOfCodePoints(), p1.getSumOfCodePoints());
//            }
//        });
//
//
//        // Search for a player's name and find which percentage their score falls in
//        String playerName = thisPlayer.getUsername(); // Replace with the name you want to search for
//        for (int i = 0; i < players.size(); i++) {
//            if (players.get(i).getUsername().equals(playerName)) {
//                int playerScore = players.get(i).getSumOfCodes();
////                Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
//                if (i < top5Percent) {
//                    if (i == 0) {
//                        total_points_placing = "Leader(Position:" + (i + 1) + ")";
//                    } else {
////                        Toast.makeText(this, playerName + " is in the top 5% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                        total_points_placing = "Gold(Position:" + (i + 1) + ")";
//                    }
//                } else if (i + 1 <= top10Percent && i + 1 >= top5Percent) {
////                    Toast.makeText(this, playerName + " is in the top 10% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Silver(Position:" + (i + 1) + ")";
//                } else if (i + 1 <= top15Percent && i + 1 >= top10Percent) {
////                    Toast.makeText(this, playerName + " is in the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Bronze(Position:" + (i + 1) + ")";
//                } else {
////                    Toast.makeText(this, playerName + " is outside of the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Position:" + Integer.toString(i + 1);
//                }
//
//                break;
//            }
//        }
//        return total_points_placing;
//    }
//
//    public String getTotalCodesPlacing() {
//        String total_points_placing = "";
//        Intent myIntent = getIntent();
//        Player thisPlayer = (Player) myIntent.getSerializableExtra("focusedPlayer");
//
//        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("playerList");
//        // Find the top 5%, 10%, and 15% of the list
//        int top5Percent = (int) Math.round(players.size() * 0.05);
//        int top10Percent = (int) Math.round(players.size() * 0.1);
//        int top15Percent = (int) Math.round(players.size() * 0.15);
//        Collections.sort(players, new Comparator<Player>() {
//            @Override
//            public int compare(Player p1, Player p2) {
//                return Integer.compare(p2.getSumOfCodes(), p1.getSumOfCodes());
//            }
//        });
//
//
//        // Search for a player's name and find which percentage their score falls in
//        String playerName = thisPlayer.getUsername(); // Replace with the name you want to search for
//        for (int i = 0; i < players.size(); i++) {
//            if (players.get(i).getUsername().equals(playerName)) {
//                int playerScore = players.get(i).getSumOfCodes();
////                Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
//                if (i < top5Percent) {
//                    if (i == 0) {
//                        total_points_placing = "Leader(Position:" + (i + 1) + ")";
//                    } else {
////                        Toast.makeText(this, playerName + " is in the top 5% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                        total_points_placing = "Gold(Position:" + (i + 1) + ")";
//                    }
//                } else if (i + 1 <= top10Percent && i + 1 >= top5Percent) {
////                    Toast.makeText(this, playerName + " is in the top 10% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Silver(Position:" + (i + 1) + ")";
//                } else if (i + 1 <= top15Percent && i + 1 >= top10Percent) {
////                    Toast.makeText(this, playerName + " is in the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Bronze(Position:" + (i + 1) + ")";
//                } else {
////                    Toast.makeText(this, playerName + " is outside of the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Position:" + Integer.toString(i + 1);
//                }
//
//                break;
//            }
//        }
//        return total_points_placing;
//    }
//
//
//    public String getHighestCodePlacing() {
//        String total_points_placing = "";
//        Intent myIntent = getIntent();
//        Player thisPlayer = (Player) myIntent.getSerializableExtra("focusedPlayer");
//
//        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("playerList");
//        // Find the top 5%, 10%, and 15% of the list
//        int top5Percent = (int) Math.round(players.size() * 0.05);
//        int top10Percent = (int) Math.round(players.size() * 0.1);
//        int top15Percent = (int) Math.round(players.size() * 0.15);
//        Collections.sort(players, new Comparator<Player>() {
//            @Override
//            public int compare(Player p1, Player p2) {
//                return Integer.compare(p2.getHighestCode(), p1.getHighestCode());
//            }
//        });
//
//
//        // Search for a player's name and find which percentage their score falls in
//        String playerName = thisPlayer.getUsername(); // Replace with the name you want to search for
//        for (int i = 0; i < players.size(); i++) {
//            if (players.get(i).getUsername().equals(playerName)) {
//                int playerScore = players.get(i).getSumOfCodes();
////                Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
//                if (i < top5Percent) {
//                    if (i == 0) {
//                        total_points_placing = "Leader(Position:" + (i + 1) + ")";
//                    } else {
////                        Toast.makeText(this, playerName + " is in the top 5% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                        total_points_placing = "Gold(Position:" + (i + 1) + ")";
//                    }
//                } else if (i + 1 <= top10Percent && i + 1 >= top5Percent) {
////                    Toast.makeText(this, playerName + " is in the top 10% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Silver(Position:" + (i + 1) + ")";
//                } else if (i + 1 <= top15Percent && i + 1 >= top10Percent) {
////                    Toast.makeText(this, playerName + " is in the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Bronze(Position:" + (i + 1) + ")";
//                } else {
////                    Toast.makeText(this, playerName + " is outside of the top 15% with a score of " + playerScore, Toast.LENGTH_SHORT).show();
//                    total_points_placing = "Position:" + Integer.toString(i + 1);
//                }
//
//                break;
//            }
//        }
//        return total_points_placing;
//    }


}
