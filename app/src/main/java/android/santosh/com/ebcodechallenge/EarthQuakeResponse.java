package android.santosh.com.ebcodechallenge;

import android.santosh.com.ebcodechallenge.model.EarthQuake;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Santosh on 9/1/17.
 */

public class EarthQuakeResponse {
    @SerializedName("earthquakes")
    private EarthQuake[] earthQuakes;

    public EarthQuake[] getEarthQuakes() {
        return earthQuakes;
    }
}
