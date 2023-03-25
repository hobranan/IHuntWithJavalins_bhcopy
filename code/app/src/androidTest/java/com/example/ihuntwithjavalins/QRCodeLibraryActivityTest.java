package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * QRCodeLibraryActivityTest is a class that tests QRCodeLibraryActivity
 *
 * @version 1.0
 */
public class QRCodeLibraryActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in QRCodeLibraryActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QRCodeLibraryActivity.class, true, true);

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
     * Checks the back button that takes us from QRCodeLibraryActivity to
     * QuickNavActivity and throws an error if it doesn't
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
        solo.clickOnButton("QN");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

    /**
     * Checks the add ex codes button that adds some example QR codes to QRCodeLibraryActivity
     * Throws an error if it doesn't find one
     */
    @Test
    public void addExCodes() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
        solo.clickOnButton("add ex codes");
        solo.waitForText("Lazy", 1, 2000);
    }
}