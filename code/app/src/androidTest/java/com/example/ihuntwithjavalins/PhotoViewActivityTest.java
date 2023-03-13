package com.example.ihuntwithjavalins;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Camera.PhotoViewActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeImageViewActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PhotoViewActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(PhotoViewActivity.class, true, true);

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
        solo.assertCurrentActivity("Wrong Activity", PhotoViewActivity.class);
        solo.clickOnButton(R.id.photo_back_btn);
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
    }


}
