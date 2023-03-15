package com.example.ihuntwithjavalins.Scoreboard;

import java.io.Serializable;
/**
 * StoreNamePoints is an object class responsible for storing the details of a QRCode
 */
public class StoreNamePoints implements Serializable {

    private String codeName;
    private String codePoints;
    private boolean isScanned;

    /**
     * Constructor for new instance of Player object representing a Player in the application
     * competing against each other.
     * @param codeName The codeName of the Player's QRCode
     * @param codePoints The points for the QRCode of the Player
     * @param isScanned for checking if the code is scanned or not by the user playing the game
     */
    public StoreNamePoints(String codeName, String codePoints, boolean isScanned) {
        this.codeName = codeName;
        this.codePoints = codePoints;
        this.isScanned = isScanned;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getCodePoints() {
        return codePoints;
    }

    public boolean isScanned() {
        return isScanned;
    }
}
