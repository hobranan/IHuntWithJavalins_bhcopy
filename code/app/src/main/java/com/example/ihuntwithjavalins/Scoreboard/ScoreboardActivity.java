package com.example.ihuntwithjavalins.Scoreboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerController;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ScoreboardActivity is an activity class that displays a list of players and their scores in a scoreboard.
 * The activity extends AppCompatActivity and initializes a ListView to display the list of players and their scores.
 * It creates an ArrayList of Player objects and adds QRCode objects to the player's list of codes. It then adds each player to the ArrayList.
 * The class also creates an instance of CustomListScoreBoard class to populate the ListView with data from the ArrayList.
 */
public class ScoreboardActivity extends AppCompatActivity {
    private Player myPlayer = new Player();
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Player> regionalList = new ArrayList<>();
    private ArrayAdapter<Player> playerArrayAdapter;
    private ArrayList<StoreNamePoints> StorageList = new ArrayList<>();
    public static boolean sortAscend = false;
    public static boolean rankingNameFlag = false;
    private PlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main);

        playerController = new PlayerController(this);

        TextView searchEditText = findViewById(R.id.search_user);
        Button names_btn = findViewById(R.id.sort_name_btn);
        Button points_btn = findViewById(R.id.sort_points_btn);
        Button numcodes_btn = findViewById(R.id.sort_numcodes_btn);
        Button highestcode_btn = findViewById(R.id.sort_highestcode_btn);
        Button quicknav_btn = findViewById(R.id.btn_quicknav);
        Button search_btn = findViewById(R.id.search_btn);
        Button region_btn = findViewById(R.id.region_btn);
        Spinner regionDropdown = (Spinner) findViewById(R.id.spin_region);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> ThisSpinAdapter = ArrayAdapter.createFromResource(this, R.array.queryRegions_array, android.R.layout.simple_spinner_item);
        ThisSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        regionDropdown.setAdapter(ThisSpinAdapter);// Apply the adapter to the spinner

        // grabbed any store username variables within app local storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");


        ListView listViewPlayerList;

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        myPlayer = (Player) myIntent.getSerializableExtra("myPlayer");
        playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");
        regionalList = new ArrayList<>(playerList);

        listViewPlayerList = findViewById(R.id.user_code_list);
        playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
        listViewPlayerList.setAdapter(playerArrayAdapter);
        playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
        playerController.sortPlayers(regionalList, "points");
        sortAscend = false;
        if (sortAscend) {
            Collections.reverse(regionalList);
        }
        sortAscend = true;
        rankingNameFlag = false;
        playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
        listViewPlayerList.setAdapter(playerArrayAdapter);
        playerArrayAdapter.notifyDataSetChanged();


        region_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                String searchQuery = regionDropdown.getSelectedItem().toString();
                ArrayList<Player> savedInCase_List = new ArrayList<>(regionalList);
                regionalList = new ArrayList<>();
                if (searchQuery.equals("") | searchQuery.equals("EVERYWHERE")) {
                    regionalList = new ArrayList<>(playerList);
                } else {
                    regionalList.addAll(playerController.getPlayersContainQuery(playerList, searchQuery, "region"));
                }
                if (regionalList.size() == 0) {
                    Toast.makeText(ScoreboardActivity.this, "Couldn't find any people in region: " + searchQuery, Toast.LENGTH_SHORT).show();
                    regionalList = new ArrayList<>(savedInCase_List);
                } else {
                    // Display search results in the list view
                    regionalList = playerController.sortPlayers(regionalList, "points");
                    sortAscend = false;
                    if (sortAscend) {
                        Collections.reverse(regionalList);
                    }
                    sortAscend = true;
                    playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
                    listViewPlayerList.setAdapter(playerArrayAdapter);
                    playerArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString().toLowerCase();

                // https://stackoverflow.com/questions/1109022/how-to-close-hide-the-android-soft-keyboard-programmatically
                InputMethodManager imm = (InputMethodManager) getSystemService(ScoreboardActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                ArrayList<Player> searchResultsList = new ArrayList<>();
                searchResultsList.addAll(playerController.getPlayersContainQuery(regionalList, searchQuery, "within"));
                if (searchQuery.equals("")) {
                    rankingNameFlag = false;
                } else {
                    rankingNameFlag = true;
                }
                if (searchResultsList.size() == 0) {
                    Toast.makeText(ScoreboardActivity.this, "Couldn't find any names starting with " + searchQuery, Toast.LENGTH_SHORT).show();
                } else {
                    // Display search results in the list view
                    playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, searchResultsList);
                    listViewPlayerList.setAdapter(playerArrayAdapter);
                    playerArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        names_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankingNameFlag = true;
                // Sort the player list by name
                regionalList = playerController.sortPlayers(regionalList, "name");

                if (sortAscend) {
                    Collections.reverse(regionalList);
                }
                sortAscend = !sortAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
            }
        });

        points_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankingNameFlag = false;
                regionalList = playerController.sortPlayers(regionalList, "points");
                if (sortAscend) {
                    Collections.reverse(regionalList);
                }
                sortAscend = !sortAscend;
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
                listViewPlayerList.setAdapter(playerArrayAdapter);

            }
        });

        numcodes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankingNameFlag = false;
                // Sort the player list by num of codes
                regionalList = playerController.sortPlayers(regionalList, "sum");
                if (sortAscend) {
                    Collections.reverse(regionalList);
                }
                sortAscend = !sortAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
            }
        });

        highestcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankingNameFlag = false;
                // Sort the player list by num of codes
                regionalList = playerController.sortPlayers(regionalList, "high+sum");
                if (sortAscend) {
                    Collections.reverse(regionalList);
                }
                sortAscend = !sortAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
            }
        });

        listViewPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player thisPlayer = playerArrayAdapter.getItem(position);
                List<QRCode> codes = thisPlayer.getCodes();
                for (int i = 0; i < codes.size(); i++) {
                    int has = 0;
                    for (QRCode mycode : myPlayer.getCodes()) {
                        if ((mycode.getCodeHash()).equals(codes.get(i).getCodeHash())) {
                            has++;
                            break;
                        }
                    }
                    boolean boolHas = has > 0;
                    StoreNamePoints store = new StoreNamePoints(codes.get(i).getCodeName(), codes.get(i).getCodePoints(), boolHas);
                    StorageList.add(store);
                }


                Intent intent = new Intent(ScoreboardActivity.this, ShowIndividualCodes.class);
                intent.putExtra("focusedPlayer", (Serializable) thisPlayer);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                intent.putExtra("playerList", (Serializable) playerList);
                intent.putExtra("StorageList", StorageList);
                startActivity(intent);
                StorageList.clear();
            }
        });

        quicknav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreboardActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

}