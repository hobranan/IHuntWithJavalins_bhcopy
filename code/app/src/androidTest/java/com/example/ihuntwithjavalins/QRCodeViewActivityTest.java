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

/**
 * QRCodeViewActivityTest is a class that tests QRCodeViewActivity
 *
 * @version 1.0
 */
public class QRCodeViewActivityTest {

    /**
     * The Robotium variable we will be using to test the class
     */
    private Solo solo;

    /**
     * Puts us in QRCodeViewActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QRCodeViewActivity.class, true, true);

    /**
     * Sets up the Activity before every test
     */
    @Before
    public void setUp() throws Exception{
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
     * Checks the back button that takes us from QRCodeViewActivity to
     * QRCodeLibraryActivity and throws an error if it doesn't
     */
    @Test
    public void backButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.btn_cvi_back);
        solo.assertCurrentActivity("Wrong Activity", QRCodeLibraryActivity.class);
    }

    /**
     * Checks the quick nav button button that takes us from QRCodeViewActivity to
     * QuickNavActivity and throws an error if it doesn't
     */
    @Test
    public void quickNavButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);

      solo.clickOnButton(R.id.btn_cvi_quicknav);

    //    solo.clickOnButton(R.id.btn_cvi_image);

        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
    }

    /**
     * Checks the QR image button button button that takes us from QRCodeViewActivity to
     * QRCodeImageViewActivity and throws an error if it doesn't
     */
    @Test
    public void QRImageButton() {
        solo.assertCurrentActivity("Wrong Activity", QRCodeViewActivity.class);
        solo.clickOnButton(R.id.image_button);
        solo.assertCurrentActivity("Wrong Activity", QRCodeImageViewActivity.class);
    }

    /**
     * Checks the delete QR button button which should pop up with a confirmation
     * if no, it should just close the confirmation window and nothing else
     * if yes, it should remove the QR code from the list and return us to QRCodeLibraryActivity
     */
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
