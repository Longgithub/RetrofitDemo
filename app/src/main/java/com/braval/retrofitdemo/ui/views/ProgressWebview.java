package com.braval.retrofitdemo.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mobanker.uzone.R;
import com.mobanker.uzone.utils.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义带进度条WebView
 * Created by zhanglong on 2016/10/1.
 */

public class ProgressWebview extends WebView {

    private ProgressBar progressbar;
    private IWebChromeClientListener mListener;
    private Context context;


    ActionMode mActionMode;

    List<String> mActionList = new ArrayList<>();

    ActionSelectListener mActionSelectListener;

    /**
     * 是否需要进度条
     */
    private boolean mIsWithProgressBar = true;

    public ProgressWebview(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        this.context = context;
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2, 0, 0));
        progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable
                .bar_progress_webview));
        addView(progressbar);
        setWebChromeClient(new CustomWebChromeClient());

        setDownloadListener(new QLDownloadListener());
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setGeolocationEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setDefaultTextEncodingName("UTF-8");
        String ua = getSettings().getUserAgentString();
        getSettings().setUserAgentString(ua + ";X-CROSS-AGENT-Android");
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (Consts.DEBUG)
                    Log.d("onPageStarted", url);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }

        }
    }

    class QLDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public void setIWebChromeListemer(IWebChromeClientListener listemer) {
        mListener = listemer;
    }

    public interface IWebChromeClientListener {
        void onReceivedTitle(WebView view, String title);

        void onProgressChanged(WebView view, int newProgress);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (null != mListener) {
                mListener.onReceivedTitle(view, title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mIsWithProgressBar) {
                progressbar.setProgress(newProgress);
                if (100 == newProgress) {
                    progressbar.setVisibility(GONE);
                }
            }
            if (mListener != null) {
                mListener.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                  JsPromptResult result) {
            return true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
    }

    /**
     * 禁用进度条
     */
    public void setNoProgressBar() {
        mIsWithProgressBar = false;
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        
        return resolveActionMode(super.startActionMode(callback));
    }

    private ActionMode resolveActionMode(ActionMode actionMode) {
        if (actionMode != null) {
            final Menu menu = actionMode.getMenu();
            mActionMode = actionMode;
            menu.clear();
            for (int i = 0; i < mActionList.size(); i++) {
                menu.add(mActionList.get(i));
            }
            for (int i = 0; i < menu.size(); i++) {
                MenuItem menuItem = menu.getItem(i);
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        getSelectedData((String) item.getTitle());
                        releaseAction();
                        return true;
                    }
                });
            }
        }
        mActionMode = actionMode;
        return actionMode;
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return resolveActionMode(super.startActionMode(callback, type));
    }
    
    private void releaseAction(){
        if (null!=mActionMode){
            mActionMode.finish();
            mActionMode=null;
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     */
    private void getSelectedData(String title) {

        String js = "(function getSelectedText() {" +
                "var txt;" +
                "var title = \"" + title + "\";" +
                "if (window.getSelection) {" +
                "txt = window.getSelection().toString();" +
                "} else if (window.document.getSelection) {" +
                "txt = window.document.getSelection().toString();" +
                "} else if (window.document.selection) {" +
                "txt = window.document.selection.createRange().text;" +
                "}" +
                "JSInterface.callback(txt,title);" +
                "})()";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:" + js, null);
        } else {
            loadUrl("javascript:" + js);
        }
    }

    public void linkJSInterface() {
        addJavascriptInterface(new ActionSelectInterface(this), "JSInterface");
    }

    /**
     * 设置弹出action列表
     * @param actionList
     */
    public void setActionList(List<String> actionList) {
        mActionList = actionList;
    }

    /**
     * 设置点击回掉
     * @param actionSelectListener
     */
    public void setActionSelectListener(ActionSelectListener actionSelectListener) {
        this.mActionSelectListener = actionSelectListener;
    }

    /**
     * 隐藏消失Action
     */
    public void dismissAction() {
        releaseAction();
    }


    /**
     * js选中的回掉接口
     */
    private class ActionSelectInterface {

        ProgressWebview mContext;

        ActionSelectInterface(ProgressWebview c) {
            mContext = c;
        }

        @JavascriptInterface
        public void callback(final String value, final String title) {
            if(mActionSelectListener != null) {
                mActionSelectListener.onClick(title, value);
            }
        }
    }

    public interface ActionSelectListener {
        void onClick(String title, String selectText);
    }
    
}
