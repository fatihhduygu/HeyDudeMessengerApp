package com.fatihduygu.heydudeapp.adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder> {
    private ArrayList<MessageModel> chatMessageList;
    String userId= FirebaseAuth.getInstance().getUid();


    public ChatRecyclerViewAdapter(ArrayList<MessageModel> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item,parent,false);
        ChatViewHolder chatViewHolder=new ChatViewHolder(itemView);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bindViews(chatMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    // ************************* View Holder *************************

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        //TextView rightMessageTxt;
        //LinearLayout messageLinearLayoutInRightSide;

        //TextView userName;
        TextView leftMessageTxt;
        //LinearLayout messageLinearLayoutInLeftSide;




        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            //rightMessageTxt=itemView.findViewById(R.id.chat_message_item_right_message_txt);
            //messageLinearLayoutInRightSide=itemView.findViewById(R.id.chat_message_item_right_linear_layout);

            leftMessageTxt=itemView.findViewById(R.id.chat_message_item_left_message_txt);
            //messageLinearLayoutInLeftSide=itemView.findViewById(R.id.chat_message_item_left_linear_layout);
            //userName=itemView.findViewById(R.id.chat_message_item_user_name);

        }

        public void bindViews(MessageModel messageItem){
            leftMessageTxt.setText(messageItem.getMessage());
            //userName.setText(messageItem.getCreator());
        }
    }
}

