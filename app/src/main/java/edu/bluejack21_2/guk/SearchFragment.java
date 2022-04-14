package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Dog;


public class SearchFragment extends Fragment {

    private String[] searchMenu = {"Name", "Breed", "Age", "Gender"};


    public SearchFragment() {
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

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Spinner menus = view.findViewById(R.id.search_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, searchMenu);
        menus.setAdapter(adapter);

        RecyclerView recyclerView;
        DogAdapter dogAdapter;
        ArrayList<Dog> dogList;

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        dogList = new ArrayList<>();
        dogAdapter = new DogAdapter(view.getContext(), dogList);
        recyclerView.setAdapter(dogAdapter);

        DogController.showAllDogs(dogAdapter, dogList);

        return view;
    }
}