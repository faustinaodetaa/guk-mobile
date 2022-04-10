package edu.bluejack21_2.guk.controller;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

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
                    document.getDocumentReference("breed").get().addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            Breed b = t.getResult().toObject(Breed.class);
                            dog.setBreedByClass(b);
                            dogAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
    }
}
