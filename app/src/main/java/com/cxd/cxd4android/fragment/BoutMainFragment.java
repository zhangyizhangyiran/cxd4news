package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.BuyProductActivity;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ISwipeRefreshCallBack;
import com.cxd.cxd4android.model.AppBoutMiddleModel;
import com.cxd.cxd4android.model.BannerBaseModel;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.CheckVersionBaseModel;
import com.cxd.cxd4android.model.NewerBaseModel;
import com.cxd.cxd4android.presenter.BounsMainFragmentPresenter;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.widget.dialog.CheckVersionDialog;
import com.cxd.cxd4android.widget.seekbar.NumberSeekBar;
import com.cxd.cxd4android.widget.viewpager.CycleViewPager;
import com.cxd.cxd4android.widget.viewpager.ViewFactory;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

import static com.tencent.smtt.utils.j.a;

/**
 * Created by moon.zhong on 2015/2/4.
 */

public class BoutMainFragment extends BaseFragment implements LoadingView {

    /**
     * 转换率
     **/
    @Bind(R.id.boutfra_tv_project_rate)
    TextView boutfra_tv_project_rate;
    /**
     * 转换率百分号
     **/
    @Bind(R.id.boutfra_tv_project_prec)
    TextView boutfra_tv_project_prec;

    /**
     * 起投
     **/
    @Bind(R.id.boutfra_tv_min_inves)
    TextView boutfra_tv_min_inves;
    /**
     * 期数
     **/
    @Bind(R.id.boutfra_tv_min_deadline)
    TextView boutfra_tv_min_deadline;

    /**
     * 加息
     **/
    @Bind(R.id.boutfra_tv_jiaxi)
    TextView boutfra_tv_jiaxi;
    /**
     * 期型
     **/
    @Bind(R.id.boutfra_tv_min_unit)
    TextView boutfra_tv_min_unit;
    /**
     * 期型
     **/
    @Bind(R.id.bouts_swiperefresh)
    SwipeRefreshLayout bouts_swiperefresh;
    @Bind(R.id.bout)
    Button bout;
    @Bind(R.id.seekbar)
    NumberSeekBar pb;
    /**
     * h5展示图片
     **/
    @Bind(R.id.bouts_middle_image)
    ImageView bouts_middle_image;
    /**
     * middle视图
     **/
    @Bind(R.id.item_hot_recommend)
    RelativeLayout item_hot_recommend;
    /**
     * middle视图h5
     **/
    @Bind(R.id.item_middle_image)
    RelativeLayout item_middle_image;

    CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<BannerModel> BannerModel = new ArrayList<BannerModel>();
    /**
     * 用户信息
     */
    LocalUserModel userModel;
    /**
     * 新手标
     */
    NewerBaseModel newerModel;
    BounsMainFragmentPresenter bounsMainPresenter;
    private String strn;
    private String boutMiddlePicUrl = "";
    private String boutMiddleTitle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_bouts_main, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        userModel = new LocalUserModel();
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());

        Gson gson = new Gson();
        strn = gson.toJson(map);

        bounsMainPresenter = new BounsMainFragmentPresenter(this);
        initBanner();
        //进入首页判断native&&h5
        setIsNativeOrH5();
        initProgressbar();//设置进度条
        initData();//请求热门推荐数据

        //版本检测
        CheckVersion();

        //定时刷新
        setTimer();
        //xia下拉刷新
        setSwipeRefresh();

    }

    /**
     * 判断状态是native或h5
     **/
    private void setIsNativeOrH5() {
        if (Constant.NATIVE.equals(Constant.boutMiddleType)) {
            item_hot_recommend.setVisibility(View.VISIBLE);
            item_middle_image.setVisibility(View.GONE);

        } else if (Constant.H5.equals(Constant.boutMiddleType)) {
            item_hot_recommend.setVisibility(View.GONE);
            item_middle_image.setVisibility(View.VISIBLE);
            //app首页中间h5 ImageView图设置
            bounsMainPresenter.loadBountMiddleH5(strn);

        } else {
            item_hot_recommend.setVisibility(View.VISIBLE);
            item_middle_image.setVisibility(View.GONE);
        }
    }


    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(ISwipeRefreshCallBack event) {
        bouts_swiperefresh.setEnabled(event.getStatus());
    }

    /**
     * 下拉刷新方法
     */

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setSwipeRefresh() {
        bouts_swiperefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        bouts_swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
//                        initBanner();
                    }
                }, 200);

            }
        });
    }

    /**
     * 定时刷新
     */
    private void setTimer() {


        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    initData();
                }
                super.handleMessage(msg);
            }

            ;
        };
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // 需要做的事:发送消息
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 1000000, 1000000); // 1s后执行task,经过1s再次执行
    }

    /**
     * 版本检测
     */
    private void CheckVersion() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("packageName", userModel.getAppPackageName());
