package com.braval.retrofitdemo.Converter;

import android.util.ArrayMap;

import com.braval.retrofitdemo.Consts;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by zhanglong on 2017/7/3.
 */

public class FormConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data; charset=utf-8");
    @Override
    public RequestBody convert(T value) throws IOException {
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
        multiBuilder.setType(MEDIA_TYPE);
        ArrayMap<String, String> converValue = (ArrayMap<String, String>) value;
        Set<String> keys = converValue.keySet();
        for (String key : keys) {
            String key_value = converValue.get(key);
            if (null != key_value) {
                if (key_value.contains(Consts.SPECIFIC_UPLOAD_IMG)) {
                    int index = key_value.indexOf(Consts.SPECIFIC_UPLOAD_IMG);
                    String pic_name = key_value.substring(0, index);
                    String file_path = key_value.substring(index + Consts.SPECIFIC_UPLOAD_IMG.length());

                    multiBuilder.addFormDataPart(key, pic_name, RequestBody.create(MediaType.parse("image/*"), new File(file_path)));
                } else {
                    multiBuilder.addFormDataPart(key, key_value);
                }
            }
        }
        return multiBuilder.build();
    }
}
