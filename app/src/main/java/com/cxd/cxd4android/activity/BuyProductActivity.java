package com.cxd.cxd4android.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.GhbH5Activity;
import com.cxd.cxd4android.activity.subactivity.MeRechargeActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IBuyProductCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductCheckCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductJumpLoginCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductLockCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductMakeMoneyCallBack;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.interfaces.IUpdataRechargeCallBack;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbH5Model;
import com.cxd.cxd4android.model.GhbInvestModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.InvesListModel;
import com.cxd.cxd4android.model.MakeMoneyModel;
import com.cxd.cxd4android.model.NewerBaseModel;
import com.cxd.cxd4android.model.RedPaperModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.BuyProductLoanPresenter;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMainFragmentPresenter;
import com.cxd.cxd4android.presenter.MeMyGhbAccountPresenter;
import com.cxd.cxd4android.shbbank.html.SHBHTML;
import com.cxd.cxd4android.shbbank.html.WebViewActivity;
import com.cxd.cxd4android.shbbank.interfaces.SHBBuyMakeMoneyCallBack;
import com.cxd.cxd4android.shbbank.model.SHBActivationAModel;
import com.cxd.cxd4android.shbbank.model.SHBUserInvest;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.shbbank.openaccount.SHBOpenAccountActivity;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.cxd.cxd4android.widget.dialog.CouponDialog;
import com.cxd.cxd4android.widget.dialog.DialogPost;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.cxd.cxd4android.widget.popupwindow.BubblePopupWindow;
import com.cxd.cxd4android.widget.popupwindow.BubbleTextView;
import com.cxd.cxd4android.widget.popupwindow.RelativePos;
import com.cxd.cxd4android.widget.time.CountdownView;
import com.cxd.cxd4android.widget.time.DynamicConfig;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.micros.utils.S;
import com.micros.utils.Z;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 购买页面
 * Created by Administrator on 15-11-23.
 */
public class BuyProductActivity extends BaseActivity implements LoadingView {


    /**
     * 购买页面信息
     */
    com.cxd.cxd4android.model.InvesListModel InvesListModel;
    @Bind(R.id.product_tv_Coupon)
    TextView productTvCoupon;
    /*
    * 倒计时控件
    * */
    @Bind(R.id.product_time_CountdownView)
    CountdownView mProductTimeCountdownView;
    /*
    * 风险提示书
    * */
    @Bind(R.id.product_tv_risk_assessment)
    TextView mProductTvRiskAssessment;
    //风险提示书按钮
    @Bind(R.id.product_img_make_money)
    ImageView mProductImgMakeMoney;
//    @Bind(R.id.item_jiaxi_decide)
//    LinearLayout item_jiaxi_decide;

    /**
     * 购买钱数
     */
    private String money = "";
    /**
     * 易宝跳转购买
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

    boolean input = false;

    /**
     * 购买正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 购买左上角箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;

    /**
     * 购买年化率
     **/
    @Bind(R.id.product_tv_rate_num)
    TextView product_tv_rate_num;
    /**
     * 购买封闭期
     **/
    @Bind(R.id.product_tv_closure_period)
    TextView product_tv_closure_period;


    /**
     * 购买项目详情
     **/
    @Bind(R.id.product_tv_prouduct_details)
    TextView product_tv_prouduct_details;
    /**
     * 购买项目风控
     **/
    @Bind(R.id.product_tv_prouduct_risk)
    TextView product_tv_prouduct_risk;
    /**
     * 购买投资记录
     **/
    @Bind(R.id.product_tv_inves_recod)
    TextView product_tv_inves_recod;
    /**
     * 购买账户余额
     **/
    @Bind(R.id.product_tv_account_balance)
    TextView product_tv_account_balance;
    /**
     * 购买充值
     **/
    @Bind(R.id.product_tv_account_rechange)
    TextView product_tv_account_rechange;
    /**
     * 购买最小投资金额
     **/
    @Bind(R.id.product_tv_min_money)
    TextView product_tv_min_money;
    /**
     * 购买递增金额
     **/
    @Bind(R.id.product_tv_cardinal_money)
    TextView product_tv_cardinal_money;
    /**
     * 购买递增金额
     **/
    @Bind(R.id.product_tv_inves_money)
    EditText product_tv_inves_money;
    /**
     * 购买余额不足
     **/
    @Bind(R.id.product_tv_not_fund)
    TextView product_tv_not_fund;
    /**
     * 购买预计收益
     **/
    @Bind(R.id.product_tv_anticipated_income)
    TextView product_tv_anticipated_income;
    /**
     * 购买账户余额类型
     **/
    @Bind(R.id.product_tv_accountbalance_type)
    TextView product_tv_accountbalance_type;
    /**
     * 购买加息
     **/
    @Bind(R.id.product_tv_jiaxi)
    TextView product_tv_jiaxi;

    /**
     * 购买马上赚钱
     **/
    @Bind(R.id.product_bt_submit_makemoney)
    Button product_bt_submit_makemoney;

    /**
     * 购买预计收益
     **/
    @Bind(R.id.product_ll_anticipated_income)
    LinearLayout product_ll_anticipated_income;

    /**
     * 滚动
     **/
    @Bind(R.id.mScrollView)
    ScrollView mScrollView;
    /**
     * 滚动
     **/
    @Bind(R.id.myLayout)
    LinearLayout myLayout;
    /**
     * 保存我页面Tab选中状态(请求余额完成后改回原值)
     **/
    String YeepayORGhb = "";
    /**
     * 等额本息字体
     **/
    @Bind(R.id.product_tv_denge)
    TextView product_tv_denge;

    @Bind(R.id.product_tv_wenhao)
    TextView product_tv_wenhao;

    @Bind(R.id.product_tv_denge_benxi)
    TextView product_tv_denge_benxi;

    @Bind(R.id.product_rel)
    RelativeLayout product_rel;

    @Bind(R.id.product_tv_huankuan_shuoming)
    TextView product_tv_huankuan_shuoming;

    @Bind(R.id.product_huankuan)
    LinearLayout product_huankuan;

    private String explain;

