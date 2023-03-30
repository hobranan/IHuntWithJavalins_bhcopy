package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * TitleActivityTest is a class that tests TitleActivity
 *
 * @version 1.0
 */
public class TitleActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in TitleActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(TitleActivity.class, true, true);

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
     * Clicks on the screen, we should be taken from TitleActivity to SignUpActivity
     */

    @Test
    public void clickOnScreen() {
        solo.assertCurrentActivity("Wrong Activity", TitleActivity.class);
        solo.sleep(5000);
        solo.clickOnScreen(5, 5);
//        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

}
