package com.example.ihuntwithjavalins.Player;

import android.util.Log;

import androidx.annotation.NonNull;

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
import com.google.firebase.firestore.WriteBatch;

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
    private final QRCodeDB codeDB;
    /**
     * Holds the CollectionReference for the user collection
     */
    private CollectionReference collection;

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
            Log.d(TAG, "getIngredient:onComplete");
            if (task.isSuccessful()) {
                Log.d(TAG, ":isSuccessful:" + id);
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, ":exists:" + id);
                    Player player = new Player();
                    player.setUsername(document.getString("username"));
                    player.setEmail(document.getString("email"));
                    player.setRegion(document.getString("region"));
                    player.setPhoneNumber(document.getString("phone number"));
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

        DocumentReference userDocument = collection.document(player.getId());
        batch.delete(userDocument);

        batch.commit().addOnCompleteListener(task -> {
            Log.d(TAG, "deleteIngredient:onComplete");
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
                .update("username", newUsername)
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
                .update("email", newEmail)
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
                .update("phone number", newContact)
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
                .update("region", newRegion)
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

    // This method probably should use getQRCodes method from QRCodeDB(when that is made)
    public void getPlayerCodes(){

    }
}
