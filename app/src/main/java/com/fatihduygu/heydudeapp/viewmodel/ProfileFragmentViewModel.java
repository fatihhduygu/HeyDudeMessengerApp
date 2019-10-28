package com.fatihduygu.heydudeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;

public class ProfileFragmentViewModel extends ViewModel {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();


    private HashMap<String,Object> hashMap;
    private MutableLiveData<HashMap<String,Object>> userInfo=new MutableLiveData<>();

    public LiveData<HashMap<String,Object>> getUserInfo(){
        return userInfo;
    }

    public void fetchUserInfo(){
        readUserData();
    }

    private void readUserData() {
        db.collection("Users")
                .whereEqualTo("phoneNumber",mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                            hashMap= (HashMap<String, Object>) documentSnapshot.getData();
                        }
                        userInfo.setValue(hashMap);
                    }
                });
    }




}
