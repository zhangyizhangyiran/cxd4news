package com.cxd.cxd4android.activity.subactivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IGiftExSucCallBack;
import com.cxd.cxd4android.interfaces.ILuckConfirmCallBack;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.widget.share.ShareContentCustomizeDemo;
import com.cxd.cxd4android.widget.share.ShareMall;
import com.cxd.cxd4android.widget.X5WebView;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * ClassName:积分抽奖
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 13:40
 * version：V1.0
 */
public class MyBsIntegralLuckDrawActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 抽奖webview
     **/
    @Bind(R.id.luckdraw_luck_web)
    X5WebView webview;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;

    private String url = "",
            contents = "我在诚信贷积分商城抽中了“XX”，你猜我抽了几次？",
            title = "诚信贷";

    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_luck_draw);
        ButterKnife.bind(this);

        userModel = new LocalUserModel();
        //初始化状态栏title
        initStatusBar();
        //加载为web数据并显示
        initWebDataShow();


        EventBus.getDefault().register(this);
    }

    //接口回调，确认地址后 重新加载web页面数据
    @Subscribe
    public void onEvent(ILuckConfirmCallBack myEvent){
        Logger.i("mTextSubscriber>>>" + "myEvent");
        //加载为web数据并显示
        initWebDataShow();
    }

    @Override
    protected void onDestroy() {
        if (webview!=null)
            webview.destroy();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载web页面数据
     **/
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebDataShow() {
//        WebSettings webSettings = webview.getSettings();
//        //设置WebView属性，能够执行Javascript脚本
//        webSettings.setJavaScriptEnabled(true);
//        //设置可以访问文件
//        webSettings.setAllowFileAccess(true);
//        //设置支持缩放
//        webSettings.setBuiltInZoomControls(true);
//        //加载需要显示的网页
//        webSettings.setSupportZoom(true);
////        webSettings.setDisplayZoomControls(true);
//        webSettings.setLoadWithOverviewMode(true);//设置屏幕自适应


        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("TOKEN", userModel.getHeader());
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                Map<String, String> requestHeaders = request.getRequestHeaders();
//                requestHeaders.put("TOKEN", userModel.getHeader());

                //L.I("shouldInterceptRequest->"+request.getUrl().toString());
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Logger.i("shouldOverrideUrlLoading---》" + url);
                if (url.contains("confirm") && url.contains("uphId")) {

                    String uphId = url.substring(url.indexOf("=") + 1);

                    Intent intentAddr = new Intent(MyBsIntegralLuckDrawActivity.this, MyBsShipAddrActivity.class);
                    intentAddr.putExtra("uphId", uphId);
                    intentAddr.putExtra("page_id", "LuckDraw");
                    startActivity(intentAddr);
                    return true;
                } else if (url.contains("share")) {
                    contents = url.substring(url.indexOf("title=") + 6);
                    try {
                        contents = URLDecoder.decode(contents, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (url.contains("qzone")) {
                        showShare(QZone.NAME);

                    } else if (url.contains("moment")) {
                        showShare(WechatMoments.NAME);

                    } else if (url.contains("weibo")) {
                        showShare(SinaWeibo.NAME);

                    }
                    return true;
                } else if (url.contains("more")) {
                    IntegralRulesFragment();
                    return true;
                }
                //view.loadUrl(url);
                return false;

            }
        });
        webview.loadUrl(Constant.pointc_pointLuckDraw + userModel.getid(), extraHeaders);
        Logger.i("token===" + userModel.getHeader());
        Logger.i("url===" + Constant.pointc_pointLuckDraw + userModel.getid());

    }

    /**
     * 积分规则
     */
    public void IntegralRulesFragment(){

        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "积分规则";
        bannerModel.url =  Constant.integral_rules;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(MyBsIntegralLuckDrawActivity.this, BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    private void initStatusBar() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_back = (TextView) findViewById(R.id.Btn_left);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("积分抽奖");
        tv_right.setText("抽奖记录");


        tv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @OnClick({R.id.Btn_left,R.id.tv_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Btn_left:
                if (webview.canGoBack()) {
                    webview.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    finish();//结束退出程序

                    //设置接口回调
                    EventBus.getDefault().post(new IGiftExSucCallBack("MyBsIntegralLuckDrawActivity"));
                }
                break;
            case R.id.tv_right:
                SetMyBsDrawPrize();
                break;
        }
    }

    /**
     * 跳转到积分抽奖记录页面
     *
     * @param
     * @param
     */
    private void SetMyBsDrawPrize() {
        Intent i = new Intent(this, MyBsDrawPrizeActivity.class);
        startActivity(i);
    }

    /**
     * 弹出分享
     */
    private void showShare(String PaltfromName) {
        url= ShareMall.shareMallUrl;
        ShareSDK.initSDK(MyBsIntegralLuckDrawActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(contents + "");//
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("诚信贷理财产品真的很棒!");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

//        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        Platform platform = ShareSDK.getPlatform(PaltfromName);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        oks.setPlatform(PaltfromName);

        // 参考代码配置章节，设置分享参数
        //通过OneKeyShareCallback来修改不同平台分享的内容
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo(MyBsIntegralLuckDrawActivity.this, contents));

//        oks.setFilePath("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");
//        oks.setImageUrl("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");

        // 启动分享GUI
        oks.show(MyBsIntegralLuckDrawActivity.this);
    }
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }else {
            finish();//结束退出程序
            //设置接口回调
            EventBus.getDefault().post(new IGiftExSucCallBack("MyBsIntegralLuckDrawActivity"));
        }
        return false;
    }


}
