package edu.bluejack21_2.guk.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.bluejack21_2.guk.CustomFragment;
import edu.bluejack21_2.guk.HomeFragment;
import edu.bluejack21_2.guk.StoryFragment;

public class TabAdapter extends FragmentStateAdapter {

    CustomFragment fragment1, fragment2;

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, CustomFragment fragment1, CustomFragment fragment2) {
        super(fragmentManager, lifecycle);
        this.fragment1 = fragment1;
        this.fragment2 = fragment2;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return fragment1;
        }
        return fragment2;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
