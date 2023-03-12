package com.example.ihuntwithjavalins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private Button confirmSignup;
    private EditText editText_signup_Username;
    private EditText editText_signup_Email;
    private Spinner spinnerRegion;
    private String username;
    private String userEmail;
    private String userRegion;
    private String userDateJoined;
    private String TAG = "Sample"; // used as starter string for debug-log messaging

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
        SpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        spinnerRegion.setAdapter(SpinAdapter);// Apply the adapter to the spinner


        confirmSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editText_signup_Username.getText().toString().trim();
                userEmail = editText_signup_Email.getText().toString().trim();
                userRegion = spinnerRegion.getSelectedItem().toString();

                if ( !Arrays.asList("Enter your username", "").contains(username) &
                !Arrays.asList("Enter your email", "").contains(userEmail) &
                !Arrays.asList("").contains(userRegion) )
                {
                    // https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
                    String pattern = "yyyyMMdd"; //String pattern = "MM/dd/yyyy HH:mm:ss";
                    DateFormat df = new SimpleDateFormat(pattern);
                    Date today = Calendar.getInstance().getTime();
                    userDateJoined = df.format(today);

                    // Access Firestore instance
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference collectionRef_Users = db.collection("Users");
                    final DocumentReference docRef_thisPlayer = collectionRef_Users.document(username);
                    docRef_thisPlayer.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (!document.exists()) {

                                    Log.d(TAG, "Document does not exist! New player, signing up");

                                    HashMap<String, String> dataMap = new HashMap<>();
                                    dataMap.put("Date Joined", userDateJoined);
                                    dataMap.put("Email", userEmail);
                                    dataMap.put("Region", userRegion);
                                    collectionRef_Users
                                            .document(username)
                                            .set(dataMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // set saved tag as savedUser so when app reopen you can recall this info and skip signup
                                                    SharedPreferences mPrefs = getSharedPreferences("Login", 0);
                                                    mPrefs.edit().putString("UsernameTag", username).apply();

                                                    Toast toast = Toast.makeText(getApplicationContext(), "Player new, signing up", Toast.LENGTH_LONG);
                                                    toast.show(); // display the Toast popup

                                                    switchToMain();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() { // log the failure on your console (if sending-action failed)
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //do nothing
                                                    Toast toast = Toast.makeText(getApplicationContext(), "firebase failed?", Toast.LENGTH_LONG);
                                                    toast.show(); // display the Toast popup
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "Document does exists! Old player, logging in");

                                    Toast toast = Toast.makeText(getApplicationContext(), "Player exists, logging in", Toast.LENGTH_LONG);
                                    toast.show(); // display the Toast popup

                                    // set saved tag as savedUser so when app reopen you can recall this info and skip signup
                                    SharedPreferences mPrefs = getSharedPreferences("Login", 0);
                                    mPrefs.edit().putString("UsernameTag", document.getId()).apply();

                                    switchToMain();

                                }
                            } else {
                                Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });
                } else {
                    // make pop-up warning about empty strings or list already has name
                    Toast toast = Toast.makeText(getApplicationContext(), "some info is empty", Toast.LENGTH_LONG);
                    toast.show(); // display the Toast popup
                }

            }

        });

    }

    void switchToMain() {
        Intent intent = new Intent(this, QuickNavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
