package android.santosh.com.ebcodechallenge.model;

import android.location.Address;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Santosh on 8/31/17.
 */

public class EarthQuake {
    @SerializedName("lat")
    private double latitude;

    @SerializedName("lng")
    private double longitude;

    @SerializedName("magnitude")
    private float magnitude;

    private List<Address> addresses;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
