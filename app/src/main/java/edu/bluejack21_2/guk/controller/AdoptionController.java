package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import edu.bluejack21_2.guk.HomeActivity;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.ActivityHelper;
import edu.bluejack21_2.guk.util.Database;

public class AdoptionController {
    public static boolean insertAdoption(Context ctx, Dog dog){
        DogController.changeDogStatus(ctx, dog, "Pending");
        DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());
        DocumentReference dogRef = Database.getDB().collection(Dog.COLLECTION_NAME).document(dog.getId());
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
}
