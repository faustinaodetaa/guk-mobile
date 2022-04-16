package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

import edu.bluejack21_2.guk.HomeActivity;
import edu.bluejack21_2.guk.adapter.AdoptionAdapter;
import edu.bluejack21_2.guk.adapter.DonationAdapter;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.ActivityHelper;
import edu.bluejack21_2.guk.util.Database;

public class AdoptionController {
    public static boolean insertAdoption(Context ctx, Dog dog){
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());
        DocumentReference dogRef = Database.getDB().collection(Dog.COLLECTION_NAME).document(dog.getId());
        DogController.changeDogStatus(ctx, dogRef, "Pending");
        Adoption adoption = new Adoption(userRef, dogRef);
        Database.getDB().collection(Adoption.COLLECTION_NAME).add(adoption.toMap()).addOnSuccessListener(documentReference -> {
            Toast.makeText(ctx, "Thank you for your Adoption! Please wait for our admin to check and review your adoption.", Toast.LENGTH_LONG).show();
            ((Activity) ctx).finish();
//            ActivityHelper.refreshActivity((Activity) ctx);
            ctx.startActivity(new Intent(ctx, HomeActivity.class));

        }).addOnFailureListener(e -> {
            Toast.makeText(ctx, "Error!", Toast.LENGTH_SHORT).show();
        });
        return true;
    }

    public static void changeAdoptionStatus(Context ctx, Adoption adoption, boolean isApproved){
        HashMap<String, Object> data =  new HashMap<String, Object>();
        data.put("status", isApproved ? 1 : 2);
        Database.getDB().collection(Adoption.COLLECTION_NAME).document(adoption.getId()).set(data, SetOptions.merge()).addOnSuccessListener(u -> {
            String notif = "";

            if(isApproved){
                notif = "Your adoption has been approved!";

                DogController.changeDogStatus(ctx, adoption.getDog(), "Adopted");

//                data.clear();
                int point = 100;
//                data.put("point", FieldValue.increment(point));
//                adoption.getUser().update(data).addOnSuccessListener(unused -> {
//                });
                UserController.increasePoint(adoption.getUser(), point);
                ActivityHelper.refreshActivity((Activity) ctx);
                Toast.makeText(ctx, "Adoption Approved!", Toast.LENGTH_LONG).show();

            } else {
                notif = "Your adoption has been rejected!";

                DogController.changeDogStatus(ctx, adoption.getDog(), "Unadopted");

                ActivityHelper.refreshActivity((Activity) ctx);
                Toast.makeText(ctx, "Adoption Rejected!", Toast.LENGTH_LONG).show();
            }
            NotificationController.insertNotification(notif, "adoption", adoption.getUser());

        });
    }

    public static void showAllAdoptions(AdoptionAdapter adoptionAdapter, ArrayList<Adoption> adoptions){
        Database.getDB().collection(Adoption.COLLECTION_NAME).orderBy("status", Query.Direction.ASCENDING)
//                .whereEqualTo("status", "pending")
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Adoption adoption = document.toObject(Adoption.class);
                    adoption.setId(document.getId());
                    adoptions.add(adoption);
                    adoptionAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static void showAllAdoptionsByUser(AdoptionAdapter adoptionAdapter, ArrayList<Adoption> adoptions){
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());

        Database.getDB().collection(Adoption.COLLECTION_NAME)
                .whereEqualTo("user", userRef)
                .orderBy("status", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Adoption adoption = document.toObject(Adoption.class);
                    adoption.setId(document.getId());
                    adoptions.add(adoption);
                    adoptionAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
