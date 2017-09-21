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

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.multidex.MultiDex;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.bugtags.library.Bugtags;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

/**
 * @version V1.0
 * @ClassName: BaseApplication
 * @Description: BaseApplication
 * @Author：Chengel_HaltuD
 * @Date：2015-6-24 上午10:00:00
 */
public class BaseApplication extends Application {

    private static BaseActivity mContext;
    /**
     * BaseApplication
     **/
    public static BaseApplication mApp;

    /**
     * 当前处于栈顶的元素
     **/
    public static BaseActivity TopAct;

    /**
     * 添加Activity集合
     **/
    private List<Activity> ActList = new LinkedList<Activity>();

    /**
     * 友盟推送代理
     **/
    private static final String TAG = BaseApplication.class.getName();
    public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";

    private volatile boolean isX5WebViewEnabled = false;


    @Inject
    LocalUserModel userModel;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //Log开关
        Logger.init("'Chengel_HaltuD").logLevel(LogLevel.FULL);

        //在这里初始化漏洞统计
        Bugtags.start("1c48e512701e7fcc061f326cbb580886", this, Bugtags.BTGInvocationEventNone);
        //在这里初始化百度统计
        initBaiDu();

//        Intent i1 = new Intent(this,Service1.class);
//        bindService(i1, conn, Context.BIND_AUTO_CREATE);
//
//        Intent i2 = new Intent(this,Service2.class);
//        bindService(i2, conn, Context.BIND_AUTO_CREATE);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //TbsDownloader.needDownload(getApplicationContext(), false);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Logger.i("0912" + " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {


            @Override
            public void onDownloadFinish(int i) {
                Logger.i("0912" + "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Logger.i("0912" + "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Logger.i("0912" + "onDownloadProgress:" + i);
            }
        });
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.PRELOAD_ONLY, cb);

    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
//            MyBinder binder = (MyBinder)service;
//            Service2 bindService = binder.getService();
//            bindService.MyMethod();
//            flag = true;
        }
    };

    private void initBaiDu() {
        // 设置AppKey
        StatService.setAppKey("7ae772aa80"); // appkey必须在mtj网站上注册生成，该设置建议在AndroidManifest.xml中填写，代码设置容易丢失

		/*
         * 设置渠道的推荐方法。该方法同setAppChannel（String）， 如果第三个参数设置为true（防止渠道代码设置会丢失的情况），将会保存该渠道，每次设置都会更新保存的渠道，
		 * 如果之前的版本使用了该函数设置渠道，而后来的版本需要AndroidManifest.xml设置渠道，那么需要将第二个参数设置为空字符串,并且第三个参数设置为false即可。
		 * appChannel是应用的发布渠道，不需要在mtj网站上注册，直接填写就可以 该参数也可以设置在AndroidManifest.xml中
		 */

        StatService.setAppChannel(this, getApplicationMetaData("CXD_CHANNEL_NAME"), true);
        // 测试时，可以使用1秒钟session过期，这样不断的间隔1S启动退出会产生大量日志。
        StatService.setSessionTimeOut(30);
        // setOn也可以在AndroidManifest.xml文件中填写，BaiduMobAd_EXCEPTION_LOG，打开崩溃错误收集，默认是关闭的
        StatService.setOn(this, StatService.EXCEPTION_LOG);
		/*
		 * 设置启动时日志发送延时的秒数<br/> 单位为秒，大小为0s到30s之间<br/> 注：请在StatService.setSendLogStrategy之前调用，否则设置不起作用
		 *
		 * 如果设置的是发送策略是启动时发送，那么这个参数就会在发送前检查您设置的这个参数，表示延迟多少S发送。<br/> 这个参数的设置暂时只支持代码加入，
		 * 在您的首个启动的Activity中的onCreate函数中使用就可以。<br/>
		 */
        StatService.setLogSenderDelayed(0);
		/*
		 * 用于设置日志发送策略<br /> 嵌入位置：Activity的onCreate()函数中 <br />
		 *
		 * 调用方式：StatService.setSendLogStrategy(this,SendStrategyEnum. SET_TIME_INTERVAL, 1, false); 第二个参数可选：
		 * SendStrategyEnum.APP_START SendStrategyEnum.ONCE_A_DAY SendStrategyEnum.SET_TIME_INTERVAL 第三个参数：
		 * 这个参数在第二个参数选择SendStrategyEnum.SET_TIME_INTERVAL时生效、 取值。为1-24之间的整数,即1<=rtime_interval<=24，以小时为单位 第四个参数：
		 * 表示是否仅支持wifi下日志发送，若为true，表示仅在wifi环境下发送日志；若为false，表示可以在任何联网环境下发送日志
		 */
        StatService.setSendLogStrategy(this, SendStrategyEnum.SET_TIME_INTERVAL, 1, false);
        // 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
        StatService.setDebugOn(false);
    }

    /**
     * 返回BaseApplication
     */
    public static BaseApplication getInstance() {
        return mApp;
    }


    /**
     * 拍照的照片存储位置
     **/
    public static File PHOTO_DIR = null;


    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        ActList.add(activity);
    }

    /**
     * 退出Activity
     */
    public void exit() {
        try {
            for (Activity activity : ActList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.exit(0);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.i("应用退出");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 1、官网:cc044b21bfba95aed0cfa42393db9dcf424e587b
     * 2、神马:f881d488120d904f3ec21ff1885a890e5d854098
     * 3、百度:0cc2662f5eb157c8ffcd43c145de499f2ab27a71
     * 4、网易:b3db930f8f21f945289061a6d909da749c141773
     * 5、陌陌:f90a13fdb95f8b4a4afb377985333aec0986ecdc
     * 6、新浪:99d87ac28dd9559a70b381c14d0f01f3cfb7e32e
     * 7、粉丝通:8251bde00dac83cf575f34703c6c8636b7a83b43
     * 8、官网公众号:890959f2acab71f6b9ffb282fec08a181a0f52f6 (微信)weixin
     * 9、网联亿信	e4904f7a7bda77124ab19ea3447ba551e596f018	wlxy
     * 10、搜狐汇算 6550c9eedd062d6f0a350ec481df784a778934f8 	shhs
     * 11、线下渠道 f580af19ff63064cad8f09aa5cef89cfa17c8eba     xxqd
     * 12、中华万年历 a56b199b2084030e0d76ba4ffa6fa86a3e1e43a4
     * 13、微车	90befa07ba7f732a4389ce523727dfba12d83c50	wc(910)
     * 14、车主无忧 64b4c445347216f96c3404a3981b2226e3ef1211
     * 15、今日头条 9720908f31411d298f61ecbae7d845b61a465070   jrtt
     * <p>
     * <p>
     * 1、360手机助手:a7cc241fdc2f332707f21ef67602efaab6beead9
     * 2、应用宝:53d1c8ba2bd708c9089d3a3cb2e6254418ed2e6f
     * 3、豌豆荚:328c6599cab9ab6fa7d531563117ded8543dcacb
     * 4、小米应用市场:d969727c153387b6ae0cb49299cb8ed1e053dc81
     * <p>
     * <p>
     * //暂时不打包
     * 1、盘石:0f03a7f7363322c37506a782dc99b9d37b07c192
     * 2、鹏吉:b10483e632cfc30bc930ba41919e2f1f4d943e8f
     * 3、有道:9b98e12b4cc068b0a35930dbf2ff6d0b3297f516
     */
    public String getApplicationMetaData(String valuve) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String ss = appInfo.metaData.getString(valuve);
        return ss;
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

}
