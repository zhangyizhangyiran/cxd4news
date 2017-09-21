package com.cxd.cxd4android.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.BuyProductRecodSimpleAdapter;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.interfaces.IBuyProductCallBack;
import com.cxd.cxd4android.model.ProductRecodBaseModel;
import com.cxd.cxd4android.model.ProductRecodModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.BuyProductRecodPresenter;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 投资记录页面
 * Created by Administrator on 15-11-23.
 */
public class BuyProducRecodActivity extends BaseActivity implements LoadingView {

    /**
     * 第几页
     **/
    private int currentPage = 1;
    /**
     * 数据类型
     **/
    private String DATATYPE = "REQUEST";

    private String queryDateType = "";

    private String loanId;
    /**
     * 投资列表正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 投资列表左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 投资列表刷新
     **/
    @Bind(R.id.productrecod_sr_fraswipe_refresh)
    SwipeRefreshLayout productrecod_sr_fraswipe_refresh;
    /**
     * 投资列表控件
     **/
    @Bind(R.id.productrecod_rv_recycler_view)
    RecyclerView productrecod_rv_recycler_view;

    @Bind(R.id.productrecod_tv_space)
    TextView productrecod_tv_space;


    private BuyProductRecodSimpleAdapter mAdapter;
    /**
     * 投资列表数据
     **/
    List<ProductRecodModel> list = new ArrayList<ProductRecodModel>();

    BuyProductRecodPresenter buyProductRecodPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_buy_productrecod);
        ButterKnife.bind(this);

        tv_title.setText("投资记录");
        Btn_left.setVisibility(View.VISIBLE);

        loanId = getIntent().getStringExtra("loanId");
        buyProductRecodPresenter = new BuyProductRecodPresenter(BuyProducRecodActivity.this);

        //开启刷新
        startRefresh();
        //请求数据
        initData();
        //设置监听
        setListener();

    }

    /**
     * 开启刷新
     */
    private void startRefresh() {
        productrecod_sr_fraswipe_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        productrecod_sr_fraswipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                productrecod_sr_fraswipe_refresh.setRefreshing(true);
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new BuyProductRecodSimpleAdapter(BuyProducRecodActivity.this);
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        productrecod_rv_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BuyProducRecodActivity.this);
        productrecod_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        productrecod_sr_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });

        productrecod_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                currentPage++;
                initData();

            }
        });
    }


    @OnClick(R.id.Btn_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                EventBus.getDefault().post(new IBuyProductCallBack());
                finish();
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
        map.put("loanId", loanId);
        map.put("pageSize", "50");//每页条数
        map.put("currentPage", currentPage + "");//第几页
        /**
         * recharge_success   	充值成功
         invest_success 		投资成功
         withdraw_success 	体现成功
         admin_operation 		管理员干预
         repay_success		还款
         */
        map.put("queryDateType", queryDateType + "");
        /**
         * 1个月以上		m_one_up
         3个月以上		m_three_up
         3-6个月			m_hree_six
         6-12个月		m_six_twelve
         12个月			m_twelve_up
         */
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        buyProductRecodPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(ProductRecodBaseModel.class)) {
            productrecod_sr_fraswipe_refresh.setRefreshing(false);
            ProductRecodBaseModel productRecodBaseModel = (ProductRecodBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(productRecodBaseModel.status)) {
                list = productRecodBaseModel.result;
                EndlessRecyclerOnScrollListener.loading = false;//
                switch (DATATYPE) {
                    case "REFRESH": //下拉刷新
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条
                        //空白页显示
                        setSpace(productRecodBaseModel);
                        break;
                    case "REQUEST": //请求
                        mAdapter.clear();
                        mAdapter.appendToList(list);
                        mAdapter.notifyDataSetChanged();
                        setSpace(productRecodBaseModel);
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
    }

    private void setSpace(ProductRecodBaseModel productRecodBaseModel) {
        if (productRecodBaseModel.result.size() == 0) {
            productrecod_rv_recycler_view.setVisibility(View.GONE);
            productrecod_tv_space.setVisibility(View.VISIBLE);


        } else {
            productrecod_rv_recycler_view.setVisibility(View.VISIBLE);
            productrecod_tv_space.setVisibility(View.GONE);
        }


    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        productrecod_sr_fraswipe_refresh.setRefreshing(false);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new IBuyProductCallBack());
            finish();
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(BuyProducRecodActivity.this, "投资记录");
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(BuyProducRecodActivity.this, "投资记录");
    }
}
