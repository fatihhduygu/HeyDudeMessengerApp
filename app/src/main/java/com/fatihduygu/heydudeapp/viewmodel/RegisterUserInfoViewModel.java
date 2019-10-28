package com.fatihduygu.heydudeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class RegisterUserInfoViewModel extends ViewModel {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    //LiveData
    private MutableLiveData<Boolean> isUserCreated=new MutableLiveData<>();
    private MutableLiveData<String> registerError=new MutableLiveData<>();

    public LiveData<Boolean> getIsUserCreated(){
        return isUserCreated;
    }

    public LiveData<String> getRegisterError(){
        return registerError;
    }

    public void setUserInfo(CharSequence userName){
        addDataToRealtimeDatabase(userName);
        addDataToFirestore(userName);

    }


    private void addDataToFirestore(CharSequence userName){
        String userUid=mAuth.getCurrentUser().getUid();
        HashMap<String,Object> user=new HashMap<>();
        user.put("userName",userName);
        user.put("about","Hey bro! I am using HeyDudeApp");
        user.put("phoneNumber",mAuth.getCurrentUser().getPhoneNumber());

        db.collection("Users")
                .document(userUid)
                .set(user)
                .addOnSuccessListener(documentReference -> {
                    isUserCreated.setValue(true);

                })
                .addOnFailureListener(e -> {
                    registerError.setValue(e.getLocalizedMessage());
                });
    }

    private void addDataToRealtimeDatabase(CharSequence userName){
        String userUid=mAuth.getCurrentUser().getUid();
        DatabaseReference myRef=database.getReference();
        myRef.child("Users").child(userUid).child("userName").setValue(userName);
        myRef.child("Users").child(userUid).child("about").setValue("Hey bro! I am using HeyDudeApp");
        myRef.child("Users").child(userUid).child("phoneNumber").setValue(mAuth.getCurrentUser().getPhoneNumber());
        isUserCreated.setValue(true);
    }




}
