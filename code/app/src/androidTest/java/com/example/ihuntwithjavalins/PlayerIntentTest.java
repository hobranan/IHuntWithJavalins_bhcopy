package com.example.ihuntwithjavalins;

import static org.junit.Assert.assertTrue;
import static java.util.concurrent.TimeUnit.SECONDS;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.ihuntwithjavalins.Camera.CameraScanActivity;
import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerDB;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.common.DBConnection;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * PlayerIntentTest is a class that tests all the Player user stories (US 01.01.01 through US 01.07.01)
 *
 * @version 1.0
 */

public class PlayerIntentTest {

    /**
     * The Robotium variable we will be using to run tests as if we were a user
     */
    private Solo solo;
    private static final long TIMEOUT = 5;
    PlayerDB playerDB;
    Player mockPlayer;
    QRCode mockCode1;
    QRCode mockCode2;
    QRCode mockCode3;


    /**
     * Remove a Player we have added to the database during testing.
     *
     * @param mockPlayer The Player that is to be removed
     * @throws InterruptedException Thrown when deleting from PlayerDB
     *                              fails.
     */
    private void removePlayer(Player mockPlayer) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.deletePlayer(mockPlayer,
                (deletedPlayer, success) -> {
                    successAtomic.set(success);
                    latch.countDown();
                });

        if (!latch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
    }


    /**
     * Sets up the starting spot for all these tests at the QuickNavActivity
     */
    @Rule
    public ActivityTestRule rule = new ActivityTestRule(QuickNavActivity.class, true, true);

    /**
     * Sets up the Activity before every test
     */
    @Before
    public void setUp() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        mockPlayer = new Player("John Doe", "jd@ualberta.ca", "Canada", "20230101");
        mockCode1 = new QRCode("9D211", "Mindless Dragon Testudine", "500", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockCode2 = new QRCode("9D212", "Mindfull Lizard Crustation", "400", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockCode3 = new QRCode("9D213", "Mindless Dragon Testudine", "100", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");

        DBConnection connection = new DBConnection(rule.getActivity());
        connection.setUsername(rule.getActivity(), "John Doe");
        playerDB = new PlayerDB(connection);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.addPlayer(mockPlayer,
                (addedPlayer, success) -> {
                    successAtomic.set(success);
                    latch.countDown();
                });

    }

    /**
     * Makes sure the set up didn't fail
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Makes sure the player can open the camera to scan a QR Code for US 01.01.01
     */
    @Test
    public void openCamera() throws InterruptedException{
        solo.assertCurrentActivity("Wrong Activity", QuickNavActivity.class);
        solo.clickOnView(solo.getView(R.id.button_qn_scanCode));
        solo.assertCurrentActivity("Wrong Activity", CameraScanActivity.class);
    }

    /**
     *
     */
    @Test
    public void PLACEHOLDERNAMETEST() throws InterruptedException{
        removePlayer(mockPlayer);
    }

    @After
    public void cleanUp() throws InterruptedException{
    //    removePlayer(mockPlayer);
    }

}
