package com.example.ihuntwithjavalins.Comment;


/**
 * This class represents a comment object containing the details of a comment made by a user on a code.
 * Design Patterns: none
 *
 * @version 1.0
 */
public class Comment {
    private String hashcode;

    /**
     * The user who made the comment.
     */
    private String username;

    /**
     * The text content of the comment.
     */
    private String codeComment;

    /**
     * The date when the comment was made.
     */
    private String unixMillis_DateTime;


    /**
     * Empty constructor for Comment class
     */
    public Comment() {
    }

    /**
     * Constructor to initialize Comment class
     *
     * @param hashcode            the Comment hashcode
     * @param username            the commenters username
     * @param unixMillis_DateTime the datetime for posting the comment
     * @param codeComment         the actual comment message
     */
    public Comment(String hashcode, String username, String unixMillis_DateTime, String codeComment) {
        this.hashcode = hashcode;
        this.username = username;
        this.unixMillis_DateTime = unixMillis_DateTime;
        this.codeComment = codeComment;
    }

    /**
     * Gets the comment hashcode
     *
     * @return the comment hashcode
     */
    public String getHashcode() {
        return hashcode;
    }

    /**
     * Sets the comment hashcode
     *
     * @param hashcode the hashcode to set the comment to
     */
    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    /**
     * Gets the comment username
     *
     * @return the comment username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the comment username
     *
     * @param username the username to set the comment to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the code comment message
     *
     * @return the code comment message
     */
    public String getCodeComment() {
        return codeComment;
    }

    /**
     * Sets the code comment message
     *
     * @param codeComment the message to give the comment object
     */
    public void setCodeComment(String codeComment) {
        this.codeComment = codeComment;
    }

    /**
     * Gets the date time of comment posting
     *
     * @return the date time of the comment
     */
    public String getUnixMillis_DateTime() {
        return unixMillis_DateTime;
    }

    /**
     * Sets the date time of the comment posting
     *
     * @param unixMillis_DateTime the date time to set the comment post to
     */
    public void setUnixMillis_DateTime(String unixMillis_DateTime) {
        this.unixMillis_DateTime = unixMillis_DateTime;
    }
}
