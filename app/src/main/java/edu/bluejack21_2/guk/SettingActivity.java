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

    private ImageView backBtn;
    private Spinner fontSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backBtn = findViewById(R.id.setting_back_icon);
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.setting_bg), "rounded-bg").toBundle());
            finish();
        });

        fontSpinner = findViewById(R.id.setting_font_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Small", "Normal", "Large"});
        fontSpinner.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("edu.bluejack21_2.guk", Context.MODE_PRIVATE);
        String current = "Normal";
        if (prefs.contains("font_size")) {
            current = prefs.getString("font_size", null);
        }
        fontSpinner.setSelection(adapter.getPosition(current));

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("coba", "onItemSelected: " + adapter.getItem(i));
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("font_size", adapter.getItem(i));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}