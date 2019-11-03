package com.fatihduygu.heydudeapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fatihduygu.heydudeapp.model.ChatObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ConnectionFragmentViewModel extends ViewModel {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mRef=database.getReference();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private ArrayList<ChatObject> chatObjectArrayList=new ArrayList<>();

    private MutableLiveData<ArrayList<ChatObject>> chatListLiveData=new MutableLiveData<>();

    public MutableLiveData<ArrayList<ChatObject>> getChatListLiveData() {
        return chatListLiveData;
    }

    public void getChatList(){
        chatObjectArrayList.clear();
        fetchUserChatList();
    }

    private void fetchUserChatList(){
        DatabaseReference databaseReference=mRef.child("Users").child(mAuth.getUid()).child("chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot childSnapShot:dataSnapshot.getChildren()){
                        ChatObject chatObject=new ChatObject(childSnapShot.getKey());
                        chatObjectArrayList.add(chatObject);
                    }
                    chatListLiveData.setValue(chatObjectArrayList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
