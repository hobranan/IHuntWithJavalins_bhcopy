package com.example.ihuntwithjavalins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.Player.PlayerDB;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
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

    /**
     * Creates database connection and mock data before each test
     */
    @Before
    public void before() {
        MockDBConnection connection = new MockDBConnection();
        playerDB = new PlayerDB(connection);
        mockCode = new QRCode("9D211", "Mindless Dragon Testudine", "356", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockPlayer = new Player("Sabel Storm", "hblow@ualberta.ca", "Canada", "20230319");
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
     * Remove a Code we have added to the database during testing.
     *
     * @param mockCode The Code that is being removed
     * @throws InterruptedException Thrown when deleting from PlayerDB fails
     */
    private void removeCode(QRCode mockCode) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.playerDelQRCode(mockCode,
                (deletedCode, success) -> {
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
     *
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

        removePlayer(mockPlayer);
    }

    /**
     * Tests getting a player from the database
     *
     * @throws InterruptedException Thrown when failed to get player from PlayerDB
     */
    @Test
    public void testGetPlayer() throws InterruptedException {
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
     *
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
                (deletedPlayer, success) -> {
                    successAtomic.set(success);
                    deletedPlayerAtomic.set(deletedPlayer);
                    deleteLatch.countDown();
                });

        if (!deleteLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals(mockPlayer, deletedPlayerAtomic.get());
    }

    /**
     * Tests updating email of player from the database
     *
     * @throws InterruptedException Thrown when updating email info fails
     */
    @Test
    public void testUpdateEmail() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch getLatch = new CountDownLatch(1);
        CountDownLatch updateLatch = new CountDownLatch(1);

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
        assertEquals(mockPlayer.getEmail(), foundPlayerAtomic.get().getEmail());

        AtomicReference<Player> updatePlayerAtomic = new AtomicReference<>();
        playerDB.updatePlayerEmail(mockPlayer, "nothblow@ualberta.ca",
                (updatePlayer, success) -> {
                    successAtomic.set(success);
                    updatePlayerAtomic.set(updatePlayer);
                    updateLatch.countDown();
                });

        if (!updateLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals("nothblow@ualberta.ca", updatePlayerAtomic.get().getEmail());

        removePlayer(mockPlayer);    // note that even though we pass a player into removePlayer, only the username matters as usernames are unique so even though we updated the email, it should still delete the player with that username
    }

    /**
     * Tests updating region of Player in database
     *
     * @throws InterruptedException Thrown when updating fails
     */
    @Test
    public void testUpdateRegion() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch getLatch = new CountDownLatch(1);
        CountDownLatch updateLatch = new CountDownLatch(1);

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
        assertEquals(mockPlayer.getRegion(), foundPlayerAtomic.get().getRegion());

        AtomicReference<Player> updatePlayerAtomic = new AtomicReference<>();
        playerDB.updatePlayerRegion(mockPlayer, "United States",
                (updatePlayer, success) -> {
                    successAtomic.set(success);
                    updatePlayerAtomic.set(updatePlayer);
                    updateLatch.countDown();
                });

        if (!updateLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals("United States", updatePlayerAtomic.get().getRegion());

        removePlayer(mockPlayer);
    }

    /**
     * Tests adding a code to player in playerDB
     *
     * @throws InterruptedException Thrown when adding code fails
     */
    @Test
    public void testPlayerAddCode() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        AtomicReference<QRCode> addedCodeAtomic = new AtomicReference<>();
        playerDB.playerAddQRCode(mockCode,
                (addedCode, success) -> {
                    successAtomic.set(success);
                    addedCodeAtomic.set(addedCode);
                    addLatch.countDown();
                });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        removeCode(mockCode);
    }

    /**
     * Tests removing a code from the player in playerDB
     *
     * @throws InterruptedException Thrown when removing code fails
     */
    @Test
    public void testPlayerRemoveCode() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch deleteLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        playerDB.playerAddQRCode(mockCode, (addedCode, success) -> {
            successAtomic.set(success);
            addLatch.countDown();
        });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        AtomicReference<QRCode> deletedPlayerAtomic = new AtomicReference<>();
        playerDB.playerDelQRCode(mockCode,
                (deletedCode, success) -> {
                    successAtomic.set(success);
                    deletedPlayerAtomic.set(deletedCode);
                    deleteLatch.countDown();
                });

        if (!deleteLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
        assertEquals(mockCode, deletedPlayerAtomic.get());
    }

    /**
     * Tests getting all codes player owns from database
     *
     * @throws InterruptedException Thrown when getting all codes fails
     */
    @Test
    public void testPlayerGetCodes() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);

        CountDownLatch getLatchOne = new CountDownLatch(1);
        CountDownLatch getLatchTwo = new CountDownLatch(1);
        List<QRCode> codeList1 = new ArrayList<>();
        playerDB.getUserCodes((foundCodes, success) -> {
            if (success) {
                codeList1.addAll(foundCodes);
                getLatchOne.countDown();
            }
        });

        if (!getLatchOne.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertEquals(0, codeList1.size());

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        AtomicReference<QRCode> addedCodeAtomic = new AtomicReference<>();
        playerDB.playerAddQRCode(mockCode,
                (addedCode, success) -> {
                    successAtomic.set(success);
                    addedCodeAtomic.set(addedCode);
                    addLatch.countDown();
                });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        playerDB.getUserCodes((foundCodes, success) -> {
            if (success) {
                codeList1.addAll(foundCodes);
                getLatchTwo.countDown();
            }
        });

        if (!getLatchTwo.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertEquals(1, codeList1.size());
        assertEquals(mockCode.getCodeName(), codeList1.get(0).getCodeName());
        assertEquals(mockCode.getCodeDate(), codeList1.get(0).getCodeDate());
        assertEquals(mockCode.getCodeHash(), codeList1.get(0).getCodeHash());
        assertEquals(mockCode.getCodeLat(), codeList1.get(0).getCodeLat());
        assertEquals(mockCode.getCodeLon(), codeList1.get(0).getCodeLon());
        assertEquals(mockCode.getCodeGendImageRef(), codeList1.get(0).getCodeGendImageRef());
        assertEquals(mockCode.getCodePhotoRef(), codeList1.get(0).getCodePhotoRef());
        assertEquals(mockCode.getCodePoints(), codeList1.get(0).getCodePoints());


        removeCode(mockCode);
    }

}
