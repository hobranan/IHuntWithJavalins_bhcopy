package com.example.ihuntwithjavalins.QRCode;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.Camera.PhotoViewActivity;
import com.example.ihuntwithjavalins.MonsterID;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    TextView codeHash;
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
//        codeHash = findViewById(R.id.civ_hash_code_);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        thisCode = (QRCode) myIntent.getSerializableExtra("savedItemObjectForImage");

        codeName.setText(thisCode.getCodeName());
//        codeHash.setText(thisCode.getCodeHash());
        codePoints.setText(thisCode.getCodePoints());

        /** Portion reponsble for generating an image of the monster from the hashcode */
        MonsterID monsterID = new MonsterID();
        // Get the AssetManager object
        AssetManager assetManager = getAssets();
        monsterID.generateAndSetImage(codePicImage, thisCode.getCodeHash());

        // Get a non-default Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
        // Create a storage reference from our app (https://firebase.google.com/docs/storage/android/download-files)
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
//        String codePicRef = "GendImages/" + thisCode.getCodeGendImageRef();
//        StorageReference pathReference_pic = storageRef.child(codePicRef);
//
//        // convert pathRef_pic to bytes, then set image bitmap via bytes (https://firebase.google.com/docs/storage/android/download-files)
//        //final long ONE_MEGABYTE = 1024 * 1024;
//        final long ONE_POINT_FIVE_MEGABYTE = 1536 * 1536; // made this to get the .getBytes() limit larger (all pics are less than 1.5MB)
//        pathReference_pic.getBytes(ONE_POINT_FIVE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                codePicImage.setImageBitmap(bmp);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
//            }
//        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( (thisCode.getCodePhotoRef() != null) || (!thisCode.getCodePhotoRef().equals(""))){
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
//                    intent.putExtra("imageSavedCodeLat", thisCode.getCodeLat());
//                    intent.putExtra("imageSavedCodeLon", thisCode.getCodeLon());
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
