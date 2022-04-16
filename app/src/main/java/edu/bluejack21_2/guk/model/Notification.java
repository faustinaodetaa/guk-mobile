package edu.bluejack21_2.guk.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class Notification {
    public static final String COLLECTION_NAME = "notifications";

    private String id, content, type;
    private Timestamp createdAt;
    private DocumentReference user;

    public Notification(){

    }

    public Notification(String content, String type, Timestamp createdAt, DocumentReference user) {
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.user = user;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", content);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
