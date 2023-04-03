package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ScoringIntentTests {
    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in ProfilActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(ProfileActivity.class, true, true);

    /**
     * Sets up the Activity before every test
     * @throws Exception if the setup fails
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
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

    /**
     * Checks players high scores when clicked on button US07.01.01
     * Able to see high-score when clicked on button
     * @throws Exception if the setup fails
     */
    @Test
    public void highScores() throws Exception{
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.prf_totalpointsplacing_data));
    }
    /**
     * Checks players high scores when clicked on button US07.02.01
     * Able to see the ranking for highest scoring unique QR code
     */
    @Test
    public void seeRank() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.prf_highestcodevalueplacing_data));
    }


}
