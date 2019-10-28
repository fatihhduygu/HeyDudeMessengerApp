package com.fatihduygu.heydudeapp.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fatihduygu.heydudeapp.view.SignInFirstPageFragment;
import com.fatihduygu.heydudeapp.view.SignInFourthPageFragment;
import com.fatihduygu.heydudeapp.view.SignInSecondPageFragment;
import com.fatihduygu.heydudeapp.view.SignInThirdPageFragment;

public class SignInViewPagerAdapter extends FragmentStatePagerAdapter {

    public SignInViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment;

        switch (position){
            case 0:
                selectedFragment= SignInFirstPageFragment.newInstance();
                break;
            case 1:
                selectedFragment= SignInSecondPageFragment.newInstance();
                break;
            case 2:
                selectedFragment= SignInThirdPageFragment.newInstance();
                break;
            case 3:
                selectedFragment= SignInFourthPageFragment.newInstance();
                break;
                default:
                    selectedFragment=null;
        }

        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
