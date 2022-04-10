package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.model.Breed;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import util.Database;

public class HomeFragment extends Fragment {

    private TextView welcomeTxt;

    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private ArrayList<Dog> dogList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeTxt = view.findViewById(R.id.home_welcome_txt);
        String txt = "Hello, <b>" + User.CURRENT_USER.getName() + "</b> !";
        welcomeTxt.setText(Html.fromHtml(txt));


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        dogList = new ArrayList<>();
        dogAdapter = new DogAdapter(view.getContext(), dogList);
        recyclerView.setAdapter(dogAdapter);

        Database.getDB().collection("dogs").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){
                    Dog dog = document.toObject(Dog.class);
                    dog.setId(document.getId());

                    dogList.add(dog);
                    document.getDocumentReference("breed").get().addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            Breed b = t.getResult().toObject(Breed.class);
                            dog.setBreedByClass(b);
//                            Log.d("coba", "setBreed:  masuk " + this.breed.getName());
                            dogAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });


        return view;
    }
}