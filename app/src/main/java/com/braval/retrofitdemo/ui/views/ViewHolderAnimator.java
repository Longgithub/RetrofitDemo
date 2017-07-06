package com.braval.retrofitdemo.ui.views;

import android.view.View;
import android.widget.TextView;

import com.braval.retrofitdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderAnimator {
    @BindView(R.id.tv_animator)
    public TextView tvAnimator;

    public ViewHolderAnimator(View view) {
        ButterKnife.bind(this, view);
    }
}
