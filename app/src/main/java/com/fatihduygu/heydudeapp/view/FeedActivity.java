package com.fatihduygu.heydudeapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;

import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.FeedActivityViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {
    public static TabLayout signInTabLayout;
    private static final int CONTACT_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=101;

    @BindView(R.id.feed_activity_view_pager)
    ViewPager signInViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        signInTabLayout=findViewById(R.id.feed_activity_tab_layout);
        FeedActivityViewPagerAdapter feedActivityViewPagerAdapter=new FeedActivityViewPagerAdapter(getSupportFragmentManager());
        signInViewPager.setAdapter(feedActivityViewPagerAdapter);
        signInTabLayout.setupWithViewPager(signInViewPager);
        importantPermission();
    }



    private void importantPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},CONTACT_REQUEST_CODE);
        }else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
        }


    }

}
