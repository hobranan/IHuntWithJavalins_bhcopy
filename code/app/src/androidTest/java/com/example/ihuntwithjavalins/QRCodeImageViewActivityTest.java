package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Camera.PhotoViewActivity;
import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * QRCodeImageViewActivityTest is a class that tests QRCodeImageViewActivity
 *
 * @version 1.0
 */
public class QRCodeImageViewActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in QRCodeImageViewActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QRCodeImageViewActivity.class, true, true);

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
     * Checks the back button that takes us from QRCodeImageViewActivity to
     * QRCodeViewActivity and throws an error if it doesn't
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
        solo.clickOnButton(R.id.civ_go_back);
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
    }

    /**
     * Checks the see attached photo button that takes us from QRCodeImageViewActivity to
     * PhotoViewActivity and throws an error if it doesn't
     */
    @Test
    public void seeAttachedPhotoButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
        solo.clickOnButton(R.id.civ_show_attached);
        solo.assertCurrentActivity("Wrong Activity", PhotoViewActivity.class);
    }

    /**
     * Checks the see geo location button that takes us from QRCodeImageViewActivity to
     * CodeRefOpenStreetMapActivity and throws an error if it doesn't
     */
    @Test
    public void seeGeolocationButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
        solo.clickOnButton(R.id.civ_no_geo_btn);
        solo.assertCurrentActivity("Wrong Activity", CodeRefOpenStreetMapActivity.class);
    }


}
