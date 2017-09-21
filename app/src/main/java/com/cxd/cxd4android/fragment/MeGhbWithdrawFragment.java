package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.GhbJsInterface;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataWithdrawCallBack;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbWithdrawModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeWithdrawFragmentPresenter;
import com.google.gson.Gson;
import com.cxd.cxd4android.widget.X5BaseWebview;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cxd.cxd4android.model.GhbGlobalDataModel.withdraw_tip;
import static com.cxd.cxd4android.model.GhbGlobalDataModel.withdraw_tip_title;


/**
 * Created by moon.zhong on 2016/11/10.
 * 华兴提现
 */
public class MeGhbWithdrawFragment extends BaseFragment implements LoadingView {

    /**
     * 提现正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 提现左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;

    /**
     * 提现可用余额
     **/
    @Bind(R.id.ghbwithdraw_tv_available_balance)
    TextView ghbwithdraw_tv_available_balance;
    /**
     * 提现金额
     **/
    @Bind(R.id.ghbwithdraw_et_withdraw_money)
    EditText ghbwithdraw_et_withdraw_money;
    /**
     * 提现余额不足
     **/
    @Bind(R.id.ghbwithdraw_tv_no_balance)
    TextView ghbwithdraw_tv_no_balance;
    /**
     * 华兴提示信息
     **/
    @Bind(R.id.ghbwithdraw_dg_warm_prompt)
    X5BaseWebview ghbwithdraw_dg_warm_prompt;

    /**
     * 用户信息
     */
    LocalUserModel userModel;
    /**
     * 可用余额
     */
    private double balance;
    /**
     * 充值钱数
     */
    private String money = "";

    public String withdraw_money = "";

    MeWithdrawFragmentPresenter withdrawFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_ghbwithdraw, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        tv_title.setText("华兴提现");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        withdrawFragmentPresenter = new MeWithdrawFragmentPresenter(this);
        balance = Double.parseDouble(userModel.getbalanceAvaliable());

        //设置数据
        setData();

        //设置监听
        setListener();

        //华兴提现提示信息
        initGuidePage();
    }

    /**
     * 事件响应方法,易宝提现返回刷新
     */
    @Subscribe
    public void onEvent(IUpdataWithdrawCallBack event) {
        Logger.i("MeGhbWithdrawFragment==" + "" + "event.getUpdataInfo()==" + event.getUpdataWithdraw());
        initDataAccount();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new IUpdataWithdrawCallBack("withdraw"));
    }

    private Handler handler = new Handler();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {

            //在这里调用服务器的接口，获取数据
            if (!Q.isEmpty(withdraw_money) && !("0.00").equals(withdraw_money)) {
                String str = withdraw_money;

                if (str.length() == 1 && (".").equals(str)) {
                    return;
                }

                //大于余额
                if (Double.parseDouble(withdraw_money) > Double.parseDouble(userModel.getbalanceAvaliable())) {
                    ghbwithdraw_tv_no_balance.setVisibility(View.VISIBLE);
                    ghbwithdraw_et_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_red);
                } else {
                    ghbwithdraw_tv_no_balance.setVisibility(View.INVISIBLE);
                    ghbwithdraw_et_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_withe);
                }
            } else {
                ghbwithdraw_tv_no_balance.setVisibility(View.INVISIBLE);
                ghbwithdraw_et_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_withe);
            }
        }
    };

    /**
     * 设置监听
     */
    private void setListener() {

        ghbwithdraw_et_withdraw_money.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }


                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 800);

                //限制两位
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                    return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }


                withdraw_money = s.toString();
            }
        });

    }

    /**
     * 设置数据
     */
    private void setData() {
        ghbwithdraw_tv_available_balance.setText(userModel.getbalanceAvaliable());
    }

    /**
     * 请求数据(账户信息)
     */
    private void initDataAccount() {
        if (!Q.isEmpty(userModel.getid())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            withdrawFragmentPresenter.loadDataAccount(strn);
        }

    }

    /**
     * 华兴银行(提现)
     */
    private void initGhbWithdraw() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("money", money);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        withdrawFragmentPresenter.loadGhbWithdraw(strn);

    }

    /**
     * 请求数据(华兴提示信息)
     */
    private void initGuidePage() {
        Gson gson = new Gson();
        String strn = gson.toJson("");

        withdrawFragmentPresenter.loadGhbGuidePage(strn);
    }

    @OnClick({R.id.Btn_left, R.id.ghbwithdraw_bt_submit_next})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
                break;
            case R.id.ghbwithdraw_bt_submit_next://下一步
                money = ghbwithdraw_et_withdraw_money.getText().toString().trim();

                //不能为空
                if (Q.isEmpty(money) || ("0.00").equals(money)) {
                    T.D("请输入金额");
                    return;
                }

                //不能一个点
                if (money.length() == 1 && (".").equals(money)) {
                    T.D("请输入金额");
                    return;
                }

                //大于余额
                if (Double.parseDouble(money) > Double.parseDouble(userModel.getbalanceAvaliable())) {
                    ghbwithdraw_tv_no_balance.setVisibility(View.VISIBLE);
                    ghbwithdraw_et_withdraw_money.setBackgroundResource(R.drawable.shape_layout_circle_red);
                    return;
                }
                //大于等于3元
                if (Double.parseDouble(money) <= 0) {
                    T.D("提现金额必须大于0元");
                    return;
                }

                //Todo 待完善华兴&易宝 请求数据
                initGhbWithdraw();//华兴提现

                StatService.onEvent(getContext(), BaiDustatistic.me_widthraw_next, "", 1);//事件统计
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(AccountBalanceBaseModel.class)) { //账户数据信息
            AccountBalanceBaseModel balanceBaseModel = (AccountBalanceBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(balanceBaseModel.status)) {

                userModel.setbalanceAvaliable(balanceBaseModel.result.balanceAvaliable);
                setData();
            } else if (Constant.STATUS_FAILED.equals(balanceBaseModel.status)) {

            }

        } else if (model.getClass().equals(GhbWithdrawModel.class)) { //华兴提现
            P.cancel();
            GhbWithdrawModel ghbWithdrawModel = (GhbWithdrawModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbWithdrawModel.getStatus())) {
                //完善 华兴提现功能
                new GhbGlobalData(getActivity()).setGhbWithdrawData(ghbWithdrawModel);
                remove("MeGhbWithdrawFragment");
                getActivity().finish(); //关闭当前页

            } else if (Constant.STATUS_FAILED.equals(ghbWithdrawModel.getStatus())) {
                T.D(ghbWithdrawModel.getMsg() + "");
            }
        } else if (model.getClass().equals(GuideModel.class)) { //华兴提示信息

            GuideModel guideModel = (GuideModel) model;
            if (Constant.STATUS_SUCCESS.equals(guideModel.getStatus())) {
                GuideModel.InResult result = guideModel.getResult();
                String tipWithdrawUrl = guideModel.getResult().getApp_tip_withdraw_url();
                withdraw_tip_title = result.getWithdraw_tip_title();
                withdraw_tip = result.getWithdraw_tip();

                ghbwithdraw_dg_warm_prompt.loadUrl(tipWithdrawUrl);
                ghbwithdraw_dg_warm_prompt.addJavascriptInterface(new GhbJsInterface(getActivity()), "withdrawGuide");
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

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "华兴提现页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "华兴提现页面");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
