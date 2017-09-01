package android.santosh.com.ebcodechallenge.activity;

import android.santosh.com.ebcodechallenge.R;
import android.santosh.com.ebcodechallenge.adapters.RecyclerViewAdapter;
import android.santosh.com.ebcodechallenge.listeners.MainControllerListener;
import android.santosh.com.ebcodechallenge.model.EarthQuake;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends BaseActivity implements MainControllerListener {
    private static String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private TextView errorMessageTextView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appAPI.getMainController().addMainControllerListener(this);
        bindUIElements();
        appAPI.getMainController().fetchList();
    }

    private void bindUIElements() {
        //Progress Bar
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //TextView
        errorMessageTextView = (TextView) findViewById(R.id.error_message_text_view);
        errorMessageTextView.setVisibility(View.GONE);

        //RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager recyclerViewLinearLayoutManger = new LinearLayoutManager(this);
        recyclerViewLinearLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLinearLayoutManger);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appAPI.getMainController().removeMainControllerListener(this);
    }

    @Override
    public void onListFetchSuccess(List<EarthQuake> earthQuakeList) {
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewAdapter.swapList(earthQuakeList);
    }

    @Override
    public void onListFetchFailure() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }
}
