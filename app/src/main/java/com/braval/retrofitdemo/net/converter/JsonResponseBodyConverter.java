package com.braval.retrofitdemo.net.converter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhanglong on 2017/7/3.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    @Override
    public T convert(ResponseBody value) throws IOException {
        JSONObject result=null;
        try {
            result=new JSONObject(value.string());
            return (T)result;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
