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

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testUpdateRegion() throws InterruptedException{
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

    @Test
    public void testPlayerAddCode() throws InterruptedException{
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

    @Test
    public void testPlayerRemoveCode() throws InterruptedException{
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

    @Test
    public void testPlayerGetCodes() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        List<QRCode> codeList1 = playerDB.getUserCodes();
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

        List<QRCode> codeList2 = playerDB.getUserCodes();

        assertEquals(1, codeList2.size());
        assertEquals(mockCode.getCodeName(), codeList2.get(0).getCodeName());
        assertEquals(mockCode.getCodeDate(), codeList2.get(0).getCodeDate());
        assertEquals(mockCode.getCodeHash(), codeList2.get(0).getCodeHash());
        assertEquals(mockCode.getCodeLat(), codeList2.get(0).getCodeLat());
        assertEquals(mockCode.getCodeLon(), codeList2.get(0).getCodeLon());
        assertEquals(mockCode.getCodeGendImageRef(), codeList2.get(0).getCodeGendImageRef());
        assertEquals(mockCode.getCodePhotoRef(), codeList2.get(0).getCodePhotoRef());
        assertEquals(mockCode.getCodePoints(), codeList2.get(0).getCodePoints());

        removeCode(mockCode);
    }

}
