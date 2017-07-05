package com.braval.retrofitdemo.net.Converter;

import android.text.TextUtils;

import com.braval.retrofitdemo.HttpCallService;
import com.braval.retrofitdemo.HttpResponseCallback;
import com.braval.retrofitdemo.net.converter.JsonConverterFactory;
import com.braval.retrofitdemo.utils.CustomLogger;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 网络请求类
 * Created by zhanglong on 2016/9/27.
 */
public class HttpCall {
    private static volatile HttpCall sInstance;
    private HttpCallService mHttpCallService;
    private static HttpCallConfiguration mConfiguration;

    static {
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    private HttpCall(@Nullable String baseURL) {
        if (mConfiguration == null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加额外请求头
        if (mConfiguration.getAddtionalHeaders() != null) {
            Interceptor interceptorAddHeader = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder newRequestBuilder = chain.request().newBuilder();
                    Map<String, String> headers = mConfiguration.getAddtionalHeaders();
                    Set<String> keys = headers.keySet();
                    for (String key : keys) {
                        newRequestBuilder.addHeader(String.valueOf(key), String.valueOf(headers.get(key)));
                    }
                    Request newRequest = newRequestBuilder.build();
                    return chain.proceed(newRequest);
                }
            };
            builder.addInterceptor(interceptorAddHeader);
        }
        builder.connectTimeout(mConfiguration.getConnectionTimeout(), TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.writeTimeout(mConfiguration.getWriteTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(mConfiguration.getReadTimeout(), TimeUnit.SECONDS);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
//                CustomLogger.d("hostname = " + hostname);
                return true;
            }
        });

        // 添加https支持
        if (mConfiguration.getKeystoreStream() != null && mConfiguration.getKeyPassword() != null) {
            SSLSocketFactory sslSocketFactory = HttpsCertificates.getCertificates(
                    mConfiguration.getKeystoreStream(), mConfiguration.getKeyPassword());
            if (sslSocketFactory != null) {
                builder.sslSocketFactory(sslSocketFactory);
            }
        }
        boolean isHttpDebug = CustomLogger.LOG_ENABLED;
        if (isHttpDebug) {
            // add interceptor for http log
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(logger);
        }

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(TextUtils.isEmpty(baseURL)?mConfiguration.getServerURL():baseURL)
                // custom converter factory, the request values are key-value pairs, and the response is json object.
                .addConverterFactory(JsonConverterFactory.create())
                // add rxjava support
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mHttpCallService = mRetrofit.create(HttpCallService.class);
    }

    /**
     * 对HttpCall进行配置，需要在Application中进行调用，其中服务器地址是必须配置项。
     *
     * @param configuration 配置
     */
    public static void init(HttpCallConfiguration configuration) {
        mConfiguration = configuration;
    }

    /**
     * Get the single instance
     *
     * @return the single instance
     */
    public static HttpCall getInstance(@Nullable String baseURL) {
        if (mConfiguration == null) {
            throw new IllegalArgumentException("HttpCall is not be initialized!");
        }

        if (mConfiguration.getServerURL() == null) {
            throw new IllegalArgumentException("The server url is not be initialized!");
        }

        if (sInstance == null) {
            synchronized (HttpCall.class) {
                if (sInstance == null) {
                    sInstance = new HttpCall(baseURL);
                }
            }
        }
        return sInstance;
    }

    /**
     * The basic http post method
     *
     * @param api           The specific api
     * @param options       The request options
     * @param callback      The callback for response
     * @param subscriptions The composite subscription, used to record the request,
     *                      may using it to cancel  the request later.
     */
    private void postBasic(final String api,
                           JSONObject options,
                           final HttpResponseCallback callback,
                           final CompositeSubscription subscriptions) {
        /**
         * Add Common Param
         *
         */
//        try {
//            //在此对请求加密
//            options.put("version", UZoneApplication.versionName);
//            options.put("deviceId", UZoneUtils.getdevice_id(DataRepository.getContext()));
//            options.put("phoneOs", Build.VERSION.RELEASE);
//            options.put("phoneModel", Build.MODEL);
//            options.put("marketChannel", UZoneApplication.channel);
//            options.put("ip", DataRepository.getInstance().getLocalIp());
//            if (Consts.GATEWAY_ENABLED) {
//                options = encryptyRequestBody(options);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        Subscription subscription =
                mHttpCallService.post(api, options)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseSubscriber(api, options, callback, true, subscriptions));
        if (subscriptions != null) {
            subscriptions.add(subscription);
        }
    }

