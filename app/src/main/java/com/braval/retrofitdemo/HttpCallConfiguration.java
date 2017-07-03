package com.braval.retrofitdemo;

import android.content.Context;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by zhanglong on 2016/9/27.
 */

public class HttpCallConfiguration {
    public static final int DEFAULT_TIMEOUT = 10;
    final Context mContext;
    final String mServerURL;
    final int mConnectionTimeout;
    final int mReadTimeout;
    final int mWriteTimeout;
    InputStream keystoreStream;
    String keyPassword;
    Map<String, String> mAddtionalHeaders;

    public Context getContext() {
        return mContext;
    }

    public String getServerURL() {
        return mServerURL;
    }

    public int getConnectionTimeout() {
        return mConnectionTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public int getWriteTimeout() {
        return mWriteTimeout;
    }

    public InputStream getKeystoreStream() {
        return keystoreStream;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public Map<String, String> getAddtionalHeaders() {
        return mAddtionalHeaders;
    }

    private HttpCallConfiguration(Builder builder) {
        this.mContext = builder.mContext;
        this.mServerURL = builder.mServerURL;
        this.mConnectionTimeout = builder.mConnectionTimeout;
        this.mReadTimeout = builder.mReadTimeout;
        this.mWriteTimeout = builder.mWriteTimeout;
        this.keystoreStream = builder.keystoreStream;
        this.keyPassword = builder.keyPassword;
        this.mAddtionalHeaders = builder.mAddtionalHeaders;
    }

    public static class Builder {
        Context mContext;
        String mServerURL;
        int mConnectionTimeout;
        int mReadTimeout;
        int mWriteTimeout;
        InputStream keystoreStream;
        String keyPassword;
        Map<String, String> mAddtionalHeaders;

        public Builder(Context mContext) {
            this.mContext = mContext;
            this.mServerURL = null;
            this.mConnectionTimeout = DEFAULT_TIMEOUT;
            this.mReadTimeout = DEFAULT_TIMEOUT;
            this.mWriteTimeout = DEFAULT_TIMEOUT;
            this.keystoreStream = null;
            this.mAddtionalHeaders = null;
            this.keyPassword = null;
        }


        public HttpCallConfiguration build() {
            return new HttpCallConfiguration(this);
        }

        public HttpCallConfiguration.Builder serverUrl(String url) {
            this.mServerURL = url;
            return this;
        }

        public HttpCallConfiguration.Builder connectionTimeout(int timeout) {
            this.mConnectionTimeout = timeout;
            return this;
        }

        public HttpCallConfiguration.Builder readTimeout(int timeout) {
            this.mReadTimeout = timeout;
            return this;
        }

        public HttpCallConfiguration.Builder writeTimeout(int timeout) {
            this.mWriteTimeout = timeout;
            return this;
        }

        public HttpCallConfiguration.Builder keystoreInputStream(InputStream inputStream) {
            this.keystoreStream = inputStream;
            return this;
        }

        public HttpCallConfiguration.Builder keystorePwd(String pwd) {
            this.keyPassword = pwd;
            return this;
        }

        public HttpCallConfiguration.Builder additionalHeaders(Map<String, String> headers) {
            this.mAddtionalHeaders = headers;
            return this;
        }

    }
}