    private BuyProductLoanPresenter loanPresenter;
    Double cardinalNumbers = 0.0;
    String model_id = "";
    private GhbGlobalData ghbGlobalData;
    private RedPaperModel redPaperModel;
    private String coupon_user_id = "";
    private boolean intput = true;
    private MakeMoneyModel mMakeMoneyModel;
    private MakeMoneyModel.ResultBean mResult;
    private boolean isMakeMoney = true;
    private MeMyGhbAccountPresenter mMeMyGhbAccountPresenter;
    private GuideModel mGuideModel;
    private BubbleTextView mPopup_bubble;
    private BubblePopupWindow mBubblePopupWindow;
    private RelativePos mRelativePos = new RelativePos(RelativePos.CENTER_HORIZONTAL, RelativePos.CENTER_VERTICAL);
    private SHBPresenter mShbPresenter;
    private String mThirdPayType;
    MeMainFragmentPresenter meMainFragmentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_buy_products);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        mThirdPayType = userModel.getThirdPayType();
        mShbPresenter = new SHBPresenter(this);
        meMainFragmentPresenter = new MeMainFragmentPresenter(this);
        ghbGlobalData = new GhbGlobalData(this);
        loanPresenter = new BuyProductLoanPresenter(BuyProductActivity.this);
        mMeMyGhbAccountPresenter = new MeMyGhbAccountPresenter(BuyProductActivity.this);
        tv_title.setText("购买");
        Btn_left.setVisibility(View.VISIBLE);
        YeepayORGhb = userModel.getThirdPayType();

        InvesListModel = (InvesListModel) getIntent().getSerializableExtra("InvesListModel");
        if (InvesListModel != null) {
            //请求数据
            initData();
            cardinalNumbers = Double.valueOf(InvesListModel.cardinalNumber);
            model_id = InvesListModel.id;

        }
        //风险提示相关数据请求
        initMakeMoney();
        //老接口相关数据请求
        initDataLoad();

        setListener();
        //初始化popuwindow
        initView();
        productTvCoupon.setClickable(false);
    }

    private void initView() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.popuwindow_layout, null);
        mPopup_bubble = (BubbleTextView) rootView.findViewById(R.id.popup_bubble);
        mBubblePopupWindow = new BubblePopupWindow(rootView, mPopup_bubble);
        mPopup_bubble.setText(InvesListModel.typeDesc);

        mRelativePos.setHorizontalRelate(RelativePos.CENTER_HORIZONTAL);
        mRelativePos.setVerticalRelate(RelativePos.ABOVE);


    }

    //老接口数据请求
    public void initDataLoad() {
        Gson gson = new Gson();
        String strn = gson.toJson("");
        mMeMyGhbAccountPresenter.loadGhbGuidePage(strn);
    }


    //是否已经评估过风险投资数据请求
    private void initMakeMoney() {
        if (!Q.isEmpty(userModel.getid())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("uid", userModel.getid());
            loanPresenter.laadMakeMoney(map);
        }

    }

    //请求红包数据
    private void initRedPaper() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", userModel.getid());
        map.put("status", "");
        map.put("loanId", InvesListModel.id);//
        map.put("investMoney", product_tv_inves_money.getText().toString().trim());
        loanPresenter.loadDataRedPaper(map);

    }


    //请求数据
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loanId", InvesListModel.id);//

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        loanPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(GhbUserExtModel.class)) {
            //保存E账户数据 和绑卡状态
            GhbUserExtModel ghbUserExtModel = (GhbUserExtModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbUserExtModel.getStatus())) {
                userModel.saveGhbEAccount(ghbUserExtModel);//保存-getGhbUserExt数据
            }
        }

        if (model.getClass().equals(UserInfoModel.class)) {
            UserInfoModel userInfoModel = (UserInfoModel) model;
            userModel.setSHBAuth(userInfoModel.getResult().getSh_realname_auth());
        }

        if (model.getClass().equals(SHBActivationAModel.class)) {
            SHBActivationAModel shbActivateStockedUser = (SHBActivationAModel) model;
            P.dismiss();
            SHBActivationAModel.ResultBean result = shbActivateStockedUser.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("激活");
            webViewModel.setUrl(autoSubmitForm + "");
            startActivity(new Intent(BuyProductActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));

        }

        if (model.getClass().equals(SHBUserInvest.class)) {
            product_tv_inves_money.setText("");
            P.dismiss();
            SHBUserInvest shbUserInvest = (SHBUserInvest) model;
            SHBUserInvest.ResultBean result = shbUserInvest.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("投资");
            webViewModel.setUrl(autoSubmitForm + "");

            startActivity(new Intent(BuyProductActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));
        }

        if (model.getClass().equals(GuideModel.class)) {
            mGuideModel = (GuideModel) model;
        }

        //新增加风险投资判断
        if (model.getClass().equals(MakeMoneyModel.class)) {

            //风险投资数据返回
            mMakeMoneyModel = (MakeMoneyModel) model;
            mResult = mMakeMoneyModel.getResult();
        }
        //红包数据
        if (model.getClass().equals(RedPaperModel.class)) {
            redPaperModel = (RedPaperModel) model;
            new CouponDialog(this, redPaperModel).show();
        }
        if (model.getClass().equals(NewerBaseModel.class)) {

            NewerBaseModel newerBaseModel = (NewerBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(newerBaseModel.status)) {
                InvesListModel = newerBaseModel.result;
                product_tv_denge.setText(InvesListModel.loanType);
                Long remaininTime = InvesListModel.remaininTime;
                //设置倒计时等于零的情况
                if (remaininTime == 0) {
                    DynamicConfig.Builder build = new DynamicConfig.Builder();
                    build.setShowDay(false);
                    build.setShowHour(false);
                    build.setShowMinute(false);
                    DynamicConfig builder = new DynamicConfig(build);
                    mProductTimeCountdownView.dynamicShow(builder);
                }
                //设置倒计时
                if (remaininTime > 0) {
                    mProductTimeCountdownView.start(remaininTime);

                }

                setData();

                if (("SH").equals(InvesListModel.trusteeshipType)) {
                    userModel.setThirdPayType(Constant.ACCOUNT_SHB);
                    if (!Q.isEmpty(userModel.getid())) {
                        initDataAccount();
                    }
                }

                if (("YEEPAY").equals(InvesListModel.trusteeshipType)) {
                    product_tv_accountbalance_type.setText("易宝账户余额(元):");
                    userModel.setThirdPayType(Constant.ACCOUNT_YEEPAY);//保存状态类型为 华兴
                    if (!Q.isEmpty(userModel.getid())) {
                        initDataAccount();
                    }

                    /**判断 CHECKED表示实名认证,NOT:未认证**/
                    if ("NOT".equals(userModel.getYeepayAuth())) {
                        product_tv_inves_money.setFocusable(false);
                        product_tv_inves_money.setClickable(false);
                        product_tv_account_rechange.setVisibility(View.GONE);
                        product_tv_not_fund.setText(InvesListModel.notInvestMsg);
                        product_tv_not_fund.setVisibility(View.VISIBLE);
                        product_bt_submit_makemoney.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                        product_bt_submit_makemoney.setClickable(false);
                    }
                } else if ("GHB".equals(InvesListModel.trusteeshipType)) {
                    product_tv_accountbalance_type.setText("华兴账户余额(元):");
                    userModel.setThirdPayType(Constant.ACCOUNT_GHB);//保存状态类型为 华兴
                    if (!Q.isEmpty(userModel.getid())) {
                        initDataAccount();
                    }
                    initGhbUserExt();//获取绑卡状态
                } else {
                    product_tv_accountbalance_type.setText("账户余额(元):");
                }
            } else if (Constant.STATUS_FAILED.equals(newerBaseModel.status)) {

            }
        } else if (model.getClass().equals(AccountBalanceBaseModel.class)) {
            AccountBalanceBaseModel accountModel = (AccountBalanceBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(accountModel.status)) {

                userModel.setbalanceAvaliable(accountModel.result.balanceAvaliable);

                setData();

            } else if (Constant.STATUS_FAILED.equals(accountModel.status)) {

            }

        } else if (model.getClass().equals(IdentityBaseModel.class)) {
            product_tv_inves_money.setText("");
            IdentityBaseModel identityBaseModel = (IdentityBaseModel) model;
            P.cancel();
            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {

                //在此接口回调,刷新我的账户
                EventBus.getDefault().post(new IBuyProductMakeMoneyCallBack());

                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
                Constant.actionUrl = identityBaseModel.result.actionUrl;
                isIntercept = identityBaseModel.result.isIntercept;
                callbackUrl = identityBaseModel.result.callbackUrl;
                WebViewFragment();
            } else {

                T.D("" + identityBaseModel.msg);
            }
        } else if (model.getClass().equals(GhbInvestModel.class)) {
            product_tv_inves_money.setText("");
            P.cancel();
            GhbInvestModel ghbInvestModel = (GhbInvestModel) model;
            if (Constant.STATUS_SUCCESS.equals(ghbInvestModel.getStatus())) {
                //Todo 待完善 华兴提现功能
                String html = ghbInvestModel.getResult().getHtml();
                GhbH5Model ghbH5Model = new GhbH5Model();
                ghbH5Model.setTitle("华兴投标");
                ghbH5Model.setType("ghbh5");
                ghbH5Model.setWebUrl(html);
                Intent intent = new Intent(BuyProductActivity.this, GhbH5Activity.class);
                intent.putExtra("h5_type", ghbH5Model);
                this.startActivity(intent);
                Log.i("ghbWithdrawModel-->", html);
            } else {

                T.D("" + ghbInvestModel.getMsg());
            }
        }
    }

    private void initGhbUserExt() {
        if (!"".equals(userModel.getid())) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            meMainFragmentPresenter.loadGhbUserExt(strn); //请求华兴E账户数据 和 回调绑卡状态查询
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

    //实现接口中的方法，该方法在resizeLayout的onSizeChanged方法中调用
    private Handler mHandler = new Handler();


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Integer event) {
        if (event == 123456) {
            //马上赚钱
            initMakeMoney();
            if ("GHB".equals(InvesListModel.trusteeshipType)) {

                initDataGhbMakemoney();
            } else if ("SH".equals(InvesListModel.trusteeshipType)) {
                initSHBDataMakemoney();
            }


        }

    }

    /**
     * 事件响应方法,回调更新View
     */
    @Subscribe
    public void onEvent(IBuyProductCallBack event) {
        setInPutView();
    }

    /**
     * 事件响应方法,请求账户信息
     */
    @Subscribe
    public void onEvent(IUpdataRechargeCallBack event) {
        //请求账户信息,更新余额
        initDataAccount();

    }

    /**
     * 事件响应方法,购买验证回调//充值和马上赚钱验证成功返回
     */
    @Subscribe
    public void onEvent(IBuyProductCheckCallBack event) {

        if (("BuyProductActivityRechange").equals(event.getBuyProductCheck())) {
            BuyProductRechargeFragment();
            EventBus.getDefault().post(new IBuyProductLockCallBack());

        } else if (("BuyProductActivityMakemoney").equals(event.getBuyProductCheck())) {
            //Todo 待完善,华兴-马上赚钱 请求数据
//            initDataMakemoney();

            initDataGhbMakemoney();
            EventBus.getDefault().post(new IBuyProductLockCallBack());
        } else if (("BuyProductActivityPsw").equals(event.getBuyProductCheck())) {
            EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityPsw"));
            EventBus.getDefault().post(new ILoginCallBack(4));
            String getid = userModel.getid();

            userModel.clear();
            userModel.setid(getid);
            finish();
        } else if (("BuyProductActivityAccount").equals(event.getBuyProductCheck())) {
            EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityAccount"));
            EventBus.getDefault().post(new ILoginCallBack(4));
            userModel.clear();
            finish();
        }
    }

    /**
     * 设置监听
     */
    private void setListener() {
//        mScrollView.setOnTouchListener( new TouchListenerImpl());

        product_tv_inves_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里必须要给一个延迟，如果不加延迟则没有效果。我现在还没想明白为什么
                //addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll， view可能还没有显示出来，所以会失败应该通过handler在新线程中更新
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        setInPutView();

                        //将ScrollView滚动到底
                        mScrollView.fullScroll(View.FOCUS_DOWN);


                        Rect r = new Rect();
                        myLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = myLayout.getRootView().getHeight();
                        //int heightDifference = screenHeight - (r.bottom - r.top);

                        int heightDifference = screenHeight - (r.top);
                        //Log.e("Keyboard Size", "Size: " + heightDifference);
//                        L.I("==bottom==" + r.bottom);//1794//986
//                        L.I("==top==" + r.top);//75
//                        L.I("==screenHeight==" + screenHeight);//1920


                        ViewGroup.LayoutParams p = mScrollView.getLayoutParams();
                        p.height = screenHeight - heightDifference;//screenHeight - heightDifference
                        mScrollView.setLayoutParams(p);
//                        L.I("==mScrollView==" + mScrollView.getHeight());//1794//986
                        //boolean visible = heightDiff > screenHeight / 3;

                    }
                }, 100);
            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; //屏幕宽
        final int height = dm.heightPixels; //屏幕高


        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        final int statusBarHeight = frame.top; //状态栏高

        final int screenHeights = getWindowManager().getDefaultDisplay().getHeight();
