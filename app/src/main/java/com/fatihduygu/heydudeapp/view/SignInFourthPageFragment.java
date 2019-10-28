package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.viewmodel.RegisterUserInfoViewModel;

public class SignInFourthPageFragment extends Fragment implements View.OnClickListener {

    private RegisterUserInfoViewModel registerUserInfoViewModel;

    private EditText userName;
    private Button nextButton;


    public static SignInFourthPageFragment newInstance() {
        SignInFourthPageFragment fragment = new SignInFourthPageFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin_fourthpage,container,false);
        nextButton=view.findViewById(R.id.signInFourthPageNextBtn);
        nextButton.setOnClickListener(this);

        userName=view.findViewById(R.id.sign_in_fragment_fourth_page_username);
        return view;
    }

    @Override
    public void onClick(View view) {
        registerUserInfoViewModel.setUserInfo(userName.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerUserInfoViewModel= ViewModelProviders.of(getActivity()).get(RegisterUserInfoViewModel.class);
        observerViews();
    }



    private void observerViews() {
        registerUserInfoViewModel.getIsUserCreated().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean==true){
                Intent intent=new Intent(getContext(),FeedActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Registry is successful", Toast.LENGTH_SHORT).show();
            }
        });

        registerUserInfoViewModel.getRegisterError().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), "Error:"+s, Toast.LENGTH_SHORT).show();
        });
    }
}
