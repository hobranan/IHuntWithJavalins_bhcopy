package com.example.ihuntwithjavalins;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LocationIntentTests {
    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in OpenStreetMapActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(OpenStreetMapActivity.class, true, true);

    /**
     * Sets up the Activity before every test
<<<<<<< Updated upstream
     * @throws Exception Thrown when robotium setup fails
=======
     * @throws Exception if the setup fails
>>>>>>> Stashed changes
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Makes sure the set up didn't fail
     * @throws Exception if the setup fails
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Checks if in current activity
     */
    @Test
    public void checkCurrentActivity() {
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
    /**
     * Checks the back button that takes us from OpenStreetMapActivity to
     * QuickNavActivity and throws an error if it doesn't
     * Completes US06.01.01
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);

    }
    /**
     * US 05.02.01 - CHeck to see if QRcode can be searched
     * I have added this US story on this file as it corresponds to searching for QRcodes using GeoLocation
     */
    @Test
    public void searchQRCode(){
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
        solo.clickOnView(solo.getView(R.id.map_region_btn));
    }


}


