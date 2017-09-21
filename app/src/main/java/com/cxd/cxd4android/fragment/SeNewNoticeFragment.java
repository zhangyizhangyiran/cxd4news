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
import com.cxd.cxd4android.adapter.SeNewNoticeSimpleAdapter;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.NewNoticeBaseModel;
import com.cxd.cxd4android.model.NewNoticeListModel;
import com.cxd.cxd4android.model.NoticeModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.SeNewNoticeFragmentPresenter;
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
 * ClassName:公告页面（最新，还款）
 * Description：
 * Author：XiaoFa
 * Date：2016/3/22 13:40
 * version：V1.0
 */
public class SeNewNoticeFragment extends BaseFragment implements LoadingView{


    /** 最新公告正中间标题 **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /** 最新公告左上角返回键 **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    
    /** 最新公告列表刷新 **/
    @Bind(R.id.newnoticefra_sr_swipe_refresh)
     SwipeRefreshLayout newnoticefra_sr_swipe_refresh;
    /** 最新公告列表控件 **/
    @Bind(R.id.newnoticefra_rv_recycler_view)
     RecyclerView newnoticefra_rv_recycler_view;
    
    /** 用户信息 **/
    LocalUserModel userModel;
    /** 第几页 **/
    private int currentPage = 1;
    
    /** 最新公告列表数据 **/
    List<NewNoticeListModel> list = new ArrayList<NewNoticeListModel>();
    /** 最新公告列表适配器 **/
    private SeNewNoticeSimpleAdapter mAdapter;
    /** 数据类型 **/
    private String DATATYPE = "REQUEST";
    /** t通知类型 最新&还款**/
    String notice;
    private boolean isNotice = false;

    NoticeModel noticeModel;
    SeNewNoticeFragmentPresenter noticeFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_se_newnotice, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noticeFragmentPresenter = new SeNewNoticeFragmentPresenter(this);
        noticeModel = (NoticeModel)getArguments().getSerializable("noticeModel");

        notice= noticeModel != null ? noticeModel.title : null;
        Logger.i("page===notice=="+notice);
        if ("newNotice".equals(notice)) {
            tv_title.setText("最新公告");
            isNotice = true;
        }else if ("paymentNotice".equals(notice)){
            tv_title.setText("还款通知");
            isNotice = false;
        }
        Btn_left.setVisibility(View.VISIBLE);
        //用户信息
        userModel = new LocalUserModel();
        //开启刷新
        startRefresh(newnoticefra_sr_swipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();
    }
    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new SeNewNoticeSimpleAdapter(getActivity(),getFragmentManager(),notice);
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        newnoticefra_rv_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newnoticefra_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        newnoticefra_sr_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });

        newnoticefra_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                currentPage++;
                initData();
            }
        });
    }

    //点击
    @OnClick(R.id.Btn_left)
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
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
        map.put("pageSize", "20");//每页条数
        map.put("currentPage", currentPage + "");//第几页
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        if (isNotice) {
            noticeFragmentPresenter.loadNoticeList(strn);
        }else {
        noticeFragmentPresenter.loadRepayList(strn);}
    }

    @Override
    public void getDataSuccess(Object model) {
        stopRefresh(newnoticefra_sr_swipe_refresh);
        NewNoticeBaseModel data = (NewNoticeBaseModel) model;

        if (Constant.STATUS_SUCCESS.equals(data.status)){
            list = data.result;
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
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        stopRefresh(newnoticefra_sr_swipe_refresh);
        Logger.e(msg);
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
        StatService.onPageStart(getActivity(), "最新公告");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "最新公告");//(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
}
