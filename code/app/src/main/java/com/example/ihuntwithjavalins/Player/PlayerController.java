package com.example.ihuntwithjavalins.Player;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.common.DBConnection;

public class PlayerController {
    private AppCompatActivity activity;
    private PlayerDB playerDB;

    public PlayerController(AppCompatActivity activity) {
        this.activity = activity;
        DBConnection connection = new DBConnection(activity.getApplicationContext());
    }
}
