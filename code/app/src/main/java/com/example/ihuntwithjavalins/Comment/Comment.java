package com.example.ihuntwithjavalins.Comment;

import android.os.Build;

import com.example.ihuntwithjavalins.Player.Player;
import com.google.firebase.firestore.auth.User;

import java.time.LocalTime;
import java.util.Date;

/**
 * This class represents a comment object containing the details of a comment made by a user on a code.
 * TODO: implement this with the app (no commenting yet)
 */
public class Comment {
    private String hashcode;

    /** The user who made the comment. */
    private String username;

    /** The text content of the comment. */
    private String codeComment;

    /** The date when the comment was made. */
    private String unixMillis_DateTime;

    /** The time of day when the comment was made */

//    /**
//     * Creates a new comment object with the specified user and text content.
//     * @param username The user who made the comment.
//     * @param codeComment The text content of the comment.
//     */
//    public Comment(String username, String codeComment, String hashcode) {
//        this.username = username;
//        this.codeComment = codeComment;
//        Date dateOfComment = new Date();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            LocalTime timeOfComment = LocalTime.now();
//        }
//        this.dateOfComment = dateOfComment.toString();
//        this.timeOfComment = timeOfComment.toString();
//        this.hashcode = hashcode;
//    }

    public Comment() {
    }

    public Comment(String hashcode, String username,  String unixMillis_DateTime, String codeComment ) {
        this.hashcode = hashcode;
        this.username = username;
        this.unixMillis_DateTime = unixMillis_DateTime;
        this.codeComment = codeComment;
    }
    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodeComment() {
        return codeComment;
    }

    public void setCodeComment(String codeComment) {
        this.codeComment = codeComment;
    }

    public String getUnixMillis_DateTime() {
        return unixMillis_DateTime;
    }

    public void setUnixMillis_DateTime(String unixMillis_DateTime) {
        this.unixMillis_DateTime = unixMillis_DateTime;
    }
}
