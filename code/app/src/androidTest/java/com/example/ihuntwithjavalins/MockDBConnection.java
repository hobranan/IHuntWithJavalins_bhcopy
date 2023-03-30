package com.example.ihuntwithjavalins;

import android.content.Context;
import android.util.Log;

import com.example.ihuntwithjavalins.common.DBConnection;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * MockDBConnection functions the same as DBConnection except initializes a username which is
 * not stored locally for testing purposes.
 */
public class MockDBConnection extends DBConnection {
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
     * Constructor for the DBConnection, ie. Connects with the firestore database
     *
     */
    public MockDBConnection() {
        super(null);
        this.db = FirebaseFirestore.getInstance();
        this.playerUsername = "Database Testing";
        Log.d(myTAG, "New Username:" + this.playerUsername);
    }

    @Override
    public String getUsername(Context context) {
        String testDocument = "Database Testing";
        return testDocument;
    }

    @Override
    public CollectionReference getSubCollection(String subCollection) {
        return this.db.collection("TestUsers").document(playerUsername).collection(subCollection);
    }

    @Override
    public CollectionReference getUserCollection() {
        return this.db.collection("TestUsers");
    }

    @Override
    public DocumentReference getUserDocument() {
        return this.db.collection("TestUsers").document(playerUsername);
    }

}
