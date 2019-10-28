package com.fatihduygu.heydudeapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fatihduygu.heydudeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInFirstPageFragment extends Fragment {
    Button signInFirstPageAgreeBtn;

    public static SignInFirstPageFragment newInstance() {
        SignInFirstPageFragment fragment = new SignInFirstPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin_firstpage,container,false);
        signInFirstPageAgreeBtn=view.findViewById(R.id.signInFirstPageAgreeBtn);
        signInFirstPageAgreeBtn.setOnClickListener(onClickListener);
        return view;
    }


    Button.OnClickListener onClickListener= view -> {
        int currentItem=SignInActivity.signInViewPager.getCurrentItem();
        SignInActivity.signInViewPager.setCurrentItem(currentItem+1);

    };
}
