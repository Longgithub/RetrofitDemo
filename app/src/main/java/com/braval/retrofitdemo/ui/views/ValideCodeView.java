package com.braval.retrofitdemo.ui.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.braval.retrofitdemo.R;
import com.braval.retrofitdemo.listener.OnAuthCodeViewClickListener;


public class ValideCodeView extends RelativeLayout {
    private final static long Long = 61 * 1000;
    private final static long Interval = 1000;
    private AuthCodeCountDown mCountDown = null;
    private OnAuthCodeViewClickListener mListener = null;
    private boolean mAvailableCount = false;
    //获取短信验证码View
    TextView sms_code_view;
    //收不到短信验证码View
    RelativeLayout voice_code_view;
    //倒计时
    TextView countdown_text;
    //发送语音验证码
    TextView send_voice_code;

    public void setVoiceEnable(boolean voiceEnable) {
        send_voice_code.setEnabled(voiceEnable);
        send_voice_code.setTextColor(getResources().getColor(R.color.defalut_gray_text_color));
    }

    public ValideCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.valide_code_layout, this);
        sms_code_view = (TextView) findViewById(R.id.sms_code_view);
        voice_code_view = (RelativeLayout) findViewById(R.id.voice_code_view);
        send_voice_code = (TextView) findViewById(R.id.send_voice_code);
        countdown_text = (TextView) findViewById(R.id.countdown_text);
        sms_code_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAuthSmsCodeClick(v);
                    if (mAvailableCount) {
                        if (mCountDown == null)
                            mCountDown = new AuthCodeCountDown(Long, Interval);
                        mCountDown.startCount();
                    }
                }
            }
        });
        send_voice_code.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAuthVoiceCodeClick(v);
                }
            }
        });
    }

    public ValideCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ValideCodeView(Context context) {
        super(context);
        this.init();
    }

    public void enableCount(boolean enable) {
        this.mAvailableCount = enable;
    }

    public void setOnAuthCodeButtonClickListener(OnAuthCodeViewClickListener l) {
        this.mListener = l;
    }

    public void reset() {
        if (mCountDown != null)
            mCountDown.cancel();
        sms_code_view.setVisibility(VISIBLE);
        voice_code_view.setVisibility(GONE);
    }

    class AuthCodeCountDown extends CountDownTimer {
        public AuthCodeCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            //To change body of implemented methods use File | Settings | File Templates.
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(l / 1000 + "s");
            countdown_text.setText(stringBuilder);
        }

        @Override
        public void onFinish() {
            //To change body of implemented methods use File | Settings | File Templates.
            //倒计时结束，恢复原始
            send_voice_code.setTextColor(getResources().getColor(R.color.rate_calculator_amount_color));
            send_voice_code.setEnabled(true);
            sms_code_view.setVisibility(VISIBLE);
            voice_code_view.setVisibility(GONE);
            if (mListener != null)
                mListener.onCountDownFinish();
        }

        public void startCount() {
            if (mListener != null)
                mListener.onCountDownStart();
            sms_code_view.setVisibility(GONE);
            voice_code_view.setVisibility(VISIBLE);
            this.start();
        }
    }

    public void setInitText(String _title) {
        sms_code_view.setText(_title);
    }
}
