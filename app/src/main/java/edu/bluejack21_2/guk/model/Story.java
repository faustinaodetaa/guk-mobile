package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Story {

    public static final String COLLECTION_NAME = "stories";

    private String id, content, picture;
    private DocumentReference user;
    private List<Map<String, Object>> comments;
    private List<DocumentReference> likes;
    public Story(){

    }

    public Story(String id, String content, String picture) {
        this.id = id;
        this.content = content;
        this.picture = picture;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public List<Map<String, Object>> getComments() {
        return comments;
    }

    public void setComments(List<Map<String, Object>> comments) {
        this.comments = comments;
    }

    public List<DocumentReference> getLikes() {
        return likes;
    }

    public void setLikes(List<DocumentReference> likes) {
        this.likes = likes;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