//    private JSONObject encryptyRequestBody(JSONObject requestBody) throws Exception {
//        //RSA加密
//        String encyptString = Base64.encode(RSAUtil.encryptByPublicKey(requestBody.toString().getBytes("UTF-8"), Consts.RSA_PUBLIC_KEY));
//        JSONObject object = new JSONObject();
//        try {
//            //在此对请求加密
//            object.put("merchantsNo", Consts.MERCHANT_NO);
//            object.put("sign", SignUtil.Sign(requestBody.toString(), "MD5", Consts.MD5_SIGN));
//            object.put("moduleName", Consts.MERCHANT_MOUDLE_NAME);
//            object.put("timestamp", System.currentTimeMillis());
//            object.put("data", encyptString);
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return object;
//    }

    /**
     * The http post method
     *
     * @param api      The specific api
     * @param options  The request options
     * @param callback The callback for response
     */
    public void post(final String api,
                     final JSONObject options,
                     final HttpResponseCallback callback,
                     final CompositeSubscription subscriptions) {
        postBasic(api, options, callback, subscriptions);
    }

    /**
     * The basic http get method
     *
     * @param api           The specific api
     * @param options       The request options
     * @param callback      The callback for response
     * @param subscriptions The composite subscription, used to record the request,
     *                      may using it to cancel  the request later.
     */
    private void getBasic(final String api,
                          final JSONObject options,
                          final HttpResponseCallback callback,
                          final CompositeSubscription subscriptions) {
        Subscription subscription =
                mHttpCallService.get(api, options)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseSubscriber(api, options, callback, false, subscriptions));
        if (subscriptions != null) {
            subscriptions.add(subscription);
        }
    }

    /**
     * The http get method
     *
     * @param api           The specific api
     * @param options       The request options
     * @param callback      The callback for response
     * @param subscriptions The composite subscription, used to record the request,
     *                      may using it to cancel  the request later.
     */
    public void get(final String api,
                    final JSONObject options,
                    final HttpResponseCallback callback,
                    final CompositeSubscription subscriptions) {
        getBasic(api, options, callback, subscriptions);
    }

    /**
     * The http response subscriber, parse the http response
     *
     * @param api           The specific api
     * @param options       The request options
     * @param callback      The callback for response
     * @param isPost        Is post or get method, true = post method, false = get method
     * @param subscriptions The composite subscription, used to record the request,
     *                      may using it to cancel  the request later.
     */
    private Subscriber<JSONObject> responseSubscriber(final String api,
                                                      final JSONObject options,
                                                      final HttpResponseCallback callback,
                                                      final boolean isPost,
                                                      final CompositeSubscription subscriptions) {
        return new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onFailure(e.getLocalizedMessage(), null);
                }
            }
            @Override
            public void onNext(JSONObject jsonObject) {
                CustomLogger.d("response: " + api + " : " + jsonObject.toString() + "\nparams" + options);
                try {
                    if (!HttpResponseAnalyzer.statusOk(jsonObject)) {
                        String error_code = HttpResponseAnalyzer.getErrorCode(jsonObject);
//                        if (HttpConstants.PERMISSON_DENIED.equals(error_code)) {
//                            // 用户登录超时或会话超时或用户在其他地方登陆
//                            callback.onHandleSessionTimeout(HttpConstants.PERMISSON_DENIED.equals(error_code));
//                            //超时,清空code
//                            DataRepository.getInstance().setCode("");
//                        } else {
//                            callback.onFailure(HttpResponseAnalyzer.getErrorMsg(jsonObject), jsonObject);
//                        }
                    } else {
                        // Success
                        callback.onSuccess(jsonObject);
                    }
                }catch (Throwable throwable){

                }
            }
        };
    }
}
