package com.cxd.cxd4android.shbbank.openaccount;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.model.SHBQueryBankList;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SHBBankListActivity extends BaseActivity implements LoadingView {

    @Bind(R.id.Btn_left)
    TextView BtnLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.shb_activity_bank_list)
    RecyclerView mShbActivityBankList;
    private SHBSelectBankAdapter mShbSelectBankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shb_bank_list);
        ButterKnife.bind(this);

        initBankList();
        initData();

    }

    private void initBankList() {

//        获取银行列表
        SHBPresenter shbPresenter = new SHBPresenter(this);
        shbPresenter.bankList();

    }

    private void initData() {
        tvTitle.setText("选择银行");
        BtnLeft.setVisibility(View.VISIBLE);

        mShbSelectBankAdapter = new SHBSelectBankAdapter(this);

        mShbActivityBankList.setLayoutManager(new LinearLayoutManager(this));
        mShbActivityBankList.setAdapter(mShbSelectBankAdapter);

        mShbSelectBankAdapter.notifyDataSetChanged();

    }


    @OnClick({R.id.Btn_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
                finish();
                break;

        }
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(SHBQueryBankList.class)) {
            SHBQueryBankList shbQueryBankList = (SHBQueryBankList) model;
            List<SHBQueryBankList.SHBBankLIstModel> result = shbQueryBankList.getResult();
            mShbSelectBankAdapter.clear();
            mShbSelectBankAdapter.appendToList(result);
            mShbSelectBankAdapter.notifyDataSetChanged();


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
