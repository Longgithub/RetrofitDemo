package com.braval.retrofitdemo.components;

import com.braval.retrofitdemo.activity.MainActivity;
import com.braval.retrofitdemo.moudles.MainMoudle;

import dagger.Component;

/**
 * Created by zhanglong on 2017/7/5.
 */
@Component(modules = MainMoudle.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
