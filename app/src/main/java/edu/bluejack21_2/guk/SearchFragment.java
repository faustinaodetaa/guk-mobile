package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Dog;


public class SearchFragment extends Fragment {

    private String[] searchMenu;
    private EditText searchTxt;
    private ImageView searchIcon;

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
        searchMenu = new String[]{getString(R.string.name), getString(R.string.breed), getString(R.string.age), getString(R.string.gender)};
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

        searchTxt = view.findViewById(R.id.search_content_txt);
        searchIcon = view.findViewById(R.id.search_bar_button);

        searchIcon.setOnClickListener(view1 -> {
            String keyword = searchTxt.getText().toString();
            String selectedFilter = menus.getSelectedItem().toString();
            if(selectedFilter.equals("Name") || selectedFilter.equals("Nama")){
                DogController.showDogsByName(dogAdapter, dogList, keyword);
            }else if(selectedFilter.equals("Breed") || selectedFilter.equals("Jenis")){
                DogController.showDogsByBreed(dogAdapter, dogList, keyword);
            }else if(selectedFilter.equals("Gender") || selectedFilter.equals("Jenis Kelamin")){
                DogController.showDogsByGender(dogAdapter, dogList, keyword);
            }else if(selectedFilter.equals("Age") || selectedFilter.equals("Umur")){
                DogController.showDogsByAge(dogAdapter, dogList, keyword);
            }

            Log.d("testing", keyword);
            Log.d("testing", selectedFilter);

        });

        return view;
    }
}