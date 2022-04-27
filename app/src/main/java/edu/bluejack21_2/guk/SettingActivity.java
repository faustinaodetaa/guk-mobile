package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import edu.bluejack21_2.guk.util.ActivityHelper;

public class SettingActivity extends BaseActivity {

    private Button saveBtn;
    private ImageView backBtn;
    private Spinner fontSpinner, notificationSpinner;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        prefs = getSharedPreferences("edu.bluejack21_2.guk", Context.MODE_PRIVATE);
        editor = prefs.edit();

        backBtn = findViewById(R.id.setting_back_icon);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        saveBtn = findViewById(R.id.setting_save_btn);
        saveBtn.setOnClickListener(view -> {
            editor.commit();
            startActivity(new Intent(this, HomeActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.setting_bg), "rounded-bg").toBundle());
            finish();
        });

        fontSpinner = findViewById(R.id.setting_font_dropdown);
        ArrayAdapter<String> fontAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Small", "Normal", "Large"});
        fontSpinner.setAdapter(fontAdapter);

        String current = "Normal";
        if (prefs.contains("font_size")) {
            current = prefs.getString("font_size", null);
        }
        fontSpinner.setSelection(fontAdapter.getPosition(current));

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("font_size", fontAdapter.getItem(i));
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notificationSpinner = findViewById(R.id.setting_notification_dropdown);
        ArrayAdapter<String> notificationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Enable", "Disable"});
        notificationSpinner.setAdapter(notificationAdapter);

        current = "Enable";
        if (prefs.contains("push_notification")) {
            current = prefs.getString("push_notification", null);
        }
        notificationSpinner.setSelection(notificationAdapter.getPosition(current));

        notificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("push_notification", notificationAdapter.getItem(i));
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}