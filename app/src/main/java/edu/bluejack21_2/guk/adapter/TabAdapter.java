package edu.bluejack21_2.guk.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.bluejack21_2.guk.HomeFragment;
import edu.bluejack21_2.guk.StoryFragment;

public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new StoryFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
