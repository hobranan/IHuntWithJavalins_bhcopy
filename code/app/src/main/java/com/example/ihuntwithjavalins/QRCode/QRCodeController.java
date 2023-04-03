package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Comment.Comment;
import com.example.ihuntwithjavalins.Player.PlayerDB;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Controller class for QRCode related operations
 * <p>
 * Manages the QR codes
 * provides methods to get and sort user QR codes
 * Design patterns: none
 */
public class QRCodeController {
    /**
     * Holds current activity using Controller
     */
    private AppCompatActivity activity;
    /**
     * Holds connection to database
     */
    private DBConnection connection;
    /**
     * Holds connection to QRCodeDB
     */
    private QRCodeDB codeDB;
    /**
     * Holds tag for logging
     */
    private String TAG = "QRCodeController";

    /**
     * Constructor to initialize controller
     *
     * @param activity the activity using the controller
     */
    public QRCodeController(AppCompatActivity activity) {
        this.activity = activity;
        this.connection = new DBConnection(activity.getApplicationContext());
        codeDB = new QRCodeDB(connection);
    }

    /**
     * Overwrites(and creates new) QRCode document based on given data map
     *
     * @param code     the QRCode to overwrite
     * @param dataMap  the data field map to overwrite the QRCode data with
     * @param listener the listener to call after overwriting
     */
    public void overwriteCode(QRCode code, HashMap<String, String> dataMap, OnCompleteListener<QRCode> listener) {
        codeDB.overwriteCode(code, dataMap, (overwrittenCode, success) -> {
            if (success) {
                Log.d(TAG, "Overwrite successful");
                listener.onComplete(code, true);
            } else {
                Log.d(TAG, "Overwrite unsuccessful");
                listener.onComplete(code, false);
            }
        });
    }

    /**
     * Sorts a given array of QRCodes based on query field
     *
     * @param codeList the array of QRCodes to sort
     * @param query    string which denotes how to sort the array
     * @return the sorted codeList
     */
    public ArrayList<QRCode> sortCodes(ArrayList<QRCode> codeList, String query) {
        if (query.toLowerCase().equals("name")) {
            Collections.sort(codeList, new Comparator<QRCode>() {
                @Override
                public int compare(QRCode q1, QRCode q2) {
                    return (q1.getCodeName().toLowerCase()).compareTo(q2.getCodeName().toLowerCase());
                }
            });
        } else if (query.toLowerCase().equals("points")) {
            Collections.sort(codeList, new Comparator<QRCode>() {
                @Override
                public int compare(QRCode q1, QRCode q2) {
                    int q1size = Integer.parseInt(q1.getCodePoints());
                    int q2size = Integer.parseInt(q2.getCodePoints());
                    return Integer.compare(q2size, q1size);
                }
            });
        } else if (query.toLowerCase().equals("date")) {
            Collections.sort(codeList, new Comparator<QRCode>() {
                @Override
                public int compare(QRCode q1, QRCode q2) {
                    return (q1.getCodeDate()).compareTo(q2.getCodeDate());
                }
            });
        }
        return codeList;
    }

    /**
     * Sorts comments of QRCodes by date time created
     *
     * @param commentList the array of comments to sort
     * @return the sorted array of comments
     */
    public ArrayList<Comment> sortComments(ArrayList<Comment> commentList) {
        Collections.sort(commentList, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.getUnixMillis_DateTime().compareTo(o2.getUnixMillis_DateTime());
            }
        });

        return commentList;
    }

    /**
     * Converts datetime value to a string which is more human readable
     *
     * @param joinedDate the date time the QRCode was created
     * @return string of formatted date time
     */
    public String getNiceDateFormat(String joinedDate) {
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
}
