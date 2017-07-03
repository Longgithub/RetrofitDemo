package com.braval.retrofitdemo.utils;

import android.util.Log;

import com.mobanker.eagleeye.utils.EagleEyeAbstractLocator;
import com.mobanker.uzone.ui.UZoneApplication;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

/**
 * 鹰眼中定位
 * Author: bianhaipeng
 * Date:   2016/11/29
 */

public class EagleEyeLocator extends EagleEyeAbstractLocator implements TencentLocationListener {
    private TencentLocationRequest mLocationRequest;
    private TencentLocationManager mLocationManager;
    private boolean mIsLocationUpdated = false;

    public EagleEyeLocator() {
        initTencentLocationParam();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        Log.d("LocationStatus",reason);
        if (!mIsLocationUpdated && TencentLocation.ERROR_OK == error) {
            mIsLocationUpdated = true;
            // 定位成功
            try {
                String lat = String.valueOf(tencentLocation.getLatitude());
                String lon = String.valueOf(tencentLocation.getLongitude());
                CustomLogger.d("LocationStatus",lat + ", " + lon + ", " + tencentLocation.getProvince() + ", " +
                        tencentLocation.getCity() + ", " + tencentLocation.getDistrict());
                setLocationInfo(tencentLocation.getLongitude(), tencentLocation.getLatitude(),
                        tencentLocation.getProvince(), tencentLocation.getCity(),
                        tencentLocation.getDistrict());
            } catch (Exception e) {
                e.printStackTrace();
            }
            removeLbsListener();
        }else{
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    /**
     * Remove the listener.
     */
    public void removeLbsListener() {
        mLocationManager.removeUpdates(this);
    }

    /**
     * Init the params.
     */
    void initTencentLocationParam() {
        mLocationManager = TencentLocationManager.getInstance(UZoneApplication.getInstance());
        // 定位服务
        mLocationRequest = TencentLocationRequest.create();
        // set请求周期
        mLocationRequest.setInterval(10000);
        // 设置定位的 request level
        mLocationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        mLocationRequest.setAllowCache(true);
        int error=mLocationManager.requestLocationUpdates(mLocationRequest, this);
    }
}
