package com.example.ihuntwithjavalins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import org.checkerframework.checker.units.qual.A;

/**
 * Displays the starter title page that the app opens to*/
public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_page);

    }

    // clicks anywhere to advance from the screen
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Intent intent = new Intent(TitleActivity.this, QuickNavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    // do nothing (so app doesn't close)
    }

}

