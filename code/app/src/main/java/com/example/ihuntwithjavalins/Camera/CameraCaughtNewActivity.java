package com.example.ihuntwithjavalins.Camera;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
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

import com.example.ihuntwithjavalins.MonsterID;
import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The activity for displaying and confirming a QR code that has been scanned using the device camera.
 * Design Patterns:
 * builder pattern - HashMap<String, String>
 * factory pattern - object creation at Intent, SharedPreferences, FirebaseFirestore, ...
 * observer pattern - addOnSuccessListener(), addOnFailureListener()
 * TODO: this is used as the only gateway from camera scanning (implement later a distinguishment between new and old captured codes)
 */
public class CameraCaughtNewActivity extends AppCompatActivity {
    /**
     * TextView field that displays the name of the QR code.
     */
    private TextView codeName;
    /**
     * TextView field that displays the hash value of the QR code.
     */
    private TextView codeHash;
    /**
     * TextView field that displays the points earned from scanning the QR code.
     */
    private TextView codePoints;
    /**
     * ImageView field that displays the picture associated with the QR code.
     */
    private ImageView codePicImage;
    /**
     * CheckBox field for user to choose whether to save geolocation data associated with the QR code.
     */
    private CheckBox save_geolocation;
    /**
     * CheckBox field for user to choose whether to save photo taken of QR code.
     */
    private CheckBox save_photo;
    /**
     * Button for user to confirm the scan of the QR code.
     */
    private Button confirmButton;
    /**
     * String variable used as a starter string for debug-log messaging.
     */
    private String TAG = "Sample";
    /**
     * Player object representing the current user.
     */
    private Player player;
    /**
     * ArrayList of QRCode objects that represents the list of scanned QR codes.
     */
    private ArrayList<QRCode> codeList = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationClient;
    private Location thislocation;

    private boolean takePhotoFlag = false;

