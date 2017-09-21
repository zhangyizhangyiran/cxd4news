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
import com.cxd.cxd4android.adapter.MeMyInvesSimpleAdapter;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.model.ScreenModel;
import com.cxd.cxd4android.model.ScreensModel;
import com.cxd.cxd4android.model.UserInvestsBaseModel;
import com.cxd.cxd4android.model.UserInvestsModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyInvestFragmentPresenter;
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
 * <p>
 * 我的投资
 */
public class MeMyInvesFragment extends BaseFragment implements LoadingView {

    /**
     * 我的投资正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 我的投资左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 我的投资右上角全部
     **/
    @Bind(R.id.Btn_right)
    TextView Btn_right;
    /**
     * 我的投资列表刷新
     **/
    @Bind(R.id.myinves_sr_fraswipe_refresh)
    SwipeRefreshLayout myinves_sr_fraswipe_refresh;
    /**
     * 我的投资列表控件
     **/
    @Bind(R.id.myinves_rv_recycler_view)
    RecyclerView myinves_rv_recycler_view;
    /**
     * 投资列表数据
     **/
    List<UserInvestsModel> list = new ArrayList<UserInvestsModel>();
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
    private String status = "";
    private MeMyInvesSimpleAdapter mAdapter;
    boolean DataScreen = false;
    MeMyInvestFragmentPresenter investFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myinves, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalUserModel localUserModel = new LocalUserModel();
        if (Constant.ACCOUNT_SHB.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的投资");
        } else if (Constant.ACCOUNT_YEEPAY.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的投资");
        } else if (Constant.ACCOUNT_GHB.equals(localUserModel.getThirdPayType())) {
            tv_title.setText("我的投资");
        } else {
            tv_title.setText("我的投资");
        }

        Btn_left.setVisibility(View.VISIBLE);
        Btn_right.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        investFragmentPresenter = new MeMyInvestFragmentPresenter(this);
        //请求筛选条件
        initDataScreen();
        //开启刷新
        startRefresh(myinves_sr_fraswipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();

    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MeMyInvesSimpleAdapter(getActivity(), getFragmentManager());
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        mAdapter.notifyDataSetChanged();
        myinves_rv_recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myinves_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        myinves_sr_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });


        myinves_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
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
//                remove("MeMyInvesFragment");
                getActivity().finish();
                break;
            case R.id.Btn_right://右边筛选按钮
                if (DataScreen) {
                    on2OptionPicker(myinves_rv_recycler_view);
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
        map.put("pageSize", "10");//每页条数
        map.put("currentPage", currentPage + "");//第几页
        map.put("status", status);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        investFragmentPresenter.loadData(strn);
        //Log.i("header-value->",strn);
    }

    /**
     * 请求筛选条件
     */
    private void initDataScreen() {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        investFragmentPresenter.loadScreenData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(UserInvestsBaseModel.class)) {
            UserInvestsBaseModel investsBaseModel = (UserInvestsBaseModel) model;
            stopRefresh(myinves_sr_fraswipe_refresh);

            if (Constant.STATUS_SUCCESS.equals(investsBaseModel.status)) {
                list = investsBaseModel.result;

                EndlessRecyclerOnScrollListener.loading = false;
                switch (DATATYPE) {
                    case "REFRESH": //下拉刷新
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条

                        break;
                    case "REQUEST": //请求
                        if (list.size() > 0) {
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.setHasFooter(true);
                            mAdapter.setHasMoreData(false);
                            mAdapter.notifyDataSetChanged();
                        }
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
                        if (list.size() > 0) {
                            mAdapter.clear();
                            mAdapter.appendToTopList(list);
                            mAdapter.notifyDataSetChanged();

                            mAdapter.setHasFooter(false);
                            mAdapter.setHasMoreData(true);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.clear();
                            mAdapter.setHasFooter(true);
                            mAdapter.setHasMoreData(false);
                            mAdapter.notifyDataSetChanged();
                        }
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
        Logger.e(msg);
        stopRefresh(myinves_sr_fraswipe_refresh);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    final ArrayList<String> option1 = new ArrayList<String>();
    List<ScreensModel> loanInvestStatus;

    /**
     * 设置选择框
     */
    private void setPicker(ScreenModel model) {
        loanInvestStatus = model.loanInvestStatus;
        for (int i = 0; i < loanInvestStatus.size(); i++) {
            option1.add(loanInvestStatus.get(i).title);
        }
    }

    /**
     * 显示选择框
     *
     * @param view
     */
    public void on2OptionPicker(View view) {

        OptionPicker picker = new OptionPicker(getActivity());
//        final String[] type = {"全部", "还款中", "已还款","已流标" };//"投资中","已终止", "已结束"
        picker.setOptions(option1);
        picker.setSelectedOption(2);
        picker.setOnWheelListener(new WheelPicker.OnWheelListener<int[]>() {
            @Override
            public void onSubmit(int[] result) {
                currentPage = 1;//设置第一页
                DATATYPE = "SCREEN";//设置类型
                myinves_sr_fraswipe_refresh.setRefreshing(true);

                status = loanInvestStatus.get(result[0]).status;

                mAdapter.setHasFooter(false);
                mAdapter.setHasMoreData(true);
                mAdapter.notifyDataSetChanged();
                myinves_rv_recycler_view.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                /**
                 * cancel			流标                       流标 (已终止,已结束)
                 * repaying 		还款中                     还款中 (还款中)
                 * complete		    完成                       已还款 (已还款)
                 * bid_success	    投标成功                    投资中 (投资中)
                 * wait_affirm      第三方资金托管确认中
                 *
                 */

                initData();

                String myinvest_screen = option1.get(result[0]);
                StatService.onEvent(getContext(), BaiDustatistic.myinvest_screen, myinvest_screen, 1);//事件统计

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
        StatService.onPageStart(getActivity(), "我的投资");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的投资");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
