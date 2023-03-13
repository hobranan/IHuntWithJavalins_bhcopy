package com.example.ihuntwithjavalins.QRCode;

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

import java.io.Serializable;

public class QRCodeViewActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton imageButton;
    private ImageButton quickNavButton;
    private Button deleteButton;
    private TextView codeName;
    private TextView codeHash;
    private TextView codePoints;
    private QRCode thisCode;
    private String TAG = "Sample"; // used as starter string for debug-log messaging

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

        // Get the intent from the previous acitvity
        Intent myIntent = getIntent();
        thisCode = (QRCode) myIntent.getSerializableExtra("savedItemObject");

        codeName.setText(thisCode.getCodeName());
        codeHash.setText(thisCode.getCodeHash());
        codePoints.setText(thisCode.getCodePoints());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // grabbed any store username variables within app local date storage
                SharedPreferences mPrefs = getSharedPreferences("Login", 0);
                String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
                // Access a Firestore instance
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionRef_Users = db.collection("Users");
                final DocumentReference docRef_thisPlayer = collectionRef_Users.document(mStringU);
                final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
                final DocumentReference docRef_hashcode = subColRef_Codes.document(thisCode.getCodeHash());

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
                                                Log.d(TAG, "Data has been deleted successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // These are a method which gets executed if there’s any problem
                                                Log.d(TAG, "Data could not be deleted!" + e.toString());
                                            }
                                        });
                                BackActionToLibrary();// Finish the activity
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
                intent.putExtra("savedItemObjectForImage", (Serializable) thisCode);
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

    void BackActionToLibrary() {
        Intent intent = new Intent(QRCodeViewActivity.this, QRCodeLibraryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
