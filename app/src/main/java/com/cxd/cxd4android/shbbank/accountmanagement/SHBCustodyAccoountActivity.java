package com.cxd.cxd4android.shbbank.accountmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.model.SHBBankPersonInfo;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SHBCustodyAccoountActivity extends BaseActivity implements LoadingView {


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
    @Bind(R.id.shb_activity_tv_name)
    TextView mShbActivityTvName;
    @Bind(R.id.shb_activity_identity_number)
    TextView mShbActivityIdentityNumber;
    @Bind(R.id.shb_activity_bank_number)
    TextView mShbActivityBankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shbcustody_accoount);
        ButterKnife.bind(this);
        mTvTitle.setText("存管账户");
        mBtnLeft.setVisibility(View.VISIBLE);
        SHBPresenter shbPresenter = new SHBPresenter(this);

        shbPresenter.SHBPersonINfo(userModel.getid());

    }

    @OnClick(R.id.Btn_left)
    public void onClick() {
        finish();
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(SHBBankPersonInfo.class)) {
            SHBBankPersonInfo shbBankPersonInfo = (SHBBankPersonInfo) model;
//            真实姓名
            mShbActivityTvName.setText(shbBankPersonInfo.getResult().getReal_name());
//            上海存管账户
            mShbActivityBankNumber.setText(shbBankPersonInfo.getResult().getBank_card_no());
//            身份证号码
            mShbActivityIdentityNumber.setText(shbBankPersonInfo.getResult().getId_card());

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
