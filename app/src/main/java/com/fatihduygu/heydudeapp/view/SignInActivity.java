package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.SignInViewPagerAdapter;
import com.fatihduygu.heydudeapp.model.FragmentListener;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    public static ViewPager signInViewPager;
    private SignInViewPagerAdapter signInViewPagerAdapter;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        getSupportActionBar().hide();
        signInViewPager=findViewById(R.id.sign_in_activity_view_pager);
        signInViewPagerAdapter=new SignInViewPagerAdapter(getSupportFragmentManager());
        signInViewPager.setAdapter(signInViewPagerAdapter);
        signInViewPager.beginFakeDrag();
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(this,FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
