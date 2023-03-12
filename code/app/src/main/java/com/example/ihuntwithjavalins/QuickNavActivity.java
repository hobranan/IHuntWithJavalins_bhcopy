package com.example.ihuntwithjavalins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Camera.CameraScanActivity;
import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;

import java.io.Serializable;
import java.util.Arrays;

public class QuickNavActivity extends AppCompatActivity {

//    TextView codeText;
//    TextView codeHash;
//    TextView codeName;
//    TextView codePoints;

    //    Button quickNavReturnButton;
//    Button homeButton;
    Button cameraButton;
    Button mapButton;
    Button libraryButton;
    Button scoreboardButton;
    Button profileButton;

    TextView userNameDisplay;
    TextView userTotalPoints;
    TextView userTotalCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // grabbed any device stored username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mString = mPrefs.getString("UsernameTag", "default_username_not_found");
        // open signup activity
        if (Arrays.asList("default_username_not_found", "Enter a new Username:", " ", "").contains(mString)) {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        setContentView(R.layout.quick_navigation);

        userNameDisplay = findViewById(R.id.tv_userNameDisplay);
        userTotalPoints = findViewById(R.id.tv_userTotalPoints);
        userTotalCodes = findViewById(R.id.tv_userTotalCodes);

        cameraButton = findViewById(R.id.button_qn_scanCode);
        mapButton = findViewById(R.id.button_qn_map);
        libraryButton = findViewById(R.id.button_qn_cl);
        scoreboardButton = findViewById(R.id.button_qn_sb);
        profileButton = findViewById(R.id.button_qn_profile);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String strUserName = extras.getString("SavedUsername");//The key argument here must match that used in the other activity
            userNameDisplay.setText(strUserName);
        } else {
            userNameDisplay.setText(mString);
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