package com.braval.retrofitdemo;

import android.text.TextUtils;


import com.braval.retrofitdemo.base.IBaseView;
import com.braval.retrofitdemo.utils.NoticeUtils;

import org.json.JSONObject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhanglong on 2016/9/27.
 */

public class HttpDataModel {
    private static HttpDataModel sInstance;

    public static HttpDataModel getInstance() {
        if (sInstance == null) {
            synchronized (HttpDataModel.class) {
                if (sInstance == null) {
                    sInstance = new HttpDataModel();
                }
            }
        }
        return sInstance;
    }

    private HttpDataModel() {
    }

    /**
     * @param view
     * @param requestApi
     * @param options
     * @param subscription
     * @param isNeedProgressDialog
     * @param isNeedCode
     * @param msg
     * @param callback
     */
    public void post(final IBaseView view,
                     final String requestApi,
                     final JSONObject options,
                     final CompositeSubscription subscription,
                     final boolean isNeedProgressDialog,
                     final boolean isNeedCode,
                     final String msg,
                     final HttpResponseCallback callback) {

        if (view != null && !view.isNetworkAvailable()) {
            return;
        }
        if (view != null && isNeedProgressDialog) {
            view.showProgressDialog(msg);
        }
        try {
//            if (isNeedCode) {
//                if (options != null) {
//                    options.put(HttpConstants.CODE, TextUtils.isEmpty(DataRepository.getInstance().getCode()) ? "" : DataRepository.getInstance().getCode());
//                }
//            }
            HttpCall.getInstance(null).post(requestApi, options,
                    new HttpResponseCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onSuccess(jsonObject);
                            }
                        }

                        @Override
                        public void onFailure(String errorMsg, JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }
                            if (callback != null) {
                                callback.onFailure(errorMsg, jsonObject);
                            }
                        }

                        @Override
                        public void onHandleSessionTimeout(boolean isNeedKillProcess) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }
                            if (view != null) {
                                view.onHandleSessionTimeout(isNeedKillProcess);
                            }
                        }
                    }, subscription);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 为不同产品的地址而配置
     */
    public void post(final String baseURL,
                     final IBaseView view,
                     final String requestApi,
                     final JSONObject options,
                     final CompositeSubscription subscription,
                     final boolean isNeedProgressDialog,
                     final boolean isNeedCode,
                     final String msg,
                     final HttpResponseCallback callback) {

        if (view != null && !view.isNetworkAvailable()) {
            return;
        }
        if (view != null && isNeedProgressDialog) {
            view.showProgressDialog(msg);
        }
        try {
//            if (isNeedCode) {
//                if (options != null) {
//                    options.put(HttpConstants.CODE, TextUtils.isEmpty(DataRepository.getInstance().getCode()) ? "" : DataRepository.getInstance().getCode());
//                }
//            }
            HttpCall.getInstance(baseURL).post(requestApi, options,
                    new HttpResponseCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onSuccess(jsonObject);
                            }
                        }

                        @Override
                        public void onFailure(String errorMsg, JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }
                            if (callback != null) {
                                callback.onFailure(errorMsg, jsonObject);
                            }
                        }

                        @Override
                        public void onHandleSessionTimeout(boolean isNeedKillProcess) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }
                            if (view != null) {
                                view.onHandleSessionTimeout(isNeedKillProcess);
                            }
                        }
                    }, subscription);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param view
     * @param requestApi
     * @param options
     * @param subscription
     * @param isNeedProgressDialog
     * @param isNeedCode
     * @param msg
     * @param callback
     */
    public void get(final IBaseView view,
                    final String requestApi,
                    final JSONObject options,
                    final CompositeSubscription subscription,
                    final boolean isNeedProgressDialog,
                    final boolean isNeedCode,
                    final String msg,
                    final ISuccess callback) {
        if (view != null && !view.isNetworkAvailable()) {
            return;
        }

        if (view != null && isNeedProgressDialog) {
            view.showProgressDialog(msg);
        }
        try {
//            if (isNeedCode) {
//                if (options != null && !TextUtils.isEmpty(DataRepository.getInstance().getCode())) {
//                    options.put(HttpConstants.CODE, DataRepository.getInstance().getCode());
//                }
//            }
            HttpCall.getInstance(null).get(requestApi, options,
                    new HttpResponseCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onSuccess(jsonObject);
                            }
                        }

                        @Override
                        public void onFailure(String errorMsg, JSONObject jsonObject) {
                            if (view != null) {
                                if (isNeedProgressDialog) {
                                    view.hideProgressDialog();
                                }
                                view.showHttpErrorMessage(errorMsg, NoticeUtils.STYLE_ALERT);
                            }
                        }

                        @Override
                        public void onHandleSessionTimeout(boolean isNeedKillProcess) {
                            if (view != null) {
                                if (isNeedProgressDialog) {
                                    view.hideProgressDialog();
                                }
                                view.onHandleSessionTimeout(isNeedKillProcess);
                            }
                        }
                    }, subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param view
     * @param requestApi
     * @param options
     * @param subscription
     * @param isNeedProgressDialog
     * @param isNeedCode
     * @param msg
     * @param callback
     */
    public void get(final IBaseView view,
                    final String requestApi,
                    final JSONObject options,
                    final CompositeSubscription subscription,
                    final boolean isNeedProgressDialog,
                    final boolean isNeedCode,
                    final String msg,
                    final HttpResponseCallback callback) {
        if (view != null && !view.isNetworkAvailable()) {
            return;
        }

        if (view != null && isNeedProgressDialog) {
            view.showProgressDialog(msg);
        }
        try {
//            if (isNeedCode) {
//                if (options != null && !TextUtils.isEmpty(DataRepository.getInstance().getCode())) {
//                    options.put(HttpConstants.CODE, DataRepository.getInstance().getCode());
//                }
//            }
            HttpCall.getInstance(null).get(requestApi, options,
                    new HttpResponseCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onSuccess(jsonObject);
                            }
                        }

                        @Override
                        public void onFailure(String errorMsg, JSONObject jsonObject) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onFailure(errorMsg, jsonObject);
                            }
                        }

                        @Override
                        public void onHandleSessionTimeout(boolean isNeedKillProcess) {
                            if (view != null && isNeedProgressDialog) {
                                view.hideProgressDialog();
                            }

                            if (callback != null) {
                                callback.onHandleSessionTimeout(isNeedKillProcess);
                            }
                        }
                    }, subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public interface ISuccess {
        void onSuccess(JSONObject jsonObject);
    }
}
