package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.activity.subactivity.HXAccountActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyBillActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyBonusPointsActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyInvesActivity;
import com.cxd.cxd4android.activity.subactivity.MeMyRecomActivity;
import com.cxd.cxd4android.activity.subactivity.MeRechargeActivity;
import com.cxd.cxd4android.activity.subactivity.MeWithdrawActivity;
import com.cxd.cxd4android.activity.subactivity.ReturnMoneyCalendarActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IBuyProductMakeMoneyCallBack;
import com.cxd.cxd4android.interfaces.IExitLoginCallBack;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.interfaces.IRepestLoginCallBack;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.interfaces.IUpdataRechargeCallBack;
import com.cxd.cxd4android.interfaces.IUpdataWithdrawCallBack;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.AccountBalanceModel;
import com.cxd.cxd4android.model.AppMypointModel;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMainFragmentPresenter;
import com.cxd.cxd4android.presenter.MeMyGhbAccountPresenter;
import com.cxd.cxd4android.presenter.SplashPresenter;
import com.cxd.cxd4android.shbbank.accountmanagement.AccountManagementActivity;
import com.cxd.cxd4android.shbbank.dialog.SHBRenewalBankDialog;
import com.cxd.cxd4android.shbbank.html.SHBHTML;
import com.cxd.cxd4android.shbbank.html.WebViewActivity;
import com.cxd.cxd4android.shbbank.interfaces.SHBBuyMakeMoneyCallBack;
import com.cxd.cxd4android.shbbank.model.SHBActivationAModel;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.shbbank.openaccount.SHBOpenAccountActivity;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.cxd.cxd4android.widget.share.ShareMall;
import com.cxd.cxd4android.yeepay.YeePayAccountActivity;
import com.google.gson.Gson;
import com.micros.utils.Q;
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
public class MeMainFragment extends BaseFragment implements LoadingView {

    /**
     * 我正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;

    /**
     * 我累计收益
     **/
    @Bind(R.id.me_tv_cumulative_gain)
    TextView me_tv_cumulative_gain;
    /**
     * 我资产总额
     **/
    @Bind(R.id.me_tv_total_assets)
    TextView me_tv_total_assets;
    /**
     * 我可用余额
     **/
    @Bind(R.id.me_tv_available_balance)
    TextView me_tv_available_balance;
    /**
     * 我冻结资金
     **/
    @Bind(R.id.me_tv_frozen_funds)
    TextView me_tv_frozen_funds;
    /**
     * 我待收金额
     **/
    @Bind(R.id.me_tv_amount_received)
    TextView me_tv_amount_received;

    /**
     * 用户信息
     */
    LocalUserModel userModel;

    /**
     * 我的刷新
     **/
    @Bind(R.id.memain_sr_swipe_refresh)
    SwipeRefreshLayout memain_sr_swipe_refresh;
    /**
     * 我的投资图片
     **/
    @Bind(R.id.me_re_my_investment_img)
    ImageView me_re_my_investment_img;
    /**
     * 我的转让图片
     **/
//    @Bind(R.id.me_re_my_transfer_img)
//    ImageView me_re_my_transfer_img;
    /**
     * 我的账单图片
     **/
    @Bind(R.id.me_re_my_bill_img)
    ImageView me_re_my_bill_img;
    /**
     * 我的账户图片
     **/
    @Bind(R.id.me_re_my_account_img)
    ImageView me_re_my_account_img;
    /**
     * 邀请好友图片
     **/
    @Bind(R.id.me_re_my_recom_img)
    ImageView me_re_my_recom_img;
    /**
     * 积分商城图片
     **/
    @Bind(R.id.me_ll_bouns_points_img)
    ImageView me_ll_bouns_points_img;
    /**
     * 汇款日历图片
     **/
    @Bind(R.id.me_ll_money_calendar_img)
    ImageView me_ll_money_calendar_img;
    /**
     * RadioGroup-华兴易宝
     **/
    @Bind(R.id.radio_hx_yb)
    RadioGroup radio_hx_yb;
    /**
     * RadioButton-华兴
     **/
    @Bind(R.id.me_huaxing)
    RadioButton rb_huaxing;

    /**
     * RadioButton-易宝
     **/
    @Bind(R.id.me_yibao)
    RadioButton rb_yibao;

