package edu.bluejack21_2.guk.model;

public class Dog {
    private String name, description, dob, gender, rescuedDate, status, picture;
    private Breed breed;

    public Dog(String name, Breed breed, String description, String dob, String gender, String rescuedDate, String status, String picture) {
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.dob = dob;
        this.gender = gender;
        this.rescuedDate = rescuedDate;
        this.status = status;
        this.picture = picture;
    }

    public Dog(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRescuedDate() {
        return rescuedDate;
    }

    public void setRescuedDate(String rescuedDate) {
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
}
