package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.MainActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyBonusPointsActivity;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.JsInterface;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.widget.X5WebView;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cxd.cxd4android.global.BaiDustatistic.se;


/**
 * Created by moon.zhong on 2015/2/4.
 */

public class BoutBannerFragment extends BaseFragment {

    /**
     * 精品横幅正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 精品横幅左上角返回键
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;

    /**
     * 精品横幅精品横幅
     **/
    @Bind(R.id.banner_wb_new_notice)
     X5WebView banner_wb_new_notice;

    private BannerModel model;
    private LocalUserModel userModel;

    String title = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView=inflater.inflate(R.layout.fragment_layout_bout_banner,container,false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userModel = new LocalUserModel();


        model = (BannerModel) getArguments().getSerializable("BannerModel");
        Btn_left.setVisibility(View.VISIBLE);
        title = model.title;
        if (!Q.isEmpty(title)) {
            tv_title.setText(title);
        }

        banner_wb_new_notice.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                Logger.i("shouldOverrideUrlLoading---》" + url);
                //如果banner广告条包含 into_integral 跳转到我的积分页面
                if (url.contains("into_integral")) {

                    if (userModel.getLOGIN_STATE().equals(LocalUserModel.LOGIN_STATE_ONLINE)) {
                        Intent intent = new Intent(getActivity(), MeMyBonusPointsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().post(new ILoginCallBack(4));//跳转我
                    }
                    return true;
                }
                //如果banner广告条包含 into_invest 跳转到 投资页面
                if (url.contains("http://into_invest")) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    EventBus.getDefault().post(new ILoginCallBack(1));//跳转投资
                    return true;
                }
                //view.loadUrl(url);
                return false;
                //return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {

                Map<String, String> requestHeaders = webResourceRequest.getRequestHeaders();
                requestHeaders.put("TOKEN", userModel.getHeader());
                requestHeaders.put("sourceCode", BaseApplication.mApp.getApplicationMetaData("CXD_CHANNEL_ID"));
                requestHeaders.put("sourceType", "1");
                requestHeaders.put("useVersion", userModel.getAppVersionName());
                requestHeaders.put("thirdPay", userModel.getThirdPayType());
                requestHeaders.put("clientid", "android-" + userModel.getAppVersionName());
                Logger.i(webResourceRequest.getRequestHeaders().toString());
                return super.shouldInterceptRequest(webView, webResourceRequest);

            }
        });

        //判段url中是否包含 ‘？’
        String webUrl;
        if (model.url.contains("?")){
            webUrl =model.url + "&token="+userModel.getHeader()+"&clientid="+"android-" + userModel.getAppVersionName();
        }else {
            webUrl =model.url + "?token="+userModel.getHeader()+"&clientid="+"android-" + userModel.getAppVersionName();
        }
        banner_wb_new_notice.loadUrl(webUrl);

        banner_wb_new_notice.addJavascriptInterface(new JsInterface(getActivity()),"native");
    }

    //点击
    @OnClick(R.id.Btn_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                if (banner_wb_new_notice.canGoBack()) {
                    banner_wb_new_notice.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    //判断隐藏软键盘是否弹出
                    WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                    if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
                        // 隐藏软键盘
                        getActivity().getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
                    }
                    //结束退出程序
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }

    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), title);//(this);
    }

    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), title);//(this);
    }

    @Override
    public void onDestroyView() {
        if (banner_wb_new_notice != null) {
            banner_wb_new_notice.setVisibility(View.GONE);
            banner_wb_new_notice.destroy();
        }
        super.onDestroyView();
    }
    // 返回键按下时会被调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (banner_wb_new_notice.canGoBack()) {
                banner_wb_new_notice.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                getActivity().finish();
            }

        }
        return false;
    }


}
