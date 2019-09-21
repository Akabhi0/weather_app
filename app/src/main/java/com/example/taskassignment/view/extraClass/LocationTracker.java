package com.example.taskassignment.view.extraClass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationTracker implements LocationListener {

    private LocationManager locationManager;
    private MutableLiveData<String> addressLiveDate;
    private Activity activity;

    public MutableLiveData<String> getAddressLiveDate() {
        return addressLiveDate;
    }

    public void setAddressLiveDate(String addressLiveDate) {
        this.addressLiveDate.setValue(addressLiveDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public LocationTracker(FragmentActivity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            List<Address> getAddress = null;
            try {
                addressLiveDate = new MutableLiveData<>();
                getAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (getAddress != null) {
                    String address = getAddress.get(0).getAddressLine(0) + " "
                            + getAddress.get(0).getLocality();
                    setAddressLiveDate(address);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
