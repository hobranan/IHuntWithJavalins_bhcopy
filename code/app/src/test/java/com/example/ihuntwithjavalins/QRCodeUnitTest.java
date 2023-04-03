package com.example.ihuntwithjavalins;

import static org.junit.Assert.assertEquals;

import com.example.ihuntwithjavalins.QRCode.CommentListForCommentAdapter;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/** Tests various methods from the QRCode folder in the project */
public class QRCodeUnitTest {
    /**
     * Method to convert a given Unix format to Datetime string from the CommentListForCommentAdapter class in the QRCode folder
     * Method has been copied due to the Dependency of the class on context
     * @param unixMillis represents a unix time stamp
     * @return simple Date Time string
     * */
    public String convertUnixMillisToDateTime(String unixMillis) {
        // https://javarevisited.blogspot.com/2012/12/how-to-convert-millisecond-to-date-in-java-example.html#axzz7wzpr7WmN
        //current time in milliseconds
        long tempDateTime = Long.parseLong(unixMillis);
        //creating Date from millisecond
        Date tempDate = new Date(tempDateTime);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(tempDate);
    }

    /**
     * Method to sort codes copied from the QRCodeController class in the QRCodes folder
     * Method has been copied due to the Dependency of the QRCodeController class on activities
     * @param query a string query for sorting
     * @param codeList a list of unsorted QRCodes
     * @return an array list of sorted QRCodes*/
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
    public void testConvertUnixMillisToDateTime() {
        // Test for valid Unix timestamp
        String validUnixMillis = "1649123456789"; // April 4, 2022, 19:50:56 AM UTC
        String expectedDateTime = "2022-04-04 19:50:56";
        String actualDateTime = convertUnixMillisToDateTime(validUnixMillis);
        Assertions.assertEquals(expectedDateTime, actualDateTime, "Conversion of valid Unix timestamp is incorrect");
    }

    /**
     * Test method from QRCodeController class for sorting a given list of QRCodes based on a query string */
    @Test
    public void sortCodesTest() {
        // Create a list of QRCode objects to be sorted
        ArrayList<QRCode> codeList = new ArrayList<>();
        QRCode code1 = new QRCode("hash123", "MyQRCode1", "10", "imgRef123", "123.456", "789.012", "photoRef123", "2022-04-01");
        QRCode code2 = new QRCode("hash456", "MyQRCode2", "20", "imgRef456", "456.789", "012.345", "photoRef456", "2022-04-02");
        QRCode code3 = new QRCode("hash789", "MyQRCode3", "30", "imgRef789", "789.012", "345.678", "photoRef789", "2022-04-03");
        codeList.add(code2);
        codeList.add(code1);
        codeList.add(code3);

        // Test sorting by name
        ArrayList<QRCode> sortedList = sortCodes(codeList, "name");
        assertEquals(sortedList.get(0), code1);
        assertEquals(sortedList.get(1), code2);
        assertEquals(sortedList.get(2), code3);

        // Test sorting by points
        sortedList = sortCodes(codeList, "points");
        assertEquals(sortedList.get(0), code3);
        assertEquals(sortedList.get(1), code2);
        assertEquals(sortedList.get(2), code1);

        // Test sorting by date
        sortedList = sortCodes(codeList, "date");
        assertEquals(sortedList.get(0), code1);
        assertEquals(sortedList.get(1), code2);
        assertEquals(sortedList.get(2), code3);
    }

    /**
     * Test method from QRCodeViewActivity for converting a given date string to a nicer format */
    @Test
    public void getNiceDateFormatTest() {
        // Test with a valid date string
        String niceDate = getNiceDateFormat("20220103");
        assertEquals(niceDate, "3rd January, 2022");

        // Test with an invalid date string
        niceDate = getNiceDateFormat(null);
        assertEquals(niceDate, "No date");
    }
}
