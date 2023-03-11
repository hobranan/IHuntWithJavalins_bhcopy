package com.example.ihuntwithjavalins.Player;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeDB;
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
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PlayerDB is a class which handles all database operations for Player objects.
 * Much functionality is derived from Well Fed project example given by TA
 *
 * @version 1.0
 */
public class PlayerDB {
    /**
     * Holds the tag for logging
     */
    private final static String TAG = "PlayerDB";
    /**
     * Holds the instance of the Firebase Firestore database
     */
    private final FirebaseFirestore db;
    /**
     * Holds the instance of the QRCodeDB
     */
    private QRCodeDB codeDB;
    /**
     * Holds the CollectionReference for the user collection
     */
    private CollectionReference collection;
    /**
     * Holds the string of userId
     */
    private String userId;

    /**
     * Constructor for the PlayerDB class, initializes declared fields
     * @param connection the DBConnection object used to access the database
     */
    public PlayerDB(DBConnection connection){
        // Gets the database and user collection based on current connection
        db = connection.getDB();
        collection = connection.getUserCollection();

        // Create new instance of QRCodeDB based on current connection
        codeDB = new QRCodeDB(connection);
        userId = connection.getUserDocument().getId();
    }

    /**
     * Adds a player to the database(Use lambda to retrieve)
     * Citation: Batch Update Info https://cloud.google.com/firestore/docs/samples/firestore-data-batch-writes
     * @param player the Player object being added to the database
     * @param listener the listener to call when the player is added
     */
    public void addPlayer(@NonNull Player player, OnCompleteListener<Player> listener) {
        // creating batch and return value
        WriteBatch batch = db.batch();

        // add player info to batch
        String playerId = player.getId();
        DocumentReference playerRef = collection.document("user" + playerId);
        Map<String, Object> item = new HashMap<>();
        item.put("Username", player.getUsername());
        item.put("Email", player.getEmail());
        item.put("Phone Number", player.getPhoneNumber());
        item.put("Region", player.getRegion());
        //item.put("highest score", 0);
        //item.put("total score", 0);
        batch.update(playerRef, item);

        // commits batch writes to firebase
        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "addPlayer:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + playerId);
                listener.onComplete(player, true);
            } else {
                Log.d(TAG, ":isFailure:" + playerId);
                listener.onComplete(player, false);
            }
        });
    }
    /**
     * Gets the player from the database(Use lambda to retrieve)
     * @param uuid the uuid of player being accessed
     * @param listener the listener to call when the player is found
     */
    public void getPlayer(String uuid, OnCompleteListener<Player> listener) {
        DocumentReference playerRef = collection.document("user" + uuid);
        playerRef.get().addOnCompleteListener(task -> {
            String id = playerRef.getId();
            Log.d(TAG, "getPlayer:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + id);
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, ":exists:" + id);
                    Player player = new Player();
                    player.setUsername(document.getString("Username"));
                    player.setEmail(document.getString("Email"));
                    player.setRegion(document.getString("Region"));
                    player.setPhoneNumber(document.getString("Phone Number"));
                    player.setId(id);
                    listener.onComplete(player, true);
                } else {
                    Log.d(TAG, ":notExists:" + id);
                    listener.onComplete(null, false);
                }
            } else {
                Log.d(TAG, ":isFailure:" + id);
                listener.onComplete(null, false);
            }

        });
    }

    /**
     * Deletes the player from the database(Use lambda to retrieve)
     * @param player the Player object representing the player to delete from database
     * @param listener the listener to call when the player is deleted
     */
    public void deletePlayer(@NonNull Player player, OnCompleteListener<Player> listener) {
        WriteBatch batch = db.batch();

        DocumentReference userDocument = collection.document("user" + player.getId());
        batch.delete(userDocument);

        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "deletePlayer:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + player.getId());
                listener.onComplete(player, true);
            } else {
                Log.d(TAG, ":isFailure:" + player.getId());
                listener.onComplete(player, false);
            }
        });
    }

    /**
     * Updates the given player's username in the database
     * @param player the given player to update
     * @param newUsername the new username
     * @param listener the listener to call when the player is updated
     */
    public void updatePlayerUsername(Player player, String newUsername, OnCompleteListener<Player> listener) {
        String uuid = player.getId();
        DocumentReference playerRef = collection.document("user" + uuid);
        playerRef
                .update("Username", newUsername)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        player.setUsername(newUsername);
                        listener.onComplete(player, true);
                        Log.d(TAG, "Username successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(player, false);
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    /**
     * Updates the given player's email in the database
     * @param player the given player to update
     * @param newEmail the new email
     * @param listener the listener to call when the player is updated
     */
    public void updatePlayerEmail(Player player, String newEmail, OnCompleteListener<Player> listener) {
        String uuid = player.getId();
        DocumentReference playerRef = collection.document("user" + uuid);
        playerRef
                .update("Email", newEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        player.setUsername(newEmail);
                        listener.onComplete(player, true);
                        Log.d(TAG, "Email successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(player, false);
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    /**
     * Updates the given player's phone number in the database
     * @param player the given player to update
     * @param newContact the new phone number
     * @param listener the listener to call when the player is updated
     */
    public void updatePlayerPhoneNumber(Player player, String newContact, OnCompleteListener<Player> listener) {
        String uuid = player.getId();
        DocumentReference playerRef = collection.document("user" + uuid);
        playerRef
                .update("Phone Number", newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        player.setUsername(newContact);
                        listener.onComplete(player, true);
                        Log.d(TAG, "Phone Number successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(player, false);
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    /**
     * Updates the given player's phone number in the database
     * @param player the given player
     * @param newRegion the new region
     * @param listener the listener to call when the player is updated
     */
    public void updatePlayerRegion(Player player, String newRegion, OnCompleteListener<Player> listener) {
        String uuid = player.getId();
        DocumentReference playerRef = collection.document("user" + uuid);
        playerRef
                .update("Region", newRegion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        player.setUsername(newRegion);
                        listener.onComplete(player, true);
                        Log.d(TAG, "Region successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(player, false);
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    /**
     * Gets reference to player document
     * @param player the Player object's document to be retrieved
     * @return reference to given player document
     */
    public DocumentReference getDocumentReference(Player player) {
        return collection.document("user" + player.getId());
    }

    /**
     * Gets query of sorted player documents(Use .get to retrieve data)
     * @param field the field to sort player documents by
     * @param ascending true if sorting in ascending order, false if sorting in descending order
     * @return the query of sorted player documents
     */
    public Query getSortedPlayers(String field, Boolean ascending) {
        return collection.orderBy(field, ascending ? Query.Direction.ASCENDING : Query.Direction.DESCENDING);    // Running .get on the Query should give you the sorted data
    }

    /**
     * Adds given code to player code collection in database
     * @param code given code to add
     * @param listener the listener to call when code is added
     */
    public void playerAddQRCode(QRCode code, OnCompleteListener<QRCode> listener) {
        codeDB.getCode(code, (foundCode, success) -> {
            if (foundCode == null) {
                codeDB.addQRCode(code, (addedCode, success2) -> {
                    if (addedCode != null) {
                        listener.onComplete(addedCode, true);
                    } else {
                        listener.onComplete(null, false);
                    }
                });
            }
        });
    }

    /**
     * Deletes given code from player code collection in database
     * @param code given code to delete
     * @param listener the listener to call when the code is deleted
     */
    public void playerDelQRCode(QRCode code, OnCompleteListener<QRCode> listener){
        codeDB.getCode(code, (foundCode, success) -> {
            if (foundCode != null) {
                codeDB.deleteCode(code, (deletedCode, success2) -> {
                    if (deletedCode != null) {
                        listener.onComplete(deletedCode, true);
                    } else {
                        listener.onComplete(null, false);
                    }
                });
            }
        });
    }

    /**
     * Returns a list of QRCodes the user owns
     * @return list of user owned QRCodes
     */
    public List<QRCode> getUserCodes (){
        return codeDB.getCodes();
    }

    /**
     * Returns a list of QRCodes the given player owns
     * @param player the given player who owns the codes
     * @return list of player owned QRCodes
     */
    public List<QRCode> getPlayerCodes(Player player){
        String playerId = player.getId();
        codeDB.switchFromPlayerToPlayerCodes(playerId);
        List<QRCode> codeList = codeDB.getCodes();
        codeDB.switchFromPlayerToPlayerCodes(userId);
        return codeList;
    }

}
