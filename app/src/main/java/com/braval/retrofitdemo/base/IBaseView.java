package com.braval.retrofitdemo.base;

/**
 * 此处定义了页面行为，所有的View交互操作均应在此类中完成或者在其子类中完成
 */
public interface IBaseView {
    /**
     * 判断网络是否可用。
     *
     * @return 网络状态。
     */
    boolean isNetworkAvailable();
    /**
     * 显示提示框。
     *
     * @param msg 提示信息。
     */
    void showProgressDialog(String msg);

    /**
     * 隐藏提示框。
     */
    void hideProgressDialog();

    /**
     * 处理用户未登陆或超时。
     *
     * @param isNeedKillProcess 是否需要杀死当前进程。
     */
    void onHandleSessionTimeout(boolean isNeedKillProcess);

    /**
     * 显示信息
     *
     * @param msg   字符串
     * @param style 样式
     */
    void showHttpErrorMessage(String msg, int style);

    /**
     * 显示信息
     *
     * @param msg   字符串id
     * @param style 样式
     */
    void showHttpErrorMessage(int msg, int style);
}
