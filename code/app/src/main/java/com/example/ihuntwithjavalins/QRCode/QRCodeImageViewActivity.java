package com.example.ihuntwithjavalins.QRCode;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.Camera.PhotoViewActivity;
import com.example.ihuntwithjavalins.MonsterID;
import com.example.ihuntwithjavalins.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

/**
 * The QRCodeImageViewActivity displays the image and information of a QRCode.
 * The activity receives a QRCode object as a SerializableExtra from the previous activity.
 * The image associated with the QRCode is retrieved from Firebase Storage and displayed in an ImageView.
 * The user can click on buttons to view a photo associated with the QRCode or view the location of the QRCode on a map.
 * The user can also click on a back button to return to the previous activity.
 */
public class QRCodeImageViewActivity extends AppCompatActivity {
    Button backButton;
    Button photoButton;
    Button geoButton;
    TextView codeName;
    TextView codePoints;
    ImageView codePicImage;
    private QRCode thisCode;
    /**
     * Initializes the activity and its components.
     * Retrieves the QRCode object from the previous activity and displays its information.
     * Retrieves the image associated with the QRCode from Firebase Storage and displays it in an ImageView.
     * Sets up the buttons to allow the user to view a photo associated with the QRCode or view its location on a map.
     * @param savedInstanceState The saved instance state of the activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.code_image_view);

        backButton = findViewById(R.id.civ_go_back);
        photoButton = findViewById(R.id.civ_show_attached);
        geoButton = findViewById(R.id.civ_no_geo_btn);
        codeName = findViewById(R.id.civ_qr_code_name);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        thisCode = (QRCode) myIntent.getSerializableExtra("savedItemObjectForImage");

        codeName.setText(thisCode.getCodeName());
        codePoints.setText(thisCode.getCodePoints());

        /** Portion reponsble for generating an image of the monster from the hashcode */
        MonsterID monsterID = new MonsterID();
        // Get the AssetManager object
        AssetManager assetManager = getAssets();
        monsterID.generateAndSetImage(codePicImage, thisCode.getCodeHash());

        // https://stackoverflow.com/questions/29801031/how-to-add-button-tint-programmatically
        if ( (thisCode.getCodePhotoRef() == null) || (thisCode.getCodePhotoRef().equals(""))){
            photoButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }
        if ( (thisCode.getCodeLat() == null) || (thisCode.getCodeLat().equals(""))){
            geoButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( (thisCode.getCodePhotoRef() != null) && (!thisCode.getCodePhotoRef().equals(""))){
                    Intent intent = new Intent(QRCodeImageViewActivity.this, PhotoViewActivity.class);
                    intent.putExtra("imageSavedCodePhotoRef", thisCode.getCodePhotoRef());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No photo taken for this code.", Toast.LENGTH_LONG);
                    toast.show(); // display the Toast popup
                }
            }
        });

        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!thisCode.getCodeLat().equals("") && !thisCode.getCodeLon().equals("") ){
                    Intent intent = new Intent(QRCodeImageViewActivity.this, CodeRefOpenStreetMapActivity.class);
                    intent.putExtra("imageSavedCode", (Serializable) thisCode);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No geolocation saved for this code.", Toast.LENGTH_LONG);
                    toast.show(); // display the Toast popup
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
