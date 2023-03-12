package com.example.ihuntwithjavalins.QRCode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class QRCodeLibraryActivity extends AppCompatActivity {

    Button codeLib_quickNav;
    Button addExamplesButton;
//    Button removeExButton;
    Button addButtonIndivid; // camera
//    Button removeButtonIndivid;
//    Button removeButtonIndividCancel;
    int captureOfSavedPosition; // captures UI-list position (when clicked)
//    ArrayList<String> text4hashlist = new ArrayList<>();
//    ArrayList<String> hashlist = new ArrayList<>();
//    ArrayList<String> pointslist = new ArrayList<>();
//    ArrayList<String> nameslist = new ArrayList<>();
    ArrayList<QRCode> codeList = new ArrayList<>();// list of objects
    ListView libraryList; // activity_main.xml's object for holding the UI-datalist (within content.xml)
    ArrayAdapter<QRCode> customAdapter; // adapter (custom child class of Adapter) to link/use on backend-datalist

//    SharedPreferences mPrefs = getSharedPreferences("Login", 0);
//    static String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
//    static String mStringR = mPrefs.getString("RegionTag", "default_region");
String myTAG = "Sample"; // used as starter string for debug-log messaging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.library_mine);

        codeLib_quickNav = findViewById(R.id.button_codelib_qn);

        addExamplesButton = findViewById(R.id.button_addHardcodes);
        addExamplesButton.setVisibility(View.VISIBLE);
//        removeExButton = findViewById(R.id.button_removeHardcodes);
//        removeExButton.setVisibility(View.INVISIBLE);

//        addButtonIndivid = findViewById(R.id.button_add_individ);
//        addButtonIndivid.setVisibility(View.VISIBLE);
//        removeButtonIndivid = findViewById(R.id.button_del_individ);
//        removeButtonIndivid.setVisibility(View.GONE);
//        removeButtonIndividCancel = findViewById(R.id.button_del_individ_cancel);
//        removeButtonIndividCancel.setVisibility(View.GONE);

        // Setup/link list to new adapter for linking data and UI
        libraryList = findViewById(R.id.code_list_listview); // grab UI-datalist var
        customAdapter = new CustomListForCustomAdapter(this, codeList); // create adapter (custom child class of Adapter) to link/use on backend-datalist
        libraryList.setAdapter(customAdapter);// Set the adapter for backend-datalist to be used with UI-datalist


        // Access a Cloud Firestore instance from your Activity
        // my database name is ...  on the ... location
        FirebaseFirestore db; // firestore database object (need to import library dependency)
        db = FirebaseFirestore.getInstance(); // pull instance of database from firestore

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
//        String mStringU = "YaBroNahSon"; // used with setting up other usernames quickly
//        String mStringR = mPrefs.getString("RegionTag", "default_region");

        // setup library based on presence of Username tag (either preferences or global var (global var needs to have an open static var that doesnt die))
        final CollectionReference collectionRef_Username = db.collection(mStringU); // pull instance of specific collection in firestore

        addExamplesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> exText4hashlist = new ArrayList<>();
                exText4hashlist.addAll(Arrays.asList(
                        "adsfadsgasga",
                        "tyurtyurtyu",
                        "89678kfuykr",
                        "678567dfghsdfgh",
                        "1234asdfasdf"
                ));
                for (int i = 0; i < exText4hashlist.size(); i++) {
                    QRCode temp = new QRCode(exText4hashlist.get(i));
                    codeList.add(temp);
                    HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                    dataMap.put("Code Name", temp.getCodeName());  // add key-value-pair for province (within subcollection of document)
                    dataMap.put("Point Value", temp.getCodePoints());
                    dataMap.put("Img Ref", temp.getCodeGendImageRef());
                    Random randomizer = new Random();// fake ones (ualberta campus points) with random offsets
                    String latitude = String.valueOf(53.5269 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    String longitude = String.valueOf(-113.52740 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    dataMap.put("Lat Value", latitude);
                    dataMap.put("Lon Value", longitude);
                    String savedCodePhotoRef = "20230311_115608.jpg"; // testing
                    dataMap.put("Photo Ref", savedCodePhotoRef);
                    collectionRef_Username
                            .document(temp.getCodeHash())
                            .set(dataMap) // add province key-value-pair (to sub-collection of document)
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
                }
            }
        });

        // This listener will pull the firestore data into your android app (if you reopen the app)
        collectionRef_Username.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
            FirebaseFirestoreException error) {
                codeList.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
                    String codeHash = doc.getId();
                    if (codeHash.equals("RegionDocument")){
                        continue;
                    }
                    String codeName = (String) doc.getData().get("Code Name");
                    String codePoints = (String) doc.getData().get("Point Value");
                    String codeImgRef = (String) doc.getData().get("Img Ref");
                    String codeLatValue = (String) doc.getData().get("Lat Value");
                    String codeLonValue = (String) doc.getData().get("Lon Value");
                    String codePhotoRef = (String) doc.getData().get("Photo Ref");
                    String codeDate = (String) doc.getData().get("Date:");
                    codeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
                }
                customAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });



