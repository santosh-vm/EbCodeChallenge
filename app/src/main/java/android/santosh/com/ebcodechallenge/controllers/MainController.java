package android.santosh.com.ebcodechallenge.controllers;

import android.content.Context;
import android.location.Geocoder;
import android.os.Handler;
import android.santosh.com.ebcodechallenge.listeners.MainControllerListener;
import android.santosh.com.ebcodechallenge.model.EarthQuake;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

/**
 * Created by Santosh on 9/1/17.
 */

public class MainController {
    private static String TAG = MainController.class.getSimpleName();

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

    public void notifyRedditPostListFetchSuccess(final List<EarthQuake> earthQuakeList) {
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

    public void notifyRedditPostListFetchFailure() {
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
