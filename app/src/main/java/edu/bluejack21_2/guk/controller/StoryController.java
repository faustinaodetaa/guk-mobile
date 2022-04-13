package edu.bluejack21_2.guk.controller;

import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.adapter.StoryAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Story;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class StoryController {
    public static void showAllStories(StoryAdapter storyAdapter, ArrayList<Story> storyList){
        Database.getDB().collection(Story.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Story story = document.toObject(Story.class);
                    story.setId(document.getId());
//                    if(story.getComments().size() > 0)
//                        Log.d("coba", "showAllStories: " + story.getComments().get(0).get("content"));

                    storyList.add(story);
                    storyAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    public static void toggleLike(ImageView icon, String storyID) {
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());
        int drawable;

        HashMap<String, Object> map = new HashMap<>();
        if(icon.getDrawable().getConstantState() == icon.getResources().getDrawable(R.drawable.like).getConstantState()){
            drawable = R.drawable.like_fill;
            map.put("likes", FieldValue.arrayUnion(userRef));
        } else {
            drawable = R.drawable.like;
            map.put("likes", FieldValue.arrayRemove(userRef));
        }
        Database.getDB().collection(Story.COLLECTION_NAME).document(storyID).update(map).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                icon.setImageResource(drawable);
            }
        });
    }
}
