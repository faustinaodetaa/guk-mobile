package edu.bluejack21_2.guk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.HashMap;

public class Dog implements Parcelable {

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

    protected Dog(Parcel in){
        this.dob = in.readTypedObject(Timestamp.CREATOR);
        this.rescuedDate = in.readTypedObject(Timestamp.CREATOR);
        this.name = in.readString();
        this.breed = in.readString();
        this.description = in.readString();
        this.gender = in.readString();
        this.status = in.readString();
        this.picture = in.readString();
        this.id = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.dob, i);
        parcel.writeTypedObject(this.rescuedDate, i);
        parcel.writeString(this.name);
        parcel.writeString(this.breed);
        parcel.writeString(this.description);
        parcel.writeString(this.gender);
        parcel.writeString(this.status);
        parcel.writeString(this.picture);
        parcel.writeString(this.id);
    }

    public static final Parcelable.Creator<Dog> CREATOR = new Parcelable.Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel source) {
            return new Dog(source);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };
}
