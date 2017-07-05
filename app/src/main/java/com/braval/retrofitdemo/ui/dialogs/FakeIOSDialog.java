package com.braval.retrofitdemo.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.braval.retrofitdemo.R;


/**
 * Author: zhanglong
 * Date:   2015/6/3.
 */
public class FakeIOSDialog extends Dialog {
    public FakeIOSDialog(Context context, int theme) {
        super(context, theme);
    }
    public static class Builder {
        private final Context context; //
        private String message;
        private String confirm_btnText;
        private String cancel_btnText;
        private Button confirmBtn;
        private Button cancelBtn;
        private TextView messageTv;

        private ImageView close_dialog;
        private ImageView dialog_image;


        private LinearLayout fake_ios_dialog_general_bottom;
        private String netural_btnText;
        private Button fake_ios_dialog_netural_button;

        String titleText;
        TextView fake_ios_dialog_title;

        private OnClickListener confirm_btnClickListener;
        private OnClickListener cancel_btnClickListener;
        private OnClickListener netural_btnClickListener;
        private OnClickListener close_btnClickLisner;
        private boolean cancelable;
        private boolean fakebold;

        private boolean negativeBold;
        private boolean positiveBold;

        private int messageColor = Color.BLACK;
        private boolean messageBold;

        private boolean isloan = false;
        private int messageTextSize = 16;
        private boolean closebtn_enable;
        private int dialog_img_res=-1;

        public Builder(Context context) {
            this.context = context;
            //Default can be canceled
            cancelable = true;
        }

        public Builder setCloseButton(boolean close_enable, OnClickListener listener) {
            closebtn_enable = close_enable;
            close_btnClickLisner = listener;
            return this;
        }

        public Builder setDialogImageRes(int redID) {
            dialog_img_res = redID;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageBold(boolean messageBold) {
            this.messageBold = messageBold;
            return this;
        }

        public Builder setMessageColor(int color) {
            this.messageColor = color;
            return this;
        }

        public Builder setMessageTextSize(int sp) {
            this.messageTextSize = sp;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setPositiveButton(int confirm_btnText,
                                         OnClickListener listener) {
            this.confirm_btnText = (String) context
                    .getText(confirm_btnText);
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String confirm_btnText,
                                         OnClickListener listener) {
            this.confirm_btnText = confirm_btnText;
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setNenativeBold(boolean negativeBold) {
            this.negativeBold = negativeBold;
            return this;
        }

        public Builder setPositiveBold(boolean negativeBold) {
            this.positiveBold = negativeBold;
            return this;
        }

        public Builder setNegativeButton(int cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = (String) context
                    .getText(cancel_btnText);
            this.cancel_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = cancel_btnText;
            this.cancel_btnClickListener = listener;
            return this;
        }

        public Builder setNeturalButton(String netural_btnText, OnClickListener listener) {
            this.netural_btnText = netural_btnText;
            this.netural_btnClickListener = listener;
            return this;
        }

        public Builder setNeturalButton(int netural_btnText, OnClickListener listener) {
            this.netural_btnText = (String) context.getText(netural_btnText);
            this.netural_btnClickListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setTitle(String title) {
            this.titleText = title;
            return this;
        }

        public Builder setTitle(int title) {
            this.titleText = (String) context.getText(title);
            return this;
        }

        public Builder setFakedBold(boolean fakebold) {
            this.fakebold = fakebold;
            return this;
        }

        @SuppressLint("InflateParams")
        public FakeIOSDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final FakeIOSDialog dialog = new FakeIOSDialog(context, R.style.com_bypay_loading_dialog_style);
            View layout = null;
            if (!isloan) {
                layout = inflater.inflate(R.layout.fake_ios_dialog_layout, null);
            } else {
//                layout = inflater.inflate(R.layout.loan_success_dialog, null);
            }
            dialog.setCanceledOnTouchOutside(cancelable);
            // set the dialog title
            confirmBtn = (Button) layout.findViewById(R.id.fake_ios_dialog_confirm);
            cancelBtn = (Button) layout.findViewById(R.id.fake_ios_dialog_cancel);
            messageTv = (TextView) layout.findViewById(R.id.fake_ios_dialog_message);
            close_dialog = (ImageView) layout.findViewById(R.id.close_dialog);
            dialog_image = (ImageView) layout.findViewById(R.id.dialog_image);

            if (dialog_img_res != -1) {
                dialog_image.setImageResource(dialog_img_res);
                dialog_image.setVisibility(View.VISIBLE);
            } else {
                dialog_image.setVisibility(View.GONE);
            }
            if (closebtn_enable) {
                close_dialog.setVisibility(View.VISIBLE);
            } else {
                close_dialog.setVisibility(View.INVISIBLE);
            }

            if (close_btnClickLisner != null) {
                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_btnClickLisner.onClick(dialog, -1);
                    }
                });
            }

            messageTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, messageTextSize);
            //While there are two
            fake_ios_dialog_general_bottom = (LinearLayout) layout.findViewById(R.id.fake_ios_dialog_general_bottom);
            //Only one item
            fake_ios_dialog_netural_button = (Button) layout.findViewById(R.id.fake_ios_dialog_netural_button);
            fake_ios_dialog_title = (TextView) layout.findViewById(R.id.fake_ios_dialog_title);
            if (!TextUtils.isEmpty(titleText)) {
                fake_ios_dialog_title.getPaint().setFakeBoldText(fakebold);
                fake_ios_dialog_title.setText(titleText);
            }else{
                fake_ios_dialog_title.setVisibility(View.GONE);
            }
            // set the confirm button
            confirmBtn.setText(confirm_btnText);
            if (confirm_btnClickListener != null) {
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm_btnClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);
                    }
                });
                confirmBtn.getPaint().setFakeBoldText(positiveBold);
                fake_ios_dialog_netural_button.setVisibility(View.GONE);
                fake_ios_dialog_general_bottom.setVisibility(View.VISIBLE);
            }
            cancelBtn.setText(cancel_btnText);
            if (cancel_btnClickListener != null) {
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        cancel_btnClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEGATIVE);
                    }
                });
                cancelBtn.getPaint().setFakeBoldText(negativeBold);
                fake_ios_dialog_netural_button.setVisibility(View.GONE);
                fake_ios_dialog_general_bottom.setVisibility(View.VISIBLE);
            }
            if (null != netural_btnClickListener) {
                fake_ios_dialog_netural_button.setText(netural_btnText);
                fake_ios_dialog_netural_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netural_btnClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
                    }
                });
                fake_ios_dialog_netural_button.setVisibility(View.VISIBLE);
                fake_ios_dialog_general_bottom.setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {
                messageTv.getPaint().setColor(messageColor);
                messageTv.getPaint().setFakeBoldText(messageBold);
                messageTv.setText(message);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }
}
