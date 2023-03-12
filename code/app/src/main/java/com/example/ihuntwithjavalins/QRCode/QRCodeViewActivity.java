package com.example.ihuntwithjavalins.QRCode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QRCodeViewActivity extends AppCompatActivity {

    ImageButton backButton;
    ImageButton imageButton;
    ImageButton quickNavButton;

    Button deleteButton;

    TextView codeName;
    TextView codeHash;
    TextView codePoints;
//    String codePicRef;
//    ImageView codePicImage;

    String myTAG = "Sample"; // used as starter string for debug-log messaging
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.code_view_individ_owned);

        backButton = findViewById(R.id.go_back);
        quickNavButton = findViewById(R.id.imageButton);
        imageButton = findViewById(R.id.image_button);
        deleteButton = findViewById(R.id.btn_remove_code);


        codeName = findViewById(R.id.player_name);
        codeHash = findViewById(R.id.player_hash);
        codePoints = findViewById(R.id.player_points);

        Bundle extras = getIntent().getExtras();
        String savedCodeName = extras.getString("librarySavedCodeName");//The key argument here must match that used in the other activity
        String savedCodeHash = extras.getString("librarySavedCodeHash");//The key argument here must match that used in the other activity
        String savedCodePoints = extras.getString("librarySavedCodePoints");//The key argument here must match that used in the other activity
        String savedCodeImageRef = extras.getString("librarySavedCodeImageRef");//The key argument here must match that used in the other activity
        String savedCodeLat = extras.getString("librarySavedCodeLat");//The key argument here must match that used in the other activity
        String savedCodeLon = extras.getString("librarySavedCodeLon");//The key argument here must match that used in the other activity
        String savedCodePhotoRef = extras.getString("librarySavedCodePhotoRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Access a Cloud Firestore instance from your Activity
                    // my database name is ...  on the ... location
                    FirebaseFirestore db; // firestore database object (need to import library dependency)
                    db = FirebaseFirestore.getInstance(); // pull instance of database from firestore

                    // grabbed any store username variables within app local date storage
                    SharedPreferences mPrefs = getSharedPreferences("Login", 0);
                    String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

                    // setup library based on presence of Username tag (either preferences or global var (global var needs to have an open static var that doesnt die))
                    final CollectionReference collectionRef_Username = db.collection(mStringU); // pull instance of specific collection in firestore
                    final DocumentReference docRef_hashcode = collectionRef_Username.document(savedCodeHash);

                AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeViewActivity.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                docRef_hashcode
                                        .delete() // delete document from firebase database
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // These are a method which gets executed when the task is succeeded
                                                Log.d(myTAG, "Data has been deleted successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // These are a method which gets executed if there’s any problem
                                                Log.d(myTAG, "Data could not be deleted!" + e.toString());
                                            }
                                        });
                                BackActionToLibrary();
                                // Finish the activity
//                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();



                }
            });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeViewActivity.this, QRCodeImageViewActivity.class);
                intent.putExtra("imageSavedCodeName", savedCodeName);
                intent.putExtra("imageSavedCodeHash", savedCodeHash);
                intent.putExtra("imageSavedCodePoints", savedCodePoints);
                intent.putExtra("imageSavedCodeImageRef", savedCodeImageRef);
                intent.putExtra("imageSavedCodeLat", savedCodeLat);
                intent.putExtra("imageSavedCodeLon", savedCodeLon);
                intent.putExtra("imageSavedCodePhotoRef", savedCodePhotoRef);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackActionToLibrary();
            }
        });

        quickNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeViewActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        BackActionToLibrary();
    }

    void BackActionToLibrary(){
        Intent intent = new Intent(QRCodeViewActivity.this, QRCodeLibraryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
