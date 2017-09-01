package android.santosh.com.ebcodechallenge.controllers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.santosh.com.ebcodechallenge.EarthQuakeResponse;
import android.santosh.com.ebcodechallenge.listeners.MainControllerListener;
import android.santosh.com.ebcodechallenge.model.EarthQuake;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Santosh on 9/1/17.
 */

public class MainController {
    private static String TAG = MainController.class.getSimpleName();
    private static String EARTH_QUAKE_LIST_URL = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";

    private Handler uiHandler;
    private ExecutorService executorService;
    private OkHttpClient client;
    private Gson gson;
    private List<MainControllerListener> mainControllerListeners = Collections.synchronizedList(new ArrayList<MainControllerListener>());
    private List<EarthQuake> earthQuakeList = new ArrayList<>();
    private Context context;
    private Geocoder geocoder;

    public MainController(Context context, Handler uiHandler) {
        this.executorService = Executors.newSingleThreadExecutor();
        this.uiHandler = uiHandler;
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder().create();
        this.context = context;
        this.geocoder = new Geocoder(context, Locale.getDefault());
    }

    public void fetchList() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (earthQuakeList != null && earthQuakeList.size() > 0) {
                        //Caching in memory to avoid repeated network request.
                        notifyListFetchSuccess(earthQuakeList);
                    } else {
                        try {
                            Request request = new Request.Builder()
                                    .url(EARTH_QUAKE_LIST_URL)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String stringyfiedJson = new String(response.body().bytes(), "UTF-8");
                            switch (response.code()) {
                                case 200:
                                    EarthQuakeResponse earthQuakeResponse = gson.fromJson(stringyfiedJson, EarthQuakeResponse.class);
                                    //Log.d(TAG, "fetchList() earthQuakeResponse.getEarthQuakes().length: " + earthQuakeResponse.getEarthQuakes().length);
                                    earthQuakeList = Arrays.asList(earthQuakeResponse.getEarthQuakes());
                                    for (EarthQuake earthQuake : earthQuakeList) {
                                        List<Address> addresses = geocoder.getFromLocation(earthQuake.getLatitude(), earthQuake.getLongitude(), 1);
                                        if (addresses != null && addresses.size() > 0) {
                                            earthQuake.setAddresses(addresses);
                                        } else {
                                            earthQuake.setAddresses(null);
                                        }
                                    }
                                    notifyListFetchSuccess(earthQuakeList);
                                    break;
                                default:
                                    notifyListFetchFailure();
                                    break;
                            }
                        } catch (IOException iex) {
                            Log.e(TAG, "fetchList() IOException iex:" + iex);
                            notifyListFetchFailure();
                        }
                    }
                }
            });
        } else {
            notifyListFetchFailure();
        }
    }


    public void addMainControllerListener(MainControllerListener mainControllerListener) {
        if (mainControllerListeners != null && !mainControllerListeners.contains(mainControllerListener)) {
            mainControllerListeners.add(mainControllerListener);
        }
    }

    public void removeMainControllerListener(MainControllerListener mainControllerListener) {
        if (mainControllerListeners != null && mainControllerListeners.contains(mainControllerListener)) {
            mainControllerListeners.add(mainControllerListener);
        }
    }

    private void notifyListFetchSuccess(final List<EarthQuake> earthQuakeList) {
        if (mainControllerListeners != null && mainControllerListeners.size() > 0) {
            for (final MainControllerListener mainControllerListener : mainControllerListeners) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainControllerListener.onListFetchSuccess(earthQuakeList);
                    }
                });
            }
        }
    }

    private void notifyListFetchFailure() {
        if (mainControllerListeners != null && mainControllerListeners.size() > 0) {
            for (final MainControllerListener mainControllerListener : mainControllerListeners) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainControllerListener.onListFetchFailure();
                    }
                });
            }
        }
    }

}
