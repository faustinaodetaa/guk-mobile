package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BadgeActivity extends BaseActivity {

    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

        backIcon = findViewById(R.id.badge_back_icon);

        backIcon.setOnClickListener(view -> {
            finish();
        });
    }
}