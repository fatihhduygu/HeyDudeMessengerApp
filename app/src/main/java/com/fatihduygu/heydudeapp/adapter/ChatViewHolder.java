package com.fatihduygu.heydudeapp.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    TextView messageContent;


    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        messageContent=itemView.findViewById(R.id.custom_chat_item_one_message_content_txt);
    }

    public void bindViews(String messageItem){
        messageContent.setText(messageItem);

    }
}
