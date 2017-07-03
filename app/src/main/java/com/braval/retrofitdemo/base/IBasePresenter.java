package com.braval.retrofitdemo.base;

/**
 * 所有与页面交互无关的操作均在此类以及子类中完成
 */
public interface IBasePresenter {
    /**
     * 执行一些初始化的操作
     */
    void init();

    /**
     * 反初始化操作，主要用于撤销网络请求
     */
    void deInit();
}
