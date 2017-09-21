package com.cxd.cxd4android.shbbank.accountmanagement.shbrevise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.model.SHBBankPersonInfo;
import com.cxd.cxd4android.shbbank.model.SHBBankResultInfo;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by administrator on 17/8/30.
 */

public class SHBReviseResultsFragment extends Fragment implements LoadingView {

    @Bind(R.id.shb_tv_revise_apply_yi)
    TextView mShbTvReviseApplyYi;
    @Bind(R.id.shb_tv_revise_apply_explain)
    TextView mShbTvReviseApplyExplain;
    @Bind(R.id.shb_but_revise_apply)
    Button mShbButReviseApply;
    private SHBPresenter mShbPresenter;
    private LocalUserModel mLocalUserModel;
    private SHBBankPersonInfo mShbBankPersonInfo;
    private String status;
    private SHBBankResultInfo.ResultBean mResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shb_frament_revise_apply, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLocalUserModel = new LocalUserModel();
        mShbPresenter = new SHBPresenter(this);
        initData();


    }

    private void initData() {
        mShbPresenter.SHBPersonINfo(mLocalUserModel.getid());


        mShbPresenter.loadBankInfo(mLocalUserModel.getid());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.shb_but_revise_apply)
    public void onClick() {
        if ("1".equals(status)) {
            SHBReviseBankActivity activity = (SHBReviseBankActivity) getActivity();
            activity.initViewStatus();
        }
        if ("2".equals(status)) {
            getActivity().finish();
        }

    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(SHBBankPersonInfo.class)) {
            mShbBankPersonInfo = (SHBBankPersonInfo) model;


        }
        if (model.getClass().equals(SHBBankResultInfo.class)) {
            SHBBankResultInfo shbBankResultInfo = (SHBBankResultInfo) model;
            mResult = shbBankResultInfo.getResult();
            mShbTvReviseApplyExplain.setText(mResult.getApply_aging_desc());
            initView();

        }

    }

    private void initView() {
        if ("FAIL".equals(mResult.getApply_status()) || "BACK".equals(mResult.getApply_status())) {
            status = "1";
            mShbTvReviseApplyYi.setText("审核失败");
            mShbButReviseApply.setText("重新绑定");
        } else if ("GATEWAY".equals(mResult.getApply_status())) {
            status = "2";
            mShbTvReviseApplyExplain.setText("我们的工作人员会在T+3个工作日内给您审核");
            mShbTvReviseApplyYi.setText("审核中");
            mShbButReviseApply.setText("确定");

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
