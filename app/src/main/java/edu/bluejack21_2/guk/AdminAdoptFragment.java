package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.AdoptionAdapter;
import edu.bluejack21_2.guk.controller.AdoptionController;
import edu.bluejack21_2.guk.model.Adoption;


public class AdminAdoptFragment extends CustomFragment {


    public AdminAdoptFragment(String name) {
        // Required empty public constructor
        super(name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_adopt, container, false);

        RecyclerView recyclerView;
        AdoptionAdapter adoptionAdapter;
        ArrayList<Adoption> adoptions;

        recyclerView = view.findViewById(R.id.admin_adopt_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adoptions = new ArrayList<>();
        adoptionAdapter = new AdoptionAdapter(view.getContext(), adoptions);
        recyclerView.setAdapter(adoptionAdapter);

        AdoptionController.showAllAdoptions(adoptionAdapter, adoptions);

        return view;
    }
}