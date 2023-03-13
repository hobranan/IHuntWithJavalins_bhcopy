package com.example.ihuntwithjavalins;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(SignUpActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void goodSignUp() {
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edittext_signup_username), "John Doe");
        solo.enterText((EditText) solo.getView(R.id.edittext_signup_email), "JDoe@noMail.com");
        solo.pressSpinnerItem(R.id.spinner_signup_region, 1);
        solo.clickOnButton("CONFIRM");
        solo.assertCurrentActivity("WrongActivity", QuickNavActivity.class);
    }

    public void badSignUp() {
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.clickOnButton("CONFIRM");
        solo.waitForText("some info is empty", 1, 2000);
    }
}