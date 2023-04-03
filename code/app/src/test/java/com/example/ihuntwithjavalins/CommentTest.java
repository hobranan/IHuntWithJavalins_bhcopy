package com.example.ihuntwithjavalins;

import com.example.ihuntwithjavalins.Comment.Comment;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Comment Model Class
 */
public class CommentTest {
    /**
     * Creates mock comment with all fields null
     * @return the mock comment
     */
    public Comment mockEmptyComment() {
        return new Comment();
    }

    /**
     * Creates mock comment with initialized fields
     * @return the mock comment
     */
    public Comment mockComment() {
        return new Comment("51602", "Sabel Storm", "1679865411702", "This code art is fire");
    }

    /**
     * Tests if all fields are null for empty mock comment constructor
     */
    @Test
    public void testEmptyComment() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getUsername(), "Username should be null");
        assertNull(comment.getCodeComment(), "Comment message should be null");
        assertNull(comment.getHashcode(), "Hashcode of comment should be null");
        assertNull(comment.getUnixMillis_DateTime(), "Unix Millisecond DateTime should be null");
    }

    /**
     * Tests if all fields are correctly initialized after mock comment constructor
     */
    @Test
    public void testComment() {
        Comment comment = mockComment();

        assertEquals("51602", comment.getHashcode());
        assertEquals("Sabel Storm", comment.getUsername());
        assertEquals("1679865411702", comment.getUnixMillis_DateTime());
        assertEquals("This code art is fire", comment.getCodeComment());
    }

    /**
     * Tests comment username getter and setter
     */
    @Test
    public void testUsername() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getUsername(), "Username should be null");
        comment.setUsername("Sabel Storm");
        assertEquals("Sabel Storm", comment.getUsername());
    }

    /**
     * Tests comment hash code getter and setter
     */
    @Test
    public void testHashCode() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getHashcode(), "Comment hash should be null");
        comment.setHashcode("51602");
        assertEquals("51602", comment.getHashcode());
    }

    /**
     * Tests comment unix millisecond date time getter and setter
     */
    @Test
    public void testDateTime() {
        Comment comment = mockEmptyComment();
        assertNull(comment.getUnixMillis_DateTime(), "Comment datetime should be null");
        comment.setUnixMillis_DateTime("1679865411702");
        assertEquals("1679865411702", comment.getUnixMillis_DateTime());
    }

    /**
     * Tests comment message getter and setter
     */
    @Test
    public void testMessage() {
        Comment comment = mockEmptyComment();
        assertNull(comment.getCodeComment(), "Comment message should be null");
        comment.setCodeComment("This code art is fire");
        assertEquals("This code art is fire", comment.getCodeComment());
    }

    @Test
    public void testGetHashcode() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("hash111", comment.getHashcode());
    }

    @Test
    public void testSetHashcode() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals(comment.getHashcode(), "hash111");
        comment.setHashcode("hash123");
        assertEquals("hash123", comment.getHashcode());
    }

    @Test
    public void testGetUsername() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("JamesBond", comment.getUsername());
    }

    @Test
    public void testSetUsername() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("JamesBond", comment.getUsername());
        comment.setUsername("JasonBourne");
        assertEquals("JasonBourne", comment.getUsername());
    }

    @Test
    public void testGetCodeComment() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("This qr looks so cool", comment.getCodeComment());
    }

    @Test
    public void testSetCodeComment() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("This qr looks so cool", comment.getCodeComment());
        comment.setCodeComment("This new code looks fire");
        assertEquals("This new code looks fire", comment.getCodeComment());
    }

    @Test
    public void testGetUnixMillis_DateTime() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("1679865411702", comment.getUnixMillis_DateTime());
    }

    @Test
    public void testSetUnixMillis_DateTime() {
        Comment comment = new Comment("hash111", "JamesBond", "1679865411702", "This qr looks so cool");
        assertEquals("1679865411702", comment.getUnixMillis_DateTime());
        comment.setUnixMillis_DateTime("1679865411705");
        assertEquals("1679865411705", comment.getUnixMillis_DateTime());
    }
}
