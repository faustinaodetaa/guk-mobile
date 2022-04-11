package edu.bluejack21_2.guk.model;

public class Breed {
    public static final String COLLECTION_NAME = "breeds";

    private String id, name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Breed(String name) {
        this.name = name;
    }

    public Breed(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
