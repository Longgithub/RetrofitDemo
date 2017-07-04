package com.braval.retrofitdemo.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.braval.retrofitdemo.utils.DensityUtils;


/**
 * Author: zhanglong
 */
@SuppressWarnings("deprecation")
public class GeneralItemRelativeLayout extends RelativeLayout {
    ImageView mIcon;
    TextView itemtitle;
    TextView itemStatus;
    ImageView indicator;
    View bottom_line;
    View top_line;
    View bottom_longer_line;
    View top_longer_line;
    Context ctx;
    boolean bottom_line_visible;
    boolean bottom_longer_line_visible;
    boolean top_line_visible;
    boolean top_longer_line_visible;
    Drawable icon_background;
    boolean icon_visible = true;
    boolean indicator_visible = true;
    int item_title_textColor = Color.rgb(48, 48, 48);
    String item_title_text = "";
    int status_text_color = Color.BLACK;
    String item_status_text = "";
    float item_icon_height_minus = 0;
    float item_icon_width_minus = 0;

    public GeneralItemRelativeLayout(final Context context) {
        super(context);
    }

    void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
//        inflater.inflate(R.layout.general_item_layout, this);
//        mIcon = (ImageView) findViewById(R.id.icon);
//        itemtitle = (TextView) findViewById(R.id.item_title);
//        itemStatus = (TextView) findViewById(R.id.item_status);
//        indicator = (ImageView) findViewById(R.id.indicator);
//        bottom_line = findViewById(R.id.bottom_line);
//        bottom_longer_line = findViewById(R.id.bottom_longer_line);
//        top_line = findViewById(R.id.top_line);
//        top_longer_line = findViewById(R.id.top_longer_line);
        mIcon.setVisibility(icon_visible ? View.VISIBLE : View.GONE);
        if (null != icon_background) {
            int iconWidth = icon_background.getIntrinsicWidth();
            int iconHeight = icon_background.getIntrinsicHeight();

            mIcon.setBackgroundDrawable(icon_background);
            mIcon.setMaxWidth(DensityUtils.dip2px(ctx, iconWidth / 2 - item_icon_width_minus));
            mIcon.setMinimumWidth(DensityUtils.dip2px(ctx, iconWidth / 2 - item_icon_width_minus));
            mIcon.setMaxHeight(DensityUtils.dip2px(ctx, iconHeight / 2 - item_icon_height_minus));
            mIcon.setMinimumHeight(DensityUtils.dip2px(ctx, iconHeight / 2 - item_icon_height_minus));
        }

        itemtitle.setText(item_title_text);
        itemtitle.setTextColor(item_title_textColor);
        itemStatus.setText(item_status_text);
        itemStatus.setTextColor(status_text_color);
        bottom_line.setVisibility(bottom_line_visible ? View.VISIBLE : View.GONE);
        bottom_longer_line.setVisibility(bottom_longer_line_visible ? VISIBLE : GONE);
        top_line.setVisibility(top_line_visible ? VISIBLE : GONE);
        top_longer_line.setVisibility(top_longer_line_visible ? VISIBLE : GONE);
        indicator.setVisibility(indicator_visible ? VISIBLE : INVISIBLE);
    }

    public GeneralItemRelativeLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
//        parseAttributes(ctx,attrs);

    }

    public GeneralItemRelativeLayout(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        ctx = context;
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GeneralItemRelativeLayout, defStyle, 0);
//        if (a != null) {
//            bottom_line_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_bottom_line_visible, true);
//            bottom_longer_line_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_bottom_longer_line_visible, false);
//            top_line_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_top_line_visible, false);
//            top_longer_line_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_top_longer_line_visible, false);
//            icon_background = a.getDrawable(R.styleable.GeneralItemRelativeLayout_icon_background);
//            icon_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_icon_visible, true);
//            indicator_visible = a.getBoolean(R.styleable.GeneralItemRelativeLayout_indicator_visible, true);
//            item_title_textColor = a.getColor(R.styleable.GeneralItemRelativeLayout_item_title_textColor, getResources().getColor(R.color.general_item_title_color));
//            item_title_text = a.getString(R.styleable.GeneralItemRelativeLayout_item_title_text);
//            status_text_color = a.getColor(R.styleable.GeneralItemRelativeLayout_status_text_color, Color.BLACK);
//            item_status_text = a.getString(R.styleable.GeneralItemRelativeLayout_item_status_text);
//            item_icon_height_minus = a.getDimension(R.styleable.GeneralItemRelativeLayout_item_icon_height_minus, TypedValue.COMPLEX_UNIT_DIP);
//            item_icon_width_minus = a.getDimension(R.styleable.GeneralItemRelativeLayout_item_icon_width_minus, TypedValue.COMPLEX_UNIT_DIP);
//            a.recycle();
//        }
        init();
//        parseAttributes(ctx,attrs);
    }

    public void setStatusText(final String s) {
        itemStatus.setText(s);
    }

}
