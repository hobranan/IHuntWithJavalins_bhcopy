package com.example.ihuntwithjavalins.Camera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 * handles taking a photo and saving it to Firebase Storage as well as saving relevant information about the photo to a Firestore database.
 * Design Patterns:
 * factory pattern - firebase getInstance for instance of FirebaseStorage
 * observer pattern - firestore onSuccess and onFailure
 * singleton pattern - only 1 instance of firebase firestore throughout the class. Uses getinstance to ensure that.
 * */
public class PhotoTakeActivity extends AppCompatActivity {
    Button btnTakePhoto;
    Button btnSavePhoto;
    ImageView imageView;
    public static final int RequestPermissionCode = 1;

//    private Player player;
    private QRCode code;
    private String TAG = "Sample"; // used as starter string for debug-log messaging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo);
        btnTakePhoto = findViewById(R.id.button_takephoto);
        btnSavePhoto = findViewById(R.id.button_savephoto);
        imageView = findViewById(R.id.imageView_photo);
        btnSavePhoto.setVisibility(View.INVISIBLE);
        EnableRuntimePermission();

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

        // Access Firestore database instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");
        final DocumentReference docRef_thisPlayer = collectionRef_Users.document(mStringU);
        final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        code = (QRCode) myIntent.getSerializableExtra("savedCodeForPhotoTake");

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                btnSavePhoto.setVisibility(View.VISIBLE);
                startActivityForResult(intent, 7);
            }
        });

        btnSavePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSavePhoto.setVisibility(View.INVISIBLE);


                // https://stackoverflow.com/questions/12116092/android-random-string-generator
                final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
                final Random random = new Random();
                final StringBuilder sb = new StringBuilder(6);
                for (int i = 0; i < 6; ++i) {
                    sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
                }

                String photoname = mStringU + "_" + code.getCodeDate() + "_" + sb;

                HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                dataMap.put("Code Date", code.getCodeDate());
                dataMap.put("Code Name", code.getCodeName());
                dataMap.put("Img Ref", code.getCodeGendImageRef());
                dataMap.put("Point Value", code.getCodePoints());
                dataMap.put("Lat Value", code.getCodeLat());
                dataMap.put("Lon Value", code.getCodeLon());
                dataMap.put("Photo Ref", photoname);
                subColRef_Codes
                        .document(code.getCodeHash())// point to at document (hashcode) then...
                        .set(dataMap) // add key-value-pairs (to fields of document)
                        .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                            @Override
                            public void onSuccess(Void aVoid) {
                                // These are a method which gets executed when the task is succeeded
                                Log.d(TAG, "Data has been added successfully!");

                                // https://firebase.google.com/docs/storage/android/upload-files
                                // Get a non-default Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
                                FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
                                // Create a storage reference from our app
                                StorageReference storageRef = storage.getReference();
                                // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
                                String codePicRef = "UserPhotos/" + photoname;
                                StorageReference pathReference_pic = storageRef.child(codePicRef);

                                // Get the data from an ImageView as bytes
                                imageView.setDrawingCacheEnabled(true);
                                imageView.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // satisfies US 9.01 ?
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = pathReference_pic.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                        Intent intent = new Intent(PhotoTakeActivity.this, QRCodeLibraryActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // These are a method which gets executed if there’s any problem
                                Log.d(TAG, "Data could not be added!" + e.toString());
                            }
                        });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

        }
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoTakeActivity.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(PhotoTakeActivity.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(PhotoTakeActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PhotoTakeActivity.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PhotoTakeActivity.this, "Permission Canceled, Your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}