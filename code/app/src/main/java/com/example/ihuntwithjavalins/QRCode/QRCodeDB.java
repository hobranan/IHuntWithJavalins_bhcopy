package com.example.ihuntwithjavalins.QRCode;

import com.example.ihuntwithjavalins.common.DBConnection;
import com.google.firebase.firestore.CollectionReference;

import java.util.Collection;

// Quick note, this class adds to a subcollection the Player has in the database
public class QRCodeDB {
    private CollectionReference collection;
    public QRCodeDB(DBConnection connection) {
        collection = connection.getSubCollection("MyCodes");
    }
}
