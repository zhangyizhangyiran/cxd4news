package com.cxd.cxd4android.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.activity.subactivity.MyBsIntegralDetailsActivity;
import com.cxd.cxd4android.activity.subactivity.MyBsIntegralLuckDrawActivity;
import com.cxd.cxd4android.adapter.MeMyBonusAdapter;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IGiftExSucCallBack;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.AllPointsModel;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.GiftRecommendModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyBounsFragmentPresenter;
import com.cxd.cxd4android.widget.FullyLayoutManager.FullyGridLayoutManager;
import com.cxd.cxd4android.widget.view.MyOnScrollChanged;
import com.cxd.cxd4android.widget.view.MyScrollview;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ClassName:我-我的积分
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */
public class MeMyBounsFragment extends BaseFragment implements LoadingView {


    @Bind(R.id.scroll_view)
    MyScrollview scroll_view;
    /**
     * 我的投资正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 我的投资左上角返回箭头g
     **/
    @Bind(R.id.Btn_left)
    TextView tv_back;
    /**
     * Recyclerview 列表
     **/
    @Bind(R.id.profile_cotent_recyclerview)
    RecyclerView mRecyclerview;
    //头部
    /**
     * 帮助
     **/
    @Bind(R.id.me_ll_bouns_points_help)
    ImageView nounds_help;
    /**
     * 总积分
     **/
    @Bind(R.id.me_bouns_points_all)
    TextView me_bouns_points_all;
    /**
     * 积分明细
     **/
    @Bind(R.id.profile_head_bonus_detail)
    TextView bonus_detail;
    /**
     * 去赚积分
     **/
    @Bind(R.id.profile_head_go_bonus)
    TextView go_bonus;
    //下部
    @Bind(R.id.profile_content_bounts_lucky)
    ImageView bounts_lucky;
    /**
     * 下拉刷新swipe
     **/
    @Bind(R.id.bonus_swiperefresh)
    SwipeRefreshLayout bonus_swiperefresh;

    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 第几页
     **/
    private int currentPage = 1;
    /**
     * 数据类型
     **/
    private String DATATYPE = "REQUEST";
    //设置分页加载到底部没有更多数据
    private boolean isLoadMoreData = true;

