package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class Adoption {
    public static final String COLLECTION_NAME = "adoptions";

    private String id;
    private int status = 0; // 0 -> pending, 1 -> approved, 2 -> rejected
    private DocumentReference user, dog;

    public Adoption(){

    }

    public Adoption(DocumentReference user, DocumentReference dog) {
        this.status = status;
        this.user = user;
        this.dog = dog;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> adoption = new HashMap<>();
        adoption.put("dog", dog);
        adoption.put("user", user);
        adoption.put("status", status);

        return adoption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public DocumentReference getDog() {
        return dog;
    }

    public void setDog(DocumentReference dog) {
        this.dog = dog;
    }
}
