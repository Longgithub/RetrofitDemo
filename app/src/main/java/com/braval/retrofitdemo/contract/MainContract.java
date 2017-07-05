package com.braval.retrofitdemo.contract;

import com.braval.retrofitdemo.base.IBasePresenter;
import com.braval.retrofitdemo.base.IBaseView;

/**
 * Created by zhanglong on 2017/7/5.
 */

public interface MainContract  {
    interface View extends IBaseView{
        void onUpdateUI(String ip);
    }
    interface Presenter extends IBasePresenter{
        void showIp();
    }
}
