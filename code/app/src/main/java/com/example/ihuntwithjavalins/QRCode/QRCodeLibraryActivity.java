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

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class QRCodeLibraryActivity extends AppCompatActivity {

    private Button codeLib_quickNav;
    private Button addExamplesButton; //testing//added lots of preloaded codes instantly
    private int captureOfSavedPosition; // captures UI-list position (when clicked)
    private ListView libraryList; // activity_main.xml's object for holding the UI-datalist (within content.xml)
    private ArrayAdapter<QRCode> customAdapter; // adapter (custom child class of Adapter) to link/use on backend-datalist

    private Player player;
    private ArrayList<QRCode> codeList = new ArrayList<>();// list of objects
    private String TAG = "Sample"; // used as starter string for debug-log messaging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.library_mine);

        codeLib_quickNav = findViewById(R.id.button_codelib_qn);
        addExamplesButton = findViewById(R.id.button_addHardcodes);
        addExamplesButton.setVisibility(View.VISIBLE);

        // Setup/link list to new adapter for linking data and UI
        libraryList = findViewById(R.id.code_list_listview); // grab UI-datalist var
        customAdapter = new CustomListForCustomAdapter(this, codeList); // create adapter (custom child class of Adapter) to link/use on backend-datalist
        libraryList.setAdapter(customAdapter);// Set the adapter for backend-datalist to be used with UI-datalist

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

        // Access a Firestore instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
        final CollectionReference collectionRef_Users = db.collection("Users"); // pull instance of specific collection in firestore
        final DocumentReference docRef_thisPlayer = collectionRef_Users.document(mStringU); // pull instance of specific collection in firestore
        final CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");

        // This listener will pull the firestore data into your android app (if you reopen the app)
        subColRef_Codes.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
            FirebaseFirestoreException error) {
                codeList.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
                    String codeHash = doc.getId();
                    String codeName = (String) doc.getData().get("Code Name");
                    String codePoints = (String) doc.getData().get("Point Value");
                    String codeImgRef = (String) doc.getData().get("Img Ref");
                    String codeLatValue = (String) doc.getData().get("Lat Value");
                    String codeLonValue = (String) doc.getData().get("Lon Value");
                    String codePhotoRef = (String) doc.getData().get("Photo Ref");
                    String codeDate = (String) doc.getData().get("Code Date:");
                    codeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
                }
                customAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });
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
                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("Code Name", temp.getCodeName());
                    dataMap.put("Point Value", temp.getCodePoints());
                    dataMap.put("Img Ref", temp.getCodeGendImageRef());
                    dataMap.put("Code Date", temp.getCodeDate());
                    Random randomizer = new Random();// fake ones (ualberta campus points) with random offsets
                    String latitude = String.valueOf(53.5269 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    String longitude = String.valueOf(-113.52740 + ( 0.0001 + (0.0009 - 0.0001) * randomizer.nextDouble()));
                    dataMap.put("Lat Value", latitude);
                    dataMap.put("Lon Value", longitude);
                    dataMap.put("Photo Ref", "20230311_115608.jpg");//testing
                    subColRef_Codes
                            .document(temp.getCodeHash())
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
            }
        });


        // This listener will allow the delete button option to appear when UI-list item is clicked on
        libraryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                captureOfSavedPosition = position; // capture list item's position
                QRCode item = customAdapter.getItem(captureOfSavedPosition);
                Intent intent = new Intent(QRCodeLibraryActivity.this, QRCodeViewActivity.class);
                intent.putExtra("savedItemObject", (Serializable) item);
                startActivity(intent);
            }
        });

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