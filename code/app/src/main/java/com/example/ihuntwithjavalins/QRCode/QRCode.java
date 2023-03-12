package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents a QRCode scanned by Players in the application.
 *
 * @version 1.0
 */
public class QRCode implements Serializable {

    /**
     * Holds the hash value for the code
     */
    private String codeHash;
    /**
     * Holds the semi unique name for the code
     */
    private String codeName;
    /**
     * Holds the point value of the code
     */
    private String codePoints;
    /**
     * Holds the image reference of the code
     */
    private String codeGendImageRef;
    private String codeLat;
    private String codeLon;
    private String codePhotoRef;

    /**
     * Holds code acquisition date
     */
    private String codeDate;
    /**
     * Constructor for new instance of QRCode object initialized based on textCode string
     * @param textCode the textCode to be converted into QRCode data
     */
    public QRCode(String textCode) {
        analyzeWordToHashToNameToPoints(textCode);
        codeLat = "";
        codeLon = "";
        codePhotoRef = "";

        // https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        String pattern = "yyyyMMdd"; //String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        codeDate = df.format(today);
    }

    /**
     * Constructor for new instance of QRCode object
     */
    public QRCode(){}

    /**
     * Constructor for new instance of QRCode object, initializes initial fields based on parameters
     * @param codeName the name of the QRCode
     * @param codePoints the point value of the QRCode
     * @param codeHash the Hash value of the QRCode
     * @param codeGendImageRef the image reference of the QRCode
     */
    public QRCode(String codeHash, String codeName, String codePoints, String codeGendImageRef) {
        this.codeHash = codeHash;
        this.codeName = codeName;
        this.codePoints = codePoints;
        this.codeGendImageRef = codeGendImageRef;
    }

    public QRCode(String codeHash, String codeName, String codePoints, String codeGendImageRef, String codeLat, String codeLon, String codePhotoRef, String codeDate) {
        this.codeHash = codeHash;
        this.codeName = codeName;
        this.codePoints = codePoints;
        this.codeGendImageRef = codeGendImageRef;
        this.codeLat = codeLat;
        this.codeLon = codeLon;
        this.codePhotoRef = codePhotoRef;
        this.codeDate = codeDate;
    }


    /**
     * Sets the unique firestore id of the QRCode
     * @param date the id to set the QRCode to
     */
    public void setCodeDate(String date) {this.codeDate = date;}

    /**
     * Sets the Hash Value of the QRCode
     * @param hash the hash value to set the QRCode to
     */
    public void setCodeHash(String hash) {this.codeHash = hash;}

    /**
     * Sets the name of the QRCode
     * @param name the name to set the QRCode to
     */
    public void setCodeName(String name) {this.codeName = name;}

    /**
     * Sets the point value of the QRCode
     * @param points the point value to set the QRCode to
     */
    public void setCodePoints(String points) {this.codePoints = points;}

    /**
     * Sets the image reference of the QRCode
     * @param imageRef the image reference to set the QRCode to
     */
    public void setCodeGendImageRef(String imageRef) {this.codeGendImageRef = imageRef;}



    public void setCodePhotoRef(String codePhotoRef) {
        this.codePhotoRef = codePhotoRef;
    }


    public void setCodeLat(String codeLat) {
        this.codeLat = codeLat;
    }


    public void setCodeLon(String codeLon) {
        this.codeLon = codeLon;
    }


    /**
     * Gets the unique QRCode firestore id
     * @return the QRCode id
     */
    public String getCodeDate() {return codeDate;}

    /**
     * Gets the Hash Value of the QRCode
     * @return the QRCode hash value
     */
    public String getCodeHash() {
        return codeHash;
    }

    /**
     * Gets the name of the QRCode
     * @return the QRCode name
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Gets the point value of the QRCode
     * @return the QRCode point value
     */
    public String getCodePoints() {
        return codePoints;
    }

