package com.example.ihuntwithjavalins;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Profile.ProfileActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(ProfileActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void quickNavButton() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnButton("Quick Nav");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);

    }

}
