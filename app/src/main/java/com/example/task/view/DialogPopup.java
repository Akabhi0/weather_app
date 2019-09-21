package com.example.task.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.task.R;
import com.example.task.databinding.AlertDialogBinding;

public class DialogPopup extends Dialog {

    private AlertDialogBinding binding;
    private String message;
    private Context context;

    public DialogPopup(@NonNull Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.alert_dialog,
                null, false);
        setContentView(binding.getRoot());

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.setTitle(message);
    }
}
