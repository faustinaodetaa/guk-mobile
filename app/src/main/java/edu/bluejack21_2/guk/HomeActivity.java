package edu.bluejack21_2.guk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import util.Database;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeTxt;

    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private ArrayList<Dog> dogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTxt = findViewById(R.id.home_welcome_txt);
        String txt = "Hello, <b>" + User.CURRENT_USER.getName() + "</b> !";
        welcomeTxt.setText(Html.fromHtml(txt));


        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogList = new ArrayList<>();
        dogAdapter = new DogAdapter(this,dogList);
        recyclerView.setAdapter(dogAdapter);

        Database.getDB().collection("dogs").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Dog dog = document.toObject(Dog.class);
                    Log.d("test", dog.getName());
                    dogList.add(dog);
                    dogAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}