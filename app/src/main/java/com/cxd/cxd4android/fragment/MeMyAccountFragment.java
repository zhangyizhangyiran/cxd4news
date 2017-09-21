package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.CheckoutGestureLockActivity;
import com.cxd.cxd4android.activity.SetGestureLockActivity;
import com.cxd.cxd4android.activity.YeePayActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IBuyProductCheckCallBack;
import com.cxd.cxd4android.interfaces.IExitLoginCallBack;
import com.cxd.cxd4android.interfaces.IGestrueSetCallBack;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.model.BindBankCardBaseModel;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.BindPhoneBaseModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.PwsTransBaseModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMainFragmentPresenter;
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
public class MeMyAccountFragment extends BaseFragment implements LoadingView {

    /**
     * 我的账户正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 我的账户左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 我的账户手机绑定
     **/
    @Bind(R.id.myaccount_tv_bind_phone)
     TextView myaccount_tv_bind_phone;
    /**
     * 我的账户实名认证
     **/
    @Bind(R.id.myaccount_tv_real_name)
     TextView myaccount_tv_real_name;
    /**
     * 我的账户银行卡
     **/
    @Bind(R.id.myaccount_tv_bank_card)
     TextView myaccount_tv_bank_card;
    /**
     * 我的账户交易密码
     **/
    @Bind(R.id.myaccount_tv_pws_trans)
     TextView myaccount_tv_pws_trans;
    /**
     * 我的账户手势密码
     **/
    @Bind(R.id.myaccount_tv_pws_modify)
     TextView myaccount_tv_pws_modify;
    /**
     * 我的账户手势密码开关
     **/
    @Bind(R.id.myaccount_iv_pws_switch)
     ImageView myaccount_iv_pws_switch;

    /**
     * 我的账户手势密码
     **/
    @Bind(R.id.myaccount_ll_pws_modify)
     LinearLayout myaccount_ll_pws_modify;


    /**
     * 我的账户退出登录
     **/
    @Bind(R.id.myaccount_bt_exit_login)
     Button myaccount_bt_exit_login;

