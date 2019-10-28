package com.fatihduygu.heydudeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;
import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<String> chatMessageList;


    public ChatRecyclerViewAdapter(List<String> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_first_person,parent,false);
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
}