    /**
     * Called when the activity is starting. Sets the UI layout, initializes the UI components, and gets the QR code object from the previous activity's intent.
     *
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.scannedcode_new);
        codeName = findViewById(R.id.civ_qr_code_name);
        codeHash = findViewById(R.id.player_hash);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);
        save_geolocation = (CheckBox) findViewById(R.id.checkbox_geolocation);
        save_photo = (CheckBox) findViewById(R.id.checkbox_photo);
        confirmButton = findViewById(R.id.civ_confirm_button);

        // Get the intent from the prev Activity
        Intent myIntent = getIntent();
        QRCode thisCode = (QRCode) myIntent.getSerializableExtra("cameraSavedCodeObject");

        CameraScanActivity.cameraFlag = 0; //testing for camera
        codeName.setText(thisCode.getCodeName());
        codeHash.setText(thisCode.getCodeHash());
        codePoints.setText(thisCode.getCodePoints());

        onPictureTaken(thisCode);

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

        // Access Firestore database instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");
        final DocumentReference docRef_thisPlayer = collectionRef_Users.document(mStringU);
        final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");

        HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
        dataMap.put("Code Date", thisCode.getCodeDate());
        dataMap.put("Code Name", thisCode.getCodeName());
        dataMap.put("Img Ref", thisCode.getCodeGendImageRef());
        dataMap.put("Point Value", thisCode.getCodePoints());
        dataMap.put("Lat Value", "");
        dataMap.put("Lon Value", "");
        dataMap.put("Photo Ref", "");
        subColRef_Codes
                .document(thisCode.getCodeHash())// point to at document (hashcode) then...
                .set(dataMap) // add key-value-pairs (to fields of document)
                .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Data could not be added!" + e.toString());
                    }
                });



        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken()) // ignore this error
                .addOnSuccessListener(CameraCaughtNewActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d(TAG, "locationadded 2");
//                                        myGPS_point[0] = new GeoPoint(location.getLatitude(), location.getLongitude()); // current 'location tracker' point
//                                        //my location map point 'item'
//                                        OverlayItem myGPSoverlayItem = new OverlayItem("My Location", " ", myGPS_point[0]);
//                                        items.add(myGPSoverlayItem);
//                                        mapController.setCenter(myGPS_point[0]);//
                            // String longitude = String.valueOf(locationTrack.getLongitude());//*hide to prevent current location tracking (use fake below)
                            // String latitude = String.valueOf(locationTrack.getLatitude());
                            thislocation = location;
                        } else {
                            Log.d(TAG, "locationadded 3");
                            Toast.makeText(getApplicationContext(), "Cannot grab geolocation", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                HashMap<String, String> dataMap2 = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                dataMap2.put("Code Date", thisCode.getCodeDate());
                dataMap2.put("Code Name", thisCode.getCodeName());
                dataMap2.put("Img Ref", thisCode.getCodeGendImageRef());
                dataMap2.put("Point Value", thisCode.getCodePoints());

                if (save_geolocation.isChecked()) {
//                    Log.d(TAG, "locationadded 1");

//                    String longitude = String.valueOf(thislocation.getLongitude());//*hide to prevent current location tracking (use fake below)
//                    String latitude = String.valueOf(thislocation.getLatitude());


                    Random randomizer = new Random();// fake ones (ualberta campus points) with random offsets
                    String latitude = String.valueOf(53.5269 + (0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    String longitude = String.valueOf(-113.52740 + (0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    thisCode.setCodeLat(latitude);
                    thisCode.setCodeLon(longitude);
                    dataMap2.put("Lat Value", latitude);
                    dataMap2.put("Lon Value", longitude);

                } else {
                    Toast.makeText(getApplicationContext(), "Not saving geolocation.", Toast.LENGTH_SHORT).show();
                }
                if (save_photo.isChecked()) { //testing for photo saving
                    dataMap2.put("Photo Ref", "");
                    takePhotoFlag = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Not saving photo.", Toast.LENGTH_SHORT).show();
                }

                subColRef_Codes
                        .document(thisCode.getCodeHash())// point to at document (hashcode) then...
                        .set(dataMap2) // add key-value-pairs (to fields of document)
                        .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                            @Override
                            public void onSuccess(Void aVoid) {
                                // These are a method which gets executed when the task is succeeded
                                Log.d(TAG, "Data has been added successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // These are a method which gets executed if there’s any problem
                                Log.d(TAG, "Data could not be added!" + e.toString());
                            }
                        });

                if (takePhotoFlag) {
                    takePhotoFlag = false;
                    Intent intent = new Intent(CameraCaughtNewActivity.this, PhotoTakeActivity.class);
                    intent.putExtra("savedCodeForPhotoTake", (Serializable) thisCode);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CameraCaughtNewActivity.this, QuickNavActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });

//        // Get Firebase Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
//        // Create a storage reference from our app (https://firebase.google.com/docs/storage/android/download-files)
//        StorageReference storageRef = storage.getReference();
//        // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
//        String codePicRef = "GendImages/" + thisCode.getCodeGendImageRef();
//        StorageReference pathReference_pic = storageRef.child(codePicRef);
//        // convert pathRef_pic to bytes, then set image bitmap via bytes (https://firebase.google.com/docs/storage/android/download-files)
//        //final long ONE_MEGABYTE = 1024 * 1024;
//        final long ONE_POINT_FIVE_MEGABYTE = 1536 * 1536; // made this to get the .getBytes() limit larger (all pics are less than 1.5MB)
//        pathReference_pic.getBytes(ONE_POINT_FIVE_MEGABYTE).
//                addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes) {
//                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        codePicImage.setImageBitmap(bmp);
//                    }
//                }).
//                addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
//                    }
//                });

    }

    private void onPictureTaken(QRCode code) {
        String hashCode = code.getCodeHash();
        MonsterID monsterID = new MonsterID();
        // Get the AssetManager object
        AssetManager assetManager = getAssets();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            monsterID.generate(baos, hashCode, 0, assetManager);
        } catch (IOException e) {
            Toast.makeText(CameraCaughtNewActivity.this, "Failed to generate monster image", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] monsterData = baos.toByteArray();
        Bitmap monsterBitmap = BitmapFactory.decodeByteArray(monsterData, 0, monsterData.length);

        // Display the monster image in the ImageView
        codePicImage.setImageBitmap(monsterBitmap);
        Toast.makeText(CameraCaughtNewActivity.this, "Monster generated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
// do nothing
    }

}
