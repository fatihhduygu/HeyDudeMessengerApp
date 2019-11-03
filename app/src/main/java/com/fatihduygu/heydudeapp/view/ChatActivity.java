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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.ChatRecyclerViewAdapter;
import com.fatihduygu.heydudeapp.model.MessageModel;
import com.fatihduygu.heydudeapp.viewmodel.ChatActivityViewModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    //Views
    @BindView(R.id.chat_activity_chatContent_rv)
    RecyclerView chatContentRecyclerView;

    @BindView(R.id.chat_activity_message_text_et)
    EditText messageText;

    //Variables
    private String contactName,chatId,contactPhoneNumber,contactUId;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private ArrayList<MessageModel> chatMessagesList;
    private RecyclerView.LayoutManager recyclerViewManager;


    //ViewModel
    private ChatActivityViewModel chatActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Intent intent=getIntent();
        Bundle contactBundle=intent.getBundleExtra("contactInfo");
        Bundle connectionBundle=intent.getBundleExtra("connectionInfo");

        if (contactBundle!=null){
            contactName=contactBundle.getString("contactName");
            contactPhoneNumber=contactBundle.getString("contactPhoneNumber");
            contactUId=contactBundle.getString("contactKey");
            chatId=contactBundle.getString("chatId");
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#B5A6A6\">"+contactName+"</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (connectionBundle!=null){
            chatId=connectionBundle.getString("chatId");
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#B5A6A6\">"+chatId+"</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);



        //View-Model
        chatActivityViewModel= ViewModelProviders.of(this).get(ChatActivityViewModel.class);
        chatActivityViewModel.getMessage(chatId);
        observerViews();

        //Recycler View Initialize
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        chatMessagesList= new ArrayList<>();
        chatRecyclerViewAdapter=new ChatRecyclerViewAdapter(chatMessagesList);
        recyclerViewManager=new LinearLayoutManager(getApplicationContext());
        chatContentRecyclerView.setLayoutManager(recyclerViewManager);
        chatContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        chatContentRecyclerView.setAdapter(chatRecyclerViewAdapter);

    }

    private void observerViews() {
        chatActivityViewModel.getAllMessageObservable().observe(this, messageModels -> {
            chatMessagesList.clear();
            chatMessagesList.addAll(messageModels);
            recyclerViewManager.scrollToPosition(chatMessagesList.size()-1);
            chatRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    public void sendMessage(View view) {
        String messageToSend=messageText.getText().toString().trim();
        chatActivityViewModel.sendMessage(chatId,messageToSend);
        messageText.setText("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
