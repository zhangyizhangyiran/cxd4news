package com.cxd.cxd4android.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.widget.X5WebView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */

public class MeWithdrawWithdrawDetailsFragment extends BaseFragment {


    /** 体现说明正中间标题 **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /** 体现说明左上角返回键 **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;

    /** 体现说明 **/
    @Bind(R.id.details_wb_withdraw_details)
    X5WebView details_wb_withdraw_details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_withdraw_details, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("提现说明");
        Btn_left.setVisibility(View.VISIBLE);
//
//        WebSettings webSettings = details_wb_withdraw_details.getSettings();
//        webSettings.setSupportZoom(true);
////        webSettings.setDisplayZoomControls(true);
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettings.setLoadWithOverviewMode(true);//设置屏幕自适应
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setBuiltInZoomControls(true);

        details_wb_withdraw_details.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
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
        });
        details_wb_withdraw_details.loadUrl(Constant.WEBBASEAPI+"/h5c/recharge");
    }

    //点击
    @OnClick( {R.id.Btn_left})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
                remove("MeWithdrawWithdrawDetailsFragment");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "提现说明");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "提现说明");//(this);
    }

}
