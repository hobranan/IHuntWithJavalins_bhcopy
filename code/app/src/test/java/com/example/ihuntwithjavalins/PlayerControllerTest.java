package com.example.ihuntwithjavalins;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.google.common.collect.ComparisonChain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Contains copies of all the methods that don't involve the database or app in the PLayerController class
 * TODO: The test for getRanking*/
public class PlayerControllerTest {

    private String TAG = "PlayerController";

    public ArrayList<Player> getRegionalPlayers(Player user, ArrayList<Player> playerList) {
        ArrayList<Player> regionalPlayers = new ArrayList<>();
        for (Player plr : playerList) {
            if ((user.getRegion()).equals(plr.getRegion())) {
                regionalPlayers.add(plr);
//                Log.d(TAG, "profile : regional_players.add(plr): " + plr.getUsername() + " "+ plr.getRegion() + " " + plr.getSumOfCodePoints() + " " + plr.getSumOfCodes() + " " + plr.getHighestCode());
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
//            Log.d(TAG, "BANANA");
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

    /**
     * Series of tests to test the getRegionalPlayers method */
    @Test
    void getRegionalTest() {
        Player user = new Player("John Doe","murab@ualberta.ca","Edmonton");
        ArrayList<Player> playerList = new ArrayList<>();
        Player player1 = new Player("JasonBourne", "thebournelegacy@jason.com", "Edmonton");
        Player player2 = new Player("JamesBond", "doubleohseven@james.com", "Edmonton");
        Player player3 = new Player("EthanHunt", "mission@impossible.com", "Calgary");
        Player player4 = new Player("NatashaRomanoff", "black@widow.com", "Regina");
        Player player5 = new Player("AustinPowers", "spy@me.com", "Edmonton");
        Player player6 = new Player("JohnnyEnglish", "british@intelligence.com", "Calgary");

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);
        playerList.add(player6);

        ArrayList<Player> regionalPlayers = getRegionalPlayers(user, playerList);

        assertEquals(3, regionalPlayers.size());
        assertTrue(regionalPlayers.contains(player1));
        assertTrue(regionalPlayers.contains(player2));
        assertTrue(regionalPlayers.contains(player5));
        assertFalse(regionalPlayers.contains(player3));
        assertFalse(regionalPlayers.contains(player4));
        assertFalse(regionalPlayers.contains(player6));
    }


    /**
     * Series of tests to test the getRanking method */
    @Test
    public void getRankingTest() {
        // Setup test data
        ArrayList<Player> playerList = new ArrayList<Player>();

        //QRCode Objects
        QRCode qrCodeObj1 = new QRCode("hash123", "MyQRCode1", "10", "imgRef123", "123.456", "789.012", "photoRef123", "2022-04-01");
        QRCode qrCodeObj2 = new QRCode("hash456", "MyQRCode2", "20", "imgRef456", "456.789", "012.345", "photoRef456", "2022-04-02");
        QRCode qrCodeObj3 = new QRCode("hash789", "MyQRCode3", "30", "imgRef789", "789.012", "345.678", "photoRef789", "2022-04-03");
        QRCode qrCodeObj4 = new QRCode("hash111", "MyQRCode4", "40", "imgRef111", "111.222", "333.444", "photoRef111", "2022-04-04");
        QRCode qrCodeObj5 = new QRCode("hash222", "MyQRCode5", "50", "imgRef222", "222.333", "444.555", "photoRef222", "2022-04-05");

        Player player1 = new Player("John Doe", "john.doe@example.com", "Edmonton");
        Player player2 = new Player("Jane Smith", "jane.smith@example.com", "Edmonton");
        Player player3 = new Player("Bob Johnson", "bob.johnson@example.com", "Vancouver");

        Player user = new Player("John Doe","murab@ualberta.ca","Edmonton");

        player1.addCode(qrCodeObj1);
        player1.addCode(qrCodeObj1);
        player1.addCode(qrCodeObj2);

        player2.addCode(qrCodeObj2);
        player2.addCode(qrCodeObj4);
        player2.addCode(qrCodeObj5);

        player3.addCode(qrCodeObj3);
        player3.addCode(qrCodeObj2);
        player3.addCode(qrCodeObj1);

        user.addCode(qrCodeObj1);
        user.addCode(qrCodeObj4);
        user.addCode(qrCodeObj5);

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);

        // Test getRanking method
        ArrayList<Player> regionalPlayers = getRegionalPlayers(user, playerList);
        String rankString = getRanking(user, playerList, "Everywhere: #", "points");
        rankString = getRanking(user, regionalPlayers, rankString + "\n" + "Regional: #", "points");
        String expected = "Everywhere: #3\nRegional: #2";
        assertEquals(expected, rankString);
    }

    /**
     * Series of tests to test the sortPlayers method */
    @Test
    public void sortPlayersTest() {
        ArrayList<Player> mockPlayers = new ArrayList<>();
        Player player1 = new Player("JasonBourne", "thebournelegacy@jason.com", "Edmonton");
        Player player2 = new Player("JamesBond", "doubleohseven@james.com", "Edmonton");
        Player player3 = new Player("EthanHunt", "mission@impossible.com", "Calgary");
        Player player4 = new Player("NatashaRomanoff", "black@widow.com", "Regina");
        mockPlayers.add(player1);
        mockPlayers.add(player2);
        mockPlayers.add(player3);
        mockPlayers.add(player4);

        ArrayList<Player> initialList = mockPlayers;
        ArrayList<Player> output = sortPlayers(mockPlayers, "name");


        for(Player plr : initialList) {
            System.out.println(plr.getUsername());
        }

        for(Player plr : output) {
            System.out.println(plr.getUsername());
        }

        assertEquals(output, initialList);
    }

    /**
     * Series of tests to test the niceDate method */
    @Test
    public void niceDateNormalTest() {
        String date = "20230319";
        assertEquals(getNiceDateFormat(date), "19th March, 2023");

        date = null;
        assertEquals(getNiceDateFormat(date), "No date");

        date = "99991230";
        assertEquals(getNiceDateFormat(date), "30th December, 9999");
    }

    /**
     * Test for playerController method calculateTotalPoints */
    @Test
    public void calculateTotalPointsTest() {
        Player player1 = new Player("JasonBourne", "thebournelegacy@jason.com", "Edmonton");
        QRCode qrCodeObj1 = new QRCode("hash123", "MyQRCode1", "10", "imgRef123", "123.456", "789.012", "photoRef123", "2022-04-01");
        QRCode qrCodeObj2 = new QRCode("hash456", "MyQRCode2", "20", "imgRef456", "456.789", "012.345", "photoRef456", "2022-04-02");
        QRCode qrCodeObj3 = new QRCode("hash789", "MyQRCode3", "30", "imgRef789", "789.012", "345.678", "photoRef789", "2022-04-03");
        QRCode qrCodeObj4 = new QRCode("hash111", "MyQRCode4", "40", "imgRef111", "111.222", "333.444", "photoRef111", "2022-04-04");
        QRCode qrCodeObj5 = new QRCode("hash222", "MyQRCode5", "50", "imgRef222", "222.333", "444.555", "photoRef222", "2022-04-05");

        assertEquals(player1.getSumOfCodePoints(), 0);

        player1.addCode(qrCodeObj1);
        player1.addCode(qrCodeObj2);
        player1.addCode(qrCodeObj3);
        player1.addCode(qrCodeObj4);
        player1.addCode(qrCodeObj5);

        assertEquals(calculateTotalPoints(player1), 150);
    }

    /**
     * Test for playerController method calculateHighestValue */
    @Test
    public void calculateHighestValueTest() {
        Player player1 = new Player("JasonBourne", "thebournelegacy@jason.com", "Edmonton");
        QRCode qrCodeObj1 = new QRCode("hash123", "MyQRCode1", "10", "imgRef123", "123.456", "789.012", "photoRef123", "2022-04-01");
        QRCode qrCodeObj2 = new QRCode("hash456", "MyQRCode2", "20", "imgRef456", "456.789", "012.345", "photoRef456", "2022-04-02");
        QRCode qrCodeObj3 = new QRCode("hash789", "MyQRCode3", "30", "imgRef789", "789.012", "345.678", "photoRef789", "2022-04-03");
        QRCode qrCodeObj4 = new QRCode("hash111", "MyQRCode4", "40", "imgRef111", "111.222", "333.444", "photoRef111", "2022-04-04");
        QRCode qrCodeObj5 = new QRCode("hash222", "MyQRCode5", "50", "imgRef222", "222.333", "444.555", "photoRef222", "2022-04-05");

        assertEquals(player1.getSumOfCodePoints(), 0);

        player1.addCode(qrCodeObj1);
        player1.addCode(qrCodeObj2);
        player1.addCode(qrCodeObj3);
        player1.addCode(qrCodeObj4);
        player1.addCode(qrCodeObj5);

        assertEquals(calculateHighestValue(player1), 50);
    }
}
