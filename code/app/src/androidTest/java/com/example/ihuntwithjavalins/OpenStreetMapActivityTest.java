package com.example.ihuntwithjavalins;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.OpenStreetMapActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class OpenStreetMapActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(OpenStreetMapActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", OpenStreetMapActivity.class);
        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }
}