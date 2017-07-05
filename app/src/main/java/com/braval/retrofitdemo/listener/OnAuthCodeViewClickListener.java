package com.braval.retrofitdemo.listener;

import android.view.View;

public interface OnAuthCodeViewClickListener {
    /**
     * 点击发送短信验证码
     *
     * @param view
     */
    void onAuthSmsCodeClick(View view);

    /**
     * 点击发送语音验证码
     *
     * @param view
     */
    void onAuthVoiceCodeClick(View view);

    /**
     * 倒计时开始
     */
    void onCountDownStart();

    /**
     * 倒计时结束
     */

    void onCountDownFinish();
}
