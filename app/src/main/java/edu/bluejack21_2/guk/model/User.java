package edu.bluejack21_2.guk.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

import util.Crypt;
import util.Database;

public class User {
    public static User CURRENT_USER = null;

    public static final String COLLECTION_NAME = "users";

    private String id, email, name, password, address, phone, profilePicture;
    private String role = "";
    private int point;

    public User() {

    }

    public User(String email, String name, String password, String address, String phone, String profilePicture, int point) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.point = point;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("address", address);
        user.put("email", email);
        user.put("password", Crypt.hash(password));
        user.put("phone", phone);
        user.put("point", point);
        user.put("profilePicture", profilePicture);

        return user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
