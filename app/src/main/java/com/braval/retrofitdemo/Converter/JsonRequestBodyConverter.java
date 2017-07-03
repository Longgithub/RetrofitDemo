package com.braval.retrofitdemo.Converter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by zhanglong on 2017/7/3.
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody>{
    private final MediaType MEDIA_TYPE= MediaType.parse("application/json;charset=utf-8");
    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE,value.toString());
    }
}
