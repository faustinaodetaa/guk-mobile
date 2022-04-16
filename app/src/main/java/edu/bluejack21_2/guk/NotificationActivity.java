package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.AdoptionAdapter;
import edu.bluejack21_2.guk.adapter.NotificationAdapter;
import edu.bluejack21_2.guk.controller.AdoptionController;
import edu.bluejack21_2.guk.controller.NotificationController;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Notification;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageView backBtn = findViewById(R.id.notification_back_icon);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        RecyclerView recyclerView;
        NotificationAdapter adapter;
        ArrayList<Notification> notifications;

        recyclerView = findViewById(R.id.notification_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(adapter);

        NotificationController.showAllNotifications(adapter, notifications);
    }
}