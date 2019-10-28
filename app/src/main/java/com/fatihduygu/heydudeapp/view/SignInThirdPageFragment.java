package com.fatihduygu.heydudeapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.viewmodel.SharedDataFromFragmentToFragmentViewModel;
import com.fatihduygu.heydudeapp.viewmodel.VerifyPhoneViewModel;
import com.santalu.maskedittext.MaskEditText;

public class SignInThirdPageFragment extends Fragment{
    private static final String TAG = "SignInThirdPageFragment";

    //View-Model
    private SharedDataFromFragmentToFragmentViewModel viewModel;
    private VerifyPhoneViewModel verifyPhoneViewModel;

    //Views
    private TextView signInThirdPagePhoneNumberTxt,signInThirdPagePhoneNumber2Txt;
    private Button signInThirdPageVerifyBtn;
    private MaskEditText signInThirdPagePhoneNumber;

    //Variable

    public static SignInThirdPageFragment newInstance() {

        SignInThirdPageFragment fragment = new SignInThirdPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin_thirdpage,container,false);
        signInThirdPageVerifyBtn=view.findViewById(R.id.signInThirdPageVerifyBtn);
        signInThirdPageVerifyBtn.setOnClickListener(onClickListener);
        signInThirdPagePhoneNumberTxt=view.findViewById(R.id.signInThirdPagePhoneNumberTxt);
        signInThirdPagePhoneNumber2Txt=view.findViewById(R.id.signInThirdPagePhoneNumber2Txt);
        signInThirdPagePhoneNumber=view.findViewById(R.id.signInThirdPagePhoneNumber);
        return view;

    }

    Button.OnClickListener onClickListener= view -> {

        String code=signInThirdPagePhoneNumber.getRawText().trim();

        if (code.isEmpty() || code.length()<6){
            signInThirdPagePhoneNumber.setError("Enter code...");
            signInThirdPagePhoneNumber.requestFocus();
        }else {
            verifyPhoneViewModel.verifyCodeManual(code);
        }

    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel= ViewModelProviders.of(getActivity()).get(SharedDataFromFragmentToFragmentViewModel.class);
        verifyPhoneViewModel= ViewModelProviders.of(getActivity()).get(VerifyPhoneViewModel.class);
        observeViews();

    }

    private void observeViews(){
        viewModel.getText().observe(getViewLifecycleOwner(), phoneNumber -> {
            signInThirdPagePhoneNumberTxt.setText(phoneNumber);
            signInThirdPagePhoneNumber2Txt.setText(phoneNumber);

            //Sent phone number to view model
            verifyPhoneViewModel.sendPhoneNumber((String) phoneNumber);

        });


        verifyPhoneViewModel.getComingCode().observe(getViewLifecycleOwner(), charSequence -> {
            signInThirdPagePhoneNumber.setText(charSequence);
        });

        verifyPhoneViewModel.getErrorMessage().observe(getViewLifecycleOwner(), charSequence -> Toast.makeText(getContext(), "Error :"+charSequence, Toast.LENGTH_SHORT).show());


        verifyPhoneViewModel.getMissionComplete().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean!=false){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int currentItem=SignInActivity.signInViewPager.getCurrentItem();
                SignInActivity.signInViewPager.setCurrentItem(currentItem+1);
            }

        });



    }


}
