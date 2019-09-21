package com.example.task.viewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.task.model.Places;

public class AddressScreenViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Places places;
    private Context context;

    public AddressScreenViewModelFactory(Context context, Places places) {
        this.places = places;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddressScreenViewModel(places, context);
    }
}
