package edu.bluejack21_2.guk.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class Notification {
    public static final String COLLECTION_NAME = "notifications";

    private String id, type;
    private int status = 0;
    private Timestamp createdAt;
    private DocumentReference user;

    public Notification(){

    }

    public Notification(int status, String type, Timestamp createdAt, DocumentReference user) {
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.user = user;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("type", type);
        map.put("user", user);
        map.put("createdAt", createdAt);

        return map;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }
}
