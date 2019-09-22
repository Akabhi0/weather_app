package com.example.task.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.task.BasicUtality.BasicFunction;
import com.example.task.BasicUtality.Constant;
import com.example.task.R;
import com.example.task.databinding.AddressFragmentBinding;
import com.example.task.model.ForeCasteMain;
import com.example.task.model.Places;
import com.example.task.model.WeatherMain;
import com.example.task.viewModel.AddressScreenViewModel;
import com.example.task.viewModel.AddressScreenViewModelFactory;

public class AddressFragment extends Fragment {

    private AddressFragmentBinding binding;
    private AddressScreenViewModel viewModel;
    private AddressScreenViewModelFactory addressScreenViewModelFactory;
    private boolean networkCheck;
    private Places places;
    private int LAYOUT_VALUE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LAYOUT_VALUE = getArguments().getInt(Constant.INTENT_STARTING_SCREEN);
        switch (LAYOUT_VALUE) {
            case 11:
                binding = DataBindingUtil.inflate(inflater, R.layout.address_fragment, container, false);
                places = new Places();
                binding.setPlaces(places);
                addressScreenViewModelFactory = new AddressScreenViewModelFactory(getContext(), places);
                viewModel = ViewModelProviders.of(this, addressScreenViewModelFactory).get(AddressScreenViewModel.class);
                binding.setViewModel(viewModel);
                viewModel.getIsChecked().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            networkCheck = BasicFunction.isNetworkOnline(getActivity());

                            if (networkCheck) {
                                viewModel.getweather(places.getPlace());
                                viewModel.getWeatherData().observe(getActivity(), new Observer<WeatherMain>() {
                                    @Override
                                    public void onChanged(final WeatherMain weatherMain) {
                                        if (weatherMain != null) {

                                            viewModel.getForecaste(places.getPlace());
                                            viewModel.getForcasteData().observe(getActivity(), new Observer<ForeCasteMain>() {
                                                @Override
                                                public void onChanged(ForeCasteMain foreCaste) {
                                                    if (foreCaste != null) {
                                                        Intent intent = new Intent(getActivity(), ClimateShowActivity.class);
                                                        intent.putExtra(Constant.INTENT_CLIMATE_ADDRESS_SCREEN, Constant.INTENT_CLIMATE_ADDRESS_SCREEN_VALUE);
                                                        intent.putExtra(Constant.ADDRESS_TO_CLIMATE_WEATHER_KEY, weatherMain);
                                                        intent.putExtra(Constant.ADDRESS_TO_CLIMATE_FORECAST_KEY, foreCaste);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                DialogPopup dialogPopup = new DialogPopup(getActivity(),
                                        getResources().getString(R.string.network_message));
                                dialogPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialogPopup.setCancelable(true);
                                dialogPopup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialogPopup.show();
                            }


                        }
                    }
                });
                break;
            default:
                binding = DataBindingUtil.inflate(inflater, R.layout.address_fragment, container, false);
                binding.acivBackword.setVisibility(View.GONE);
                break;

        }
        return binding.getRoot();
    }
}
