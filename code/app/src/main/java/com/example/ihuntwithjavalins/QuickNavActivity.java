package com.example.ihuntwithjavalins;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.content.Context;
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
import com.example.ihuntwithjavalins.Player.PlayerController;
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

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

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
    private String TAG = "Sample"; // used as string tag for debug-log messaging
    private PlayerController playerController;
    private AtomicReference<Player> player;
    private AtomicReference<List<Player>> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerController = new PlayerController(this);
        // MODEL
        // grabbed any device stored username variables within app local date storage
        SharedPreferences mPrefs = this.getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
//        mPrefs.edit().clear().commit();    // uncomment to easily clear the shared preferences for login testing
        String mString = mPrefs.getString("UsernameTag", "default_username_not_found");


        // VIEW
        setContentView(R.layout.quick_navigation);

        cameraButton = findViewById(R.id.button_qn_scanCode);
        mapButton = findViewById(R.id.button_qn_map);
        libraryButton = findViewById(R.id.button_qn_cl);
        scoreboardButton = findViewById(R.id.button_qn_sb);
        profileButton = findViewById(R.id.button_qn_profile);

        userNameDisplay = findViewById(R.id.tv_userNameDisplay);
        userTotalPoints = findViewById(R.id.tv_userTotalPoints);
        userTotalCodes = findViewById(R.id.tv_userTotalCodes);


        // CONTROLLER


        // open signup/login activity if shared pref not there, otherwise: grab database data
        if (Arrays.asList("default_username_not_found", "Enter a new Username:", " ", "").contains(mString)) {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {

            // grab all players from firebase into ArrayList<Player> playerList;
            // calculate rankings/stats from playerlist
            // pull myPlayer from playerList;

            // Access a Firestore instance
            AtomicReference<Player> player = new AtomicReference<>();
            playerController.getPlayerData(mString, (foundPlayer, success) -> {
                if (success) {
                    player.set(foundPlayer);
                    userNameDisplay.setText(player.get().getUsername());
                    userTotalPoints.setText(String.valueOf(playerController.calculateTotalPoints(player.get())));
                    userTotalCodes.setText(String.valueOf(playerController.getTotalCodes(player.get())));
                } else {
                    Log.d(TAG, "Error finding player data");
                }
            });

            AtomicReference<List<Player>> playerList = new AtomicReference<>();
            playerController.getAllPlayerData((foundPlayers, listSuccess) -> {
                if (listSuccess) {
                    Log.d(TAG, "Found all player data");
                    playerList.set(foundPlayers);
                } else {
                    Log.d(TAG, "Failed to retrieve all player data");
                }
            });
            // Gets player QRCode info

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
                    Intent intent = new Intent(QuickNavActivity.this, ScoreboardActivity.class);
                    intent.putExtra("myPlayer", (Serializable) player.get());
                    intent.putExtra("playerList", (Serializable) playerList.get());
                    startActivity(intent);
                }
            });

            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuickNavActivity.this, ProfileActivity.class);
                    // Add the custom object as an extra to the Intent
                    intent.putExtra("myPlayer", (Serializable) player.get());
                    intent.putExtra("playerList", (Serializable) playerList.get());
                    startActivity(intent);
                }
            });


        }
    }
    @Override
    public void onBackPressed() {
// do nothing
    }
}