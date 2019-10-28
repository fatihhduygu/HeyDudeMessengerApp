package com.fatihduygu.heydudeapp.viewmodel;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fatihduygu.heydudeapp.model.UserContactModel;

import java.util.ArrayList;

public class ContactFragmentViewModel extends ViewModel {
    private ArrayList<String> userContactNameList=new ArrayList<>();
    private ArrayList<String> userContactPhoneNumberList=new ArrayList<>();
    private ArrayList<UserContactModel> userContactsList=new ArrayList<>();
    private Context context;
    private MutableLiveData<ArrayList<UserContactModel>> userContactNameLiveData=new MutableLiveData<>();



    public MutableLiveData<ArrayList<UserContactModel>> getUserContactNameLiveData() {
        return userContactNameLiveData;
    }

    public void readUserContact(Context context){
        this.context=context;
        userContactNameList.clear();
        userContactPhoneNumberList.clear();
        fetchUserContact();
    }



    private void fetchUserContact() {
        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){


            //*************** Contact Name Content Resolver ***************
            ContentResolver contentResolver=context.getContentResolver();
            String[] projections={ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,projections,null,null,ContactsContract.Contacts.DISPLAY_NAME);
            String nameColumnIx=ContactsContract.Contacts.DISPLAY_NAME;
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex(nameColumnIx));
                userContactNameList.add(name);
            }
            cursor.close();

            //*************** Phone Content Resolver ***************
            String[] projections2={ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER};
            Cursor phoneCursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projections2,null,null,ContactsContract.Contacts.DISPLAY_NAME);

            while (phoneCursor.moveToNext()){
                String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                userContactPhoneNumberList.add(phoneNumber);
            }
            phoneCursor.close();

        }

        if (userContactNameList.size()==userContactPhoneNumberList.size()){

            for (int position=0; position<userContactNameList.size(); position++){
                String userContactName=userContactNameList.get(position);
                String userContactPhoneNumber=userContactPhoneNumberList.get(position);



                userContactsList.add(new UserContactModel(userContactName,userContactPhoneNumber));
            }
        }

        userContactNameLiveData.setValue(userContactsList);



    }

    private void checkPhoneNumberInFirebase(){

    }


}
