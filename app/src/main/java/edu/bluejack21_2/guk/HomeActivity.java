package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.bluejack21_2.guk.model.User;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTxt = findViewById(R.id.home_welcome_txt);
        welcomeTxt.setText("Welcome, " + User.CURRENT_USER.getName() + " !");
    }
}