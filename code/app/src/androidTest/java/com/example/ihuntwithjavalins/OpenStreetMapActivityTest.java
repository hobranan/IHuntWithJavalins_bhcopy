package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * OpenStreetMapActivityTest is a class that tests OpenStreetMapActivity
 *
 * @version 1.0
 */
public class OpenStreetMapActivityTest {

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
     * Checks the back button that takes us from OpenStreetMapActivity to
     * QuickNavActivity and throws an error if it doesn't
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }
}