package com.cxd.cxd4android.yeepay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.MeMyAccountActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyBillActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyInvesActivity;
import com.cxd.cxd4android.activity.subactivity.ReturnMoneyCalendarActivity;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.AccountBalanceModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMainFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YeePayAccountActivity extends BaseActivity implements LoadingView {

    @Bind(R.id.yeepay_tv_total_assets)
    TextView yeepayTvTotalAssets;
    @Bind(R.id.yeepay_tv_available_balance)
    TextView yeepayTvAvailableBalance;
    @Bind(R.id.yeepay_tv_frozen_funds)
    TextView yeepayTvFrozenFunds;
    @Bind(R.id.yeepay_tv_amount_received)
    TextView yeepayTvAmountReceived;
    @Bind(R.id.yeepay_ll_invest_info)
    LinearLayout yeepayLlInvestInfo;
    @Bind(R.id.yeepay_ll_bill_record)
    LinearLayout yeepayLlBillRecord;
    @Bind(R.id.yeepay_ll_account_info)
    LinearLayout yeepayLlAccountInfo;
    @Bind(R.id.yeepay_ll_money_calendar)
    LinearLayout yeepayLlMoneyCalendar;
    @Bind(R.id.Btn_left)
    TextView BtnLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.yeepay_tv_cumulative_gain)
    TextView yeepayTvCumulativeGain;

    MeMainFragmentPresenter meMainFragmentPresenter;

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yee_pay_account);
        ButterKnife.bind(this);

        meMainFragmentPresenter = new MeMainFragmentPresenter(this);

        initData();

    }

    /**
     * 请求参数
     */
    private void initData() {
        tvTitle.setText("易宝账户");
        BtnLeft.setVisibility(View.VISIBLE);

        type = userModel.getThirdPayType();
        userModel.setThirdPayType(Constant.ACCOUNT_YEEPAY);//保存状态类型为 易宝

        initAccountData();  //请求账户金额(账户信息)

    }

    /**
     * 设置参数
     */
    private void setData(AccountBalanceBaseModel model) {
        AccountBalanceModel model1 = model.result;
        yeepayTvCumulativeGain.setText(model1.collectProfit + "");//累计收益

        yeepayTvTotalAssets.setText(model1.balance + "");//资产总额
        yeepayTvAvailableBalance.setText(model1.balanceAvaliable + "");//可用余额
        yeepayTvFrozenFunds.setText(model1.freezeAmount + "");//冻结资金
        yeepayTvAmountReceived.setText(model1.collectCorpusAndInterest + "");//待收金额
    }


    /**
     * 请求账户金额数据(账户信息)
     */
    private void initAccountData() {
        if (!Q.isEmpty(userModel.getid())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            meMainFragmentPresenter.loadAccountData(strn);
        }

    }

    /**
     * 请求数据(是否实名认证)
     */
    private void initDataIsRealName() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadIsRealName(strn);
    }

    /**
     * 请求数据(银行卡状态)
     */
    private void initDataBankCard() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadBankCard(strn);
    }

    @OnClick({R.id.Btn_left, R.id.yeepay_ll_invest_info, R.id.yeepay_ll_bill_record, R.id.yeepay_ll_account_info, R.id.yeepay_ll_money_calendar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
                finish();
                break;
            case R.id.yeepay_ll_invest_info:
                startActivity(new Intent(YeePayAccountActivity.this, MeMyInvesActivity.class));
                break;
            case R.id.yeepay_ll_bill_record:

                startActivity(new Intent(YeePayAccountActivity.this, MeMyBillActivity.class));
                break;
            case R.id.yeepay_ll_account_info:

                startActivity(new Intent(YeePayAccountActivity.this, MeMyAccountActivity.class));
                break;
            case R.id.yeepay_ll_money_calendar:

                startActivity(new Intent(YeePayAccountActivity.this, ReturnMoneyCalendarActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        userModel.setThirdPayType(type);//保存状态类型为 之前类型
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(AccountBalanceBaseModel.class)) {

            AccountBalanceBaseModel balanceBaseModel = (AccountBalanceBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(balanceBaseModel.status)) {

                setData(balanceBaseModel);

            } else if (Constant.STATUS_FAILED.equals(balanceBaseModel.status)) {

            }
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
