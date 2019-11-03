package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.ConnectionRecyclerViewAdapter;
import com.fatihduygu.heydudeapp.model.ChatObject;
import com.fatihduygu.heydudeapp.viewmodel.ConnectionFragmentViewModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionsFragment extends Fragment implements ConnectionRecyclerViewAdapter.OnChatListener {
    private ConnectionFragmentViewModel connectionFragmentViewModel;
    private ConnectionRecyclerViewAdapter connectionRecyclerViewAdapter;
    private ArrayList<ChatObject> chatsArrayList;

    @BindView(R.id.connection_fragment_recycler_view)
    RecyclerView connectionRecyclerView;


    public static ConnectionsFragment newInstance() {
        ConnectionsFragment fragment = new ConnectionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_connections,container,false);
        ButterKnife.bind(this,view);
        chatsArrayList=new ArrayList<>();
        connectionFragmentViewModel= ViewModelProviders.of(getActivity()).get(ConnectionFragmentViewModel.class);
        connectionFragmentViewModel.getChatList();
        observerViews();
        return view;

    }

    private void observerViews() {
        connectionFragmentViewModel.getChatListLiveData().observe(getViewLifecycleOwner(), chatObjects -> {
            chatsArrayList.clear();
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
            connectionRecyclerViewAdapter=new ConnectionRecyclerViewAdapter(chatObjects,this);
            chatsArrayList.addAll(chatObjects);
            connectionRecyclerView.setLayoutManager(layoutManager);
            connectionRecyclerView.setAdapter(connectionRecyclerViewAdapter);
            connectionRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onChatClickListener(int position) {
        ChatObject chatObject=chatsArrayList.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("chatId",chatObject.getChatId());

        Intent intent=new Intent(getContext(),ChatActivity.class);
        intent.putExtra("connectionInfo",bundle);
        startActivity(intent);

    }
}
