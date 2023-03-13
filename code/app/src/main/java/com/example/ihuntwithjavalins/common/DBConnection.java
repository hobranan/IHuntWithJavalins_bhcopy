package com.example.ihuntwithjavalins.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

/**
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
     * Holds string representation of unique user id
     */
    private String uuid;

    /**
     * Constructor for the DBConnection, ie. Connects with the firestore database and gives the
     * user a unique randomly generated UUID
     * @param context the context of the application
     */
    public DBConnection(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.uuid = getUUID(context);
        Log.d(myTAG, "New UUID:" + this.uuid);
    }

    /**
     * Gets the UUID for the device to identify user and randomly generates one if not existing already
     * @param context the context of the application
     * @return the unique user id
     */
    public String getUUID(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);    // Opening Preference files Citation: https://developer.android.com/reference/android/content/Context#getApplicationContext()

        String uuid = sharedPreferences.getString("UUID", null);    // second value null means return null if preference UUID does not exist

        if (uuid == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            uuid = UUID.randomUUID().toString();    // generates a random UUID for the user, not sure if this will cause a problem if it creates the same UUID by chance for 2 seperate users
            editor.putString("UUID", uuid);
            editor.apply();
        }

        return uuid;
    }

    /**
     * Gets the reference to the a given subcollection within the user document
     * @param subCollection the given subCollection id
     * @return reference to given subcollection
     */
    public CollectionReference getSubCollection(String subCollection) {
        return this.db.collection("Users").document(uuid).collection(subCollection);
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
        return this.db.collection("Users").document(uuid);
    }

    /**
     * Gets the instance of the Firebase Firestore database
     * @return the instance of the Firebase Firestore database
     */
    public FirebaseFirestore getDB() {
        return this.db;
    }
}
