package com.example.ihuntwithjavalins.Scoreboard;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.example.ihuntwithjavalins.TitleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ComparisonChain;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ScoreboardActivity is an activity class that displays a list of players and their scores in a scoreboard.
 * The activity extends AppCompatActivity and initializes a ListView to display the list of players and their scores.
 * It creates an ArrayList of Player objects and adds QRCode objects to the player's list of codes. It then adds each player to the ArrayList.
 * The class also creates an instance of CustomListScoreBoard class to populate the ListView with data from the ArrayList.
 */
public class ScoreboardActivity extends AppCompatActivity {
    Player myPlayer = new Player();
    ArrayList<Player> playerList = new ArrayList<>();
    ArrayList<Player> regionalList = new ArrayList<>();
    ArrayAdapter<Player> playerArrayAdapter;
    ArrayList<StoreNamePoints> StorageList = new ArrayList<>();
    public static boolean sortAscend = false;
    public static boolean rankingNameFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main);

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
        playerList = new ArrayList<>();

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        myPlayer = (Player) myIntent.getSerializableExtra("myPlayer");
        playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");
        regionalList = new ArrayList<>(playerList);

        listViewPlayerList = findViewById(R.id.user_code_list);
        playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
        listViewPlayerList.setAdapter(playerArrayAdapter);
        playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
        Collections.sort(regionalList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        sortAscend = false;
        if (sortAscend) {
            Collections.reverse(regionalList);
        }
        sortAscend = true;
        rankingNameFlag = false;
        playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
        listViewPlayerList.setAdapter(playerArrayAdapter);
        playerArrayAdapter.notifyDataSetChanged();

//        // Access a Firestore instance
//        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final CollectionReference collectionRef_Users = db.collection("Users");
//        DocumentReference docRef_myPlayer = collectionRef_Users.document(mStringU);
//        CollectionReference subColRef_myCodes = docRef_myPlayer.collection("QRCodesSubCollection");
//
//        // grab all players and their codes from firebase and put into player list
//        collectionRef_Users
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                Player tempPlayer = new Player();
//                                tempPlayer.setUsername(doc.getId());
//                                tempPlayer.setEmail((String) doc.getData().get("Email"));
//                                tempPlayer.setRegion((String) doc.getData().get("Region"));
//                                tempPlayer.setDateJoined((String) doc.getData().get("Date Joined"));
//                                DocumentReference docRef_thisPlayer = collectionRef_Users.document(doc.getId());
//                                CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
//                                ArrayList<QRCode> tempCodeList = new ArrayList<>();
//                                subColRef_Codes
//                                        .get()
//                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                                                        String codeHash = doc.getId();
//                                                        String codeName = (String) doc.getData().get("Code Name");
//                                                        String codePoints = (String) doc.getData().get("Point Value");
//                                                        String codeImgRef = (String) doc.getData().get("Img Ref");
//                                                        String codeLatValue = (String) doc.getData().get("Lat Value");
//                                                        String codeLonValue = (String) doc.getData().get("Lon Value");
//                                                        String codePhotoRef = (String) doc.getData().get("Photo Ref");
//                                                        String codeDate = (String) doc.getData().get("Code Date:");
//                                                        tempCodeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
//                                                    }
//                                                    tempPlayer.addCodes(tempCodeList);
//                                                    playerList.add(tempPlayer);
//                                                    //if you, also put you in separate obj
//                                                    if ((tempPlayer.getUsername()).equals(mStringU)) {
//                                                        myPlayer = new Player();
//                                                        myPlayer.setUsername(mStringU);
//                                                        myPlayer.addCodes(tempCodeList);
//                                                    }
//                                                }
//                                            }
//                                        });
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud


        region_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                String searchQuery = regionDropdown.getSelectedItem().toString();
                ArrayList<Player> savedInCase_List = new ArrayList<>(regionalList);
                regionalList = new ArrayList<>();
                if (searchQuery.equals("") | searchQuery.equals("EVERYWHERE")){
                        regionalList = new ArrayList<>(playerList);
                } else {
                    for (Player player : playerList) {
                        if (player.getRegion().contains(searchQuery)) {
                            regionalList.add(player);
                        }
                    }
                }
                if (regionalList.size() == 0) {
                    Toast.makeText(ScoreboardActivity.this, "Couldn't find any people in region: " + searchQuery, Toast.LENGTH_SHORT).show();
                    regionalList = new ArrayList<>(savedInCase_List);
                } else {
                    // Display search results in the list view
//                    playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, regionalList);
//                    listViewPlayerList.setAdapter(playerArrayAdapter);
//                    playerArrayAdapter.notifyDataSetChanged();
                    Collections.sort(regionalList, new Comparator<Player>() {
                        @Override
                        public int compare(Player p1, Player p2) {
                            int p1size = p1.getSumOfCodePoints();
                            int p2size = p2.getSumOfCodePoints();
                            return Integer.compare(p2size, p1size);
                        }
                    });
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
                for (Player player : regionalList) {
                    if (player.getUsername().toLowerCase().contains(searchQuery)) {
                        searchResultsList.add(player);
                    }
                }
                if (searchQuery.equals("")){
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
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by name
                Collections.sort(regionalList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return (p1.getUsername().toLowerCase()).compareTo(p2.getUsername().toLowerCase());
                    }
                });
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
//                Toast.makeText(ScoreboardActivity.this, "Sort By Points", Toast.LENGTH_SHORT).show();
                Collections.sort(regionalList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        int p1size = p1.getSumOfCodePoints();
                        int p2size = p2.getSumOfCodePoints();
                        return Integer.compare(p2size, p1size);
                    }
                });
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
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(regionalList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        int p1size = p1.getCodes().size();
                        int p2size = p2.getCodes().size();
                        return Integer.compare(p2size, p1size);
                    }
                });
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
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(regionalList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        // https://stackoverflow.com/questions/4258700/collections-sort-with-multiple-fields
                        return ComparisonChain.start().compare(p2.getHighestCode(), p1.getHighestCode()).compare(p2.getSumOfCodePoints(), p1.getSumOfCodePoints()).result();
//                        int p1size = p1.getHighestCode();
//                        int p2size = p2.getHighestCode();
//                        return Integer.compare(p2size, p1size);
                    }
                });
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
//                Log.d(TAG, "playerID: "+ (playerArrayAdapter.getAdapter().getItem(position)).toString() );
//                Player thisPlayer = listViewPlayerList.getId();
                Player thisPlayer = playerArrayAdapter.getItem(position);
                List<QRCode> codes = thisPlayer.getCodes();
