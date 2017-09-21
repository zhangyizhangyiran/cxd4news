package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.YeePayActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.GhbJsInterface;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataRechargeCallBack;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbRechargeModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeRechargeFragmentPresenter;
import com.google.gson.Gson;
import com.cxd.cxd4android.widget.X5BaseWebview;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cxd.cxd4android.model.GhbGlobalDataModel.recharge_tip_realtime;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.recharge_tip_realtime_title;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.recharge_tip_t1;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.recharge_tip_t1_title;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeRechargeFragment extends BaseFragment implements LoadingView {


    /**
     * 充值正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 充值左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 充值银行图标
     **/
//    @Bind(R.id.recharge_iv_bank_type)
//    ImageView recharge_iv_bank_type;
    /**
     * 充值银行名称
     **/
//    @Bind(R.id.recharge_tv_bank_type)
//    TextView recharge_tv_bank_type;
    /**
     * 充值银行卡尾号
     **/
//    @Bind(R.id.recharge_tv_card_num)
//    TextView recharge_tv_card_num;
    /**
     * 充值银行卡限额
     **/
//    @Bind(R.id.recharge_tv_card_limit)
//    TextView recharge_tv_card_limit;

    /**
     * 充值可用余额
     **/
    @Bind(R.id.recharge_tv_available_balance)
    TextView recharge_tv_available_balance;
    /**
     * 充值充值金额
     **/
    @Bind(R.id.recharge_et_recharge_money)
    EditText recharge_et_recharge_money;

    /**
     * 充值下一步
     **/
    @Bind(R.id.recharge_bt_submit_next)
    Button recharge_bt_submit_next;

    /**
     * 充值提示信息
     **/
    @Bind(R.id.recharge_dg_warm_prompt)
    X5BaseWebview recharge_dg_warm_prompt;

    /**
     * 充值(易宝账户余额，华兴账户余额)
     **/
    @Bind(R.id.recharge_tv_available_balance_tv)
    TextView recharge_tv_available_balance_tv;

    /**
     * 用户信息
     */
    LocalUserModel userModel;
    /**
     * 充值钱数
     */
    private String money = "";
    /**
     * 跳转
     **/
    WebViewFragment WebViewFragment;
    /**
     * 易宝充值
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
    MeRechargeFragmentPresenter rechargeFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_recharge, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        tv_title.setText("充值");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        rechargeFragmentPresenter = new MeRechargeFragmentPresenter(this);

        //设置数据
        setData();
        //设置监听
        setListener();

        if (Constant.ACCOUNT_YEEPAY.equals(userModel.getThirdPayType())) {
            recharge_tv_available_balance_tv.setText("易宝可用余额");
        }else if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
            recharge_tv_available_balance_tv.setText("华兴可用余额");
            //设置提示信息
            initGuidePage();
        }

    }

    private void setListener() {
        recharge_et_recharge_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //限制两位
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                    return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    /**
     * 事件响应方法, 易宝提现返回刷新
     */
    @Subscribe
    public void onEvent(IUpdataRechargeCallBack event) {
        initDataAccount();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new IUpdataRechargeCallBack("recharge"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置数据
     */
    private void setData() {
//        if (!Q.isEmpty(userModel.geticonUrl())){
//            Glide.with(getActivity()).load(userModel.geticonUrl()).into(recharge_iv_bank_type);
//        }
//
//        recharge_tv_bank_type.setText(userModel.getbankName());
        recharge_tv_available_balance.setText(userModel.getbalanceAvaliable());
//        if (!Q.isEmpty(userModel.getcardNo())) {
//            recharge_tv_card_num.setText("尾号" + userModel.getcardNo().substring(userModel.getcardNo().length() - 4, userModel.getcardNo().length()));
//        }
//        recharge_tv_card_limit.setText(userModel.getbaofoo_limit_des());
    }

    @OnClick({R.id.Btn_left, R.id.recharge_bt_submit_next})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
                break;
            case R.id.recharge_bt_submit_next://下一步

                money = recharge_et_recharge_money.getText().toString().trim();

                if (Q.isEmpty(money)) {
                    T.D("金额为空");
                    return;
                }
                if (!(Double.parseDouble(money) > 0)) {
                    T.D("金额必须大于零");
                    return;
                }

                if (Constant.ACCOUNT_YEEPAY.equals(userModel.getThirdPayType())) {
                    if (!Q.isEmpty(userModel.getbaofoo_limit_money())) {
                        if (Double.parseDouble(money) > Double.parseDouble(userModel.getbaofoo_limit_money())) {
                            T.D("超过当前银行限额");
                            return;
                        }
                    }
                    //请求数据
                    initData();
                }else if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {

                    initGhbRecharge();//华兴充值
                    Constant.guideType = "recharge";//设置为充值标示,回调时区分类型
                }

                StatService.onEvent(getContext(), BaiDustatistic.me_rechange_next, "", 1);//事件统计
                break;
            default:
                break;
        }
    }

    /**
     * 请求数据-易宝充值
     */
    private void initData() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("money", money);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        rechargeFragmentPresenter.loadData(strn);
    }

    /**
     * 请求华兴充值数据
     */
    private void initGhbRecharge() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("money", money);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        rechargeFragmentPresenter.loadGhbRecharge(strn);
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

            rechargeFragmentPresenter.loadDataAccount(strn);
        }

    }
    /**
     * 请求数据(华兴提示信息)
     */
    private void initGuidePage() {
        Gson gson = new Gson();
        String strn = gson.toJson("");

        rechargeFragmentPresenter.loadGhbGuidePage(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(IdentityBaseModel.class)) {
            P.cancel();
            IdentityBaseModel identityBaseModel = (IdentityBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {
                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
                Constant.actionUrl = identityBaseModel.result.actionUrl;
                isIntercept = identityBaseModel.result.isIntercept;
                callbackUrl = identityBaseModel.result.callbackUrl;
                WebViewFragment();
            } else if (Constant.STATUS_FAILED.equals(identityBaseModel.status)) {
                AccountBalanceBaseModel balanceBaseModel = (AccountBalanceBaseModel) model;
                if (Constant.STATUS_SUCCESS.equals(balanceBaseModel.status)) {
                    userModel.setbalanceAvaliable(balanceBaseModel.result.balanceAvaliable);
                    setData();
                } else if (Constant.STATUS_FAILED.equals(balanceBaseModel.status)) {

                }
            }
        } else if (model.getClass().equals(AccountBalanceBaseModel.class)) {

        } else if (model.getClass().equals(GhbRechargeModel.class)) {
            P.cancel();
            GhbRechargeModel rechargeModel = (GhbRechargeModel) model;
            if (Constant.STATUS_SUCCESS.equals(rechargeModel.getStatus())) {
                // 完成调试, 华兴充值
                new GhbGlobalData(getActivity()).setGhbRechargeData(rechargeModel);
//                remove("MeRechargeFragment");
//                getActivity().finish();
            }
        } else if (model.getClass().equals(GuideModel.class)){

            GuideModel guideModel = (GuideModel) model;
            if (Constant.STATUS_SUCCESS.equals(guideModel.getStatus())){
                GuideModel.InResult result = guideModel.getResult();
                String tipRechargeUrl = result.getApp_tip_recharge_url();
                recharge_tip_realtime_title = result.getRecharge_tip_realtime_title();
                recharge_tip_realtime = result.getRecharge_tip_realtime();

                recharge_tip_t1_title = result.getRecharge_tip_t1_title();
                recharge_tip_t1 = result.getRecharge_tip_t1();

                recharge_dg_warm_prompt.loadUrl(tipRechargeUrl);
                recharge_dg_warm_prompt.addJavascriptInterface(new GhbJsInterface(getActivity()), "rechargeGuideRealtime");
                recharge_dg_warm_prompt.addJavascriptInterface(new GhbJsInterface(getActivity()), "rechargeGuideT");
//                recharge_dg_warm_prompt.loadUrl(" file:///android_asset/index2.html ");

            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        P.cancel();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 易宝充值跳转
     */
    public void WebViewFragment() {
        YeePayModel payModel = new YeePayModel();
        payModel.title = "充值";
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
        StatService.onPageStart(getActivity(), "充值页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "充值页面");//(this);
    }

}
