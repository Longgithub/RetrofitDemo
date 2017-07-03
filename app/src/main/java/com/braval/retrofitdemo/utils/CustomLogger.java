package com.braval.retrofitdemo.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * 封装Logger工具类
 * Created by zhanglong on 2016/9/27.
 */

public class CustomLogger {
    public static final boolean LOG_ENABLED=true;
    /**
     * It is better to add init at Application -> OnCreate()
     * @param tag
     */
    public static void init(String tag) {
        if (LOG_ENABLED) {
            Logger.init(tag).logLevel(LogLevel.FULL);
        }
        else {
            Logger.init(tag).logLevel(LogLevel.NONE);
        }
    }

    public static void clear() {
        Logger.clear();
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }
}
