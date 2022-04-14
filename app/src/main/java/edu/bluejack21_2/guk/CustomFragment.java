package edu.bluejack21_2.guk;

import androidx.fragment.app.Fragment;

public class CustomFragment extends Fragment {
    private String name;

    public CustomFragment() {
    }

    public CustomFragment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
