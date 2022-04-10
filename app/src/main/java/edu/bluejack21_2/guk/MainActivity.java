package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.listener.FinishListener;
import edu.bluejack21_2.guk.model.User;

public class MainActivity extends AppCompatActivity implements FinishListener<User> {

    private TextView registerLink;
    private EditText emailTxt, passwordTxt;
    private Button loginBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.login_email_txt);
        passwordTxt = findViewById(R.id.login_password_txt);
        loginBtn = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.register_link);

        registerLink.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        loginBtn.setOnClickListener(view -> {
            String email = emailTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            User.CURRENT_USER = UserController.auth(this, email, password);
//            if(User.CURRENT_USER != null){
//                Intent i = new Intent(this, HomeActivity.class);
//                startActivity(i);
//            } else {
//                Toast.makeText(this, "Login fail!", Toast.LENGTH_SHORT).show();
//            }
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

    }

    @Override
    public void onFinish(User data, String message) {
        if(data == null){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_id", data.getId());
            editor.commit();
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}