package android.santosh.com.ebcodechallenge;

import android.santosh.com.ebcodechallenge.controllers.MainController;

/**
 * Created by Santosh on 9/1/17.
 */

public class AppAPI {
    private MainController mainController;

    public AppAPI(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }
}
