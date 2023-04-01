package com.example.ihuntwithjavalins;

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

import com.example.ihuntwithjavalins.Camera.CameraScanActivity;
import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.Scoreboard.ScoreboardActivity;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * The QuickNavActivity class is responsible for handling the main navigation screen in the app.
 * It allows users to navigate to different features of the app by clicking on corresponding buttons.
 */
public class QuickNavActivity extends AppCompatActivity {
    private Button cameraButton;
    private Button mapButton;
    private Button libraryButton;
    private Button scoreboardButton;
    private Button profileButton;
    private TextView userNameDisplay;
    private TextView userTotalPoints;
    private TextView userTotalCodes;
    private Player player = new Player();
    private Player myPlayer = new Player();
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<QRCode> codeList = new ArrayList<>();
    private String TAG = "Sample"; // used as string tag for debug-log messaging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // grabbed any device stored username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String savedDeviceUser = mPrefs.getString("UsernameTag", "default_username_not_found");

        // set views
        setContentView(R.layout.quick_navigation);

        cameraButton = findViewById(R.id.button_qn_scanCode);
        mapButton = findViewById(R.id.button_qn_map);
        libraryButton = findViewById(R.id.button_qn_cl);
        scoreboardButton = findViewById(R.id.button_qn_sb);
        profileButton = findViewById(R.id.button_qn_profile);

        userNameDisplay = findViewById(R.id.tv_userNameDisplay);
        userTotalPoints = findViewById(R.id.tv_userTotalPoints);
        userTotalCodes = findViewById(R.id.tv_userTotalCodes);


        // open signup/login activity if shared pref not there, otherwise: grab database data
        if (Arrays.asList("default_username_not_found", "Enter a new Username:", " ", "").contains(savedDeviceUser)) {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {

            // grab all players from firebase into ArrayList<Player> playerList;
            // calculate rankings/stats from playerlist (
            // pull myPlayer from playerList;

            // Access a Firestore instance
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            final CollectionReference collectionRef_Users = db.collection("Users");
            DocumentReference docRef_myPlayer = collectionRef_Users.document(savedDeviceUser);
            CollectionReference subColRef_myCodes = docRef_myPlayer.collection("QRCodesSubCollection");

            // grab all players and their codes from firebase and put into player list
            collectionRef_Users
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Player tempPlayer = new Player();
                                    tempPlayer.setUsername(doc.getId());
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
                                                        if ((tempPlayer.getUsername()).equals(savedDeviceUser)) {
                                                            myPlayer = new Player();
                                                            myPlayer.setUsername(tempPlayer.getUsername());
                                                            myPlayer.setRegion(tempPlayer.getRegion());
                                                            myPlayer.setEmail(tempPlayer.getEmail());
                                                            myPlayer.setDateJoined(tempPlayer.getDateJoined());
                                                            myPlayer.addCodes(tempCodeList);

//                                                            // calc most codes and points
//                                                            int totalPointsInt = 0;
//                                                            int highestcodeval = 0;
//                                                            for (QRCode code: codeList ) {
//                                                                totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
//                                                                if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
//                                                                    highestcodeval = Integer.parseInt(code.getCodePoints());
//                                                                }
//                                                            }
//                                                            userNameDisplay.setText(myPlayer.getUsername());
//                                                            userTotalPoints.setText(String.valueOf(totalPointsInt));
//                                                            userTotalCodes.setText(String.valueOf(codeList.size()));

                                                            userNameDisplay.setText(myPlayer.getUsername());
                                                            userTotalPoints.setText(String.valueOf(myPlayer.getSumOfCodePoints()));
                                                            userTotalCodes.setText(String.valueOf(myPlayer.getSumOfCodes()));
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


//            // Access a Firestore instance
//            final FirebaseFirestore db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
//            final CollectionReference collectionRef_Users = db.collection("Users"); // pull instance of specific collection in firestore
//            final DocumentReference docRef_thisPlayer = collectionRef_Users.document(savedDeviceUser); // pull instance of specific collection in firestore
//            final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
//            player = new Player(savedDeviceUser);
//            //https://firebase.google.com/docs/firestore/query-data/get-data
//            docRef_thisPlayer.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            Log.d(TAG, "DocumentSnapshot data" + document.getData());
//                            player.setUsername(document.getId());
//                            player.setEmail(document.getString("Email"));
//                            player.setRegion(document.getString("Region"));
//                            player.setDateJoined(document.getString("Date Joined"));
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//                    } else {
//                        Log.d(TAG, "get failed with ", task.getException());
//                    }
//                }
//            });
//            // This listener will pull the firestore data into your android app (if you reopen the app)
//            subColRef_Codes.addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
//                FirebaseFirestoreException error) {
//                    codeList.clear(); // Clear the old list
//                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
//                        String codeHash = doc.getId();
//                        String codeName = (String) doc.getData().get("Code Name");
//                        String codePoints = (String) doc.getData().get("Point Value");
//                        String codeImgRef = (String) doc.getData().get("Img Ref");
//                        String codeLatValue = (String) doc.getData().get("Lat Value");
//                        String codeLonValue = (String) doc.getData().get("Lon Value");
//                        String codePhotoRef = (String) doc.getData().get("Photo Ref");
//                        String codeDate = (String) doc.getData().get("Date:");
//                        codeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
//                    }
//                    // calc most codes and points
//                    int totalPointsInt = 0;
//                    int highestcodeval = 0;
//                    for (QRCode code: codeList ) {
//                        totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
//                        if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
//                            highestcodeval = Integer.parseInt(code.getCodePoints());
//                        }
//                    }
//                    userNameDisplay.setText(player.getUsername());
//                    userTotalPoints.setText(String.valueOf(totalPointsInt));
//                    userTotalCodes.setText(String.valueOf(codeList.size()));
//                }
//            });



        } // end of 'else' statement

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, CameraScanActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, OpenStreetMapActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, QRCodeLibraryActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                startActivity(intent);
            }
        });

        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, ScoreboardActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                intent.putExtra("playerList", (Serializable) playerList);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, ProfileActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("myPlayer", (Serializable) myPlayer);
                intent.putExtra("playerList", (Serializable) playerList);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
// do nothing
    }

}