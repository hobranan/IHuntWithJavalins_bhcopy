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

/**
 * This class represents the activity where the user can view their profile information.
 * It extends the AppCompatActivity class.
 * TODO: add algorithms for rankings
 */
public class ProfileActivity extends AppCompatActivity {

    private Button quickNavButton;
    private Button logoutButton;
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
    private Player myPlayer = new Player();
    private ArrayList<Player> playerList = new ArrayList<>();

    private String TAG = "Sample"; // used as starter string for debug-log messaging

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

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        myPlayer = (Player) myIntent.getSerializableExtra("myPlayer");
        playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");

        username.setText(myPlayer.getUsername());
        userEmail.setText(myPlayer.getEmail());
        userregion.setText(myPlayer.getRegion());
        userdateJoined.setText(myPlayer.getDateJoined());

        totalPoints.setText(String.valueOf(myPlayer.getSumOfCodePoints()));
        totalCodes.setText(String.valueOf(myPlayer.getSumOfCodes()));
        highestCodeValue.setText(String.valueOf(myPlayer.getHighestCode()));

        // add regional list
        ArrayList<Player> all_players = playerList;
        ArrayList<Player> regional_players = new ArrayList<>();
        for (Player plr : playerList) {
            if ((plr.getRegion()).equals(myPlayer.getRegion())) {
                regional_players.add(plr);
                Log.d(TAG, "profile : regional_players.add(plr): " + plr.getUsername() + " " + plr.getSumOfCodePoints() + " " + plr.getSumOfCodes() + " " + plr.getHighestCode());
            }
        }


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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                pointsString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                pointsString = pointsString + "\n" + "Regional: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                numCodesString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                numCodesString = numCodesString + "\n" + "Regional: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                highestCodeString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
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
            if ((plr.getUsername()).equals(myPlayer.getUsername())) {
                highestCodeString = highestCodeString + "\n" + "Regional: #" + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.25f) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.10f) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= 0.05f) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                highestCodeString = highestCodeString + rankString;
                highestCodeValuePlacing.setText(highestCodeString);
            }
        }

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

        quickNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // https://stackoverflow.com/questions/3687315/how-to-delete-shared-preferences-data-from-app-in-android
                SharedPreferences preferences = getSharedPreferences("Login", 0);
                preferences.edit().remove("UsernameTag").commit();
                preferences.edit().remove("UsernameTag").apply();

                Intent intent = new Intent(ProfileActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

}