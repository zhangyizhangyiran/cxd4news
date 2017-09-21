package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.MyBsIntegralDetailAdapter;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.PointHistoryModel;
import com.cxd.cxd4android.model.PointHistoryResultModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.model.ScreenModel;
import com.cxd.cxd4android.model.ScreensModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MyUserPointHistoryPresenter;
import com.cxd.cxd4android.widget.picker.OptionPicker;
import com.cxd.cxd4android.widget.picker.WheelPicker;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName:积分明细
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 13:40
 * version：V1.0
 */

public class MyBsIntegralDetailsActivity extends BaseActivity implements View.OnClickListener,LoadingView{

    private MyBsIntegralDetailAdapter mAdapter;

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.Btn_left)
    TextView tv_back;
    @Bind(R.id.Btn_right)
    TextView tv_right;
    /** 投资列表刷新 **/
    @Bind(R.id.integral_fraswipe_refresh)
    SwipeRefreshLayout integral_fraswipe_refresh;
    /** 投资列表控件 **/
    @Bind(R.id.integral_recycler_view)
    RecyclerView integral_recycler_view;

    /** 投资列表数据 **/
    List<PointHistoryResultModel> list = new ArrayList<PointHistoryResultModel>();
    /** 第几页 **/
    private int currentPage = 1;
    /** 数据类型 **/
    private String DATATYPE = "REQUEST";

    private String mode = "";//mode 类型
    private String dateMax = "";//时间最大值(单位月)
    private String dateMin = "";//时间最小值(单位月)
    private MyUserPointHistoryPresenter historyPresenter;
    boolean DataScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_details);
        ButterKnife.bind(this);

        historyPresenter = new MyUserPointHistoryPresenter(this);
        //初始化状态栏title
        initStatusBar();
        //请求筛选条件
        initDataScreen();
        //开启刷新
        startRefresh(integral_fraswipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();
    }

    private void initStatusBar() {

        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("积分明细");
        tv_right.setText("筛选");

    }
    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("pageSize", "50");//每页条数
        map.put("currentPage", currentPage + "");//第几页
        map.put("mode", mode); //传值为空时，查询所有数据
        map.put("dateMax", dateMax);//时间最大值(单位月)
        map.put("dateMin", dateMin);//时间最小值(单位月)
        /**mode
         invest	投资
         cost 	积分兑换
         prize 	积分抽奖
         **/
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        historyPresenter.loadData(strn);
    }

    /**
     * 请求筛选条件
     */
    private void initDataScreen() {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        historyPresenter.loadDataScreen(strn);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        integral_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        integral_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });

        integral_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                currentPage++;
                initData();
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MyBsIntegralDetailAdapter(this);
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        integral_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置选择框
     */
    final ArrayList<String> option1 = new ArrayList<String>();
    final ArrayList<ArrayList<String>> option2 = new ArrayList<ArrayList<String>>();
    List<ScreensModel> pointModeList;
    List<ScreensModel> prizeDateList;
    private void setPicker( ScreenModel model) {
        pointModeList = model.pointModeList;
        prizeDateList = model.prizeDateList;
        for (int i = 0;i < pointModeList.size();i++){
            option1.add(pointModeList.get(i).title);
            ArrayList<String> option = new ArrayList<String>();
            for (int x = 0;x < prizeDateList.size();x++){
                option.add(prizeDateList.get(x).title);
            }
            option2.add(option);
        }
    }
    /**
     * 显示选择框
     * @param view
     */
    public void on2OptionPicker(View view) {

        OptionPicker picker = new OptionPicker(this);
        picker.setOptions(option1, option2);
        picker.setSelectedOption(2, 2);//设置当前被选中
        picker.setOnWheelListener(new WheelPicker.OnWheelListener<int[]>() {
            @Override
            public void onSubmit(int[] result) {
                String type = option1.get(result[0]) + option2.get(result[0]).get(result[1]);
                T.D(type);
                currentPage = 1;//设置第一页
                DATATYPE = "SCREEN";//设置类型
                integral_fraswipe_refresh.setRefreshing(true);

                mode = pointModeList.get(result[0]).status;
                dateMin = prizeDateList.get(result[1]).min;
                dateMax = prizeDateList.get(result[1]).max;


                initData();
                String integdetail_screen = option1.get(result[0]) +"---"+ option2.get(result[0]).get(result[1]);
                StatService.onEvent(MyBsIntegralDetailsActivity.this, BaiDustatistic.integdetail_screen, integdetail_screen, 1);//事件统计
            }
        });
        picker.showAtBottom();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @OnClick({R.id.Btn_left,R.id.Btn_right})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_left:
                finish();
                break;
            case R.id.Btn_right:
                if (DataScreen){
                    on2OptionPicker(integral_recycler_view);
                }else {
                    T.D("未获取筛选条件");
                }

                break;
        }
    }
    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(ScreenBaseModel.class)) {
            ScreenBaseModel screenBaseModel = (ScreenBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(screenBaseModel.status)) {
                DataScreen = true;
                //设置选择器
                setPicker(screenBaseModel.result);
            } else {
                DataScreen = false;
            }

        } else if (model.getClass().equals(PointHistoryModel.class)) {
            stopRefresh(integral_fraswipe_refresh);

            PointHistoryModel historyModel = (PointHistoryModel) model;
            if (Constant.STATUS_SUCCESS.equals(historyModel.status)) {
                EndlessRecyclerOnScrollListener.loading = false;
                list = historyModel.result;

                switch (DATATYPE) {
                    case "REFRESH": //下拉刷新
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条
                        break;
                    case "REQUEST": //请求
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "LODE": //加载
                        if (list.size() > 0) {
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.setHasFooter(true);
                            mAdapter.setHasMoreData(false);
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    case "SCREEN": //筛选
//                        mAdapter.clear();
//                        mAdapter.appendToList(list);
//                        mAdapter.notifyDataSetChanged();

                        if (list.size() > 0) {
                            mAdapter.clear();
                            mAdapter.appendToTopList(list);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setHasFooter(false);
                            mAdapter.setHasMoreData(true);
                        } else {
                            mAdapter.clear();
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setHasFooter(true);
                            mAdapter.setHasMoreData(false);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        stopRefresh(integral_fraswipe_refresh);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(this, "积分明细");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(this, "积分明细");//(this);
    }

}
