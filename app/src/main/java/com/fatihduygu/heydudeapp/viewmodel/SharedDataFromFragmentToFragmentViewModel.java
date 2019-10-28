package com.fatihduygu.heydudeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedDataFromFragmentToFragmentViewModel extends ViewModel {
    private MutableLiveData<CharSequence> phoneNumber=new MutableLiveData<>();

    public void setPhoneNumber(CharSequence phoneNumber){
        this.phoneNumber.setValue(phoneNumber);
    }

    public LiveData<CharSequence> getText(){
        return phoneNumber;
    }


}
