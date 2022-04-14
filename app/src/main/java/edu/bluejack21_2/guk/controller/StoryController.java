package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.adapter.StoryAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Story;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.ActivityHelper;
import edu.bluejack21_2.guk.util.Database;

public class StoryController {
    public static void showAllStories(StoryAdapter storyAdapter, ArrayList<Story> storyList){
        Database.getDB().collection(Story.COLLECTION_NAME).orderBy("createdAt", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
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

    public static void insertStory(Context ctx, String content, Uri filePath, Timestamp createdAt){
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());

        String extension = filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
        String fileName = "images/story/" + UUID.randomUUID().toString() + "." + extension;

        Database.uploadImage(filePath, fileName, ctx, (data, message) -> {
            Story story = new Story(userRef, content, data, createdAt);
            Database.getDB().collection(Story.COLLECTION_NAME).add(story.toMap()).addOnSuccessListener(documentReference -> {
                ActivityHelper.refreshActivity((Activity) ctx);
                Toast.makeText(ctx, "Story added successfully!", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> {
                Log.d("msg", "error insert");
            });

        });



    }
}
