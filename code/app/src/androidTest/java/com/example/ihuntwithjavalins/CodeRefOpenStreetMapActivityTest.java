package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * CodeRefOpenStreetMapActivityTest is a class that tests CodeRefOpenStreetMapActivity
 *
 * @version 1.0
 */
public class CodeRefOpenStreetMapActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in CodeRefOpenStreetMapActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(CodeRefOpenStreetMapActivity.class, true, true);

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
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Checks the back button that takes us from CodeRefOpenStreetMapActivity to
     * QRCodeImageViewActivity and throws an error if it doesn't
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", CodeRefOpenStreetMapActivity.class);
        solo.clickOnButton(R.id.map_backButton);
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
    }


}
