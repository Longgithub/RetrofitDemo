package com.braval.retrofitdemo.net;

import com.braval.retrofitdemo.consts.NetConsts;

import org.json.JSONObject;

/**
 * 返回报文的状态码
 */

public class HttpResponseAnalyzer {
    /**
     * Get the status
     * @param jsonObject The json data
     * @return The status
     */
    public static boolean statusOk(JSONObject jsonObject) {
        int status = jsonObject.optInt(NetConsts.RESULTKEY_STATUS, 0);
        return 1==status;
    }

    /**
     * Get the error code
     * @param jsonObject The json data
     * @return The code
     */
    public static String getErrorCode(JSONObject jsonObject) {
        return jsonObject.optString(NetConsts.RESULTKEY_ERROR, "");
    }

    /**
     * Get the error message
     * @param jsonObject The json data
     * @return The error message
     */
    public static String getErrorMsg(JSONObject jsonObject) {
        return jsonObject.optString(NetConsts.RESULTKEY_MSG, "");
    }
}