//        addButtonIndivid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(QRCodeLibraryActivity.this, CameraActivity.class);
//                startActivity(intent);
//            }
//        });

//        removeExButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addExamplesButton.setVisibility(View.VISIBLE);
//                removeExButton.setVisibility(View.INVISIBLE);
//            }
//        });

        // This listener will allow the delete button option to appear when UI-list item is clicked on
        libraryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                captureOfSavedPosition = position; // capture list item's position

//                                removeButtonIndivid.setVisibility(View.VISIBLE); // show delete button
//                removeButtonIndividCancel.setVisibility(View.VISIBLE); // show delete-cancelling button

                Intent intent = new Intent(QRCodeLibraryActivity.this, QRCodeViewActivity.class);
                QRCode item = customAdapter.getItem(captureOfSavedPosition);
                intent.putExtra("librarySavedCodeHash", item.getCodeHash());
                intent.putExtra("librarySavedCodeName", item.getCodeName());
                intent.putExtra("librarySavedCodePoints", item.getCodePoints());
                intent.putExtra("librarySavedCodeImageRef", item.getCodeGendImageRef());
                intent.putExtra("librarySavedCodeLat", item.getCodeLat());
                intent.putExtra("librarySavedCodeLon", item.getCodeLon());
                intent.putExtra("librarySavedCodePhotoRef", item.getCodePhotoRef());
                startActivity(intent);
            }
        });


        // This listener will delete the selected UI-list item.
//        removeButtonIndivid.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                QRCode item = customAdapter.getItem(captureOfDeletePosition);
//                customAdapter.remove(item); // delete current list entry
//                removeButtonIndivid.setVisibility(View.GONE); // re-hide delete and delete-cancelling button
//                removeButtonIndividCancel.setVisibility(View.GONE);
//                customAdapter.notifyDataSetChanged(); // update UI-list adapter
//                String tempCodeName = String.valueOf(item.getCodeName());
//                DocumentReference docRef = collectionRefDocRefCol.document(tempCodeName);
//                docRef
//                        .delete() // delete document from firebase database
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // These are a method which gets executed when the task is succeeded
//                                Log.d(myTAG, "Data has been deleted successfully!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // These are a method which gets executed if there’s any problem
//                                Log.d(myTAG, "Data could not be deleted!" + e.toString());
//                            }
//                        });
//                if (codeList.size() <= 0) {
//                    addExamplesButton.setVisibility(View.VISIBLE);
//                } else {
//                    addExamplesButton.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

        // This listener will re-hide the delete button (and its cancel-delete button) if you want to cancel the delete prompt.
//        removeButtonIndividCancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                removeButtonIndivid.setVisibility(View.GONE); // re-hide delete and delete-cancel button
//                removeButtonIndividCancel.setVisibility(View.GONE); // re-hide delete button
//            }
//        });

        codeLib_quickNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeLibraryActivity.this, QuickNavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


}