package com.example.taskassignment.viewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskassignment.model.UserModel;

public class DialogViewModelFactroy extends ViewModelProvider.NewInstanceFactory {

    private UserModel userModel;
    private Context context;

    public DialogViewModelFactroy(UserModel userModel, Context context) {
        this.userModel = userModel;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DialogViewModel(context, userModel);
    }
}
