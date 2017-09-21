package com.cxd.cxd4android.shbbank.accountmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.model.SHBAssessModel;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SHBRiskAssessmentActivity extends BaseActivity implements LoadingView {

    @Bind(R.id.viewtop_toplly)
    LinearLayout mViewtopToplly;
    @Bind(R.id.Btn_left)
    TextView mBtnLeft;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    @Bind(R.id.Btn_right)
    TextView mBtnRight;
    @Bind(R.id.shb_activity_tv_type)
    TextView mShbActivityTvType;
    @Bind(R.id.shb_activity_tv_explain)
    TextView mShbActivityTvExplain;
    @Bind(R.id.shb_activity_but_money)
    Button mShbActivityButMoney;
    @Bind(R.id.shb_activity_tv_remeasure)
    TextView mShbActivityTvRemeasure;
    private SHBAssessModel mShbAssessModel;
    private SHBPresenter mShbPresenter;
    private String mApp_tip_riskassessment_url;
    private GhbGlobalData ghbGlobalData;
    private GuideModel mGuideModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shbrisk_assessment);
        ButterKnife.bind(this);
        mTvTitle.setText("风险等级评估测试");
        mBtnLeft.setVisibility(View.VISIBLE);
        mShbAssessModel = (SHBAssessModel) getIntent().getSerializableExtra("SHBRiskAssessmentActivity");

        mShbPresenter = new SHBPresenter(this);
        ghbGlobalData = new GhbGlobalData(this);
        initView();
        initData();

    }

    private void initData() {
        Gson gson = new Gson();
        String strn = gson.toJson("");
        mShbPresenter.loadSHBGuidePage(strn);
    }

    private void initView() {
        mShbActivityTvType.setText(mShbAssessModel.getResult().getAssessType());
        mShbActivityTvExplain.setText(mShbAssessModel.getResult().getAssessTypeDesc());

    }

    @OnClick({R.id.shb_activity_but_money, R.id.shb_activity_tv_remeasure, R.id.Btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
                finish();
                break;
            case R.id.shb_activity_but_money:
                EventBus.getDefault().post(new ILoginCallBack(1));//跳转投资
                AccountManagementActivity.instance.finish();
                this.finish();


                break;
            case R.id.shb_activity_tv_remeasure:
                Constant.SHB_PING_GUO = "2";
                if (mApp_tip_riskassessment_url != "") {

                    ghbGlobalData.setMakeMoneystimate(mGuideModel);
                    AccountManagementActivity.instance.finish();
                    this.finish();

                }


                break;
        }
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(GuideModel.class)) {
            mGuideModel = (GuideModel) model;
            mApp_tip_riskassessment_url = mGuideModel.getResult().getApp_tip_riskassessment_url();
        }

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
