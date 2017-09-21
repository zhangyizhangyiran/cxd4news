package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.CheckoutGestureLockActivity;
import com.cxd.cxd4android.activity.SetGestureLockActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IExitLoginCallBack;
import com.cxd.cxd4android.interfaces.IGestrueSetCallBack;
import com.cxd.cxd4android.interfaces.IUpdataGhbUserExt;
import com.cxd.cxd4android.model.GhbBindCardModel;
import com.cxd.cxd4android.model.GhbH5Model;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyGhbAccountPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_unbind_card;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_unbind_card_title;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_update_phone;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_update_phone_title;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_update_pwd;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.account_guide_update_pwd_title;

/**
 * Created by moon.zhong on 2016/10/27.
 * 华兴银行存款账户
 */
public class HXAccountFragment extends BaseFragment implements LoadingView {
    @Bind(R.id.viewtop_toplly)
    LinearLayout viewtopToplly;
    //返回键
    @Bind(R.id.Btn_left)
    TextView BtnLeft;
    //标题
    @Bind(R.id.tv_title)
    TextView tvTitle;
    //开通华兴银行E账户
    @Bind(R.id.hx_tv_dredge_account)
    TextView hxTvDredgeAccount;
    //退出键
    @Bind(R.id.hx_account_exit)
    TextView hxAccountExit;
    //我是手机号码设置图文指引
    @Bind(R.id.hx_obligate_number_guide)
    TextView hxobligatenumberguide;
    //我是密码设置图文指引
    @Bind(R.id.hx_deal_password_guide)
    TextView hxdealpasswordguide;
    //我是绑卡设置图文指引
    @Bind(R.id.hx_tied_card_guide)
    TextView hxtiedcardguide;

    //已通过实名认证,显示该视图
    @Bind(R.id.hx_me_account_ghbaccount_lly)
    LinearLayout getHxLlyGhbAccount;
    //已通过实名认证,户主名称
    @Bind(R.id.include_hx_tide_e_master)
    TextView hxTideEMaster;
    //已通过实名认证,绑卡激活E账户
    @Bind(R.id.include_hx_tide_card_e_account)
    TextView hxTideCardEAccount;
    //已通过实名认证,开户地址
    @Bind(R.id.include_hx_e_account_addr)
    TextView hxEAccountAddr;
    //已通过实名认证,动态获取卡号
    @Bind(R.id.include_hx_e_card_number)
    TextView hxECardNumber;

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

    @Bind(R.id.hx_account_psw_login)
    LinearLayout hxaccountpswlogin;
    private MeMyGhbAccountPresenter meMyGhbAccountPresenter;

    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    GhbH5Model ghbH5Model;