    /**
     * 易宝绑定银行卡
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

    //    private MeMyAccountBindPhoneFragment MeMyAccountBindPhoneFragment;
    private MeMyAccountIdentityInfoFragment MeMyAccountIdentityInfoFragment;
    private MeMyAccountBankCardFragment MeMyAccountBankCardFragment;
    private MeMyAccountPwsLoginModifyFragment MeMyAccountPwsLoginModifyFragment;
    //    private MeMyAccountPwsTransModifyFragment MeMyAccountPwsTransModifyFragment;
    private MeLoginFragment MeLoginFragment;
    MeMainFragmentPresenter meMainFragmentPresenter;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myaccount, container, false);
            return contentView;
        }
        return contentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("我的账户");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        meMainFragmentPresenter = new MeMainFragmentPresenter(this);
        EventBus.getDefault().register(this);


        //对话框点击跳转进来的
        if (Constant.DIALOG_JUMP.equals("MeMyAccountIdentityInfoFragment")) {//实名认证

            MeMyAccountIdentityInfoFragment();
        } else if (Constant.DIALOG_JUMP.equals("MeMyAccountBankCardFragment")) {//绑卡
            initDataBindBankCard();
//            MeMyAccountBankCardFragment();
        } else {

            initDataBankCard();
            initDataIsRealName();
        }
        Constant.DIALOG_JUMP = "";


        //设置数据
        setData();
    }

    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(IGestrueSetCallBack event) {
        if (("MeMyAccountFragmentModify").equals(event.getGestrue())) {//去修改,成功
            T.D("修改成功");
            myaccount_tv_pws_modify.setText("去修改");
        } else if (("MeMyAccountFragmentSwitch").equals(event.getGestrue())) {//点击开关,并设置成功
            T.D("设置成功");
            myaccount_tv_pws_modify.setText("去修改");
            userModel.setGestrueSwitch("ON");
            myaccount_iv_pws_switch.setImageResource(R.mipmap.toggle_btn_checked);
            //显示
            myaccount_ll_pws_modify.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(IBuyProductCheckCallBack event) {
        if (("BuyProductActivityAccount").equals(event.getBuyProductCheck())) {
            //清除数据
            userModel.clear();
            EventBus.getDefault().post(new IExitLoginCallBack());
            getActivity().finish();
        }
    }
    /**
     * 事件响应方法,更新个人信息回调,返回更新银行卡状态,返回更新实名认证状态
     */
    @Subscribe
    public void onEvent(IUpdataInfoCallBack event) {
        Logger.i("MeMyAccountFragment==" + "" + "event.getUpdataInfo()==" + event.getUpdataInfo());
        initDataBankCard();
        initDataIsRealName();
    }
    /**
     * 设置数据
     */
    private void setData() {
        //验证手机是否绑定
        if (!Q.isEmpty(userModel.getBindPhone())) {//!Q.isEmpty(userModel.getmobileNumber())
            if (userModel.getmobileNumber().length() == 11) {
                myaccount_tv_bind_phone.setText(userModel.getmobileNumber().substring(0, 2) + "****" + userModel.getmobileNumber().substring(8, 11));
            }
    }

        //验证是否实名
        if (("1").equals(userModel.getauth())) {
            if (!Q.isEmpty(userModel.getrealname())) {
                myaccount_tv_real_name.setText(userModel.getrealname().substring(0, 1) + "**");

            } else {
                myaccount_tv_real_name.setText("已认证");
            }
            myaccount_tv_pws_trans.setText("去修改");
        }

        //验证是否绑卡
        if (("绑卡成功").equals(userModel.getBankCardStatus())) {

            if (!Q.isEmpty(userModel.getcardNo())) {
                myaccount_tv_bank_card.setText("******" + userModel.getcardNo().substring(userModel.getcardNo().length() - 4, userModel.getcardNo().length()));
            } else {
                myaccount_tv_bank_card.setText("绑卡成功");
            }
        } else if (("审核中").equals(userModel.getBankCardStatus())) {
            myaccount_tv_bank_card.setText("审核中");
        }

        //验证手势密码是否开启
        if (("ON").equals(userModel.getGestrueSwitch())) {
            myaccount_iv_pws_switch.setImageResource(R.mipmap.toggle_btn_checked);
            //显示
            myaccount_ll_pws_modify.setVisibility(View.VISIBLE);
        } else {
            myaccount_iv_pws_switch.setImageResource(R.mipmap.toggle_btn_unchecked);
            //隐藏
            myaccount_ll_pws_modify.setVisibility(View.GONE);
        }

        //验证是否设置手势密码
        if (!Q.isEmpty(userModel.getLockPattern())) {//LockPatternUtils.getLockPattern(context, Constant.GESTURE_KEY)
            myaccount_tv_pws_modify.setText("去修改");
        } else {
            myaccount_tv_pws_modify.setText("去设置");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!Q.isEmpty(userModel.getLockPattern())) {//LockPatternUtils.getLockPattern(context, Constant.GESTURE_KEY)
//            myaccount_tv_pws_modify.setText("去修改");
//        }
    }


    @OnClick( {R.id.Btn_left, R.id.myaccount_ll_bind_phone, R.id.myaccount_ll_real_name, R.id.myaccount_ll_bank_card, R.id.myaccount_ll_pws_login, R.id.myaccount_ll_pws_trans, R.id.myaccount_ll_pws_switch, R.id.myaccount_ll_pws_modify, R.id.myaccount_bt_exit_login, R.id.myaccount_iv_pws_switch})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
                break;

            case R.id.myaccount_ll_bind_phone://手机绑定
                //验证是否实名
                if (("1").equals(userModel.getauth())) {//!Q.isEmpty(userModel.getidCard()) && !Q.isEmpty(userModel.getrealname())!Q.isEmpty(userModel.getBindName())
                    initBindPhone();
                } else {
                    T.D("请先实名认证");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_bindphone, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_real_name://实名认证201608060112
                MeMyAccountIdentityInfoFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_auth, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_bank_card://银行卡
                //验证是否实名
                if (("1").equals(userModel.getauth())) {//!Q.isEmpty(userModel.getidCard()) && !Q.isEmpty(userModel.getrealname())
                    if (("绑卡成功").equals(userModel.getBankCardStatus()) || ("审核中").equals(userModel.getBankCardStatus())) {//!Q.isEmpty(userModel.getcardNo())
                        MeMyAccountBankCardFragment();
                    }else {
                        initDataBindBankCard();
                    }

                } else {
                    T.D("请先实名认证");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_bindcard, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_pws_login://登录密码
                MeMyAccountPwsLoginModifyFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_modifypwd, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_pws_trans://交易密码
                //验证是否实名
                if (("1").equals(userModel.getauth())) {//!Q.isEmpty(userModel.getidCard()) && !Q.isEmpty(userModel.getrealname())
                    initDataPwsTrans();
                } else {
                    T.D("请先实名认证");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_transactionpwd, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_pws_switch://手势密码(开关)

                break;
            case R.id.myaccount_iv_pws_switch://手势密码(开关)
                if (!Q.isEmpty(userModel.getLockPattern())) {//已设置//LockPatternUtils.getLockPattern(context, Constant.GESTURE_KEY)
                    if (("ON").equals(userModel.getGestrueSwitch())) {
                        userModel.setGestrueSwitch("OFF");
                        myaccount_iv_pws_switch.setImageResource(R.mipmap.toggle_btn_unchecked);
                        //隐藏
                        myaccount_ll_pws_modify.setVisibility(View.GONE);
                    } else {
                        userModel.setGestrueSwitch("ON");
                        myaccount_iv_pws_switch.setImageResource(R.mipmap.toggle_btn_checked);
                        //显示
                        myaccount_ll_pws_modify.setVisibility(View.VISIBLE);
                    }
                } else {//未设置
                    Intent intent = new Intent(context, SetGestureLockActivity.class);
                    intent.putExtra("KEY", "MeMyAccountFragmentSwitch");
                    startActivity(intent);
                    userModel.setIsFirstSaveLock("first");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_gesturepwd, "", 1);//事件统计
                break;
            case R.id.myaccount_ll_pws_modify://手势密码(去修改)

                //手势密码,验证是否为空
                if (!Q.isEmpty(userModel.getLockPattern())) {//有值,去修改,先验证通过才能修改//LockPatternUtils.getLockPattern(context, Constant.GESTURE_KEY)
                    Intent intents = new Intent(context, CheckoutGestureLockActivity.class);
                    intents.putExtra("KEY", "MeMyAccountFragmentModify");
                    startActivity(intents);
                } else {//空,去设置
                    Intent intent = new Intent(context, SetGestureLockActivity.class);
                    intent.putExtra("KEY", "MeMyAccountFragmentModify");
                    startActivity(intent);
                }
                StatService.onEvent(getContext(), BaiDustatistic.gesturepwd_modify, "", 1);//事件统计
                break;
            case R.id.myaccount_bt_exit_login://退出登录
                //关闭主界面及我的账户
//                remove("MeMainFragment");
//                remove("MeMyAccountFragment");
                //清除数据
                userModel.clear();
                //跳转登录
//                MeLoginFragment();
//                Intent intent = new Intent(getActivity(), MeLoginActivity.class);
//                startActivity(intent);
//                MainActivityTest.mainActivity.finish();

                EventBus.getDefault().post(new IExitLoginCallBack());
                getActivity().finish();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_logout, "", 1);//事件统计
                break;
            default:
                break;
        }
    }


    /**
     * 请求绑定银行卡数据
     */
    private void initDataBindBankCard() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadBindBankCard(strn);
    }

