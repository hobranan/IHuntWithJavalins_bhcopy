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

/**
 * QuickNavActivityTest is a class that tests QuickNavActivity
 *
 * @version 1.0
 */
public class QuickNavActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in QuickNavActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QuickNavActivity.class, true, true);

    /**
     * Sets up the Activity before every test
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Makes sure the set up didn't fail
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Checks the scan a code button that takes us from QuickNavActivity to
     * CameraScanActivity and throws an error if it doesn't
     */
    @Test
    public void scanACodeButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Scan A Code");
        solo.assertCurrentActivity("Wrong Activity", CameraScanActivity.class);
    }

    /**
     * Checks the open the map button that takes us from QuickNavActivity to
     * OpenStreetMapActivity and throws an error if it doesn't
     */
    @Test
    public void openTheMapButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Open The Map");
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
    }

    /**
     * Checks the see my code library button that takes us from QuickNavActivity to
     * QRCodeLibraryActivity and throws an error if it doesn't
     */
    @Test
    public void seeMyCodeLibraryButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("See My Code Library");
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
    }

    /**
     * Checks the see the scoreboard button that takes us from QuickNavActivity to
     * ScoreboardActivity and throws an error if it doesn't
     */
    @Test
    public void seeTheScoreboardButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("See The Scoreboard");
        solo.assertCurrentActivity("Wrong Activity", ScoreboardActivity.class);
    }

    /**
     * Checks the look at my profile button that takes us from QuickNavActivity to
     * ProfileActivity and throws an error if it doesn't
     */
    @Test
    public void lookAtMyProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnButton("Look At My Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

}
