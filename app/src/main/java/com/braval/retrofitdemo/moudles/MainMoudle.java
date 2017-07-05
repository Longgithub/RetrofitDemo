package com.braval.retrofitdemo.moudles;

import com.braval.retrofitdemo.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhanglong on 2017/7/5.
 */
@Module
public class MainMoudle {
    private final MainContract.View mView;

    public MainMoudle(MainContract.View view){
        mView=view;
    }
    @Provides
    MainContract.View provideMainView(){
        return mView;
    }

}
