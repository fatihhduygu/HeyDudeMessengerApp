package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.fatihduygu.heydudeapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionsFragment extends Fragment {

    @BindView(R.id.button)
    Button button;


    public static ConnectionsFragment newInstance() {
        ConnectionsFragment fragment = new ConnectionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_connections,container,false);
        ButterKnife.bind(this,view);
        button.setOnClickListener(view1 -> {
            Intent intent=new Intent(getContext(),ChatActivity.class);
            startActivity(intent);
        });
        return view;

    }
}
