package com.example.ihuntwithjavalins.Player;

import android.util.Log;

import com.example.ihuntwithjavalins.Comment.Comment;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Player who competes to scan and own more valuable QRCodes than other
 * Players in the application.
 * Design Patterns: none
<<<<<<< Updated upstream
 *
=======
>>>>>>> Stashed changes
 * @version 1.0
 */
    public class Player implements Serializable {
    /**
     * Holds the username of the Player
     */
    private String username;
    /**
     * Holds the email of the Player
     */
    private String email;
    /**
     * Holds the region the Player competes/lives in
     */
    private String region;

    /**
     * Holds the the date the Player signed up
     */
    private String dateJoined;
    /**
     * Holds the QRCodes the Player has scanned
     */
    private ArrayList<QRCode> codes = new ArrayList<>();

    private ArrayList<Comment> comments = new ArrayList<>();    // add QRCode class later

    /**
     * Constructor for new instance of Player object
     */
    public Player() {
        this.username = "UNKNOWN";
        this.email = "UNKNOWN";
        this.region = "UNKNOWN";
        this.dateJoined = "00000000";
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    }

    /**
     * Constructor for new instance of Player object without email given
     * @param username The username of the Player
     * @param region The region the Player is in
     * @param email the email of the player
     * @param dateJoined the date the user created player
     */
    public Player(String username, String email, String region, String dateJoined) {
        this.username = username;
        this.email = email;
        this.region = region;
        this.dateJoined = dateJoined;
    }

    /**
     * Gets the date the user joined
     * @return the date the user joined
     */
    public String getDateJoined() {
        return dateJoined;
    }

    /**
     * Sets the date the user joined
     * @param dateJoined the date to set as the user's join date
     */
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    /**
     * Gets the username of the Player
     * @return The String representing the username of the Player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the Player
     * @param username The String representing the username of the Player
     */
    public void setUsername(String username) {     // This setter may be deleted if we decide a Player cannot change their username(although I think the functionality is good if an administrator wishes to get rid of someone's inappropriate name)
        this.username = username;
    }

    /**
     * Gets the email of the Player
     * @return The String representing the email of the Player
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the Player
     * @param email The String representing the email of the Player
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the region the Player is playing in
     * @return The String representing the region the Player is playing in
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region the Player is playing in
     * @param region The String representing the region the Player is playing in
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the list of QRCode objects the Player has scanned
     * @return The list containing QRCode objects the Player has scanned
     */
    public List<QRCode> getCodes() {
        return codes;
    }

    /**
     * Adds all codes from list to Player costs
     * @param codes Codes to add to player
     */
    public void setCodes(ArrayList<QRCode> codes) {
        this.codes.clear();
        this.codes.addAll(codes);
    }

    /**
     * Adds a QRCode to the list of codes the Player has scanned
     * @param code The QRCode the Player scanned to be added to list of codes
     */
    public void addCode(QRCode code) {
        this.codes.add(code);
    }

    /**
     * Adds all QRCodes from list to Player list of codes
     * @param codes The list containing QRCode objects to add to Player list
     */
    public void addCodes(List<QRCode> codes) {
        this.codes.addAll(codes);
    }

    /**
     * Deletes a QRCode from the list of codes the Player has scanned
     * @param code The QRCode to be removed from list of codes Player has scanned
     */
    public void delCode(QRCode code) {
        codes.remove(code);    // May want to have an exception thrown if trying to remove a QRCode that does not exist
    }


    //this compares whole code, we need to just compare bode hash (dates will almost almost be different)
    /**
     * Checks if the QRCode given is a QRCode the Player has scanned
     * @param code The QRCode to be searched for in the Player's list of codes
     * @return True if the QRCode is in the Player list of codes, false otherwise
     */
    public boolean hasCode(QRCode code) {
        if (codes.contains(code)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the sum of points from QRCode objects the Player has scanned
     * @return sum of points from QRCode objects the Player has scanned
     */
    public int getSumOfCodePoints(){
        List<QRCode> all_codes = getCodes();
        int sum = 0;
        for (QRCode object : all_codes) {
            sum += Integer.parseInt(object.getCodePoints());
        }
        return sum;
    }

<<<<<<< Updated upstream
    /**
     * Gets the sum of codes Player has
     * @return the sum of codes
     */
=======
>>>>>>> Stashed changes
    public int getSumOfCodes(){
        return codes.size();
    }

<<<<<<< Updated upstream
    /**
     * Gets the value of the highest code Player has
     * @return the highest value of Player codes owned
     */
=======
>>>>>>> Stashed changes
    public int getHighestCode() {
        List<QRCode> all_codes = getCodes();
        int max_code = 0;
        for (QRCode object : all_codes) {
            if (Integer.parseInt(object.getCodePoints())>max_code){
                max_code = Integer.parseInt(object.getCodePoints());
            }
        }
        return max_code;
    }
}
