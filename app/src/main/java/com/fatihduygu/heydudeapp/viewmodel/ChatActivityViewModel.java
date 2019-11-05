package com.fatihduygu.heydudeapp.viewmodel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fatihduygu.heydudeapp.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivityViewModel extends ViewModel {

    //Firebase variable
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference myRef=database.getReference();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser user=mAuth.getCurrentUser();

    //Variables
    private ArrayList<MessageModel> allMessages = new ArrayList<>();

    //Mutable Live Datas
    private MutableLiveData<ArrayList<MessageModel>> fetchAllMessages=new MutableLiveData<>();




    public LiveData<ArrayList<MessageModel>> getAllMessageObservable(){
        return fetchAllMessages;
    }

    public void sendMessage(String chatId, String message){
        sendMessageToFirebase(chatId,message);
    }

    public void getMessage(String chatId){
        getMessageToFirebase(chatId);
    }


    private void getMessageToFirebase(String chatId) {
        myRef.child("Chat").child(chatId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String messageId="",creator="",message="",messageDate="";

                    if (dataSnapshot.child("message").getValue()!=null)
                        message=dataSnapshot.child("message").getValue().toString();
                    if (dataSnapshot.child("creator").getValue()!=null)
                        creator=dataSnapshot.child("creator").getValue().toString();
                    if (dataSnapshot.child("date").getValue()!=null)
                        messageDate=dataSnapshot.child("date").getValue().toString();
                    if (dataSnapshot.getKey()!=null)
                        messageId=dataSnapshot.getKey();

                    MessageModel messageModel=new MessageModel(messageId,creator,message,messageDate,"");
                    allMessages.add(messageModel);
                    fetchAllMessages.setValue(allMessages);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessageToFirebase(String chatId, String message) {
        if (!message.isEmpty()){
            DatabaseReference newMessageRef=myRef.child("Chat").child(chatId).push();
            Map newMessage=new HashMap();
            newMessage.put("message",message);
            newMessage.put("creator",user.getUid());
            newMessage.put("date", ServerValue.TIMESTAMP);
            newMessageRef.updateChildren(newMessage);
        }
    }
}
