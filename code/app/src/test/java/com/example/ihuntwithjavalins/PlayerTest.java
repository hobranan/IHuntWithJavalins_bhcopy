package com.example.ihuntwithjavalins;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for Player model class
 */
public class PlayerTest {
    /**
     * Creates mock player with all fields null
     * @return the mock player
     */
    public Player mockPlayer() {return new Player();}


    /**
     * Creates mock player with only username and region
     * @return the mock player
     */
    public Player mockPlayerWithSomeInfo(){
        Player temp = new Player();
        temp.setUsername("Sabel Storm");
        temp.setRegion("Canada");
        return temp;
    }

    /**
     * Creates mock player with username, region, and email
     * @return the mock player
     */
    public Player mockPlayerWithInfo(){return new Player("Sabel Storm", "hblow@ualberta.ca", "Canada");}

    /**
     * Tests whether a new Player has all null fields
     */
    @Test
    public void testEmptyPlayer() {
        Player player = mockPlayer();

        assertNull(player.getUsername(), "Username should be null");
        assertNull(player.getEmail(), "Email should be null");
        assertNull(player.getRegion(), "Region should be null");
        assertEquals(0, player.getCodes().size(), "Code list should be empty");
        assertNull(player.getDateJoined(), "Date joined should be null");
    }

    /**
     * Tests whether  Player constructor creates Player object with correct data
     */
    @Test
    public void testPlayerWithInfo() {
        Player player = mockPlayerWithInfo();

        assertEquals("Sabel Storm", player.getUsername(), "Username should be Sabel Storm");
        assertEquals("hblow@ualberta.ca", player.getEmail(), "Email should be hblow@ualberta.ca");
        assertEquals("Canada", player.getRegion(), "Region should be Canada");
        assertEquals(0, player.getCodes().size(), "Code list should be empty");
        assertNull(player.getDateJoined(), "Date joined should be null");
    }

    /**
     * Tests whether Player constructor creates Player object with correct data
     */
    @Test
    public void testPlayerWithSomeInfo() {
        Player player = mockPlayerWithSomeInfo();

        assertEquals("Sabel Storm", player.getUsername(), "Username should be Sabel Storm");
        assertNull(player.getEmail(), "Email should be null");
        assertEquals("Canada", player.getRegion(), "Region should be Canada");
        assertEquals(0, player.getCodes().size(), "Code list should be empty");
        assertNull(player.getDateJoined(), "Date joined should be null");
    }

    /**
     * Tests getting and setting username of Player
     */
    @Test
    public void testUsername() {
        Player player = mockPlayer();

        // Checks if username setter and getter work
        assertNull(player.getUsername(), "Username should be null");
        player.setUsername("Banana Lord");
        assertEquals("Banana Lord", player.getUsername(), "Username should be Banana Lord");

        // Checks if get username works on constructor created username
        player = mockPlayerWithInfo();
        assertEquals("Sabel Storm", player.getUsername(), "Username should be Sabel Storm");
    }

    /**
     * Tests getting and setting email of Player
     */
    @Test
    public void testEmail() {
        Player player = mockPlayer();

        // Checks if email setter and getter work
        assertNull(player.getEmail(), "Email should be null");
        player.setEmail("hblow@ualberta.ca");
        assertEquals("hblow@ualberta.ca", player.getEmail(), "Email should be hblow@ualberta.ca");

        // Checks if get email works on constructor created email
        player = mockPlayerWithInfo();
        assertEquals("hblow@ualberta.ca", player.getEmail(), "Email should be hblow@ualberta.ca");
    }

    /**
     * Tests getting and setting region of Player
     */
    @Test
    public void testRegion() {
        Player player = mockPlayer();

        // Checks if region setter and getter work
        assertNull(player.getRegion(), "Region should be null");
        player.setRegion("Canada");
        assertEquals("Canada", player.getRegion(), "Region should be Canada");

        // Checks if get region works on constructor created region
        player = mockPlayerWithInfo();
        assertEquals("Canada", player.getRegion(), "Region should be Canada");
    }

    /**
     * Tests getting and setting date joined of Player
     */
    @Test
    public void testDate() {
        Player player = mockPlayer();

        assertNull(player.getDateJoined(), "Date joined should be null");
        player.setDateJoined("Jan 15");
        assertEquals("Jan 15", player.getDateJoined(), "Date joined should be Jan 15");
    }

    /**
     * Tests add one or multiple codes into Player, deleting code and hasCode functionality
     */
    @Test
    public void testPlayerCodes() {
        // Setup list of codes to have dummy code data
        Player player = mockPlayerWithInfo();
        QRCode code1 = new QRCode("1", "Banana", "5", "ref", "10", "12", "photRef", "2");
        QRCode code2 = new QRCode("1", "Bana", "5", "ref", "10", "12", "photRef", "2");
        List<QRCode> codeList = new ArrayList<>();
        codeList.add(code1);
        codeList.add(code2);

        // Test if addCode method adds to Player code list
        assertEquals(0, player.getCodes().size(), "Player should have no codes");
        player.addCode(code1);
        assertTrue(player.getCodes().contains(code1), "Player should have code1");
        player.delCode(code1);
        assertFalse(player.getCodes().contains(code1), "Player should not have code1");

        // Test if addCodes method adds multiple codes to Player code list
        assertEquals(0, player.getCodes().size(), "Player should have no codes");
        player.addCodes(codeList);
        assertTrue(player.getCodes().containsAll(codeList), "Player should have all codes in codeList");
        player.delCode(code1);
        player.delCode(code2);
        assertFalse(player.getCodes().contains(code1), "Player should not have code1");
        assertFalse(player.getCodes().contains(code2), "Player should not have code2");

        // Test if hasCode method works
        assertEquals(0, player.getCodes().size(), "Player should have no codes");
        player.addCode(code1);
        assertTrue(player.hasCode(code1), "Player should have code1");
        player.delCode(code1);
        assertFalse(player.hasCode(code2), "Player should not have code1");
    }
}
