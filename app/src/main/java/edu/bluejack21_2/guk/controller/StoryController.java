package edu.bluejack21_2.guk.controller;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.StoryAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Story;
import edu.bluejack21_2.guk.util.Database;

public class StoryController {
    public static void showAllStories(StoryAdapter storyAdapter, ArrayList<Story> storyList){
        Database.getDB().collection(Story.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Story story = document.toObject(Story.class);
                    story.setId(document.getId());

                    storyList.add(story);
                    storyAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}
