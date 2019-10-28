package com.fatihduygu.heydudeapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.model.CountryData;
import com.fatihduygu.heydudeapp.viewmodel.SharedDataFromFragmentToFragmentViewModel;
import com.santalu.maskedittext.MaskEditText;

public class SignInSecondPageFragment extends Fragment {
    private Button signInSecondPageNextBtn;
    private Spinner countriesSpinner;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private MaskEditText signInSecondPagePhoneNumber;

    private SharedDataFromFragmentToFragmentViewModel viewModel;


    public static SignInSecondPageFragment newInstance() {
        SignInSecondPageFragment fragment = new SignInSecondPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_signin_secondpage,container,false);
        signInSecondPageNextBtn=view.findViewById(R.id.signInSecondPageNextBtn);
        signInSecondPageNextBtn.setOnClickListener(onClickListener);


        countriesSpinner=view.findViewById(R.id.sign_in_second_page_countries_spinner);
        spinnerArrayAdapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        countriesSpinner.setAdapter(spinnerArrayAdapter);

        signInSecondPagePhoneNumber=view.findViewById(R.id.signInSecondPagePhoneNumber);

        return view;
    }

    Button.OnClickListener onClickListener= view -> {
        String code=CountryData.countryAreaCodes[countriesSpinner.getSelectedItemPosition()];
        String number=signInSecondPagePhoneNumber.getRawText().trim();

        if (number.isEmpty() || number.length()<10){
            signInSecondPagePhoneNumber.setError("Valid number is required");
            signInSecondPagePhoneNumber.requestFocus();
        }else{
            String phoneNumber="+"+code+number;

            //Send phoneNumber to Next Fragment
            viewModel.setPhoneNumber(phoneNumber);

            //Next FragmentPage
            int currentItem=SignInActivity.signInViewPager.getCurrentItem();
            SignInActivity.signInViewPager.setCurrentItem(currentItem+1);
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel= ViewModelProviders.of(getActivity()).get(SharedDataFromFragmentToFragmentViewModel.class);
    }
}
