package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import edu.bluejack21_2.guk.HomeActivity;
import edu.bluejack21_2.guk.MainActivity;
import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.listener.FinishListener;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.ActivityHelper;
import edu.bluejack21_2.guk.util.Database;

public class DogController {
    public static void showAllDogs(DogAdapter dogAdapter, ArrayList<Dog> dogList) {
        Database.getDB().collection(Dog.COLLECTION_NAME).whereEqualTo("status", "Unadopted").orderBy("rescuedDate", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Dog dog = document.toObject(Dog.class);
                    dog.setId(document.getId());

                    dogList.add(dog);
//                    document.getDocumentReference("breed").get().addOnCompleteListener(t -> {
//                        if (t.isSuccessful()) {
//                            Breed b = t.getResult().toObject(Breed.class);
//                            dog.setBreedByClass(b);
//
//                        }
//                    });
                    dogAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static boolean insertDog(Context ctx, String name, String breed, String description, Timestamp dob, Timestamp rescuedDate, String gender, String status, Uri filePath){
        String errorMsg = "";
        if(name.isEmpty()){
            errorMsg = "Name must be filled!";
        }else if(description.isEmpty()){
            errorMsg = "Description must be filled!";
        }else if(gender.isEmpty()){
            errorMsg = "Gender must be filled!";
        }

        if(!errorMsg.isEmpty()){
//            Toast.makeText(ctx, errorMsg, Toast.LENGTH_SHORT).show();
            Log.d("msg", errorMsg);
            return false;
        }

        String extension = filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
        String fileName = "images/dog/" + UUID.randomUUID().toString() + "." + extension;


        Database.uploadImage(filePath, fileName, ctx, (data, message) -> {
            Dog dog = new Dog(name, breed, description, dob, gender, rescuedDate, status, data);
            Database.getDB().collection(Dog.COLLECTION_NAME).add(dog.toMap()).addOnSuccessListener(documentReference -> {
                ActivityHelper.refreshActivity((Activity) ctx);
                Toast.makeText(ctx, "Dog added successfully!", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> {
                Log.d("msg", "error insert");
            });

        });
        return true;
    }



    public static void deleteDog(String id){
        HashMap<String, Object> data = new HashMap<>();
        Database.getDB().collection(Dog.COLLECTION_NAME).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Deleted", "Success delete dog");
                }
            }
        });
    }

    public static void updateDog(Context ctx, String id, String breed, String description, Timestamp dob, String gender, String name){
        HashMap<String, Object> data =  new HashMap<String, Object>();
        data.put("breed", breed);
        data.put("description", description);
        data.put("dob", dob);
        data.put("gender", gender);
        data.put("name", name);
        Database.getDB().collection(Dog.COLLECTION_NAME).document(id).set(data, SetOptions.merge()).addOnSuccessListener(u -> {
            ((Activity)ctx).finish();
            ((Activity)ctx).startActivity(new Intent(((Activity)ctx), HomeActivity.class));
            Toast.makeText(ctx, "Dog updated successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    public static void changeDogStatus(Context ctx, Dog dog, String status){
        HashMap<String, Object> data =  new HashMap<String, Object>();
        data.put("status", status);
        Database.getDB().collection(Dog.COLLECTION_NAME).document(dog.getId()).set(data, SetOptions.merge()).addOnSuccessListener(u -> {

//                ActivityHelper.refreshActivity((Activity) ctx);
//                Toast.makeText(ctx, "Adoption!", Toast.LENGTH_LONG).show();

        });
    }

    public static void getDogById(String id, FinishListener<Dog> listener){
        Database.getDB().collection(Dog.COLLECTION_NAME).document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Dog d = document.toObject(Dog.class);
//                Log.d("coba", "getUserById: " + u.getIsDeleted());
//                if(!u.getIsDeleted()){
//                    User.CURRENT_USER = u;
                d.setId(id);
                if(listener != null)
                    listener.onFinish(d, null);
//                }
            }

        });
    }

}
