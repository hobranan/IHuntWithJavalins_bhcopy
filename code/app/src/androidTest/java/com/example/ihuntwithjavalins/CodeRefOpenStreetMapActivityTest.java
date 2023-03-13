package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CodeRefOpenStreetMapActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(CodeRefOpenStreetMapActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", CodeRefOpenStreetMapActivity.class);
        solo.clickOnButton(R.id.map_backButton);
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
    }


}
