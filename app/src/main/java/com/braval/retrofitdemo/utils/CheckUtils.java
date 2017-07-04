package com.braval.retrofitdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanglong on 2016/9/27.
 */

public class CheckUtils {
    /**
     * @param phone Phone Number
     * @return whether the Number is under rule
     */
    public static boolean isAvailableMobilePhone(final String phone) {
        if (isEmptyString(phone))
            return false;
//        if (phone.length() != Consts.MobilePhoneLength)
//            return false;
        if (StringFilter_phone(phone)) {
            return false;
        }
        final String two = phone.substring(0, 2);
        final String three = phone.substring(0, 3);
        return !(!two.equals("13") && !two.equals("15") &&
                !two.equals("18") && !three.equals("145") &&
                !two.equals("17") &&
                !three.equals("147") && !three.equals("120") &&
                !three.equals("170") && !three.equals("177") &&
                !three.equals("171") && !three.equals("173") &&
                !three.equals("175") && !three.equals("149") &&
                !three.equals("178") && !three.equals("176"));
    }

    /**
     * // 清除掉所有特殊字符
     *
     * @param str
     * @return
     */
    public static boolean StringFilter_phone(String str) {

        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#¥%……&*（）——+|{}【】‘；：”“’。，、？_-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


    public static boolean isEmptyString(final String str) {
        return
                TextUtils.isEmpty(str) ||
                        str.equalsIgnoreCase("null");

    }


    public static boolean isNetworkValid(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (null != info) {
                for (NetworkInfo temp : info) {
                    if (temp.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
