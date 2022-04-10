package edu.bluejack21_2.guk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private TextView welcomeTxt;

    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private AddDogFragment addDogFragment = new AddDogFragment();
    private AddStoryFragment addStoryFragment = new AddStoryFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTxt = findViewById(R.id.home_welcome_txt);
        String txt = "Hello, <b>" + User.CURRENT_USER.getName() + "</b> !";
        welcomeTxt.setText(Html.fromHtml(txt));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            return true;
        } else if(item.getItemId() == R.id.search){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
            return true;
        } else if(item.getItemId() == R.id.add){
            if(User.CURRENT_USER.getRole().equals("admin")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addDogFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addStoryFragment).commit();
            }
            return true;
        }

        return false;
    }
}