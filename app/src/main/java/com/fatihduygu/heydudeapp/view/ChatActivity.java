package com.fatihduygu.heydudeapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.ChatRecyclerViewAdapter;
import com.fatihduygu.heydudeapp.model.ChatModel;
import com.fatihduygu.heydudeapp.viewmodel.ChatActivityViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    //Views
    @BindView(R.id.chat_activity_chatContent_rv)
    RecyclerView chatContentRecyclerView;

    @BindView(R.id.chat_activity_message_text_et)
    EditText messageText;

    //Variables
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private ArrayList<String> chatMessagesList;
    private String contactName;
    private String contactPhoneNumber;

    //ViewModel
    private ChatActivityViewModel chatActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("contactInfo");
        if (bundle!=null){
            contactName=bundle.getString("contactName");
            contactPhoneNumber=bundle.getString("contactPhoneNumber");
            //Toast.makeText(this, contactName+": "+contactPhoneNumber, Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#B5A6A6\">"+contactName+"</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ButterKnife.bind(this);
        chatMessagesList= new ArrayList<>();
        chatRecyclerViewAdapter=new ChatRecyclerViewAdapter(chatMessagesList);
        chatActivityViewModel= ViewModelProviders.of(this).get(ChatActivityViewModel.class);
        chatActivityViewModel.getMessage(contactPhoneNumber);
        observerViews();

        //Recycler View  Data Binding Process
        RecyclerView.LayoutManager recyclerViewManager=new LinearLayoutManager(getApplicationContext());
        chatContentRecyclerView.setLayoutManager(recyclerViewManager);
        chatContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        chatContentRecyclerView.setAdapter(chatRecyclerViewAdapter);
    }

    private void observerViews() {
        chatActivityViewModel.getAllMessageObservable().observe(this, new Observer<List<ChatModel>>() {
            @Override
            public void onChanged(List<ChatModel> chatModels) {
                chatMessagesList.clear();
                for (ChatModel messageItem :chatModels){
                    chatMessagesList.add(messageItem.getUserPhoneNumber()+": "+messageItem.getUserMessage());
                }
                chatContentRecyclerView.scrollToPosition(chatMessagesList.size()-1);
                chatRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        chatActivityViewModel.getAllMessagesErrorObservable().observe(this, s -> {
            Toast.makeText(this, "Error : "+s, Toast.LENGTH_SHORT).show();
            Log.d("Fatih", "observerViews: Error : "+s);
        });


    }
    public void sendMessage(View view) {
        String messageToSend=messageText.getText().toString().trim();
        chatActivityViewModel.sendMessage(messageToSend,contactPhoneNumber);
        chatActivityViewModel.getMessage(contactPhoneNumber);
        messageText.setText("");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
