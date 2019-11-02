package com.fatihduygu.heydudeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.model.UserContactModel;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {
    private ArrayList<UserContactModel> userContactList;
    private OnPhoneListener onPhoneListener;

    public ContactRecyclerViewAdapter(ArrayList<UserContactModel> userContact,OnPhoneListener onPhoneListener) {
        this.userContactList = userContact;
        this.onPhoneListener=onPhoneListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contact_item,parent,false);
        ContactViewHolder contactViewHolder=new ContactViewHolder(itemView,onPhoneListener);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindViews(userContactList.get(position));
    }

    @Override
    public int getItemCount() {
        return userContactList.size();
    }



    // ****************************** ViewHolder ******************************
    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactUserNameTxt;
        TextView contactPhoneNumberTxt;
        OnPhoneListener onPhoneListener;
        ImageView contactProfilePhotoImageView;

        public ContactViewHolder(@NonNull View itemView,OnPhoneListener onPhoneClickListener) {
            super(itemView);
            contactUserNameTxt=itemView.findViewById(R.id.contactUserNameTxt);
            contactPhoneNumberTxt=itemView.findViewById(R.id.contactPhoneNumberTxt);
            contactProfilePhotoImageView=itemView.findViewById(R.id.contactProfilePhoto);

            this.onPhoneListener=onPhoneClickListener;
            itemView.setOnClickListener(this);
        }

        public void bindViews(UserContactModel contactModel){
            contactUserNameTxt.setText(contactModel.getUserContactName());
            contactPhoneNumberTxt.setText(contactModel.getUserContactPhoneNumber());
            if (contactModel.getUserProfileUrl()==null || contactModel.getUserProfileUrl().equals("")){
                Picasso.get().load("abc").placeholder(R.drawable.kangaroos).error(R.drawable.kangaroos).into(contactProfilePhotoImageView);
            }else {
                Transformation transformation=new RoundedTransformationBuilder().oval(true).build();
                Picasso.get().load(contactModel.getUserProfileUrl()).fit().centerCrop().transform(transformation).error(R.drawable.kangaroos).into(contactProfilePhotoImageView);

            }

        }

        @Override
        public void onClick(View view) {
            onPhoneListener.onPhoneClickListener(getAdapterPosition());

        }
    }

    public interface OnPhoneListener{
        void onPhoneClickListener(int position);
    }
}
