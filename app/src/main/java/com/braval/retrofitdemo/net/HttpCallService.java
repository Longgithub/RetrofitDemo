package com.braval.retrofitdemo.net.Converter;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by zhanglong on 2016/9/27.
 */

public interface HttpCallService {
    @GET("{api}")
    Observable<JSONObject> get(@Path(value = "api", encoded = true) String api, @QueryMap JSONObject options);

    @POST("{api}")
    Observable<JSONObject> post(@Path(value = "api", encoded = true) String api, @Body JSONObject options);


    /**
     * For file Upload
     * @param params
     * @param parts
     * @return
     */
//    @Multipart
//    @POST(HttpConstants.SERVER_IP + "/" + ApiConstants.API_UPLOADP_AVATAR)
//    Call<JSONObject> uploadAvatar(@PartMap Map<String, RequestBody> params,
//                                  @Part List<MultipartBody.Part> parts);
//
//    @Multipart
//    @POST(HttpConstants.SERVER_IP + "/" + ApiConstants.API_UPLOAD_IDCARD)
//    Call<JSONObject> uploadIDCard(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> parts);
//
//    @Multipart
//    @POST(HttpConstants.SERVER_IP + "/" + ApiConstants.API_OTHER_SUGGEST)
//    Call<JSONObject> feedBack(@PartMap Map<String, RequestBody> param, @Part List<MultipartBody.Part> parts);
//
//
//    @Multipart
//    @POST(HttpConstants.SERVER_IP + "/" + ApiConstants.API_OTHER_SUGGEST)
//    Call<JSONObject> feedBackWithoutPic(@PartMap Map<String, RequestBody> param);

    /**
     * 返回本机IP
     * @return
     */
    @GET("https://api.ipify.org/")
    Call<String> getIPOnline();
}
