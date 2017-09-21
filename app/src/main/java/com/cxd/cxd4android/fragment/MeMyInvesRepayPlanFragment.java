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
import com.cxd.cxd4android.adapter.MeMyInvesRepayPlanSimpleAdapter;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.RepayPlanBaseModel;
import com.cxd.cxd4android.model.RepayPlanModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyRepayPlanFragmentPresenter;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 还款计划
 * Created by moon.zhong on 2015/2/4.
 * UpData:16/7/12
 */
public class MeMyInvesRepayPlanFragment extends BaseFragment implements LoadingView  {


    /**
     * 还款计划正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 还款计划左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 还款计划列表刷新
     **/
    @Bind(R.id.repayplan_sr_fraswipe_refresh)
    SwipeRefreshLayout repayplan_sr_fraswipe_refresh;
    /**
     * 还款计划列表控件
     **/
    @Bind(R.id.repayplan_rv_recycler_view)
    RecyclerView repayplan_rv_recycler_view;
    /**
     * 投资列表数据
     **/
    List<RepayPlanModel> list = new ArrayList<RepayPlanModel>();
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
    /**bid_success	投标成功
     cancel			流标
     repaying 		还款中
     complete		完成
     */

    /**
     * 项目ID
     **/
    private String loanId = "";
    private MeMyInvesRepayPlanSimpleAdapter mAdapter;
    MeMyRepayPlanFragmentPresenter planFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myinves_repayplan, container, false);
            ButterKnife.bind(this,contentView);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("还款计划");
        Btn_left.setVisibility(View.VISIBLE);

        loanId = getArguments().getString("loanId");
        userModel = new LocalUserModel();
        planFragmentPresenter = new MeMyRepayPlanFragmentPresenter(this);
        //开启刷新
        startRefresh(repayplan_sr_fraswipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MeMyInvesRepayPlanSimpleAdapter(getActivity());
        mAdapter.setHasFooter(false);/** Modify By Gele **/
        mAdapter.setHasMoreData(false);/** Modify By Gele **/
        mAdapter.setHasMoreDataAndFooter(false, false);/** Modify By Gele **/
        repayplan_rv_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        repayplan_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        repayplan_sr_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });

        repayplan_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
//                DATATYPE = "LODE";
//                mAdapter.setHasFooter(true);
//                currentPage++;
//                initData();

                mAdapter.setHasFooter(false);/** Modify By Gele **/
            }
        });
    }

    @OnClick({R.id.Btn_left, R.id.tv_right})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
//                getFragmentManager().popBackStack();
                remove("MeMyInvesRepayPlanFragment");
                break;
            case R.id.tv_right://全部

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
        map.put("loanId", loanId);//每页条数

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        planFragmentPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        stopRefresh(repayplan_sr_fraswipe_refresh);
        RepayPlanBaseModel planBaseModel = (RepayPlanBaseModel) model;
        if (Constant.STATUS_SUCCESS.equals(planBaseModel.status)) {
            list = planBaseModel.result;
            if (list.size() > 0) {
                switch (DATATYPE) {
                    case "REFRESH": //下拉刷新
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "REQUEST": //请求
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "LODE": //加载
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            } else {
                mAdapter.setHasMoreDataAndFooter(false, true);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.i(msg);
        stopRefresh(repayplan_sr_fraswipe_refresh);
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
        StatService.onPageStart(getActivity(), "还款计划");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "还款计划");//(this);
    }


}
