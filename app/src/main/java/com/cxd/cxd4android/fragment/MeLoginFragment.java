package com.cxd.cxd4android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.LoginBaseModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeLoginFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.A;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeLoginFragment extends BaseFragment implements LoadingView {

    /**
     * 登录正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 登录右上角标题
     **/
    @Bind(R.id.tv_right)
     TextView tv_right;
    /**
     * 登录用户名或手机号
     **/
    @Bind(R.id.login_et_name_phone)
     EditText login_et_name_phone;
    /**
     * 登录密码
     **/
    @Bind(R.id.login_et_pws_login)
     EditText login_et_pws_login;
    /**
     * 登录登录
     **/
    @Bind(R.id.login_bt_submit_login)
     Button login_bt_submit_login;
    /**
     * 登录忘记密码?
     **/
    @Bind(R.id.login_tv_forget_pws)
     TextView login_tv_forget_pws;
    /**
     * 登录用户名错误提示
     **/
    @Bind(R.id.login_tv_namephone_error)
     TextView login_tv_namephone_error;
    /**
     * 登录登录密码错误提示
     **/
    @Bind(R.id.login_tv_pwslogin_error)
     TextView login_tv_pwslogin_error;
    /**
     * 注册
     **/
    private MeRegisterVerifiFragment MeRegisterVerifiFragment;
    /**
     * 忘记密码
     **/
    private MeForgetVerifiFragment MeForgetVerifiFragment;
    /**
     * 我的主页
     **/
    private MeMainFragment MeMainFragment;

    /**
     * 检查输入用户名
     **/
    private boolean CheckInputName = false;
    /**
     * 检查输入密码
     **/
    private boolean CheckInputPws = false;
    /**
     * 用户名
     **/
    private String Name = "";
    /**
     * 密码
     **/
    private String Pws = "";
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 用户id(用于登录后请求银行卡)
     **/
    private String userId = "";
    MeLoginFragmentPresenter loginFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_login, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("登录");
        tv_right.setText("注册");
        tv_right.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        loginFragmentPresenter = new MeLoginFragmentPresenter(this);
        //设置数据
        setData();

        //设置监听
        setListener();

    }

    /**
     * 设置数据
     */
    private void setData() {

        if(("MainMeFragment").equals(Constant.FRAGMENT_JUMP)){
            login_et_name_phone.setText(userModel.getid());
            CheckInputName = true;
        }else {
            login_et_name_phone.setText("");
            CheckInputName = false;
        }


    }

    /**
     * 设置监听
     */
    private void setListener() {
        login_et_name_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Q.isEmpty(login_et_name_phone.getText().toString().trim())) {
                        CheckInputName = true;
                        Login();
                        return;
                    } else {
                        CheckInputName = false;
                        Login();
                    }
                }
            }
        });
        login_et_name_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Q.isEmpty(String.valueOf(s))) {
                    CheckInputName = true;
                    Login();
                } else {
                    CheckInputName = false;
                    Login();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_et_pws_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!Q.isEmpty(login_et_pws_login.getText().toString().trim())) {
                    CheckInputPws = true;
                    Login();
                    return;
                } else {
                    CheckInputPws = false;
                    Login();
                }
            }

        });
        login_et_pws_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    CheckInputPws = true;
                    login_tv_pwslogin_error.setVisibility(View.INVISIBLE);
                    login_et_pws_login.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                    Login();
                } else {
                    CheckInputPws = false;
                    Login();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void Login() {

        if (CheckInputName && CheckInputPws) {
            login_bt_submit_login.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
            login_bt_submit_login.setClickable(true);
        } else {
            login_bt_submit_login.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
            login_bt_submit_login.setClickable(false);
        }
    }


    @OnClick({R.id.tv_right, R.id.login_bt_submit_login, R.id.login_tv_forget_pws})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right://注册
                MeRegisterVerifiFragment();
                StatService.onEvent(getContext(), BaiDustatistic.register, "", 1);//事件统计
                break;
            case R.id.login_bt_submit_login://登录
                Name = login_et_name_phone.getText().toString().trim();
                Pws = login_et_pws_login.getText().toString().trim();
                if (Pws.length() < 6) {
                    login_tv_pwslogin_error.setVisibility(View.VISIBLE);
                    login_et_pws_login.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    return;
                }
                //请求数据
                initData();
                StatService.onEvent(getContext(), BaiDustatistic.login, "", 1);//事件统计
                break;
            case R.id.login_tv_forget_pws://忘记密码?

                MeRegisterForgetFragment();
                StatService.onEvent(getContext(), BaiDustatistic.forgetpwd, "", 1);//事件统计
                break;
            default:
                break;
        }
    }

    /** 回调接口 **/
    public static ILoginCallBack ILoginCallBack;

    /**
     * 请求数据(登录)
     */
    private void initData() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginName", Name);
        map.put("password", Pws);
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        loginFragmentPresenter.loadData(strn);

    }

    /**
     * 请求数据(银行卡状态)
     */
    private void initDataBankCard() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        loginFragmentPresenter.loadBindCardQuery(strn);
    }

    /**
     * 请求数据(保存用户系统信息)
     */
    private void initDataUploadDeviceToken() {
        Map<String, String> map = new HashMap<String, String>();
        /*uid 用户ID
        phone_model 手机型号
        system_version 系统版本
        system_type 系统类型
        ip IP地址
        device_token 系统标示
        app_version APP版本*/

        map.put("uid", userId);
        map.put("phone_model", getOsDisplay(android.os.Build.MODEL));
        map.put("system_version", getOsDisplay(android.os.Build.VERSION.RELEASE));
        map.put("system_type", "Android");
        map.put("ip", A.getIPAddress(context));
        map.put("app_version", userModel.getAppVersionName());

        if (("READ_PHONE_STATE").equals(userModel.getREAD_PHONE_STATE())) {
            map.put("device_token", A.getMyDeviceID(context));
        } else {
            map.put("device_token", "用户未授权");
        }


        Gson gson = new Gson();
        String strn = gson.toJson(map);
        loginFragmentPresenter.loadUploadDeviceToken(strn);
        Logger.i(strn);
    }
    /**解决OKHttp-header中不能传中文问题**/
    private String getOsDisplay(String str) {
        if (str.length() < str.getBytes().length) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return str;
        }
    }
    @Override
    public void getDataSuccess(Object model) {
        P.cancel();
        if (model.getClass().equals(LoginBaseModel.class)){
            LoginBaseModel loginBaseModel = (LoginBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(loginBaseModel.status)) {
                userId = loginBaseModel.result.id;
                //登录状态
                userModel.setLOGIN_STATE(userModel.LOGIN_STATE_ONLINE);
                //保存数据
                SaveData(loginBaseModel);

                //极光推送设置别名
                setJpushAlias(userId);
                if ("MainActivityTest".equals(Constant.INTENT_JUMP)) {//跳转投资页面(购买)
                        /*Intent intent = new Intent(getActivity(), BuyProductActivity.class);
                        getActivity().startActivity(intent);*/
                    //跳转
                    MeMainFragment();
                }else if("SeFeedBackFragment".equals(Constant.INTENT_JUMP)){//跳转
                    //跳转
                    MeMainFragment();
//                        ILoginCallBack.OnLogin(3);//跳转服务
                }else {
                    //跳转
                    MeMainFragment();
                }
                initDataUploadDeviceToken();
                initDataBankCard();
                //关闭输入法
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

            } else if (Constant.STATUS_FAILED.equals(loginBaseModel.status)) {
                T.D("登录失败");
            }
        }else if (model.getClass().equals(BindCardQueryBaseModel.class)){
            BindCardQueryBaseModel bindCardQueryBaseModel = (BindCardQueryBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(bindCardQueryBaseModel.status)) {
                if (bindCardQueryBaseModel.result != null) {

                    if (bindCardQueryBaseModel.result.size() > 0){
                        //保存数据(银行卡状态)
                        SaveDataBankCard(bindCardQueryBaseModel);
                    }

                }
            } else if (Constant.STATUS_FAILED.equals(bindCardQueryBaseModel.status)) {

            }

        }
    }

    @Override
    public void getDataFail(String msg) {
        P.cancel();
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    /**
     * 保存数据(登录)
     * @param model
     */
    private void SaveData(final LoginBaseModel model) {
        userModel.SaveLoginData(model);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//               ;
//            }
//
//        });
//        thread.start();
    }

    /**
     * 保存数据(银行卡状态)
     * @param model
     */
    private void SaveDataBankCard(final BindCardQueryBaseModel model) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userModel.SaveBankCardData(model);
            }

        });
        thread.start();
    }

    /**
     * 登录跳转主页
     */
    public void MeMainFragment() {

//        if (MeMainFragment == null) {
            MeMainFragment = new MeMainFragment();
//        }
        add(R.id.main_fr_main_me, MeMainFragment, "MeMainFragment", null);

    }

    /**
     * 设置极光推送别名 setJpushAlias()
     */
    private void setJpushAlias(String alias) {
        //设置极光推送别名
        JPushInterface.setAlias(getContext().getApplicationContext(), alias, null);
    }
    /**
     * 登录忘记密码
     */
    public void MeRegisterForgetFragment() {

//        if (MeForgetVerifiFragment == null) {
            MeForgetVerifiFragment = new MeForgetVerifiFragment();
//        }
        add(R.id.main_fr_main_me, MeForgetVerifiFragment, "MeForgetVerifiFragment", null);
    }

    /**
     * 登录注册
     */
    public void MeRegisterVerifiFragment() {

//        if (MeRegisterVerifiFragment == null) {
            MeRegisterVerifiFragment = new MeRegisterVerifiFragment();
//        }
        //if (!MeRegisterVerifiFragment.isAdded())
        add(R.id.main_fr_main_me, MeRegisterVerifiFragment, "MeRegisterVerifiFragment", null);
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "登录页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "登录页面");//(this);
    }
}
