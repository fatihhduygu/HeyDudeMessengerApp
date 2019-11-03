package com.fatihduygu.heydudeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.model.ChatObject;
import java.util.ArrayList;

public class ConnectionRecyclerViewAdapter extends RecyclerView.Adapter<ConnectionRecyclerViewAdapter.ConnectionViewHolder> {
    private ArrayList<ChatObject> userChatList;
    private OnChatListener onChatListener;

    public ConnectionRecyclerViewAdapter(ArrayList<ChatObject> userChatList, OnChatListener onChatListener) {
        this.userChatList = userChatList;
        this.onChatListener=onChatListener;
    }

    @NonNull
    @Override
    public ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_connections_item,parent,false);
        ConnectionViewHolder connectionViewHolder=new ConnectionViewHolder(itemView,onChatListener);
        return connectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionViewHolder holder, int position) {
        holder.bindViews(userChatList.get(position));
    }

    @Override
    public int getItemCount() {
        return userChatList.size();
    }


    // ****************************** ViewHolder ******************************
    public class ConnectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView friendsUserNameTxt;
        TextView lastMessageTxt;
        OnChatListener onChatListener;
        ImageView friendsProfilePhotoImageView;

        public ConnectionViewHolder(@NonNull View itemView,OnChatListener onChatClickListener) {
            super(itemView);

            friendsUserNameTxt=itemView.findViewById(R.id.friendsUserName);
            lastMessageTxt=itemView.findViewById(R.id.lastMessage);
            friendsProfilePhotoImageView=itemView.findViewById(R.id.friendsProfilePhoto);

            this.onChatListener=onChatClickListener;
            itemView.setOnClickListener(this);
        }

        public void bindViews(ChatObject chatObject){
            friendsUserNameTxt.setText(chatObject.getChatId());

        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClickListener(getAdapterPosition());

        }
    }

    public interface OnChatListener{
        void onChatClickListener(int position);
    }
}
