package edu.bluejack21_2.guk.controller;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Breed;
import edu.bluejack21_2.guk.model.Dog;
import util.Database;

public class BreedController {
    public static void showAllBreeds(ArrayList<Breed> breedList) {
        Database.getDB().collection(Breed.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Breed breed = document.toObject(Breed.class);
                    breed.setId(document.getId());

                    breedList.add(breed);
                    for(int i =0; i < breedList.size(); i++){
                        Log.d("breedlists", breedList.get(i).getName());
                        Log.d("breedlists", breedList.get(i).getId());
                    }

                }
            }
        });
    }
}
