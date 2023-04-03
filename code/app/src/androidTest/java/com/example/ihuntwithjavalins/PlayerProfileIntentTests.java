package com.example.ihuntwithjavalins;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PlayerProfileIntentTests {

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
     * Checks if in current activity
     */
    @Test
    public void checkCurrentActivity() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

    /**
     * Checks if backButton works for page
     */
    @Test
    public void quickNavButton() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnButton("Quick Nav");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

    /**
     * Checks to make sure a properly filled out sign up works by inputting a name, email, and
     * selecting an item from the spinner then clicking confirm
     * we should be taken to QuickNavActivity
     * US04.01.01 and US04.02.01
     */
    @Test
    public void goodSignUp() {
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edittext_signup_username), "Khushi Shah");
        solo.enterText((EditText) solo.getView(R.id.edittext_signup_email), "khushi3@ualberta.ca");
        solo.pressSpinnerItem(R.id.spinner_signup_region, 1);
        solo.clickOnButton("CONFIRM");
        solo.assertCurrentActivity("WrongActivity", QuickNavActivity.class);
    }

    /**
     * Checks to make sure an improperly filled out sign up doesn't progress us by filling out
     * nothing we should be told some info is empty
     */
    @Test
    public void badSignUp() {
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.clickOnButton("CONFIRM");
        solo.waitForText("some info is empty", 1, 2000);
    }
}