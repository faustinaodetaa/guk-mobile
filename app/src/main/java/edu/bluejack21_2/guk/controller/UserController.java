package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.UUID;

import edu.bluejack21_2.guk.MainActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.listener.FinishListener;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Crypt;
import edu.bluejack21_2.guk.util.Database;

public class UserController {

//    public static final String COLLECTION_NAME = "dogs";

    public static User auth(FinishListener<User> listener, String email, String password){
        String errorMsg = "";

        if(email.isEmpty()){
            errorMsg = "Email must be filled!";
        } else if(password.isEmpty()){
            errorMsg = "Password must be filled!";
        }

        if(!errorMsg.isEmpty()){
//            Toast.makeText(ctx,errorMsg, Toast.LENGTH_SHORT).show();
            listener.onFinish(null, errorMsg);

            return null;
        }

        Database.getDB().collection(User.COLLECTION_NAME).whereEqualTo("email", email).whereEqualTo("isDeleted", false).limit(1).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User u = document.toObject(User.class);
                            boolean isValid = !u.getIsDeleted() && Crypt.check(password, u.getPassword());
                            if(isValid){
                                u.setId(document.getId());
                                User.CURRENT_USER = u;
                                if(listener != null)
                                    listener.onFinish(u, "Login Success");
//                                Toast.makeText(ctx, "Login Success!", Toast.LENGTH_SHORT).show();
                            } else {
                                if(listener != null)
                                    listener.onFinish(null, "Incorrect Credential!");
                            }
                        }
                    } else {
                        if(listener != null)
                            listener.onFinish(null, "Incorrect Credential!");
                    }
                });
        return User.CURRENT_USER;
    }

    public static void getUserByEmail(String email, FinishListener<User> listener){
        Database.getDB().collection(User.COLLECTION_NAME).whereEqualTo("email", email).whereEqualTo("isDeleted", false).limit(1).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User u = document.toObject(User.class);
                            u.setId(document.getId());
                            if(listener != null)
                                listener.onFinish(u, null);
                        }
                    } else {
                        if(listener != null)
                            listener.onFinish(null, null);
                    }
                });
    }

    public static boolean insertUser(AppCompatActivity ctx, String name, String email, String password, String confirmPassword, String phone, String address, Uri filePath){

        String errorMsg = "";
        if(name.isEmpty()){
            errorMsg = "Name must be filled!";
        } else if(email.isEmpty()){
            errorMsg = "Email must be filled!";
        } else if(password.isEmpty()){
            errorMsg = "Password must be filled!";
        } else if(!password.equals(confirmPassword)){
            errorMsg = "Password and Confirm Password must be the same!";
        } else if(phone.isEmpty()){
            errorMsg = "Phone number must be filled!";
        } else if(address.isEmpty()){
            errorMsg = "Address must be filled!";
        } else if(filePath == null){
            errorMsg = "Profile picture must be chosen!";
        }

        if(!errorMsg.isEmpty()){
            Toast.makeText(ctx,errorMsg, Toast.LENGTH_SHORT).show();
            return false;
        }

        String extension = filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
        String fileName = "images/user/" + UUID.randomUUID().toString() + "." + extension;


        Database.uploadImage(filePath, fileName, ctx, (data, message) -> {
            User user = new User(email, name, password, address, phone, data, 0);

            Database.getDB().collection(User.COLLECTION_NAME)
                    .add(user.toMap())
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ctx,"Register success!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ctx,"Register fail!", Toast.LENGTH_SHORT).show();
                    });

        });

        return true;
    }

    public static void getUserById(String id, FinishListener<User> listener){
        Database.getDB().collection(User.COLLECTION_NAME).document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                User u = document.toObject(User.class);
                Log.d("coba", "getUserById: " + u.getIsDeleted());
//                if(!u.getIsDeleted()){
//                    User.CURRENT_USER = u;
                    u.setId(id);
                    if(listener != null)
                        listener.onFinish(u, null);
//                }
            }

        });
    }

    public static void logout(Context ctx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(ctx, MainActivity.class);
        ctx.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(((Activity)ctx), ((Activity)ctx).findViewById(R.id.bottomNavigationView), "rounded-bg").toBundle());
        ((Activity)ctx).finish();
    }

    public static void deleteAccount(String id){
        HashMap<String, Object> data =  new HashMap<String, Object>();
        data.put("isDeleted", true);
        Database.getDB().collection(User.COLLECTION_NAME).document(id).set(data, SetOptions.merge());
    }

}
