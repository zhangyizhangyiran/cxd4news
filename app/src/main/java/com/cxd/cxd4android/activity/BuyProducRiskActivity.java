package com.cxd.cxd4android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.SeShowRolloutAnimViewAdapter;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.interfaces.IBuyProductCallBack;
import com.cxd.cxd4android.model.InvesListLoanPicUrlListModel;
import com.cxd.cxd4android.model.InvesListModel;
import com.cxd.cxd4android.widget.rollout.activity.RolloutBaseActivity;
import com.cxd.cxd4android.widget.rollout.model.RolloutInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 项目风控
 * Created by Administrator on 15-11-23.
 */
public class BuyProducRiskActivity extends BaseActivity {

    @Bind(R.id.se_show_name)
    TextView mSeShowName;
    @Bind(R.id.se_show_identification)
    TextView mSeShowIdentification;
    @Bind(R.id.se_show_number)
    TextView mSeShowNumber;
    @Bind(R.id.se_show_money)
    TextView mSeShowMoney;
    @Bind(R.id.se_show_info)
    TextView mSeShowInfo;

    @Bind(R.id.include_buy)
    LinearLayout minclude_buy;

    @Bind(R.id.include_xian)
    RelativeLayout include_xian;
    //    include_fenkong
    @Bind(R.id.include_fenkong)
    LinearLayout include_fenkong;


    private ArrayList<RolloutInfo> data;
    com.cxd.cxd4android.model.InvesListModel InvesListModel;
    /**
     * 项目风控正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 项目风控左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 列表控件 显示文字信息
     **/
    @Bind(R.id.prodetails_rv_recycler_viewinfos)
    RecyclerView prodetails_rv_recycler_viewinfo;
    private LinearLayoutManager mLayoutManager;
    private List<InvesListLoanPicUrlListModel> mLoanWindControlPicUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_buy_productrisk);
        ButterKnife.bind(this);

        tv_title.setText("项目风控");
        Btn_left.setVisibility(View.VISIBLE);

        InvesListModel = (InvesListModel) getIntent().getSerializableExtra("InvesListModel");


        //初始化数据
        setInfo(InvesListModel);
        //初始化图片URL
        handler();
        //设置适配器
        setAdapterInfo();


    }

    private void setInfo(InvesListModel invesListModel) {
        if (invesListModel != null) {
            mLoanWindControlPicUrls = invesListModel.loanWindControlPicUrls;
            if ("天".equals(invesListModel.unit) ) {
                minclude_buy.setVisibility(View.GONE);
                include_xian.setVisibility(View.GONE);
                include_fenkong.setVisibility(View.GONE);
            }
            if (invesListModel.loanWindControlPicUrls.size()==0) {
                minclude_buy.setVisibility(View.GONE);
                include_xian.setVisibility(View.GONE);
            }

        }


//        mSeShowName.setText(invesListModel.realName);
//        mSeShowIdentification.setText(invesListModel.idCard);
        mSeShowNumber.setText(invesListModel.overdue_times);
        mSeShowInfo.setText(invesListModel.repayGuarantee);
        mSeShowMoney.setText(invesListModel.overdue_money);

    }

    private void handler() {
        data = new ArrayList<RolloutInfo>();
        if (mLoanWindControlPicUrls.size() > 0) {

            for (int i = 0; i < mLoanWindControlPicUrls.size(); i++) {
                RolloutInfo model = new RolloutInfo();
                model.url = mLoanWindControlPicUrls.get(i).picUrl;
                model.width = 1280;
                model.height = 720;
                data.add(model);


            }

        }


    }

    private void setAdapterInfo() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        prodetails_rv_recycler_viewinfo.setLayoutManager(mLayoutManager);
        SeShowRolloutAnimViewAdapter seShowRolloutAnimViewAdapter = new SeShowRolloutAnimViewAdapter(this, prodetails_rv_recycler_viewinfo, data, mLayoutManager);
        prodetails_rv_recycler_viewinfo.setAdapter(seShowRolloutAnimViewAdapter);
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
