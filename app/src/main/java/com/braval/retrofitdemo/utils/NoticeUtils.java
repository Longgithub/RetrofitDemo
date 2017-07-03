package com.braval.retrofitdemo.utils;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Looper;
import android.widget.TextView;


/**
 * 自定义通知反馈组件
 * Created by bianhaipeng on 2016/6/29.
 */
public class NoticeUtils {
    public static final int STYLE_ALERT = 1;
    public static final int STYLE_CONFIRM = 2;
    public static final int STYLE_INFO = 3;

    public static void showAnimator(Context context, final TextView animatorView, int res, String msg, int style, boolean isFromNegativePos) {
        if (res == -1 && msg == null) {
            return;
        }

        final int navAnimatorDist = DensityUtils.dp2px(context, 48);
        if (null != animatorView) {
//            switch (style) {
//                case STYLE_ALERT:
//                    animatorView.setBackgroundColor(context.getResources().getColor(R.color.alert));
//                    break;
//                case STYLE_CONFIRM:
//                    animatorView.setBackgroundColor(context.getResources().getColor(R.color.confirm));
//                    break;
//                case STYLE_INFO:
//                    animatorView.setBackgroundColor(context.getResources().getColor(R.color.info));
//                    break;
//                default:
//                    animatorView.setBackgroundColor(context.getResources().getColor(R.color.alert));
//                    break;
//            }

            if (res != -1) {
                animatorView.setText(res);
            } else {
                animatorView.setText(msg);
            }

            if (isFromNegativePos) {
                ObjectAnimator.ofFloat(animatorView, "translationY", -navAnimatorDist, 0).setDuration(600).start();
                ObjectAnimator.ofFloat(animatorView, "alpha", 0, 1).setDuration(600).start();
                new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator.ofFloat(animatorView, "translationY", 0, -navAnimatorDist).setDuration(600).start();
                        ObjectAnimator.ofFloat(animatorView, "alpha", 1, 0).setDuration(600).start();
                        animatorView.setEnabled(true);
                    }
                }, 2000);
            } else {
                ObjectAnimator.ofFloat(animatorView, "translationY", 0, navAnimatorDist).setDuration(600).start();
                ObjectAnimator.ofFloat(animatorView, "alpha", 0, 1).setDuration(600).start();
                new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator.ofFloat(animatorView, "translationY", navAnimatorDist, 0).setDuration(600).start();
                        ObjectAnimator.ofFloat(animatorView, "alpha", 1, 0).setDuration(600).start();
                        animatorView.setEnabled(true);
                    }
                }, 2000);
            }
        }
    }
}
