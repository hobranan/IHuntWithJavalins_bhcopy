package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * QRCodeDB is a class which handles all database operations for QRCode objects.
 * Much functionality is derived from Well Fed project example given by TA
 * Design Patterns:
 * factory pattern - DBConnection object defining db and collection
 * observer pattern - Use of OnCompleteListener
 * command pattern - addQRCode, getQRCode, and deleteCode all pass commands to QRCodeDB
 * singleton pattern - use of getINstance to access the FirebaseFirestore object
 *
 * @version 1.0
 */
public class QRCodeDB {
    /**
     * Holds the tag for logging
     */
    private final static String TAG = "QRCodeDB";
    /**
     * Holds the instance of the Firebase Firestore database
     */
    private FirebaseFirestore db;
    /**
     * Holds the CollectionReference for the user collection
     */
    private CollectionReference collection;

    /**
     * Constructor for QRCodeDB class, initializes initial fields
     *
     * @param connection the DBConnection object used to access the database
     */
    public QRCodeDB(DBConnection connection) {
        collection = connection.getSubCollection("QRCodesSubCollection");
        db = connection.getDB();
    }

    /**
     * Switches the collection reference from one player to another
     *
     * @param playerUsername given player's username to switch the collection reference to
     */
    public void switchFromPlayerToPlayerCodes(String playerUsername) {
        this.collection = db.collection("Users").document(playerUsername).collection("QRCodesSubCollection");
    }

    /**
     * Adds a new QRCode document to the database
     *
     * @param code     the code to add to the database
     * @param listener the listener to call when the code is added
     */
    public void addQRCode(@NonNull QRCode code, OnCompleteListener<QRCode> listener) {
        // creating batch and return value
        WriteBatch batch = db.batch();

        String hashValue = code.getCodeHash();
        // add code info to batch
        DocumentReference codeRef = collection.document(hashValue);
        Map<String, Object> item = new HashMap<>();
        item.put("Name", code.getCodeName());
        item.put("Img Ref", code.getCodeGendImageRef());
        item.put("Latitude", code.getCodeLat());
        item.put("Longitude", code.getCodeLon());
        item.put("Photo Ref", code.getCodePhotoRef());
        item.put("Point Value", code.getCodePoints());
        item.put("Code Date", code.getCodeDate());
        batch.set(codeRef, item);

        // commits batch writes to firebase
        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "addCode:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + hashValue);
                listener.onComplete(code, true);
            } else {
                Log.d(TAG, ":isFailure:" + hashValue);
                listener.onComplete(code, false);
            }
        });
    }

    /**
     * Overwrites(and creates new if not existing) QRCode in database based on HashMap
     *
     * @param code     The QRCode to data to update
     * @param dataMap  The map of the data fields to update to
     * @param listener the listener to call after overwriting
     */
    public void overwriteCode(QRCode code, HashMap<String, String> dataMap, OnCompleteListener<QRCode> listener) {
        collection.document(code.getCodeHash()).set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Data has been added successfully!");
                listener.onComplete(code, true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Data could not be added!" + e.toString());
                listener.onComplete(code, false);
            }
        });
    }

    /**
     * Gets the QRCode from the database(Use Lambda to retrieve)
     *
     * @param code     the code to retrieve from database
     * @param listener the listener to call after getting code
     */
    public void getCode(QRCode code, OnCompleteListener<QRCode> listener) {
        String hashValue = code.getCodeHash();
        DocumentReference codeRef = collection.document(hashValue);
        codeRef.get().addOnCompleteListener(task -> {
            Log.d(TAG, "getCode:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + hashValue);
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, ":exists:" + hashValue);
                    QRCode foundCode = new QRCode();
                    foundCode.setCodeHash(document.getId());
                    foundCode.setCodeName(document.getString("Code Name"));
                    foundCode.setCodeLat(document.getString("Latitude"));
                    foundCode.setCodeLon(document.getString("Longitude"));
                    foundCode.setCodePhotoRef(document.getString("Photo Ref"));
                    foundCode.setCodePoints(document.getString("Point Value"));
                    foundCode.setCodeGendImageRef(document.getString("Img Ref"));
                    foundCode.setCodeDate(document.getString("Code Date"));
                    listener.onComplete(foundCode, true);
                } else {
                    Log.d(TAG, ":notExists:" + hashValue);
                    listener.onComplete(null, false);
                }
            } else {
                Log.d(TAG, ":isFailure:" + hashValue);
                listener.onComplete(null, false);
            }

        });
    }

    /**
     * Deletes given code from the database
     *
     * @param code     the given code to delete
     * @param listener the listener to call when the code is deleted
     */
    public void deleteCode(@NonNull QRCode code, OnCompleteListener<QRCode> listener) {
        WriteBatch batch = db.batch();

        String hashValue = code.getCodeHash();
        DocumentReference codeDocument = collection.document(hashValue);
        batch.delete(codeDocument);

        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "deleteCode:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + hashValue);
                listener.onComplete(code, true);
            } else {
                Log.d(TAG, ":isFailure:" + hashValue);
                listener.onComplete(code, false);
            }
        });
    }

    /**
     * Gets and returns a list of QRCodes from the collection in the database
     * Used Lab 5 Code to iterate over Document Snapshots.
     * Citation: How to get data from firestore https://firebase.google.com/docs/firestore/query-data/get-data#java_14
     *
     * @param listener the on complete listener for a list of QRCodes
     */
    public void getCodes(OnCompleteListener<ArrayList<QRCode>> listener) {
        collection.get().addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<QRCode> codeList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Log.d(TAG, doc.getId() + " => " + doc.getData());

                        QRCode newCode = new QRCode();
                        newCode.setCodeHash(doc.getId());
                        newCode.setCodeName((String) doc.getData().get("Code Name"));
                        newCode.setCodePoints((String) doc.getData().get("Point Value"));
                        newCode.setCodePhotoRef((String) doc.getData().get("Photo Ref"));
                        newCode.setCodeLon((String) doc.getData().get("Longitude"));
                        newCode.setCodeLat((String) doc.getData().get("Latitude"));
                        newCode.setCodeGendImageRef((String) doc.getData().get("Img Ref"));
                        newCode.setCodeDate((String) doc.getData().get("Code Date"));
                        codeList.add(newCode);
                    }
                    listener.onComplete(codeList, true);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onComplete(null, false);
                }
            }
        });
    }

    /**
     * Gets and returns reference to QRCode document
     *
     * @param code given code to find document of
     * @return the document reference of QRCode in database
     */
    public DocumentReference getDocumentReference(QRCode code) {
        return collection.document(code.getCodeHash());
    }

    /**
     * Gets and returns the Query for sorted QRCodes based on Point Value in database
     * Citation: How to sort firestore query: https://firebase.google.com/docs/firestore/query-data/order-limit-data
     *
     * @param ascending true if sorting in ascending order, false if sorting in descending order
     * @return a query of the sorted QRCodes in the database
     */
    public Query getSortedCodes(Boolean ascending) {
        return collection.orderBy("Point Value", ascending ? Query.Direction.ASCENDING : Query.Direction.DESCENDING);    // Running .get on the Query should give you the sorted data
    }
}