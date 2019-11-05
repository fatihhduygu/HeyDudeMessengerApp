package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.adapter.ContactRecyclerViewAdapter;
import com.fatihduygu.heydudeapp.model.UserContactModel;
import com.fatihduygu.heydudeapp.viewmodel.ContactFragmentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactFragment extends Fragment implements ContactRecyclerViewAdapter.OnPhoneListener {
    private ContactFragmentViewModel contactFragmentViewModel;
    private ContactRecyclerViewAdapter contactRecyclerViewAdapter;

    private ArrayList<UserContactModel>  contactsInfo=new ArrayList<>();

    @BindView(R.id.user_contact_recycler_view)
    RecyclerView userContactRecyclerView;

    @BindView(R.id.contact_fragment_refresh_layout)
    SwipeRefreshLayout refreshLayout;


    public static ContactFragment newInstance() {

        Bundle args = new Bundle();

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);
        ButterKnife.bind(this,view);

        refreshLayout.setOnRefreshListener(() -> {
            contactFragmentViewModel.readUserContact(getContext());
            refreshLayout.setRefreshing(false);
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactFragmentViewModel= ViewModelProviders.of(getActivity()).get(ContactFragmentViewModel.class);
        contactFragmentViewModel.readUserContact(getActivity());
        observerViews();
    }

    private void observerViews() {

        contactFragmentViewModel.getUserContactNameLiveData().observe(getViewLifecycleOwner(), userContactModels -> {
            contactsInfo.clear();
            contactsInfo.addAll(userContactModels);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
            contactRecyclerViewAdapter=new ContactRecyclerViewAdapter(contactsInfo,this);
            userContactRecyclerView.setLayoutManager(layoutManager);
            userContactRecyclerView.setAdapter(contactRecyclerViewAdapter);
            contactRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onPhoneClickListener(int position) {
        UserContactModel userContactModel=contactsInfo.get(position);
        String creator=FirebaseAuth.getInstance().getUid();
        String otherUser=userContactModel.getuId();
        String key= FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();


        FirebaseDatabase.getInstance().getReference().child("Users").child(creator).child("chat").child(key).setValue(true);
        FirebaseDatabase.getInstance().getReference().child("Users").child(otherUser).child("chat").child(key).setValue(true);

        FirebaseDatabase.getInstance().getReference().child("Users").child(creator).child("chat").child(key).child("user1").setValue(creator);
        FirebaseDatabase.getInstance().getReference().child("Users").child(creator).child("chat").child(key).child("user2").setValue(otherUser);

        FirebaseDatabase.getInstance().getReference().child("Users").child(otherUser).child("chat").child(key).child("user1").setValue(creator);
        FirebaseDatabase.getInstance().getReference().child("Users").child(otherUser).child("chat").child(key).child("user2").setValue(otherUser);


        Bundle bundle=new Bundle();
        bundle.putString("contactName",userContactModel.getUserContactName());
        bundle.putString("contactPhoneNumber",userContactModel.getUserContactPhoneNumber());
        bundle.putString("contactKey",userContactModel.getuId());
        bundle.putString("chatId",key);
        Intent intent=new Intent(getContext(),ChatActivity.class);
        intent.putExtra("contactInfo",bundle);
        startActivity(intent);
    }
}
