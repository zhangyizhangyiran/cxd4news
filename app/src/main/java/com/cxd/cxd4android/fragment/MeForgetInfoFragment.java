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
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeForgetInfoFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeForgetInfoFragment extends BaseFragment implements LoadingView{

    /**
     * 忘记密码信息正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 忘记密码信息左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 忘记密码信息输入新密码
     **/
    @Bind(R.id.forgetinfo_et_login_pws)
     EditText forgetinfo_et_login_pws;
    /**
     * 忘记密码信息确认
     **/
    @Bind(R.id.forgetinfo_bt_submit_next)
     Button forgetinfo_bt_submit_next;
    /**
     * 忘记密码信息密码错误提示
     **/
    @Bind(R.id.forgetinfo_tv_loginpws_error)
     TextView forgetinfo_tv_loginpws_error;

    /**
     * 用户密码
     **/
    private String Pws = "";
    LocalUserModel userModel;
    MeForgetInfoFragmentPresenter forgetInfoFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_forgetinfo, container, false);
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
        forgetInfoFragmentPresenter= new MeForgetInfoFragmentPresenter(this);

        setListener();

    }

    /**
     * 设置监听
     */
    private void setListener() {
        forgetinfo_et_login_pws.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        forgetinfo_et_login_pws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgetinfo_et_login_pws.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                forgetinfo_tv_loginpws_error.setVisibility(View.INVISIBLE);
                if (Q.isEmpty(String.valueOf(s))) {
                    forgetinfo_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forgetinfo_bt_submit_next.setClickable(false);
                } else {
                    forgetinfo_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    forgetinfo_bt_submit_next.setClickable(true);
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


    @OnClick({R.id.Btn_left, R.id.forgetinfo_bt_submit_next})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                remove("MeForgetInfoFragment");
                break;
            case R.id.forgetinfo_bt_submit_next://确认

                Pws = forgetinfo_et_login_pws.getText().toString().trim();
                if (Pws.length() < 6) {
                    forgetinfo_et_login_pws.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    forgetinfo_tv_loginpws_error.setVisibility(View.VISIBLE);
                    forgetinfo_bt_submit_next.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    forgetinfo_bt_submit_next.setClickable(false);
                    return;
                }
                //重置密码
                resetPsw();
                break;
            default:
                break;
        }
    }

    private void resetPsw() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("authCode", userModel.getauthCode());
        map.put("password", Pws);
        map.put("phoneNo", userModel.getmobileNumber());

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        forgetInfoFragmentPresenter.loadData(strn);
//        MicroRequestParams params = new MicroRequestParams();
//        params.put("value", strn);
//
//        //LoginBaseModel
//        TypeToken<List<SimpleModel>> typeToken = new TypeToken<List<SimpleModel>>() {
//        };
//        FinalFetch<SimpleModel> fetch = new FinalFetch<SimpleModel>(new IFetch<SimpleModel>() {
//            @Override
//            public void onSuccess(List<SimpleModel> datas) {
//                SimpleModel model = datas.get(0);
//                if (Constant.STATUS_SUCCESS.equals(model.status)) {
//
//                    T.D("重置成功");
//                    remove("MeForgetInfoFragment");
//                    remove("MeForgetVerifiFragment");
//                } else if (Constant.STATUS_FAILED.equals(model.status)) {
//                    T.D("修改失败");
//                }
//
//            }
//
//            @Override
//            public void onFail(ResultModel result) {
//                L.I(Constant.CONNECT_FAILURE);
//
//            }
//
//            @Override
//            public void onFetching(int proccess) {
//            }
//
//            @Override
//            public void onPrevious() {
//            }
//        }, params, typeToken, Constant.userc_resetPsw);
    }
    @Override
    public void getDataSuccess(Object model) {
        SimpleModel simpleModel = (SimpleModel) model;
        if (Constant.STATUS_SUCCESS.equals(simpleModel.status)) {

            T.D("重置成功");
            remove("MeForgetInfoFragment");
            remove("MeForgetVerifiFragment");
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
        StatService.onPageStart(getActivity(), "忘记密码信息");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "忘记密码信息");//(this);
    }


}
