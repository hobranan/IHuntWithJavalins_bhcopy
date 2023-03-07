package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a QRCode scanned by Players in the application.
 *
 * @version 1.0
 */
public class QRCode {
    // putting making QRCode on hold until we figure out visual representation and QR Code scanning functionality and how we store those values

    String codeHash;
    String codeName;
    String codePoints;

    public QRCode(String textCode) {
//        dateFirstGenerated = new Date();
//        CommentsForThisCode = new ArrayList<Comments>();
        analyzeWordToHashToNameToPoints(textCode);
    }

    public String getCodeHash() {
        return codeHash;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getCodePoints() {
        return codePoints;
    }

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
                break;
            case '1':
                SecondName = "Water";
                break;
            case '2':
                SecondName = "Plant";
                break;
            case '3':
                SecondName = "Electric";
                break;
            case '4':
                SecondName = "Ice";
                break;
            case '5':
                SecondName = "Fighting";
                break;
            case '6':
                SecondName = "Poison";
                break;
            case '7':
                SecondName = "Ground";
                break;
            case '8':
                SecondName = "Flying";
                break;
            case '9':
                SecondName = "Psychic";
                break;
            case 'A':
                SecondName = "Bug";
                break;
            case 'B':
                SecondName = "Rock";
                break;
            case 'C':
                SecondName = "Ghost";
                break;
            case 'D':
                SecondName = "Dragon";
                break;
            case 'E':
                SecondName = "Dark";
                break;
            case 'F':
                SecondName = "Steel";
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