    /**
     * Gets the image reference of the QRCode
     * @return the QRCode image reference
     */
    public String getCodeGendImageRef() { return codeGendImageRef;}


    public String getCodePhotoRef() {
        return codePhotoRef;
    }
    public String getCodeLat() {
        return codeLat;
    }
    public String getCodeLon() {
        return codeLon;
    }


    /**
     * Initializes the QRCode object's fields based on textCode
     * @param textCode the textCode to convert into QRCode object field data
     */
    void analyzeWordToHashToNameToPoints(String textCode) {
        Log.d("myTag", textCode);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("myTag", "NoSuchAlgorithmException");
        }

        assert md != null;
//            md.reset();
        byte[] hash = md.digest(textCode.getBytes(StandardCharsets.UTF_8));
        String hashCode = String.format("%0" + (hash.length * 2) + "X", new BigInteger(1, hash));
        Log.d("myTag", hashCode);
        codeHash = hashCode.toUpperCase();

        String FirstName = "", SecondName = "", ThirdName = "", FullName = "";
        char[] hashCharArray = (hashCode.toUpperCase()).toCharArray();
        switch (hashCharArray[0]) {
            case '0':
                FirstName = "Aggressive";
                break;
            case '1':
                FirstName = "Bitter";
                break;
            case '2':
                FirstName = "Charming";
                break;
            case '3':
                FirstName = "Dangerous";
                break;
            case '4':
                FirstName = "Delirious";
                break;
            case '5':
                FirstName = "Frightening";
                break;
            case '6':
                FirstName = "Gleaming";
                break;
            case '7':
                FirstName = "Jagged";
                break;
            case '8':
                FirstName = "Lazy";
                break;
            case '9':
                FirstName = "Mindless";
                break;
            case 'A':
                FirstName = "Noxious";
                break;
            case 'B':
                FirstName = "Poised";
                break;
            case 'C':
                FirstName = "Shimmering";
                break;
            case 'D':
                FirstName = "Unruly";
                break;
            case 'E':
                FirstName = "Wretched";
                break;
            case 'F':
                FirstName = "Zealous";
                break;
        }
        switch (hashCharArray[1]) {
            case '0':
                SecondName = "Fire";
                codeGendImageRef = "picture_1-min.png";
                break;
            case '1':
                SecondName = "Water";
                codeGendImageRef = "picture_2-min.png";
                break;
            case '2':
                SecondName = "Plant";
                codeGendImageRef = "picture_3-min.png";
                break;
            case '3':
                SecondName = "Electric";
                codeGendImageRef = "picture_4-min.png";
                break;
            case '4':
                SecondName = "Ice";
                codeGendImageRef = "picture_5-min.png";
                break;
            case '5':
                SecondName = "Fighting";
                codeGendImageRef = "picture_6-min.png";
                break;
            case '6':
                SecondName = "Poison";
                codeGendImageRef = "picture_7-min.png";
                break;
            case '7':
                SecondName = "Ground";
                codeGendImageRef = "picture_8-min.png";
                break;
            case '8':
                SecondName = "Flying";
                codeGendImageRef = "picture_9-min.png";
                break;
            case '9':
                SecondName = "Psychic";
                codeGendImageRef = "picture_10-min.png";
                break;
            case 'A':
                SecondName = "Bug";
                codeGendImageRef = "picture_11-min.png";
                break;
            case 'B':
                SecondName = "Rock";
                codeGendImageRef = "picture_12-min.png";
                break;
            case 'C':
                SecondName = "Ghost";
                codeGendImageRef = "picture_13-min.png";
                break;
            case 'D':
                SecondName = "Dragon";
                codeGendImageRef = "picture_14-min.png";
                break;
            case 'E':
                SecondName = "Dark";
                codeGendImageRef = "picture_15-min.png";
                break;
            case 'F':
                SecondName = "Steel";
                codeGendImageRef = "picture_16-min.png";
                break;
        }
        switch (hashCharArray[2]) {
            case '0':
                ThirdName = "Reptile";
                break;
            case '1':
                ThirdName = "Viperine";
                break;
            case '2':
                ThirdName = "Testudine";
                break;
            case '3':
                ThirdName = "Porifera";
                break;
            case '4':
                ThirdName = "Waterfowl";
                break;
            case '5':
                ThirdName = "Landfowl";
                break;
            case '6':
                ThirdName = "Mammal";
                break;
            case '7':
                ThirdName = "Fish";
                break;
            case '8':
                ThirdName = "Robot";
                break;
            case '9':
                ThirdName = "Amphibian";
                break;
            case 'A':
                ThirdName = "Aeternae";
                break;
            case 'B':
                ThirdName = "Briareus";
                break;
            case 'C':
                ThirdName = "Lycampyre";
                break;
            case 'D':
                ThirdName = "Cercecopes";
                break;
            case 'E':
                ThirdName = "Erymithan";
                break;
            case 'F':
                ThirdName = "Alien";
                break;
        }
        FullName = FirstName + " " + SecondName + " " + ThirdName;
        Log.d("myTag", FullName);
        codeName = FullName;

