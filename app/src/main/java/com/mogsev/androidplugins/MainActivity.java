package com.mogsev.androidplugins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mogsev.androidplugins.adapter.HashingSpeedRVAdapter;
import com.mogsev.androidplugins.model.ProviderStatsOfBTCAddr;
import com.mogsev.androidplugins.model.StatsGlobalCurrent;
import com.mogsev.androidplugins.network.ApiNicehash;
import com.mogsev.androidplugins.widget.ViewPicker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private HashingSpeedRVAdapter mAdapter = new HashingSpeedRVAdapter();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        initViewPicker();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCompositeDisposable.add(ApiNicehash.API_NICEHASH.statsGlobalCurrent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

        mCompositeDisposable.add(ApiNicehash.API_NICEHASH.getProoviderStatsOfBTCAddr("1LXabKdQyAdfPUWDxdD7cNBaLoxLpNsWHx")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::log, this::handleError));

        mCompositeDisposable.add(ApiNicehash.API_NICEHASH.getProoviderStatsOfBTCAddr("1AguBgPDku98RRpV4DZTntEjcUjWaJTUxA")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::log, this::handleError));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void log(ProviderStatsOfBTCAddr item) {
        Log.i(TAG, item.toString());
    }

    private void handleResponse(StatsGlobalCurrent result) {
        mAdapter.setList(result.getResult().getStats());
    }

    private void handleError(Throwable error) {
        Log.e(TAG, error.getLocalizedMessage());
        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void initViewPicker() {
        ViewPicker viewPicker = (ViewPicker) findViewById(R.id.viewPicker);
        String[] months = new String[12];
        months[0] = "00";
        months[1] = "01";
        months[2] = "02";
        months[3] = "03";
        months[4] = "04";
        months[5] = "05";
        months[6] = "06";
        months[7] = "07";
        months[8] = "08";
        months[9] = "09";
        months[10] = "10";
        viewPicker.setValues(months);
    }
}