//        ViewGroup.LayoutParams p=mScrollView.getLayoutParams();
//        p.height = screenHeights+statusBarHeight;
//        mScrollView.setLayoutParams(p);

//        L.I("==height==" + height);//1794
//        L.I("==statusBarHeight==" + statusBarHeight);//0
//        L.I("==screenHeights==" + screenHeights);//1794
        myLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                myLayout.getWindowVisibleDisplayFrame(r);

                int screenHeight = myLayout.getRootView().getHeight();
//                int heightDifference = screenHeight - (r.bottom - r.top);

                int heightDifference = screenHeight - (r.bottom);
                //Log.e("Keyboard Size", "Size: " + heightDifference);
//                L.I("==bottom==" + r.bottom);//1794//986
//                L.I("==top==" + r.top);//75
//                L.I("==screenHeight==" + screenHeight);//1920


                ViewGroup.LayoutParams p = mScrollView.getLayoutParams();
                p.height = screenHeight - heightDifference;//screenHeight - heightDifference
                mScrollView.setLayoutParams(p);
//                L.I("==mScrollView==" + mScrollView.getHeight());//1794//986
                //boolean visible = heightDiff > screenHeight / 3;

            }
        });


        product_tv_inves_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Q.isEmpty(product_tv_inves_money.getText().toString().trim())) {
                    if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) % (cardinalNumbers) == 0) {

                        if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {//已登录
                            if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) > Double.parseDouble(userModel.getbalanceAvaliable())) {
                                input = false;
                                product_tv_not_fund.setText("余额不足");
                                product_tv_not_fund.setVisibility(View.VISIBLE);
                                //优惠券不可点击
                                productTvCoupon.setClickable(false);
//                                productTvCoupon.setBackgroundColor(getResources().getColor(R.color.gray));
                                productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));

                                //等额本息,loan_type_1、loan_type_5   先息后本；loan_type_6 等额本息
                                if (("loan_type_6").equals(InvesListModel.type)) {
                                    product_tv_denge_benxi.setText(String.valueOf(Z.round(getMonthRepay(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));
                                    product_tv_anticipated_income.setText(String.valueOf(Z.round(getInterset(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));

                                } else {
                                    if (("天").equals(InvesListModel.unit)) {


                                        product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline), 2)));
                                    } else {
                                        product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline) * 30, 2)));
                                    }
                                }


                            } else {

                                if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) > Double.parseDouble(InvesListModel.remainMoney)) {
                                    input = false;
                                    product_tv_not_fund.setText("大于可投金额");
                                    product_tv_not_fund.setVisibility(View.VISIBLE);
                                    //优惠券不可点击
                                    productTvCoupon.setClickable(false);
//                                    productTvCoupon.setBackgroundColor(getResources().getColor(R.color.gray));
                                    productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));

                                } else {

                                    if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) > 0) {
                                        input = true;
                                        product_tv_not_fund.setVisibility(View.INVISIBLE);

                                        //优惠券可以点击
                                        productTvCoupon.setClickable(true);
//                                        productTvCoupon.setBackgroundColor(getResources().getColor(R.color.Color_Main_Title_Bar));
                                        productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_Bar));

                                        //等额本息,loan_type_1、loan_type_5   先息后本；loan_type_6 等额本息
                                        if (("loan_type_6").equals(InvesListModel.type)) {

                                            product_tv_denge_benxi.setText(String.valueOf(Z.round(getMonthRepay(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));
                                            product_tv_anticipated_income.setText(String.valueOf(Z.round(getInterset(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));


                                        } else {
                                            if (("天").equals(InvesListModel.unit)) {
                                                product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline), 2)));
                                            } else {
                                                product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline) * 30, 2)));
                                            }
                                        }

                                    } else {
                                        input = false;
                                        product_tv_not_fund.setText("递增金额" + cardinalNumbers + "元");
                                        product_tv_not_fund.setVisibility(View.VISIBLE);

                                        //优惠券不可点击
                                        productTvCoupon.setClickable(false);
//                                        productTvCoupon.setBackgroundColor(getResources().getColor(R.color.gray));
                                        productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));

                                        //等额本息,loan_type_1、loan_type_5   先息后本；loan_type_6 等额本息
                                        if (("loan_type_6").equals(InvesListModel.type)) {
                                            product_tv_denge_benxi.setText(String.valueOf(Z.round(getMonthRepay(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));
                                            product_tv_anticipated_income.setText(String.valueOf(Z.round(getInterset(Double.parseDouble(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));


                                        } else {
                                            if (("天").equals(InvesListModel.unit)) {
                                                product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline), 2)));
                                            } else {
                                                product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline) * 30, 2)));
                                            }
                                        }
                                    }


                                }
                            }
                        } else {//未登录
                            if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) > Double.parseDouble(InvesListModel.remainMoney)) {
                                input = false;
                                product_tv_not_fund.setText("大于可投金额");
                                product_tv_not_fund.setVisibility(View.VISIBLE);
                                //优惠券不可点击
                                productTvCoupon.setClickable(false);
//                                productTvCoupon.setBackgroundColor(getResources().getColor(R.color.gray));
                                productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));

                            } else {
                                input = true;
                                product_tv_not_fund.setVisibility(View.INVISIBLE);
                                //优惠券可以点击
                                productTvCoupon.setClickable(true);
//                                productTvCoupon.setBackgroundColor(getResources().getColor(R.color.Color_Main_Title_Bar));
                                productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_Bar));

                                //等额本息,loan_type_1、loan_type_5   先息后本；loan_type_6 等额本息
                                if (("loan_type_6").equals(InvesListModel.type)) {
                                    product_tv_denge_benxi.setText(String.valueOf(Z.round(getMonthRepay(Integer.parseInt(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));
                                    product_tv_anticipated_income.setText(String.valueOf(Z.round(getInterset(Integer.parseInt(product_tv_inves_money.getText().toString().trim()), Double.parseDouble(InvesListModel.rate) / 100 / 12, Integer.parseInt(InvesListModel.deadline)), 2)));


                                } else {
                                    product_tv_anticipated_income.setText(String.valueOf(Z.round(Double.parseDouble(product_tv_inves_money.getText().toString().trim()) * Double.parseDouble(InvesListModel.rate) / 100 / 365 * Double.parseDouble(InvesListModel.deadline), 2)));
                                }
                            }
                        }

                    } else {
                        input = false;
                        product_tv_not_fund.setText("递增金额" + cardinalNumbers + "元");
                        product_tv_not_fund.setVisibility(View.VISIBLE);
                        //优惠券不可点击
                        productTvCoupon.setClickable(false);
//                        productTvCoupon.setBackgroundColor(getResources().getColor(R.color.gray));
                        productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));

                    }
                } else {
                    input = false;
                    product_tv_not_fund.setVisibility(View.INVISIBLE);
                    product_tv_denge_benxi.setText("");
                    product_tv_anticipated_income.setText("");
                    productTvCoupon.setText("优惠券");
//                    productTvCoupon.setBackgroundColor(getResources().getColor(R.color.Color_Main_Title_Bar));
                    productTvCoupon.setClickable(false);
                    productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_hongbao));


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int total = 1;
    public static IBuyProductJumpLoginCallBack IBuyProductJumpLoginCallBack;


    /**
     * 重新设置View
     */
    private void setInPutView() {
        //                    DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels; //屏幕宽
//        final int height = dm.heightPixels; //屏幕高

        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        final int statusBarHeight = frame.top; //状态栏高

        final int screenHeights = getWindowManager().getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams p = mScrollView.getLayoutParams();
        p.height = screenHeights + statusBarHeight;
        mScrollView.setLayoutParams(p);
    }

    /**
     * 设置数据
     */
    private void setData() {
        if (!("".equals(InvesListModel.addRate))) {

            if (!("0".equals(InvesListModel.addRate))) {
                //总数
                String rate = InvesListModel.rate;
                float parseFloatRate = Float.parseFloat(rate);
                //加息
                String addRate = InvesListModel.addRate;
                float parseFloatAddRate = Float.parseFloat(addRate);

                float totalNumber = parseFloatRate - parseFloatAddRate;

                String totalNumberFinal = setStrings(String.valueOf(totalNumber));
                String loanLabelsNumber = setStrings(addRate);

                product_tv_rate_num.setText(totalNumberFinal);
                product_tv_jiaxi.setText("+" + loanLabelsNumber + "%");
                product_tv_jiaxi.setVisibility(View.VISIBLE);
//                item_jiaxi_decide.setVisibility(View.VISIBLE);

            } else {
                product_tv_rate_num.setText(InvesListModel.rate);
            }
        } else {
            product_tv_rate_num.setText(InvesListModel.rate);
        }


        String progress = InvesListModel.progress;
        if (progress.contains(".")) {
            if (progress.startsWith("0") && Double.parseDouble(progress) != 0) {
                progress = "1";
            } else {
                int index = progress.indexOf(".");
                progress = progress.substring(0, index);
            }
        }
        if (!Q.isEmpty(progress)) {
//            bar0.setProgress(Integer.parseInt(progress));
        }


//        + InvesListModel.unit
        product_tv_closure_period.setText(InvesListModel.deadline);
        if (InvesListModel.unit.equals("天")) {
            product_tv_huankuan_shuoming.setText("还款期限(天)");
        } else {
            product_tv_huankuan_shuoming.setText("还款期限(月)");
        }

        product_tv_account_balance.setText("\t" + userModel.getbalanceAvaliable());
        //更改剩余可投金额位置
        product_tv_min_money.setText(InvesListModel.remainMoney);
        product_tv_cardinal_money.setText(InvesListModel.cardinalNumber);
        //更改最小投资金额为起投金额
        product_tv_inves_money.setHint("起投金额(元)\t" + InvesListModel.minInvestMoney);

        if (("loan_type_6").equals(InvesListModel.type)) {
            product_huankuan.setVisibility(View.VISIBLE);
        } else {
            product_huankuan.setVisibility(View.INVISIBLE);
        }

        if (("raising").equals(InvesListModel.status)) {//可投资

        } else {
            product_tv_inves_money.setHint("起投金额(元)\t" + InvesListModel.minInvestMoney);
            product_tv_inves_money.setFocusable(false);
            product_tv_inves_money.setClickable(false);
            product_ll_anticipated_income.setVisibility(View.GONE);
            product_bt_submit_makemoney.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
            product_bt_submit_makemoney.setClickable(false);
        }


    }

    // 每月月供额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
    // 100*22.44%/12*(1+22.44%/12)^5/(1+22.44%/12)^5-1
    public double getMonthRepay(double investMoney, double investMonthRate, int deadline) {
        Log.d("wertyutrerty", String.valueOf(investMonthRate));
        return investMoney * investMonthRate * (Math.pow((1 + investMonthRate), deadline)) / (Math.pow((1 + investMonthRate), deadline) - 1);

    }

    // 总利息=还款月数×每月月供额-贷款本金
    public double getInterset(double investMoney, double investMonthRate, int deadline) {

        return getMonthRepay(investMoney, investMonthRate, deadline) * deadline - investMoney;
    }

    //加息计算
    private String setStrings(String s) {

        if (s.contains(".")) {

            String[] array = s.split("\\.");

            String s1 = array[1];
            char FirstNumber = s1.charAt(0);
            String firstNumber = String.valueOf(FirstNumber);
            int parseInt = Integer.parseInt(firstNumber);


            if (s1.length() > 1) {
                char TwoNumber = s1.charAt(1);
                String twoNumber = String.valueOf(TwoNumber);
                int two = Integer.parseInt(twoNumber);

                if (parseInt > 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }

                if (parseInt < 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }
                if (parseInt > 0 && two < 0) {
                    return array[0] + "." + firstNumber;
                }


            } else {
                if (firstNumber.equals("0")) {
                    return array[0];
                }
            }

        }


        return s;
    }


    @OnClick({R.id.Btn_left, R.id.product_tv_prouduct_details, R.id.product_tv_prouduct_risk, R.id.product_tv_inves_recod, R.id.product_tv_account_rechange, R.id.product_bt_submit_makemoney, R.id.product_tv_Coupon, R.id.product_tv_risk_assessment, R.id.product_img_make_money, R.id.product_tv_risk_cont, R.id.product_tv_risk_loan, R.id.product_tv_risk_mass, R.id.product_rel})
    void onClick(View view) {
        switch (view.getId()) {
            //等额本息
            case R.id.product_rel:
                //弹出提示
                ShowPopuWindow();

                break;

            case R.id.product_tv_risk_mass:
                ghbGlobalData.setmass();
                break;
            case R.id.product_tv_risk_loan:
                //诚信贷网络借款服务协议
                ghbGlobalData.setLoan();
                break;
            //投资理财委托协议书
            case R.id.product_tv_risk_cont:
                ghbGlobalData.setCont();
                break;
            //风险提示书按钮
            case R.id.product_img_make_money:
                if (isMakeMoney) {
                    mProductImgMakeMoney.setBackgroundResource(R.mipmap.make_money_q);
                } else {
                    mProductImgMakeMoney.setBackgroundResource(R.mipmap.make_money_y);
                }
                isMakeMoney = !isMakeMoney;

                break;
            //风险提示书事件监听
            case R.id.product_tv_risk_assessment:
                if (mGuideModel.getResult().getApp_tip_riskwarning_title() != null & mGuideModel.getResult().getApp_tip_riskwarning_url() != null) {
                    ghbGlobalData.setMakeMoney(mGuideModel);
                } else {
                    T.D("风险提示书数据有误");
                }

                break;
            case R.id.product_tv_Coupon://点击优惠券
                //用户已登录
                if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {

                    //如果不等于空（输入的金额）
                    if (!Q.isEmpty(product_tv_inves_money.getText().toString().trim())) {
                        //如果输入的金额不是100的倍数
                        if (Double.parseDouble(product_tv_inves_money.getText().toString().trim()) % (cardinalNumbers) == 0) {
                            if (intput) {
                                initRedPaper();
                                intput = !intput;
                            }

                        } else {
                            T.D("递增金额为100");

                        }
                    }
                    //输入的金额等于空
                    else {
                        T.D("请输入投资金额");
                    }
                }
                //用户未登录
                else {
                    //优惠券不可点击
                    productTvCoupon.setClickable(true);
//                    productTvCoupon.setBackgroundColor(getResources().getColor(R.color.Color_Main_Title_Bar));
                    productTvCoupon.setTextColor(getResources().getColor(R.color.Color_Main_Title_Bar));
                    doShowDialog("温馨提示", "您处于未登录状态,请先登录");
                }
                break;
            case R.id.Btn_left://返回
                userModel.setThirdPayType(YeepayORGhb);//保存状态类型为 易宝或者华兴

                finish();
//                overridePendingTransition(R.anim.activity_on_in_animation, R.anim.activity_on_out_animation);
                break;
            case R.id.product_tv_prouduct_details://项目详情
                BuyProductDetailsFragment();
                StatService.onEvent(this, BaiDustatistic.buy_product_info, "", 1);//事件统计
                break;
            case R.id.product_tv_prouduct_risk://项目风控
                BuyProductRiskFragment();
                StatService.onEvent(this, BaiDustatistic.buy_product_risk, "", 1);//事件统计
                break;
            case R.id.product_tv_inves_recod://投资记录
                BuyProductRecodFragment();
                StatService.onEvent(this, BaiDustatistic.buy_product_record, "", 1);//事件统计
                break;
            case R.id.product_tv_account_rechange://充值
                StatService.onEvent(this, BaiDustatistic.buy_product_rechange, "", 1);//事件统计

                if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {

                    //已登录
                    //验证是否实名
                    if (("YEEPAY").equals(InvesListModel.trusteeshipType)) {
                        BuyProductRechargeFragment();
                    }

                    if ("GHB".equals(InvesListModel.trusteeshipType)) {
                        explain = "此产品为华兴存管银行,您尚未开通华兴银行存管账户,不能进行充值";
                        //Todo 根据易宝-华兴"CHECKED"判断是否实名认证; Y:绑卡, N:未绑卡
                        if ("CHECKED".equals(userModel.getGhbAuth())) {
                            if ("Y".equals(userModel.getGhbBindCard())) {

                                BuyProductRechargeFragment(); //华兴充值


                            } else if ("N".equals(userModel.getGhbBindCard())) {

//                                ghbGlobalData.setBindcardGuide(); //华兴绑卡提示
                                showSHBTip();
                            } else {
                                showSHBTip();
                            }

                        } else if ("NOT".equals(userModel.getGhbAuth())) {

//                            ghbGlobalData.setRlNmAtGuide(); //华兴开户提示
                            showSHBTip();
                        }
                    }


                    if ("SH".equals(InvesListModel.trusteeshipType)) {

//                        上海银行充值逻辑判断
                        if ("N".equals(userModel.getActive())) {
//                            去激活上海账户
                            SHBUserActivate();
                            return;
                        }
                        if ("Y".equals(userModel.getActive())) {

                            if ("NOT".equals(userModel.getSHBAuth())) {
//                                去上海银行实名
                                Intent intents = new Intent(BuyProductActivity.this, SHBOpenAccountActivity.class);
                                startActivity(intents);
                                return;

                            }
                            if ("CHECKED".equals(userModel.getSHBAuth())) {
//                                去充值
                                initSHbRecharge();
                                return;
                            }
                        }
                    }
                } else {//未登录
                    doShowDialog("温馨提示", "您处于未登录状态,请先登录");
                }
                break;

            case R.id.product_bt_submit_makemoney://马上赚钱
                StatService.onEvent(this, BaiDustatistic.buy_product_makemoney, "", 1);//事件统计

                if (LocalUserModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {
                    //已登录
                    if (!isMakeMoney) {
                        T.D("请勾选风险提示同意书");
                        return;
                    }
                    if (("YEEPAY").equals(InvesListModel.trusteeshipType)) {
                        if ("raising".equals(InvesListModel.status)) {
                            money = product_tv_inves_money.getText().toString().trim();
                            if (Q.isEmpty(money)) {
                                T.D("请输入投资金额");
                            }
                            if (input) {
                                if (("yes").equals(userModel.getGestrueIsFirst())) {
                                    //Todo 待完善华兴 马上赚钱
                                    initDataMakemoney();
//                                     initDataGhbMakemoney();
                                } else if (("ON").equals(userModel.getGestrueSwitch())) {
                                    Intent intents = new Intent(BuyProductActivity.this, CheckoutGestureLockActivity.class);
                                    intents.putExtra("KEY", "BuyProductActivityMakemoney");
                                    startActivity(intents);
                                } else {
                                    //Todo 待完善,华兴银行 马上赚钱 请求数据
                                    initDataMakemoney();
//                                    initDataGhbMakemoney();
                                }
                            }
                        } else {
                            T.D("项目处于不可投状态");
                        }
                    } else if ("GHB".equals(InvesListModel.trusteeshipType)) {
                        explain = " 此产品为华兴标的，您尚未开通华兴存管账号，不能购买";
                        //Todo 根据易宝-华兴"CHECKED"判断是否实名认证; Y:绑卡, N:未绑卡
                        if ("CHECKED".equals(userModel.getGhbAuth())) {
                            if ("Y".equals(userModel.getGhbBindCard())) {

                            } else if ("N".equals(userModel.getGhbBindCard())) {

//                                ghbGlobalData.setBindcardGuide(); //华兴绑卡提示
                                showSHBTip();
                                return;
                            } else {
                                showSHBTip();
                            }

                        } else if ("NOT".equals(userModel.getGhbAuth())) {

//                            ghbGlobalData.setRlNmAtGuide(); //华兴开户提示
                            showSHBTip();
                            return;

                        }


                        if ("raising".equals(InvesListModel.status)) {
                            money = product_tv_inves_money.getText().toString().trim();
                            if (Q.isEmpty(money)) {
                                T.D("请输入投资金额");
                            }
                            if (input) {
                                if (("yes").equals(userModel.getGestrueIsFirst())) {

                                    if ("333".equals(mResult.getStatus())) {
                                        doShowMakeMoney();
                                        return;
                                    }
                                    initDataGhbMakemoney();
                                } else if (("ON").equals(userModel.getGestrueSwitch())) {
                                    Intent intents = new Intent(BuyProductActivity.this, CheckoutGestureLockActivity.class);
                                    intents.putExtra("KEY", "BuyProductActivityMakemoney");
                                    startActivity(intents);
                                } else {

                                    if ("333".equals(mResult.getStatus())) {
                                        doShowMakeMoney();
                                        return;
                                    }
                                    initDataGhbMakemoney();

                                }

                            }

                        } else {
                            T.D("项目处于不可投状态");
                        }
                    }
//                        判断是否为上海标的
                    else if ("SH".equals(InvesListModel.trusteeshipType)) {

                        if ("N".equals(userModel.getActive())) {
//                        上海用户激活
                            SHBUserActivate();

                            return;
                        }
//
                        if ("Y".equals(userModel.getActive())) {

                            if ("CHECKED".equals(userModel.getSHBAuth())) {
//                        "CHECKED"已 实名;

                                if ("raising".equals(InvesListModel.status)) {
                                    money = product_tv_inves_money.getText().toString().trim();
                                    if (Q.isEmpty(money)) {
                                        T.D("请输入投资金额");
                                    }
                                    if (input) {

                                        if ("333".equals(mResult.getStatus())) {
                                            doShowMakeMoney();
                                            return;
                                        }
                                        initSHBDataMakemoney();
                                    }

                                } else {
                                    T.D("项目处于不可投状态");
                                }
                                return;

                            } else if ("NOT".equals(userModel.getSHBAuth())) {
//                         未实名去上海银行实名开户
                                Intent intents = new Intent(BuyProductActivity.this, SHBOpenAccountActivity.class);
                                startActivity(intents);
                                return;
                            }


                        }


                    }

                } else {//未登录
                    doShowDialog("温馨提示", "您处于未登录状态,请先登录");
                }

                break;
            default:
                break;
        }
    }

    //    上海用户激活
    private void SHBUserActivate() {
        P.show();
        mShbPresenter.activateStockedUser(userModel.getid());

    }

    private void ShowPopuWindow() {
        mBubblePopupWindow.showArrowTo(product_tv_wenhao, mRelativePos, 0, 0);
    }

    //新用户在华兴标的没有华兴账户提示不能购买
    public void showSHBTip() {
        final PromptDialog makemoney = new PromptDialog(this, R.style.Make_Money);
        makemoney.setCanceledOnTouchOutside(false);
        makemoney.mDialog_view.setVisibility(View.GONE);
        makemoney.dialog_imageView.setVisibility(View.INVISIBLE);
        //标题 mResult
        makemoney.dialog_title.setTextSize(16);
        makemoney.title = "非常抱歉";
        makemoney.dialog_sure.setVisibility(View.VISIBLE);
        makemoney.dialog_sure.setText("我知道了");
        makemoney.dialog_sure.setBackgroundResource(R.drawable.shape_layout_make_money_shb);
        makemoney.dialog_sure.setTextColor(getResources().getColor(R.color.btg_global_text_blue));

        makemoney.dialog_content.setVisibility(View.VISIBLE);
        makemoney.content = explain;
        makemoney.dialog_confirm.setVisibility(View.GONE);

        makemoney.dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makemoney.dismiss();

            }
        });
        makemoney.show();

    }


    //风险评估提示
    private void doShowMakeMoney() {

        final PromptDialog makemoney = new PromptDialog(this, R.style.Make_Money);
        makemoney.setCanceledOnTouchOutside(false);
        makemoney.mDialog_view.setVisibility(View.VISIBLE);
        //标题 mResult
        makemoney.dialog_title.setTextSize(16);
        makemoney.title = mResult.getAssess_title();
        makemoney.dialog_imageView.setVisibility(View.INVISIBLE);
        makemoney.dialog_confirm.setVisibility(View.VISIBLE);
        makemoney.dialog_confirm.setText("放弃赚钱");
        makemoney.dialog_confirm.setTextColor(getResources().getColor(R.color.gray));
        makemoney.dialog_confirm.setBackgroundResource(R.drawable.shape_layout_make_money_promptdialog);
        makemoney.dialog_sure.setVisibility(View.VISIBLE);
        makemoney.dialog_sure.setText("去评估");
        makemoney.dialog_sure.setBackgroundResource(R.drawable.shape_layout_make_money_right_promptdialog);
        makemoney.dialog_sure.setTextColor(getResources().getColor(R.color.btg_global_text_blue));
        makemoney.dialog_content.setVisibility(View.VISIBLE);
        makemoney.content = mResult.getSecurity_alert() + "\n" + "\n" + "\n" + mResult.getAssess_describe();

        makemoney.dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //放弃赚钱
                makemoney.dismiss();
            }
        });

        makemoney.dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.SHB_PING_GUO = "1";
                //去赚钱
                ghbGlobalData.setMakeMoneystimate(mGuideModel);
                makemoney.dismiss();

            }
        });
        makemoney.show();

    }

    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private void doShowDialog(String title, String text) {
        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(false);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Constant.INTENT_JUMP = "MainActivityTest";
                Intent intent = new Intent(BuyProductActivity.this, MainActivity.class);
                startActivity(intent);
                try {
                    EventBus.getDefault().post(new ILoginCallBack(4));
                } catch (NullPointerException e) {
                    e.printStackTrace();
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
     * (上海-去充值)
     */

    void initSHbRecharge() {
        WebViewModel webViewModel = new WebViewModel();
        webViewModel.setType("type_shb_loadurl");
        webViewModel.setTitle("上海银行充值");
        webViewModel.setUrl(Constant.SHB_CHONGZHI);
        startActivity(new Intent(BuyProductActivity.this, WebViewActivity.class).putExtra("WebViewModel", webViewModel));

    }

    /**
     * 请求数据(上海-马上赚钱)
     */
    private void initSHBDataMakemoney() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("amount", money);
        map.put("projectNo", InvesListModel.id);
        map.put("couponUserId", coupon_user_id);
        mShbPresenter.userInvest(map);


    }

    /**
     * 请求数据(易宝-马上赚钱)
     */
    private void initDataMakemoney() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("loanId", InvesListModel.id);
        map.put("investMoney", money);

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        loanPresenter.loadDataInvest(strn);


    }

    /**
     * 请求数据(华兴-马上赚钱)
     */
    private void initDataGhbMakemoney() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("loanId", InvesListModel.id);
        map.put("investMoney", money);
        map.put("couponUserId", coupon_user_id);

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        Log.d("wedwwedwweew", coupon_user_id);

        loanPresenter.loadDataGhbInvest(strn);


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
            loanPresenter.loadDataAccount(strn);
        }
    }


    /**
     * 充值
     */
    public void BuyProductRechargeFragment() {

        Intent it = new Intent(BuyProductActivity.this, MeRechargeActivity.class);
        startActivity(it);
    }

    /**
     * 投资记录
     */
    public void BuyProductRecodFragment() {

        Intent intent = new Intent(BuyProductActivity.this, BuyProducRecodActivity.class);
        intent.putExtra("loanId", model_id);
        startActivity(intent);
    }


    /**
     * 项目风控
     */
    public void BuyProductRiskFragment() {

        Intent intent = new Intent(BuyProductActivity.this, BuyProducRiskActivity.class);
        intent.putExtra("InvesListModel", InvesListModel);
        startActivity(intent);

    }

    /**
     * 项目详情
     */
    public void BuyProductDetailsFragment() {

        Intent intent = new Intent(BuyProductActivity.this, BuyProducDetailsActivity.class);
        intent.putExtra("InvesListModel", InvesListModel);
        startActivity(intent);
    }

    /**
     * 易宝充值跳转
     */
    public void WebViewFragment() {

        YeePayModel payModel = new YeePayModel();
        payModel.title = "投资";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(BuyProductActivity.this, YeePayActivity.class).putExtra("YeePayModel", payModel));
    }

    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         *与StatService.onResume(this)类似;
         */
        if (!Q.isEmpty(userModel.getid())) {
            initDataAccount();

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userModel.getid());
            Gson gson = new Gson();
            String strn = gson.toJson(map);
            mShbPresenter.loadUserInfo(strn);

        }

        StatService.onPageStart(BuyProductActivity.this, "投资页面");//(this);
    }

    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(BuyProductActivity.this, "投资页面");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        userModel.setThirdPayType(YeepayORGhb);//保存状态类型为 易宝或者华兴
        EventBus.getDefault().post(new SHBBuyMakeMoneyCallBack());
        EventBus.getDefault().unregister(this);
        S.remove(this, "couponid");
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        userModel.setThirdPayType(YeepayORGhb);//保存状态类型为 易宝或者华兴

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
//            overridePendingTransition(R.anim.activity_on_in_animation, R.anim.activity_on_out_animation);
        }
        return false;
    }

    //红包选择
    @Subscribe
    public void onEvent(DialogPost dialogPost) {
        coupon_user_id = dialogPost.getString();
        intput = true;
        productTvCoupon.setText("已选" + "  " + String.valueOf(dialogPost.getInt()));


    }

    //红包选择
    @Subscribe
    public void onEvent(String dialogPost) {
        if (dialogPost.equals("zy")) {
            intput = true;
        }
        //风险评估返回键回调监听
        if (dialogPost.equals("riskassessment")) {
            initMakeMoney();
        }

    }


}
