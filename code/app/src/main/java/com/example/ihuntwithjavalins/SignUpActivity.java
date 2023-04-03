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

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerController;
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

/**
 * Class for the signup_login page that the user opens */
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
    private PlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_login);

        playerController = new PlayerController(this);
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

                    Player newUser = new Player(username, userEmail, userRegion, userDateJoined);
                    playerController.addUser(newUser, (addedUser, success) -> {
                        if (success) {
                            Toast toast = Toast.makeText(getApplicationContext(), "User Data Found, Signing In...", Toast.LENGTH_LONG);
                            toast.show();
                            switchToMain();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Sign In Failed, Please Try Again", Toast.LENGTH_LONG);
                            toast.show();
                            Log.d(TAG, "Adding Failed");
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