//                Toast.makeText(ScoreboardActivity.this,Integer.toString(codes.size()) , Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(ScoreboardActivity.this, StorageList.get(1).getCodeName(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ScoreboardActivity.this, ShowIndividualCodes.class);
//                Toast.makeText(ScoreboardActivity.this, PlayerCodeList.get(position).getUsername(), Toast.LENGTH_SHORT).show();
//                String get_username = playerList.get(position).getUsername();
//                Player focusedPlayer = playerList.get(position);
                intent.putExtra("focusedPlayer", (Serializable) thisPlayer);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                intent.putExtra("playerList",playerList);
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

//        //delay timer to show list (sort by names) automatically after 2 seconds (otherwise you have to press 'sort by' button)
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
//                // Sort the player list by name
//                Collections.sort(playerList, new Comparator<Player>() {
//                    @Override
//                    public int compare(Player p1, Player p2) {
//                        return (p1.getUsername().toLowerCase()).compareTo(p2.getUsername().toLowerCase());
//                    }
//                });
//                if (sortNameAscend) {
//                    Collections.reverse(playerList);
//                }
//                sortNameAscend = !sortNameAscend;
//                // Update the adapter with the sorted list
//                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
//                listViewPlayerList.setAdapter(playerArrayAdapter);
//                playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
//            }
//        }, 1500);

    }

}