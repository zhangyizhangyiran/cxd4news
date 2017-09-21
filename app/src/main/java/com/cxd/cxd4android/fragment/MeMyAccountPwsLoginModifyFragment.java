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
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyPwsLoginModifyFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeMyAccountPwsLoginModifyFragment extends BaseFragment implements LoadingView {

    /**
     * 修改登录密码正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 修改登录密码左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;


    /**
     * 修改登录密码原始密码
     **/
    @Bind(R.id.pwslogin_tv_old_pws)
     TextView pwslogin_tv_old_pws;
    /**
     * 修改登录密码新密码
     **/
    @Bind(R.id.pwslogin_tv_new_pws)
     TextView pwslogin_tv_new_pws;
    /**
     * 修改登录密码确认密码
     **/
    @Bind(R.id.pwslogin_tv_new_pwsconfirm)
     TextView pwslogin_tv_new_pwsconfirm;

    /**
     * 修改登录密码原始密码
     **/
    @Bind(R.id.pwslogin_et_old_pws)
     EditText pwslogin_et_old_pws;
    /**
     * 修改登录密码新密码
     **/
    @Bind(R.id.pwslogin_et_new_pws)
     EditText pwslogin_et_new_pws;
    /**
     * 修改登录密码确认密码
     **/
    @Bind(R.id.pwslogin_et_new_pwsconfirm)
     EditText pwslogin_et_new_pwsconfirm;

    /**
     * 修改登录密码提交信息
     **/
    @Bind(R.id.pwslogin_bt_submit_info)
     Button pwslogin_bt_submit_info;

    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    MeMyPwsLoginModifyFragmentPresenter MeMyPwsLoginFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myaccount_pwslogin, container, false);
            return contentView;
        }
        return contentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("修改登录密码");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        MeMyPwsLoginFragmentPresenter = new MeMyPwsLoginModifyFragmentPresenter(this);
        //设置监听
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        pwslogin_et_old_pws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwslogin_tv_old_pws.setVisibility(View.INVISIBLE);

                if (!Q.isEmpty(pwslogin_et_old_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pwsconfirm.getText().toString().trim())) {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    pwslogin_bt_submit_info.setClickable(true);
                } else {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    pwslogin_bt_submit_info.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwslogin_et_new_pws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwslogin_tv_new_pws.setVisibility(View.INVISIBLE);

                if (!Q.isEmpty(pwslogin_et_old_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pwsconfirm.getText().toString().trim())) {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    pwslogin_bt_submit_info.setClickable(true);
                    pwslogin_bt_submit_info.setFocusable(true);
                } else {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    pwslogin_bt_submit_info.setClickable(false);
                    pwslogin_bt_submit_info.setFocusable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwslogin_et_new_pwsconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwslogin_tv_new_pwsconfirm.setVisibility(View.INVISIBLE);

                if (!Q.isEmpty(pwslogin_et_old_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pws.getText().toString().trim()) && !Q.isEmpty(pwslogin_et_new_pwsconfirm.getText().toString().trim())) {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    pwslogin_bt_submit_info.setClickable(true);
                } else {
                    pwslogin_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    pwslogin_bt_submit_info.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private String old_pws = "";
    private String new_pws = "";
    private String new_pwsconfirm = "";

    @OnClick({R.id.Btn_left, R.id.pwslogin_bt_submit_info})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();
                //关闭输入法
//                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputMethodManager.isActive()){
//                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                }

                remove("MeMyAccountPwsLoginModifyFragment");

                // 输入法是否弹出
//                if (getActivity().getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
//                    //关闭输入法
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                }

                break;
            case R.id.pwslogin_bt_submit_info://提交信息
                old_pws = pwslogin_et_old_pws.getText().toString().trim();
                new_pws = pwslogin_et_new_pws.getText().toString().trim();
                new_pwsconfirm = pwslogin_et_new_pwsconfirm.getText().toString().trim();
                if (old_pws.length() < 6) {
                    pwslogin_tv_old_pws.setVisibility(View.VISIBLE);
                    return;
                }
                if (new_pws.length() < 6) {
                    pwslogin_tv_new_pws.setVisibility(View.VISIBLE);
                    return;
                }
                if (!new_pws.equals(new_pwsconfirm)) {
                    pwslogin_tv_new_pwsconfirm.setVisibility(View.VISIBLE);
                    return;
                }

                //请求数据
                initData();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount_modifypwd_submit, "", 1);//事件统计
                break;
            default:
                break;
        }
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("newPsw", new_pws);
        map.put("oldPsw", old_pws);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        MeMyPwsLoginFragmentPresenter.loadData(strn);

    }
    @Override
    public void getDataSuccess(Object model) {
        SimpleModel simpleModel = (SimpleModel) model;
        if (Constant.STATUS_SUCCESS.equals(simpleModel.status)) {
            T.D("修改成功");
//                    getFragmentManager().popBackStack();
            remove("MeMyAccountPwsLoginModifyFragment");
        } else if (Constant.STATUS_FAILED.equals(simpleModel.status)) {
            T.D("修改失败");
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
        StatService.onPageStart(getActivity(), "登录密码");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "登录密码");//(this);
    }

}
