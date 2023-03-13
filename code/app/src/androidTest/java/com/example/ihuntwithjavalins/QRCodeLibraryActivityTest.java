package com.example.ihuntwithjavalins;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QRCodeLibraryActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QRCodeLibraryActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
        solo.clickOnButton("QN");
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

    @Test
    public void addExCodes() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
        solo.clickOnButton("add ex codes");
        solo.waitForText("Lazy", 1, 2000);
    }
}