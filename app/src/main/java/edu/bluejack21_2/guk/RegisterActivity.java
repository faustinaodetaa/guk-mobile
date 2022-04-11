package edu.bluejack21_2.guk;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import edu.bluejack21_2.guk.controller.UserController;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private CardView choosePicBtn;
    private ImageView imageView;

    private Uri filePath;

    private EditText nameTxt, emailTxt, passwordTxt, confirmPasswordTxt, phoneTxt, addressTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        choosePicBtn = findViewById(R.id.register_choose_btn);
        registerBtn = findViewById(R.id.register_btn);
        imageView = findViewById(R.id.register_profile_picture);

        nameTxt = findViewById(R.id.register_name_txt);
        emailTxt = findViewById(R.id.register_email_txt);
        passwordTxt = findViewById(R.id.register_password_txt);
        confirmPasswordTxt = findViewById(R.id.register_confirm_password_txt);
        phoneTxt = findViewById(R.id.register_phone_txt);
        addressTxt = findViewById(R.id.register_address_txt);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        filePath = result.getData().getData();
//                        Log.d("coba", "onActivityResult: " + filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });

        choosePicBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(Intent.createChooser(intent,"Select Image from here..."));
        });

        registerBtn.setOnClickListener(view -> {
            String name = nameTxt.getText().toString();
            String email = emailTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            String confirmPassword = confirmPasswordTxt.getText().toString();
            String phoneNumber = phoneTxt.getText().toString();
            String address = addressTxt.getText().toString();

            if(UserController.insertUser(this, name, email, password, confirmPassword, phoneNumber, address, filePath)){
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }


}