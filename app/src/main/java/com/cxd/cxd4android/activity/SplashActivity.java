package com.cxd.cxd4android.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.jpush.ExampleUtil;
import com.cxd.cxd4android.model.ShareMallModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.SplashPresenter;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

/**
 * @{# SplashActivity.java Create on 2015-3-26
 * class desc: 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 * (2)是，则进入GuideActivity；否，则进入LoginActivity (3)3s后执行(2)操作
 * @Author Rongxh
 */
public class SplashActivity extends BaseActivity implements LoadingView {
    String IsFirstIn = "";
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_OnResume = 1002;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 500;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private View view;


    public static boolean isForeground = false;
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private volatile boolean isX5WebViewEnabled = false;

    private TimeCount time;
    ImageView img_ad;
    TextView text_right;
    SplashPresenter splashPresenter;
    LocalUserModel userModel;
    /**
     * Handler:跳转到不同界面
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
//                    goHome();
//                    goAdImage();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
            + "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
            + "(\\w*:)*(\\w*\\+)*(\\w*\\.)*"
            + "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashpage);
        userModel = new LocalUserModel();
        splashPresenter = new SplashPresenter(this);

        img_ad = (ImageView) findViewById(R.id.splash_img_pic);
        text_right = (TextView) findViewById(R.id.splash_act_right_text);

        final EditText editText = new EditText(this);

        /*if (("").equals(userModel.getDebug())) {

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("请输入测试地址" + "\n" + Constant.WEBBASEAPI)
                    .setIcon(R.mipmap.ic_launcher)
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!Q.isEmpty(editText.getText().toString().trim())) {

                                if (!Regular(editText.getText().toString().trim(), URL)) {
                                    Constant.WEBBASEAPI = editText.getText().toString().trim();
                                    Toast.makeText(SplashActivity.this, "执行啦!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            inits();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("关闭调试模式", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userModel.setDebug("false");
                            inits();
                            dialog.dismiss();
                        }
                    })
                    .show();

        }else {
            inits();
        }*/
        inits();

        final Intent intent = getIntent();
        final Uri uri = intent.getData();
        setTitle(String.valueOf(uri));

        registerMessageReceiver();//注册极光推送广播

    }


    private void inits() {
        splashPresenter.loadData("");
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        IsFirstIn = preferences.getString("IsFirstIn", "");

        if (!TextUtils.isEmpty(IsFirstIn)) {
            // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
            // 使用Handler的postDelayed方法，1秒后执行跳转到MainActivity
//            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);

        } else {

            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    /*显示启动页广告图片*/
    private void goAdImage(String start_page_url) {
        Glide.with(this).load(start_page_url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                text_right.setVisibility(View.VISIBLE);
                time = new TimeCount(4000, 1000);//构造CountDownTimer对象
                time.start();
                return false;
            }
        }).into(img_ad);
        //点击跳过直接跳转到主页面
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.cancel();
                goHome();
            }
        });

    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(ShareMallModel.class)) {
            ShareMallModel shareMallModel = (ShareMallModel) model;
            if (Constant.STATUS_SUCCESS.equals(shareMallModel.status)) {
                ShareMallModel result = shareMallModel.result;
                //判断图片地址是否为空（广告页面）
                if (!Q.isEmpty(result.start_page_url)) {
                    goAdImage(result.start_page_url);
                } else {
                    goHome();
                }
                setPicUrl(result);
                setAppShowType(result);
            } else {
                setPicUrl(null);
                goHome();
            }
        }
    }

    /**
     * 设置app首页&积分商城展示类型 ：h5&native
     **/
    private void setAppShowType(ShareMallModel result) {
        if (!Q.isEmpty(result)) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("userId", userModel.getid());//
//
//            Gson gson = new Gson();
//            String strn = gson.toJson(map);
            if (!Q.isEmpty(result.app_index_middle_show_type)) {//app首页中间推荐展现形式
                if (Constant.NATIVE.equals(result.app_index_middle_show_type)) {
                    Constant.boutMiddleType = Constant.NATIVE;//当返回字段为native时，传给Constant.boutMiddleType
                } else if (Constant.H5.equals(result.app_index_middle_show_type)) {
                    Constant.boutMiddleType = Constant.H5;//当返回字段为native时，传给Constant.boutMiddleType
                }
            }
            if (!Q.isEmpty(result.app_my_point_show_type)) {//app积分商城展现形式
                if (Constant.NATIVE.equals(result.app_my_point_show_type)) {
                    Constant.myPointType = Constant.NATIVE;//当返回字段为native时，传给Constant.myPointType
                } else if (Constant.H5.equals(result.app_my_point_show_type)) {
                    Constant.myPointType = Constant.H5;//当返回字段为native时，传给Constant.myPointType
                }
            }
            Log.i("app_index_type->", result.app_index_middle_show_type);
        }
    }

    /**
     * 设置 我 页面图标
     **/
    public void setPicUrl(ShareMallModel shareMallModel) {
        if (shareMallModel != null) {
            Constant.my_invest = shareMallModel.my_invest;
            Constant.my_transfer = shareMallModel.my_transfer;
            Constant.my_account = shareMallModel.my_account;
            Constant.my_bill = shareMallModel.my_bill;
            Constant.my_calendar = shareMallModel.my_calendar;
            Constant.my_recommended = shareMallModel.my_recommended;
            Constant.my_piont = shareMallModel.my_piont;
            Constant.my_hongbao = shareMallModel.my_coupons;
            Constant.my_fuli = shareMallModel.my_vip;
            Constant.SHB_CHONGZHI = shareMallModel.sh_invest_h5_url;
            Constant.SHB_TIXIAN = shareMallModel.sh_withdraw_h5_url;


        }

    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        setPicUrl(null);
        goHome();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 验证码倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            String strRight = millisUntilFinished / 1000 + "秒 | 跳过";
            text_right.setText(strRight);

            if (millisUntilFinished / 1000 <= 1) {
                time.cancel();
                goHome();
            }
        }
    }

    //到主页面方法
    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
        overridePendingTransition(R.anim.activity_next_in_animation, R.anim.activity_next_out_animation);
    }

    //到引导页面方法
    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuidePageActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
        overridePendingTransition(R.anim.activity_next_in_animation, R.anim.activity_next_out_animation);
    }

    //zhz注册广播
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(this, "启动页面");//(this);
        isForeground = true;
        JPushInterface.onResume(this);
    }

    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(this, "启动页面");//(this);
        isForeground = false;

        JPushInterface.onPause(this);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
