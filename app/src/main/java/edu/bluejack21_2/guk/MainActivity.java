package edu.bluejack21_2.guk;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.listener.FinishListener;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class MainActivity extends AppCompatActivity implements FinishListener<User> {
    private TextView registerLink;
    private EditText emailTxt, passwordTxt;
    private Button loginBtn, googleLoginBtn;

    private GoogleSignInClient googleSignInClient;

    private LinearLayout roundedBg;

    private String client_webId = "798408453919-lcjvkil6547t34l2fk0av3d777mf98bd.apps.googleusercontent.com";

    private ImageView titleLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        checkIsLoggedIn();

        setContentView(R.layout.activity_main);

        googleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope(Scopes.DRIVE_APPFOLDER)).requestServerAuthCode(client_webId).requestIdToken(client_webId).build());
        emailTxt = findViewById(R.id.login_email_txt);
        passwordTxt = findViewById(R.id.login_password_txt);
        loginBtn = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.register_link);
        googleLoginBtn = findViewById(R.id.google_login_btn);

        roundedBg = findViewById(R.id.login_rounded_bg);
        titleLogo = findViewById(R.id.title_logo);

        registerLink.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, titleLogo, "title-logo").toBundle());
        });

        loginBtn.setOnClickListener(view -> {
            String email = emailTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            User.CURRENT_USER = UserController.auth(this, email, password);
        });


        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {
                            try {
                                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                Log.d("dapet", account.getServerAuthCode());
                                String email = account.getEmail();
                                String name = account.getDisplayName();
                                String picture = account.getPhotoUrl().toString();

                                UserController.getUserByEmail(email, (data, message) -> {
                                    if (data == null) {
                                        User user = new User(email, name, "", "", "", picture, 0);

                                        Database.getDB().collection(User.COLLECTION_NAME)
                                                .add(user.toMap())
                                                .addOnSuccessListener(documentReference -> {
                                                    user.setId(documentReference.getId());
                                                    showHomePageAndSaveData(user);
                                                })
                                                .addOnFailureListener(e -> {
                                                });
                                    } else {
                                        showHomePageAndSaveData(data);
                                    }
                                });
                            } catch (ApiException e) {
                                Log.d("error google", "onActivityResult: " + e.getStatusCode());
                                e.printStackTrace();
                            }
                        }
                    }
                });

        googleLoginBtn.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
//            startActivityForResult(intent, 100);
            resultLauncher.launch(intent);
        });
    }

    @Override
    public void onFinish(User data, String message) {
        if (data == null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            showHomePageAndSaveData(data);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "GukChannel";
            String description = "";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("General", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showHomePageAndSaveData(User data) {
        User.CURRENT_USER = data;

       Notification notification = new NotificationCompat.Builder(this, "General")
                .setSmallIcon(R.drawable.ic_main_logo)
                .setContentTitle("Login Success")
                .setContentText("Hello, " + data.getName() + "!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager!=null)
            notificationManager.notify(1, notification);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
//        Log.d("user_id", "showHomePageAndSaveData: " + data.getId());
        editor.putString("user_id", data.getId());
        editor.commit();
        Intent i = new Intent(this, HomeActivity.class);
//        Intent i = new Intent(this, TabActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this, roundedBg, "rounded-bg").toBundle());
        finish();
    }

    private void checkIsLoggedIn() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (prefs.contains("user_id")) {
            UserController.getUserById(prefs.getString("user_id", null), (data, message) -> {
                User.CURRENT_USER = data;
                if (data != null) {
//                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                    startActivity(i);
//                    finish();
                    showHomePageAndSaveData(data);
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                }
            });
        }

    }

}