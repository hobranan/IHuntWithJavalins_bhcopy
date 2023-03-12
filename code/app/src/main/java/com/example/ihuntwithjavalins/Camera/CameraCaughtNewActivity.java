package com.example.ihuntwithjavalins.Camera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Map.LocationTrack;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.HashMap;
import java.util.Random;

public class CameraCaughtNewActivity extends AppCompatActivity {

//    ImageButton backButton;
//    ImageButton imageButton;
//    ImageButton quickNavButton;


    TextView codeName;
    TextView codeHash;
    TextView codePoints;
//    String codePicRef;

    ImageView codePicImage;

    CheckBox save_geolocation;
    CheckBox save_photo;

    Button confirmButton;

    String myTAG = "Sample"; // used as starter string for debug-log messaging

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scanned_code_new);

//        backButton = findViewById(R.id.go_back_);
//        quickNavButton = findViewById(R.id.imageButton);
//        imageButton = findViewById(R.id.image_button);
//        deleteButton = findViewById(R.id.btn_remove_code);

        codeName = findViewById(R.id.civ_qr_code_name);
        codeHash = findViewById(R.id.civ_hash_code_);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);

        save_geolocation = (CheckBox) findViewById(R.id.checkbox_geolocation);
        save_photo = (CheckBox) findViewById(R.id.checkbox_photo);

        confirmButton = findViewById(R.id.civ_confirm_button);

        Bundle extras = getIntent().getExtras();
        String savedCodeName;
        String savedCodeHash;
        String savedCodePoints;
        String savedCodeImageRef;
        String savedCodeLat;
        String savedCodeLon;
        String savedCodePhotoRef;


        // Access a Cloud Firestore instance from your Activity
        // my database name is ...  on the ... location
        FirebaseFirestore db; // firestore database object (need to import library dependency)
        db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
        // setup library based on presence of Username tag (either preferences or global var (global var needs to have an open static var that doesnt die))
        final CollectionReference collectionRef_Username = db.collection(mStringU); // pull instance of specific collection in firestore

//        if ((extras != null) & (CameraActivity.cameraFlag == 1)) {
        CameraScanActivity.cameraFlag = 0;
        savedCodeName = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
        savedCodeHash = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
        savedCodePoints = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
        savedCodeImageRef = extras.getString("cameraSavedCodeImageRef");//The key argument here must match that used in the other activity
//        String savedCodeLat = extras.getString("librarySavedCodeLat");//The key argument here must match that used in the other activity
//        String savedCodeLon = extras.getString("librarySavedCodeLon");//The key argument here must match that used in the other activity
//        String savedCodePhotoRef = extras.getString("librarySavedCodePhotoRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

        HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
        dataMap.put("Point Value", savedCodePoints); // add key-value-pair for province (within subcollection of 'city name' document)
        dataMap.put("Code Name", savedCodeName);
        dataMap.put("Img Ref", savedCodeImageRef);
        collectionRef_Username
                .document(savedCodeHash)// point to at document (hashcode) then...
                .set(dataMap) // add key-value-pairs (to fields of document)
                .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(myTAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(myTAG, "Data could not be added!" + e.toString());
                    }
                });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save_geolocation.isChecked()) {
                    // location tracker https://www.digitalocean.com/community/tutorials/android-location-api-tracking-gps
                    LocationTrack locationTrack = new LocationTrack(CameraCaughtNewActivity.this);
                    if (locationTrack.canGetLocation()) {
//                        String longitude = String.valueOf(locationTrack.getLongitude());//hide to prevent current location tracking (use fake below)
//                        String latitude = String.valueOf(locationTrack.getLatitude());
                        Random randomizer = new Random();// fake ones (ualberta campus points) with random offsets
                        String latitude = String.valueOf(53.5269 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                        String longitude = String.valueOf(-113.52740 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                        HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                        dataMap.put("Point Value", savedCodePoints); // add key-value-pair for province (within subcollection of 'city name' document)
                        dataMap.put("Code Name", savedCodeName);
                        dataMap.put("Img Ref", savedCodeImageRef);
                        dataMap.put("Lat Value", latitude); // add key-value-pair for province (within subcollection of 'city name' document)
                        dataMap.put("Lon Value", longitude);
                        if (save_photo.isChecked()) {
                            dataMap.put("Photo Ref", "20230311_115608.jpg"); //* example; delete later
                        }
                        collectionRef_Username
                                .document(savedCodeHash)// point to at document (hashcode) then...
                                .set(dataMap) // add key-value-pairs (to fields of document)
                                .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // These are a method which gets executed when the task is succeeded
                                        Log.d(myTAG, "Data has been added successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // These are a method which gets executed if there’s any problem
                                        Log.d(myTAG, "Data could not be added!" + e.toString());
                                    }
                                });
                    } else {
                        locationTrack.showSettingsAlert();
                        Toast.makeText(getApplicationContext(), "Cannot grab geolocation", Toast.LENGTH_SHORT).show();
                    }
                }


//                if (save_photo.isChecked()) {
//                   Intent intent = new Intent(CameraCaughtNewActivity.this, PhotoTakeActivity.class);
//                   intent.putExtra("photoTakeSavedCodeHash", savedCodeHash);
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(CameraCaughtNewActivity.this, QuickNavActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }

                    Intent intent = new Intent(CameraCaughtNewActivity.this, QuickNavActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }
        });


        // Get a non-default Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
        // Create a storage reference from our app (https://firebase.google.com/docs/storage/android/download-files)
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
        String codePicRef = "GendImages/" + savedCodeImageRef;
        StorageReference pathReference_pic = storageRef.child(codePicRef);

        // convert pathRef_pic to bytes, then set image bitmap via bytes (https://firebase.google.com/docs/storage/android/download-files)
        //final long ONE_MEGABYTE = 1024 * 1024;
        final long ONE_POINT_FIVE_MEGABYTE = 1536 * 1536; // made this to get the .getBytes() limit larger (all pics are less than 1.5MB)
        pathReference_pic.getBytes(ONE_POINT_FIVE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                codePicImage.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });


    }


}
