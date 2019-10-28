package com.fatihduygu.heydudeapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fatihduygu.heydudeapp.model.ChatModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivityViewModel extends ViewModel {

    //Firebase variable
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser user=mAuth.getCurrentUser();

    private List<ChatModel> allMessages = new ArrayList<>();


    //Mutable Live Datas
    private MutableLiveData<List<ChatModel>> fetchAllMessages=new MutableLiveData<>();
    private MutableLiveData<String> fetchAllMessagesError=new MutableLiveData<>();

    public LiveData<List<ChatModel>> getAllMessageObservable(){
        return fetchAllMessages;
    }

    public LiveData<String> getAllMessagesErrorObservable(){
        return fetchAllMessagesError;
    }


    public void sendMessage(String message,String contactPhoneNumber){
        sendMessageToFirestore(message,contactPhoneNumber);
    }

    public void getMessage(String contactPhoneNumber){
        getAllMessages(contactPhoneNumber);
    }

    private void sendMessageToFirestore(String message,String contactPhoneNumber){
        String userPhoneNumber=user.getPhoneNumber();
        String contactPhone=contactPhoneNumber;

        if (message.trim().length()>0){
            HashMap<String,Object> chatData=new HashMap<>();
            chatData.put("userPhoneNumber",userPhoneNumber);
            chatData.put("contactPhoneNumber",contactPhone);
            chatData.put("userMessage",message);
            chatData.put("messageDate", FieldValue.serverTimestamp());


            db.collection("Chats")
                    .add(chatData)
                    .addOnSuccessListener(documentReference -> {

                    })
                    .addOnFailureListener(e -> {

                    });
        }

    }


    private void getAllMessages(String contactPhoneNumber){
        String userPhone=user.getPhoneNumber();
        String contactPhone=contactPhoneNumber;

        db.collection("Chats")
                .orderBy("messageDate", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        allMessages.clear();
                        for (QueryDocumentSnapshot document:task.getResult()){
                            HashMap<String,Object> hashMap= (HashMap<String, Object>) document.getData();
                            String userPhoneNumber= (String) hashMap.get("userPhoneNumber");
                            String userMessage= (String) hashMap.get("userMessage");
                            Timestamp messageDate= (Timestamp) hashMap.get("messageDate");
                            allMessages.add(new ChatModel(userPhoneNumber,userMessage,messageDate));
                        }
                        fetchAllMessages.setValue(allMessages);
                    }
                })
                .addOnFailureListener(e -> {
                    fetchAllMessagesError.setValue(e.getLocalizedMessage());
                });
    }





}
