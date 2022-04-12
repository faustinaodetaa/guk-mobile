package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.bluejack21_2.guk.adapter.DogAdapter;
import edu.bluejack21_2.guk.adapter.StoryAdapter;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.controller.StoryController;
import edu.bluejack21_2.guk.model.Story;

public class StoryFragment extends Fragment {

    public StoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        RecyclerView recyclerView;
        StoryAdapter storyAdapter;
        ArrayList<Story> storyList;

        recyclerView = (RecyclerView) view.findViewById(R.id.story_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        storyList = new ArrayList<>();
        storyAdapter = new StoryAdapter(view.getContext(), storyList);
        recyclerView.setAdapter(storyAdapter);

        StoryController.showAllStories(storyAdapter, storyList);

        return view;
    }
}