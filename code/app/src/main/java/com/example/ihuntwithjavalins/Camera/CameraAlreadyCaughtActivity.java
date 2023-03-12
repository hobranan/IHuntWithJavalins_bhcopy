package com.example.ihuntwithjavalins.Camera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CameraAlreadyCaughtActivity extends AppCompatActivity {

    ImageButton backButton;
//    ImageButton imageButton;
//    ImageButton quickNavButton;

//    Button deleteButton;

    TextView codeName;
    TextView codeHash;
    TextView codePoints;
//    String codePicRef;

    ImageView codePicImage;

    String myTAG = "Sample"; // used as starter string for debug-log messaging
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scanned_code_already_caught);

        backButton = findViewById(R.id.go_back_);
//        quickNavButton = findViewById(R.id.imageButton);
//        imageButton = findViewById(R.id.image_button);
//        deleteButton = findViewById(R.id.btn_remove_code);

        codeName = findViewById(R.id.nameofcode);
        codeHash = findViewById(R.id.hashofcode);
        codePoints = findViewById(R.id.pointsearned);
        codePicImage = findViewById(R.id.monstor);

        Bundle extras = getIntent().getExtras();
        String savedCodeName = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
        String savedCodeHash = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
        String savedCodePoints = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
        String savedCodeImageRef = extras.getString("cameraSavedCodeImageRef");//The key argument here must match that used in the other activity
//        String savedCodeLat = extras.getString("librarySavedCodeLat");//The key argument here must match that used in the other activity
//        String savedCodeLon = extras.getString("librarySavedCodeLon");//The key argument here must match that used in the other activity
//        String savedCodePhotoRef = extras.getString("librarySavedCodePhotoRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

//        // Access a Cloud Firestore instance from your Activity
//        // my database name is ...  on the ... location
//        FirebaseFirestore db; // firestore database object (need to import library dependency)
//        db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
//
//        // grabbed any store username variables within app local date storage
//        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
//        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
//        // setup library based on presence of Username tag (either preferences or global var (global var needs to have an open static var that doesnt die))
//        final CollectionReference collectionRef_Username = db.collection(mStringU); // pull instance of specific collection in firestore

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BackActionToLibrary();
                finish();
            }
        });

//        quickNavButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CameraAlreadyCaughtActivity.this, QuickNavActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });

    }

//    @Override
//    public void onBackPressed() {
//        BackActionToLibrary();
//    }

//    void BackActionToLibrary(){
//        Intent intent = new Intent(CameraAlreadyCaughtActivity.this, QRCodeLibraryActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

}
