package com.example.taskassignment.view;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskassignment.BasicUtality.BasicFunction;
import com.example.taskassignment.BasicUtality.Constant;
import com.example.taskassignment.R;
import com.example.taskassignment.databinding.DialogRegisterFragmentBinding;
import com.example.taskassignment.model.UserModel;
import com.example.taskassignment.view.extraClass.LocationTracker;
import com.example.taskassignment.viewModel.DialogViewModel;
import com.example.taskassignment.viewModel.DialogViewModelFactroy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private DialogRegisterFragmentBinding binding;
    private String title, userId;
    private UserModel userModel;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public void onRegister(String string) {
        this.title = string;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_register_fragment, null, false);
        binding.setTitle(title);
        userModel = new UserModel();
        userModel.setOtp(Constant.OTP);
        DialogViewModelFactroy dialogViewModelFactroy = new DialogViewModelFactroy(userModel, getContext());
        DialogViewModel viewModel = ViewModelProviders.of(this, dialogViewModelFactroy).get(DialogViewModel.class);
        binding.setViewModel(viewModel);
        binding.setUserModel(userModel);

        /**
         * This is for dismiss the dialog
         */
        binding.actvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /**
         * This is for opening the date picker dialog and getting the date
         */
        binding.dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthNew = month + 1;
                        userModel.setDateOfBirth(dayOfMonth + "/" + monthNew + "/" + year);
                        binding.acetDateofbirth.setText(userModel.getDateOfBirth());
                    }
                };
                BasicFunction.getDialoge(getContext(), onDateSetListener).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                BasicFunction.getDialoge(getContext(), onDateSetListener).show();
            }
        });

        /**
         * This is for getting the lat and log and convert into address
         */
        binding.location.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                LocationTracker locationTracker = new LocationTracker(getActivity());
                locationTracker.getAddressLiveDate().observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String address) {
                        userModel.setAddress(address);
                        binding.acetAddress.setText(userModel.getAddress());
                    }
                });
            }
        });

        /**
         * This is part where we are storing the user profile in firebase data base
         */
        viewModel.getIsValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    mFirebaseInstance = FirebaseDatabase.getInstance();
                    // get reference to 'users' node
                    mFirebaseDatabase = mFirebaseInstance.getReference("users");
                    // store app title to 'app_title' node
                    mFirebaseInstance.getReference("app_title").setValue("Answer & Question");
                    userId = mFirebaseDatabase.push().getKey();
                    mFirebaseDatabase.child(userId).setValue(userModel);
                    addUserChangeListener();
                }
            }
        });

        return binding.getRoot();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dismiss();
                userModel = dataSnapshot.getValue(UserModel.class);
                startActivity(BasicFunction.passValueObjectIntent(getActivity(), DashboardActivity.class, Constant.PASS_INTENT_OBJECT_VALUE, userModel));
                getActivity().finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
}