        int score = 0;
        String scorelog = "";
        for (char alphaletter = '0'; alphaletter < ('9' + 1); alphaletter++) {
            Log.d("myTag", "alphaletter=" + String.valueOf(alphaletter));
            int scoremodifer = (int) (alphaletter - 46); // added minus 1 to remove 1 as score modifier (start at 2 for '0', 3 for '1')
            Log.d("myTag", "scoremodifer=" + String.valueOf(scoremodifer));
            for (int i = 0; i < hashCharArray.length; i = i + 4) {
                if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 4);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 3);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 3);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] != alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] != alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else {
                    int scorepiece = 0;
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                }
                Log.d("myTag", "scorelog=" + scorelog);
                Log.d("myTag", "i=" + String.valueOf(i));
                Log.d("myTag", "score=" + String.valueOf(score));
            }
        }

        for (char alphaletter = 'A'; alphaletter < ('F' + 1); alphaletter++) {
            Log.d("myTag", "alphaletter=" + String.valueOf(alphaletter));
            int scoremodifer = (int) (alphaletter - 53); // set to be above 0-9 modifiers (11 is '9', 12 is 'A', 13 is 'B')
            Log.d("myTag", "scoremodifer=" + String.valueOf(scoremodifer));
            for (int i = 0; i < hashCharArray.length; i = i + 4) {
                if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 4);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 3);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 3);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] == alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] != alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] == alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] != alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else if ((hashCharArray[i + 0] != alphaletter) & (hashCharArray[i + 1] != alphaletter)
                        & (hashCharArray[i + 2] == alphaletter) & (hashCharArray[i + 3] == alphaletter)) {
                    int scorepiece = (int) Math.pow(scoremodifer, 2);
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                } else {
                    int scorepiece = 0;
                    Log.d("myTag", "scorepiece=" + scorepiece);
                    scorelog = scorelog + String.valueOf(scorepiece) + " ";
                    score = score + (scorepiece);
                }
                Log.d("myTag", "scorelog=" + scorelog);
                Log.d("myTag", "i=" + String.valueOf(i));
                Log.d("myTag", "score=" + String.valueOf(score));
            }
        }

        Log.d("myTag", "Interscorelog=" + scorelog + " ");
        Log.d("myTag", "Interscore=" + String.valueOf(score));
        if (score < 10) {
            score = 10;
            Log.d("myTag", "poorcodescore=" + String.valueOf(score));
        }
        codePoints = String.valueOf(score);

        Log.d("myTag", "text= " + textCode);
        Log.d("myTag", "hash= " + codeHash);
        Log.d("myTag", "name= " + codeName);
        Log.d("myTag", "points= " + codePoints);
    }

}