//		map.put("version", version);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        bounsMainPresenter.loadCheckVersion(strn);

    }

    /**
     * 请求Banner
     */
    private void initBanner() {
        bounsMainPresenter.loadBanner(strn);
    }

    /**
     * 请求数据
     */
    private void initData() {

        bounsMainPresenter.loadData(strn);
    }

    @OnClick({R.id.boutfra_bt_make_money, R.id.bouts_middle_image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.boutfra_bt_make_money:// 马上赚钱
                if (newerModel != null && newerModel.result != null) {
                    if (("200").equals(newerModel.status)) {
                        Intent intent = new Intent(context, BuyProductActivity.class);
                        intent.putExtra("InvesListModel", newerModel.result);
                        startActivity(intent);
                        //getActivity().overridePendingTransition(R.anim.activity_next_in_animation,R.anim.activity_next_out_animation);
                    }
                }

                StatService.onEvent(getActivity(), BaiDustatistic.bout_makemoney, "", 1);//事件统计
                break;
            case R.id.bouts_middle_image:// middle中间图片
                BannerModel bannerModel = new BannerModel();
                bannerModel.title = boutMiddleTitle;
                bannerModel.url = boutMiddlePicUrl;
                bannerModel.location = "";
                bannerModel.imgRootUrl = "";

                startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
//                StatService.onEvent(getActivity(), BaiDustatistic.bout_makemoney, "", 1);//事件统计
                break;
        }
    }


    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(BannerBaseModel.class)) {
            BannerBaseModel bannerBaseModel = (BannerBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(bannerBaseModel.status)) {
                initialize(bannerBaseModel);
            } else if (Constant.STATUS_FAILED.equals(bannerBaseModel.status)) {

            }
        } else if (model.getClass().equals(NewerBaseModel.class)) {
            newerModel = (NewerBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(newerModel.status)) {
                setData(newerModel);
            } else if (Constant.STATUS_FAILED.equals(newerModel.status)) {
                stopRefresh(bouts_swiperefresh);//无网络时，设置刷新图标为隐藏状态
            } else if (Constant.STATUS_ERROR.equals(newerModel.status)) {
                stopRefresh(bouts_swiperefresh);//出404异常时，设置刷新图标为隐藏状态
            } else {
                stopRefresh(bouts_swiperefresh);//失败时，设置刷新图标为隐藏状态
            }
        } else if (model.getClass().equals(CheckVersionBaseModel.class)) {
            CheckVersionBaseModel versionBaseModel = (CheckVersionBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(versionBaseModel.status)) {
                //（负值前字符串的值小于后字符串，正值前字符串大于后字符串）
                if (userModel.getAppVersionName().compareTo(versionBaseModel.result.lastversion) == 0) {//应用版本与线上版本相同

                } else if (userModel.getAppVersionName().compareTo(versionBaseModel.result.lastversion) < 0) {

//                    if ("1".equals(versionBaseModel.result.isUpdate)){
//                        UpdateHelper.getInstance().init(context, Color.parseColor("#0A93DB"));
//                        //UpdateHelper.getInstance().setDebugMode(true);
//                        long intervalMillis = 10 * 1000L;           //第一次调用startUpdateSilent出现弹窗后，如果10秒内进行第二次调用不会查询更新
//                        UpdateHelper.getInstance().autoUpdate(userModel.getAppPackageName(), true, intervalMillis);
//                    }else {
//                        UpdateHelper.getInstance().init(context, Color.parseColor("#0A93DB"));
//                        //UpdateHelper.getInstance().setDebugMode(true);
//                        long intervalMillis = 10* 1000L;           //第一次调用startUpdateSilent出现弹窗后，如果10秒内进行第二次调用不会查询更新
//                        UpdateHelper.getInstance().autoUpdate(userModel.getAppPackageName(), false, intervalMillis);
////                        Toast.makeText(context, "version"+versionBaseModel.result.isUpdate, Toast.LENGTH_SHORT).show();
//
//                    }

                    CheckVersionDialog dialog = new CheckVersionDialog(getActivity(), versionBaseModel.result.url, "新版本更新啦", versionBaseModel.result.description, versionBaseModel.result.lastversion, versionBaseModel.result.isUpdate);
                    dialog.show();

                } else if (userModel.getAppVersionName().compareTo(versionBaseModel.result.lastversion) > 0) {//应用版本比线上版本高

                }

            } else if (Constant.STATUS_FAILED.equals(versionBaseModel.status)) {

            }
        } else if (model.getClass().equals(AppBoutMiddleModel.class)) {//app首页中间推荐展现形式h5
            AppBoutMiddleModel boutMiddle = (AppBoutMiddleModel) model;
            if (Constant.STATUS_SUCCESS.equals(boutMiddle.status)) {
                AppBoutMiddleModel result = boutMiddle.result;
//                Constant.boutMiddlePic = result.pic;
                boutMiddlePicUrl = result.pic_url;
                boutMiddleTitle = result.title;

                Glide.with(this)
                        .load(result.pic)
//                        .placeholder(R.mipmap.ic_me_touzi)
//                        .error(R.mipmap.ic_me_touzi)
                        .into(bouts_middle_image);

            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        stopRefresh(bouts_swiperefresh);//无网络时，设置刷新图标为隐藏状态
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 填充数据
     */
    int progr = 0;
    int pg = 0;

    @SuppressLint("SetTextI18n")
    private void setData(NewerBaseModel model) {

        if (!("".equals(model.result.addRate))) {

            if (!("0".equals(model.result.addRate))) {
                String rate="";
                String addRate="";
                rate = model.result.rate;
                addRate = model.result.addRate;
                float parseFloatRate = Float.parseFloat(rate);
                float parseFloatAddRate = Float.parseFloat(addRate);
                float TotalNumber = parseFloatRate - parseFloatAddRate;

                rate = setString(String.valueOf(TotalNumber));
                addRate = setString(addRate);
                boutfra_tv_jiaxi.setTextSize(15);
                boutfra_tv_project_rate.setTextSize(40);
                boutfra_tv_jiaxi.setText("+" + addRate + "%");
                boutfra_tv_project_prec.setTextSize(30);
                boutfra_tv_project_rate.setText(rate);
                boutfra_tv_jiaxi.setVisibility(View.VISIBLE);
            } else {

                boutfra_tv_project_rate.setText(model.result.rate);

            }

        } else {
            boutfra_tv_project_rate.setText(model.result.rate);
        }


        String progress = model.result.progress;

        if (progress.contains(".")) {
            if (progress.startsWith("0") && Double.parseDouble(progress) != 0) {
                progress = "1";
            } else {
                int index = progress.indexOf(".");
                progress = progress.substring(0, index);
            }
        }
        if (!Q.isEmpty(progress)) {
            progr = Integer.parseInt(progress);
            pb.setProgress(progr);
        }


        boutfra_tv_min_inves.setText(String.valueOf(model.result.minInvestMoney) + "元起投");
        boutfra_tv_min_deadline.setText(String.valueOf(model.result.deadline));
        boutfra_tv_min_unit.setText(String.valueOf(model.result.unit));
        stopRefresh(bouts_swiperefresh);

//        start();
    }

    private String setString(String s) {


        if (s.contains(".")) {

            String[] array = s.split("\\.");

            String s1 = array[1];
            char FirstNumber = s1.charAt(0);
            String firstNumber = String.valueOf(FirstNumber);
            int parseInt = Integer.parseInt(firstNumber);


            if (s1.length() > 1) {
                char TwoNumber = s1.charAt(1);
                String twoNumber = String.valueOf(TwoNumber);
                int two = Integer.parseInt(twoNumber);

                if (parseInt > 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }

                if (parseInt < 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }
                if (parseInt > 0 && two < 0) {
                    return array[0] + "." + firstNumber;
                }


            } else {
                if (firstNumber.equals("0")) {
                    return array[0];
                }
            }

        }


        return s;
    }

    /**
     * 设置进度条
     */
    private void initProgressbar() {
        pb.setTextSize(20);// 设置字体大小
        pb.setTextColor(Color.RED);// 颜色
        pb.setMyPadding(10, 10, 10, 10);// 设置padding 调用setpadding会无效
        pb.setImagePadding(0, 1);// 可以不设置
        pb.setTextPadding(0, 0);// 可以不设置
        pb.setEnabled(false);
    }

    /**
     * 开始计时进度条
     */
    private void start() {
        TimerTask tt = new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 100, 200);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    if (pg <= progr) {
                        pg = pg + 1;
                        pb.setProgress(pg);
                    }

                    break;
            }
        }
    };

    @SuppressLint("NewApi")
    private void initialize(BannerBaseModel model) {
        /*for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }*/
        BannerModel = model.result;

        if (BannerModel.size() > 1) {/** Modify By Gele **/
            // 将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(getActivity(), BannerModel.get(BannerModel.size() - 1).imgRootUrl + BannerModel.get(BannerModel.size() - 1).location));

            for (int i = 0; i < BannerModel.size(); i++) {
                views.add(ViewFactory.getImageView(getActivity(), BannerModel.get(i).imgRootUrl + BannerModel.get(i).location));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(getActivity(), BannerModel.get(0).imgRootUrl + BannerModel.get(0).location));
            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(true);
        } else {

            for (int i = 0; i < BannerModel.size(); i++) {
                views.add(ViewFactory.getImageView(getActivity(), BannerModel.get(i).imgRootUrl + BannerModel.get(i).location));
            }

            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(false);
            Logger.i("views" + views.size() + "BannerModel" + BannerModel.size());
        }
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, BannerModel, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(3000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

        //cycleViewPager.disableParentViewPagerTouchEvent();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerModel model, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
//                Toast.makeText(getActivity(), "position-->" + model.url, Toast.LENGTH_SHORT).show();
                if (!Q.isEmpty(model.url)) {
                    BoutBannerFragment(model);
                }

            }
        }

    };

    /**
     * Banner
     */
    public void BoutBannerFragment(BannerModel model) {

        Intent intent = new Intent(getActivity(), BoutBannerActivity.class);
        intent.putExtra("BannerModel", model);
        startActivity(intent);

        StatService.onEvent(context, BaiDustatistic.bout_banner, model.title, 1);//事件统计
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "精选页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "精选页面");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
