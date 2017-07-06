package com.braval.retrofitdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;

public class JavaScriptInterfaceContract {
    private static final String TAG = JavaScriptInterfaceContract.class.getSimpleName();
    private Activity mContext;
    private WebView mWebView;
    private INavigateCallBack mNavigateCallback;
    private IPopupCallBack mPopupCallback;
    private IDialogCallBack mDialogCalback;
    private IPersonIdValidCallback mPersonIdvalidCallback;
    private IChooseContactCallBack mChooseContactCallback;
    private IProgressCallBack mProgressCallback;
    private IAddCardCallBack mAddCardCallback;
    private IChooseCardCallBack mChooseCardCallback;
    private IEagleEyeCallBack mEagleCallback;
    private IEndPageCallBack mEndPageCallback;
    private IChooseSchool mSchoolCallback;
    private IGetURLCallBack mGetURLCallback;
    private IOpenAppCallBack mOpenAppCallback;
    private IGrantAuthCallBack mGrantAuthCallback;
    private IShareCallBack mShareCallback;
    private IShowBarCallBack mShowBarCallback;
    private IHideBarCallBack mHideBarCallback;
    private ISetNavBarCallBack mSetBarCallback;
    private IOpenAblumCallBack mOpenAblumCallBack;

    public JavaScriptInterfaceContract(@NonNull Activity context,
                                       @NonNull WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    /**
     * 网页内分享
     *
     * @param url
     * @param title
     * @param content
     */
    @JavascriptInterface
    public void share_url(final String url, final String title, final String content) {
        Intent intent = new Intent("should_share");
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("url", url);
        mContext.sendBroadcast(intent);
    }

    /**
     * JS通知native调用API
     */
    @JavascriptInterface
    public void call(String api, String param, final String callback) {
        JSONObject object = null;
        try {
            object = new JSONObject(param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (mICallBackListener != null) {
            mICallBackListener.callback(api, object, callback);
        }
    }
    private ICallbackListener mICallBackListener;
    public void setIOpenAblumCallback(IOpenAblumCallBack _callback) {
        mOpenAblumCallBack = _callback;
    }
    public void setINavigateCallback(INavigateCallBack _mNavigateCallback) {
        this.mNavigateCallback = _mNavigateCallback;
    }
    public void setIPopupCallback(IPopupCallBack mPopupCallback) {
        this.mPopupCallback = mPopupCallback;
    }
    public void setIDialogCalback(IDialogCallBack mDialogCalback) {
        this.mDialogCalback = mDialogCalback;
    }
    public void setIPersonIdvalidCallback(IPersonIdValidCallback mPersonIdvalidCallback) {
        this.mPersonIdvalidCallback = mPersonIdvalidCallback;
    }
    public void setIChooseContactCallback(IChooseContactCallBack mChooseContactCallback) {
        this.mChooseContactCallback = mChooseContactCallback;
    }
    public void setIChooseCardCallback(IChooseCardCallBack mChooseCardCallback) {
        this.mChooseCardCallback = mChooseCardCallback;
    }
    public void setIEndPageCallback(IEndPageCallBack mEndPageCallback) {
        this.mEndPageCallback = mEndPageCallback;
    }
    public void setISchoolCallback(IChooseSchool mSchoolCallback) {
        this.mSchoolCallback = mSchoolCallback;
    }
    public void setIGetURLCallback(IGetURLCallBack mGetURLCallback) {
        this.mGetURLCallback = mGetURLCallback;
    }
    public void setIOpenAppCallback(IOpenAppCallBack mOpenAppCallback) {
        this.mOpenAppCallback = mOpenAppCallback;
    }
    public void setIShareCallback(IShareCallBack mShareCallback) {
        this.mShareCallback = mShareCallback;
    }
    public void setIShowBarCallback(IShowBarCallBack _ShowBarCallback) {
        mShowBarCallback = _ShowBarCallback;
    }
    public void setIHideBarCallback(IHideBarCallBack _hideBarcallback) {
        mHideBarCallback = _hideBarcallback;
    }
    public void setNavBarCallback(ISetNavBarCallBack _callback) {
        mSetBarCallback = _callback;
    }

    public interface ICallbackListener {
        /**
         * 调用API,并且回传调用结果
         *
         * @param api            API名称
         * @param param          API入参
         * @param callbackmethod 回调方法名
         */
        void callback(String api, JSONObject param, String callbackmethod);
    }
    public void setICallBackListener(ICallbackListener _listener) {
        mICallBackListener = _listener;
    }
    //以下为JS调用本地插件化方法
    @JavascriptInterface
    public void plugin(String pluginID, String param) {
//        if (NetConsts.DEBUG)
//            Log.d("plugin", pluginID + "|" + param);
        if (null != param) {
            if ("navigate".equals(pluginID)) {
                try {
                    navigate(param);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if ("dialog".equals(pluginID)) {
                try {
                    dialog(param);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if ("popup".equals(pluginID)) {
                try {
                    popup(param);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if ("personIdValid".equals(pluginID)) {
                personIdValid(param);
            } else if ("contact".equals(pluginID)) {
                chooseContact(param);
            } else if ("log".equals(pluginID)) {
                log(param);
            } else if ("addCard".equals(pluginID)) {
                addCard(param);
            } else if ("chooseCard".equals(pluginID)) {
                chooseCard(param);
            } else if ("progress".equals(pluginID)) {
                progress(param);
            } else if ("ee".equals(pluginID)) {
                ee(param);
            } else if ("endPage".equals(pluginID)) {
                endPage(param);
            } else if ("chooseSchool".equals(pluginID)) {
                chooseSchool(param);
            } else if ("getUrl".equals(pluginID)) {
                getURL(param);
            } else if ("copyPaste".equals(pluginID)) {
                copyPaste(param);
            } else if ("openApp".equals(pluginID)) {
                openApp(param);
            } else if ("grantAuth".equals(pluginID)) {
                grantAuth(param);
            } else if ("share".equals(pluginID)) {
                share(param);
            } else if ("eeLogError".equals(pluginID)) {
                //上传异常
                try {
//                    EagleEye.getInstance().monitorLogCrashExceptionEvent(new JSONObject(param).optString("param"));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if ("eeLogUBT".equals(pluginID)) {
                //用户行为
//                EagleEye.getInstance().monitorLogIndistinct(param);
            } else if ("showBarButton".equals(pluginID)) {
                showBarButton(param);
            } else if ("hideBarButton".equals(pluginID)) {
                hideBarButton(param);
            } else if ("setNavigationBar".equals(pluginID)) {
                setNavigationBar(param);
            } else if ("openAlbum".equals(pluginID)) {
                openAblum(param);
            }
        }
    }
    private void openAblum(String param) {
        if (null != mOpenAblumCallBack) {
            try {
                mOpenAblumCallBack.openAblum(new JSONObject(param));
            } catch (Throwable e) {

            }
        }
    }
    private void setNavigationBar(String param) {
        if (mSetBarCallback != null) {
            try {
                mSetBarCallback.setNavBar(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    private void hideBarButton(String param) {
        if (mHideBarCallback != null) {
            try {
                mHideBarCallback.hideBar(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void showBarButton(String param) {
        if (mShowBarCallback != null) {
            try {
                mShowBarCallback.showBar(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    private void share(String param) {
        if (mShareCallback != null) {
            try {
                mShareCallback.share(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void grantAuth(String param) {
        if (mGrantAuthCallback != null) {
            try {
                mGrantAuthCallback.grantAuth(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void openApp(String param) {
        if (mOpenAppCallback != null) {
            try {
                mOpenAppCallback.openApp(new JSONObject(param));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void copyPaste(String param) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        //支付宝还款账号
        clipboard.setText(jsonObject.optString("text"));
    }

    private void getURL(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if (null != mGetURLCallback) {
                mGetURLCallback.getURL(jsonObject);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void chooseSchool(String param) {
        try {
            JSONObject json = new JSONObject(param);
            if (mSchoolCallback != null) {
                mSchoolCallback.chooseSchool(json.optString("callback"));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void endPage(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if (mEndPageCallback != null) {
                mEndPageCallback.endpage(jsonObject);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用鹰眼
     *
     * @param param
     */
    private void ee(String param) {
        try {
            JSONObject data = new JSONObject(param);
            if (mEagleCallback != null) {
                mEagleCallback.ee(data.optString("content"));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示进度条
     *
     * @param param
     */
    private void progress(String param) {
        JSONObject data = null;
        try {
            data = new JSONObject(param);
            if (mProgressCallback != null) {
                mProgressCallback.showProgress(data.optString("type"));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void chooseCard(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if (null != mChooseCardCallback) {
                mChooseCardCallback.chooseCard(jsonObject);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void addCard(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if (null != mAddCardCallback) {
                mAddCardCallback.addCard(jsonObject);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 负责根据PageID跳转画面
     */
    public void navigate(String _param) throws Throwable {
//        JSONObject param = new JSONObject(_param);
//        JSONObject extras = param.optJSONObject("param");
//        String pageId = param.optString("pageId");
//        Intent intent = new Intent();
//        if (null != extras) {
//            intent.putExtra("extras", extras.toString());
//        }
//        if (null != param) {
//            intent.putExtra("callback", param.optString("callback"));
//        }
//        intent.putExtra("page", pageId);
//        if ("Page_Home".equals(pageId)) {
//            intent.setClass(mContext, MainActivity.class);
//        } else if (NetConsts.Page_Bankcard.equals(pageId)) {
//            intent.setClass(mContext, Bankcard.class);
//        } else if (NetConsts.Page_SingleLoan.equals(pageId) || NetConsts.Page_InstallmentLoan.equals(pageId)) {
//            intent.setClass(mContext, IWantLoan.class);
//        } else if (NetConsts.Page_RepayGuide.equals(pageId) || NetConsts.Page_LoanGuide.equals(pageId)) {
//            intent.setClass(mContext, UServiceDetailActivity.class);
//        } else if (NetConsts.Page_ConsignAuthorize.equals(pageId) ||
//                NetConsts.Page_threeSidesAgreement.equals(pageId) ||
//                NetConsts.Page_uServiceAgreement.equals(pageId) ||
//                NetConsts.Page_privatePolicy.equals(pageId) ||
//                NetConsts.Page_uLoanPartServiceAgreement.equals(pageId)) {
//            intent.setClass(mContext, LoanContract.class);
//        } else if (NetConsts.Page_repayStyle.equals(pageId) ||
//                   NetConsts.Page_baiduRepay.equals(pageId)
//                ) {
//            intent.setClass(mContext, RepayWayActivity.class);
//        } else if (NetConsts.Page_yijianRepay.equals(pageId)) {
//            intent.setClass(mContext, OneKeyRepayWay.class);
//        } else if (NetConsts.Page_yibao_newCard.equals(pageId)) {
//            intent.setClass(mContext, YiBaoWithoutCard.class);
//        } else if (NetConsts.Page_yibao_HasCard.equals(pageId)) {
//            intent.setClass(mContext, YiBaoWithCard.class);
//        } else if (NetConsts.Page_YijianSucc.equals(pageId) ||
//                   NetConsts.Page_yibaoRepay.equals(pageId)) {
//            intent.setClass(mContext, RepaySuccess.class);
//        } else if (NetConsts.Page_AlipayAuth.equals(pageId)) {
//            intent.setClass(mContext, VerifyAlipay.class);
//        } else if (NetConsts.Page_GetAmount_Simple.equals(pageId)) {
//            intent.setClass(mContext, GetAmount.class);
//        } else if (NetConsts.Page_Banner_Detail.equals(pageId) || NetConsts.Page_Flash.equals(pageId)) {
//            intent.setClass(mContext, BannerActivity.class);
//        } else if (NetConsts.Page_UU_Detail.equals(pageId)) {
//            intent.setClass(mContext, UUDetail.class);
//        } else if (NetConsts.Page_noPay.equals(pageId)) {
//            intent.setClass(mContext, NoNeedRepay.class);
//        } else if (NetConsts.Page_InstallmentBills.equals(pageId)) {
//            intent.setClass(mContext, PayMentInstall.class);
//        } else if (NetConsts.Page_Login.equals(pageId)) {
//            intent.setClass(mContext, LoginActivity.class);
//        } else if (NetConsts.Page_SingleRepay.equals(pageId)) {
//            intent.setClass(mContext, IWantRepay.class);
//        }
//
//        if (mNavigateCallback != null) {
//            mNavigateCallback.navigate(intent, pageId);
//        }
    }

    /**
     * JS 调用Native 对话框，并返回结果
     *
     * @param _param 需要的参数信息
     */
    public void dialog(String _param) throws Throwable {
        final JSONObject param = new JSONObject(_param);
        if (null != mDialogCalback) {
            mDialogCalback.dialogCallback(param.optString("callback"), param);
        }
    }

    /**
     * JS 调用Native 弹出框
     *
     * @param _param 调用画面参数
     */
    public void popup(String _param) throws Throwable {
        final JSONObject param = new JSONObject(_param);
        if (mPopupCallback != null) {
            mPopupCallback.popupCallback("", param);
        }
    }

    /**
     * JS调用Native身份证识别
     *
     * @param
     */
    public void personIdValid(String _param) {
        //开启身份证识别模块
        //完成之后
        JSONObject param = null;
        try {
            param = new JSONObject(_param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (null != mPersonIdvalidCallback) {
            mPersonIdvalidCallback.personIdValideCallback(param.optString("callback"), param.optString("api"));
        }
    }


    /**
     * JS 调用Native通讯录,待用户选择通讯录后返回通讯录选择结果
     *
     * @param _param
     */
    public void chooseContact(String _param) {
        JSONObject param = null;
        try {
            param = new JSONObject(_param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //获取之后,这个需要在调用的地方来回传，因为JavaScript无法接受Activity回参
        if (null != mChooseContactCallback) {
            mChooseContactCallback.chooseContactCallback(param.optString("callback"), param.optString("api"));
        }
    }

    /**
     * 打印提示信息。
     *
     * @param _param
     */
    public void log(String _param) {
        JSONObject param = null;
        try {
            param = new JSONObject(_param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        String type = param.optString("type");
        String content = param.optString("content");
        if ("info".equals(type)) {
            Log.i(TAG, content);
        } else if ("debug".equals(type)) {
            Log.d(TAG, content);
        } else if ("error".equals(type)) {
            Log.e(TAG, content);
        }

    }

    public interface INavigateCallBack {
        void navigate(Intent intent, String pageID);
    }

    public interface IPopupCallBack {
        /**
         * {
         * “status”:”<成功1,失败0>”,
         * “error”:”<失败编码>”,
         * “message”:”<失败消息>”,
         * “data”:{“name”:”<姓名>”, “tel”:”<联系电话>”}
         * }
         */
        void popupCallback(String methodname, JSONObject param);
    }

    public interface IDialogCallBack {
        /**
         * 对话框操作回调
         * 回调时 回调对象格式
         * {
         * “status”:”<成功1,失败0>”,
         * “error”:”<失败编码>”,
         * “message”:”<失败消息>”,
         * “data”:{“result”:”<返回结果>”}
         * }
         *
         * @param methodname 回调函数名
         * @param param      回填结果
         */
        void dialogCallback(String methodname, JSONObject param);
    }

    public interface IPersonIdValidCallback {
        /**
         * 扫描身份证回调
         * <p>
         * {
         * “status”:”<成功1,失败0>”,
         * “error”:”<失败编码>”,
         * “message”:”<失败消息>”,
         * “data”:{<上传返回结果>} 上传返回结果是 调用API之后的返回报文
         * }
         *
         * @param methodname 回调函数名
         * @param api        需要调用者请求的API
         */
        void personIdValideCallback(String methodname, String api);

    }

    public interface IChooseContactCallBack {
        /**
         * 选择联系人回调
         * 返回数据报文
         * <p>
         * {
         * “status”:”<成功1,失败0>”,
         * “error”:”<失败编码>”,
         * “message”:”<失败消息>”,
         * “data”:{“name”:”<姓名>”, “tel”:”<联系电话>”}
         * }
         *
         * @param methodname 回调函数名
         * @param api        需要调用者请求的API
         */
        void chooseContactCallback(String methodname, String api);
    }


    public interface IProgressCallBack {
        void showProgress(String type);
    }

    public interface IAddCardCallBack {
        void addCard(JSONObject jsonbject);
    }

    public interface IChooseCardCallBack {
        void chooseCard(JSONObject param);
    }

    public interface IEagleEyeCallBack {
        void ee(String content);
    }

    public interface IEndPageCallBack {
        void endpage(JSONObject jsonObject);
    }

    public interface IChooseSchool {
        void chooseSchool(String callback);
    }

    public interface IGetURLCallBack {
        void getURL(JSONObject param);
    }

    public interface IOpenAppCallBack {
        void openApp(JSONObject jsonObject);
    }

    public interface IGrantAuthCallBack {
        void grantAuth(JSONObject data);
    }

    public interface IShareCallBack {
        void share(JSONObject jsonObject);
    }

    public interface IShowBarCallBack {
        void showBar(JSONObject jsonObject);
    }

    public interface IHideBarCallBack {
        void hideBar(JSONObject data);
    }

    public interface ISetNavBarCallBack {
        void setNavBar(JSONObject data);
    }

    public interface IOpenAblumCallBack {
        void openAblum(JSONObject object);
    }
}

