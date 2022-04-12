package edu.bluejack21_2.guk.model;

public class Story {

    public static final String COLLECTION_NAME = "stories";

    private String id, content, picture;

    public Story(){

    }

    public Story(String id, String content, String picture) {
        this.id = id;
        this.content = content;
        this.picture = picture;
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
