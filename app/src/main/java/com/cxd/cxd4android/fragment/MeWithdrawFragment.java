package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.YeePayActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataWithdrawCallBack;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.WithdrawBaseModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeWithdrawFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeWithdrawFragment extends BaseFragment implements LoadingView {

    /**
     * 提现正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 提现左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 提现银行图标
     **/
    @Bind(R.id.withdraw_iv_bank_type)
    ImageView withdraw_iv_bank_type;
    /**
     * 提现银行名称
     **/
    @Bind(R.id.withdraw_tv_bank_type)
    TextView withdraw_tv_bank_type;
    /**
     * 提现银行卡尾号
     **/
    @Bind(R.id.withdraw_tv_card_num)
    TextView withdraw_tv_card_num;
    /**
     * 提现可用余额
     **/
    @Bind(R.id.withdraw_tv_available_balance)
    TextView withdraw_tv_available_balance;
    /**
     * 提现提现金额
     **/
    @Bind(R.id.withdraw_tv_withdraw_money)
    EditText withdraw_tv_withdraw_money;
    /**
     * 提现余额不足
     **/
    @Bind(R.id.withdraw_tv_no_balance)
    TextView withdraw_tv_no_balance;

    /**
     * 提现余额不足
     **/
    @Bind(R.id.withdraw_rg_withdraw_money)
    RadioGroup withdraw_rg_withdraw_money;
    /**
     * 提现T+1提现
     **/
    @Bind(R.id.withdraw_rb_t_1)
    RadioButton withdraw_rb_t_1;
    /**
     * 提现实时提现
     **/
    @Bind(R.id.withdraw_rb_real_time)
    RadioButton withdraw_rb_real_time;

    /**
     * 提现提现费用
     **/
    @Bind(R.id.withdraw_tv_withdraw_cost)
    TextView withdraw_tv_withdraw_cost;
    /**
     * 提现实际提现
     **/
    @Bind(R.id.withdraw_tv_actual_withdraw)
    TextView withdraw_tv_actual_withdraw;

    /**
     * 用户信息
     */
    LocalUserModel userModel;
    /**
     * 可用余额
     */
    private double balance;
    /**
     * 充值钱数
     */
    private String money = "";
    /**
     * 易宝实名认证
     **/
    private String url = "";
    /**
     * 易宝是否回调
     **/
    private String isIntercept = "";
    /**
     * 易宝回调链接
     **/
    private String callbackUrl = "";
    /**
     * 跳转
     **/
    MeWithdrawWithdrawDetailsFragment MeWithdrawWithdrawDetailsFragment;
    public String toMoney = "";
    public String withdraw_money = "";

    /**
     * T+1 或者 实时提现
     **/
//    public String URGENT = "";
    /**
     * 手续费获取是否成功
     **/
    public boolean Fee = false;
    MeWithdrawFragmentPresenter withdrawFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_withdraw, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        tv_title.setText("提现");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        withdrawFragmentPresenter = new MeWithdrawFragmentPresenter(this);
        balance = Double.parseDouble(userModel.getbalanceAvaliable());

        //设置数据
        setData();
        //设置监听
        setListener();
    }

    /**
     * 事件响应方法,易宝提现返回刷新
     */
    @Subscribe
    public void onEvent(IUpdataWithdrawCallBack event) {
        Logger.i("MeWithdrawFragment==" + "" + "event.getUpdataInfo()==" + event.getUpdataWithdraw());
        initDataAccount();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new IUpdataWithdrawCallBack("MeWithdrawFragment"));
    }

    private Handler handler = new Handler();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
