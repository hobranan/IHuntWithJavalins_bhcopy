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

import java.util.ArrayList;

/**
 * Adapter (customized) for linking/showing the backend-datalist (of objects) with the UI-content-list (in content.xml)
 */
public class CommentListForCommentAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentListForCommentAdapter(Context context, ArrayList<Comment> comments){
        super(context,0, comments);
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.comment_content, parent,false);
        }
        Comment tempComment = comments.get(position);
        TextView cmtUsername = view.findViewById(R.id.cmt_username);
        TextView cmtDateTime = view.findViewById(R.id.cmt_datetime);
        TextView cmtComment = view.findViewById(R.id.cmt_comment);
        cmtUsername.setText(tempComment.getUsername());
        cmtDateTime.setText(tempComment.getUnixMillis_DateTime());
        cmtComment.setText(tempComment.getCodeComment());
        return view;
    }
}