package com.fatihduygu.heydudeapp.viewmodel;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneViewModel extends ViewModel {
    //Firebase variable
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    //Other Variables
    private String verificationId;

    //Live Data
    private MutableLiveData<CharSequence> comingCode=new MutableLiveData<>();
    private MutableLiveData<CharSequence> errorMessage=new MutableLiveData<>();
    private MutableLiveData<Boolean> missionComplete=new MutableLiveData<>();


    public void sendPhoneNumber(String number){
        sendVerificationCode(number);
    }

    public void verifyCodeManual(String code){
        verifyCode(code);
    }

    public LiveData<CharSequence> getComingCode(){
        return comingCode;
    }

    public LiveData<CharSequence> getErrorMessage(){
        return errorMessage;
    }

    public LiveData<Boolean> getMissionComplete(){
        return missionComplete;
    }




    //******************************* Receive Verification Code *******************************
    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null){
                comingCode.setValue(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            errorMessage.setValue(e.getLocalizedMessage());
        }
    };



    //******************************* Sign in with phone number *******************************
    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        missionComplete.setValue(true);
                    }
                    else{
                        errorMessage.setValue(task.getException().getLocalizedMessage());
                        missionComplete.setValue(false);
                    }
                });
    }
}
