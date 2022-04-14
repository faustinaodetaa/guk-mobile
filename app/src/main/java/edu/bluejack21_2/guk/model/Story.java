package edu.bluejack21_2.guk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Story implements Parcelable {

    public static final String COLLECTION_NAME = "stories";

    private String id, content, picture;
    private DocumentReference user;
    private List<Map<String, Object>> comments;
    private List<DocumentReference> likes;
    private Timestamp createdAt;
    public Story(){

    }

    public Story(DocumentReference user, String content, String picture, Timestamp createdAt) {
        this.user = user;
        this.content = content;
        this.picture = picture;
        this.createdAt = createdAt;
    }

    protected Story(Parcel in){
        this.createdAt = in.readTypedObject(Timestamp.CREATOR);
        this.content = in.readString();
        this.picture = in.readString();
        this.id = in.readString();
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> story = new HashMap<>();
        story.put("content", content);
        story.put("picture", picture);
        story.put("user", user);
        story.put("createdAt", createdAt);
        return story;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.createdAt, i);
        parcel.writeString(this.content);
        parcel.writeString(this.picture);
        parcel.writeString(this.id);
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
