package com.example.marti.amiclient.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Marti on 12/09/18.
 */

public class LocationAddress {

    Context context;
    double latitude,longitude;

    public LocationAddress(Context context, double latitude, double longitude){
        this.context=context;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getLocationAddress(){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size()>0) {
                String address = addresses.get(0).getAddressLine(0); //si address.size = 0 falla
                //String city = addresses.get(0).getLocality();
                return address;
            }else{ return "AddressNotFound";}
        } catch (IOException e) {
            e.printStackTrace();
            return "AddressNotFound";
        }

    }
}
