package com.cxd.cxd4android.shbbank.accountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.SetGestureLockActivity;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.interfaces.IExitLoginCallBack;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.accountmanagement.shbrevise.SHBReviseBankActivity;
import com.cxd.cxd4android.shbbank.html.SHBHTML;
import com.cxd.cxd4android.shbbank.html.WebViewActivity;
import com.cxd.cxd4android.shbbank.model.SHBActivationAModel;
import com.cxd.cxd4android.shbbank.model.SHBAssessModel;
import com.cxd.cxd4android.shbbank.model.SHBBankPersonInfo;
import com.cxd.cxd4android.shbbank.model.SHBMobileChange;
import com.cxd.cxd4android.shbbank.model.SHBModel;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.shbbank.openaccount.SHBOpenAccountActivity;
import com.cxd.cxd4android.shbbank.password.SHBPassWordActivity;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountManagementActivity extends BaseActivity implements LoadingView {

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
    @Bind(R.id.shb_tv_manger)
    TextView mShbTvManger;
    @Bind(R.id.shb_layout_manger)
    LinearLayout mShbLayoutManger;
    @Bind(R.id.shb_layout_login)
    LinearLayout mShbLayoutLogin;
    @Bind(R.id.shb_layout_gensture)
    LinearLayout mShbLayoutGensture;
    @Bind(R.id.shb_tv_risk)
    TextView mShbTvRisk;
    @Bind(R.id.shb_layout_risk)
    LinearLayout mShbLayoutRisk;
    @Bind(R.id.shb_tv_bank)
    TextView mShbTvBank;
    @Bind(R.id.shb_layout_bank)
    LinearLayout mShbLayoutBank;
    @Bind(R.id.shb_tv_bank_number)
    TextView mShbTvBankNumber;
    @Bind(R.id.shb_layout_bank_number)
    LinearLayout mShbLayoutBankNumber;
    @Bind(R.id.shb_tv_account_phone_number)
    TextView mShbTvAccountPhoneNumber;
    @Bind(R.id.shb_layout_account_phone_number)
    LinearLayout mShbLayoutAccountPhoneNumber;
    @Bind(R.id.shb_layout_account_deal)
    LinearLayout mShbLayoutAccountDeal;

    SHBPresenter shbPresenter;
    String title = "";
    public SHBBankPersonInfo mShbBankPersonInfo;
    private SHBAssessModel mShbAssessModel;
    private String mApp_tip_riskassessment_url = "";
    private String mShRealNameAuth;
    private GhbGlobalData mGhbGlobalData;
    private GuideModel mGuideModel;
    private String mBindcard_status;
    public static AccountManagementActivity instance = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        ButterKnife.bind(this);
        instance = this;

        mGhbGlobalData = new GhbGlobalData(this);
        mTvTitle.setText("账户管理");
        mBtnLeft.setVisibility(View.VISIBLE);
        shbPresenter = new SHBPresenter(this);
        mShbTvManger.setText(userModel.getid());
        initBankStatus();
        initData();
        initSHBOPenAccount();
        initAssessResult();

    }

    private void initBankStatus() {
        shbPresenter.SHBPersonINfo(userModel.getid());
    }

    private void initAssessResult() {

        Map<String, String> map = new HashMap<>();
        map.put("userId", userModel.getid());
        shbPresenter.userAssessResult(map);

    }


    private void initSHBOPenAccount() {
        if (!Q.isEmpty(userModel.getid())) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            shbPresenter.loadUserInfo(strn);
        }

    }

    private void initData() {

        shbPresenter.SHBPersonINfo(userModel.getid());

        Gson gson = new Gson();
        String strn = gson.toJson("");
        shbPresenter.loadSHBGuidePage(strn);

    }


    @OnClick({R.id.Btn_left, R.id.shb_layout_manger, R.id.shb_layout_login, R.id.shb_layout_gensture, R.id.shb_layout_risk, R.id.shb_layout_bank, R.id.shb_layout_bank_number, R.id.shb_layout_account_phone_number, R.id.shb_layout_account_deal, R.id.shb_account_but_exit})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.shb_account_but_exit:
                userModel.clear();
                EventBus.getDefault().post(new IExitLoginCallBack());
                finish();

                break;
            case R.id.Btn_left:
                finish();
                break;
            case R.id.shb_layout_manger:

                break;
            case R.id.shb_layout_login:
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountManagementActivity.this, SHBPassWordActivity.class));

                break;
            case R.id.shb_layout_gensture:
                Toast.makeText(this, "手势", Toast.LENGTH_SHORT).show();
                //验证手势密码是否开启
                Intent intent2 = new Intent(this, SetGestureLockActivity.class);
                intent2.putExtra("KEY", "MeMyAccountFragmentSwitch");
                startActivity(intent2);
                userModel.setIsFirstSaveLock("first");

                break;
            case R.id.shb_layout_risk:
                if (!Q.isEmpty(mShbAssessModel)) {
                    if ("0".equals(mShbAssessModel.getResult().getAssessTimes())) {
                        Constant.SHB_PING_GUO = "2";
                        if (null != mGuideModel) {
                            if (mApp_tip_riskassessment_url != null) {
                                mGhbGlobalData.setMakeMoneystimate(mGuideModel);
                                this.finish();
                            }
                        }
                    } else {
                        Intent SHB = new Intent(this, SHBRiskAssessmentActivity.class);
                        SHB.putExtra("SHBRiskAssessmentActivity", (Serializable) mShbAssessModel);
                        startActivity(SHB);
                    }
                }
                break;
            case R.id.shb_layout_bank:
                if ("Y".equals(userModel.getActive())) {
                    if ("CHECKED".equals(mShRealNameAuth)) {
                        startActivity(new Intent(AccountManagementActivity.this, SHBCustodyAccoountActivity.class));
                        return;
                    }

                    if ("NOT".equals(mShRealNameAuth)) {
                        Toast.makeText(this, "请先开通上海银行存管账户", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AccountManagementActivity.this, SHBOpenAccountActivity.class));
                        return;
                    }

                } else if ("N".equals(userModel.getActive())) {
                    T.D("请先激活上海银行存管账户");
                    goActivation();
                }


                break;
            case R.id.shb_layout_bank_number:

                if ("Y".equals(userModel.getActive())) {
                    if ("NOT".equals(mShRealNameAuth)) {
                        Toast.makeText(this, "请先开通上海银行存管账户", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AccountManagementActivity.this, SHBOpenAccountActivity.class));
                        return;
                    }
                    if ("CHECKED".equals(mShRealNameAuth)) {


                        Intent intent = new Intent(this, SHBReviseBankActivity.class);
                        startActivity(intent);
                        return;
                    }


                }

                if ("N".equals(userModel.getActive())) {
                    T.D("请先激活上海银行存管账户");
                    goActivation();
                }


                break;
            case R.id.shb_layout_account_phone_number:
                if ("Y".equals(userModel.getActive())) {
                    if ("NOT".equals(mShRealNameAuth)) {
                        Toast.makeText(this, "请先开通上海银行存管账户", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AccountManagementActivity.this, SHBOpenAccountActivity.class));
                        return;
                    }
                    if ("CHECKED".equals(mShRealNameAuth)) {
                        title = "修改手机号";
                        resetMobile();
                        return;
                    }
                } else if ("N".equals(userModel.getActive())) {
                    T.D("请先激活上海银行存管账户");
                    goActivation();
                }


                break;

            case R.id.shb_layout_account_deal:
                if ("Y".equals(userModel.getActive())) {
                    if ("NOT".equals(mShRealNameAuth)) {
                        Toast.makeText(this, "请先开通上海银行存管账户", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AccountManagementActivity.this, SHBOpenAccountActivity.class));
                        return;
                    }
                    if ("CHECKED".equals(mShRealNameAuth)) {
                        title = "交易密码";
                        resetPassword();
                        return;
                    }

                } else if ("N".equals(userModel.getActive())) {
                    T.D("请先激活上海银行存管账户");
                    goActivation();
                }


                break;
        }
    }
