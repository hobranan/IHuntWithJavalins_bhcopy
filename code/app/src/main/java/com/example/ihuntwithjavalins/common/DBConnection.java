package com.example.ihuntwithjavalins.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * TODO: Update javadoc comments as functionality changed from uuid to username
 *
 * Connects to Database, gettings the users unique firestore ID to identify them.
 * Predominantly code is from Well Fed project
 *
 * @version 1.0
 */
public class DBConnection {
    /**
     * Holds tag for logging
     */
    private final static String myTAG = "DBConnector";
    /**
     * Holds instance of Firestore database
     */
    private FirebaseFirestore db;
    /**
     * Holds string representation of unique user username
     */
    private String playerUsername;

    /**
     * Constructor for the DBConnection, ie. Connects with the firestore database and gives the
     * user a unique randomly generated UUID
     * @param context the context of the application
     */
    public DBConnection(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.playerUsername = getUsername(context);
        Log.d(myTAG, "New Username:" + this.playerUsername);
    }

    /**
     * Sets the username in the shared preferences of the device to identify the user
     * @param context the context of the application
     * @param username the username to put into shared preferences
     */
    public void setUsername(Context context, String username) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);    // Opening Preference files Citation: https://developer.android.com/reference/android/content/Context#getApplicationContext()

        String foundUsername = sharedPreferences.getString("Username", null);    // second value null means return null if preference UUID does not exist

        if (foundUsername == null) {
            this.playerUsername = username; // added for intent tests, do not delete
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Username", playerUsername);
            editor.apply();
        }
    }

    /**
     * Gets the Username for the device to identify user
     * @param context the context of the application
     * @return the unique username if found, null if not found
     */
    public String getUsername(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);    // Opening Preference files Citation: https://developer.android.com/reference/android/content/Context#getApplicationContext()

        String username = sharedPreferences.getString("Username", null);    // second value null means return null if preference UUID does not exist

        return username;
    }

    /**
     * Gets the username of the user of the DBConnection
     * @return returns the username of the user
     */
    public String getUsername() {
        return this.playerUsername;
    }

    /**
     * Gets the reference to the a given subcollection within the user document
     * @param subCollection the given subCollection id
     * @return reference to given subcollection
     */
    public CollectionReference getSubCollection(String subCollection) {

        return this.db.collection("Users").document(playerUsername).collection(subCollection);

    }

    /**
     * Gets the reference to the collection of users of the application
     * @return reference to user collection
     */
    public CollectionReference getUserCollection() {
        return this.db.collection("Users");
    }

    /**
     * Gets the reference to the document of the current user
     * @return reference to the user document
     */
    public DocumentReference getUserDocument() {

        return this.db.collection("Users").document(playerUsername);
    }

    /**
     * Gets the instance of the Firebase Firestore database
     * @return the instance of the Firebase Firestore database
     */
    public FirebaseFirestore getDB() {
        return this.db;
    }
}