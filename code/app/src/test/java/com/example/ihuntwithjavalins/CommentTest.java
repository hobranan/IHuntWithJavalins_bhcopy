package com.example.ihuntwithjavalins;

import com.example.ihuntwithjavalins.Comment.Comment;

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

        assertEquals("51602", comment.getHashcode(), "Hash code should be 51602");
        assertEquals("Sabel Storm", comment.getUsername(), "Username should be Sabel Storm");
        assertEquals("1679865411702", comment.getUnixMillis_DateTime(), "Comment unix milliseconds should be 1679865411702");
        assertEquals("This code art is fire", comment.getCodeComment(), "Comment message should be 'This code art is fire'");
    }

    /**
     * Tests comment username getter and setter
     */
    @Test
    public void testUsername() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getUsername(), "Username should be null");
        comment.setUsername("Sabel Storm");
        assertEquals("Sabel Storm", comment.getUsername(), "Username should be Sabel Storm");
    }

    /**
     * Tests comment hash code getter and setter
     */
    @Test
    public void testHashCode() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getHashcode(), "Comment hash should be null");
        comment.setHashcode("51602");
        assertEquals("51602", comment.getHashcode(), "Comment hash should be 51602");
    }

    /**
     * Tests comment unix millisecond date time getter and setter
     */
    @Test
    public void testDateTime() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getUnixMillis_DateTime(), "Comment datetime should be null");
        comment.setUnixMillis_DateTime("1679865411702");
        assertEquals("1679865411702", comment.getUnixMillis_DateTime(), "Comment datetime should be 1679865411702");
    }

    /**
     * Tests comment message getter and setter
     */
    @Test
    public void testMessage() {
        Comment comment = mockEmptyComment();

        assertNull(comment.getCodeComment(), "Comment message should be null");
        comment.setCodeComment("This code art is fire");
        assertEquals("This code art is fire", comment.getCodeComment(), "Comment message should be 'This code art is fire'");
    }
}