    /**
     * 请求修改交易密码
     */
    private void initDataPwsTrans() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        meMainFragmentPresenter.loadPwsTrans(strn);
    }
    /**
     * 请求修改手机号
     */
    private void initBindPhone() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        meMainFragmentPresenter.loadBindPhone(strn);
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

    /**
     * 请求数据(实名认证)
     */
    private void initDataIsRealName() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadIsRealName(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        //绑卡
        if (model.getClass().equals(BindCardQueryBaseModel.class)){
            BindCardQueryBaseModel cardQueryBaseModel = (BindCardQueryBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(cardQueryBaseModel.status)) {
                if (cardQueryBaseModel.result != null) {

                    if (cardQueryBaseModel.result.size() > 0){
                        //保存数据(银行卡状态)
                        userModel.setcardNo(cardQueryBaseModel.result.get(0).cardNo);
                        userModel.setrealname(cardQueryBaseModel.result.get(0).realname);
                        userModel.setbankName(cardQueryBaseModel.result.get(0).bankName);
                        userModel.setidCard(cardQueryBaseModel.result.get(0).idCard);
                        userModel.setBankCardStatus(cardQueryBaseModel.result.get(0).status);
                        userModel.seticonUrl(cardQueryBaseModel.result.get(0).iconUrl);
                        userModel.setbaofoo_limit_des(cardQueryBaseModel.result.get(0).baofoo_limit_des);
                        userModel.setbaofoo_limit_money(cardQueryBaseModel.result.get(0).baofoo_limit_money);

                        //设置数据
                        setData();
                    }

                }
            } else if (Constant.STATUS_FAILED.equals(cardQueryBaseModel.status)) {

            }
        }else if (model.getClass().equals(IsRealNameBaseModel.class)){
            IsRealNameBaseModel realNameBaseModel = (IsRealNameBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(realNameBaseModel.status)) {
                userModel.setidCard(realNameBaseModel.result.idCard);
                userModel.setrealname(realNameBaseModel.result.realname);
                userModel.setmobileNumber(realNameBaseModel.result.mobileNumber);
                userModel.setauth(realNameBaseModel.result.auth);
                setData();
            } else if (Constant.STATUS_FAILED.equals(realNameBaseModel.status)) {

            }

        }else if (model.getClass().equals(BindBankCardBaseModel.class)){
            P.cancel();
            BindBankCardBaseModel bindBankCardBaseModel = (BindBankCardBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(bindBankCardBaseModel.status)) {
                url = Constant.createAutoSubmitForm(bindBankCardBaseModel.result.sign, bindBankCardBaseModel.result.reqContent, bindBankCardBaseModel.result.actionUrl);
                Constant.actionUrl = bindBankCardBaseModel.result.actionUrl;
                isIntercept = bindBankCardBaseModel.result.isIntercept;
                callbackUrl = bindBankCardBaseModel.result.callbackUrl;

                MeMyAccountBankCardActivity();
            } else if (Constant.STATUS_FAILED.equals(bindBankCardBaseModel.status)) {

            }

        }else if (model.getClass().equals(PwsTransBaseModel.class)){
            P.cancel();
            PwsTransBaseModel pwsTransBaseModel = (PwsTransBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(pwsTransBaseModel.status)){
                url =  Constant.createAutoSubmitForm(pwsTransBaseModel.result.sign,pwsTransBaseModel.result.reqContent,pwsTransBaseModel.result.actionUrl);
                isIntercept = pwsTransBaseModel.result.isIntercept;
                callbackUrl = pwsTransBaseModel.result.callbackUrl;
                Constant.actionUrl = pwsTransBaseModel.result.actionUrl;

                MeMyAccountPwsTransModifyFragment();
            }else if (Constant.STATUS_FAILED.equals(pwsTransBaseModel.status)){

            }
        }else if (model.getClass().equals(BindPhoneBaseModel.class)){
            P.cancel();
            BindPhoneBaseModel bindPhoneBaseModel = (BindPhoneBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(bindPhoneBaseModel.status)) {

                url = Constant.createAutoSubmitForm(bindPhoneBaseModel.result.sign, bindPhoneBaseModel.result.reqContent, bindPhoneBaseModel.result.actionUrl);
                isIntercept = bindPhoneBaseModel.result.isIntercept;
                callbackUrl = bindPhoneBaseModel.result.callbackUrl;
                Constant.actionUrl = bindPhoneBaseModel.result.actionUrl;

                MeMyAccountBindPhoneFragment();
            } else if (Constant.STATUS_FAILED.equals(bindPhoneBaseModel.status)) {

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
     * 手机绑定
     */
    public void MeMyAccountBindPhoneFragment() {

//        if (MeMyAccountBindPhoneFragment == null) {
//        MeMyAccountBindPhoneFragment = new MeMyAccountBindPhoneFragment();
//        }
//        replace(BindPhoneFragment);
//        add(R.id.fragment_myaccount, MeMyAccountBindPhoneFragment, "MeMyAccountBindPhoneFragment", null);

        YeePayModel payModel = new YeePayModel();
        payModel.title = "修改手机号";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));
    }

    /**
     * 身份信息
     */
    public void MeMyAccountIdentityInfoFragment() {

//        if (MeMyAccountIdentityInfoFragment == null) {
        MeMyAccountIdentityInfoFragment = new MeMyAccountIdentityInfoFragment();
//        }
//        replace(IdentityInfoFragment);
        add(R.id.fragment_myaccount, MeMyAccountIdentityInfoFragment, "MeMyAccountIdentityInfoFragment", null);

    }

    /**
     * 银行卡
     */
    public void MeMyAccountBankCardFragment() {

//        if (MeMyAccountBankCardFragment == null) {
        MeMyAccountBankCardFragment = new MeMyAccountBankCardFragment();
//        }
//        replace(BankCardFragment);
        add(R.id.fragment_myaccount, MeMyAccountBankCardFragment, "MeMyAccountBankCardFragment", null);

    }
    /**
     * 银行卡
     */
    public void MeMyAccountBankCardActivity() {

        YeePayModel payModel = new YeePayModel();
        payModel.title = "银行卡";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));
    }

    /**
     * 修改登录密码
     */
    public void MeMyAccountPwsLoginModifyFragment() {

//        if (MeMyAccountPwsLoginModifyFragment == null) {
        MeMyAccountPwsLoginModifyFragment = new MeMyAccountPwsLoginModifyFragment();
//        }
//        replace(PwsLoginModifyFragment);
        add(R.id.fragment_myaccount, MeMyAccountPwsLoginModifyFragment, "MeMyAccountPwsLoginModifyFragment", null);
    }

    /**
     * 修改交易密码
     */
    public void MeMyAccountPwsTransModifyFragment() {

//        if (MeMyAccountPwsTransModifyFragment == null) {
//        MeMyAccountPwsTransModifyFragment = new MeMyAccountPwsTransModifyFragment();
//        }
//        replace(MeMyAccountPwsTransModifyFragment);
//        add(R.id.fragment_myaccount, MeMyAccountPwsTransModifyFragment, "MeMyAccountPwsTransModifyFragment", null);

        YeePayModel payModel = new YeePayModel();
        payModel.title = "修改交易密码";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));
    }


    /**
     * 退出登录
     */
    public void MeLoginFragment() {

//        if (MeLoginFragment == null) {
        MeLoginFragment = new MeLoginFragment();
//        }
//        replace(MeLoginFragment);
        add(R.id.fragment_myaccount, MeLoginFragment, "MeLoginFragment", null);
    }

    /*public void replace(BaseFragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_myaccount, fragment).addToBackStack(null).commit();
    }*/

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "我的账户");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的账户");//(this);
    }

}