//            withdraw_money = withdraw_tv_withdraw_money.getText().toString().trim();
            if (!Q.isEmpty(withdraw_money) && !("0.00").equals(withdraw_money)) {
                String str = withdraw_money;

                if (str.length() == 1 && (".").equals(str)) {
                    return;
                }


                //大于余额
                if (Double.parseDouble(withdraw_money) > Double.parseDouble(userModel.getbalanceAvaliable())) {
                    withdraw_tv_no_balance.setVisibility(View.VISIBLE);
                    withdraw_tv_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_red);
                } else {
                    withdraw_tv_no_balance.setVisibility(View.INVISIBLE);
                    withdraw_tv_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_withe);

                    if (withdraw_rb_t_1.isChecked()) {
//                            withdraw_tv_withdraw_cost.setText("2.00");
//                            withdraw_tv_actual_withdraw.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - 2.00)));
                        initDataFee("0", withdraw_money);
                    }
                    if (withdraw_rb_real_time.isChecked()) {
//                            if (Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) > 10000) {//大于一万,万分之五
//                                withdraw_tv_withdraw_cost.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) * (0.0005))));
//                                withdraw_tv_actual_withdraw.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) * (0.0005))));
//                            } else {//小于一万,3块
//                                withdraw_tv_withdraw_cost.setText("3.00");
//                                withdraw_tv_actual_withdraw.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - 3.00)));
//                            }
                        initDataFee("1", withdraw_money);
                    }

                }
            } else {
                withdraw_tv_withdraw_cost.setText("0.00");
                withdraw_tv_actual_withdraw.setText("0.00");
                withdraw_tv_no_balance.setVisibility(View.INVISIBLE);
                withdraw_tv_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_withe);
            }
        }
    };

    /**
     * 设置监听
     */
    private void setListener() {
        withdraw_rg_withdraw_money.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.withdraw_rb_t_1://T+1提现
                       /* if (!Q.isEmpty(withdraw_tv_withdraw_money.getText().toString().trim()) && !("0.00").equals(withdraw_tv_withdraw_money.getText().toString().trim())) {
//                            withdraw_tv_withdraw_cost.setText("2.00");
//                            withdraw_tv_actual_withdraw.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - 2.00)));
                            initDataFee("0", withdraw_tv_withdraw_money.getText().toString().trim());
                            URGENT = "";
                        } else {
                            withdraw_tv_withdraw_cost.setText("0.00");
                            withdraw_tv_actual_withdraw.setText("0.00");
                        }*/

                        if (!Q.isEmpty(withdraw_tv_withdraw_money.getText().toString().trim())) {
//                            URGENT = "";
                            initDataFee("0", withdraw_tv_withdraw_money.getText().toString().trim());
                        }
                        StatService.onEvent(getContext(), BaiDustatistic.me_widthraw_t, "", 1);//事件统计
                        break;
                    case R.id.withdraw_rb_real_time://实时提现
                        /*if (!Q.isEmpty(withdraw_tv_withdraw_money.getText().toString().trim()) && !("0.00").equals(withdraw_tv_withdraw_money.getText().toString().trim())) {
//                            if (Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) > 10000){//大于一万,万分之五
//                                withdraw_tv_withdraw_cost.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim())*(0.0005))));
//                                withdraw_tv_actual_withdraw.setText(String.valueOf(df.format( Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim())*(0.0005))));
//                            }else {//小于一万,3块
//                                withdraw_tv_withdraw_cost.setText("3.00");
//                                withdraw_tv_actual_withdraw.setText(String.valueOf(df.format(Double.parseDouble(withdraw_tv_withdraw_money.getText().toString().trim()) - 3.00)));
//                            }
                            initDataFee("1", withdraw_tv_withdraw_money.getText().toString().trim());
                            URGENT = "URGENT";
                        } else {
                            withdraw_tv_withdraw_cost.setText("0.00");
                            withdraw_tv_actual_withdraw.setText("0.00");
                        }*/

                        if (!Q.isEmpty(withdraw_tv_withdraw_money.getText().toString().trim())) {
//                            URGENT = "URGENT";
                            initDataFee("1", withdraw_tv_withdraw_money.getText().toString().trim());
                        }
                        StatService.onEvent(getContext(), BaiDustatistic.me_widthraw_realtime, "", 1);//事件统计
                        break;
                }
            }
        });

        withdraw_tv_withdraw_money.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*if (hasFocus) {
                    if (Q.isEmpty(withdraw_tv_withdraw_money.getText().toString().trim())) {
                        withdraw_tv_withdraw_money.setText("0.00");
                    }
                }*/

            }
        });

        withdraw_tv_withdraw_money.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                withdraw_money = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 800);
            }
        });

    }

    /**
     * 设置数据
     */
    private void setData() {
        Glide.with(getActivity()).load(userModel.geticonUrl()).into(withdraw_iv_bank_type);
        withdraw_tv_bank_type.setText(userModel.getbankName());
        withdraw_tv_available_balance.setText(userModel.getbalanceAvaliable());
        if (!Q.isEmpty(userModel.getcardNo())) {
            withdraw_tv_card_num.setText("尾号" + userModel.getcardNo().substring(userModel.getcardNo().length() - 4, userModel.getcardNo().length()));
        }

    }

    /**
     * 请求数据(账户信息)
     */
    private void initDataAccount() {
        if (!Q.isEmpty(userModel.getid())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            withdrawFragmentPresenter.loadDataAccount(strn);
        }


    }

    /**
     * 请求提现费用
     */
    private void initDataFee(String type, final String money) {
//        P.OperationIng(Constant.LOADING);
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type);//0:T+1提现 1:实时提现
        map.put("money", money);

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        withdrawFragmentPresenter.loadWithdrawalFee(strn);
    }


    @OnClick({R.id.Btn_left, R.id.withdraw_bt_submit_next, R.id.withdraw_tv_withdraw_info})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
                break;
            case R.id.withdraw_bt_submit_next://下一步
                money = withdraw_tv_withdraw_money.getText().toString().trim();

                //不能为空
                if (Q.isEmpty(money) || ("0.00").equals(money)) {
                    T.D("请输入金额");
                    return;
                }

                //不能一个点
                if (money.length() == 1 && (".").equals(money)) {
                    T.D("请输入金额");
                    return;
                }

                //大于余额
                if (Double.parseDouble(money) > Double.parseDouble(userModel.getbalanceAvaliable())) {
                    withdraw_tv_no_balance.setVisibility(View.VISIBLE);
                    withdraw_tv_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    return;
                }
                //大于等于3元
                if (Double.parseDouble(money) < 3) {
                    T.D("提现金额必须大于等于3元");
                    return;
                }
                //手续费获取成功
                if (!Fee) {
                    return;
                }

                initData();//易宝提现

                StatService.onEvent(getContext(), BaiDustatistic.me_widthraw_next, "", 1);//事件统计
                break;
            case R.id.withdraw_tv_withdraw_info://提现说明
                MeWithdrawWithdrawDetailsFragment();
                StatService.onEvent(getContext(), BaiDustatistic.me_widthraw_explain, "", 1);//事件统计
                break;
            default:
                break;
        }
    }

    /**
     * 请求数据(提现)
     */
    private void initData() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("money", toMoney);
        map.put("withdraw_money", withdraw_money);
        int checkedRadioButtonId = withdraw_rg_withdraw_money.getCheckedRadioButtonId();
        if (checkedRadioButtonId == withdraw_rb_t_1.getId()) {
            map.put("withdrawType", "");
        } else if (checkedRadioButtonId == withdraw_rb_real_time.getId()) {
            map.put("withdrawType", "URGENT");
        }

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        withdrawFragmentPresenter.loadWithdraw(strn);

    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(AccountBalanceBaseModel.class)) {
            AccountBalanceBaseModel balanceBaseModel = (AccountBalanceBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(balanceBaseModel.status)) {

                userModel.setbalanceAvaliable(balanceBaseModel.result.balanceAvaliable);
                setData();
            } else if (Constant.STATUS_FAILED.equals(balanceBaseModel.status)) {

            }

        } else if (model.getClass().equals(WithdrawBaseModel.class)) {
            WithdrawBaseModel withdrawBaseModel = (WithdrawBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(withdrawBaseModel.status)) {
                Fee = true;
                toMoney = withdrawBaseModel.result.toMoney;
                withdraw_tv_withdraw_cost.setText(withdrawBaseModel.result.fee + "");
                withdraw_tv_actual_withdraw.setText(withdrawBaseModel.result.toShowMoney + "");
            } else if (Constant.STATUS_FAILED.equals(withdrawBaseModel.status)) {
                Fee = false;
            }
        } else if (model.getClass().equals(IdentityBaseModel.class)) {
            P.cancel();
            IdentityBaseModel identityBaseModel = (IdentityBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {
                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
                Constant.actionUrl = identityBaseModel.result.actionUrl;
                isIntercept = identityBaseModel.result.isIntercept;
                callbackUrl = identityBaseModel.result.callbackUrl;
                WebViewFragment();
            } else if (Constant.STATUS_FAILED.equals(identityBaseModel.status)) {
                T.D(identityBaseModel.msg + "");
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        P.cancel();
        Fee = false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 提现说明
     */
    public void MeWithdrawWithdrawDetailsFragment() {

        MeWithdrawWithdrawDetailsFragment = new MeWithdrawWithdrawDetailsFragment();

        add(R.id.fragment_mywithdraw, MeWithdrawWithdrawDetailsFragment, "MeWithdrawWithdrawDetailsFragment", null);

    }

    /**
     * 易宝提现跳转
     */
    public void WebViewFragment() {

        YeePayModel payModel = new YeePayModel();
        payModel.title = "提现";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));

    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "提现页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "提现页面");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
