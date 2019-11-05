package com.fatihduygu.heydudeapp.viewmodel;

import android.util.Log;

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
                        String currentUser=mAuth.getCurrentUser().getUid();
                        String friendId="",user1="",user2="";
                        if (childSnapShot.child("user1").getValue()!=null)
                            user1=childSnapShot.child("user1").getValue().toString();
                        if (childSnapShot.child("user2").getValue()!=null)
                            user2=childSnapShot.child("user2").getValue().toString();

                        if (!currentUser.equals(user1))
                            friendId=user1;
                        if (!currentUser.equals(user2))
                            friendId=user2;

                        Log.d("Fatih", "onDataChange: "+friendId);
                        ChatObject chatObject=new ChatObject(childSnapShot.getKey(),friendId);
                        getFriendInfo(chatObject);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void getFriendInfo(ChatObject chatObject){
        chatObjectArrayList.clear();
        DatabaseReference databaseReference=mRef.child("Users").child(chatObject.getFriendId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String friendName="",friendPhoneNumber="",friendProfileImageUrl="";
                    if (dataSnapshot.child("userName").getValue()!=null)
                        friendName=dataSnapshot.child("userName").getValue().toString();
                    if (dataSnapshot.child("phoneNumber").getValue()!=null)
                        friendPhoneNumber=dataSnapshot.child("phoneNumber").getValue().toString();
                    if (dataSnapshot.child("profileImageUrl").getValue()!=null)
                        friendProfileImageUrl=dataSnapshot.child("profileImageUrl").getValue().toString();
                    ChatObject chatObject1=new ChatObject(chatObject.getChatId(),chatObject.getFriendId(),friendName,friendPhoneNumber,friendProfileImageUrl);
                    chatObjectArrayList.add(chatObject1);
                    chatListLiveData.setValue(chatObjectArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
