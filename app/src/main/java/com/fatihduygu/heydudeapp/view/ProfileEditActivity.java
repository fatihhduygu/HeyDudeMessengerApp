package com.fatihduygu.heydudeapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fatihduygu.heydudeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileEditActivity extends AppCompatActivity {
    private static final int STORAGE_REQUEST_CODE=101;
    private static final int GALLERY_REQUEST_CODE=102;
    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    //Firebase Realtime Database variables
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //Firebase Firestore variables
    private FirebaseFirestore db;

    //Firebase Storage variables
    private StorageReference storageReference;

    //Firebase Authentication variables
    private FirebaseAuth mAuth;

    @BindView(R.id.profile_edit_activity_profile_photo_image_view)
    ImageView userProfileImageView;

    @BindView(R.id.profile_edit_activity_username_edit_txt)
    EditText userName;

    @BindView(R.id.profile_edit_activity_about_edit_txt)
    EditText about;

    @BindView(R.id.profile_edit_activity_user_phone_txt)
    TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#B5A6A6\">Edit Profile</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        //*****Firebase variables Initialization*****
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();
        db=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        //*****Firebase variables Initialization*****
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        userName.setText(bundle.getString("userName"));
        about.setText(bundle.getString("about"));
        phoneNumber.setText(bundle.getString("phoneNumber"));
        Transformation transformation=new RoundedTransformationBuilder().oval(true).build();
        Picasso.get().load(ProfileFragment.profileImageUrl).fit().centerCrop().transform(transformation).error(R.drawable.kangaroos).into(userProfileImageView);
        if (ProfileFragment.profileImageUrl==null){
            Picasso.get().load(ProfileFragment.profileImageUrl).placeholder(R.drawable.logo1).error(R.drawable.logo1).into(userProfileImageView);
        }

    }


    public void selectPicture(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
        }else {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,GALLERY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==STORAGE_REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            selectedImageUri=data.getData();
            try {
                selectedImageBitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                userProfileImageView.setBackground(null);
                userProfileImageView.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void upload(View view) {
        UUID randomId=UUID.randomUUID();
        String imageId="images/"+randomId+".jpg";
        StorageReference newReference=storageReference.child(imageId);
        
        if (selectedImageBitmap==null){
            Toast.makeText(this, "Please select profile image", Toast.LENGTH_SHORT).show();
            return;
        }
        
        newReference.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    StorageReference profileImageRef=FirebaseStorage.getInstance().getReference(imageId);
                    profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUri=uri.toString();
                        addDataToFirestore(downloadUri);
                        addDataToRealtimeDatabase(downloadUri);
                        Intent intent=new Intent(this,FeedActivity.class);
                        startActivity(intent);
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(ProfileEditActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }


    private void addDataToRealtimeDatabase(String imageDownloadUri){
        String userUid=mAuth.getCurrentUser().getUid();
        DatabaseReference myRef=database.getReference();
        myRef.child("Users").child(userUid).child("userName").setValue(userName.getText().toString());
        myRef.child("Users").child(userUid).child("about").setValue(about.getText().toString());
        myRef.child("Users").child(userUid).child("profileImageUrl").setValue(imageDownloadUri);

    }

    private void addDataToFirestore(String imageDownloadUri){
        String userUid=mAuth.getCurrentUser().getUid();
        HashMap<String,Object> user=new HashMap<>();
        user.put("userName",userName.getText().toString());
        user.put("about",about.getText().toString());
        user.put("phoneNumber",mAuth.getCurrentUser().getPhoneNumber());
        user.put("profileImageUrl",imageDownloadUri);

        db.collection("Users")
                .document(userUid)
                .set(user)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> {
                });



    }
}
