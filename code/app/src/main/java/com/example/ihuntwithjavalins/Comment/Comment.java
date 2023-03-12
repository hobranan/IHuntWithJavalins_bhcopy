package com.example.ihuntwithjavalins.Comment;

import android.os.Build;

import com.google.firebase.firestore.auth.User;

import java.time.LocalTime;
import java.util.Date;

public class Comment {
    private User username;
    private String codeComment;
    private Date dateOfComment;
    private LocalTime timeOfComment;

    public Comment(User username, String codeComment) {
        this.codeComment = codeComment;
        dateOfComment = new Date();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeOfComment = LocalTime.now();
        }
    }
}
