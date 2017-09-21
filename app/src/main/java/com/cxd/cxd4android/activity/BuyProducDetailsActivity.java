package com.cxd.cxd4android.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.BuyProductDetailsRiskInfoSimpleAdapter;
import com.cxd.cxd4android.adapter.SampleFooter;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.interfaces.IBuyProductCallBack;
import com.cxd.cxd4android.model.InvesListLoanInfoListModel;
import com.cxd.cxd4android.model.InvesListModel;
import com.github.captain_miao.recyclerviewutils.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.github.captain_miao.recyclerviewutils.recyclerview.RecyclerViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 项目详情
 * Created by Administrator on 15-11-23.hello
 */
public class BuyProducDetailsActivity extends BaseActivity {

    private BuyProductDetailsRiskInfoSimpleAdapter mAdapterInfo;

    com.cxd.cxd4android.model.InvesListModel InvesListModel;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    /**
     * 项目详情正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 项目详情左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 列表控件 显示文字信息
     **/
    @Bind(R.id.prodetails_rv_recycler_viewinfo)
    RecyclerView prodetails_rv_recycler_viewinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_buy_productdetails);
        ButterKnife.bind(this);

        tv_title.setText("项目详情");
        Btn_left.setVisibility(View.VISIBLE);

        InvesListModel = (InvesListModel) getIntent().getSerializableExtra("InvesListModel");

        //设置适配器
        setAdapterInfo();

    }

    /**
     * 设置适配器
     */
    @SuppressLint("NewApi")
    private void setAdapterInfo() {
//        CycleViewPager  cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragdsaf);
        mAdapterInfo = new BuyProductDetailsRiskInfoSimpleAdapter(this);
        mAdapterInfo.setHasFooter(false);
        mAdapterInfo.setHasMoreData(true);
        List<InvesListLoanInfoListModel> listLoanInfoMap = InvesListModel.listLoanInfoList;

        mAdapterInfo.setData(listLoanInfoMap, InvesListModel.loanWindControlPicUrls, getFragmentManager(),InvesListModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        prodetails_rv_recycler_viewinfo.setLayoutManager(linearLayoutManager);
        prodetails_rv_recycler_viewinfo.setAdapter(mAdapterInfo);


        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapterInfo);
        prodetails_rv_recycler_viewinfo.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        prodetails_rv_recycler_viewinfo.setLayoutManager(new LinearLayoutManager(this));

        if (InvesListModel.loanInfoPicUrls.size() > 0) {
            RecyclerViewUtils.setFooterView(prodetails_rv_recycler_viewinfo, new SampleFooter(this, null, InvesListModel.loanInfoPicUrls, getFragmentManager()));
        }

    }

    @OnClick(R.id.Btn_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                EventBus.getDefault().post(new IBuyProductCallBack());
                finish();
                break;
        }
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
        StatService.onPageStart(this, "项目风控");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(this, "项目风控");//(this);
    }
}
