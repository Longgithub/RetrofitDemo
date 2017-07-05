package com.braval.retrofitdemo.presenter;

import android.util.Log;

import com.braval.retrofitdemo.contract.MainContract;
import com.braval.retrofitdemo.net.HttpCallService;
import com.braval.retrofitdemo.net.converter.StringConverterFactory;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhanglong on 2017/7/5.
 */

public class MainPresenter implements MainContract.Presenter{
    private MainContract.View mView;
    CompositeSubscription compositeSubscription;
    @Inject
    MainPresenter(MainContract.View view){
        mView=view;
    }

    @Override
    public void init() {

    }

    @Override
    public void deInit() {
         compositeSubscription.unsubscribe();
    }

    @Override
    public void showIp() {
        Log.d("response","here");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.ipify.org/").addConverterFactory(StringConverterFactory.create()).build();
        HttpCallService service = retrofit.create(HttpCallService.class);
        Call<String> call = service.getIPOnline();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response",response.body());
                mView.onUpdateUI(response.body());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("response",call.toString());
            }
        });

    }
}
