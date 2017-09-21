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
import com.cxd.cxd4android.model.CheckMobileBaseModel;
import com.cxd.cxd4android.model.SendCodeModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeForgetVerifiFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.micros.utils.X;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeForgetVerifiFragment extends BaseFragment implements LoadingView{

    /**
     * 忘记密码正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 忘记密码左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 忘记密码手机号
     **/
    @Bind(R.id.forget_et_phone_num)
     EditText forget_et_phone_num;
    /**
     * 忘记密码验证码
     **/
    @Bind(R.id.forget_et_phonenum_code)
     EditText forget_et_phonenum_code;
    /**
     * 忘记密码获取验证码
     **/
    @Bind(R.id.forget_bt_get_code)
     Button forget_bt_get_code;
    /**
     * 忘记密码下一步
     **/
    @Bind(R.id.forget_bt_submit_next)
     Button forget_bt_submit_next;
    /**
     * 忘记密码手机号错误提示
     **/
    @Bind(R.id.forget_tv_phonenum_error)
     TextView forget_tv_phonenum_error;
    /**
     * 忘记密码验证码错误提示
     **/
    @Bind(R.id.forget_tv_code_error)
     TextView forget_tv_code_error;

    private MeForgetInfoFragment MeForgetInfoFragment;

    /** 检查输入手机号 **/
    private boolean CheckInputPhone = false;
    /** 检查输入验证码 **/
    private boolean CheckInputCode = false;
    /** 输入手机号 **/
    private String Phone = "";
    /** 输入验证码 **/
    private String Code = "";
    /** 收到验证码 **/
    private String SendCode = "";
    /** 收到手机号 **/
    private String SendPhone = "";

    LocalUserModel userModel;
    private TimeCount time;
    MeForgetVerifiFragmentPresenter meForgetVerifiPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_forgetverifi, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("忘记密码");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        meForgetVerifiPresenter = new MeForgetVerifiFragmentPresenter(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        setListener();

    }

    /**
     * 设置监听
     */
    private void setListener() {

        forget_et_phone_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (X.isMobilePhoneVerify(forget_et_phone_num.getText().toString().trim())) {
                        CheckInputPhone = true;
                        /*forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                        forget_bt_get_code.setClickable(true);*/
                    } else {
                        CheckInputPhone = false;
                        /*forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        forget_bt_get_code.setClickable(false);*/
                    }
                }
            }
        });

        forget_et_phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                forget_tv_phonenum_error.setVisibility(View.INVISIBLE);
                if (!Q.isEmpty(String.valueOf(s))) {
                    forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                    /*if (s.length() == 11) {
                        CheckInputPhone = true;
                        forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                        forget_bt_get_code.setClickable(true);
                    } else {
                        CheckInputPhone = false;
                        forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        forget_bt_get_code.setClickable(false);

                    }*/

                }/* else {
                    CheckInputPhone = false;
                    forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forget_bt_get_code.setClickable(false);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        forget_et_phonenum_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        forget_et_phonenum_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forget_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                forget_tv_code_error.setVisibility(View.INVISIBLE);
                if (CheckInputPhone) {
                    if (s.length() > 0) {
                        CheckInputCode = true;
                        forget_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                        forget_bt_submit_next.setClickable(true);
                    } else {
                        CheckInputCode = false;
                        forget_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        forget_bt_submit_next.setClickable(false);
                    }
                } else {
                    CheckInputCode = false;
                    forget_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forget_bt_submit_next.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.Btn_left,R.id.forget_bt_get_code,R.id.forget_bt_submit_next})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
                remove("MeForgetVerifiFragment");
                break;
            case R.id.forget_bt_get_code://获取验证码
                Phone = forget_et_phone_num.getText().toString().trim();
                Code = forget_et_phonenum_code.getText().toString().trim();

                if (!X.isMobilePhoneVerify(Phone)){
                    forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forget_tv_phonenum_error.setVisibility(View.VISIBLE);
                    return;
                }

                //获取验证码
                checkPhone();
                break;
            case R.id.forget_bt_submit_next://下一步
                Phone = forget_et_phone_num.getText().toString().trim();
                Code = forget_et_phonenum_code.getText().toString().trim();
                if(Code.length() < 4){
                    forget_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forget_tv_code_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!X.isMobilePhoneVerify(Phone)){
                    forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                    forget_tv_phonenum_error.setVisibility(View.INVISIBLE);
                    return;
                }
                if (!SendCode.equals(Code)){//验证码填写错误
                    forget_et_phonenum_code.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forget_tv_code_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!SendPhone.equals(Phone)){//手机号填写错误
                    forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forget_tv_phonenum_error.setVisibility(View.VISIBLE);
                    return;
                }

                userModel.setmobileNumber(Phone);
                userModel.setauthCode(Code);

                MeForgetInfoFragment();
                break;
            default:
                break;
        }
    }

    /**
     * 验证手机号是否存在
     */
    private void checkPhone() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNo", Phone);
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meForgetVerifiPresenter.loadIsHaveUser(strn);

