package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Camera.CameraScanActivity;
import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.Scoreboard.ScoreboardActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QuickNavActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QuickNavActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    @Test
    public void scanACodeButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Scan A Code");
        solo.assertCurrentActivity("Wrong Activity", CameraScanActivity.class);
    }

    @Test
    public void openTheMapButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Open The Map");
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
    }

    @Test
    public void seeMyCodeLibraryButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("See My Code Library");
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
    }

    @Test
    public void seeTheScoreboardButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("See The Scoreboard");
        solo.assertCurrentActivity("Wrong Activity", ScoreboardActivity.class);
    }

    @Test
    public void lookAtMyProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Look At My Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

}
