package com.example.ihuntwithjavalins;

import android.content.Context;

import com.example.ihuntwithjavalins.common.DBConnection;

/**
 * MockDBConnection functions the same as DBConnection except initializes a username which is
 * not stored locally for testing purposes.
 */
public class MockDBConnection extends DBConnection {

    /**
     * Constructor for the DBConnection, ie. Connects with the firestore database
     *
     */
    public MockDBConnection() {
        super(null);
    }

    @Override
    public String getUsername(Context context) {
        String testDocument = "Database Testing";
        return testDocument;
    }
}
