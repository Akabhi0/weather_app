package com.example.taskassignment.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivtyViewModel extends ViewModel {

    private MutableLiveData<Boolean> onClickRegisterButton = new MutableLiveData<>();

    public MutableLiveData<Boolean> getOnClickLogin() {
        return onClickLogin;
    }

    public void setOnClickLogin(Boolean onClickLogin) {
        this.onClickLogin.setValue(onClickLogin);
    }

    MutableLiveData<Boolean> onClickLogin = new MutableLiveData<>();

    public MutableLiveData<Boolean> getOnClickRegisterButton() {
        return onClickRegisterButton;
    }

    public void setOnClickRegisterButton(Boolean onClickRegisterButton) {
        this.onClickRegisterButton.setValue(onClickRegisterButton);
    }

    public void setOnClickRegisterButton() {
        setOnClickRegisterButton(true);
    }

    public void setOnClickLoginButton() {
        setOnClickLogin(true);
    }
}
