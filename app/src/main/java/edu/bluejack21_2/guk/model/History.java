package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public abstract class History {
    protected String id;
    protected int status = 0; // 0 -> pending, 1 -> approved, 2 -> rejected
    protected DocumentReference user;

    public History(){

    }

    public History(DocumentReference user) {
        this.user = user;
    }

    public abstract HashMap<String, Object> toMap();

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
}
