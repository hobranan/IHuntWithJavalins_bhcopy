package com.example.ihuntwithjavalins;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.Scoreboard.ShowUserProfile;

import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import java.util.List;

public class ShowUserProfileTest {



    ArrayList<Player> playerList;
    ArrayList<Player> regional_players;
    Player player_details;

    @Before
    public void LoadData(){
        playerList = new ArrayList<Player>();

        //QRCode Objects
        QRCode qrCodeObj1 = new QRCode("hash123", "MyQRCode1", "10", "imgRef123", "123.456", "789.012", "photoRef123", "2022-04-01");
        QRCode qrCodeObj2 = new QRCode("hash456", "MyQRCode2", "20", "imgRef456", "456.789", "012.345", "photoRef456", "2022-04-02");
        QRCode qrCodeObj3 = new QRCode("hash789", "MyQRCode3", "30", "imgRef789", "789.012", "345.678", "photoRef789", "2022-04-03");
        QRCode qrCodeObj4 = new QRCode("hash111", "MyQRCode4", "40", "imgRef111", "111.222", "333.444", "photoRef111", "2022-04-04");
        QRCode qrCodeObj5 = new QRCode("hash222", "MyQRCode5", "50", "imgRef222", "222.333", "444.555", "photoRef222", "2022-04-05");

        Player john = new Player("John Doe", "john.doe@example.com", "Edmonton");
        Player jane = new Player("Jane Smith", "jane.smith@example.com", "Edmonton");
        Player bob = new Player("Bob Johnson", "bob.johnson@example.com", "Vancouver");

        player_details = new Player("Jane Smith","murab@ualberta.ca","Edmonton");




        john.addCode(qrCodeObj1);
        john.addCode(qrCodeObj1);
        john.addCode(qrCodeObj2);
        //total points for John Doe = 10+10+20 = 40

        jane.addCode(qrCodeObj2);
        jane.addCode(qrCodeObj4);
        jane.addCode(qrCodeObj5);
        jane.addCode(qrCodeObj1);
        //total points for Jane Smith = 20+40+50 = 120
        player_details.addCode(qrCodeObj2);
        player_details.addCode(qrCodeObj4);
        player_details.addCode(qrCodeObj5);
        player_details.addCode(qrCodeObj1);

        bob.addCode(qrCodeObj3);
        bob.addCode(qrCodeObj2);
        bob.addCode(qrCodeObj1);
        //total points for Bob Johnson = 30+20+10 = 60



        playerList.add(john);
        playerList.add(jane);
        playerList.add(bob);



    }

    float goldLevel = 0.05f;
    float silverLevel = 0.10f;
    float bronzeLevel = 0.25f;


    public String sort_by_points(ArrayList<Player> playerList,Player player_details){
        String pointsString = "";

        regional_players = new ArrayList<>();
        for (Player plr : playerList) {
            if ((player_details.getRegion()).equals(plr.getRegion())) {
                regional_players.add(plr);
            }
        }

        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodePoints();
                int p2size = p2.getSumOfCodePoints();
                return Integer.compare(p2size, p1size);
            }
        });




        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
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
                pointsString = pointsString + rankString;
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = pointsString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                pointsString = pointsString + rankString;
            }
        }
        return pointsString;
    }


    public String sort_by_numberCodes(ArrayList<Player> playerList,Player player_details){
        String pointsString = "";

        regional_players = new ArrayList<>();
        for (Player plr : playerList) {
            if ((player_details.getRegion()).equals(plr.getRegion())) {
                regional_players.add(plr);
            }
        }

        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodes();
                int p2size = p2.getSumOfCodes();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getSumOfCodes();
                int p2size = p2.getSumOfCodes();
                return Integer.compare(p2size, p1size);
            }
        });




        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
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
                pointsString = pointsString + rankString;
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = pointsString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                pointsString = pointsString + rankString;
            }
        }
        return pointsString;
    }

    public String sort_by_highestCode(ArrayList<Player> playerList,Player player_details){
        String pointsString = "";

        regional_players = new ArrayList<>();
        for (Player plr : playerList) {
            if ((player_details.getRegion()).equals(plr.getRegion())) {
                regional_players.add(plr);
            }
        }

        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getHighestCode();
                int p2size = p2.getHighestCode();
                return Integer.compare(p2size, p1size);
            }
        });
        Collections.sort(regional_players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int p1size = p1.getHighestCode();
                int p2size = p2.getHighestCode();
                return Integer.compare(p2size, p1size);
            }
        });




        for (Player plr : playerList) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = "Everywhere: #" + (playerList.indexOf(plr) + 1);
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
                pointsString = pointsString + rankString;
            }
        }
        for (Player plr : regional_players) {
            if ((plr.getUsername()).equals(player_details.getUsername())) {
                pointsString = pointsString + "\n" + "Regional: #" + (regional_players.indexOf(plr) + 1);
                String rankString = "";
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= bronzeLevel) {
                    rankString = " Bronze Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= silverLevel) {
                    rankString = " Silver Level";
                }
                if (((float) (regional_players.indexOf(plr) + 1) / (float) regional_players.size()) <= goldLevel) {
                    rankString = " Gold Level";
                }
                if (regional_players.indexOf(plr) == 0) {
                    rankString = " Leader!";
                }
                pointsString = pointsString + rankString;
            }
        }
        return pointsString;
    }




    @Test
    public void checkSortByPoints(){
        LoadData();
        String value = sort_by_points(playerList,player_details);
        assertEquals(120,player_details.getSumOfCodePoints());
        assertEquals("Jane Smith",playerList.get(0).getUsername()); // Testing if Jane Smith if 1st position Overall
        assertEquals("Jane Smith",regional_players.get(0).getUsername()); // Testing if Jane Smith if 1st position Regionally
        assertEquals("Everywhere: #1 Leader!\n" + "Regional: #1 Leader!",value);
    }

    @Test
    public void checkSortByNumberCodes(){
        LoadData();
        String value = sort_by_numberCodes(playerList,player_details);
        assertEquals(4,player_details.getSumOfCodes());
        assertEquals("Jane Smith",playerList.get(0).getUsername()); // Testing if Jane Smith if 1st position Overall
        assertEquals("Jane Smith",regional_players.get(0).getUsername()); // Testing if Jane Smith if 1st position Regionally
        assertEquals("Everywhere: #1 Leader!\n" + "Regional: #1 Leader!",value);
    }

    @Test
    public void checkSortByHighestCode(){
        LoadData();
        String value = sort_by_highestCode(playerList,player_details);
        assertEquals(50,player_details.getHighestCode()); //Checking the user's(i.e Jane Smith) highest code
        assertEquals("Jane Smith",playerList.get(0).getUsername())





        ; // Testing if Jane Smith if 1st position Overall
        assertEquals("Jane Smith",regional_players.get(0).getUsername()); // Testing if Jane Smith if 1st position Regionally
        assertEquals("Everywhere: #1 Leader!\n" + "Regional: #1 Leader!",value);
    }


}

