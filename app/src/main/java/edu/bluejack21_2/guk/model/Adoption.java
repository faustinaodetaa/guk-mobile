package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class Adoption extends History{
    public static final String COLLECTION_NAME = "adoptions";
    private DocumentReference dog;
    public Adoption(){

    }
    public Adoption(DocumentReference user, DocumentReference dog) {
        super(user);
        this.dog = dog;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> adoption = new HashMap<>();
        adoption.put("dog", dog);
        adoption.put("user", user);
        adoption.put("status", status);

        return adoption;
    }

    public DocumentReference getDog() {
        return dog;
    }

    public void setDog(DocumentReference dog) {
        this.dog = dog;
    }
}
