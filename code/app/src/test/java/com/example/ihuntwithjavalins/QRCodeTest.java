package com.example.ihuntwithjavalins;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for QRCode model class
 */
public class QRCodeTest {

    // Needs to make QRCode based on textCode
//    public QRCode mockScannedQRCode() {
//        String textCode =
//    }

    /**
     * Creates mock QRCode with all fields null
     * @return the mock QRCode
     */
    public QRCode mockEmptyQRCode() {
        return new QRCode();
    }

    /**
     * Creates mock QRCode with initialized fields
     * @return the mock QRCode
     */
    public QRCode mockQRCode() {
        return new QRCode("9D211", "Mindless Dragon Testudine", "356", "picture_min.png", "53.5", "-113.5", "2023.jpg", "20230312");
    }

    /**
     * Tests empty QRCode constructor null fields
     */
    @Test
    public void testEmptyCode() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeHash(), "Hash should be null");
        assertNull(code.getCodeName(), "Name should be null");
        assertNull(code.getCodeLat(), "Code latitude should be null");
        assertNull(code.getCodeLon(), "Code longitude should be null");
        assertNull(code.getCodeGendImageRef(), "Image reference should be null");
        assertNull(code.getCodePhotoRef(), "Photo reference should be null");
        assertNull(code.getCodePoints(), "Points should be null");
        assertNull(code.getCodeDate(), "Code date should be null");
    }

    /**
     * Test initialized QRCode constructor fields
     */
    @Test
    public void testQRCodeWithInfo() {
        QRCode code = mockQRCode();

        assertEquals("9D211", code.getCodeHash(), "Hash should be 9D211");
        assertEquals("Mindless Dragon Testudine", code.getCodeName(), "Name should be Mindless Dragon Testudine");
        assertEquals("356", code.getCodePoints(), "Point should be 356");
        assertEquals("picture_min.png", code.getCodeGendImageRef(), "Image reference should be picture_min.png");
        assertEquals("53.5", code.getCodeLat(), "Latitude should be 53.5");
        assertEquals("-113.5", code.getCodeLon(), "Longitude should be -113.5");
        assertEquals("2023.jpg", code.getCodePhotoRef(), "Photo reference should be 2023.jpg");
        assertEquals("20230312", code.getCodeDate(), "Code date should be 20230312");
    }

    /**
     * Test getting and setting code date
     */
    @Test
    public void testCodeDate() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeDate(), "Code date should be null");
        code.setCodeDate("20230512");
        assertEquals("20230512", code.getCodeDate(), "COde date should be 20230512");
    }

    /**
     * Test getting and setting code hash
     */
    @Test
    public void testCodeHash() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeHash(), "Hash should be null");
        code.setCodeHash("D12A");
        assertEquals("D12A", code.getCodeHash(), "Hash should be D12A");
    }

    /**
     * Test getting and setting code name
     */
    @Test
    public void testCodeName(){
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeName(), "Name should be null");
        code.setCodeName("Banana Dragon Lord");
        assertEquals("Banana Dragon Lord", code.getCodeName(), "Name should be Banana Dragon Lord");
    }

    /**
     * Test getting and setting code points
     */
    @Test
    public void testCodePoints() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodePoints(), "Points should be null");
        code.setCodePoints("500");
        assertEquals("500", code.getCodePoints(), "Points should be 500");
    }

    /**
     * Test getting and setting image and photo references of code
     */
    @Test
    public void testCodeReferences() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeGendImageRef(), "Image reference should be null");
        assertNull(code.getCodePhotoRef(), "Photo reference should be null");
        code.setCodeGendImageRef("AIDragon.png");
        code.setCodePhotoRef("Cheetos.jpg");
        assertEquals("AIDragon.png", code.getCodeGendImageRef(), "Image reference should be AIDragon.png");
        assertEquals("Cheetos.jpg", code.getCodePhotoRef(), "Photo reference should be Cheetos.jpg");
    }

    /**
     * Test getting and setting geolocation info of code
     */
    @Test
    public void testCodeGeolocation() {
        QRCode code = mockEmptyQRCode();

        assertNull(code.getCodeLat(), "Code latitude should be null");
        assertNull(code.getCodeLon(), "Code longitude should be null");
        code.setCodeLat("53.7");
        code.setCodeLon("-123.1");
        assertEquals("53.7", code.getCodeLat(), "Code latitude should be 53.7");
        assertEquals("-123.1", code.getCodeLon(), "Code longitude should be -123.1");
    }

//    @Test
//    public void testWordToHash() {
//        // test analyseWordToHash here
//    }
}
