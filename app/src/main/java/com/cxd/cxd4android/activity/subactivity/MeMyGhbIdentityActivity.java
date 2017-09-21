package com.cxd.cxd4android.activity.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.WebViewFragment;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataGhbUserExt;
import com.cxd.cxd4android.model.GhbH5Model;
import com.cxd.cxd4android.model.GhbIdentityModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyGhbAccountPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.micros.utils.X;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by moon.zhong on 2015/2/4.
 * hua 华兴实名认证
 */
public class MeMyGhbIdentityActivity extends BaseActivity implements LoadingView {

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
     * 华兴实名认证
     **/
    private String weburl = "";
    /**
     * 华兴是否回调
     **/
    private String isIntercept = "";
    /**
     * 华兴回调链接
     **/
    private String callbackUrl = "";
    MeMyGhbAccountPresenter meMyGhbAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me_ghbaccount_identity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        userModel = new LocalUserModel();
        meMyGhbAccountPresenter = new MeMyGhbAccountPresenter(this);

        tv_title.setText("华兴银行开户");
        Btn_left.setVisibility(View.VISIBLE);

        //设置数据
        setData();
        //设置监听
        setListener();
    }

    private String name = "";
    private String idcard = "";

    /**
     * 事件响应方法,刷新实名认证
     */
    @Subscribe
    public void onEvent(IUpdataGhbUserExt event) {

        if ("realnameAuth".equals(event.getUpdataGhbUserExt())) {
            //请求UserInfo用户数据,得到是否实名认证,ghb_realname_auth
            initloadUserInfo();

            //实名认证成功后,返回E账户信息,绑卡状态信息 身份证号
            initGhbUserExt();
            //finish();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(new IUpdataGhbUserExt("callbackRealname"));
        Log.i("GhbJsInterface->", "GhbUserExtModel-回调实名-onDestroy");

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
    }

    /**
     * 请求数据(华兴实名认证)
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
     * 请求E账户数据和回调绑卡状态查询
     */
    private void initGhbUserExt() {
        P.show();
        //P.setCancelable(false);

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        final String strn = gson.toJson(map);

        Observable.just(strn)
                .delay(3, TimeUnit.SECONDS) //单位:秒
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String strJson) {
                        meMyGhbAccountPresenter.loadGhbUserExt(strJson);
                    }
                });

    }

    /**
     * 请求数据Identity
     */
    private void initData() {
        P.show();

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("realName", name);
        map.put("idCard", idcard);
        map.put("phoneNo", userModel.getmobileNumber() + "");//identityinfo_et_bind_phone.getText().toString().trim()
        map.put("email", "");

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        meMyGhbAccountPresenter.loadGhbIdentity(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(UserInfoModel.class)) {

            UserInfoModel userInfoModel = (UserInfoModel) model;
            if (Constant.STATUS_SUCCESS.equals(userInfoModel.getStatus())) {
                UserInfoModel.ResultBean result = userInfoModel.getResult();
                userModel.setGhbPayAuth(result.getGhb_realname_auth());
                Log.i("GhbJsInterface->", "userInfoModel-回调userinfo-" + result.getGhb_realname_auth());
            }
        } else if (model.getClass().equals(GhbUserExtModel.class)) {

            GhbUserExtModel ghbUserExtModel = (GhbUserExtModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbUserExtModel.getStatus())) {
                GhbUserExtModel.UserExtResult result = ghbUserExtModel.getResult();
                userModel.saveGhbEAccount(ghbUserExtModel);//保存数据
                Log.i("GhbJsInterface->", "GhbUserExtModel-回调实名-" + result.getGhbAccount());
                setData();
                finish();//

            }
        } else if (model.getClass().equals(GhbIdentityModel.class)) {
            P.cancel();

            GhbIdentityModel ghbIdentityModel = (GhbIdentityModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbIdentityModel.getStatus())) {
//                weburl = ghbIdentityModel.getResult().getHtml();
//                WebViewFragment();
                new GhbGlobalData(this).setGhbRlNmAtWeb(ghbIdentityModel);

            } else if (Constant.STATUS_FAILED.equals(ghbIdentityModel.getResult())) {

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
        String realname = userModel.getGhbEUserName();
        String idCard = userModel.getEIdCard();
        Log.i("GhbJsInterface->", "GhbUserExtModel-显示数据-" + userModel.getGhbAuth() + "--" + realname);

        if (("CHECKED").equals(userModel.getGhbAuth())) {
            T.D("开户成功");
            if (!Q.isEmpty(realname)) {
                identityinfo_et_real_name.setHint(realname.substring(0, 1) + "**");
            } else {
                identityinfo_et_real_name.setHint("");
            }

            if (!Q.isEmpty(idCard)) {
//                if (idCard.length() >= 4) {
//                    identityinfo_et_card_num.setHint("****" + idCard.substring(idCard.length() - 4, idCard.length()));
//                } else {
//                    identityinfo_et_card_num.setHint("****" + idCard);
//                }
                identityinfo_et_card_num.setHint(idCard);

            } else {
                identityinfo_et_card_num.setHint("");
            }

            identityinfo_et_real_name.setFocusable(false);
            identityinfo_et_card_num.setFocusable(false);
            identityinfo_bt_submit_info.setVisibility(View.GONE);
        } else {
            identityinfo_et_real_name.setFocusable(true);
            identityinfo_et_card_num.setFocusable(true);
            identityinfo_bt_submit_info.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.Btn_left, R.id.identityinfo_bt_submit_info})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回

                finish();
                break;
            case R.id.identityinfo_bt_submit_info://立即开户

                name = identityinfo_et_real_name.getText().toString().trim();
                idcard = identityinfo_et_card_num.getText().toString().trim();

                //验证姓名汉字,身份证格式,及手机号
                if (!Q.isAllChinese(name)) {
                    identityinfo_tv_real_name.setVisibility(View.VISIBLE);
                    return;
                }
                if (!X.isIDCardVerify(idcard)) {
                    identityinfo_tv_card_num.setVisibility(View.VISIBLE);
                    return;
                }
                //请求数据
                initData();
                break;
            default:
                break;
        }
    }

    /**
     * 华兴实名认证跳转
     */
    public void WebViewFragment() {

        GhbH5Model ghbH5Model = new GhbH5Model();
        ghbH5Model.setTitle("华兴银行开户");
        ghbH5Model.setWebUrl(weburl);
        ghbH5Model.setType("ghbh5");

        startActivity(new Intent(this, GhbH5Activity.class).putExtra("h5_type", ghbH5Model));

    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(this, "华兴银行开户");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(this, "华兴银行开户");//(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
