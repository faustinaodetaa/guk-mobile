package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DonationAdapter;
import edu.bluejack21_2.guk.controller.DonationController;
import edu.bluejack21_2.guk.model.Donation;

public class AdminDonateFragment extends CustomFragment {

    public AdminDonateFragment() {
        // Required empty public constructor
        super("Donations");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_donate, container, false);

        RecyclerView recyclerView;
        DonationAdapter adapter;
        ArrayList<Donation> donations;

        recyclerView = view.findViewById(R.id.admin_donate_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        donations = new ArrayList<>();
        adapter = new DonationAdapter(view.getContext(), donations);
        recyclerView.setAdapter(adapter);

        DonationController.showAllDonations(adapter, donations);

        return view;
    }
}