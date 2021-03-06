package com.mogsev.androidplugins.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mogsev.androidplugins.model.DetailedStatsOfBTCAddr;
import com.mogsev.androidplugins.model.ProviderStatsOfBTCAddr;
import com.mogsev.androidplugins.model.StatsGlobalCurrent;

import io.reactivex.Observable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public interface ApiNicehash {
    public static final String BASE_URL = "https://www.nicehash.com";

    @GET("api?method=stats.global.current")
    Observable<StatsGlobalCurrent> statsGlobalCurrent();

    @GET("api?method=stats.provider")
    Observable<ProviderStatsOfBTCAddr> getProoviderStatsOfBTCAddr(@Query("addr") String addr);

    @GET("api?method=stats.provider.ex")
    Observable<DetailedStatsOfBTCAddr> getDetailedStatesOfBTCAddr(@Query("addr") String addr);

    public static ApiNicehash API_NICEHASH = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiNicehash.class);
}
