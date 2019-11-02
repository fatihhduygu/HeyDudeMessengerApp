package com.fatihduygu.heydudeapp.viewmodel;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fatihduygu.heydudeapp.model.CountryToPhonePrefix;
import com.fatihduygu.heydudeapp.model.UserContactModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ContactFragmentViewModel extends ViewModel {
    private ArrayList<UserContactModel> userContactsList = new ArrayList<>();
    private Context context;
    private MutableLiveData<ArrayList<UserContactModel>> userContactNameLiveData = new MutableLiveData<>();


    public MutableLiveData<ArrayList<UserContactModel>> getUserContactNameLiveData() {
        return userContactNameLiveData;
    }

    public void readUserContact(Context context) {
        userContactsList.clear();
        this.context = context;
        fetchUserContact();
    }


    private void fetchUserContact() {
        String countryCode=getCountryCode();
        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            //*************** Contact Name and PhoneNumber Content Resolver ***************
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
            String nameColumnIx = ContactsContract.Contacts.DISPLAY_NAME;
            String phoneColumnIx = ContactsContract.CommonDataKinds.Phone.NUMBER;
            String previousPhone="";
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(nameColumnIx));
                String phone = cursor.getString(cursor.getColumnIndex(phoneColumnIx));

                phone=phone.replace(" ","");
                phone=phone.replace("-","");
                phone=phone.replace("(","");
                phone=phone.replace(")","");

                if (!String.valueOf(phone.charAt(0)).equals("+"))
                    phone=countryCode+phone.replaceFirst("0","").trim();

                if (previousPhone.equals(phone)){
                    continue;
                }
                previousPhone=phone;
                UserContactModel userContactModel=new UserContactModel(name,phone);
                checkPhoneNumberInFirebase(userContactModel);
            }
            cursor.close();
        }

    }


    private void checkPhoneNumberInFirebase (UserContactModel mContact) {
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("Users");
        Query query=mRef.orderByChild("phoneNumber").equalTo(mContact.getUserContactPhoneNumber());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String phoneNumber="",
                            userName="",
                            profileImageUrl="";

                    for (DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                        if (childSnapshot.child("phoneNumber").getValue()!=null)
                            phoneNumber=childSnapshot.child("phoneNumber").getValue().toString();
                        if (childSnapshot.child("userName").getValue()!=null)
                            userName=childSnapshot.child("userName").getValue().toString();
                        if (childSnapshot.child("profileImageUrl").getValue()!=null)
                            profileImageUrl=childSnapshot.child("profileImageUrl").getValue().toString();

                        UserContactModel mUser=new UserContactModel(mContact.getUserContactName(),phoneNumber,profileImageUrl);
                        userContactsList.add(mUser);
                    }
                    Collections.sort(userContactsList,UserContactModel.sortByName);
                    userContactNameLiveData.setValue(userContactsList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getCountryCode(){
        String countryCode="";
        TelephonyManager telephonyManager= (TelephonyManager) context.getApplicationContext().getSystemService(context.getApplicationContext().TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso()!=null)
            if (!telephonyManager.getNetworkCountryIso().equals(""))
                countryCode=telephonyManager.getNetworkCountryIso();
        return CountryToPhonePrefix.getPhone(countryCode);
    }


}
