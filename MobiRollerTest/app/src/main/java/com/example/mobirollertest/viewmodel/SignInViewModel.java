package com.example.mobirollertest.viewmodel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mobirollertest.model.SignInUser;
import com.example.mobirollertest.repository.SignInRepository;
import com.google.firebase.auth.AuthCredential;


public class SignInViewModel extends AndroidViewModel {
    private SignInRepository repository;
    public LiveData<SignInUser>checkAuthenticateLiveData;
    public LiveData<SignInUser> collectUserInfoLiveData;
    public  LiveData<String>authenticationLiveData;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        repository=new SignInRepository();
    }
    //collect user info  from authentication..........
    public void collectUserInfo(){
        collectUserInfoLiveData= repository.collectUserData();
    }
    public void signInWithGoogle(AuthCredential authCredential)
    {
        authenticationLiveData=repository.firebaseSignInWithGoogle(authCredential);
    }


    public void checkAuthenticate()
    {
        checkAuthenticateLiveData=repository.checkAuthenticationInFirebase();
    }
}
