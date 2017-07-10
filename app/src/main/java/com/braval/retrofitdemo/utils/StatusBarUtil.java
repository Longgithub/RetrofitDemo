package com.braval.retrofitdemo.utils;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.braval.retrofitdemo.R;
import com.braval.retrofitdemo.contract.MainContract;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by zhanglong on 2017/7/10.
 */

public class StatusBarUtil {
    public static final int DEFAULT_STATUS_BAR_ALPHA=112;
    private static final int FAKE_STATUS_BAR_VIEW_ID= R.id.statusbarutil_fake_status_bar_view;
    private static final int FAKE_TRANSLUCENT_VIEW_ID=R.id.statusbarutil_translucent_view;
    private static final int TAG_KEY_HAVE_SET_OFFSET=-123;

    /**
     * 设置状态栏颜色
     * @param activity
     * @param color
     */
    public static void setColor(Activity activity, @ColorInt int color){
        setColor(activity,color,DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     * @param activity
     * @param color
     * @param statusBarAlpha
     */
    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0,to = 255) int statusBarAlpha){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(calculateStatusColor(color,statusBarAlpha));
        } else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorView= (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView=decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (null!=fakeStatusBarView){
                if (fakeStatusBarView.getVisibility()==View.GONE){
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color,statusBarAlpha));
            }else {
                decorView.addView(createStatusBarView(activity,color,statusBarAlpha));
            }
            setRootView(activity);
        }
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     * @param activity
     * @param color
     */
    public static void setColorForSwipeBack(Activity activity,int color){
        setColorForSwipeBack(activity,color,DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     * @param activity
     * @param color
     * @param defaultStatusBarAlpha
     */
    public static void setColorForSwipeBack(Activity activity, int color, int defaultStatusBarAlpha) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            ViewGroup contentView= (ViewGroup) activity.findViewById(android.R.id.content);
            View rootView=contentView.getChildAt(0);
            int statusBarHeight=getStatusBarHeight(activity);
            if (null!=rootView&&rootView instanceof CoordinatorLayout){
                final CoordinatorLayout coordinatorLayout= (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(calculateStatusColor(color,defaultStatusBarAlpha));
                    boolean isNeedRequestLayout=contentView.getPaddingTop()<statusBarHeight;
                    if (isNeedRequestLayout){
                        contentView.setPadding(0,statusBarHeight,0,0);
                        coordinatorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                coordinatorLayout.requestLayout();
                            }
                        });
                    }
                }else {
                    coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color,defaultStatusBarAlpha));
                }
            }else {
                contentView.setPadding(0,statusBarHeight,0,0);
                contentView.setBackgroundColor(calculateStatusColor(color,defaultStatusBarAlpha));
            }
            setTransparentFowWindow(activity);
        }

    }

    /**
     * 设置状态栏纯色，不添加半透明效果
     * @param activity
     * @param color
     */
    public  static void setColorNoTranslucent(Activity activity,int color){
        setColor(activity,color,0);
    }

    /**
     * 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity){
        setTranslucent(activity,DEFAULT_STATUS_BAR_ALPHA);
    }
    /**
     * 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param defaultStatusBarAlpha 状态栏透明度
     */

    public static void setTranslucent(Activity activity, int defaultStatusBarAlpha) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        setTransparent(activity);
        addTranslucentView(activity,defaultStatusBarAlpha);
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 设置状态栏全透明
     * @param activity
     */
    public static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public static void addTranslucentView(Activity activity, int defaultStatusBarAlpha) {
        ViewGroup contentView= (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView=contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null!=fakeStatusBarView){
            if (fakeStatusBarView.getVisibility()==View.GONE){
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(defaultStatusBarAlpha,0,0,0));
        }else {
            contentView.addView(createTranslucentStatusBarView(activity, defaultStatusBarAlpha));
        }
    }

    public static View createTranslucentStatusBarView(Activity activity, int defaultStatusBarAlpha) {
        View statusBarView=new View(activity);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(defaultStatusBarAlpha,0,0,0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;

    }



    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    private static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        transparentStatusBar(activity);
        setRootView(activity);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    /**
     * 为Drawerlayout布局设置状态栏颜色
     * @param activity
     * @param drawerLayout
     * @param color
     * @param i
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup contentLayout= (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView=contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null!=fakeStatusBarView){
            if (fakeStatusBarView.getVisibility()==View.GONE){
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        }else {
            contentLayout.addView(createStatusBarView(activity,color),0);
        }

        if (!(contentLayout instanceof LinearLayout) && null!=contentLayout.getChildAt(0)){
            contentLayout.getChildAt(0)
                    .setPadding(contentLayout.getPaddingLeft(),
                            getStatusBarHeight(activity)+ contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(),
                            contentLayout.getPaddingBottom());
        }
        setDrawerLayoutProperty(drawerLayout,contentLayout);
        addTranslucentView(activity,statusBarAlpha);
    }

    /**
     * 设置DrawerLayout属性
     *
     * @param drawerLayout
     * @param contentLayout
     */
    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup contentLayout) {
        ViewGroup drawer= (ViewGroup) drawerLayout.getChildAt(0);
        drawerLayout.setFitsSystemWindows(false);
        contentLayout.setFitsSystemWindows(false);
        contentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }
    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentDrawerLayout(Activity activity,DrawerLayout drawerLayout){
        setTranslucentDrawerLayout(activity,drawerLayout,DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     *  为 DrawerLayout 布局设置状态栏透明
     * @param activity
     * @param drawerLayout
     * @param defaultStatusBarAlpha
     */
    public static void setTranslucentDrawerLayout(Activity activity, DrawerLayout drawerLayout, int defaultStatusBarAlpha) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        setTransparentDrawerLayout(activity,drawerLayout);
        addTranslucentView(activity,defaultStatusBarAlpha);
    }
    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTransparentDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup contentLayout= (ViewGroup) drawerLayout.getChildAt(0);
        if (!(contentLayout instanceof LinearLayout)&&contentLayout.getChildAt(0)!=null){
            contentLayout.getChildAt(1).setPadding(0,getStatusBarHeight(activity),0,0);
        }
        setDrawerLayoutProperty(drawerLayout,contentLayout);
    }
    /**
     * 为头部是 ImageView 的界面设置状态栏全透明
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, 0, needOffsetView);
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param activity              需要设置的activity
     * @param defaultStatusBarAlpha 状态栏透明度
     * @param needOffsetView        需要向下偏移的 View
     */
    public static void setTranslucentForImageView(Activity activity, int defaultStatusBarAlpha, View needOffsetView) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        setTransparentFowWindow(activity);
        addTranslucentView(activity,defaultStatusBarAlpha);

        if (needOffsetView!=null){
            Object haveSetOffset=needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if (null!=haveSetOffset&&(Boolean)haveSetOffset){
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin+getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET,true);
        }
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
                                                            View needOffsetView) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity);
        }
    }

    public static void clearPreviousSetting(Activity activity) {
        ViewGroup decorView= (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView=decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView!=null){
            decorView.removeView(fakeStatusBarView);
            ViewGroup rootView= (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setPadding(0,0,0,0);
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     */
    public static void setTransparentFowWindow(Activity activity) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }else if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 隐藏伪状态栏
     * @param activity
     */
    public static void hideFakeStatusBarView(Activity activity){
        ViewGroup decorView= (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView=decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView!=null){
            fakeStatusBarView.setVisibility(View.GONE);
        }
        View fakeTranslucentView=decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView!=null){
            fakeTranslucentView.setVisibility(View.GONE);
        }
    }
    /**
     *
     * @param activity
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent= (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i=0,count=parent.getChildCount();i<count;i++){
            View childView=parent.getChildAt(i);
            if (childView instanceof ViewGroup){
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     * @param activity
     * @param color
     * @return
     */
    public static View createStatusBarView(Activity activity,int color){
        return  createStatusBarView(activity,color,0);
    }


    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     * @param activity
     * @param color
     * @param statusBarAlpha
     * @return
     */
    private static View createStatusBarView(Activity activity, int color, int statusBarAlpha) {
        View statusBarView=new View(activity);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color,statusBarAlpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int resourceId=context.getResources().getIdentifier("status_bar_height","dimen","android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 计算状态栏颜色
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color,int alpha){
        if (0==alpha){
            return color;
        }
        float a=1-alpha/255;
        int red = color>>16&0xff;
        int green = color>>8&0xff;
        int blue=color&0xff;
        red= (int) (red*a+0.5);
        green= (int) (green*a+0.5);
        blue= (int) (blue*a+0.5);

        return 0xff<<24|red<<16|green<<8|blue;
    }

}

