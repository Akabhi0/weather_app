package com.example.taskassignment.viewModel;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskassignment.BasicUtality.BasicFunction;
import com.example.taskassignment.model.UserModel;

public class DialogViewModel extends ViewModel {
    private UserModel userModel;
    private Context context;

    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();

    public DialogViewModel(Context context, UserModel userModel) {
        this.context = context;
        this.userModel = userModel;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid.setValue(isValid);
    }

    public void onClickRegister() {
        if (TextUtils.isEmpty(userModel.getName())) {
            BasicFunction.getToastMessage("Enter name", context);
        } else if (TextUtils.isEmpty(userModel.getEmail())) {
            BasicFunction.getToastMessage("Enter email", context);
        } else if (TextUtils.isEmpty(userModel.getMobileNumber())) {
            BasicFunction.getToastMessage("Enter mobile", context);
        } else if (TextUtils.isEmpty(userModel.getDateOfBirth())) {
            BasicFunction.getToastMessage("Enter date of birth", context);
        } else if (TextUtils.isEmpty(userModel.getAddress())) {
            BasicFunction.getToastMessage("Enter address", context);
        } else {
            setIsValid(true);
        }
    }
}
