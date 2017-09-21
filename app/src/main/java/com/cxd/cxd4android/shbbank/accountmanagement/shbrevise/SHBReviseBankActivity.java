package com.cxd.cxd4android.shbbank.accountmanagement.shbrevise;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.interfaces.SHBChangeCardCallBack;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.model.SHBBankPersonInfo;
import com.cxd.cxd4android.shbbank.model.SHBBankResultInfo;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.cxd.cxd4android.widget.dialog.PromptDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cxd.cxd4android.activity.PdfShowActivity.openAppDetails;

public class SHBReviseBankActivity extends BaseActivity implements LoadingView {

    private static final int REQUEST_CODE_CHOOSE = 23;
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
    @Bind(R.id.title_bar)
    RelativeLayout mTitleBar;
    @Bind(R.id.fragment_revise_table)
    FrameLayout mFragmentReviseTable;
    private SHBReviseInfoFragment mShbReviseInfoFragment;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SHBPresenter mShbPresenter;
    private SHBReviseResultsFragment mShbReviseResultsFragment;
    private SHBBankResultInfo mShbBankPersonInfo;
    private SHBBankPersonInfo mShbBankInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shbrevise_bank);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();

        mShbReviseInfoFragment = new SHBReviseInfoFragment();
        mShbReviseResultsFragment = new SHBReviseResultsFragment();


        mTvTitle.setText("银行卡修改");
        mBtnLeft.setVisibility(View.VISIBLE);
        //新增sd卡权限申请
        //判断手机版本号是否需要申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //新增SD卡读写权限申请
            getPermission();
        }

    }

    private void initData() {
        mShbPresenter = new SHBPresenter(this);

        mShbPresenter.SHBPersonINfo(userModel.getid());
        mShbPresenter.loadBankInfo(userModel.getid());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mShbReviseInfoFragment.setPtotoPath(requestCode, resultCode, data);
        }

    }

    @OnClick(R.id.Btn_left)
    public void onClick() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void getPermission() {

        int i1 = ContextCompat.checkSelfPermission(this, permissions[0]);
        int i2 = ContextCompat.checkSelfPermission(this, permissions[1]);
        int i3 = ContextCompat.checkSelfPermission(this, permissions[2]);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (i1 != PackageManager.PERMISSION_GRANTED ||
                i2 != PackageManager.PERMISSION_GRANTED ||
                i3 != PackageManager.PERMISSION_GRANTED) {

            // 如果没有授予该权限，就去提示用户请求
            ActivityCompat.requestPermissions(this, permissions, 100);

        }


    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);

    }

    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == 100) {
            for (int i = 0; i < grantResults.length - 1; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    doShowPermission();
                }

            }
        }

    }

    //风险评估提示
    private void doShowPermission() {

        final PromptDialog makemoney = new PromptDialog(this, R.style.Make_Money);
        makemoney.setCanceledOnTouchOutside(false);
        makemoney.mDialog_view.setVisibility(View.VISIBLE);
        //标题 mResult
        makemoney.dialog_title.setTextSize(16);
        makemoney.title = "权限设置提示";
        makemoney.dialog_imageView.setVisibility(View.INVISIBLE);
        makemoney.dialog_confirm.setVisibility(View.VISIBLE);
        makemoney.dialog_confirm.setText("放弃设置");
        makemoney.dialog_confirm.setTextColor(getResources().getColor(R.color.gray));
        makemoney.dialog_confirm.setBackgroundResource(R.drawable.shape_layout_make_money_promptdialog);
        makemoney.dialog_sure.setVisibility(View.VISIBLE);
        makemoney.dialog_sure.setText("去设置");
        makemoney.dialog_sure.setBackgroundResource(R.drawable.shape_layout_make_money_right_promptdialog);
        makemoney.dialog_sure.setTextColor(getResources().getColor(R.color.btg_global_text_blue));
        makemoney.dialog_content.setVisibility(View.VISIBLE);
        makemoney.content = "尊敬的用户,为了您正常使用,请同意存储空间权限和拍照功能";

        makemoney.dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //放弃
                makemoney.dismiss();
                finish();
            }
        });

        makemoney.dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去设置
                openAppDetails(SHBReviseBankActivity.this, "com.cxd.cxd4android");

                makemoney.dismiss();


            }
        });
        makemoney.show();

    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(SHBBankPersonInfo.class)) {
            mShbBankInfo = (SHBBankPersonInfo) model;

        }
        if (model.getClass().equals(SHBBankResultInfo.class)) {
            mShbBankPersonInfo = (SHBBankResultInfo) model;

        }

        initStatus();
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

    private void initStatus() {
        String is_bind_card = mShbBankInfo.getResult().getBindcard_status();


        if ("GATEWAY".equals(mShbBankInfo.getResult().getBindcard_status())) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_revise_table, mShbReviseResultsFragment);
            fragmentTransaction.commit();


        } else if ("PASSED".equals(mShbBankInfo.getResult().getBindcard_status()) || "NONE".equals(mShbBankInfo.getResult().getBindcard_status()) || "APPLY".equals(mShbBankInfo.getResult().getBindcard_status())) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_revise_table, mShbReviseInfoFragment);
            fragmentTransaction.commit();

        } else if ("FAIL".equals(mShbBankInfo.getResult().getBindcard_status()) || "BACK".equals(mShbBankInfo.getResult().getBindcard_status())) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_revise_table, mShbReviseResultsFragment);
            fragmentTransaction.commit();
        }


    }


    void initViewStatus() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_revise_table, mShbReviseInfoFragment);
        fragmentTransaction.commit();

    }

    /**
     * 上海银行换卡回调
     */
    @Subscribe
    public void onEvent(SHBChangeCardCallBack event) {
        if ("1".equals(event.getS())) {
            finish();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
