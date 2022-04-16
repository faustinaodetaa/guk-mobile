package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import edu.bluejack21_2.guk.CommentActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.StoryController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Comment;
import edu.bluejack21_2.guk.model.Story;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentsViewHolder>{

    Context context;

    private ArrayList<Comment> commentList;
    private Story story;

    public CommentAdapter(Context context, ArrayList<Comment> commentList, Story story){
        this.context = context;
        this.commentList = commentList;
        this.story = story;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_template,parent,false);

        return new CommentAdapter.CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentsViewHolder holder, int position) {

        Comment comment = commentList.get(position);


        holder.contentTxt.setText(" " + comment.getContent());

        UserController.getUserById(comment.getUser().getId(), (data, message) -> {
            if(data != null){
                holder.userNameTxt.setText(data.getName());
            }
        });


        holder.deleteBtn.setOnClickListener(view -> {
            Log.d("delete id", story.getId());
            StoryController.deleteComment(context, comment.getContent(), story.getId());
        });


    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    class CommentsViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTxt, contentTxt;

        CardView commentCard;

        ImageView deleteBtn;

        public CommentsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            userNameTxt = itemView.findViewById(R.id.comment_user_name_txt);
            contentTxt = itemView.findViewById(R.id.comment_user_content_txt);

            commentCard = itemView.findViewById(R.id.comment_card);

            deleteBtn = itemView.findViewById(R.id.comment_delete);


        }
    }
}
