package edu.bluejack21_2.guk.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import edu.bluejack21_2.guk.RegisterActivity;
import edu.bluejack21_2.guk.model.User;
import util.Crypt;
import util.Database;

public class UserController {

    public static User auth(AppCompatActivity ctx, String email, String password){
        String errorMsg = "";

        if(email.isEmpty()){
            errorMsg = "Email must be filled!";
        } else if(password.isEmpty()){
            errorMsg = "Password must be filled!";
        }

        if(!errorMsg.isEmpty()){
            Toast.makeText(ctx,errorMsg, Toast.LENGTH_SHORT).show();
            return null;
        }

        Database.getDB().collection(User.COLLECTION_NAME).whereEqualTo("email", email).limit(1).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User u = document.toObject(User.class);
                            boolean isValid = Crypt.check(password, u.getPassword());
                            if(isValid){
                                User.CURRENT_USER = u;
                                Toast.makeText(ctx, "Login Success!", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                            }
                        }
                    }
                });
        return User.CURRENT_USER;
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

        String fileName = "images/user/" + UUID.randomUUID().toString();

        User user = new User(email, name, password, address, phone, fileName, 0);

        Database.getDB().collection(User.COLLECTION_NAME)
                .add(user.toMap())
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ctx,"Register success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ctx,"Register fail!", Toast.LENGTH_SHORT).show();
                });

        Database.uploadImage(filePath, fileName, ctx);

        return true;
    }


}
