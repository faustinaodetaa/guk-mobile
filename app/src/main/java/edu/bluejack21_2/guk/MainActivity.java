package edu.bluejack21_2.guk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import edu.bluejack21_2.guk.model.User;
import util.Crypt;

public class MainActivity extends AppCompatActivity {

    private TextView registerLink;
    private EditText emailTxt, passwordTxt;
    private Button loginBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.email_txt);
        passwordTxt = findViewById(R.id.password_txt);
        loginBtn = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.register_link);

        registerLink.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        loginBtn.setOnClickListener(view -> {
            String email = emailTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            db.collection("users").whereEqualTo("email", email).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("cobacoba", document.getId() + " => " + document.getData());
                                User u = document.toObject(User.class);
                                Log.d("cobacoba-pass", u.getPassword());
                                boolean isValid = Crypt.check(password, u.getPassword());
                                if(isValid){
                                    Log.d("cobacoba-pass", "password bener");
                                } else {
                                    Log.d("cobacoba-pass", "salah");

                                }
                            }
                        } else {
                            Log.w("cobacoba", "Error getting documents.", task.getException());
                        }
                    }
                });
        });

//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("cobacoba", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w("cobacoba", "Error getting documents.", task.getException());
//                        }
//                    }
//                });

//        Map<String, Object> user = new HashMap<>();
//        user.put("name", "Dummy");
//        user.put("address", "Dummy Street");
//        user.put("email", "dummy@mail.com");
//        user.put("password", Crypt.hash("dummy123"));
//        user.put("phone", "1234");
//        user.put("point", "0");
//        user.put("profile_picture", "path");
//
//
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("cobacoba", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("cobacoba", "Error adding document", e);
//                    }
//                });
    }
}