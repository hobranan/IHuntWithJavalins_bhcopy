package com.example.ihuntwithjavalins;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MyCodeLibraryActivity extends AppCompatActivity {

    Button codeLib_quickNav;
    Button addExButton;
    Button removeExButton;

    Button addButtonIndivid;
    Button removeButtonIndivid;
    Button removeButtonIndividCancel;


    int captureOfDeletePosition; // captures UI-list position (when clicked)

    ArrayList<String> text4hashlist = new ArrayList<>();
    ArrayList<String> hashlist = new ArrayList<>();
    ArrayList<String> pointslist = new ArrayList<>();
    ArrayList<String> nameslist = new ArrayList<>();
    ArrayList<QRCode> codeList = new ArrayList<>();// list of objects
    ListView libraryList; // activity_main.xml's object for holding the UI-datalist (within content.xml)
    ArrayAdapter<QRCode> customAdapter; // adapter (custom child class of Adapter) to link/use on backend-datalist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_code_library_activity);

        codeLib_quickNav = findViewById(R.id.button_codelib_qn);

        addExButton = findViewById(R.id.button_addHardcodes);
        addExButton.setVisibility(View.VISIBLE);
        removeExButton = findViewById(R.id.button_removeHardcodes);
        removeExButton.setVisibility(View.INVISIBLE);

        // Declare xml vars, and set button visibilities
        addButtonIndivid = findViewById(R.id.button_add_individ);
        removeButtonIndivid = findViewById(R.id.button_del_individ);
        removeButtonIndividCancel = findViewById(R.id.button_del_individ_cancel);
        addButtonIndivid.setVisibility(View.VISIBLE);
        removeButtonIndivid.setVisibility(View.GONE);
        removeButtonIndividCancel.setVisibility(View.GONE);

        // Setup/link City list to new adapter for linking data and UI
        libraryList = findViewById(R.id.code_list_listview); // grab UI-datalist var
        customAdapter = new CustomListForCustomAdapter(this, codeList); // create adapter (custom child class of Adapter) to link/use on backend-datalist
        libraryList.setAdapter(customAdapter);// Set the adapter for backend-datalist to be used with UI-datalist


        // Access a Cloud Firestore instance from your Activity
        // my database name is ...  on the ... location
        FirebaseFirestore db; // firestore database object (need to import library dependency)
        db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
        final CollectionReference collectionReference = db.collection("MyCodes"); // pull instance of specific collection in firestore
        final String TAG = "Sample"; // used as starter string for debug-log messaging

        codeLib_quickNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCodeLibraryActivity.this, QuickNavActivity.class);
                startActivity(intent);

            }
        });

        addExButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text4hashlist.addAll( Arrays.asList(
                        "asdasdffhsad",
                        "hashcode",
                        "Treat each line as a separate string",
                        "sdjldksfjlasdhfoiasdhofjasdoifmoiasdjfinjx",
                        "44181028",
                        "066341365154"
                ));
                for (int i = 0; i < text4hashlist.size(); i++) {
                    QRCode temp = new QRCode( text4hashlist.get(i));
                    codeList.add(temp);
                    hashlist.add(temp.getCodeHash());
                    pointslist.add(temp.getCodePoints());
                    nameslist.add(temp.getCodeName());

                    HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                    dataMap.put("Point Value", temp.getCodePoints()); // add key-value-pair for province (within subcollection of 'city name' document)
                    dataMap.put("Hash Value", temp.getCodeHash()); // add key-value-pair for province (within subcollection of 'city name' document)
                    collectionReference
                            .document(temp.getCodeName())// point to at city name then...
                            .set(dataMap) // add province key-value-pair (to sub-collection of document)
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

                }
//                removeExButton.setVisibility(View.VISIBLE);
                addExButton.setVisibility(View.INVISIBLE);
//                addButtonIndivid.setVisibility(View.VISIBLE);
            }
        });

        // This listener will pull the firestore data into your android app (if you reopen the app)
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
            FirebaseFirestoreException error) {

                codeList.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
//                    Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    String codeName = doc.getId();
                    String codePoints = (String) doc.getData().get("Point Value");
                    String codeHash = (String) doc.getData().get("Hash Value");
                    codeList.add(new QRCode(codeName, codePoints, codeHash));
                }
                customAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });

        Bundle extras = getIntent().getExtras();
        if ((extras != null)&(com.example.ihuntwithjavalins.CameraActivity.cameraFlag == 1)) {
            com.example.ihuntwithjavalins.CameraActivity.cameraFlag = 0;
            String value1 = extras.getString("cameraSavedCodeText");//The key argument here must match that used in the other activity
            String value2 = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
            String value3 = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
            String value4 = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity

            HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
            dataMap.put("Point Value:", value4); // add key-value-pair for province (within subcollection of 'city name' document)
            dataMap.put("Hash Value", value2); // add key-value-pair for province (within subcollection of 'city name' document)
            collectionReference
                    .document(value3)// point to at city name then...
                    .set(dataMap) // add province key-value-pair (to sub-collection of document)
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
        }

        addButtonIndivid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCodeLibraryActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        removeExButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExButton.setVisibility(View.VISIBLE);
                removeExButton.setVisibility(View.INVISIBLE);
            }
        });

        // This listener will allow the delete button option to appear when UI-list item is clicked on
        libraryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                captureOfDeletePosition = position; // capture list item's position
                                removeButtonIndivid.setVisibility(View.VISIBLE); // show delete button
                removeButtonIndividCancel.setVisibility(View.VISIBLE); // show delete-cancelling button

            }
        });

        // This listener will delete the selected UI-list item.
        removeButtonIndivid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                QRCode item = customAdapter.getItem(captureOfDeletePosition);
                customAdapter.remove(item); // delete current list entry
                removeButtonIndivid.setVisibility(View.GONE); // re-hide delete and delete-cancelling button
                removeButtonIndividCancel.setVisibility(View.GONE);
                customAdapter.notifyDataSetChanged(); // update UI-list adapter

                DocumentReference docRef = db.collection("MyCodes").document(String.valueOf(item.getCodeName()));
                docRef
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
                if (codeList.size() <=0 ) {
                    addExButton.setVisibility(View.VISIBLE);
                } else {
                    addExButton.setVisibility(View.INVISIBLE);
                }
            }


        });

        // This listener will re-hide the delete button (and its cancel-delete button) if you want to cancel the delete prompt.
        removeButtonIndividCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeButtonIndivid.setVisibility(View.GONE); // re-hide delete and delete-cancel button
                removeButtonIndividCancel.setVisibility(View.GONE); // re-hide delete button
            }
        });








//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String value1 = extras.getString("cameraSavedCodeText");//The key argument here must match that used in the other activity
//            String value2 = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
//            String value3 = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
//            String value4 = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
//            codeText.setText(value1);
//            codeHash.setText(value2);
//            codeName.setText(value3);
//            codePoints.setText(value4);
//        } else {
//            codeText.setText("null text");
//            codeHash.setText("null hash");
//            codeName.setText("null name");
//            codePoints.setText("null point");
//        }
    }

}