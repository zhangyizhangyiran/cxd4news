package com.cxd.cxd4android.fragment;

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
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.CheckMobileBaseModel;
import com.cxd.cxd4android.model.RegisterBaseModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeRegisterFragmentPresenter;
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
public class MeRegisterInfoFragment extends BaseFragment implements LoadingView{

    /**
     * 注册信息正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 注册信息左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 注册信息用户名
     **/
    @Bind(R.id.registerinfo_et_user_name)
     EditText registerinfo_et_user_name;
    /**
     * 注册信息登录密码
     **/
    @Bind(R.id.registerinfo_et_login_pws)
     EditText registerinfo_et_login_pws;
    /**
     * 注册信息推荐人
     **/
    @Bind(R.id.registerinfo_et_referee_name)
     EditText registerinfo_et_referee_name;
    /**
     * 注册信息注册
     **/
    @Bind(R.id.register_bt_submit_next)
     Button register_bt_submit_next;
    /**
     * 注册信息用户名错误提示
     **/
    @Bind(R.id.registerinfo_tv_username_error)
     TextView registerinfo_tv_username_error;
    /**
     * 注册信息登录密码错误提示
     **/
    @Bind(R.id.registerinfo_tv_loginpws_error)
     TextView registerinfo_tv_loginpws_error;
    /**
     * 注册信息推荐人错误提示
     **/
    @Bind(R.id.registerinfo_tv_refereename_error)
     TextView registerinfo_tv_refereename_error;

    /**
     * 检查输入用户名
     **/
    private boolean CheckInputName = false;
    /**
     * 检查输入登录密码
     **/
    private boolean CheckInputPws = false;
    /**
     * 检查输入推荐人
     **/
    private boolean CheckInputRecom = false;

    /**
     * 检查输入用户名
     **/
    private String Name = "";
    /**
     * 检查输入登录密码
     **/
    private String Pws = "";
    /**
     * 检查输入推荐人
     **/
    private String Recom = "";

    private MeMainFragment MeMainFragment;

    LocalUserModel userModel;

    MeRegisterFragmentPresenter registerFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_registerinfo, container, false);
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
        registerFragmentPresenter = new MeRegisterFragmentPresenter(this);
        setListener();
    }

    /**
     * 监听设置
     */
    private void setListener() {
        registerinfo_et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerinfo_et_user_name.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                registerinfo_tv_username_error.setVisibility(View.INVISIBLE);
                if (Q.isEmpty(String.valueOf(s))) {
                    CheckInputName = false;
                } else {
                    CheckInputName = true;
                }

                if (CheckInputName && CheckInputPws) {
                    register_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    register_bt_submit_next.setClickable(true);
                } else {
                    register_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    register_bt_submit_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerinfo_et_login_pws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerinfo_et_login_pws.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                registerinfo_tv_loginpws_error.setVisibility(View.INVISIBLE);
                if (Q.isEmpty(String.valueOf(s))) {
                    CheckInputPws = false;
                } else {
                    CheckInputPws = true;
                }

                if (CheckInputName && CheckInputPws) {
                    register_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    register_bt_submit_next.setClickable(true);
                } else {
                    register_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    register_bt_submit_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerinfo_et_referee_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerinfo_et_referee_name.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                registerinfo_tv_refereename_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick( {R.id.Btn_left, R.id.register_bt_submit_next})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
                remove("MeRegisterInfoFragment");
                break;
            case R.id.register_bt_submit_next://注册

                Name = registerinfo_et_user_name.getText().toString().trim();
                Pws = registerinfo_et_login_pws.getText().toString().trim();
                Recom = registerinfo_et_referee_name.getText().toString().trim();

                if (Name.length() < 6 || Name.length()>16) {
                    registerinfo_et_user_name.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerinfo_tv_username_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (Pws.length() < 6 || Pws.length()>16) {
                    registerinfo_et_login_pws.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerinfo_tv_loginpws_error.setVisibility(View.VISIBLE);
                    return;
                }

                //是否全是字符串(字母,数字)
                if (!Q.isAllText(Name)){
                    registerinfo_et_user_name.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerinfo_tv_username_error.setVisibility(View.VISIBLE);
                    return;
                }

                if (!Q.isEmpty(Recom)) {
                    if (!X.isMobilePhoneVerify(Recom)) {

                        registerinfo_et_referee_name.setBackgroundResource(R.drawable.shape_layout_circle_red);
                        registerinfo_tv_refereename_error.setVisibility(View.VISIBLE);
                        registerinfo_tv_refereename_error.setText("手机格式不正确，请重新输入！");
                        return;
                    }else {
                        checkRecom();
                    }
                } else {
                    register();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", Name);
        map.put("password", Pws);
        map.put("phoneNo", userModel.getmobileNumber());
        map.put("authCode", userModel.getauthCode());
        map.put("referrer", Recom);

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        registerFragmentPresenter.loadData(strn);
    }

    /**
     * 验证推荐人是否存在
     */
    private void checkRecom() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNo", Recom);
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        registerFragmentPresenter.loadIsHaveUser(strn);

    }
    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(RegisterBaseModel.class)){
            RegisterBaseModel registerBaseModel = (RegisterBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(registerBaseModel.status)) {//注册成功

                //保存数据
//                    SaveData(model);
                userModel.setid(registerBaseModel.result.get(0).id);
//                    getFragmentManager().popBackStack("LoginRegist",1);//1是包括自己
                //跳转
//                    MeMainFragment();
                T.D("注册成功");
                remove("MeRegisterInfoFragment");
                remove("MeRegisterVerifiFragment");

            } else {//注册失败
                T.D(registerBaseModel.msg + "");
            }
        }else if (model.getClass().equals(CheckMobileBaseModel.class)){
            CheckMobileBaseModel mobileBaseModel = (CheckMobileBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(mobileBaseModel.status)) {
                //验证手机号格式
                if (!X.isMobilePhoneVerify(Recom)) {

                    registerinfo_et_referee_name.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerinfo_tv_refereename_error.setVisibility(View.VISIBLE);
                    registerinfo_tv_refereename_error.setText("手机格式不正确，请重新输入！");
                }else if (("true").equals(mobileBaseModel.result.isUse)){//手机号不存在
                    registerinfo_et_referee_name.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    registerinfo_tv_refereename_error.setVisibility(View.VISIBLE);
                    registerinfo_tv_refereename_error.setText("推荐人号码不存在,请重新输入！");
                }else {//手机号存在
                    register();
                }
            } else {
                T.D(mobileBaseModel.msg);
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

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "注册信息");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "注册信息");//(this);
    }


}
