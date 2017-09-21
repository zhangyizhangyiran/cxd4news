package com.cxd.cxd4android.widget;

import android.net.Uri;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;


/**
 * 新增webview对上传文件的支持
 * zhangyi
 * MyWebChomeClient
 */
public class MyWebChomeClient extends WebChromeClient {

    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public MyWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

        //判断手机版本号是否需要申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //新增SD卡读写权限申请
            return;
        }
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

        //判断手机版本号是否需要申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //新增SD卡读写权限申请
            return;
        }
        openFileChooser(uploadMsg, acceptType);
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        //判断手机版本号是否需要申请权限
        if (android.os.Build.VERSION.SDK_INT < 23) {
            //新增SD卡读写权限申请
            return false;
        }
        return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams);
    }

    public interface OpenFileChooserCallBack {
        // for API - Version below 5.0.
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        // for API - Version above 5.0 (contais 5.0).
        boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                FileChooserParams fileChooserParams);
    }


}
