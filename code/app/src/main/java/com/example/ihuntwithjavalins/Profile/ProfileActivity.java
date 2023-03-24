package com.example.ihuntwithjavalins.Profile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents the activity where the user can view their profile information.
 * It extends the AppCompatActivity class.
 * TODO: add algorithms for rankings
 */
public class ProfileActivity extends AppCompatActivity {

    private Button quickNavButton;
    private TextView username;
    private TextView userdateJoined;
    private TextView userregion;
    private TextView userEmail;
    private TextView totalPoints;
    private TextView totalPointsPlacing;
    private TextView totalCodes;
    private TextView totalCodesPlacing;
    private TextView highestCodeValue;
    private TextView highestCodeValuePlacing;
    private Player myPlayer;
    private ArrayList<QRCode> myCodeList;
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Player> playerListWithCodes = new ArrayList<>(); // testing
    private ArrayList<Player> regionalPlayerList = new ArrayList<>();

    private ArrayList<QRCode> codeList = new ArrayList<>();// list of objects
    private String TAG = "Sample"; // used as starter string for debug-log messaging

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_profile_page);

        quickNavButton = findViewById(R.id.button_prf_quicknav);
        logoutButton = findViewById(R.id.button_prf_logout);

        username = findViewById(R.id.prf_username_data);
        userEmail = findViewById(R.id.prf_email_data);
        userdateJoined = findViewById(R.id.prf_datejoined_data);
        userregion = findViewById(R.id.prf_region_data);
        totalPoints = findViewById(R.id.prf_totalpoints_data);
        totalPointsPlacing = findViewById(R.id.prf_totalpointsplacing_data);
        totalCodes = findViewById(R.id.prf_totalcodes_data);
        totalCodesPlacing = findViewById(R.id.prf_totalcodesplacing_data);
        highestCodeValue = findViewById(R.id.prf_highestcodevalue_data);
        highestCodeValuePlacing = findViewById(R.id.prf_highestcodevalueplacing_data);


        // Get the intent from the previous acitvity
        Intent myIntent = getIntent();
        myPlayer = (Player) myIntent.getSerializableExtra("myPlayer");
//        playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");

//        // Access a Firestore instance
//        final FirebaseFirestore db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
//        final CollectionReference collectionRef_Users = db.collection("Users"); // pull instance of specific collection in firestore
//        final DocumentReference docRef_thisPlayer = collectionRef_Users.document(myPlayer.getUsername()); // pull instance of specific collection in firestore
//
//        //https://firebase.google.com/docs/firestore/query-data/get-data
//        docRef_thisPlayer.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data" + document.getData());
//                        myPlayer.setEmail(document.getString("Email"));
//                        myPlayer.setRegion(document.getString("Region"));
//                        myPlayer.setDateJoined(document.getString("Date Joined"));
//                        username.setText(myPlayer.getUsername());
//                        userEmail.setText(myPlayer.getEmail());
//                        userregion.setText(myPlayer.getRegion());
//                        userdateJoined.setText(myPlayer.getDateJoined());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//
//        final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
//        // This listener will pull the firestore data into your android app (if you reopen the app)
//        subColRef_Codes.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
//            FirebaseFirestoreException error) {
//                codeList.clear(); // Clear the old list
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
//                    String codeHash = doc.getId();
//                    String codeName = (String) doc.getData().get("Code Name");
//                    String codePoints = (String) doc.getData().get("Point Value");
//                    String codeImgRef = (String) doc.getData().get("Img Ref");
//                    String codeLatValue = (String) doc.getData().get("Lat Value");
//                    String codeLonValue = (String) doc.getData().get("Lon Value");
//                    String codePhotoRef = (String) doc.getData().get("Photo Ref");
//                    String codeDate = (String) doc.getData().get("Date:");
//                    codeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
//                }
//                // calc most codes and points
//                int totalPointsInt = 0;
//                int highestcodeval = 0;
//                for (QRCode code: codeList ) {
//                    totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
//                    if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
//                        highestcodeval = Integer.parseInt(code.getCodePoints());
//                    }
//                }
//                totalPoints.setText(String.valueOf(totalPointsInt));
//                totalCodes.setText(String.valueOf(codeList.size()));
//                highestCodeValue.setText(String.valueOf(highestcodeval));
//            }
//        });

        // Access a Firestore instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");
        DocumentReference docRef_myPlayer = collectionRef_Users.document(myPlayer.getUsername());
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
                                                    playerList = new ArrayList<>();
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
//                                                    if ((tempPlayer.getUsername()).equals(myPlayer.getUsername())) {
//                                                        myPlayer = new Player(myPlayer.getUsername());
//                                                        myPlayer.addCodes(tempCodeList);
//                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        username.setText(myPlayer.getUsername());
        userEmail.setText(myPlayer.getEmail());
        userregion.setText(myPlayer.getRegion());
        userdateJoined.setText(myPlayer.getDateJoined());

        // calc my: code amount, points total, and highest code
        int totalPointsInt = 0;
        int highestcodeval = 0;
        myCodeList = (ArrayList<QRCode>) myPlayer.getCodes();
        for (QRCode code : myCodeList) {
            totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
            if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
                highestcodeval = Integer.parseInt(code.getCodePoints());
            }
        }

        totalPoints.setText(String.valueOf(totalPointsInt));
        totalCodes.setText(String.valueOf(myCodeList.size()));
        highestCodeValue.setText(String.valueOf(highestcodeval));

