package com.fatihduygu.heydudeapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fatihduygu.heydudeapp.view.ConnectionsFragment;
import com.fatihduygu.heydudeapp.view.ContactFragment;
import com.fatihduygu.heydudeapp.view.ProfileFragment;

public class FeedActivityViewPagerAdapter extends FragmentStatePagerAdapter {

    public FeedActivityViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment;

        switch (position){
            case 0:
                selectedFragment= ConnectionsFragment.newInstance();
                break;
            case 1:
                selectedFragment= ContactFragment.newInstance();
                break;
            case 2:
                selectedFragment= ProfileFragment.newInstance();
                break;
                default:
                    selectedFragment=null;
        }

        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence selectedTitle;

        switch (position){
            case 0:
                selectedTitle="CHATS";
                break;
            case 1:
                selectedTitle="CONTACTS";
                break;
            case 2:
                selectedTitle="PROFILE";
                break;
                default:
                    selectedTitle=null;
        }
        return selectedTitle;
    }
}
