package com.example.ihuntwithjavalins.QRCode;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.PlayerDB;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.example.ihuntwithjavalins.common.OnCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Manages the QR codes
 * provides methods to get and sort user QR codes
 * Design patterns: none
 */
public class QRCodeController {
    private AppCompatActivity activity;
    private DBConnection connection;
    private QRCodeDB codeDB;
    private String TAG = "QRCodeController";

    public QRCodeController(AppCompatActivity activity) {
        this.activity = activity;
        this.connection = new DBConnection(activity.getApplicationContext());
        codeDB = new QRCodeDB(connection);
    }

    public void getUserCodes(OnCompleteListener<ArrayList<QRCode>> listener) {
        codeDB.getCodes((foundCodes, success) ->{
            if (success) {
                Log.d(TAG, "Found codes");
                listener.onComplete(foundCodes, true);
            } else {
                Log.d(TAG, "Failed to get user codes");
                listener.onComplete(null, false);
            }
        });
    }

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
}
