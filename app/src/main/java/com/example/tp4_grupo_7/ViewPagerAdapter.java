package com.example.tp4_grupo_7;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AltaFragment();
            case 1:
                return new BuscarFragment();
            case 2:
                return new ListadoFragment();
            default:
                return new AltaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;  // cantidad de fragments
    }
}

