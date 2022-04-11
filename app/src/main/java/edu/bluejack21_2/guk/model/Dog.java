package edu.bluejack21_2.guk.model;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;

import util.Crypt;

public class Dog implements Serializable {
    public static final String COLLECTION_NAME = "dogs";

    private String id, name, description, gender, status, picture, breed;
    private Timestamp dob, rescuedDate;
//    private Breed b;
    public Dog(){

    }

    public Dog(String name, String breed, String description, Timestamp dob, String gender, Timestamp rescuedDate, String status, String picture) {
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.dob = dob;
        this.gender = gender;
        this.rescuedDate = rescuedDate;
        this.status = status;
        this.picture = picture;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> dog = new HashMap<>();
        dog.put("name", name);
        dog.put("breed", breed);
        dog.put("description", description);
        dog.put("dob", dob);
        dog.put("rescuedDate", rescuedDate);
        dog.put("gender", gender);
        dog.put("status", status);
        dog.put("picture", picture);

        return dog;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

//    public void setBreed(DocumentReference ref) {
//        ref.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                Breed b = document.toObject(Breed.class);
//                this.breed = b;
//                Log.d("coba", "setBreed:  masuk " + this.breed.getName());
//            }
//        });
//    }

//    public void setBreedByClass(String breed) {
//        this.breed = breed;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getRescuedDate() {
        return rescuedDate;
    }

    public void setRescuedDate(Timestamp rescuedDate) {
        this.rescuedDate = rescuedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
