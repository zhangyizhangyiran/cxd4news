package com.cxd.cxd4android.shbbank.html;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.GhbJsInterface;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.widget.MyWebChomeClient;
import com.cxd.cxd4android.widget.X5WebView;
import com.micros.ui.webview.X5WebViewDownLoadListener;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chengelhaltud on 17/9/1.
 */

public class WebViewActivity extends BaseActivity implements MyWebChomeClient.OpenFileChooserCallBack {


    @Bind(R.id.Btn_left)
    TextView BtnLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.x5webview)
    X5WebView x5webview;

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_web_view);
        ButterKnife.bind(this);

        setWindow();

        setX5WebView();

        getData();

    }

    //设置窗口
    private void setWindow() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                getWindow()
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设置X5WebView
    private void setX5WebView() {
        X5WebView.setSmallWebViewEnabled(true);
        //监听下载文件事件触发此方法
        x5webview.setDownloadListener(new X5WebViewDownLoadListener(this));

        //新增的方法适配webview文件上传 zhangyi
        x5webview.setWebChromeClient(new MyWebChomeClient(this));
        x5webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.getInstance().sync();
                } else {
                    CookieManager.getInstance().flush();
                }
            }

        });

    }

    //获取数据
    private void getData() {
        WebViewModel webViewModel = (WebViewModel) getIntent().getExtras().get("WebViewModel");
        tvTitle.setText(webViewModel.getTitle());
        BtnLeft.setVisibility(View.VISIBLE);
        type = webViewModel.getType();


        if (type.equals("type_shb_loaddata")) {
            x5webview.loadData(webViewModel.getUrl(), "text/html", "utf-8");
            if ("shresetBankCard".equals(webViewModel.getIdentify())) {
                x5webview.addJavascriptInterface(new GhbJsInterface(this), webViewModel.getIdentify());
            }

        } else if (type.equals("type_shb_loadurl")) {
            String SHBurl;
            String url = webViewModel.getUrl();
            if ("shresetBankCard".equals(webViewModel.getIdentify())) {
                x5webview.addJavascriptInterface(new GhbJsInterface(this), webViewModel.getIdentify());
            }
            if (url.contains("?")) {
                SHBurl = url + "&userId=" + userModel.getid() + "&token=" + userModel.getHeader() + "&clientid=" + "android-" + userModel.getAppVersionName();
            } else {
                SHBurl = url + "?userId=" + userModel.getid() + "&token=" + userModel.getHeader() + "&clientid=" + "android-" + userModel.getAppVersionName();
            }
            x5webview.loadUrl(SHBurl);
        }

    }


    @OnClick(R.id.Btn_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:


                if (type.equals("type_shb_loaddata")) {
                    finish();
//                    goBackPage();
                } else if (type.equals("type_shb_loadurl")) {
//                    goBackPage();
                    finish();
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (type.equals("type_shb_loaddata")) {
                finish();
            } else {
//                goBackPage();
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 处理WebView 中多级页面返回问题
     **/
    private void goBackPage() {
        if (x5webview.canGoBack()) {
            x5webview.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            //判断隐藏软键盘是否弹出
            WindowManager.LayoutParams params = getWindow().getAttributes();
            if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
                // 隐藏软键盘
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
            }
            //结束退出程序
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (x5webview != null) {
            x5webview.setVisibility(View.GONE);
            x5webview.destroy();
        }
        super.onDestroy();
        finish();
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {

    }

    @Override
    public boolean openFileChooserCallBackAndroid5(com.tencent.smtt.sdk.WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
    }

}
