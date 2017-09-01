package android.santosh.com.ebcodechallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Santosh on 8/31/17.
 */

public class EarthQuake {
    @SerializedName("lat")
    private long latitude;

    @SerializedName("lng")
    private long longitude;

    @SerializedName("magnitude")
    private float magnitude;

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
