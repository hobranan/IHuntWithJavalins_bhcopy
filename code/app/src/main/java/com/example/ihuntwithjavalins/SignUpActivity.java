package com.example.ihuntwithjavalins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Button confirmSignup;
    EditText editText_signup_Username;
    EditText editText_signup_Email;
    Spinner spinnerRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_login);

        confirmSignup = findViewById(R.id.button_signup_confirm);
        editText_signup_Username = findViewById(R.id.edittext_signup_username);
        editText_signup_Email = findViewById(R.id.edittext_signup_email);

        spinnerRegion = (Spinner) findViewById(R.id.spinner_signup_region);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> SpinAdapter = ArrayAdapter.createFromResource(this, R.array.regions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        SpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRegion.setAdapter(SpinAdapter);


        confirmSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db; // firestore database object (need to import library dependency)
                db = FirebaseFirestore.getInstance(); // pull instance of database from firestore
                String regionString = spinnerRegion.getSelectedItem().toString();
                String conCatRegion = "Region_" + regionString;

                if (!Arrays.asList("Region_", " ", "").contains(conCatRegion)) {

//                final CollectionReference collectionReference = db.collection("Region_Edmonton");
                    final CollectionReference collectionReference = db.collection(conCatRegion); // pull instance of specific collection in firestore

                    HashMap<String, String> dataMap = new HashMap<>(); //setup temp key-value mapping (to throw list items at firestore)
                    collectionReference
                            .document(editText_signup_Username.getText().toString()) // point to at city name then...
                            .set(dataMap) // add province key-value-pair (to sub-collection of document)
                            .addOnSuccessListener(new OnSuccessListener<Void>() { // log the success on your console (this helps you verify that the firestore sending-action worked)
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
//                                Log.d(TAG, "Data has been added successfully!");
                                    Player thisUser = new Player(editText_signup_Username.toString().trim(), spinnerRegion.getSelectedItem().toString());
                                    // set saved tag as savedUser so when app reopen you can recall this info and skip signup
                                 /*
                                If you are using Android Studio 3.0 or later version then follow these steps.
                                 Click View > Tool Windows > Device File Explorer.
                                 Expand /data/data/[package-name] nodes.
                                 It will be located at /data/data/com.your.package.name/shared_prefs/X.xml .
                                 You can just delete that file from the location.
                                 Also check /data/data/com.your.package.name/shared_prefs/X.bak file,
                                 and if it exists, delete it too. But be aware, that
                                 SharedPreferences instance saves all data in memory.
                                 */
                                    SharedPreferences mPrefs = getSharedPreferences("Login", 0);
                                    String uname = editText_signup_Username.getText().toString().trim();
                                    mPrefs.edit().putString("UsernameTag", uname).apply();
                                    mPrefs.edit().putString("RegionTag", conCatRegion).apply();

                                    switchToMain(uname);
                                }
                            }).addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
//                                Log.d(TAG, "Data could not be added!" + e.toString());

                                    //do nothing
                                }
                            });
                } else {
                    // make pop-up warning about empty strings or list already has name
                    Toast toast = Toast.makeText(getApplicationContext(), "Region is empty", Toast.LENGTH_LONG);
                    toast.show(); // display the Toast popup
                }


            }

        });
    }

    void switchToMain(String strUserName) {
        Intent intent = new Intent(this, QuickNavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("SavedUsername",strUserName);
        startActivity(intent);
    }


}
