package com.example.ihuntwithjavalins;

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

public class QuickNavActivity extends AppCompatActivity {
    private Button cameraButton;
    private Button mapButton;
    private Button libraryButton;
    private Button scoreboardButton;
    private Button profileButton;
    private TextView userNameDisplay;
    private TextView userTotalPoints;
    private TextView userTotalCodes;
    private Player player;
    private ArrayList<QRCode> codeList = new ArrayList<>();// list of objects
    private String TAG = "Sample"; // used as starter string for debug-log messaging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_navigation);
        userNameDisplay = findViewById(R.id.tv_userNameDisplay);
        userTotalPoints = findViewById(R.id.tv_userTotalPoints);
        userTotalCodes = findViewById(R.id.tv_userTotalCodes);
        cameraButton = findViewById(R.id.button_qn_scanCode);
        mapButton = findViewById(R.id.button_qn_map);
        libraryButton = findViewById(R.id.button_qn_cl);
        scoreboardButton = findViewById(R.id.button_qn_sb);
        profileButton = findViewById(R.id.button_qn_profile);

        // grabbed any device stored username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mString = mPrefs.getString("UsernameTag", "default_username_not_found");
        // open signup activity
        if (Arrays.asList("default_username_not_found", "Enter a new Username:", " ", "").contains(mString)) {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            player = new Player(mString);

            // Access a Firestore instance
            final FirebaseFirestore db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
            final CollectionReference collectionRef_Users = db.collection("Users"); // pull instance of specific collection in firestore
            final DocumentReference docRef_thisPlayer = collectionRef_Users.document(player.getUsername()); // pull instance of specific collection in firestore
            //https://firebase.google.com/docs/firestore/query-data/get-data
            docRef_thisPlayer.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data" + document.getData());
                            player.setEmail(document.getString("Email"));
                            player.setRegion(document.getString("Region"));
                            player.setDateJoined(document.getString("Date Joined"));
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
            userNameDisplay.setText(player.getUsername());
            final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
            // This listener will pull the firestore data into your android app (if you reopen the app)
            subColRef_Codes.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                FirebaseFirestoreException error) {
                    codeList.clear(); // Clear the old list
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
                        String codeHash = doc.getId();
                        String codeName = (String) doc.getData().get("Code Name");
                        String codePoints = (String) doc.getData().get("Point Value");
                        String codeImgRef = (String) doc.getData().get("Img Ref");
                        String codeLatValue = (String) doc.getData().get("Lat Value");
                        String codeLonValue = (String) doc.getData().get("Lon Value");
                        String codePhotoRef = (String) doc.getData().get("Photo Ref");
                        String codeDate = (String) doc.getData().get("Date:");
                        codeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
                    }
                    // calc most codes and points
                    int totalPointsInt = 0;
                    int highestcodeval = 0;
                    for (QRCode code: codeList ) {
                        totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
                        if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
                            highestcodeval = Integer.parseInt(code.getCodePoints());
                        }
                    }
                    userTotalPoints.setText(String.valueOf(totalPointsInt));
                    userTotalCodes.setText(String.valueOf(codeList.size()));
                }
            });
        }

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
                startActivity(intent);
            }
        });

        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(QuickNavActivity.this, ScoreboardActivity.class);
//                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, ProfileActivity.class);
                Player player = new Player(mString);
                // Add the custom object as an extra to the Intent
                intent.putExtra("savedPlayerObject", (Serializable) player);
                startActivity(intent);
            }
        });


    }
}