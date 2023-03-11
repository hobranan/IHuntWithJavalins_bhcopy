package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QRCodeDB is a class which handles all database operations for QRCode objects.
 * Much functionality is derived from Well Fed project example given by TA
 *
 * @version 1.0
 */
public class QRCodeDB {
    /**
     * Holds the tag for logging
     */
    private final static String TAG = "PlayerDB";
    /**
     * Holds the instance of the Firebase Firestore database
     */
    private final FirebaseFirestore db;
    /**
     * Holds the CollectionReference for the user collection
     */
    private CollectionReference collection;

    /**
     * Constructor for QRCodeDB class, initializes initial fields
     * @param connection the DBConnection object used to access the database
     */
    public QRCodeDB(DBConnection connection) {
        collection = connection.getSubCollection("QRCodes");
        db = connection.getDB();
    }

    /**
     * Switches the collection reference from one player to another
     * @param playerId given playerId to switch the collection reference to
     */
    public void switchFromPlayerToPlayerCodes(String playerId) {
        this.collection = db.collection("users").document("user" + playerId).collection("QRCodes");
    }

    public void addQRCode(@NonNull QRCode code, OnCompleteListener<QRCode> listener) {
        // creating batch and return value
        WriteBatch batch = db.batch();

        // add code info to batch
        String codeId = collection.document().getId();    // creates a new document with a unique firestore id for the code
        DocumentReference codeRef = collection.document(codeId);
        Map<String, Object> item = new HashMap<>();
        item.put("Name", code.getCodeName());
        item.put("Hash Value", code.getCodeHash());
        item.put("Img Ref", code.getCodeImageRef());
        item.put("Point Value", code.getCodePoints());
        batch.set(codeRef, item);

        // commits batch writes to firebase
        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "addCode:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + codeId);
                listener.onComplete(code, true);
            } else {
                Log.d(TAG, ":isFailure:" + codeId);
                listener.onComplete(code, false);
            }
        });
    }

    public void getCode(QRCode code, OnCompleteListener<QRCode> listener) {
        String codeId = code.getCodeId();
        DocumentReference codeRef = collection.document(codeId);
        codeRef.get().addOnCompleteListener(task -> {
            Log.d(TAG, "getIngredient:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + codeId);
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, ":exists:" + codeId);
                    QRCode foundCode = new QRCode();
                    foundCode.setCodeId(codeId);
                    foundCode.setCodeHash(document.getString("Hash Value"));
                    foundCode.setCodeName(document.getString("Name"));
                    foundCode.setCodePoints(document.getString("Point Value"));
                    foundCode.setCodeImageRef(document.getString("Img Ref"));
                    listener.onComplete(foundCode, true);
                } else {
                    Log.d(TAG, ":notExists:" + codeId);
                    listener.onComplete(null, false);
                }
            } else {
                Log.d(TAG, ":isFailure:" + codeId);
                listener.onComplete(null, false);
            }

        });
    }

    public void deleteCode(@NonNull QRCode code, OnCompleteListener<QRCode> listener) {
        WriteBatch batch = db.batch();

        DocumentReference codeDocument = collection.document(code.getCodeId());
        batch.delete(codeDocument);

        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "deleteCode:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + code.getCodeId());
                listener.onComplete(code, true);
            } else {
                Log.d(TAG, ":isFailure:" + code.getCodeId());
                listener.onComplete(code, false);
            }
        });
    }

    /**
     * Gets and returns a list of QRCodes from the collection in the database
     * Used Lab 5 Code to iterate over Document Snapshots.
     * Citation: How to get data from firestore https://firebase.google.com/docs/firestore/query-data/get-data#java_14
     * @return list of QRCodes from the database collection
     */
    public List<QRCode> getCodes() {
        List<QRCode> codeList = new ArrayList<>();
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) { // Re-add firestore collection sub-documents and sub-sub-collection items)
                    Log.d(TAG, (String) doc.getData().get("Name"));

                    QRCode newCode = new QRCode();
                    newCode.setCodeId((String) doc.getId());
                    newCode.setCodeName((String) doc.getData().get("Name"));
                    newCode.setCodePoints((String) doc.getData().get("Point Value"));
                    newCode.setCodeHash((String) doc.getData().get("Hash Value"));
                    newCode.setCodeImageRef((String) doc.getData().get("Img Ref"));
                    codeList.add(newCode);
                }
            }
        });

        return codeList;
    }

    public DocumentReference getDocumentReference(QRCode code) {
        return collection.document(code.getCodeId());
    }

    public Query getSortedCodes(Boolean ascending) {
        return collection.orderBy("Point Value", ascending ? Query.Direction.ASCENDING : Query.Direction.DESCENDING);    // Running .get on the Query should give you the sorted data
    }
}
