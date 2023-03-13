package com.example.ihuntwithjavalins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerDB;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * TODO: Update test after adding the added fields of player to playerdb
 * PlayerDBTest class is used for testing PlayerDB functionality.
 * Note this is a test in AndroidTest folder as it relies on Contexts which is a part of the instrumentation
 * Based on Well Fed Project DB test classes.
 */
@RunWith(AndroidJUnit4.class)
public class PlayerDBTest {
    private static final long TIMEOUT = 5;
    PlayerDB playerDB;

    Player mockPlayer;

    QRCode mockCode;

    @Before
    public void before() {
        MockDBConnection connection = new MockDBConnection();
        playerDB = new PlayerDB(connection);
        mockCode = new QRCode("9D211", "Mindless Dragon Testudine", "356", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockPlayer = new Player("Sabel Storm", "hblow@ualberta.ca", "Canada");
    }

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
                    (deletedIngredient, success) -> {
                        successAtomic.set(success);
                        latch.countDown();
                    });

            if (!latch.await(TIMEOUT, SECONDS)) {
                throw new InterruptedException();
            }

            assertTrue(successAtomic.get());
        }

    /**
     * Tests adding a player to the database
     * @throws InterruptedException Thrown when adding from PlayerDB fails
     */
    @Test
    public void testAddPlayer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        AtomicReference<Player> addedPlayerAtomic = new AtomicReference<>();
        playerDB.addPlayer(mockPlayer,
                (addedPlayer, success) -> {
                    successAtomic.set(success);
                    addedPlayerAtomic.set(addedPlayer);
                    latch.countDown();
                });

        if (!latch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals(mockPlayer, addedPlayerAtomic.get());

        removePlayer(mockPlayer);
    }

    @Test
    public void testGetIngredient() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch getLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.addPlayer(mockPlayer,
                (addedPlayer, success) -> {
                    successAtomic.set(success);
                    addLatch.countDown();
                });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        AtomicReference<Player> foundPlayerAtomic = new AtomicReference<>();
        playerDB.getPlayer(mockPlayer,
                (foundPlayer, success) -> {
                    successAtomic.set(success);
                    foundPlayerAtomic.set(foundPlayer);
                    getLatch.countDown();
                });

        if (!getLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals(mockPlayer.getUsername(), foundPlayerAtomic.get().getUsername());
        assertEquals(mockPlayer.getEmail(), foundPlayerAtomic.get().getEmail());
        assertEquals(mockPlayer.getRegion(), foundPlayerAtomic.get().getRegion());

        removePlayer(mockPlayer);
    }

    /**
     * Tests deleting a player from the database
     * @throws InterruptedException Thrown when adding a Player or deleting from PlayerDB fails
     */
    @Test
    public void testDeletePlayer() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch deleteLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.addPlayer(mockPlayer, (addedPlayer, success) -> {
            successAtomic.set(success);
            addLatch.countDown();
        });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        AtomicReference<Player> deletedPlayerAtomic = new AtomicReference<>();
        playerDB.deletePlayer(mockPlayer,
                (deletedIngredient, success) -> {
                    successAtomic.set(success);
                    deletedPlayerAtomic.set(deletedIngredient);
                    deleteLatch.countDown();
                });

        if (!deleteLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals(mockPlayer, deletedPlayerAtomic.get());
    }

}