//        // grab region
//        for (Player plr : playerList) {
//            if ( (plr.getRegion()).equals(myPlayer.getRegion()) ){
//                regionalPlayerList.add(plr); //add all regional players (including you)
//            }
//        }

        // grab playerList of players with codes
        for (Player plr : playerList) {
            if (plr.getSumOfCodePoints() > 0) {
                playerListWithCodes.add(plr);
            }
        }

        // grab region playerList of players with codes
        for (Player plr : playerListWithCodes) {
            if ( (plr.getRegion()).equals(myPlayer.getRegion()) ){
                regionalPlayerList.add(plr); //add all regional players (including you)
            }
        }



        // calc my rankings of: code amount, points total, and highest code using playerlist
        // for my region and for everywhere
        // show gold, silver, bronze, initiate placement too
//        String[][] rankList_mostCodes_Everywhere = new String[playerList.size()][2];
//        String[][] rankList_mostTotalPoints_Everywhere = new String[playerList.size()][2];
//        String[][] rankList_mostBiggestCode_Everywhere = new String[playerList.size()][2];
//        String[][] rankList_mostCodes_MyRegion = new String[regionalPlayerList.size()][2];
//        String[][] rankList_mostTotalPoints_MyRegion = new String[regionalPlayerList.size()][2];
//        String[][] rankList_mostBiggestCode_MyRegion = new String[regionalPlayerList.size()][2];

//        for (int i = 0; i < playerList.size(); i++) {
//            rankList_mostCodes_Everywhere[i][0] = Integer.toString(playerList.get(i).getSumOfCodes());
//            rankList_mostCodes_Everywhere[i][1] = playerList.get(i).getUsername();
//
//        }

        ArrayList<Player> rankList_mostCodes_Everywhere;
        ArrayList<Player> rankList_mostTotalPoints_Everywhere;
        ArrayList<Player> rankList_biggestCode_Everywhere;
        ArrayList<Player> rankList_mostCodes_MyRegion;
        ArrayList<Player> rankList_mostTotalPoints_MyRegion;
        ArrayList<Player> rankList_biggestCode_MyRegion;
        Collections.sort(playerListWithCodes, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getCodes().size();
                int p2size = p2.getCodes().size();
                return Integer.compare(p2size, p1size);
            }
        });
            Collections.reverse(playerListWithCodes);
        rankList_mostCodes_Everywhere = new ArrayList<>(playerListWithCodes);

        Collections.sort(playerListWithCodes, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.reverse(playerListWithCodes);
        rankList_mostTotalPoints_Everywhere = new ArrayList<>(playerListWithCodes);

        Collections.sort(playerListWithCodes, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = Integer.parseInt(p1.getHighestCode().getCodePoints());
                int p2size = Integer.parseInt(p2.getHighestCode().getCodePoints());
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.reverse(playerListWithCodes);
        rankList_biggestCode_Everywhere = new ArrayList<>(playerListWithCodes);

        Collections.sort(regionalPlayerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getCodes().size();
                int p2size = p2.getCodes().size();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.reverse(regionalPlayerList);
        rankList_mostCodes_MyRegion = new ArrayList<>(regionalPlayerList);

        Collections.sort(regionalPlayerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.reverse(regionalPlayerList);
        rankList_mostTotalPoints_MyRegion= new ArrayList<>(regionalPlayerList);

        Collections.sort(regionalPlayerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = Integer.parseInt(p1.getHighestCode().getCodePoints());
                int p2size = Integer.parseInt(p2.getHighestCode().getCodePoints());
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.reverse(regionalPlayerList);
        rankList_biggestCode_MyRegion = new ArrayList<>(regionalPlayerList);

        totalCodesPlacing.setText(String.valueOf(1 + rankList_mostCodes_Everywhere.indexOf(myPlayer) ));
        totalPointsPlacing.setText(String.valueOf(1+rankList_mostTotalPoints_Everywhere.indexOf(myPlayer)));
        highestCodeValuePlacing.setText(String.valueOf(1+rankList_biggestCode_Everywhere.indexOf(myPlayer)));



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // https://stackoverflow.com/questions/3687315/how-to-delete-shared-preferences-data-from-app-in-android
                SharedPreferences preferences = getSharedPreferences("Login", 0);
                preferences.edit().remove("UsernameTag").commit();

                Intent intent = new Intent(ProfileActivity.this, TitleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        quickNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
}