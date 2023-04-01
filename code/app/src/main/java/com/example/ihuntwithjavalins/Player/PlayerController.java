package com.example.ihuntwithjavalins.Player;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;

import java.util.List;

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
                        listener.onComplete(addedPlayer, false);
                    } else {
                        this.connection.setUsername(activity, addedPlayer.getUsername());
                        listener.onComplete(addedPlayer, true);
                    }
                });
            } else {
                this.connection.setUsername(activity, foundPlayer.getUsername());
                Log.d(TAG, "Document does exists! Old player, logging in");

                listener.onComplete(foundPlayer, true);
            }
        });
    }

    public void getPlayerData(Player player, OnCompleteListener<Player> listener) {
        playerDB.getPlayer(player, (foundPlayer, success) -> {
            if (success) {
                Log.d(TAG, "Player data found");
                listener.onComplete(foundPlayer, true);
            } else {
                Log.d(TAG, "Player data not found");
                listener.onComplete(foundPlayer, false);
            }
        });
    }

    public void getPlayerCodes(String username, OnCompleteListener<List<QRCode>> listener) {
        Player player = new Player();
        player.setUsername(username);

        playerDB.getPlayerCodes(player, (codeCollection, success) -> {
            if (success) {
                listener.onComplete(codeCollection, true);
            } else {
                listener.onComplete(null, false);
            }
        });
    }

    public int calculateTotalPoints(List<QRCode> codeList) {
        int totalPointsInt = 0;
        for (QRCode code : codeList) {
            totalPointsInt = totalPointsInt + Integer.parseInt(code.getCodePoints());
        }

        return totalPointsInt;
    }

    public int calculateHighestValue(List<QRCode> codeList) {
        int highestcodeval = 0;
        for (QRCode code: codeList ) {
            if (Integer.parseInt(code.getCodePoints()) > highestcodeval) {
                highestcodeval = Integer.parseInt(code.getCodePoints());
            }
        }

        return highestcodeval;
    }

    public int getTotalCodes(List<QRCode> codeList) {
        return codeList.size();
    }
}
