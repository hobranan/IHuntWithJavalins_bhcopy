package com.example.ihuntwithjavalins.Player;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;

public class PlayerController {
    private AppCompatActivity activity;
    private DBConnection connection;
    private PlayerDB playerDB;
    private String TAG = "PlayerController";

    public PlayerController(AppCompatActivity activity) {
        this.activity = activity;
        this.connection = new DBConnection(activity.getApplicationContext());
        playerDB = new PlayerDB(connection);
    }

    public void addUser(Player player, OnCompleteListener<Player> listener) {
        playerDB.getPlayer(player, (foundPlayer, foundSuccess) -> {
            if (!foundSuccess) {
                playerDB.addPlayer(player, (addedPlayer, addSuccess) -> {
                    if (!addSuccess) {
                        Log.d(TAG, "User could not be added to database");
                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Login/Signup failed, please try again.", Toast.LENGTH_LONG);
                        toast.show(); // display the Toast popup
                        listener.onComplete(addedPlayer, false);
                    } else {
                        this.connection.setUsername(activity, addedPlayer.getUsername());
                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Player new, signing up", Toast.LENGTH_LONG);
                        toast.show(); // display the Toast popup
                        listener.onComplete(addedPlayer, true);
                    }
                });
            } else {
                this.connection.setUsername(activity, foundPlayer.getUsername());
                Log.d(TAG, "Document does exists! Old player, logging in");

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Player exists, logging in", Toast.LENGTH_LONG);
                toast.show(); // display the Toast popup
                listener.onComplete(foundPlayer, true);
            }
        });
    }
}
