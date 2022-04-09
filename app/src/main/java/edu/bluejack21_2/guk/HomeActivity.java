package edu.bluejack21_2.guk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import util.Database;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeTxt;

    RecyclerView recyclerView;
//    DatabaseReference database;
    DogAdapter dogAdapter;
    ArrayList<Dog> dogList;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTxt = findViewById(R.id.home_welcome_txt);
        welcomeTxt.setText("Welcome, " + User.CURRENT_USER.getName() + " !");


        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
//        database = FirebaseDatabase.getInstance().getReference("");
//        Log.d("test", database.toString());
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
//                    for(int i =0; i<dogList.size(); i++){
//                        Log.d("test", dogList.get(i).toString());
//                    }
                    dogAdapter.notifyDataSetChanged();
                }
            }
        });

//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Dog dog = dataSnapshot.getValue(Dog.class);
//                    dogList.add(dog);
//
//                    for(int i =0; i<dogList.size(); i++){
//                        Log.d("test", dogList.get(i).toString());
//                    }
//                }
//                dogAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}