    private List<GiftRecommendModel.ResultBean> list;
    /**
     * MeMyBonusAdapter 适配器
     **/
    private MeMyBonusAdapter mAdapter;
    MeMyBounsFragmentPresenter myBounsFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_bouns, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        userModel = new LocalUserModel();
        myBounsFragmentPresenter = new MeMyBounsFragmentPresenter(this);
        //初始化状态栏title
        initStatusBar();
        //开启刷新
        startRefresh(bonus_swiperefresh);
        //获取数据
        initData();
        //加载商品数据
        loadGoodsData();
        setListener();

    }

    private void initStatusBar() {

        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("我的积分");
    }

    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(IGiftExSucCallBack event) {
        if (("BounsConfirmFragment").equals(event.getType())) { //如果商品兑换成功，返回时重新加载下数据（礼品确认）
            currentPage = 1;
            DATATYPE = "CALLBACK";
            scroll_view.scrollTo(0, 0);
            initData();
            loadGoodsData();
        } else if (("BounsShipAddrFragment").equals(event.getType())) { //如果商品兑换成功，返回时重新加载下数据(地址确认)
            currentPage = 1;
            DATATYPE = "CALLBACK";
            scroll_view.scrollTo(0, 0);
            initData();
            loadGoodsData();
        } else if (("MyBsIntegralLuckDrawActivity").equals(event.getType())) {//如果商品兑换成功，返回时重新加载下数据（抽奖）
            currentPage = 1;
            DATATYPE = "CALLBACK";
            initData();
        }
    }

    /**
     * 请求数据 ,获取积分
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        myBounsFragmentPresenter.loadData(strn);
    }

    /**
     * 请求数据
     */
    private void loadGoodsData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageSize", "20");//每页条数
        map.put("currentPage", currentPage + "");//第几页

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        myBounsFragmentPresenter.loadGoodsData(strn);

    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(AllPointsModel.class)) {
            AllPointsModel allPointsModel = (AllPointsModel) model;
            String point = allPointsModel.getResult().getPoint();
            if (Constant.STATUS_SUCCESS.equals(allPointsModel.getStatus())) {
                me_bouns_points_all.setText(point);
            } else if (Constant.STATUS_ERROR.equals(allPointsModel.getStatus())) {
                Toast.makeText(getActivity(), "" + allPointsModel.getMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (model.getClass().equals(GiftRecommendModel.class)) {
            stopRefresh(bonus_swiperefresh);
            GiftRecommendModel giftRecommendModel = (GiftRecommendModel) model;
            if (Constant.STATUS_SUCCESS.equals(giftRecommendModel.getStatus())) {
                //EndlessRecyclerOnScrollListener.loading = false;

                list = giftRecommendModel.getResult();
                if (list.size() > 0) {
                    switch (DATATYPE) {
                        case "REFRESH": //下拉刷新
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            break;
                        case "REQUEST": //请求
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                            break;
                        case "LODE": //加载
                            if (list.size() > 0) {
                                mAdapter.appendToList(list);
                                mAdapter.notifyItemChanged(0);
                            } else {
                        /*mAdapter.setHasFooter(true);
                        mAdapter.setHasMoreData(false);*/
                                mAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "CALLBACK": //回调加载
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条

                            break;
                    }

                } else {
                    Toast.makeText(getActivity(), "全部礼品已展示！", Toast.LENGTH_SHORT).show();
                    isLoadMoreData = false;//设置为false，不再加载数据
                }
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

        stopRefresh(bonus_swiperefresh);
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    /**
     * 设置监听
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void setListener() {
        setAdapter();
        bonus_swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                //获取数据
                initData();
                //获取礼品列表
                loadGoodsData();
                isLoadMoreData = true;
            }
        });

        scroll_view.onS(new MyOnScrollChanged() {
            @Override
            public void down() {
                super.down();
                if (isLoadMoreData) {
                    bonus_swiperefresh.setRefreshing(true);
                    //延时操作，请求数据
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DATATYPE = "LODE";
                            currentPage++;
                            loadGoodsData();
                        }
                    }, 600);

                } else {
                    isLoadMoreData = false;//设置为false，不再加载数据
                    stopRefresh(bonus_swiperefresh);
                }
            }

            @Override
            public void up() {
                super.up();
                //Toast.makeText(getActivity(), "到达顶部了",
                //Toast.LENGTH_SHORT).show();
            }
        });

        scroll_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    /** 滑动到顶部和底部做处理 **/
                    scroll_view.istouch = true;
                }
                return false;
            }
        });
    }

    /**
     * 设置适配器
     */
    FullyGridLayoutManager gridLayoutManager;

    private void setAdapter() {
        mAdapter = new MeMyBonusAdapter(getActivity());
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(false);
        mAdapter.notifyDataSetChanged();
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();

        gridLayoutManager = new FullyGridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerview.setLayoutManager(gridLayoutManager);
    }
    /**
     * 回调接口
     **/

    @OnClick({R.id.Btn_left, R.id.me_ll_bouns_points_help, R.id.profile_head_bonus_detail, R.id.profile_head_go_bonus, R.id.profile_content_bounts_lucky})
    void Onclick(View v) {
        switch (v.getId()) {
            case R.id.Btn_left:
                getActivity().finish();
                break;
            case R.id.me_ll_bouns_points_help://积分规则
                IntegralRulesFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_integralrules, "", 1);//事件统计

                break;
            case R.id.profile_head_bonus_detail://积分明细
                Intent intent1 = new Intent(getActivity(), MyBsIntegralDetailsActivity.class);
                startActivity(intent1);
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_makeintegral, "", 1);//事件统计

                break;
            case R.id.profile_head_go_bonus://去赚积分
                EventBus.getDefault().post(new ILoginCallBack(1));//跳转投资
                getActivity().finish();
                StatService.onEvent(getContext(), BaiDustatistic.integdetail, "", 1);//事件统计

                break;
            case R.id.profile_content_bounts_lucky://积分抽奖

                SetIntegralLuckDraw();
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_integraldraw, "", 1);//事件统计

                break;
        }
    }
    public void IntegralRulesFragment(){

        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "积分规则";
        bannerModel.url =  Constant.integral_rules;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    /**
     * 跳转到积分抽奖页面
     *
     * @param
     * @param
     */
    private void SetIntegralLuckDraw() {
        Intent i = new Intent(getActivity(), MyBsIntegralLuckDrawActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "我的积分");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的积分");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!getActivity().isFinishing()) {
            remove("MeMyBounsFragment");
        }
    }

}
