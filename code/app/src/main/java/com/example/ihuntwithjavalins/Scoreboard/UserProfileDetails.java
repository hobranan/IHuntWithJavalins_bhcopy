package com.example.ihuntwithjavalins.Scoreboard;

import java.io.Serializable;

public class UserProfileDetails implements Serializable {

    private String playerName;
    private String DateJoined;
    private String Email;
    private String Region;
    private String TotalPoints;
    private String TotalPointsPlacing;
    private String TotalCodes;
    private String TotalCodesPlacing;
    private String HighestCodeValue;
    private String HighestCodeValuePlacing;

    public UserProfileDetails(String playerName, String dateJoined, String email, String region, String totalPoints, String totalPointsPlacing, String totalCodes, String totalCodesPlacing, String highestCodeValue, String highestCodeValuePlacing) {
        this.playerName = playerName;
        DateJoined = dateJoined;
        Email = email;
        Region = region;
        TotalPoints = totalPoints;
        TotalPointsPlacing = totalPointsPlacing;
        TotalCodes = totalCodes;
        TotalCodesPlacing = totalCodesPlacing;
        HighestCodeValue = highestCodeValue;
        HighestCodeValuePlacing = highestCodeValuePlacing;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDateJoined() {
        return DateJoined;
    }

    public String getEmail() {
        return Email;
    }

    public String getRegion() {
        return Region;
    }

    public String getTotalPoints() {
        return TotalPoints;
    }

    public String getTotalPointsPlacing() {
        return TotalPointsPlacing;
    }

    public String getTotalCodes() {
        return TotalCodes;
    }

    public String getTotalCodesPlacing() {
        return TotalCodesPlacing;
    }

    public String getHighestCodeValue() {
        return HighestCodeValue;
    }

    public String getHighestCodeValuePlacing() {
        return HighestCodeValuePlacing;
    }
}
