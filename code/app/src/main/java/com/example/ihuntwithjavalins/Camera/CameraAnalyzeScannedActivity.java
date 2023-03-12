package com.example.ihuntwithjavalins.Camera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CameraAnalyzeScannedActivity extends AppCompatActivity {

    private int alreadycaught_flag = 0;
    String savedHash;
    String savedName;
    String savedPoints;
    String savedImgRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access a Cloud Firestore instance from your Activity
        // my database name is ...  on the ... location
        FirebaseFirestore db; // firestore database object (need to import library dependency)
        db = FirebaseFirestore.getInstance(); // pull instance of database from firestore

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
        //        String mStringU = "YaBroNahSon"; // used with setting up other usernames quickly

        // setup library based on presence of Username tag (either preferences or global var (global var needs to have an open static var that doesnt die))
        final CollectionReference collectionRef_Username = db.collection(mStringU); // pull instance of specific collection in firestore

        Bundle extras = getIntent().getExtras();
        savedHash = extras.getString("cameraHash");//The key argument here must match that used in the other activity cameraName
        savedName = extras.getString("cameraName");//The key argument here must match that used in the other activity cameraName
        savedPoints = extras.getString("cameraPoints");//The key argument here must match that used in the other activity cameraName
        savedImgRef = extras.getString("cameraGenImgRef");//The key argument here must match that used in the other activity cameraName

//        ArrayList<QRCode> codeList = new ArrayList<>();// list of objects

        // This listener will pull the firestore data into your android app (if you reopen the app)
        collectionRef_Username.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
            FirebaseFirestoreException error) {
//                codeList.clear(); // Clear the old list
                String myCamTag = "myCamTag"; // used as starter string for debug-log messaging
                Log.d(myCamTag, savedHash);
                alreadycaught_flag = 0;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
                    String codeHash = doc.getId();
                    if (codeHash.equals("RegionDocument")) {
                        continue;
                    }
                    Log.d(myCamTag, codeHash);
                        if (savedHash.equals(codeHash)) {
                            alreadycaught_flag++;
                            Log.d(myCamTag, "already caught");
                            break;
                        }
                }
                if (alreadycaught_flag > 0) {
                    Intent intent = new Intent(CameraAnalyzeScannedActivity.this, CameraAlreadyCaughtActivity.class);
//                                                intent.putExtra("cameraSavedCodeText",barcodeText.getText().toString());
                    intent.putExtra("cameraSavedCodeHash", savedHash);
                    intent.putExtra("cameraSavedCodeName", savedName);
                    intent.putExtra("cameraSavedCodePoints", savedPoints);
                    intent.putExtra("cameraSavedCodeImageRef", savedImgRef);
//                    cameraFlag = 1;
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CameraAnalyzeScannedActivity.this, CameraCaughtNewActivity.class);
//                                                intent.putExtra("cameraSavedCodeText",barcodeText.getText().toString());
                    intent.putExtra("cameraSavedCodeHash", savedHash);
                    intent.putExtra("cameraSavedCodeName", savedName);
                    intent.putExtra("cameraSavedCodePoints", savedPoints);
                    intent.putExtra("cameraSavedCodeImageRef", savedImgRef);
//                    cameraFlag = 1;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

    }
}
