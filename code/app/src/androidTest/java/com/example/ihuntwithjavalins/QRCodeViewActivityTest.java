package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QRCodeViewActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QRCodeViewActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.go_back);
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
    }

    @Test
    public void quickNavButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.imageButton);
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

    @Test
    public void QRImageButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.image_button);
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
    }

    @Test
    public void deleteQRButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.btn_remove_code);
        solo.waitForText("Confirmation", 1, 2000);
        solo.clickOnButton("NO");
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.btn_remove_code);
        solo.waitForText("Confirmation", 1, 2000);
        solo.clickOnButton("YES");
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
    }

}
