package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.global.SendYeePayMsg;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.interfaces.IUpdataRechargeCallBack;
import com.cxd.cxd4android.interfaces.IUpdataWithdrawCallBack;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.widget.X5WebView;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class WebViewFragment extends BaseFragment {

    /**
     * WebView正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * WebView左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * WebView
     **/
    @Bind(R.id.webview_wb_web_view)
     X5WebView webview_wb_web_view;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 支付信息
     **/
    YeePayModel payModel;
    private String BaiDuTongJi = "";
    public static com.cxd.cxd4android.interfaces.IUpdataInfoCallBack IUpdataInfoCallBack;

    /**
     * 发送易宝日志网页源码
     **/
    public String html = "";
    /**
     * 发送易宝日志method
     **/
    public String method = "";
    /**
     * 发送易宝日志body
     **/
    public String data = "";

    public String YeePayurl = "";
    public boolean isFirst = false;
    SendYeePayMsg sendYeePayMsg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_web_view, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        payModel = (YeePayModel) getArguments().getSerializable("YeePayModel");

        if (!Q.isEmpty(payModel.title)) {
            tv_title.setText(payModel.title);
            BaiDuTongJi = payModel.title;
        }
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        sendYeePayMsg = new SendYeePayMsg();
        //设置数据
        setData();

    }

    /**
     * 设置数据
     */
    @SuppressLint("AddJavascriptInterface")
    private void setData() {
//        WebSettings webSettings = webview_wb_web_view.getSettings();
//        webSettings.setSupportZoom(true);
////        webSettings.setDisplayZoomControls(true);
//
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettings.setLoadWithOverviewMode(true);//设置屏幕自适应
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setJavaScriptEnabled(true);

        webview_wb_web_view.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
//        webview_wb_web_view.addJavascriptInterface(new InJavaScripthandler(), "handler");
        webview_wb_web_view.getSettings().setUseWideViewPort(false);

        webview_wb_web_view.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                method = request.getMethod();
                Logger.i("------shouldInterceptRequest----" + request.getUrl().toString());
                return super.shouldInterceptRequest(view, request);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.i("------isIntercept----" + payModel.isIntercept);
                Logger.i("------callbackUrl----" + payModel.callbackUrl);
                if (("true").equals(payModel.isIntercept)) {
                    if ((payModel.callbackUrl).equals(url)) {
//                        IUpdataInfoCallBack.OnUpdataInfo("Success");
//                        remove("WebViewFragment");
                        return false;
                    }
                }else {

                }

                view.loadUrl(url);
                Logger.i("------shouldOverrideUrlLoading----" + url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                view.loadUrl(String.format("javascript:window.handler.showss(document.body.innerHTML);"));

                if (("true").equals(payModel.isIntercept)) {
                    if ((payModel.callbackUrl).equals(url)) {
                        EventBus.getDefault().post(new IUpdataInfoCallBack("Success"));
//                        remove("WebViewFragment");
                        getActivity().finish();
                        return;
                    }
                }

                super.onPageStarted(view, url, favicon);



                Logger.i("------onPageStarted----" + url);


                YeePayurl = url;
                //https://ok.yeepay.com/uucharge/wap/first/cardinfo

                if (url.contains(Constant.fillterStr)) {
                    if (isFirst) {
                        Logger.i("------发送----loading");

                        //发送易宝app日志
                        sendYeePayMsg.sendYeePayMsg(context, payModel.title, url, method, data, "loading", html);
                    } else {
                        if (Constant.actionUrl.equals(url)) {
                            Logger.i("------发送----loading");
                            //发送易宝app日志
                            sendYeePayMsg.sendYeePayMsg(context, payModel.title, url, method, data, "loading", html);
                        }
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                YeePayurl = url;
                view.loadUrl(String.format("javascript:window.local_obj.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');"));

                super.onPageFinished(view, url);

                Logger.i("------onPageFinished----" + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Logger.i("-----onReceivedError-----" + failingUrl);
                Logger.i("-----onReceivedError-----" + description);
            }

        });
        webview_wb_web_view.loadData(payModel.url, "text/html", "utf-8");
//        webview_wb_web_view.loadDataWithBaseURL(Constant.WEBBASEAPI, payModel.url, "text/html", "utf-8", null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //易宝返回前请求
        sendYeePayMsg.returnYeePayMsg();
        Logger.i("------发送----return");
        //发送易宝app日志
        sendYeePayMsg.sendYeePayMsg(context, payModel.title, YeePayurl, method, data, "return", html);


        if (("投资").equals(payModel.title)) {
//            EventBus.getDefault().post(new IUpdataInfoCallBack("BuyProductInvestSuccess"));
            EventBus.getDefault().post(new IUpdataRechargeCallBack("WebViewFragment"));
        } else if (("实名认证").equals(payModel.title)) {
            if ((payModel.callbackUrl).equals(YeePayurl)) {
                EventBus.getDefault().post(new IUpdataInfoCallBack("Refresh"));
            }
        } else if (("充值").equals(payModel.title)) {
            EventBus.getDefault().post(new IUpdataInfoCallBack("BuyProductRechargeSuccess"));

        } else if (("提现").equals(payModel.title)) {
            EventBus.getDefault().post(new IUpdataWithdrawCallBack("WebViewFragment"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick(R.id.Btn_left)
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                remove("WebViewFragment");
                getActivity().finish();
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
        StatService.onPageStart(getActivity(), "易宝" + BaiDuTongJi);//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "易宝" + BaiDuTongJi);//(this);
    }


    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String htmlA) {
            Logger.i("HTML:" + htmlA);
            html = htmlA;
            if (YeePayurl.contains(Constant.fillterStr)) {
                if (isFirst) {
                    Logger.i("------发送----complete");
                    //发送易宝app日志
                    sendYeePayMsg.sendYeePayMsg(context, payModel.title, YeePayurl, method, data, "complete", html);
                } else {
                    if (Constant.actionUrl.equals(YeePayurl)) {
                        Logger.i("------发送----complete");
                        //发送易宝app日志
                        sendYeePayMsg.sendYeePayMsg(context, payModel.title, YeePayurl, method, data, "complete", html);
                        isFirst = true;
                    }
                }
            }
        }
    }

//    final class InJavaScripthandler {
//        @JavascriptInterface
//        public void showss(String body) {
//            L.I("HTML:body:"+body);
//            data = body;
////            if (bodyBegin){
//                //发送易宝app日志
////                sendYeePayMsg.sendYeePayMsg(context, payModel.title, YeePayurl, method, data, "loading", html);
////            }
//
//        }
//    }
}
