package com.example.ihuntwithjavalins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuickNavActivity extends AppCompatActivity {

//    TextView codeText;
//    TextView codeHash;
//    TextView codeName;
//    TextView codePoints;

    Button quickNavReturnButton;
    Button homeButton;
    Button cameraButton;
    Button mapButton;
    Button libraryButton;
    Button scoreboardButton;
    Button profileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_quick_navigation);
//        codeText = findViewById(R.id.textView_hosc_code);
//        codeHash = findViewById(R.id.textView_hosc_code2);
//        codeName = findViewById(R.id.textView_hosc_code3);
//        codePoints = findViewById(R.id.textView_hosc_code4);
        quickNavReturnButton = findViewById(R.id.FAButton_close_quickNav);
        homeButton = findViewById(R.id.button_qn_hs);
        cameraButton = findViewById(R.id.button_qn_scanCode);
        mapButton = findViewById(R.id.button_qn_map);
        libraryButton = findViewById(R.id.button_qn_cl);
        scoreboardButton = findViewById(R.id.button_qn_sb);
        profileButton = findViewById(R.id.button_qn_profile);


//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String value1 = extras.getString("cameraSavedCodeText");//The key argument here must match that used in the other activity
//            String value2 = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
//            String value3 = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
//            String value4 = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
//            codeText.setText(value1);
//            codeHash.setText(value2);
//            codeName.setText(value3);
//            codePoints.setText(value4);
//        } else {
//            codeText.setText("null text");
//            codeHash.setText("null hash");
//            codeName.setText("null name");
//            codePoints.setText("null point");
//        }

        quickNavReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, CameraActivity.class);
                startActivity(intent);

            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, OpenStreetMapActivity.class);
                startActivity(intent);

            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickNavActivity.this, MyCodeLibraryActivity.class);
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
//                Intent intent = new Intent(QuickNavActivity.this, ProfileActivity.class);
//                startActivity(intent);

            }
        });


    }
}