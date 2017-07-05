package com.braval.retrofitdemo.ui.views;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.braval.retrofitdemo.R;


@SuppressWarnings("ALL")
public class ClearEditText extends EditText implements
        OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private boolean hasFoucs;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    int paddinleft;

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//	throw new NullPointerException("You can add drawableRight attribute in XML");
//            mClearDrawable = getResources().getDrawable(R.drawable.delete_selector);
        }
        setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
//        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        mClearDrawable.setBounds(0, 0, 50, 50);
//        this.setTypeface(Typeface.MONOSPACE);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setHintTextColor(getResources().getColor(R.color.clear_text_hint_color));
        setBackgroundColor(Color.TRANSPARENT);
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


// --Commented out by Inspection START (2015/5/26 17:07):
//    /**
//     * ���ûζ�����
//     */
//    public void setShakeAnimation() {
//        this.setAnimation(shakeAnimation(5));
//    }
// --Commented out by Inspection STOP (2015/5/26 17:07)


// --Commented out by Inspection START (2015/5/27 9:51):
//    public static Animation shakeAnimation(int counts) {
//        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
//        translateAnimation.setInterpolator(new CycleInterpolator(counts));
//        translateAnimation.setDuration(1000);
//        return translateAnimation;
//    }
// --Commented out by Inspection STOP (2015/5/27 9:51)


}