//    去激活

    private void goActivation() {

        shbPresenter.activateStockedUser(userModel.getid());

    }

    //请求修改手机号数据
    private void resetMobile() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());

        shbPresenter.resetMobile(map);
        P.show();

    }

    //请求修改交易密码数据
    private void resetPassword() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());

        shbPresenter.resetPassword(map);
        P.show();

    }

    @Override
    public void getDataSuccess(Object model) {
        P.cancel();
        if (model.getClass().equals(SHBBankPersonInfo.class)) {
            SHBBankPersonInfo shbBankPersonInfo = (SHBBankPersonInfo) model;
            mBindcard_status = shbBankPersonInfo.getResult().getBindcard_status();
        }

//        修改交易密码
        if (model.getClass().equals(SHBModel.class)) {
            SHBModel shbActivateStockedUser = (SHBModel) model;
            SHBModel.ResultBean result = shbActivateStockedUser.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("修改交易密码");
            webViewModel.setUrl(autoSubmitForm + "");
            startActivity(new Intent(AccountManagementActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));


        }
        if (model.getClass().equals(SHBActivationAModel.class)) {
            SHBActivationAModel shbActivateStockedUser = (SHBActivationAModel) model;
            SHBActivationAModel.ResultBean result = shbActivateStockedUser.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("激活");
            webViewModel.setUrl(autoSubmitForm + "");
            startActivity(new Intent(AccountManagementActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));
        }
        if (model.getClass().equals(UserInfoModel.class)) {
            UserInfoModel userInfoModel = (UserInfoModel) model;
            mShRealNameAuth = userInfoModel.getResult().getSh_realname_auth();
            if ("Y".equals(userModel.getActive())) {
                if ("NOT".equals(mShRealNameAuth)) {
                    mShbTvBank.setText("未开通");
                }
                if ("CHECKED".equals(mShRealNameAuth)) {
                    mShbTvBank.setText("已开通");

                }
            } else if ("N".equals(userModel.getActive())) {
                mShbTvBank.setText("未激活");
            }


        }
        if (model.getClass().equals(GuideModel.class)) {
            mGuideModel = (GuideModel) model;
            mApp_tip_riskassessment_url = mGuideModel.getResult().getApp_tip_riskassessment_url();
        }
        if (model.getClass().equals(SHBMobileChange.class)) {
            SHBMobileChange shbModel = (SHBMobileChange) model;
            SHBMobileChange.ResultBean result = shbModel.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());

            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle(title);
            webViewModel.setUrl(autoSubmitForm);


            startActivity(new Intent(AccountManagementActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));
//            获取个人银行卡信息
        } else if (model.getClass().equals(SHBBankPersonInfo.class)) {
            mShbBankPersonInfo = (SHBBankPersonInfo) model;
            mShbTvBankNumber.setText(mShbBankPersonInfo.getResult().getBank_card_no());
            mShbTvManger.setText(userModel.getid());
            mShbTvAccountPhoneNumber.setText(mShbBankPersonInfo.getResult().getMobile());
            mShbTvBankNumber.setText(mShbBankPersonInfo.getResult().getBank_card_no());

        } else if (model.getClass().equals(SHBAssessModel.class)) {
            mShbAssessModel = (SHBAssessModel) model;
            SHBAssessModel.ResultBean resultBean = mShbAssessModel.getResult();
            if (!"0".equals(resultBean.getAssessTimes())) {
                mShbTvRisk.setText(resultBean.getAssessType());
            } else {
                mShbTvRisk.setText(resultBean.getAssessType());
            }


        }


    }

    @Override
    public void getDataFail(String msg) {
        P.cancel();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
