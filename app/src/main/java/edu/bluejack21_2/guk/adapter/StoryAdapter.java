package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.bluejack21_2.guk.DogDetailActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Story;
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

//        holder.username.setText(story.get);
        holder.content.setText(story.getContent());

        Database.showImage(story.getPicture(), ((Activity)context), holder.picture );



   }

    @Override
    public int getItemCount() {
        return storyList.size();
    }


    class StoriesViewholder extends RecyclerView.ViewHolder {

        TextView username, content;
        ImageView picture;
        CardView storyCard;

        public StoriesViewholder(@NonNull View itemView)
        {
            super(itemView);

            username = itemView.findViewById(R.id.story_username);
            content = itemView.findViewById(R.id.story_content);

            picture = itemView.findViewById(R.id.story_dog_image);

            storyCard = itemView.findViewById(R.id.story_card);

        }
    }
}
