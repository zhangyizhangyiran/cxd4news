package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.MeMyBillSimpleAdapter;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.BillBaseModel;
import com.cxd.cxd4android.model.BillModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.model.ScreenModel;
import com.cxd.cxd4android.model.ScreensModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyBillFragmentPresenter;
import com.cxd.cxd4android.widget.picker.OptionPicker;
import com.cxd.cxd4android.widget.picker.WheelPicker;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 * 我的账单(华兴)
 */
public class MeMyGhbBillFragment extends BaseFragment implements LoadingView {

    /**
     * 我的账单正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 我的账单左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 我的账单右上角全部
     **/
    @Bind(R.id.Btn_right)
    TextView Btn_right;
    /**
     * 投资列表刷新
     **/
    @Bind(R.id.mybill_sr_fraswipe_refresh)
    SwipeRefreshLayout mybill_sr_fraswipe_refresh;
    /**
     * 投资列表控件
     **/
    @Bind(R.id.mybill_rv_recycler_view)
    RecyclerView mybill_rv_recycler_view;

    /**
     * 投资列表数据
     **/
    List<BillModel> list = new ArrayList<BillModel>();
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
    private String typeInfo = "";
    private String dataMax = "";
    private String dateMin = "";

    boolean DataScreen = false;
    private MeMyBillSimpleAdapter mAdapter;
    MeMyBillFragmentPresenter billFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_mybill, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalUserModel localUserModel = new LocalUserModel();
        if (Constant.ACCOUNT_SHB.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的账单");
        } else if (Constant.ACCOUNT_YEEPAY.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的账单");
        } else if (Constant.ACCOUNT_GHB.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的账单");
        } else {
            tv_title.setText("我的账单");
        }
        Btn_left.setVisibility(View.VISIBLE);
        Btn_right.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        billFragmentPresenter = new MeMyBillFragmentPresenter(this);
        //请求筛选条件
        initDataScreen();
        //开启刷新
        startRefresh(mybill_sr_fraswipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MeMyBillSimpleAdapter(getActivity());
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        mybill_rv_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mybill_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        mybill_sr_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });


        mybill_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                currentPage++;
                initData();
            }
        });
    }


    @OnClick({R.id.Btn_left, R.id.Btn_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
//                remove("MeMyBillFragment");
                getActivity().finish();
                break;
            case R.id.Btn_right://筛选
                if (DataScreen) {
                    on2OptionPicker(mybill_rv_recycler_view);
                } else {
                    T.D("未获取筛选条件");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("pageSize", "50");//每页条数
        map.put("currentPage", currentPage + "");//第几页
        map.put("dataMax", dataMax + "");
        map.put("dateMin", dateMin + "");
        map.put("typeInfo", typeInfo + "");
        /**
         * recharge_success   	充值成功
         * invest_success 		投资成功
         * withdraw_success 	体现成功
         * admin_operation 		管理员干预
         * repay_success		还款
         */
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        billFragmentPresenter.loadGhbUserBill(strn);
    }

    /**
     * 请求筛选条件
     */
    private void initDataScreen() {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        billFragmentPresenter.loadScreenData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(BillBaseModel.class)) {
            stopRefresh(mybill_sr_fraswipe_refresh);
            BillBaseModel billBaseModel = (BillBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(billBaseModel.status)) {
                list = billBaseModel.result;
//                    if (list.size() > 0){
                EndlessRecyclerOnScrollListener.loading = false;
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
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        break;
                }

            }
        } else if (model.getClass().equals(ScreenBaseModel.class)) {
            ScreenBaseModel screenBaseModel = (ScreenBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(screenBaseModel.status)) {
                DataScreen = true;
                //设置选择器
                setPicker(screenBaseModel.result);
            } else {
                DataScreen = false;
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        stopRefresh(mybill_sr_fraswipe_refresh);
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    final ArrayList<String> option1 = new ArrayList<String>();
    final ArrayList<ArrayList<String>> option2 = new ArrayList<ArrayList<String>>();
    List<ScreensModel> ghbBillStatusList;
    List<ScreensModel> billDateQuery;

    /**
     * 设置选择框
     */
    private void setPicker(ScreenModel model) {
        ghbBillStatusList = model.ghbBillStatusList;
        billDateQuery = model.billDateQuery;
        for (int i = 0; i < ghbBillStatusList.size(); i++) {
            option1.add(ghbBillStatusList.get(i).title);
            ArrayList<String> option = new ArrayList<String>();
            for (int x = 0; x < billDateQuery.size(); x++) {
                option.add(billDateQuery.get(x).title);
            }
            option2.add(option);
        }
    }

    /**
     * 显示选择框
     *
     * @param view
     */
    public void on2OptionPicker(View view) {

        OptionPicker picker = new OptionPicker(getActivity());
        picker.setOptions(option1, option2);
        picker.setSelectedOption(2, 2);//设置当前被选中
        picker.setOnWheelListener(new WheelPicker.OnWheelListener<int[]>() {
            @Override
            public void onSubmit(int[] result) {
                String type = option1.get(result[0]) + option2.get(result[0]).get(result[1]);
                T.D(type);
                currentPage = 1;//设置第一页
                DATATYPE = "SCREEN";//设置类型
                mybill_sr_fraswipe_refresh.setRefreshing(true);

                typeInfo = ghbBillStatusList.get(result[0]).status;
                dataMax = billDateQuery.get(result[1]).max;
                dateMin = billDateQuery.get(result[1]).min;

                initData();
                String mybill_screen = option1.get(result[0]) + "---" + option2.get(result[0]).get(result[1]);
                StatService.onEvent(getContext(), BaiDustatistic.mybill_screen, mybill_screen, 1);//事件统计

            }
        });
        picker.showAtBottom();
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "我的账单");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的账单");//(this);
    }

}
