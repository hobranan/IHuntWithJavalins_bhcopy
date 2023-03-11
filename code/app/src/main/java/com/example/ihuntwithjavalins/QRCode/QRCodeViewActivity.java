package com.example.ihuntwithjavalins.QRCode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class QRCodeViewActivity extends AppCompatActivity {

    ImageButton backButton;
    ImageButton imageButton;
    ImageButton quickNavButton;

    TextView codeName;
    TextView codeHash;
    TextView codePoints;
//    String codePicRef;
//    ImageView codePicImage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.code_view_individ_owned);

        backButton = findViewById(R.id.go_back);
        quickNavButton = findViewById(R.id.imageButton);
        imageButton = findViewById(R.id.image_button);

        codeName = findViewById(R.id.player_name);
        codeHash = findViewById(R.id.player_hash);
        codePoints = findViewById(R.id.player_points);

        Bundle extras = getIntent().getExtras();
        String savedCodeName = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
        String savedCodeHash = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
        String savedCodePoints = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
        String savedCodeImageRef = extras.getString("cameraSavedCodeImageRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        quickNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeViewActivity.this, QuickNavActivity.class);
                startActivity(intent);

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeViewActivity.this, QRCodeImageViewActivity.class);
                intent.putExtra("cameraSavedCodeName", savedCodeName);
                intent.putExtra("cameraSavedCodeHash", savedCodeHash);
                intent.putExtra("cameraSavedCodePoints", savedCodePoints);
                intent.putExtra("cameraSavedCodeImageRef", savedCodeImageRef);
                startActivity(intent);

            }
        });


    }
}
