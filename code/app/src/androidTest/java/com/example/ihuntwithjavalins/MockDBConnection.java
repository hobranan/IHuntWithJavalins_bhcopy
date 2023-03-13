package com.example.ihuntwithjavalins;

import android.content.Context;

import com.example.ihuntwithjavalins.common.DBConnection;

public class MockDBConnection extends DBConnection {

    /**
     * Constructor for the DBConnection, ie. Connects with the firestore database
     *
     */
    public MockDBConnection() {
        super(null);
    }

    @Override
    public String getUsername() {
        String testDocument = "Database Testing";
        return testDocument;
    }
}
