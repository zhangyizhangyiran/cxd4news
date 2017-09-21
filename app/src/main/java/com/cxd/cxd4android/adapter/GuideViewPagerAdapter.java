package com.cxd.cxd4android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.MainActivity;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.micros.utils.D;
import com.micros.utils.S;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @{# ViewPagerAdapter.java Create on 2015-3-26
 * class desc: 引导页面适配器
 * Copyright: Copyright(c) 2013
 * @Author Rongxh
 */
public class GuideViewPagerAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views;
    private Context activity;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private TextView mStartWeiboImageButton;
    private TextView iv_start_register;

    public GuideViewPagerAdapter(List<View> views, Context activity) {
        this.views = views;
        this.activity = activity;
        EventBus.getDefault().register(this);
    }

    // 销毁arg1位置的界面
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    // 获得当前界面数
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }


    @Subscribe
    public void onEvent(String s) {
        if (s.equals("cxd")) {
            EventBus.getDefault().post(new ILoginCallBack(4));
            EventBus.getDefault().unregister(this);
            ((Activity) activity).finish();
        }

    }

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(View arg0, final int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            mStartWeiboImageButton = (TextView) arg0.findViewById(R.id.iv_start_weibo);

            iv_start_register = (TextView) arg0.findViewById(R.id.iv_start_register);


            iv_start_register.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    S.put(activity,"k","first");
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    setGuided();

                }

            });

            mStartWeiboImageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置已经引导
                    setGuided();
                    goHome();
                }
            });

        }

        return views.get(arg1);
    }

    private void goHome() {
        // 跳转
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        try {
            EventBus.getDefault().post(new ILoginCallBack(4));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        ((Activity) activity).finish();

    }

    /**
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
//        LocalUserModel userModel = LocalUserModel.getInstance();
//        userModel.setIsFirstIn("No");

        SharedPreferences preferences = activity.getSharedPreferences(SHAREDPREFERENCES_NAME, activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // 存入数据
        editor.putString("IsFirstIn", "No");
        // 提交修改
        editor.commit();
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }
}
