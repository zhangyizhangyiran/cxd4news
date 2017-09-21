package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyIdentityinfoFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.micros.utils.X;
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
public class MeMyAccountIdentityInfoFragment extends BaseFragment implements LoadingView {

    /**
     * 身份信息正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 身份信息左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 身份信息认证状态
     **/
    @Bind(R.id.identityinfo_tv_real_type)
    TextView identityinfo_tv_real_type;
    /**
     * 身份信息真是姓名错误提示
     **/
    @Bind(R.id.identityinfo_tv_bind_phone)
    TextView identityinfo_tv_bind_phone;
    /**
     * 身份信息身份证错误提示
     **/
    @Bind(R.id.identityinfo_tv_card_num)
    TextView identityinfo_tv_card_num;
    /**
     * 身份信息手机号错误提示
     **/
    @Bind(R.id.identityinfo_tv_real_name)
    TextView identityinfo_tv_real_name;

    /**
     * 身份信息真实姓名
     **/
    @Bind(R.id.identityinfo_et_real_name)
    EditText identityinfo_et_real_name;
    /**
     * 身份信息身份证号
     **/
    @Bind(R.id.identityinfo_et_card_num)
    EditText identityinfo_et_card_num;
    /**
     * 身份信息提交信息
     **/
    @Bind(R.id.identityinfo_bt_submit_info)
     Button identityinfo_bt_submit_info;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 跳转
     **/
    private WebViewFragment WebViewFragment;
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
    MeMyIdentityinfoFragmentPresenter identityinfoFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myaccount_identityinfo, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        tv_title.setText("实名认证");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        identityinfoFragmentPresenter = new MeMyIdentityinfoFragmentPresenter(this);
        //设置数据
        setData();

        //设置监听
        setListener();
    }

    private String name = "";
    private String idcard = "";
