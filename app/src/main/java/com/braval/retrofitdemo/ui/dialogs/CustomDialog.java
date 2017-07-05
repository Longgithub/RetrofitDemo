package com.braval.retrofitdemo.ui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.braval.retrofitdemo.R;


public class CustomDialog extends ProgressDialog {
    TextView interla = null;
    static String _message;
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
    public static CustomDialog getCustomDialog(Context context, String message) {
        _message=message;
        CustomDialog dialog = new CustomDialog(context, R.style.com_bypay_loading_dialog_style);
        dialog.setCancelable(true);
        return dialog;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        interla = (TextView) findViewById(R.id.internal_text);
        if (!TextUtils.isEmpty(_message)){
            interla.setText(_message);
        }
    }
}
