package com.braval.retrofitdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.braval.retrofitdemo.R;
import com.braval.retrofitdemo.listener.OnTopNavigationListener;
import com.braval.retrofitdemo.ui.views.ViewHolderAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanglong
 */

public abstract class BaseActivity extends AppCompatActivity implements OnTopNavigationListener ,IBaseView{
    Context mContext;
    ViewHolderAnimator mViewHolderAnimator;
    /**
     * TopBar
     */
    @BindView(R.id.fl_top_bar)
    FrameLayout flTopBar;
    /**
     * child view container
     */
    @BindView(R.id.fl_main)
    FrameLayout flMain;

    public abstract void init();

    public abstract boolean isNeedTopNavBar();

    public abstract boolean isNeedAnmatorNoticeView();

    public abstract void generateChildViewHolder(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_base);
        mContext=this;
        if(isNeedTopNavBar()){
            initTopNavBar(flTopBar);
        }
    }

    private void initTopNavBar(ViewGroup parent) {
        View topNav= LayoutInflater.from(this).inflate(R.layout.top_navigation_layout,null);
        topNav.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.topnav_height)));
        parent.addView(topNav);
        //To do

        /**
         * Hide System ActionBar
         */
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (R.layout.activity_base==layoutResID){
            super.setContentView(layoutResID);
        }else{
            /**
             * Add childview
             */
            flMain.removeAllViews();
            View childView= LayoutInflater.from(this).inflate(layoutResID,null);
            childView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            flMain.addView(childView);
            /**
             * Add Top NavBar
             */
            if (isNeedAnmatorNoticeView())
            {
                initAnimatorView(flMain);
            }
            generateChildViewHolder(childView);
            init();
        }
        ButterKnife.bind(this);
    }

    private void initAnimatorView(ViewGroup flMain) {
        View topNav=this.getLayoutInflater().inflate(R.layout.nav_animator_layout,null);
        topNav.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.animator_notice_view_height)));
        flMain.addView(topNav);
        mViewHolderAnimator=new ViewHolderAnimator(topNav);
    }
}
