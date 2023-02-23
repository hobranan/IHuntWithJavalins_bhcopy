package com.example.ihuntwithjavalins.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class DBConnector {
    private final static String TAG = "DBConnector";
    private FirebaseFirestore db;
    private String uuid;

    public DBConnector(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.uuid = getUUID(context);
        Log.d(TAG, "New UUID:" + this.uuid);
    }

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

    public CollectionReference getCollection(String subCollection) {
        return this.db.collection("users").document("user" + uuid).collection(subCollection);
    }

    public DocumentReference getUserDocument() {
        return this.db.collection("users").document("user" + uuid);
    }

    public FirebaseFirestore getDB() {
        return this.db;
    }
}
