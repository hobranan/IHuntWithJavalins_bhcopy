package com.example.ihuntwithjavalins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.ihuntwithjavalins.Comment.Comment;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeDB;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * QRCodeDBTest class is used for testing QRCodeDB functionality.
 * Note this is a test in AndroidTest folder as it relies on Contexts which is a part of the instrumentation
 */
@RunWith(AndroidJUnit4.class)
public class QRCodeDBTest {
    private static final long TIMEOUT = 5;
    QRCodeDB codeDB;

    Comment mockComment;

    QRCode mockCodeTwo;

    QRCode mockCodeOne;

    /**
     * Creates database connection and mock data before each test
     */
    @Before
    public void before() {
        MockDBConnection connection = new MockDBConnection();
        codeDB = new QRCodeDB(connection);
        mockCodeTwo = new QRCode("9D211", "Mindless Dragon Testudine", "356", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockCodeOne = new QRCode("9AB23", "Super Dragon Blob", "355", "picture_man.png", "53.5", "-113.5", "2023.jpg", "20230312");
        mockComment = new Comment("51602", "Sabel Storm", "1679865411702", "This code art is fire");
    }

    /**
     * Remove a Code we have added to the database during testing.
     * @param mockCode The Code that is being removed
     * @throws InterruptedException Thrown when deleting from PlayerDB fails
     */
    private void removeCode(QRCode mockCode) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        codeDB.deleteCode(mockCode, (deletedCode, success) -> {
            successAtomic.set(success);
            latch.countDown();
        });

        if (!latch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
    }

    /**
     * Tests adding QRCode to database
     * @throws InterruptedException Thrown if failed to add QRCode
     */
    @Test
    public void testAddCode() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        AtomicReference<QRCode> addedPlayerAtomic = new AtomicReference<>();
        codeDB.addQRCode(mockCodeTwo, (addedCode, success) -> {
            successAtomic.set(success);
            addedPlayerAtomic.set(addedCode);
            latch.countDown();
        });

        if (!latch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        removeCode(mockCodeTwo);
    }

    /**
     * Tests getting one QRCode from database
     * @throws InterruptedException Thrown if failed to get QRCode
     */
    @Test
    public void testGetCode() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch getLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        codeDB.addQRCode(mockCodeTwo, (addedCode, success) -> {
            successAtomic.set(success);
            addLatch.countDown();
        });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        AtomicReference<QRCode> foundPlayerAtomic = new AtomicReference<>();
        codeDB.getCode(mockCodeTwo, (foundCode, success) -> {
            successAtomic.set(success);
            foundPlayerAtomic.set(foundCode);
            getLatch.countDown();
        });

        if (!getLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        assertEquals(mockCodeTwo.getCodeName(), foundPlayerAtomic.get().getCodeName());
        assertEquals(mockCodeTwo.getCodeDate(), foundPlayerAtomic.get().getCodeDate());
        assertEquals(mockCodeTwo.getCodeHash(), foundPlayerAtomic.get().getCodeHash());
        assertEquals(mockCodeTwo.getCodeLat(), foundPlayerAtomic.get().getCodeLat());
        assertEquals(mockCodeTwo.getCodeLon(), foundPlayerAtomic.get().getCodeLon());
        assertEquals(mockCodeTwo.getCodeGendImageRef(), foundPlayerAtomic.get().getCodeGendImageRef());
        assertEquals(mockCodeTwo.getCodePhotoRef(), foundPlayerAtomic.get().getCodePhotoRef());
        assertEquals(mockCodeTwo.getCodePoints(), foundPlayerAtomic.get().getCodePoints());

        removeCode(mockCodeTwo);
    }

    /**
     * Tests deleting code from codeDB
     * @throws InterruptedException Thrown when failed to delete from codeDB
     */
    @Test
    public void testDeleteCode() throws InterruptedException {
        CountDownLatch addLatch = new CountDownLatch(1);
        CountDownLatch delLatch = new CountDownLatch(1);

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        codeDB.addQRCode(mockCodeTwo, (addedCode, success) -> {
            successAtomic.set(success);
            addLatch.countDown();
        });

        if (!addLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        codeDB.deleteCode(mockCodeTwo, (delCode, success) -> {
            successAtomic.set(success);
            delLatch.countDown();
        });

        if (!delLatch.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());
    }

    /**
     * Tests getting multiple codes from codeDB
     * @throws InterruptedException Thrown if getting codes fails
     */
    @Test
    public void testGetAllCodes() throws InterruptedException {
        CountDownLatch addLatchOne = new CountDownLatch(1);
        CountDownLatch addLatchTwo = new CountDownLatch(1);
        CountDownLatch getLatchOne = new CountDownLatch(1);
        CountDownLatch getLatchTwo = new CountDownLatch(1);

        List<QRCode> codeList = new ArrayList<>();
        codeDB.getCodes((foundCodeList, success) -> {
            codeList.addAll(foundCodeList);
            getLatchOne.countDown();
        });

        if (!getLatchOne.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertEquals(0, codeList.size());

        AtomicReference<Boolean> successAtomic = new AtomicReference<>();
        codeDB.addQRCode(mockCodeOne, (addedCode, success) -> {
            successAtomic.set(success);
            addLatchOne.countDown();
        });

        if (!addLatchOne.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        codeDB.addQRCode(mockCodeTwo, (addedCode, success) -> {
            successAtomic.set(success);
            addLatchTwo.countDown();
        });

        if (!addLatchTwo.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertTrue(successAtomic.get());

        codeDB.getCodes((foundCodeList, success) -> {
            codeList.addAll(foundCodeList);
            getLatchTwo.countDown();
        });

        if (!getLatchTwo.await(TIMEOUT, SECONDS)) {
            throw new InterruptedException();
        }

        assertEquals(2, codeList.size());

        removeCode(mockCodeTwo);
        removeCode(mockCodeOne);
    }
}
