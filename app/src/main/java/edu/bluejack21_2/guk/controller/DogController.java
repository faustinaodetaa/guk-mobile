package edu.bluejack21_2.guk.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import edu.bluejack21_2.guk.AddDogFragment;
import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Breed;
import edu.bluejack21_2.guk.model.Dog;
import util.Database;

public class DogController {
    public static void showAllDogs(DogAdapter dogAdapter, ArrayList<Dog> dogList) {
        Database.getDB().collection(Dog.COLLECTION_NAME).get().addOnCompleteListener(task -> {
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


        Dog dog = new Dog(name, breed, description, dob, gender, rescuedDate, status, fileName);
        Database.getDB().collection(Dog.COLLECTION_NAME).add(dog.toMap()).addOnSuccessListener(documentReference -> {
            Log.d("msg", "success insert");
        }).addOnFailureListener(e -> {
            Log.d("msg", "error insert");
        });
        Database.uploadImage(filePath, fileName, ctx);
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
}
