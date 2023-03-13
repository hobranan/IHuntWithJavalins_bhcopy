package com.example.ihuntwithjavalins.Comment;

import android.os.Build;

import com.google.firebase.firestore.auth.User;

import java.time.LocalTime;
import java.util.Date;

/**
 * This class represents a comment object containing the details of a comment made by a user on a code.
 */
public class Comment {

    /** The user who made the comment. */
    private User username;

    /** The text content of the comment. */
    private String codeComment;

    /** The date when the comment was made. */
    private Date dateOfComment;

    /** The time of day when the comment was made */
    private LocalTime timeOfComment;

    /**
     * Creates a new comment object with the specified user and text content.
     * @param username The user who made the comment.
     * @param codeComment The text content of the comment.
     */

    public Comment(User username, String codeComment) {
        this.codeComment = codeComment;
        dateOfComment = new Date();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeOfComment = LocalTime.now();
        }
    }
}
