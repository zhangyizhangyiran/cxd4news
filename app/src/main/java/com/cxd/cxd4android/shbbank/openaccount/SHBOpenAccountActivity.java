package com.cxd.cxd4android.shbbank.openaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.html.SHBHTML;
import com.cxd.cxd4android.shbbank.html.WebViewActivity;
import com.cxd.cxd4android.shbbank.model.SHBOPenccountAModel;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.micros.utils.Q;
import com.micros.utils.X;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SHBOpenAccountActivity extends BaseActivity implements LoadingView {

    @Bind(R.id.Btn_left)
    TextView BtnLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.activity_open_account_name)
    EditText activityOpenAccountName;
    @Bind(R.id.activity_open_account_idcard)
    EditText activityOpenAccountIdcard;
    @Bind(R.id.activity_open_account_bankcard)
    EditText activityOpenAccountBankcard;
    @Bind(R.id.activity_open_account_bankname)
    TextView activityOpenAccountBankname;
    @Bind(R.id.activity_ll_openaccount_bankname)
    LinearLayout activityLlOpenaccountBankname;
    @Bind(R.id.activity_open_account_phonenum)
    EditText activityOpenAccountPhonenum;
    @Bind(R.id.activity_open_account_submit)
    Button activityOpenAccountSubmit;

    SHBPresenter shbPresenter;
    String Name;
    String Idcard;
    String Bankcard;
    String Bankname;
    String Phonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shb_open_account);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        shbPresenter = new SHBPresenter(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("开户");
        BtnLeft.setVisibility(View.VISIBLE);
        activityOpenAccountPhonenum.setText(Q.hiddenMobile(userModel.getmobileNumber()));
        Phonenum = userModel.getmobileNumber();

    }

    @OnClick({R.id.Btn_left, R.id.activity_ll_openaccount_bankname, R.id.activity_open_account_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
                finish();
                break;
            case R.id.activity_ll_openaccount_bankname:
                Intent intent = new Intent(SHBOpenAccountActivity.this, SHBBankListActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_open_account_submit:

                Name = activityOpenAccountName.getText().toString().trim();

                Idcard = activityOpenAccountIdcard.getText().toString().trim();

                Bankcard = activityOpenAccountBankcard.getText().toString().trim();

                Bankname = activityOpenAccountBankname.getText().toString().trim();

                String inputPhonenum = activityOpenAccountPhonenum.getText().toString().trim();
                if (Q.isEmpty(Name)) {
                    T.D("请输入真实姓名");
                    return;
                }
                if (Q.isEmpty(Idcard)) {
                    T.D("请输入身份证号码");
                    return;
                }
                if (Q.isEmpty(Bankcard)) {
                    T.D("请输入银行卡号");
                    return;
                }
                if (Q.isEmpty(Bankname)) {
                    T.D("请选择开户行");
                    return;
                }
                if (Q.isEmpty(inputPhonenum)) {
                    T.D("请输入手机号");
                    return;
                }

                if (!Q.isAllChinese(Name)) {
                    T.D("请正确输入姓名");
                    return;
                }
                if (!X.isIDCardVerify(Idcard)) {
                    T.D("请正确输入身份证");
                    return;
                }
                if (!X.checkBankCard(Bankcard)) {
                    T.D("请正确输入银行卡号");
                    return;
                }


                if (!(Q.hiddenMobile((Phonenum))).equals(inputPhonenum)){
                    if (!X.isMobilePhoneVerify(inputPhonenum)) {
                        T.D("请正确输入手机号");
                        return;
                    }
                    Phonenum = inputPhonenum;

                }


                summit();

                break;
        }
    }

    // 接受选择银行结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SHBBankSelect shbBankSelect) {
        activityOpenAccountBankname.setText(shbBankSelect.getName());
    }

    //请求开户数据
    private void summit() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("realName", Name);
        map.put("idCardNo", Idcard);
        map.put("bankcardNo", Bankcard);
        map.put("mobile", Phonenum);

        shbPresenter.openAccount(map);
        P.show();

    }

    @Override
    public void getDataSuccess(Object model) {
        P.cancel();
        if (model.getClass().equals(SHBOPenccountAModel.class)) {
            SHBOPenccountAModel shbModel = (SHBOPenccountAModel) model;
            SHBOPenccountAModel.ResultBean result = shbModel.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());

            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("开户");
            webViewModel.setUrl(autoSubmitForm + "");


            startActivity(new Intent(SHBOpenAccountActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));

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
