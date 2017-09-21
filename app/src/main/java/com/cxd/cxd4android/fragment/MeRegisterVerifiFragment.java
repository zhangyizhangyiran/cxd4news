package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.SendCodeModel;
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeRegisterVerifiFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.micros.utils.X;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeRegisterVerifiFragment extends BaseFragment implements LoadingView{

    /**
     * 注册验证码正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 注册验证码左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;

    /**
     * 注册验证码手机号
     **/
    @Bind(R.id.registerverifi_et_phone_num)
     EditText registerverifi_et_phone_num;
    /**
     * 注册验证码验证码
     **/
    @Bind(R.id.registerverifi_et_phonenum_code)
     EditText registerverifi_et_phonenum_code;
    /**
     * 注册验证码获取验证码
     **/
    @Bind(R.id.registerverifi_bt_get_code)
     Button registerverifi_bt_get_code;
    /**
     * 注册验证码下一步
     **/
    @Bind(R.id.registerverifi_bt_submit_next)
     Button registerverifi_bt_submit_next;


    /**
     * 注册验证码手机号错误提示
     **/
    @Bind(R.id.registerverifi_tv_phonenum_error)
     TextView registerverifi_tv_phonenum_error;
    /**
     * 注册验证码手机号绑定提示
     **/
    @Bind(R.id.registerverifi_tv_phonenum_have)
     TextView registerverifi_tv_phonenum_have;
    /**
     * 注册验证码验证码错误提示
     **/
    @Bind(R.id.registerverifi_tv_code_error)
     TextView registerverifi_tv_code_error;
    /**
     * 注册验证码已有账号
     **/
    @Bind(R.id.registerverifi_tv_have_account)
     TextView registerverifi_tv_have_account;


    private MeRegisterInfoFragment MeRegisterInfoFragment;

    /**
     * 验证输入手机号
     **/
    private boolean CheckInputPhone = false;
    /**
     * 验证输入验证码
     **/
    private boolean CheckInputCode = false;
    /**
     * 输入手机号
     **/
    private String Phone = "";
    /**
     * 输入验证码
     **/
    private String Code = "";
    /**
     * 收到验证码
     **/
    private String SendCode = "";
    /**
     * 收到手机号
     **/
    private String SendPhone = "";

    LocalUserModel userModel;
    private TimeCount time;
    MeRegisterVerifiFragmentPresenter registerVerifiPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_registerverifi, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("注册");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        registerVerifiPresenter = new MeRegisterVerifiFragmentPresenter(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        registerverifi_et_phone_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (X.isMobilePhoneVerify(registerverifi_et_phone_num.getText().toString().trim())) {
                        CheckInputPhone = true;
                        /*registerverifi_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                        registerverifi_bt_get_code.setClickable(true);*/
                    } else {
                        CheckInputPhone = false;
                        /*registerverifi_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        registerverifi_bt_get_code.setClickable(false);*/
                    }
                }
            }
        });

        registerverifi_et_phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                registerverifi_tv_phonenum_error.setVisibility(View.INVISIBLE);
                registerverifi_tv_phonenum_have.setVisibility(View.INVISIBLE);
                if (!Q.isEmpty(String.valueOf(s))) {
                    registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerverifi_et_phonenum_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        registerverifi_et_phonenum_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerverifi_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                registerverifi_tv_code_error.setVisibility(View.INVISIBLE);
                if (CheckInputPhone) {
                    if (s.length() > 0) {
                        CheckInputCode = true;
                        registerverifi_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                        registerverifi_bt_submit_next.setClickable(true);
                    } else {
                        CheckInputCode = false;
                        registerverifi_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        registerverifi_bt_submit_next.setClickable(false);
                    }
                } else {
                    CheckInputCode = false;
                    registerverifi_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    registerverifi_bt_submit_next.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.Btn_left, R.id.registerverifi_bt_get_code, R.id.registerverifi_bt_submit_next, R.id.registerverifi_tv_have_account})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
                remove("MeRegisterVerifiFragment");
                break;
            case R.id.registerverifi_bt_get_code://获取验证码
                Phone = registerverifi_et_phone_num.getText().toString().trim();
                Code = registerverifi_et_phonenum_code.getText().toString().trim();

                if (!X.isMobilePhoneVerify(Phone)) {
                    registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerverifi_tv_phonenum_error.setVisibility(View.VISIBLE);
                    return;
                }
                //获取验证码
                checkPhone();
                break;
            case R.id.registerverifi_bt_submit_next://下一步
                Phone = registerverifi_et_phone_num.getText().toString().trim();
                Code = registerverifi_et_phonenum_code.getText().toString().trim();
                if (Code.length() < 4) {
                    registerverifi_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerverifi_tv_code_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!X.isMobilePhoneVerify(Phone)) {
                    registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                    registerverifi_tv_phonenum_error.setVisibility(View.INVISIBLE);
                    return;
                }
                if (!SendCode.equals(Code)) {//验证码填写错误
                    registerverifi_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerverifi_tv_code_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!SendPhone.equals(Phone)) {//手机号填写错误
                    registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerverifi_tv_phonenum_error.setVisibility(View.VISIBLE);
                    return;
                }

                userModel.setmobileNumber(Phone);
                userModel.setauthCode(Code);

                MeRegisterInfoFragment();
                break;
            case R.id.registerverifi_tv_have_account://已有账户
//                getFragmentManager().popBackStack();
                remove("MeRegisterVerifiFragment");
                break;
            default:
                break;
        }
    }

    /**
     * 验证手机号是否可以注册
     */
    private void checkPhone() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNo", Phone);
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        registerVerifiPresenter.loadData(strn);
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNo", Phone);
        map.put("type", "register");//发送短信接口修改，增加type参数type :    register     注册, lookpwd    找回密码


        Gson gson = new Gson();
        String strn = gson.toJson(map);
        registerVerifiPresenter.loadSendCode(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(SimpleModel.class)){
            SimpleModel simpleModel = (SimpleModel) model;
            if (Constant.STATUS_SUCCESS.equals(simpleModel.status)) {//手机号可以注册
                time.start();//开始计时
                sendCode();
            } else {//手机号已经注册
                registerverifi_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                registerverifi_tv_phonenum_have.setVisibility(View.VISIBLE);
                    /*registerverifi_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    registerverifi_bt_get_code.setClickable(false);*/

            }
        }else if (model.getClass().equals(SendCodeModel.class)){
            SendCodeModel sendCodeModel = (SendCodeModel) model;
            if (Constant.STATUS_SUCCESS.equals(sendCodeModel.status)) {//发送成功
                T.D("发送成功");
                SendCode = sendCodeModel.result.authCode;
                SendPhone = Phone;
            } else {//发送失败
                T.D("发送失败");
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 验证码倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            registerverifi_bt_get_code.setText("重新获取");
            registerverifi_bt_get_code.setClickable(true);
            registerverifi_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);

        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            registerverifi_bt_get_code.setClickable(false);
            registerverifi_bt_get_code.setText(millisUntilFinished / 1000 + "\t秒");
            registerverifi_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
        }
    }

    /**
     * 注册验证码下一步
     */
    public void MeRegisterInfoFragment() {

//        if (MeRegisterInfoFragment == null) {
        MeRegisterInfoFragment = new MeRegisterInfoFragment();
//        }

//        replace(MeRegisterInfoFragment);
        add(R.id.main_fr_main_me, MeRegisterInfoFragment, "MeRegisterInfoFragment", null);
    }

    /*public void replace(BaseFragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fr_main_me, fragment).addToBackStack("LoginRegist").commit();
    }*/

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "注册验证");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "注册验证");//(this);
    }
}
