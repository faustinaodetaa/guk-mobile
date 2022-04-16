package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.HomeActivity;
import edu.bluejack21_2.guk.adapter.NotificationAdapter;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Notification;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class NotificationController {
    public static void insertNotification(String content, String type, DocumentReference user){
        Notification notification = new Notification(content, type, Timestamp.now(), user);
        Database.getDB().collection(Notification.COLLECTION_NAME).add(notification.toMap()).addOnSuccessListener(documentReference -> {

        });
    }

    public static void showAllNotifications(NotificationAdapter adapter, ArrayList<Notification> notifications) {
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());

        Database.getDB().collection(Notification.COLLECTION_NAME)
                .whereEqualTo("user", userRef)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Notification notification = document.toObject(Notification.class);
                    notification.setId(document.getId());
                    notifications.add(notification);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
