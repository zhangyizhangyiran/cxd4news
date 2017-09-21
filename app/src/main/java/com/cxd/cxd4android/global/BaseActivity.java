/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cxd.cxd4android.global;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;
import com.cxd.cxd4android.injection.module.ActivityModule;
import com.cxd.cxd4android.injection.module.ContainerModule;
import com.cxd.cxd4android.widget.toolbar.SystemBarTintManager;
import com.micros.ui.widget.MicroAVLIDialog;
import com.micros.utils.T;

import javax.inject.Inject;

/**
 * @version V1.0
 * @ClassName: BaseActivity
 * @Description: 描述：继承这个BaseActivity使你的Activity拥有一个程序框架.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:42:25
 */
public class BaseActivity extends FragmentActivity {


    Activity mActivity;
    public T T;
    public MicroAVLIDialog P;

    @Inject
    public LocalUserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        然后在setconentView后面加这句话：
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (android.os.Build.VERSION.SDK_INT > 18) {

            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 激活导航栏设置
            tintManager.setNavigationBarTintEnabled(true);

            // 设置一个颜色给系统栏
            tintManager.setTintColor(Color.parseColor("#FF3e64b5"));
            //tintManager.setTintResource(R.mipmap.ic_launcher);

            // set a custom navigation bar resource
            //tintManager.setNavigationBarTintResource(R.drawable.bg_ad);
            // set a custom status bar drawable
            //tintManager.setStatusBarTintDrawable(MyDrawable);

            tintManager.setTintColor(Color.alpha(250));
        }

//        if (android.os.Build.VERSION.SDK_INT > 11) {
//            ActionBar actionBar = getActionBar();
//            actionBar.hide();
//        }


        BaseApplication.TopAct = this;

        T = new T(this);

        P = new MicroAVLIDialog(BaseActivity.this);

        userModel = new LocalUserModel();

    }

    /**
     * Fragment添加控制
     *
     * @param containerViewId
     * @param fragment
     * @param stackName
     */
    public void add(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, tag).commitAllowingStateLoss();
    }

    public void replace(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).addToBackStack(stackName).commitAllowingStateLoss();
    }

    /**
     * Fragment移除控制
     *
     * @param tag
     */
    public void remove(String tag) {

        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(tag)).commitAllowingStateLoss();
    }

    //当前新订单状态是否是打开的  false:还没有打开   true:已经打开了
    public boolean BA_NOTI_NEWORDER_STATUS = false;

    /**
     * 开启刷新
     */
    protected void startRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 结束刷新
     */
    protected void stopRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        BaseApplication.TopAct = this;
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        BaseApplication.TopAct = this;
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }


    /**
     * 申请权限
     *
     * @param activity
     */
    public void checkSelfPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    0);
            userModel.setREAD_PHONE_STATE("");
        } else {
            userModel.setREAD_PHONE_STATE("READ_PHONE_STATE");
        }
    }

    /**
     * 处理权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    userModel.setREAD_PHONE_STATE("READ_PHONE_STATE");
                } else {
                    userModel.setREAD_PHONE_STATE("");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            default:
                break;
        }
    }

}
