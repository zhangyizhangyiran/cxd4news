//package com.cxd.cxd4android.activity.subactivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.baidu.mobstat.StatService;
//import com.cxd.cxd4android.R;
//import com.cxd.cxd4android.activity.BuyProducDetailsActivity;
//import com.cxd.cxd4android.activity.BuyProducRecodActivity;
//import com.cxd.cxd4android.activity.BuyProducRiskActivity;
//import com.cxd.cxd4android.activity.CheckoutGestureLockActivity;
//import com.cxd.cxd4android.activity.MainActivity;
//import com.cxd.cxd4android.activity.YeePayActivity;
//import com.cxd.cxd4android.fragment.MeMyInvesContractFragment;
//import com.cxd.cxd4android.fragment.MeMyInvesRepayPlanFragment;
//import com.cxd.cxd4android.fragment.MeMySaveVoucherFragment;
//import com.cxd.cxd4android.global.BaseActivity;
//import com.cxd.cxd4android.global.BaseApplication;
//import com.cxd.cxd4android.global.Constant;
//import com.cxd.cxd4android.interfaces.IBuyProductCheckCallBack;
//import com.cxd.cxd4android.interfaces.IBuyProductJumpLoginCallBack;
//import com.cxd.cxd4android.interfaces.IBuyProductLockCallBack;
//import com.cxd.cxd4android.interfaces.IBuyProductMakeMoneyCallBack;
//import com.cxd.cxd4android.interfaces.ILoginCallBack;
//import com.cxd.cxd4android.interfaces.IUpdataRechargeCallBack;
//import com.cxd.cxd4android.model.AccountBalanceBaseModel;
//import com.cxd.cxd4android.model.IdentityBaseModel;
//import com.cxd.cxd4android.model.NewerBaseModel;
//import com.cxd.cxd4android.model.UserInvestsModel;
//import com.cxd.cxd4android.model.YeePayModel;
//import com.cxd.cxd4android.presenter.BuyProductLoanPresenter;
//import com.cxd.cxd4android.presenter.LoadingView;
//import com.cxd.cxd4android.widget.dialog.PromptDialog;
//import com.google.gson.Gson;
//import com.micros.utils.Q;
//import com.orhanobut.logger.Logger;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//
///**
// * 我的转让详情页面
// * Created by Administrator on 16-7-20.
// */
//public class MeMyTransferDetailsActivity extends BaseActivity implements LoadingView {
//    /**
//     * 购买页面信息
//     */
//    UserInvestsModel InvesListModel;
//    /**
//     * 购买钱数
//     */
//    private String money = "";
//    /**
//     * 易宝跳转购买
//     **/
//    private String url = "";
//    /**
//     * 易宝是否回调
//     **/
//    private String isIntercept = "";
//    /**
//     * 易宝回调链接
//     **/
//    private String callbackUrl = "";
//
//    boolean input = false;
//
//    /**
//     * 购买正中间标题
//     **/
//    @Bind(R.id.tv_title)
//    TextView tv_title;
//    /**
//     * 购买左上角箭头
//     **/
//    @Bind(R.id.Btn_left)
//    TextView Btn_left;
//
//    /**
//     * 转让标名称
//     **/
//    @Bind(R.id.my_debt_details_housing_mortgage)
//    TextView my_debt_details_housing_mortgage;
//    /**
//     * 转让年化率
//     **/
//    @Bind(R.id.my_debt_details_rate_num)
//    TextView my_debt_details_rate_num;
//
//    /**
//     * 转让项目详情
//     **/
//    @Bind(R.id.my_debt_details_prouduct_details)
//    TextView my_debt_details_prouduct_details;
//    /**
//     * 转让项目风控
//     **/
//    @Bind(R.id.my_debt_details_prouduct_risk)
//    TextView my_debt_details_prouduct_risk;
//    /**
//     * 转让借款合同
//     **/
//    @Bind(R.id.my_debbt_details_loan_agreement)
//    TextView my_debbt_details_loan_agreement;
//    /**
//     * 转让保全凭证
//     **/
//    @Bind(R.id.my_debbt_details_save_voucher)
//    TextView my_debbt_details_save_voucher;
//    /**
//     * 转让投资记录
//     **/
//    @Bind(R.id.my_debt_details_inves_recod)
//    TextView my_debt_details_inves_recod;
//    /**
//     * 转让还款计划
//     **/
//    @Bind(R.id.my_debt_details_repayment_plan)
//    TextView my_debt_details_repayment_plan;
////    /**
////     * 转让 转让价格
////     **/
////    @Bind(R.id.debt_details_transfer_price)
////     TextView debt_details_transfer_price;
////    /**
////     * 转让 债权价值
////     **/
////    @Bind(R.id.debt_details_creditor_value)
////     TextView debt_details_creditor_value;
////    /**
////     * 转让 债权本金
////     **/
////    @Bind(R.id.debt_details_creditor_principal)
////     TextView debt_details_creditor_principal;
////    /**
////     * 转让 折让金
////     **/
////    @Bind(R.id.debt_details_discount_gold)
////     TextView debt_details_discount_gold;
////    /**
////     * 转让 剩余天数
////     **/
////    @Bind(R.id.debt_details_remain_day)
////     TextView debt_details_remain_day;
////    /**
////     * 转让账户余额
////     **/
////    @Bind(R.id.debt_details_account_balance)
////    TextView debt_details_account_balance;
////    /**
////     * 转让充值
////     **/
////    @Bind(R.id.debt_details_account_rechange)
////     TextView debt_details_account_rechange;
//
//    /**
//     * 转让余额不足
//     **/
////    @Bind(R.id.debt_details_not_fund)
////    TextView debt_details_not_fund;
//
//    /**
//     * 购买马上赚钱
//     **/
//    @Bind(R.id.mytransfer_details_submit_makemoney)
//    Button mytransfer_details_submit_makemoney;
//
//
//    private BuyProductLoanPresenter loanPresenter;
//
//    Double cardinalNumbers = 0.0;
//    String model_id = "";
//    private MeMyInvesRepayPlanFragment MeMyInvesRepayPlanFragment;
//    private MeMyInvesContractFragment MeMyInvesContractFragment;
//    private MeMySaveVoucherFragment MeMySaveVoucherFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_layout_memytransfer_details);
//        EventBus.getDefault().register(this);
//        ButterKnife.bind(this);
//        loanPresenter = new BuyProductLoanPresenter(MeMyTransferDetailsActivity.this);
//
//        tv_title.setText("购买");
//        Btn_left.setVisibility(View.VISIBLE);
//
//        InvesListModel = (UserInvestsModel) getIntent().getSerializableExtra("MeMyTransferDetails");
//
//        if (InvesListModel != null) {
//            //请求数据
//            initData();
//            cardinalNumbers = Double.valueOf(InvesListModel.cardinalNumber);
//            model_id = InvesListModel.id;
//        }
//    }
//
//    //请求数据
//    private void initData() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("loanId", InvesListModel.id);//
//
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//
//        loanPresenter.loadData(strn);
//    }
//
//    @Override
//    public void getDataSuccess(Object model) {
//        if (model.getClass().equals(NewerBaseModel.class)) {
//            NewerBaseModel newerBaseModel = (NewerBaseModel) model;
//            if (Constant.STATUS_SUCCESS.equals(newerBaseModel.status)) {
//
//
//            } else if (Constant.STATUS_FAILED.equals(newerBaseModel.status)) {
//
//            }
//        } else if (model.getClass().equals(AccountBalanceBaseModel.class)) {
//            AccountBalanceBaseModel accountModel = (AccountBalanceBaseModel) model;
//            if (Constant.STATUS_SUCCESS.equals(accountModel.status)) {
//                userModel.setbalanceAvaliable(accountModel.result.balanceAvaliable);
//
//            } else if (Constant.STATUS_FAILED.equals(accountModel.status)) {
//
//            }
//        } else if (model.getClass().equals(IdentityBaseModel.class)) {
//            IdentityBaseModel identityBaseModel = (IdentityBaseModel) model;
//            P.cancel();
//            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {
//
//                //在此接口回调,刷新我的账户
//                EventBus.getDefault().post(new IBuyProductMakeMoneyCallBack());
//
//                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
//                Constant.actionUrl = identityBaseModel.result.actionUrl;
//                isIntercept = identityBaseModel.result.isIntercept;
//                callbackUrl = identityBaseModel.result.callbackUrl;
//                WebViewFragment();
//            } else {
//
//                T.D("" + identityBaseModel.msg);
//            }
//        }
//    }
//
//    @Override
//    public void getDataFail(String msg) {
//        Logger.e(msg);
//        P.cancel();
//    }
//
//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    /**
//     * 事件响应方法,请求账户信息
//     */
//    @Subscribe
//    public void onEvent(IUpdataRechargeCallBack event) {
//        //请求账户信息,更新余额
//        initDataAccount();
//
//    }
//
//    /**
//     * 事件响应方法,购买验证回调//充值和马上赚钱验证成功返回
//     */
//    @Subscribe
//    public void onEvent(IBuyProductCheckCallBack event) {
//
//        if (("BuyProductActivityRechange").equals(event.getBuyProductCheck())) {
//            BuyProductRechargeFragment();
//            EventBus.getDefault().post(new IBuyProductLockCallBack());
//        } else if (("BuyProductActivityMakemoney").equals(event.getBuyProductCheck())) {
//            //请求数据
//            initDataMakemoney();
//            EventBus.getDefault().post(new IBuyProductLockCallBack());
//        } else if (("BuyProductActivityPsw").equals(event.getBuyProductCheck())) {
//            EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityPsw"));
//            EventBus.getDefault().post(new ILoginCallBack(4));
//            String getid = userModel.getid();
//
//            userModel.clear();
//            userModel.setid(getid);
//            finish();
//        } else if (("BuyProductActivityAccount").equals(event.getBuyProductCheck())) {
//            EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityAccount"));
//            EventBus.getDefault().post(new ILoginCallBack(4));
//            userModel.clear();
//            finish();
//        }
//    }
//
//
//    @OnClick({R.id.Btn_left, R.id.my_debt_details_prouduct_details, R.id.my_debt_details_prouduct_risk, R.id.my_debt_details_inves_recod, R.id.mytransfer_details_submit_makemoney,R.id.my_debt_details_repayment_plan,R.id.my_debbt_details_loan_agreement,R.id.my_debbt_details_save_voucher})
//    void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.Btn_left://返回
//                finish();
////                overridePendingTransition(R.anim.activity_on_in_animation, R.anim.activity_on_out_animation);
//                break;
//            case R.id.my_debt_details_prouduct_details://项目详情
//                BuyProductDetailsFragment();
//                break;
//            case R.id.my_debt_details_prouduct_risk://项目风控
//                BuyProductRiskFragment();
//                break;
//            case R.id.my_debt_details_inves_recod://投资记录
//                BuyProductRecodFragment();
//                break;
//            case R.id.my_debt_details_repayment_plan://还款计划
//                MeMyInvesRepayPlanFragment();
//                break;
//            case R.id.my_debbt_details_loan_agreement://借款合同
//                MeMyInvesContractFragment();
//                break;
//            case R.id.my_debbt_details_save_voucher://保全凭证
//                MeMySaveVoucherFragment();
//                break;
//
//            case R.id.mytransfer_details_submit_makemoney://立即购买
//
//                if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {//已登录
//                    if ("raising".equals(InvesListModel.status)) {
//                        if (Q.isEmpty(money)) {
//                            T.D("请输入投资金额");
//                        }
//                        if (input) {
//                            if (("yes").equals(userModel.getGestrueIsFirst())) {
////                                BuyProductRechargeFragment();
//                                initDataMakemoney();
//                            } else if (("ON").equals(userModel.getGestrueSwitch())) {
//                                Intent intents = new Intent(MeMyTransferDetailsActivity.this, CheckoutGestureLockActivity.class);
//                                intents.putExtra("KEY", "BuyProductActivityMakemoney");
//                                startActivity(intents);
//                            } else {
//                                //请求数据
//                                initDataMakemoney();
//                            }
//
//                        }
//
//                    } else {
//                        T.D("项目处于不可投状态");
//                    }
//                } else {//未登录
//                    doShowDialog("温馨提示", "您处于未登录状态,请先登录");
//                }
//
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 弹出对话框
//     *
//     * @param
//     * @param
//     */
//    private void doShowDialog(String title, String text) {
//        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
//        dialog.title = title;
//        dialog.content = text;
//        dialog.setCancelable(false);
//        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                Constant.INTENT_JUMP = "MainActivityTest";
//                Intent intent = new Intent(MeMyTransferDetailsActivity.this, MainActivity.class);
//                startActivity(intent);
//                try {
//                    EventBus.getDefault().post(new ILoginCallBack(4));
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                dialog.dismiss();
//            }
//        });
//        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.dialog_imageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    /**
//     * 请求数据(马上赚钱)
//     */
//    private void initDataMakemoney() {
//        P.show();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userId", userModel.getid());
//        map.put("loanId", InvesListModel.id);
//        map.put("investMoney", money);
//
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//
//        loanPresenter.loadDataInvest(strn);
//
//
//    }
//
//    /**
//     * 请求数据(账户信息)
//     */
//    private void initDataAccount() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userId", userModel.getid());
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//
//        loanPresenter.loadDataAccount(strn);
//    }
//
//
//    /**
//     * 借款合同
//     */
//    public void MeMyInvesContractFragment() {
//
////        if (MeMyInvesContractFragment == null) {
//        MeMyInvesContractFragment = new MeMyInvesContractFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("UserInvestsModel", InvesListModel);
//        MeMyInvesContractFragment.setArguments(bundle);
////        }
////        replace(MeMyInvesContractFragment);
//
////        replace(R.id.main_fr_main_me,MeMyInvesContractFragment,"MeMyInvesContractFragment");
//
//        add(R.id.fragment_myinves, MeMyInvesContractFragment, "MeMyInvesContractFragment", null);
//    }
//
//    /**
//     * 保全凭证
//     */
//    public void MeMySaveVoucherFragment() {
//
////        if (MeMyInvesContractFragment == null) {
//        MeMySaveVoucherFragment = new MeMySaveVoucherFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("AnCunModel", InvesListModel);
//        MeMySaveVoucherFragment.setArguments(bundle);
////        }
////        replace(MeMyInvesContractFragment);
//
////        replace(R.id.main_fr_main_me,MeMyInvesContractFragment,"MeMyInvesContractFragment");
//
//        add(R.id.fragment_myinves, MeMySaveVoucherFragment, "MeMySaveVoucherFragment", null);
//    }
//
//    /**
//     * 还款计划
//     */
//    public void MeMyInvesRepayPlanFragment() {
//
////        if (MeMyInvesRepayPlanFragment == null) {
//        MeMyInvesRepayPlanFragment = new MeMyInvesRepayPlanFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("loanId", InvesListModel.id);//model.id
//        MeMyInvesRepayPlanFragment.setArguments(bundle);
////        }
////        replace(MeMyInvesRepayPlanFragment);
//        add(R.id.fragment_myinves, MeMyInvesRepayPlanFragment, "MeMyInvesRepayPlanFragment", null);
//
//    }
//
//    /**
//     * 充值
//     */
//    public void BuyProductRechargeFragment() {
//
//        Intent it = new Intent(MeMyTransferDetailsActivity.this, MeRechargeActivity.class);
//        startActivity(it);
//    }
//
//    /**
//     * 投资记录
//     */
//    public void BuyProductRecodFragment() {
//
//        Intent intent = new Intent(MeMyTransferDetailsActivity.this, BuyProducRecodActivity.class);
//        intent.putExtra("loanId", model_id);
//        startActivity(intent);
//    }
//
//
//    /**
//     * 项目风控
//     */
//    public void BuyProductRiskFragment() {
//
//        Intent intent = new Intent(MeMyTransferDetailsActivity.this, BuyProducRiskActivity.class);
//        intent.putExtra("InvesListModel", InvesListModel);
//        startActivity(intent);
//
//    }
//
//    /**
//     * 项目详情
//     */
//    public void BuyProductDetailsFragment() {
//
//        Intent intent = new Intent(MeMyTransferDetailsActivity.this, BuyProducDetailsActivity.class);
//        intent.putExtra("InvesListModel", InvesListModel);
//        startActivity(intent);
//    }
//
//    /**
//     * 易宝充值跳转
//     */
//    public void WebViewFragment() {
//
//        YeePayModel payModel = new YeePayModel();
//        payModel.title = "投资";
//        payModel.url = url;
//        payModel.isIntercept = isIntercept;
//        payModel.callbackUrl = callbackUrl;
//
//        startActivity(new Intent(MeMyTransferDetailsActivity.this, YeePayActivity.class).putExtra("YeePayModel", payModel));
//    }
//
//
//    public void onResume() {
//        super.onResume();
//
//        /**
//         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
//         * 与StatService.onResume(this)类似;
//         */
//        StatService.onPageStart(MeMyTransferDetailsActivity.this, "转让详情页面");//(this);
//    }
//
//    public void onPause() {
//        super.onPause();
//
//        /**
//         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
//         * 与StatService.onPause(this)类似;
//         */
//        StatService.onPageEnd(MeMyTransferDetailsActivity.this, "转让详情页面");//(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    /**
//     * 菜单、返回键响应
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
////            overridePendingTransition(R.anim.activity_on_in_animation, R.anim.activity_on_out_animation);
//        }
//        return false;
//    }
//}
