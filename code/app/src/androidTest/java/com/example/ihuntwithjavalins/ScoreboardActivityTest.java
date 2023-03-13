package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Scoreboard.ScoreboardActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * ScoreboardActivityTest is a class that tests ScoreboardActivity
 *
 * @version 1.0
 */
public class ScoreboardActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in ScoreboardActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(ScoreboardActivity.class, true, true);

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
     * Checks the quick nav button that takes us from ScoreboardActivity to
     * QuickNavActivity and throws an error if it doesn't
     */
    @Test
    public void quickNavButton() {
        solo.assertCurrentActivity("Wrong Activity", ScoreboardActivity.class);
        solo.clickOnButton("QUICK NAV");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }
}
