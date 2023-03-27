package com.example.ihuntwithjavalins.Scoreboard;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

    ArrayList<Player> playerList;
    ArrayAdapter<Player> playerArrayAdapter;
    ArrayList<StoreNamePoints> StorageList = new ArrayList<>();
    Player myPlayer;

    boolean sortNameAscend = false;
    boolean sortPointsAscend = false;
    boolean sortCodeAmountAscend = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main);
        ListView listViewPlayerList;

        playerList = new ArrayList<>();
        listViewPlayerList = findViewById(R.id.user_code_list);
        playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
        listViewPlayerList.setAdapter(playerArrayAdapter);


        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

        // Access a Firestore instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");

        DocumentReference docRef_myPlayer = collectionRef_Users.document(mStringU);
        CollectionReference subColRef_myCodes = docRef_myPlayer.collection("QRCodesSubCollection");

        // grab all players and their codes from firebase and put into player list
        collectionRef_Users
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Player tempPlayer = new Player(doc.getId());
                                tempPlayer.setEmail((String) doc.getData().get("Email"));
                                tempPlayer.setRegion((String) doc.getData().get("Region"));
                                tempPlayer.setDateJoined((String) doc.getData().get("Date Joined"));
                                DocumentReference docRef_thisPlayer = collectionRef_Users.document(doc.getId());
                                CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
                                ArrayList<QRCode> tempCodeList = new ArrayList<>();
                                subColRef_Codes
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        String codeHash = doc.getId();
                                                        String codeName = (String) doc.getData().get("Code Name");
                                                        String codePoints = (String) doc.getData().get("Point Value");
                                                        String codeImgRef = (String) doc.getData().get("Img Ref");
                                                        String codeLatValue = (String) doc.getData().get("Lat Value");
                                                        String codeLonValue = (String) doc.getData().get("Lon Value");
                                                        String codePhotoRef = (String) doc.getData().get("Photo Ref");
                                                        String codeDate = (String) doc.getData().get("Code Date:");
                                                        tempCodeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
                                                    }
                                                    tempPlayer.addCodes(tempCodeList);
                                                    playerList.add(tempPlayer);
                                                    //if you, also put you in separate obj
                                                    if ((tempPlayer.getUsername()).equals(mStringU)) {
                                                        myPlayer = new Player(mStringU);
                                                        myPlayer.addCodes(tempCodeList);
                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud


        listViewPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<QRCode> codes = playerList.get(position).getCodes();
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
                Player focusedPlayer = playerList.get(position);
                intent.putExtra("focusedPlayer", (Serializable) focusedPlayer);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                intent.putExtra("playerList",playerList);
                intent.putExtra("StorageList", StorageList);
                startActivity(intent);
                StorageList.clear();
            }
        });

        Button points_btn = findViewById(R.id.sort_points_btn);
        points_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort By Points", Toast.LENGTH_SHORT).show();
                Collections.sort(playerList);
                if (sortPointsAscend) {
                    Collections.reverse(playerList);
                }
                sortPointsAscend = !sortPointsAscend;
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
                listViewPlayerList.setAdapter(playerArrayAdapter);

            }
        });

        Button names_btn = findViewById(R.id.sort_name_btn);
        names_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by name
                Collections.sort(playerList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return (p1.getUsername().toLowerCase()).compareTo(p2.getUsername().toLowerCase());
                    }
                });
                if (sortNameAscend) {
                    Collections.reverse(playerList);
                }
                sortNameAscend = !sortNameAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
            }
        });


        Button numcodes_btn = findViewById(R.id.sort_numcodes_btn);
        numcodes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by num of codes
                Collections.sort(playerList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        int p1size = p1.getCodes().size();
                        int p2size = p2.getCodes().size();
                        return Integer.compare(p2size, p1size);
                    }
                });
                if (sortCodeAmountAscend) {
                    Collections.reverse(playerList);
                }
                sortCodeAmountAscend = !sortCodeAmountAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
            }
        });

        Button search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView searchEditText = findViewById(R.id.search_user);
                String searchQuery = searchEditText.getText().toString().toLowerCase();
                ArrayList<Player> searchResultsList = new ArrayList<>();
                for (Player player : playerList) {
                    if (player.getUsername().toLowerCase().contains(searchQuery)) {
                        searchResultsList.add(player);
                    }
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

        Button region_btn = findViewById(R.id.region_btn);

        Spinner regionDropdown = (Spinner) findViewById(R.id.spin_region);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> ThisSpinAdapter = ArrayAdapter.createFromResource(this, R.array.queryRegions_array, android.R.layout.simple_spinner_item);
        ThisSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        regionDropdown.setAdapter(ThisSpinAdapter);// Apply the adapter to the spinner
        region_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Spinner regionDropdown = findViewById(R.id.spin_region);
                String searchQuery = regionDropdown.getSelectedItem().toString();
                ArrayList<Player> searchResultsList = new ArrayList<>();
                if (searchQuery.equals("") | searchQuery.equals("EVERYWHERE")){

                    for (Player player : playerList) {
                            searchResultsList.add(player);
                    }
                } else {
                    for (Player player : playerList) {
                        if (player.getRegion().contains(searchQuery)) {
                            searchResultsList.add(player);
                        }
                    }
                }
                if (searchResultsList.size() == 0) {
                    Toast.makeText(ScoreboardActivity.this, "Couldn't find any people in region: " + searchQuery, Toast.LENGTH_SHORT).show();
                } else {
                    // Display search results in the list view
                    playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, searchResultsList);
                    listViewPlayerList.setAdapter(playerArrayAdapter);
                    playerArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        Button quicknav_btn = findViewById(R.id.btn_quicknav);
        quicknav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreboardActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //delay timer to show list automatically after 2 seconds (otherwise you have to press button)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //                Toast.makeText(ScoreboardActivity.this, "Sort by names", Toast.LENGTH_SHORT).show();
                // Sort the player list by name
                Collections.sort(playerList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return (p1.getUsername().toLowerCase()).compareTo(p2.getUsername().toLowerCase());
                    }
                });
                if (sortNameAscend) {
                    Collections.reverse(playerList);
                }
                sortNameAscend = !sortNameAscend;
                // Update the adapter with the sorted list
                playerArrayAdapter = new CustomListScoreBoard(ScoreboardActivity.this, playerList);
                listViewPlayerList.setAdapter(playerArrayAdapter);
                playerArrayAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        }, 1500);

    }

}