    /**
     * 我的投资(华兴投资或易宝投资)
     **/
    @Bind(R.id.me_re_my_investment_tv)
    TextView me_re_my_investment_tv;
    /**
     * 我的账单(华兴账单或易宝账单)
     **/
    @Bind(R.id.me_re_my_bill_tv)
    TextView me_re_my_bill_tv;
    /**
     * 我的账户(华兴账户或易宝账户)
     **/
    @Bind(R.id.me_re_my_account_tv)
    TextView me_re_my_account_tv;
    /**
     * 我充值(华兴充值或易宝充值)
     **/
    @Bind(R.id.me_bt_recharge_money)
    Button me_bt_recharge_money;
    /**
     * 我提现(华兴提现或易宝提现)
     **/
    @Bind(R.id.me_bt_withdrawals_money)
    Button me_bt_withdrawals_money;
    /**
     * 我的红包
     **/
    @Bind(R.id.me_ll_red_wrap_img)
    ImageView me_ll_red_wrap_img;
    /**
     * 我的指引
     **/
    @Bind(R.id.me_ll_guide_img)
    ImageView me_ll_guide_img;

    /**
     * 易宝账户
     **/
    @Bind(R.id.tv_right)
    TextView tv_right;


    /**
     * 顶部累计收益布局
     **/
    @Bind(R.id.include_layout_me_viewtop)
    LinearLayout include_layout_me_viewtop;
    /**
     * 中间账户余额布局
     **/
    @Bind(R.id.include_layout_me_viewmiddle)
    LinearLayout include_layout_me_viewmiddle;

    /**
     * 未开通上海银行布局
     **/
    @Bind(R.id.include_layout_me_opennow)
    LinearLayout include_layout_me_opennow;

    /**
     * 未开通上海银行已开通华兴银行布局
     **/
    @Bind(R.id.me_ll_shb_opennow)
    LinearLayout me_ll_shb_opennow;
    /**
     * 已开通上海银行已开通华兴银行布局
     **/
    @Bind(R.id.me_ll_shb_opend)
    LinearLayout me_ll_shb_opend;

    /**
     * 未开通上海银行立即开通顶部
     **/
    @Bind(R.id.me_btn_shb_opennow_top)
    Button me_btn_shb_opennow_top;
    /**
     * 未开通上海银行立即开通底部
     **/
    @Bind(R.id.me_btn_shb_opennow_middle)
    Button me_btn_shb_opennow_middle;


//    private boolean isShow = false;
//    public boolean isShowDialog = true;


    MeMainFragmentPresenter meMainFragmentPresenter;

    private String myPointPicUrl;
    private String myPointTitle;

    Drawable drawable;
    Drawable drawable2;
    private GhbGlobalData ghbGlobalData;
    private int times = 0;
    private MeMyGhbAccountPresenter meMyGhbAccountPresenter;
    private GuideModel guideModel;
    private String strn;
    private SHBPresenter mShbPresenter;
    SplashPresenter splashPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_main, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("我");
        userModel = new LocalUserModel();
        ghbGlobalData = new GhbGlobalData(getActivity());

        splashPresenter = new SplashPresenter(this);
        //新增请求数据
        meMyGhbAccountPresenter = new MeMyGhbAccountPresenter(this);
        meMainFragmentPresenter = new MeMainFragmentPresenter(this);
        mShbPresenter = new SHBPresenter(this);


        EventBus.getDefault().register(this);

        startRefresh(memain_sr_swipe_refresh);//开启刷新

        setListener();//设置监听

        new ShareMall().getInstance();//進入积分商城页面，首先获取要分享的内容链接

        //加载数据
        initLoadData();

