package android.santosh.com.ebcodechallenge.listeners;

import android.santosh.com.ebcodechallenge.model.EarthQuake;

import java.util.List;

/**
 * Created by Santosh on 9/1/17.
 */

public interface MainControllerListener {
    void onListFetchSuccess(List<EarthQuake> earthQuakeList);

    void onListFetchFailure();
}
