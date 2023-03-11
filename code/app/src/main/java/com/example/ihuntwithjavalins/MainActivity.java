package com.example.ihuntwithjavalins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ihuntwithjavalins.map.MapActivity;

public class MainActivity extends AppCompatActivity {

    Button cameraButton;
    Button mapButton;
    Button quickNavButton;
    TextView codeText;
    TextView codeHash;
    TextView codeName;
    TextView codePoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homescreen);

        cameraButton = findViewById(R.id.button_hosc_scanCode);
        mapButton = findViewById(R.id.button_hosc_checkmap);
        quickNavButton = findViewById(R.id.FAButton_hosc_quickNav);


        codeText = findViewById(R.id.textView_hosc_code);
        codeHash = findViewById(R.id.textView_hosc_code2);
        codeName = findViewById(R.id.textView_hosc_code3);
        codePoints = findViewById(R.id.textView_hosc_code4);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value1 = extras.getString("cameraSavedCodeText");//The key argument here must match that used in the other activity
            String value2 = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
            String value3 = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
            String value4 = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
            codeText.setText(value1);
            codeHash.setText(value2);
            codeName.setText(value3);
            codePoints.setText(value4);
        } else {
            codeText.setText("null text");
            codeHash.setText("null hash");
            codeName.setText("null name");
            codePoints.setText("null point");
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);

            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });

        quickNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuickNavActivity.class);
                startActivity(intent);

            }
        });







    }
}