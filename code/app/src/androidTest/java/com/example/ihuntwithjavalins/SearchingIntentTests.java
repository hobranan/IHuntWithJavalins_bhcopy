package com.example.ihuntwithjavalins;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.example.ihuntwithjavalins.Scoreboard.ScoreboardActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchingIntentTests {
    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in ScoreBoard Activity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(ScoreboardActivity.class, true, true);

    /**
     * Sets up the Activity before every test
     *
     * @throws Exception if the setup fails
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Makes sure the set up didn't fail
     *
     * @throws Exception if the setup fails
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Able to test if player can search for other players or not in search
     * US05.01.01
     */
    @Test
    public void searchPlayer() {
        solo.assertCurrentActivity("Wrong Activity", ScoreboardActivity.class);
        solo.enterText((EditText) solo.getView(R.id.search_user), "Khushi Shah");
    }


}
