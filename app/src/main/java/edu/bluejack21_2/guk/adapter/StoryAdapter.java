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
import edu.bluejack21_2.guk.DogDetailActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.StoryController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Story;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoriesViewholder> {

    Context context;

    private ArrayList<Story> storyList;

    public StoryAdapter(Context context, ArrayList<Story> storyList){
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public StoryAdapter.StoriesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.story_template,parent,false);

        return new StoryAdapter.StoriesViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.StoriesViewholder holder, int position) {

        Story story = storyList.get(position);

        holder.contentTxt.setText(story.getContent());

        UserController.getUserById(story.getUser().getId(), (data, message) -> {
            if(data != null){
                holder.userNameTxt.setText(data.getName());
            }
        });

        if(story.getComments() != null){
            AtomicInteger i = new AtomicInteger(0);
            for(i.get(); i.get() < story.getComments().size() && i.get() < 2; i.incrementAndGet()){
                Map<String, Object> c = story.getComments().get(i.get());
                TextView temp = (i.get() == 0) ? holder.topCommentTxt1 : holder.topCommentTxt2;
                UserController.getUserById(((DocumentReference) c.get("user")).getId(), (data, message) -> {
                    if(data != null){
                        String txt = "<b>" + data.getName() + "</b> " + c.get("content");

                        temp.setText(Html.fromHtml(txt));
                    }
                });
            }
        }

        if(story.getLikes() != null){
            for(DocumentReference docRef : story.getLikes()){
                if(docRef.getId().equals(User.CURRENT_USER.getId())){
                    holder.likeIcon.setImageResource(R.drawable.like_fill);
                    break;
                }
            }
        }

        holder.likeIcon.setOnClickListener(view -> {
            StoryController.toggleLike(holder.likeIcon, story.getId());
        });

        holder.commentIcon.setOnClickListener(view -> {
            openCommentActivity(holder, story);
        });

        holder.viewAllCommentsTxt.setOnClickListener(view -> {
            openCommentActivity(holder, story);
        });

        Database.showImage(story.getPicture(), ((Activity)context), holder.picture );



   }

    private void openCommentActivity(@NonNull StoriesViewholder holder, Story story) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("story", story);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder.contentTxt, "story-content").toBundle());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }


    class StoriesViewholder extends RecyclerView.ViewHolder {

        TextView userNameTxt, contentTxt, topCommentTxt1, topCommentTxt2, viewAllCommentsTxt;
        ImageView picture, commentIcon, likeIcon;
        CardView storyCard;

        public StoriesViewholder(@NonNull View itemView)
        {
            super(itemView);

            viewAllCommentsTxt = itemView.findViewById(R.id.story_view_comment_txt);

            userNameTxt = itemView.findViewById(R.id.story_user_name);
            contentTxt = itemView.findViewById(R.id.story_content);

            picture = itemView.findViewById(R.id.story_dog_image);

            storyCard = itemView.findViewById(R.id.story_card);

            commentIcon = itemView.findViewById(R.id.story_comment_icon);
            likeIcon = itemView.findViewById(R.id.story_like_icon);

            topCommentTxt1 = itemView.findViewById(R.id.story_top_comment_1);
            topCommentTxt2 = itemView.findViewById(R.id.story_top_comment_2);
//            topCommentTxt.add(itemView.findViewById(R.id.story_top_comment_1));
//            topCommentTxt.add(itemView.findViewById(R.id.story_top_comment_2));

        }
    }
}
