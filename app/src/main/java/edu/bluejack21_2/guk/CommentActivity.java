package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.CommentAdapter;
import edu.bluejack21_2.guk.adapter.StoryAdapter;
import edu.bluejack21_2.guk.controller.StoryController;
import edu.bluejack21_2.guk.model.Comment;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Story;

public class CommentActivity extends AppCompatActivity {

    private TextView storyContentTxt, postComment;
    private EditText commentContentTxt;
    private ImageView backIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        storyContentTxt = findViewById(R.id.comment_story_content);
        postComment = findViewById(R.id.comment_btn);
        commentContentTxt = findViewById(R.id.comment_content);
        backIcon = findViewById(R.id.story_back_icon);

        backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DogDetailActivity.this, HomeActivity.class));
                finish();
            }
        });

        final Story story = (Story)getIntent().getParcelableExtra("story");
        Log.d("coba intent story", "a: " + story.getContent());
        Log.d("coba intent story", "a: " + story.getId());

        postComment.setOnClickListener(view -> {
            StoryController.insertComment(this, commentContentTxt.getText().toString(), story.getId());
        });

        RecyclerView recyclerView;
        CommentAdapter commentAdapter;
        ArrayList<Comment> commentList;

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList, story);
        recyclerView.setAdapter(commentAdapter);

        StoryController.showAllCommentsByStory(commentAdapter, commentList, story.getId());
        storyContentTxt.setText(story.getContent());



    }
}