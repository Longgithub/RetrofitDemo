package com.braval.retrofitdemo.net.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhanglong on 2017/2/14.
 */

public class StringResponseConverter<T> implements Converter<ResponseBody, T> {
    @Override
    public T convert(ResponseBody value) throws IOException {
        return (T)value.string();
    }
}
