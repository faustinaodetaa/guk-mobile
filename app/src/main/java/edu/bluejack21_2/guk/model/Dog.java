package edu.bluejack21_2.guk.model;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class Dog {
    private String id, name, description, gender, status, picture;
    private Timestamp dob, rescuedDate;
    private Breed b;

    public Dog(String name, Breed breed, String description, Timestamp dob, String gender, Timestamp rescuedDate, String status, String picture) {
        this.name = name;
        this.b = breed;
        this.description = description;
        this.dob = dob;
        this.gender = gender;
        this.rescuedDate = rescuedDate;
        this.status = status;
        this.picture = picture;
    }

    public Dog(){

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

    public Breed getBreed() {
        return b;
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

    public void setBreedByClass(Breed breed) {
        this.b = breed;
    }

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
