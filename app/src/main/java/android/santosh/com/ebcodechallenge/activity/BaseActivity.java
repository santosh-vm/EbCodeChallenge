package android.santosh.com.ebcodechallenge.activity;

import android.os.Bundle;
import android.santosh.com.ebcodechallenge.AppAPI;
import android.santosh.com.ebcodechallenge.AppApplication;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Santosh on 9/1/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected AppAPI appAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appAPI = ((AppApplication)getApplication()).getAppAPI();
    }
}
