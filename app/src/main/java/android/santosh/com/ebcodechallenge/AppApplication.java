package android.santosh.com.ebcodechallenge;

import android.app.Application;
import android.os.Handler;
import android.santosh.com.ebcodechallenge.controllers.MainController;

/**
 * Created by Santosh on 9/1/17.
 */

public class AppApplication extends Application {
    private static String TAG = AppApplication.class.getSimpleName();
    private AppAPI appAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        MainController mainController = new MainController(getApplicationContext(), new Handler());
        appAPI = new AppAPI(mainController);
    }

    public AppAPI getAppAPI() {
        return appAPI;
    }
}