        //初始化页面
        initView();

    }


    boolean show = false;

    private void setView() {

        // 华兴已认证,上海未认证--显示切换栏
        if ("CHECKED".equals(userModel.getGhbAuth())) {//老用户


            if ("Y".equals(userModel.getActive()) && "CHECKED".equals(userModel.getSHBAuth())) {
                me_ll_shb_opennow.setVisibility(View.GONE);
                me_ll_shb_opend.setVisibility(View.VISIBLE);
            } else {
                if (rb_yibao.isChecked()){
                    return;
                }
                me_ll_shb_opennow.setVisibility(View.VISIBLE);
                me_ll_shb_opend.setVisibility(View.GONE);
                renewalSHBDialog(show);
                show = true;
            }
        } else {//新用户
            if ("Y".equals(userModel.getActive()) && "CHECKED".equals(userModel.getSHBAuth())) {
                // 华兴未认证,上海未认证--都不显示切换栏
                radio_hx_yb.setVisibility(View.GONE);
                include_layout_me_viewtop.setVisibility(View.VISIBLE);
                include_layout_me_viewmiddle.setVisibility(View.VISIBLE);
                include_layout_me_opennow.setVisibility(View.GONE);
            } else {
                include_layout_me_viewtop.setVisibility(View.GONE);
                include_layout_me_viewmiddle.setVisibility(View.GONE);
                include_layout_me_opennow.setVisibility(View.VISIBLE);

                openSHBDialog(show);
                show = true;
            }
        }
    }

    private void initView() {

        //判断 CHECKED:已认证,NOT:未认证
        if ("CHECKED".equals(userModel.getYeepayAuth())) {
            tv_right.setText("易宝账户");
            tv_right.setVisibility(View.VISIBLE);
        }

        // 华兴已认证,上海未认证--显示切换栏
        if ("CHECKED".equals(userModel.getGhbAuth())) {//老用户

            if ("Y".equals(userModel.getActive()) && "CHECKED".equals(userModel.getSHBAuth())) {
                me_ll_shb_opennow.setVisibility(View.GONE);
                me_ll_shb_opend.setVisibility(View.VISIBLE);

            } else {
                me_ll_shb_opennow.setVisibility(View.VISIBLE);
                me_ll_shb_opend.setVisibility(View.GONE);

            }

            rb_huaxing.setChecked(true);

            userModel.setThirdPayType(Constant.ACCOUNT_SHB);//保存状态类型为 上海

            //修改按钮文字
            ChangeText();

            //华兴-易宝 切换事件
            setOnAccountClick();
        } else {//新用户

            if ("Y".equals(userModel.getActive()) && "CHECKED".equals(userModel.getSHBAuth())) {
                // 华兴未认证,上海未认证--都不显示切换栏
                radio_hx_yb.setVisibility(View.GONE);
                include_layout_me_viewtop.setVisibility(View.VISIBLE);
                include_layout_me_viewmiddle.setVisibility(View.VISIBLE);
                include_layout_me_opennow.setVisibility(View.GONE);
            } else {
                include_layout_me_viewtop.setVisibility(View.GONE);
                include_layout_me_viewmiddle.setVisibility(View.GONE);
                include_layout_me_opennow.setVisibility(View.VISIBLE);

            }

            userModel.setThirdPayType(Constant.ACCOUNT_SHB);//保存状态类型为 上海

            //修改按钮文字
            ChangeText();

        }


    }


    //上海银行对话框，立即激活
    private void renewalSHBDialog(boolean show) {
        if (show) {
            return;
        }
        final SHBRenewalBankDialog dialog = new SHBRenewalBankDialog(getActivity());
        dialog.mShb_dia_tv_explain.setText("诚信贷已更换存管银行");

        dialog.mShbDialogText.setText("为了方便您继续投资\n" + "请先激活上海存管银行");
        dialog.mShbDialogBtn.setText("立即激活");
        dialog.mShbDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if ("Y".equals(userModel.getActive())) {//开户
                    startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
                } else if ("N".equals(userModel.getActive())) {//激活
                    SHBUserActivate();
                }
            }
        });
        dialog.show();
    }

    //上海银行对话框，立即开通
    private void openSHBDialog(boolean show) {
        if (show) {
            return;
        }
        final SHBRenewalBankDialog dialog = new SHBRenewalBankDialog(getActivity());
        dialog.mShb_dia_tv_explain.setText("上海银行存管系统正式上线");
        dialog.mShbDialogText.setText("为了保障您的资金安全\n" + "请先开通存管账户");
        dialog.mShbDialogBtn.setText("立即开通");
        dialog.mShbDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
            }
        });
        dialog.show();
    }

    private void initGuidInfo() {
        Gson gson = new Gson();
        strn = gson.toJson("");

        meMyGhbAccountPresenter.loadGhbGuidePage(strn);
    }

    /**
     * 华兴-易宝账户切换
     **/
    private void setOnAccountClick() {
        drawable = getResources().getDrawable(R.drawable.trans_line);
        drawable2 = getResources().getDrawable(R.color.white);

        if (rb_huaxing.isChecked()) {

            rb_huaxing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            rb_yibao.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable2);
        } else if (rb_yibao.isChecked()) {

            rb_yibao.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            rb_huaxing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable2);
        }
        radio_hx_yb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.me_huaxing:
                        if ("Y".equals(userModel.getActive()) && "CHECKED".equals(userModel.getSHBAuth())) {
                            me_ll_shb_opennow.setVisibility(View.GONE);
                            me_ll_shb_opend.setVisibility(View.VISIBLE);

                        } else {

                            me_ll_shb_opennow.setVisibility(View.VISIBLE);
                            me_ll_shb_opend.setVisibility(View.GONE);
                        }
                        userModel.setThirdPayType(Constant.ACCOUNT_SHB);//保存状态类型为 上海

                        if (rb_huaxing.isChecked()) {
                            rb_huaxing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                            rb_yibao.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable2);
                        }

                        //TODO 请求华兴账户数据(账户资产)
                        ChangeText();

                        initAccountData();

                        break;
                    case R.id.me_yibao:
                        me_ll_shb_opennow.setVisibility(View.GONE);
                        me_ll_shb_opend.setVisibility(View.VISIBLE);

                        userModel.setThirdPayType(Constant.ACCOUNT_GHB);//保存状态类型为 华兴
                        if (rb_yibao.isChecked()) {
                            rb_yibao.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                            rb_huaxing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable2);
                        }

                        //TODO 请求易宝账户数据(账户资产)
                        ChangeText();
                        initAccountData();
                        initGhbUserExt();//获取绑卡状态
                        break;
                }
            }
        });
    }

    /**
     * 根据标签选中状态切换按钮文字
     */
    private void ChangeText() {
        if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
            me_re_my_investment_tv.setText("华兴投资");
            me_re_my_bill_tv.setText("华兴账单");
            me_re_my_account_tv.setText("华兴账户");
            me_bt_recharge_money.setText("华兴充值");
            me_bt_withdrawals_money.setText("华兴提现");
            me_bt_recharge_money.setVisibility(View.VISIBLE);
            me_bt_withdrawals_money.setVisibility(View.VISIBLE);

        } else if (Constant.ACCOUNT_SHB.equals(userModel.getThirdPayType())) {
            me_re_my_investment_tv.setText("投资明细");
            me_re_my_bill_tv.setText("账单记录");
            me_re_my_account_tv.setText("账户管理");
            me_bt_recharge_money.setText("充值");
            me_bt_withdrawals_money.setText("提现");
            me_bt_recharge_money.setVisibility(View.VISIBLE);
            me_bt_withdrawals_money.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 请求所有数据
     */
    private void initLoadData() {

        loadIconView();//加载图片

        initGuidInfo();//新增加载图文请求

        iupLoadData();

        initDataIsRealName(); //请求数据(是否实名认证)

        initDataBankCard(); //请求数据(银行卡状态)

        initPointH5();  //请求积分商城h5数据


    }


    /**
     * 请求刷新数据
     */
    private void iupLoadData() {
        initAccountData();  //请求账户金额(账户信息)

        initUserInfo();//请求UserInfo数据

    }


    /**
     * 请求账户金额数据(账户信息)
     */
    private void initAccountData() {
        if (!Q.isEmpty(userModel.getid())) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            meMainFragmentPresenter.loadAccountData(strn);
        }

    }

    /**
     * 请求数据(是否实名认证)
     */
    private void initDataIsRealName() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadIsRealName(strn);
    }

    /**
     * 请求数据(银行卡状态)
     */
    private void initDataBankCard() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadBankCard(strn);
    }

    //请求积分商城h5数据
    private void initPointH5() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadMyPointH5(strn);
    }

    //请求华兴E账户数据 和 回调绑卡状态查询
    private void initGhbUserExt() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        meMainFragmentPresenter.loadGhbUserExt(strn); //请求华兴E账户数据 和 回调绑卡状态查询
    }

    //请求数据(userinfo)
    private void initUserInfo() {
        if (!Q.isEmpty(userModel.getid())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            meMainFragmentPresenter.loadUserInfo(strn); //请求数据(userinfo)
        }

    }

    //    上海用户激活
    private void SHBUserActivate() {
        P.show();

        mShbPresenter.activateStockedUser(userModel.getid());

    }


    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private void doShowDialog(final String title, final String text) {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        doShowDialogThread(title, text);
                    }
                });

                Looper.loop();
            }
        };
        thread.start();
    }

    private void doShowDialogThread(final String title, final String text) {
        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(false);
        dialog.dialog_imageView.setVisibility(View.INVISIBLE);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EventBus.getDefault().post(new ILoginCallBack(3));//跳转我

                MeLoginFragment();
                //清除数据
                userModel.clear();

                Class aClass = BaseApplication.TopAct.getClass();
                Logger.i("class==" + aClass);
                if ("class com.cxd.cxd4android.activity.MainActivityTest".equals(aClass.toString())) {
                } else {
                    BaseApplication.TopAct.finish();
                }
                dialog.dismiss();
            }
        });
        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.dialog_imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        memain_sr_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //加载 账户信息，是否认证，银行卡状态三种数据
                iupLoadData();
                //進入积分商城页面，首先获取要分享的内容链接
                new ShareMall().getInstance();
            }
        });
    }

    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(IUpdataRechargeCallBack event) {
        Logger.i("MeMainFragment==" + "MeRechargeFragment" + "event.getUpdataInfo()==" + event.getUpdataRecharge());
        if ("recharge".equals(event.getUpdataRecharge()) || "WebViewFragment".equals(event.getUpdataRecharge())) {
            //请求数据(账户信息)
            initAccountData();
        }
    }

    /**
     * 事件响应方法
     */
    @Subscribe
    public void onEvent(IUpdataWithdrawCallBack event) {
        Logger.i("MeMainFragment==" + "MeWithdrawFragment" + "event.getUpdataInfo()==" + event.getUpdataWithdraw());
        if ("withdraw".equals(event.getUpdataWithdraw()) || "MeWithdrawFragment".equals(event.getUpdataWithdraw()) || "WebViewFragment".equals(event.getUpdataWithdraw())) {
            //请求数据(账户信息)
            initAccountData();
        }
    }

    /**
     * 事件响应方法,更新银行卡状态(直接跳转过去的,返回回来),更新实名认证状态(直接跳转过去的,返回回来)
     */
    @Subscribe
    public void onEvent(IUpdataInfoCallBack event) {

        if (("MeRechargeFragment").equals(event.getUpdataInfo()) || ("MeWithdrawFragment").equals(event.getUpdataInfo())) {
            Logger.i("MeMainFragment==" + "MeRechargeFragmentMeWithdrawFragment" + "event.getUpdataInfo()==" + event.getUpdataInfo());

            //请求数据(账户信息)
            initAccountData();
        } else {
            Logger.i("MeMainFragment==" + "" + "event.getUpdataInfo()==" + event.getUpdataInfo());

            //请求数据(账户信息，是否认证，银行卡状态三种数据)
            iupLoadData();
        }

    }

    /**
     * 购买页onDestroy事回调
     */

    @Subscribe
    public void onEvent(SHBBuyMakeMoneyCallBack event) {
        iupLoadData();

    }

    /**
     * 事件响应方法,重复登录
     */
    @Subscribe
    public void onEvent(IRepestLoginCallBack event) {
        Logger.i("温馨提示:::" + "onEvent");
        doShowDialog("温馨提示", event.getRestLoginCallBack());
    }

    /**
     * 事件响应方法,登录回调
     */
    @Subscribe
    public void onEvent(IExitLoginCallBack event) {
        MeLoginFragment();
    }

    /**
     * 事件响应方法,马上赚钱回来刷新我的账户
     */
    @Subscribe
    public void onEvent(IBuyProductMakeMoneyCallBack event) {
        //加载 账户信息，是否认证，银行卡状态三种数据
        iupLoadData();
    }


    // 打开下载列表页
    @OnClick({R.id.tv_right, R.id.me_btn_shb_opennow_top, R.id.me_btn_shb_opennow_middle, R.id.me_re_my_investment, R.id.me_re_my_bill, R.id.me_re_my_account, R.id.me_re_my_recom, R.id.me_ll_bouns_points, R.id.me_ll_money_calendar, R.id.me_bt_recharge_money, R.id.me_bt_withdrawals_money, R.id.me_ll_guide, R.id.me_ll_red_wrap})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(getActivity(), YeePayAccountActivity.class));
                break;
            case R.id.me_btn_shb_opennow_top:
                startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
                break;
            case R.id.me_btn_shb_opennow_middle:
                if ("Y".equals(userModel.getActive())) {//开户
                    startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
                } else if ("N".equals(userModel.getActive())) {//激活
                    SHBUserActivate();

                }
                break;
            case R.id.me_ll_guide://会员福利
                if (guideModel != null) {
                    if (guideModel.getResult().getApp_tip_operation_title() != null && guideModel.getResult().getApp_tip_operation_url() != null) {
                        ghbGlobalData.setRedGuild(guideModel);
                    }
                } else {
                    Toast.makeText(getContext(), "数据获取出错,请检查网络情况", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.me_ll_red_wrap:
                if (guideModel != null) {
                    if (guideModel.getResult().getApp_tip_lucklymoney_title() != null && guideModel.getResult().getApp_tip_lucklymoney_url() != null) {
                        ghbGlobalData.setRedWarp(guideModel);
                    }
                } else {
                    Toast.makeText(getContext(), "数据获取出错,请检查网络情况", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.me_re_my_investment://我的投资
                MyInvestmentFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myinvset, "", 1);//事件统计
                break;
            case R.id.me_re_my_bill://我的账单
                MyBillFragment();
                StatService.onEvent(getContext(), BaiDustatistic.mybill, "", 1);//事件统计
                break;
            case R.id.me_re_my_account://我的账户
                MyAccountFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myaccount, "", 1);//事件统计
                break;
            case R.id.me_re_my_recom://邀请好友
                MyRecomFragment();
                StatService.onEvent(getContext(), BaiDustatistic.myrecomed, "", 1);//事件统计
                break;
            case R.id.me_ll_bouns_points://积分商城（更多）
                MyBonusPoints();
                StatService.onEvent(getContext(), BaiDustatistic.me_myintegral, "", 1);//事件统计
                break;
            case R.id.me_ll_money_calendar://汇款日历
                ReturnMoneyCalendar();
                StatService.onEvent(getContext(), BaiDustatistic.me_repaycalander, "", 1);//事件统计
                break;
            case R.id.me_bt_recharge_money://充值
                if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
                    MeRechargeFragment();
                } else if (Constant.ACCOUNT_SHB.equals(userModel.getThirdPayType())) {


                    if ("Y".equals(userModel.getActive())) {
                        //开户
                        if ("CHECKED".equals(userModel.getSHBAuth())) {
                            WebViewModel webViewModel = new WebViewModel();
                            webViewModel.setType("type_shb_loadurl");
                            webViewModel.setTitle("充值");
                            webViewModel.setUrl(Constant.SHB_CHONGZHI);
                            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("WebViewModel", webViewModel));
                        } else if ("NOT".equals(userModel.getSHBAuth())) {
                            T.D("请先开通上海银行存管账户");
                            startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
                        }
                    } else if ("N".equals(userModel.getActive())) {//激活
                        T.D("请先激活上海银行存管账户");
                        SHBUserActivate();
                    }
                }


                StatService.onEvent(getContext(), BaiDustatistic.me_rechange, "", 1);//事件统计
                break;
            case R.id.me_bt_withdrawals_money://提现

                if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
                    if (!"".equals(userModel.getWithdrawTimes())) {

                        times = Integer.parseInt(userModel.getWithdrawTimes());
                        if (times > 0) {
                            //跳转提现页面
                            MeWithdrawFragment();
                        } else {
                            ghbGlobalData.setWithdrawGuide(); //华兴提现提示
                        }
                    } else {
                        ghbGlobalData.setWithdrawGuide(); //华兴提现提示
                    }
                } else if (Constant.ACCOUNT_SHB.equals(userModel.getThirdPayType())) {

                    if ("Y".equals(userModel.getActive())) {
                        //开户
                        if ("CHECKED".equals(userModel.getSHBAuth())) {
                            WebViewModel webViewModel = new WebViewModel();
                            webViewModel.setType("type_shb_loadurl");
                            webViewModel.setTitle("提现");
                            webViewModel.setUrl(Constant.SHB_TIXIAN);
                            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("WebViewModel", webViewModel));
                        } else if ("NOT".equals(userModel.getSHBAuth())) {
                            T.D("请先开通上海银行存管账户");
                            startActivity(new Intent(getActivity(), SHBOpenAccountActivity.class));
                        }
                    } else if ("N".equals(userModel.getActive())) {//激活
                        T.D("请先激活上海银行存管账户");
                        SHBUserActivate();
                    }

                }

                StatService.onEvent(getContext(), BaiDustatistic.me_widthraw, "", 1);//事件统计
                break;
            default:
                break;
        }
    }


    @Override
    public void getDataSuccess(Object model) {

        //新增红包判断
        if (model.getClass().equals(GuideModel.class)) {
            guideModel = (GuideModel) model;
        }

        if (model.getClass().equals(AccountBalanceBaseModel.class)) {
            stopRefresh(memain_sr_swipe_refresh);

            AccountBalanceBaseModel balanceBaseModel = (AccountBalanceBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(balanceBaseModel.status)) {

                userModel.setbalanceAvaliable(balanceBaseModel.result.balanceAvaliable);
                userModel.setbalance(balanceBaseModel.result.balance);

                setData(balanceBaseModel);
                Logger.i("AccountBalance-isSuccess->" + ((AccountBalanceBaseModel) model).result.toString());

            } else if (Constant.STATUS_FAILED.equals(balanceBaseModel.status)) {

            }
        }
        if (model.getClass().equals(IsRealNameBaseModel.class)) {
            IsRealNameBaseModel realNameBaseModel = (IsRealNameBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(realNameBaseModel.status)) {
                userModel.setidCard(realNameBaseModel.result.idCard);
                userModel.setrealname(realNameBaseModel.result.realname);
                userModel.setmobileNumber(realNameBaseModel.result.mobileNumber);
                userModel.setauth(realNameBaseModel.result.auth);
                Logger.i("IsRealNameBaseModel-isSuccess->" + realNameBaseModel.result.toString());

            } else if (Constant.STATUS_FAILED.equals(realNameBaseModel.status)) {

            }
        }
        if (model.getClass().equals(BindCardQueryBaseModel.class)) {
            BindCardQueryBaseModel cardQueryBaseModel = (BindCardQueryBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(cardQueryBaseModel.status)) {
                if (cardQueryBaseModel.result != null) {
                    if (cardQueryBaseModel.result.size() > 0) {
                        //保存数据(银行卡状态)
                        userModel.setcardNo(cardQueryBaseModel.result.get(0).cardNo);
                        userModel.setrealname(cardQueryBaseModel.result.get(0).realname);
                        userModel.setbankName(cardQueryBaseModel.result.get(0).bankName);
                        userModel.setidCard(cardQueryBaseModel.result.get(0).idCard);
                        userModel.setBankCardStatus(cardQueryBaseModel.result.get(0).status);
                        userModel.seticonUrl(cardQueryBaseModel.result.get(0).iconUrl);
                        userModel.setbaofoo_limit_des(cardQueryBaseModel.result.get(0).baofoo_limit_des);
                        userModel.setbaofoo_limit_money(cardQueryBaseModel.result.get(0).baofoo_limit_money);
                    }
                    Logger.i("BindCardQueryBaseModel-isSuccess->" + cardQueryBaseModel.result.toString());
                }

            } else if (Constant.STATUS_FAILED.equals(cardQueryBaseModel.status)) {

            }

        }
        //app积分商城展现形式h5
        if (model.getClass().equals(AppMypointModel.class)) {
            AppMypointModel myPoint = (AppMypointModel) model;
            if (Constant.STATUS_SUCCESS.equals(myPoint.status)) {
                AppMypointModel result = myPoint.result;
                myPointPicUrl = result.pic_url;
                myPointTitle = result.title;
                Logger.i("AppMypointModel-isSuccess->" + result.toString());

            }
        }
        //UserInfo
        if (model.getClass().equals(UserInfoModel.class)) {

            UserInfoModel userInfoModel = (UserInfoModel) model;
            if (Constant.STATUS_SUCCESS.equals(userInfoModel.getStatus())) {
                //保存易宝-华兴 实名状态
                UserInfoModel.ResultBean result = userInfoModel.getResult();
                userModel.setActive(result.getActive());
                userModel.setSHBAuth(result.getSh_realname_auth());
                userModel.setGhbPayAuth(result.getGhb_realname_auth());
                userModel.setYeepayAuth(result.getRealname_auth());
                setView();
                Logger.i("UserInfoModel-isSuccess->" + result.toString());

            }
        }
        //getGhbUserExt
        if (model.getClass().equals(GhbUserExtModel.class)) {
            //保存E账户数据 和绑卡状态
            GhbUserExtModel ghbUserExtModel = (GhbUserExtModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbUserExtModel.getStatus())) {
                Logger.i("GhbUserExtModel-isSuccess->" + ghbUserExtModel.getResult().toString());
                userModel.saveGhbEAccount(ghbUserExtModel);//保存-getGhbUserExt数据
            }
        }

        if (model.getClass().equals(SHBActivationAModel.class)) {
            SHBActivationAModel shbModel = (SHBActivationAModel) model;
            P.dismiss();
            SHBActivationAModel.ResultBean result = shbModel.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("激活上海银行存管账户");
            webViewModel.setUrl(autoSubmitForm + "");

            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("WebViewModel", webViewModel));

        }


    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        stopRefresh(memain_sr_swipe_refresh);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    /**
     * 设置参数
     */
    private void setData(AccountBalanceBaseModel model) {
        AccountBalanceModel model1 = model.result;
        me_tv_cumulative_gain.setText(model1.collectProfit + "");
        me_tv_total_assets.setText(model1.balance + "");//资产总额
        me_tv_available_balance.setText(model1.balanceAvaliable + "");
        me_tv_frozen_funds.setText(model1.freezeAmount + "");
        me_tv_amount_received.setText(model1.collectCorpusAndInterest + "");
    }

    /**
     * 退出登录
     */
    public void MeLoginFragment() {
        remove("MeMainFragment");
        MeLoginFragment meLoginFragment = new MeLoginFragment();
        add(R.id.main_fr_main_me, meLoginFragment, "MeLoginFragment", null);
    }

    /**
     * 我的投资
     */
    public void MyInvestmentFragment() {

        Intent it = new Intent(getActivity(), MeMyInvesActivity.class);
        startActivity(it);
    }

    /**
     * 我的账单
     */
    public void MyBillFragment() {

        Intent it = new Intent(getActivity(), MeMyBillActivity.class);
        startActivity(it);
    }

    /**
     * 我的账户
     */
    public void MyAccountFragment() {
        //上海--华兴切换状态
        if (Constant.ACCOUNT_SHB.equals(userModel.getThirdPayType())) {
            Intent it = new Intent(getActivity(), AccountManagementActivity.class);
            startActivity(it);
        } else if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
            Intent it = new Intent(getActivity(), HXAccountActivity.class);
            startActivity(it);
        }

    }

    /**
     * 我的推荐
     */
    public void MyRecomFragment() {

        Intent it = new Intent(getActivity(), MeMyRecomActivity.class);
        startActivity(it);
    }

    /**
     * 积分商城
     */
    public void MyBonusPoints() {
        //判断字段是native&h5，进行相应操作
        if (Constant.NATIVE.equals(Constant.myPointType)) {
            Intent it = new Intent(getActivity(), MeMyBonusPointsActivity.class);
            startActivity(it);
        } else if (Constant.H5.equals(Constant.myPointType)) {

            BannerModel bannerModel = new BannerModel();
            bannerModel.title = myPointTitle;
            bannerModel.url = myPointPicUrl;
            bannerModel.location = "";
            bannerModel.imgRootUrl = "";

            startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));

        } else {
            Intent it = new Intent(getActivity(), MeMyBonusPointsActivity.class);
            startActivity(it);
        }
    }

    /**
     * 汇款日历
     */
    public void ReturnMoneyCalendar() {

        Intent it = new Intent(getActivity(), ReturnMoneyCalendarActivity.class);
        startActivity(it);
    }

    /**
     * 充值
     */
    public void MeRechargeFragment() {

        Intent it = new Intent(getActivity(), MeRechargeActivity.class);
        startActivity(it);
    }

    /**
     * 提现
     */
    public void MeWithdrawFragment() {

        Intent it = new Intent(getActivity(), MeWithdrawActivity.class);
        startActivity(it);

    }

    private void loadIconView() {
        Glide.with(this)
                .load(Constant.my_invest)
                .placeholder(R.mipmap.ic_me_touzi)
                .error(R.mipmap.ic_me_touzi)
                .into(me_re_my_investment_img);
        Glide.with(this)
                .load(Constant.my_bill)
                .placeholder(R.mipmap.ic_me_zhangdan)
                .error(R.mipmap.ic_me_zhangdan)
                .into(me_re_my_bill_img);
        Glide.with(this)
                .load(Constant.my_account)
                .placeholder(R.mipmap.ic_me_zhanghu)
                .error(R.mipmap.ic_me_zhanghu)
                .into(me_re_my_account_img);

        Glide.with(this)
                .load(Constant.my_recommended)
                .placeholder(R.mipmap.ic_me_recom)
                .error(R.mipmap.ic_me_recom)
                .into(me_re_my_recom_img);
        Glide.with(this)
                .load(Constant.my_calendar)
                .placeholder(R.mipmap.calendar_return)
                .error(R.mipmap.calendar_return)
                .into(me_ll_money_calendar_img);

        Glide.with(this)
                .load(Constant.my_piont)
                .placeholder(R.mipmap.ic_me_mypoints)
                .error(R.mipmap.ic_me_mypoints)
                .into(me_ll_bouns_points_img);
        Glide.with(this)
                .load(Constant.my_hongbao)
                .placeholder(R.mipmap.ic_me_hongbao)
                .error(R.mipmap.ic_me_hongbao)
                .into(me_ll_red_wrap_img);
        Glide.with(this)
                .load(Constant.my_fuli)
                .placeholder(R.mipmap.ic_me_fuli)
                .error(R.mipmap.ic_me_fuli)
                .into(me_ll_guide_img);

    }


    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "我的页面");//(this);
        iupLoadData();
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的页面");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
