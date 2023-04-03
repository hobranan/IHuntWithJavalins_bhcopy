package com.example.ihuntwithjavalins;

import com.example.ihuntwithjavalins.QRCode.CommentListForCommentAdapter;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Tests various methods from the QRCode folder in the project */
public class QRCodeUnitTest {

    /**
     * Method to sort codes copied from the QRCodeController class in the QRCodes folder
     * Method has been copied due to the Dependency of the QRCodeController class on activities */
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
     * Method to convert a given string of a date to a more legible format
     * copied from the QRCodeViewActivity in the QRCode folder of the project*/
    String getNiceDateFormat(String joinedDate) {
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

    /** Test method from the CommentListForCommentAdapter class in the QRCode folder */
    @Test
    public void convertUnixMillisToDateTimeTest() {

    }

    /**
     * Test method from QRCodeController class for sorting a given list of QRCodes based on a query string */
    @Test
    public void sortCodesTest() {

    }

    /**
     * Test method from QRCodeViewActivity for converting a given date string to a nicer format */
    @Test
    public void getNiceDateFormatTest() {

    }
}
