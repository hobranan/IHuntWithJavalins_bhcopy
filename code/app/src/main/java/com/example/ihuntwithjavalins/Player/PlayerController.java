package com.example.ihuntwithjavalins.Player;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;
import com.google.common.collect.ComparisonChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void getPlayerData(String username, OnCompleteListener<Player> listener) {
        Player player = new Player();
        player.setUsername(username);
        playerDB.getPlayer(player, (foundPlayer, success) -> {
            if (success) {
                Log.d(TAG, "Player data found");
                playerDB.getPlayerCodes(foundPlayer, (codeCollection, codeSuccess) -> {
                    if (codeSuccess) {
                        foundPlayer.addCodes(codeCollection);
                        listener.onComplete(foundPlayer, true);
                    } else {
                        Log.d(TAG, "Getting codes failed");
                    }
                });
            } else {
                Log.d(TAG, "Player data not found");
                listener.onComplete(foundPlayer, false);
            }
        });
    }

    public void getAllPlayerData(OnCompleteListener<List<Player>> listener) {
        playerDB.getAllPlayers((playerList, success) ->{
            if (success) {
                Log.d(TAG, "All player data obtained");
                listener.onComplete(playerList, true);
            } else {
                Log.d(TAG, "Player data retrieval failed");
                listener.onComplete(null, false);
            }
        });
    }

    public ArrayList<Player> getRegionalPlayers(Player user, ArrayList<Player> playerList) {
        ArrayList<Player> regionalPlayers = new ArrayList<>();
        for (Player plr : playerList) {
            if ((user.getRegion()).equals(plr.getRegion())) {
                regionalPlayers.add(plr);
                Log.d(TAG, "profile : regional_players.add(plr): " + plr.getUsername() + " "+ plr.getRegion() + " " + plr.getSumOfCodePoints() + " " + plr.getSumOfCodes() + " " + plr.getHighestCode());
            }
        }
        return regionalPlayers;
    }

    public String getRanking(Player user, ArrayList<Player> playerList, String wordBrake, String rankType) {
        float goldLevel = 0.05f;
        float silverLevel = 0.10f;
        float bronzeLevel = 0.25f;
        String codeString = "";

        playerList = sortPlayers(playerList, rankType);

        for (Player plr : playerList) {
            Log.d(TAG, "BANANA");
            if ((plr.getUsername()).equals(user.getUsername())) {
                codeString = wordBrake + (playerList.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (playerList.indexOf(plr) + 1) / (float) playerList.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (playerList.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                codeString = codeString + rankString;
            }
        }

        return codeString;
    }

    public ArrayList<Player> sortPlayers(ArrayList<Player> playerList, String query) {
        if (query.toLowerCase().equals("sum")) {
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    int p1size = p1.getSumOfCodes();
                    int p2size = p2.getSumOfCodes();
                    return Integer.compare(p2size, p1size);
                }
            });
        } else if (query.toLowerCase().equals("points")) {
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    int p1size = p1.getSumOfCodePoints();
                    int p2size = p2.getSumOfCodePoints();
                    return Integer.compare(p2size, p1size);
                }
            });
        } else if (query.toLowerCase().equals("high")) {
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    int p1size = p1.getHighestCode();
                    int p2size = p2.getHighestCode();
                    return Integer.compare(p2size, p1size);
                }
            });
        } else if (query.toLowerCase().equals("name")){
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    return (p1.getUsername().toLowerCase()).compareTo(p2.getUsername().toLowerCase());
                }
            });
        } else if (query.toLowerCase().equals("high+sum")) {
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    // https://stackoverflow.com/questions/4258700/collections-sort-with-multiple-fields
                    return ComparisonChain.start().compare(p2.getHighestCode(), p1.getHighestCode()).compare(p2.getSumOfCodePoints(), p1.getSumOfCodePoints()).result();
                }
            });
        } else {
            return playerList;
        }

        return playerList;
    }

    public ArrayList<Player> getPlayersContainQuery(ArrayList<Player> players, String query) {
        ArrayList<Player> foundPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getRegion().contains(query)) {
                foundPlayers.add(player);
            }
        }

        return foundPlayers;
    }


    public int calculateTotalPoints(Player player) {
        return player.getSumOfCodePoints();
    }

    public int calculateHighestValue(Player player) {
        return player.getHighestCode();
    }

    public int getTotalCodes(Player player) {
        return player.getSumOfCodes();
    }
}
