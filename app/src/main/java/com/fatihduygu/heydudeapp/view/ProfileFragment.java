package com.fatihduygu.heydudeapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fatihduygu.heydudeapp.R;
import com.fatihduygu.heydudeapp.viewmodel.ProfileFragmentViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.time.LocalDateTime;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public static String profileImageUrl;
    @BindView(R.id.ProfileFragmentUserNameTxt)
    TextView userNameHeadTxt;

    @BindView(R.id.profile_fragment_username_txt)
    TextView userNameSubTxt;

    @BindView(R.id.profile_fragment_about_txt)
    TextView aboutTxt;

    @BindView(R.id.profile_fragment_user_phone_txt)
    TextView phoneNumberTxt;


    @BindView(R.id.profile_fragment_constraint_layout)
    ConstraintLayout profileFragmentConstraintLayout;

    @BindView(R.id.profile_fragment_progress_bar)
    ProgressBar profileFragmentProgressBar;

    @BindView(R.id.profile_fragment_log_out)
    Button logout;

    @BindView(R.id.user_profile_edit_btn)
    Button editProfile;

    @BindView(R.id.ProfileFragmentProfilePhotoImageView)
    ImageView userProfileImageView;

    @BindView(R.id.profile_fragment_user_image_loading)
    ProgressBar userProfileImageLoadingProgress;

    private ProfileFragmentViewModel profileFragmentViewModel;


    public static ProfileFragment newInstance() {

        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        ButterKnife.bind(this,view);
        logout.setOnClickListener(logOutOnClickListener);
        editProfile.setOnClickListener(editProfileOnClickListener);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileFragmentViewModel= ViewModelProviders.of(getActivity()).get(ProfileFragmentViewModel.class);
        profileFragmentViewModel.fetchUserInfo();
        observerViews();
    }

    private void observerViews() {
        profileFragmentViewModel.getUserInfo().observe(getViewLifecycleOwner(), stringObjectHashMap -> {

            String userName= (String) stringObjectHashMap.get("userName");
            String about= (String) stringObjectHashMap.get("about");
            String phoneNumber= (String) stringObjectHashMap.get("phoneNumber");
            profileImageUrl=(String) stringObjectHashMap.get("profileImageUrl");
            Transformation transformation=new RoundedTransformationBuilder().oval(true).build();
            Picasso.get().load(profileImageUrl).fit().centerCrop().transform(transformation).error(R.drawable.kangaroos).into(userProfileImageView);
            if (profileImageUrl==null){
                Picasso.get().load(profileImageUrl).placeholder(R.drawable.kangaroos).error(R.drawable.kangaroos).into(userProfileImageView);
            }




            userNameHeadTxt.setText(userName);
            userNameSubTxt.setText(userName);
            aboutTxt.setText(about);
            phoneNumberTxt.setText(phoneNumber);
            profileFragmentProgressBar.setVisibility(View.GONE);
            profileFragmentConstraintLayout.setVisibility(View.VISIBLE);
            userProfileImageLoadingProgress.setVisibility(View.GONE);

        });
    }

    Button.OnClickListener logOutOnClickListener= view -> {
        mAuth.signOut();
        Intent intent=new Intent(getContext(),SignInActivity.class);
        startActivity(intent);
        getActivity().finish();

    };

    Button.OnClickListener editProfileOnClickListener= view -> {

        String userName=userNameHeadTxt.getText().toString();
        String about= aboutTxt.getText().toString();
        String phoneNumber=phoneNumberTxt.getText().toString();

        Bundle bundle=new Bundle();
        bundle.putString("userName",userName);
        bundle.putString("about",about);
        bundle.putString("phoneNumber",phoneNumber);

        Intent intent=new Intent(getContext(),ProfileEditActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    };
}