//    private String phone = "";

    /**
     * 事件响应方法,刷新实名认证
     */
    @Subscribe
    public void onEvent(IUpdataInfoCallBack event) {

        if (("Success").equals(event.getUpdataInfo())) {
            Logger.i("MeMyAccountIdentityInfoFragment==" + "Success" + "event.getUpdataInfo()==" + event.getUpdataInfo());
            T.D("认证成功");
            remove("MeMyAccountIdentityInfoFragment");
        } else if (("Refresh").equals(event.getUpdataInfo())) {
            Logger.i("MeMyAccountIdentityInfoFragment==" + "Refresh" + "event.getUpdataInfo()==" + event.getUpdataInfo());
            initDataIsRealName();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new IUpdataInfoCallBack(""));
    }

    private void setListener() {

        identityinfo_et_real_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                identityinfo_tv_real_name.setVisibility(View.INVISIBLE);
                identityinfo_tv_card_num.setVisibility(View.INVISIBLE);
                identityinfo_tv_bind_phone.setVisibility(View.INVISIBLE);
                name = identityinfo_et_real_name.getText().toString().trim();
                idcard = identityinfo_et_card_num.getText().toString().trim();
//                phone = identityinfo_et_bind_phone.getText().toString().trim();
                if (!Q.isEmpty(name) && !Q.isEmpty(idcard)) {// && !Q.isEmpty(phone)
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    identityinfo_bt_submit_info.setClickable(true);
                } else {
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    identityinfo_bt_submit_info.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        identityinfo_et_card_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                identityinfo_tv_real_name.setVisibility(View.INVISIBLE);
                identityinfo_tv_card_num.setVisibility(View.INVISIBLE);
                identityinfo_tv_bind_phone.setVisibility(View.INVISIBLE);
                name = identityinfo_et_real_name.getText().toString().trim();
                idcard = identityinfo_et_card_num.getText().toString().trim();
//                phone = identityinfo_et_bind_phone.getText().toString().trim();
                if (!Q.isEmpty(name) && !Q.isEmpty(idcard)) {// && !Q.isEmpty(phone)
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    identityinfo_bt_submit_info.setClickable(true);
                } else {
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    identityinfo_bt_submit_info.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*identityinfo_et_bind_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                identityinfo_tv_real_name.setVisibility(View.INVISIBLE);
                identityinfo_tv_card_num.setVisibility(View.INVISIBLE);
                identityinfo_tv_bind_phone.setVisibility(View.INVISIBLE);
                name = identityinfo_et_real_name.getText().toString().trim();
                idcard = identityinfo_et_card_num.getText().toString().trim();
                phone = identityinfo_et_bind_phone.getText().toString().trim();
                if (!Q.isEmpty(name) && !Q.isEmpty(idcard) && !Q.isEmpty(phone)) {
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    identityinfo_bt_submit_info.setClickable(true);
                } else {
                    identityinfo_bt_submit_info.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    identityinfo_bt_submit_info.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    /**
     * 请求数据(实名认证)
     */
    private void initDataIsRealName() {
        P.show();
        P.setCancelable(false);

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        final String strn = gson.toJson(map);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                identityinfoFragmentPresenter.loadIsRealName(strn);
            }
        },5000);

    }

    /**
     * 请求数据Identity
     */
    private void initData() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("realName", identityinfo_et_real_name.getText().toString().trim());
        map.put("idCard", identityinfo_et_card_num.getText().toString().trim());
        map.put("phoneNo", userModel.getmobileNumber() + "");//identityinfo_et_bind_phone.getText().toString().trim()
        map.put("email", "");

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        identityinfoFragmentPresenter.loadIdentity(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        P.cancel();
        if (model.getClass().equals(IsRealNameBaseModel.class)) {
            IsRealNameBaseModel realNameBaseModel = (IsRealNameBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(realNameBaseModel.status)) {
                userModel.setidCard(realNameBaseModel.result.idCard);
                userModel.setrealname(realNameBaseModel.result.realname);
                userModel.setmobileNumber(realNameBaseModel.result.mobileNumber);
                userModel.setauth(realNameBaseModel.result.auth);
                setData();
            } else if (Constant.STATUS_FAILED.equals(realNameBaseModel.status)) {

            }
        } else if (model.getClass().equals(IdentityBaseModel.class)) {

            IdentityBaseModel identityBaseModel = (IdentityBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {
                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
                Constant.actionUrl = identityBaseModel.result.actionUrl;
                isIntercept = identityBaseModel.result.isIntercept;
                callbackUrl = identityBaseModel.result.callbackUrl;

                WebViewFragment();
            } else if (Constant.STATUS_FAILED.equals(identityBaseModel.status)) {

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
     * 设置数据
     */
    private void setData() {
        String realname = userModel.getrealname();
        String idCard = userModel.getidCard();

        if (("1").equals(userModel.getauth())) {
            if (!Q.isEmpty(realname)) {
                identityinfo_et_real_name.setHint(realname.substring(0, 1) + "**");
            } else {
                identityinfo_et_real_name.setHint("");
            }

            if (!Q.isEmpty(idCard)){
                if (idCard.length() >=4){
                    identityinfo_et_card_num.setHint("****" + idCard.substring(idCard.length() - 4, idCard.length()));
                }else {
                    identityinfo_et_card_num.setHint("****" + idCard);
                }

            }else {
                identityinfo_et_card_num.setHint("");
            }

            identityinfo_et_real_name.setFocusable(false);
            identityinfo_et_card_num.setFocusable(false);
            identityinfo_tv_real_type.setText("已认证");
            identityinfo_tv_real_type.setBackgroundResource(R.drawable.shape_layout_circle_identity);
//            identityinfo_et_bind_phone.setVisibility(View.GONE);
            identityinfo_bt_submit_info.setVisibility(View.GONE);
        } else {
            identityinfo_et_real_name.setFocusable(true);
            identityinfo_et_card_num.setFocusable(true);
            identityinfo_tv_real_type.setText("未认证");
            identityinfo_tv_real_type.setBackgroundResource(R.drawable.shape_layout_circle_identityno);
//            identityinfo_et_bind_phone.setVisibility(View.VISIBLE);
            identityinfo_bt_submit_info.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.Btn_left, R.id.identityinfo_bt_submit_info})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回

                remove("MeMyAccountIdentityInfoFragment");
                break;
            case R.id.identityinfo_bt_submit_info://提交信息

                name = identityinfo_et_real_name.getText().toString().trim();
                idcard = identityinfo_et_card_num.getText().toString().trim();
//                phone = identityinfo_et_bind_phone.getText().toString().trim();

                //验证姓名汉字,身份证格式,及手机号
                if (!Q.isAllChinese(name)) {
                    identityinfo_tv_real_name.setVisibility(View.VISIBLE);
                    return;
                }
                if (!X.isIDCardVerify(idcard)) {
                    identityinfo_tv_card_num.setVisibility(View.VISIBLE);
                    return;
                }
               /* if (!X.isMobilePhoneVerify(phone)) {
                    identityinfo_tv_bind_phone.setVisibility(View.VISIBLE);
                    return;
                }*/
                //请求数据
                initData();
                break;
            default:
                break;
        }
    }

    /**
     * 易宝实名认证跳转
     */
    public void WebViewFragment() {

//        if (WebViewFragment == null) {
//        WebViewFragment = new WebViewFragment();
//        YeePayModel payModel = new YeePayModel();
//        payModel.title = "实名认证";
//        payModel.url = url;
//        payModel.isIntercept = isIntercept;
//        payModel.callbackUrl = callbackUrl;
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("YeePayModel", payModel);
//        WebViewFragment.setArguments(bundle);
//        }
//        replace(WebViewFragment);
//        add(R.id.fragment_myaccount, WebViewFragment, "WebViewFragment", null);


        YeePayModel payModel = new YeePayModel();
        payModel.title = "实名认证";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));

    }

    /*public void replace(BaseFragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fr_main_me, fragment).addToBackStack(null).commit();
    }*/

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "实名认证");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "实名认证");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