    private GhbGlobalData ghbGlobalData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_hx_account, container, false);
            return contentView;
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userModel = new LocalUserModel();
        ghbH5Model = new GhbH5Model();
        ghbGlobalData = new GhbGlobalData(getActivity());
        EventBus.getDefault().register(this);
        meMyGhbAccountPresenter = new MeMyGhbAccountPresenter(this);
        //TODO: 16/10/25 在代码中更改标题栏
        BtnLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("华兴银行存管账户");

        getHxLlyGhbAccount.setVisibility(View.GONE);
        //请求UserInfo用户数据,得到是否实名认证,ghb_realname_auth
        initloadUserInfo();

        //设置数据
        setData();

        initGuidePage();//请求图文指引数据


        //实名认证成功后,返回E账户信息,绑卡状态信息 身份证号
        initGhbUserExt();

        setEAccountData();
    }

    /**
     * 设置显示E账户数据
     **/
    private void setEAccountData() {

        //判断用户是否实名认证:CHECKED 表示 是,否则没有
        if (("CHECKED").equals(userModel.getGhbAuth())) {
            getHxLlyGhbAccount.setVisibility(View.VISIBLE); //E账户信息 显示
            hxTvDredgeAccount.setVisibility(View.GONE); //开通华兴E账户 隐藏

            //设置显示E账户数据
            hxTideEMaster.setText(userModel.getGhbEUserName());
            hxEAccountAddr.setText(userModel.getGhbEBranch());
            hxECardNumber.setText(userModel.getGhbECardNumber());
            if ("Y".equals(userModel.getGhbBindCard())) {
//                hxTideCardEAccount.setVisibility(View.GONE);
            } else if ("N".equals(userModel.getGhbBindCard()) || "".equals(userModel.getGhbBindCard())) {
                //Todo 没有绑卡成功
            }
        } else {
            getHxLlyGhbAccount.setVisibility(View.GONE);
            hxTvDredgeAccount.setVisibility(View.VISIBLE);

        }

    }

    /**
     * 请求绑定银行卡数据
     */
    private void initDataBindBankCard() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMyGhbAccountPresenter.loadGhbBankCard(strn);
    }

    /**
     * 请求E账户数据和回调绑卡状态查询
     */
    private void initGhbUserExt() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMyGhbAccountPresenter.loadGhbUserExt(strn);
    }

    /**
     * 请求数据(userinfo)
     */
    private void initloadUserInfo() {
        if (!Q.isEmpty(userModel.getid())) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            meMyGhbAccountPresenter.loadUserInfo(strn);
        }

    }

    /**
     * 请求数据(华兴提示信息)
     */
    private void initGuidePage() {
        Gson gson = new Gson();
        String strn = gson.toJson("");

        meMyGhbAccountPresenter.loadGhbGuidePage(strn);
    }

    private void setData() {

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
    public void onEvent(IUpdataGhbUserExt event) {

        Logger.i("HXAccountFragment==>" + event.getUpdataGhbUserExt());
        if ("callbackRealname".equals(event.getUpdataGhbUserExt())) {
            //请求UserInfo用户数据,得到是否实名认证,ghb_realname_auth
            initloadUserInfo();

            //实名认证成功后,返回E账户信息,绑卡状态信息 身份证号
            setEAccountData();

        } else if ("bindCard".equals(event.getUpdataGhbUserExt())) {

            //实名认证成功后,返回E账户信息,绑卡状态信息 身份证号
            initGhbUserExt();
            T.D("绑卡成功");

        } else if ("realnameAuth".equals(event.getUpdataGhbUserExt())) {
            //请求UserInfo用户数据,得到是否实名认证,ghb_realname_auth
            initloadUserInfo();

            //实名认证成功后,返回E账户信息,绑卡状态信息 身份证号
            setEAccountData();

        }

    }

    @OnClick({

            R.id.hx_account_psw_login,
            R.id.hx_tv_dredge_account,
            R.id.myaccount_iv_pws_switch,
            R.id.myaccount_ll_pws_modify,
            R.id.hx_account_exit,
            R.id.hx_obligate_number_guide,
            R.id.hx_deal_password_guide,
            R.id.hx_tied_card_guide,
            R.id.Btn_left,
            R.id.include_hx_tide_card_e_account
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hx_account_psw_login:
                //登陆密码
                MeMyAccountPwsLoginModifyFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_modifypwd, "", 1);//事件统计
                break;

            case R.id.hx_tv_dredge_account:
                //// 开通华兴E账户 跳往图文指引webview
                ghbGlobalData.setRlNmAtGuide();

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
                    Intent intent2 = new Intent(context, SetGestureLockActivity.class);
                    intent2.putExtra("KEY", "MeMyAccountFragmentSwitch");
                    startActivity(intent2);
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
                    Intent intent3 = new Intent(context, SetGestureLockActivity.class);
                    intent3.putExtra("KEY", "MeMyAccountFragmentModify");
                    startActivity(intent3);
                }
                StatService.onEvent(getContext(), BaiDustatistic.gesturepwd_modify, "", 1);//事件统计
                break;
            case R.id.hx_account_exit:
                Toast.makeText(getContext(), "退出", Toast.LENGTH_SHORT).show();
//                getActivity().finish();
                //清除数据
                userModel.clear();

                EventBus.getDefault().post(new IExitLoginCallBack());
                getActivity().finish();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_logout, "", 1);//事件统计
                break;
            case R.id.hx_obligate_number_guide:
                //16/10/26 跳往图文指引webview
                ghbGlobalData.setGhbPhoneGuide();
                break;
            case R.id.hx_deal_password_guide:
                // 16/10/26 跳往图文指引webview
                ghbGlobalData.setGhbUpdatePwdGuide();
                break;
            case R.id.hx_tied_card_guide:
                //16/10/26 跳往图文指引webview
                ghbGlobalData.setGhbUnbindCardGuide();
                break;
            case R.id.Btn_left:
                getActivity().finish();
                break;
            case R.id.include_hx_tide_card_e_account://绑卡激活E账户
                //完成跳转到绑卡激活E账户完成绑卡功能
                initDataBindBankCard();
                Constant.guideType = "bindCard";//设置为绑卡标示,回调时区分 认证和绑卡类型
                break;
            default:

                break;
        }
    }


    /**
     * 修改登录密码
     */
    public void MeMyAccountPwsLoginModifyFragment() {

        MeMyAccountPwsLoginModifyFragment meMyAccountPwsLoginModifyFragment = new MeMyAccountPwsLoginModifyFragment();
        add(R.id.fragment_hxaccount, meMyAccountPwsLoginModifyFragment, "MeMyAccountPwsLoginModifyFragment", null);
    }

    @Override
    public void getDataSuccess(Object model) {
        /*//实名认证
        if (model.getClass().equals(IsRealNameBaseModel.class)) {
            IsRealNameBaseModel isRealName = (IsRealNameBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(isRealName.status)) {
                if (isRealName.result != null) {
                    Toast.makeText(getActivity(), "result--->" + isRealName.result, Toast.LENGTH_SHORT).show();
                }
            } else if (Constant.STATUS_FAILED.equals(isRealName.status)) {

            }
        }*/
        //绑卡
        if (model.getClass().equals(GhbBindCardModel.class)) {
            P.cancel();
            GhbBindCardModel ghbBindCardModel = (GhbBindCardModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbBindCardModel.getStatus())) {
                //Todo 绑卡html
                ghbGlobalData.setGhbBindCardData(ghbBindCardModel.getResult().getHtml());
            } else if (Constant.STATUS_FAILED.equals(ghbBindCardModel.getStatus())) {

            }

        } else if (model.getClass().equals(GuideModel.class)) {

            GuideModel guideModel = (GuideModel) model;
            if (Constant.STATUS_SUCCESS.equals(guideModel.getStatus())) {
                GuideModel.InResult result = guideModel.getResult();

                account_guide_unbind_card = result.getAccount_guide_unbind_card();
                account_guide_unbind_card_title = result.getAccount_guide_unbind_card_title();
                account_guide_update_phone = result.getAccount_guide_update_phone();
                account_guide_update_phone_title = result.getAccount_guide_update_phone_title();
                account_guide_update_pwd = result.getAccount_guide_update_pwd();
                account_guide_update_pwd_title = result.getAccount_guide_update_pwd_title();

            }
        } else if (model.getClass().equals(UserInfoModel.class)) {

            UserInfoModel userInfoModel = (UserInfoModel) model;
            if (Constant.STATUS_SUCCESS.equals(userInfoModel.getStatus())) {
                UserInfoModel.ResultBean result = userInfoModel.getResult();
                userModel.setGhbPayAuth(result.getGhb_realname_auth());
                Log.i("GhbJsInterface->", "userInfoModel--" + result.getGhb_realname_auth());

            }
            initGhbUserExt();
        } else if (model.getClass().equals(GhbUserExtModel.class)) {
            P.cancel();
            GhbUserExtModel ghbUserExtModel = (GhbUserExtModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbUserExtModel.getStatus())) {
                GhbUserExtModel.UserExtResult result = ghbUserExtModel.getResult();
                userModel.saveGhbEAccount(ghbUserExtModel);//N 表示没有绑卡,Y 表示成功
                setEAccountData();//设置显示E账户数据
                Log.i("GhbJsInterface->", "GhbUserExtModel--" + result.getGhbAccount());

            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        P.cancel();
        T.D("加载失败");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}