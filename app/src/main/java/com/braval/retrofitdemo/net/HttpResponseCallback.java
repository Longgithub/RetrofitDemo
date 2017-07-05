package com.braval.retrofitdemo.net.Converter;

import org.json.JSONObject;

/**
 * Created by zhanglong on 2016/9/27.
 */

public interface HttpResponseCallback {
    void onSuccess(JSONObject jsonObject);

    void onFailure(String errorMsg, JSONObject jsonObject);

    void onHandleSessionTimeout(boolean killProcess);
}
