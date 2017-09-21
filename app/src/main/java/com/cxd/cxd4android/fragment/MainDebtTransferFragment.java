//package com.cxd.cxd4android.fragment;
//
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.baidu.mobstat.StatService;
//import com.cxd.cxd4android.R;
//import com.cxd.cxd4android.adapter.MainDebtTransferAdapter;
//import com.cxd.cxd4android.global.BaseFragment;
//import com.cxd.cxd4android.global.Constant;
//import com.cxd.cxd4android.global.LocalUserModel;
//import com.cxd.cxd4android.interfaces.IPageSelectCallBack;
//import com.cxd.cxd4android.model.InvesListBaseModel;
//import com.cxd.cxd4android.model.InvesListModel;
//import com.cxd.cxd4android.model.ScreenBaseModel;
//import com.cxd.cxd4android.model.ScreenModel;
//import com.cxd.cxd4android.model.ScreensModel;
//import com.cxd.cxd4android.presenter.LoadingView;
//import com.cxd.cxd4android.presenter.MainInvestFragmentPresenter;
//import com.cxd.cxd4android.widget.picker.OptionPicker;
//import com.cxd.cxd4android.widget.picker.WheelPicker;
//import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
//import com.google.gson.Gson;
//import com.orhanobut.logger.Logger;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.Bind;
//
///**
// * @version V1.0
// * @ClassName:债权转让
// * @Description:投资-债权转让
// * @Author：xiaofa
// * @Date：2016/7/18 11:43
// */
//public class MainDebtTransferFragment extends BaseFragment implements LoadingView {
//
//    /**
//     * 投资正中间标题
//     **/
////    @Bind(R.id.tv_title)
////    TextView tv_title;
//    /**
//     * 投资右上角全部
//     **/
////    @Bind(R.id.Btn_right)
////    TextView Btn_right;
//    /**
//     * 投资列表刷新
//     **/
//    @Bind(R.id.invesfra_sr_swipe_refresh)
//    SwipeRefreshLayout invesfra_sr_swipe_refresh;
//    /**
//     * 投资列表控件
//     **/
//    @Bind(R.id.invesfra_rv_recycler_view)
//    RecyclerView invesfra_rv_recycler_view;
//    /**
//     * 用户信息
//     **/
//    LocalUserModel userModel;
//    /**
//     * 第几页
//     **/
//    private int currentPage = 1;
//    /**
//     * 投资列表数据
//     **/
//    List<InvesListModel> list = new ArrayList<InvesListModel>();
//    /**
//     * 投资列表适配器
//     **/
//    private MainDebtTransferAdapter mAdapter;
//    /**
//     * 数据类型
//     **/
//    private String DATATYPE = "REQUEST";
//
//    private String rateMax = "";//率最大值(小数如 0.15 = 15%)
//    private String rateMin = "";//利率最小值
//    private String dateMax = "";//时间最大值(单位月)
//    private String dateMin = "";//时间最小值(单位月)
//
//    private boolean DataScreen = false, isGetDataScreen = false;
//    MainInvestFragmentPresenter investFragmentPresenter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        if (contentView == null) {
//            contentView = inflater.inflate(R.layout.fragment_layout_main_debt, container, false);
//            return contentView;
//        }
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        EventBus.getDefault().register(this);
//        investFragmentPresenter = new MainInvestFragmentPresenter(this);
//        //用户信息
//        userModel = new LocalUserModel();
//        //请求筛选条件
//        initDataScreen();
//        //开启刷新
//        startRefresh(invesfra_sr_swipe_refresh);
//        //请求数据
//        initData();
//        //设置监听
//        setListener();
//
//    }
//
//
//    /**
//     * 设置适配器
//     */
//    private void setAdapter() {
//        mAdapter = new MainDebtTransferAdapter(getActivity());
//        mAdapter.setHasFooter(false);
//        mAdapter.setHasMoreData(true);
//        mAdapter.notifyDataSetChanged();
//        invesfra_rv_recycler_view.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        invesfra_rv_recycler_view.setLayoutManager(linearLayoutManager);
//    }
//
//    LinearLayoutManager linearLayoutManager;
//
//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        setAdapter();
//
//        //设置加载圈圈的颜色(设置四种颜色)
//        invesfra_sr_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                DATATYPE = "REFRESH";
//                currentPage = 1;
//                initData();
//                if (isGetDataScreen) {
//                    initDataScreen();//请求筛选数据
//                }
//            }
//        });
//        invesfra_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(final int current_page) {
//                DATATYPE = "LODE";
//                mAdapter.setHasFooter(true);
//                currentPage++;
//                initData();
//            }
//        });
//    }
//
//    @Subscribe
//    public void onEvent(IPageSelectCallBack event) {
//
//        if (1==event.getPage()) {
//            if (DataScreen) {
//                on2OptionPicker(invesfra_rv_recycler_view);
//            } else {
//                T.D("未获取筛选条件");
//            }
//        }
//    }
//    //点击
//   /* @OnClick(R.id.Btn_right)
//    void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.Btn_right://全部
//
//                if (DataScreen) {
//                    on2OptionPicker(invesfra_rv_recycler_view);
//                } else {
//                    T.D("未获取筛选条件");
//                }
//
//                break;
//        }
//    }*/
//
//    /**
//     * 请求数据
//     */
//    private void initData() {
////        P.OperationIng("正在加载");
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("pageSize", "10");//每页条数
//        map.put("currentPage", currentPage + "");//第几页
//        map.put("rateMax", rateMax);//利率最大值(小数如 0.15 = 15%)
//        map.put("rateMin", rateMin);//利率最小值
//        map.put("dateMax", dateMax);//时间最大值(单位月)
//        map.put("dateMin", dateMin);//时间最小值(单位月)
//
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//        investFragmentPresenter.loadData(strn);
//    }
//
//    @Override
//    public void getDataSuccess(Object model) {
//        if (model.getClass().equals(InvesListBaseModel.class)) {
//            //P.OperationSuccess("加载成功");
//            stopRefresh(invesfra_sr_swipe_refresh);
//            InvesListBaseModel investModel = (InvesListBaseModel) model;
//            if (Constant.STATUS_SUCCESS.equals(investModel.status)) {
//                list = investModel.result;
//                if (list.size() > 0) {
//
//                    EndlessRecyclerOnScrollListener.loading = false;
//                    switch (DATATYPE) {
//                        case "REFRESH": //下拉刷新
//                            mAdapter.clear();
//                            mAdapter.appendToList(list);
//                            mAdapter.notifyDataSetChanged();
//                            mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条
//
//                            break;
//                        case "REQUEST": //请求
//                            mAdapter.clear();
//                            mAdapter.appendToList(list);
//                            mAdapter.notifyDataSetChanged();
//                            break;
//                        case "LODE": //加载
//                            if (list.size() > 0) {
//                                mAdapter.appendToList(list);
//                                mAdapter.notifyDataSetChanged();
//
//                            } else {
//                                mAdapter.setHasFooter(true);
//                                mAdapter.setHasMoreData(false);
//                                mAdapter.notifyDataSetChanged();
//                            }
//                            break;
//                        case "SCREEN": //筛选
//                            if (list.size() > 0) {
//                                mAdapter.clear();
//                                mAdapter.appendToTopList(list);
//                                mAdapter.notifyDataSetChanged();
//
//                                mAdapter.setHasFooter(false);
//                                mAdapter.setHasMoreData(true);
//                                mAdapter.notifyDataSetChanged();
//
//                            } else {
//                                mAdapter.clear();
////                        T.D("筛选结果为空");
//                                mAdapter.setHasFooter(true);
//                                mAdapter.setHasMoreData(false);
//                                mAdapter.notifyDataSetChanged();
//                            }
//                            break;
//                    }
//                } else {
//                    mAdapter.setHasMoreDataAndFooter(false, true);
//                }
//            }
//        }else if (model.getClass().equals(ScreenBaseModel.class)){
//            ScreenBaseModel screenBaseModel = (ScreenBaseModel) model;
//            if (Constant.STATUS_SUCCESS.equals(screenBaseModel.status)) {
//                DataScreen = true;
//                //设置选择器
//                setPicker(screenBaseModel.result);
//                isGetDataScreen = false;//如果第一次加载成功 ，就将该值致为false
//            } else {
//                DataScreen = false;
//                isGetDataScreen = true;//if没有得到筛选数据，将该值致为true；
//            }
//
//        }
//    }
//
//    @Override
//    public void getDataFail(String msg) {
//        stopRefresh(invesfra_sr_swipe_refresh);
//        Logger.e(msg);
//    }
//
//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    /**
//     * 请求筛选条件
//     */
//    private void initDataScreen() {
//        Map<String, String> map = new HashMap<String, String>();
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//        investFragmentPresenter.loadScreenData(strn);
//    }
//
//    final ArrayList<String> option1 = new ArrayList<String>();
//    final ArrayList<ArrayList<String>> option2 = new ArrayList<ArrayList<String>>();
//    List<ScreensModel> loansRateQuery;
//    List<ScreensModel> loansDateQuery;
//
//    /**
//     * 设置 选择框
//     */
//    private void setPicker(ScreenModel model) {
//        loansRateQuery = model.loansRateQuery;
//        loansDateQuery = model.loansDateQuery;
//        for (int i = 0; i < loansRateQuery.size(); i++) {
//            option1.add(loansRateQuery.get(i).title);
//            ArrayList<String> option = new ArrayList<String>();
//            for (int x = 0; x < loansDateQuery.size(); x++) {
//                option.add(loansDateQuery.get(x).title);
//            }
//            option2.add(option);
//        }
//    }
//
//    /**
//     * 显示选择框
//     *
//     * @param view
//     */
//    public void on2OptionPicker(View view) {
//        /*final ArrayList<String> option1 = new ArrayList<String>();
//        option1.add("全部收益");
//        option1.add("10%以下");
//        option1.add("10%~15%");
//        option1.add("15%~20%");
//        option1.add("20%以上");
//        final ArrayList<ArrayList<String>> option2 = new ArrayList<ArrayList<String>>();
//        ArrayList<String> language = new ArrayList<String>();
//        language.add("全部期限");
//        language.add("3个月以上");
//        language.add("3~6个月");
//        language.add("6~12个月");
//        language.add("12个月以上");
//        option2.add(language);
//        ArrayList<String> tool = new ArrayList<String>();
//        tool.add("全部期限");
//        tool.add("3个月以上");
//        tool.add("3~6个月");
//        tool.add("6~12个月");
//        tool.add("12个月以上");
//        option2.add(tool);
//        ArrayList<String> environment = new ArrayList<String>();
//        environment.add("全部期限");
//        environment.add("3个月以上");
//        environment.add("3~6个月");
//        environment.add("6~12个月");
//        environment.add("12个月以上");
//        option2.add(environment);
//        ArrayList<String> length = new ArrayList<String>();
//        length.add("全部期限");
//        length.add("3个月以上");
//        length.add("3~6个月");
//        length.add("6~12个月");
//        length.add("12个月以上");
//        option2.add(length);
//        ArrayList<String> length2 = new ArrayList<String>();
//        length2.add("全部期限");
//        length2.add("3个月以上");
//        length2.add("3~6个月");
//        length2.add("6~12个月");
//        length2.add("12个月以上");
//        option2.add(length2);*/
//        OptionPicker picker = new OptionPicker(getActivity());
//        picker.setOptions(option1, option2);
//        picker.setSelectedOption(2, 2);//设置当前被选中
//        picker.setOnWheelListener(new WheelPicker.OnWheelListener<int[]>() {
//            @Override
//            public void onSubmit(int[] result) {
//                String type = option1.get(result[0]) + option2.get(result[0]).get(result[1]);
//                T.D(type);
//                currentPage = 1;//设置第一页
//                DATATYPE = "SCREEN";//设置类型
//                invesfra_sr_swipe_refresh.setRefreshing(true);
//
//                rateMin = loansRateQuery.get(result[0]).min;
//                rateMax = loansRateQuery.get(result[0]).max;
//
//                dateMin = loansDateQuery.get(result[1]).min;
//                dateMax = loansDateQuery.get(result[1]).max;
//
//                mAdapter.setHasFooter(false);
//                mAdapter.setHasMoreData(true);
//                mAdapter.notifyDataSetChanged();
//                invesfra_rv_recycler_view.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
//
//                initData();
//
//            }
//        });
//        picker.showAtBottom();
//    }
//
//    public void onResume() {
//        super.onResume();
//
//        /**
//         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
//         * 与StatService.onResume(this)类似;
//         */
//        StatService.onPageStart(getActivity(), "债权转让页面");//(this);
//    }
//
//    public void onPause() {
//        super.onPause();
//
//        /**
//         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
//         * 与StatService.onPause(this)类似;
//         */
//        StatService.onPageEnd(getActivity(), "债权转让页面");//(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//
//}