/*        MicroRequestParams params = new MicroRequestParams();
        params.put("value", strn);

        TypeToken<List<CheckMobileBaseModel>> typeToken = new TypeToken<List<CheckMobileBaseModel>>() {
        };
        FinalFetch<CheckMobileBaseModel> fetch = new FinalFetch<CheckMobileBaseModel>(new IFetch<CheckMobileBaseModel>() {
            @Override
            public void onSuccess(List<CheckMobileBaseModel> datas) {
                CheckMobileBaseModel model = datas.get(0);
                if (Constant.STATUS_SUCCESS.equals(model.status)){//手机号不存在

                    if (("true").equals(model.result.isUse)){//手机号不存在
                        forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                        forget_tv_phonenum_error.setVisibility(View.VISIBLE);
                    *//*forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forget_bt_get_code.setClickable(false);*//*
                    }else {//手机号存在
                        time.start();//开始计时
                        sendCode();
                    }

                }else{
                    T.D(model.msg+"");
                }
            }

            @Override
            public void onFail(ResultModel result) {
                L.I(Constant.CONNECT_FAILURE);

            }

            @Override
            public void onFetching(int proccess) {
            }

            @Override
            public void onPrevious() {
            }
        }, params, typeToken, Constant.userc_isHaveUser);*/
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNo", Phone);
        map.put("type", "lookpwd");//发送短信接口修改，增加type参数type :    register     注册, lookpwd    找回密码

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meForgetVerifiPresenter.loadSendCode(strn);

/*        MicroRequestParams params = new MicroRequestParams();
        params.put("value", strn);

        TypeToken<List<SendCodeModel>> typeToken = new TypeToken<List<SendCodeModel>>() {
        };
        FinalFetch<SendCodeModel> fetch = new FinalFetch<SendCodeModel>(new IFetch<SendCodeModel>() {
            @Override
            public void onSuccess(List<SendCodeModel> datas) {
                SendCodeModel model = datas.get(0);
                if (Constant.STATUS_SUCCESS.equals(model.status)){//发送成功
                    T.D("发送成功");
                    SendCode = model.result.authCode;
                    SendPhone = Phone;
                }else{//发送失败
                    T.D("发送失败");
                }
            }

            @Override
            public void onFail(ResultModel result) {
                L.I(Constant.CONNECT_FAILURE);

            }

            @Override
            public void onFetching(int proccess) {
            }

            @Override
            public void onPrevious() {
            }
        }, params, typeToken, Constant.userc_registerSendVerificationCode);*/
    }

    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(CheckMobileBaseModel.class)){
            CheckMobileBaseModel mobileBaseModel = (CheckMobileBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(mobileBaseModel.status)){//手机号不存在

                if (("true").equals(mobileBaseModel.result.isUse)){//手机号不存在
                    forget_et_phone_num.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forget_tv_phonenum_error.setVisibility(View.VISIBLE);
                    /*forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forget_bt_get_code.setClickable(false);*/
                }else {//手机号存在
                    time.start();//开始计时
                    sendCode();
                }
            }else{
                T.D(mobileBaseModel.msg+"");
            }
        }else if (model.getClass().equals(SendCodeModel.class)){
            SendCodeModel sendCodeModel = (SendCodeModel) model;
            if (Constant.STATUS_SUCCESS.equals(sendCodeModel.status)){//发送成功
                T.D("发送成功");
                SendCode = sendCodeModel.result.authCode;
                SendPhone = Phone;
            }else{//发送失败
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
            forget_bt_get_code.setText("重新获取");
            forget_bt_get_code.setClickable(true);
            forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);

        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            forget_bt_get_code.setClickable(false);
            forget_bt_get_code.setText(millisUntilFinished / 1000 + "\t秒");
            forget_bt_get_code.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
        }
    }

    /**
     * 忘记密码验证码下一步
     */
    public void MeForgetInfoFragment() {

//        if (MeForgetInfoFragment == null) {
            MeForgetInfoFragment = new MeForgetInfoFragment();
//        }

//        replace(MeForgetInfoFragment);
        add(R.id.main_fr_main_me, MeForgetInfoFragment, "MeForgetInfoFragment", null);
    }

  /*  public void replace(BaseFragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fr_main_me, fragment).addToBackStack("LoginRegist").commit();
    }*/

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "忘记密码验证");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "忘记密码验证");//(this);
    }
}
