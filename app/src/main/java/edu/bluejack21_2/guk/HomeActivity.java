package edu.bluejack21_2.guk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.bluejack21_2.guk.model.User;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView welcomeTxt;
    private ImageView notificationBtn;

    private LinearLayout topTitleBar;

    private BottomNavigationView bottomNavigationView;

    //    private HomeFragment homeFragment = new HomeFragment();
    private TabFragment homeTabFragment = new TabFragment(new HomeFragment(), new StoryFragment());
    private TabFragment donateAdoptTabFragment = new TabFragment(new AdminDonateFragment(), new AdminAdoptFragment());
    private SearchFragment searchFragment = new SearchFragment();
    private AddDogFragment addDogFragment = new AddDogFragment();
    private AddStoryFragment addStoryFragment = new AddStoryFragment();
    private UserDonateFragment userDonateFragment = new UserDonateFragment();
    private AdminDonateFragment adminDonateFragment = new AdminDonateFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private StoryFragment storyFragment = new StoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration(), 1);
        setContentView(R.layout.activity_home);

        welcomeTxt = findViewById(R.id.home_welcome_txt);
        String txt = "Hello, <b style=\"font-weight: 800;\">" + User.CURRENT_USER.getName() + "</b> !";
        welcomeTxt.setText(Html.fromHtml(txt));

        notificationBtn = findViewById(R.id.notification_btn);
        notificationBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });

        topTitleBar = findViewById(R.id.top_title_bar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void adjustFontScale(Configuration configuration, float scale) {
        configuration.fontScale = scale;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            toggleTitleBar(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeTabFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.search) {
            toggleTitleBar(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.add) {
            toggleTitleBar(false);
            if (User.CURRENT_USER.getRole().equals("admin")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addDogFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addStoryFragment).commit();
            }
            return true;
        } else if (item.getItemId() == R.id.donate) {
            toggleTitleBar(false);
            if (User.CURRENT_USER.getRole().equals("admin")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, donateAdoptTabFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userDonateFragment).commit();
            }
            return true;
        } else if (item.getItemId() == R.id.profile) {
            toggleTitleBar(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
            return true;
        }

        return false;
    }

    private void toggleTitleBar(boolean isProfile) {
        topTitleBar.setVisibility(isProfile ? View.GONE : View.VISIBLE);
        if (isProfile) {
            findViewById(R.id.fragment_container).setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            findViewById(R.id.fragment_container).setBackground(getResources().getDrawable(R.drawable.rounded_edge));
        }
    }
}