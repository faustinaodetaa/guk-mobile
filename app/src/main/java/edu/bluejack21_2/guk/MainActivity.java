package edu.bluejack21_2.guk;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.listener.FinishListener;
import edu.bluejack21_2.guk.model.User;

public class MainActivity extends AppCompatActivity implements FinishListener<User> {
//    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//    private boolean showOneTapUI = true;
    private TextView registerLink;
    private EditText emailTxt, passwordTxt;
    private Button loginBtn, googleLoginBtn;
//    private SignInClient oneTapClient;
//    private BeginSignInRequest signInRequest;

    private GoogleSignInClient googleSignInClient;

    String client_webId = "798408453919-lcjvkil6547t34l2fk0av3d777mf98bd.apps.googleusercontent.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIsLoggedIn();
//    GoogleSignIn
        googleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope(Scopes.DRIVE_APPFOLDER)).requestServerAuthCode(client_webId).requestIdToken(client_webId).build());
        emailTxt = findViewById(R.id.login_email_txt);
        passwordTxt = findViewById(R.id.login_password_txt);
        loginBtn = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.register_link);
        googleLoginBtn = findViewById(R.id.google_login_btn);

        registerLink.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
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
                        Log.d("dapet", "onActivityResult: masuk");
                        if (result.getData() != null){
                            Log.d("dapet", "onActivityResult: masuk2");
                            try {
                                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                                GoogleSignInAccount account = task.getResult(ApiException.class);

                                Log.d("dapet", account.getEmail());
                                Log.d("dapet", account.getDisplayName());
                                Log.d("dapet", account.getPhotoUrl().toString());
                                Log.d("dapet", account.getServerAuthCode());
                            } catch (ApiException e) {
                                Log.d("dapet", "onActivityResult fail: " + e.getStatusCode());
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

    private void checkIsLoggedIn() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (prefs.contains("user_id")) {
            UserController.getUserById(prefs.getString("user_id", null), (data, message) -> {
                User.CURRENT_USER = data;
                if(data != null){
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

        }
    }
}