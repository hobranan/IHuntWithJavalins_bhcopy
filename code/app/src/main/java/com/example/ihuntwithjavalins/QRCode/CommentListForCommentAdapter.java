package com.example.ihuntwithjavalins.QRCode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ihuntwithjavalins.Comment.Comment;
import com.example.ihuntwithjavalins.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter (customized) for linking/showing the backend-datalist (of objects) with the UI-content-list (in content.xml)
 * Design Patterns:
 * adapter pattern - it is an adapter
 */
public class CommentListForCommentAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentListForCommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.comment_content, parent, false);
        }
        Comment tempComment = comments.get(position);
        TextView cmtUsername = view.findViewById(R.id.cmt_username);
        TextView cmtDateTime = view.findViewById(R.id.cmt_datetime);
        TextView cmtComment = view.findViewById(R.id.cmt_comment);
        cmtUsername.setText(tempComment.getUsername());
        cmtDateTime.setText(convertUnixMillisToDateTime(tempComment.getUnixMillis_DateTime()));
        cmtComment.setText(tempComment.getCodeComment());
        return view;
    }


    public String convertUnixMillisToDateTime(String unixMillis) {
        // https://javarevisited.blogspot.com/2012/12/how-to-convert-millisecond-to-date-in-java-example.html#axzz7wzpr7WmN
        //current time in milliseconds
        long tempDateTime = Long.parseLong(unixMillis);
        //creating Date from millisecond
        Date tempDate = new Date(tempDateTime);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(tempDate);
    }

}