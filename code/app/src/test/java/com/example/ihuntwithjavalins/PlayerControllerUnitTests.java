package com.example.ihuntwithjavalins;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import com.example.ihuntwithjavalins.Player.Player;
import com.google.common.collect.ComparisonChain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Contains copies of all the methods that don't involve the database or app in the PLayerController class */
public class PlayerControllerUnitTests {

    private String TAG = "PlayerController";

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
    public String getNiceDateFormat (String joinedDate){
        String date = joinedDate;
        String date_joined = "";
        if (date != null) {
            String[] months = {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
            };
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6, 8);
            // Convert the day from a string to an integer
            int dayInt = Integer.parseInt(day);
            // Get the day suffix
            String daySuffix;
            if (dayInt % 10 == 1 && dayInt != 11) {
                daySuffix = "st";
            } else if (dayInt % 10 == 2 && dayInt != 12) {
                daySuffix = "nd";
            } else if (dayInt % 10 == 3 && dayInt != 13) {
                daySuffix = "rd";
            } else {
                daySuffix = "th";
            }
            // Get the month name from the array
            int monthInt = Integer.parseInt(month);
            String monthName = months[monthInt - 1];
            // Build the final date string
            date_joined = dayInt + daySuffix + " " + monthName + ", " + year;
        } else {
            date_joined = "No date";
        }
        return date_joined;
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

    /** Series of tests to test the getRegionalPlayers method */
    @Test
    public void getRegionalTest() {
        ArrayList<Player> mockPlayers = Mockito.mock(ArrayList.class);
        mockPlayers.add(new Player("JasonBourne", "thebournelegacy@jason.com", "Edmonton"));
        mockPlayers.add(new Player("JamesBond", "doubleohseven@james.com", "Edmonton"));
        mockPlayers.add(new Player("EthanHunt", "mission@impossible.com", "Calgary"));
        mockPlayers.add(new Player("NatashaRomanoff", "black@widow.com", "Regina"));

        Player mockUser = Mockito.mock(Player.class);
        mockUser.setRegion("Edmonton");
        mockUser.setEmail("john@gmail.com");
        mockUser.setUsername("JohnDoe");

        assertEquals(getRegionalPlayers(mockUser, mockPlayers), "something idk");
    }


    /** Series of tests to test the niceDate method */
    @Test
    public void niceDateNormalTest() {
        String date = "20230319";
        assertEquals(getNiceDateFormat(date), "19th March, 2023");

        date = null;
        assertEquals(getNiceDateFormat(date), "No date");

        date = "99991230";
        assertEquals(getNiceDateFormat(date), "30th December, 9999");
    }
}
