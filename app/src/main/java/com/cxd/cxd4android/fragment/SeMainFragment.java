package com.cxd.cxd4android.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.activity.subactivity.SeFeedBackActivity;
import com.cxd.cxd4android.activity.subactivity.SeHelpCenterActivity;
import com.cxd.cxd4android.activity.subactivity.SeNewNoticeActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.NoticeModel;
import com.cxd.cxd4android.model.ServiceBaseModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.SeMainFragmentPresenter;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.google.gson.Gson;
import com.micros.utils.S;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 */
public class SeMainFragment extends BaseFragment implements LoadingView {


    /**
     * 服务正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;


    /**
     * 服务理财保证金
     **/
    @Bind(R.id.se_tv_financial_bond)
    TextView se_tv_financial_bond;
    /**
     * 服务坏账/逾期
     **/
    @Bind(R.id.se_tv_be_overdue)
    TextView se_tv_be_overdue;

    /**
     * 服务刷新
     **/
    @Bind(R.id.semain_sr_swipe_refresh)
    SwipeRefreshLayout semain_sr_swipe_refresh;
    SeMainFragmentPresenter seMainFragmentPresenter;
    /**
     * 累计交易金额
     */
    @Bind(R.id.se_tv_total_money)
    TextView mSeTvTotalMoney;
    /**
     * 用户赚取金额
     */
    @Bind(R.id.se_tv_total_yonghu_money)
    TextView mSeTvTotalYonghuMoney;
    /**
     * 最新公告
     */
    @Bind(R.id.se_ll_new)
    TextView mSeLlNew;
    /**
     * 还款通知
     */
    @Bind(R.id.se_ll_repayments)
    TextView mSeLlRepayments;

    @Bind(R.id.myaccount_tv_bind_phone_news)
    TextView mMyaccountTvBindPhoneNew;
    private ServiceBaseModel mServiceBaseModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_se_mains, container, false);
            return contentView;
        }
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("服务");
        seMainFragmentPresenter = new SeMainFragmentPresenter(this);
        initData();
        //设置监听
        setListener();
        LocalUserModel localUserModel = new LocalUserModel();
        mMyaccountTvBindPhoneNew.setText("版本号" + "V" + localUserModel.getAppVersionName());


    }


    /**
     * 设置监听
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setListener() {
        semain_sr_swipe_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        semain_sr_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();

            }
        });
    }

    //点击
    @OnClick({R.id.se_ll_new_notice, R.id.se_ll_payment_notice, R.id.se_ll_about_us, R.id.se_ll_help_center, R.id.se_ll_give_score, R.id.se_ll_feed_back, R.id.se_ll_essential_information, R.id.se_ll_administer_information, R.id.se_ll_platform_information, R.id.se_ll_matter_information, R.id.se_ll_Person_information, R.id.se_ll_Legislation_information, R.id.se_ll_help_Guide})
//,
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.se_ll_new_notice://最新公告
                SeNewNoticeFragment();

                StatService.onEvent(getContext(), BaiDustatistic.newnotice, "", 1);//事件统计
                break;
            case R.id.se_ll_payment_notice://还款公告
                SePaymentNoticeFragment();
                StatService.onEvent(getContext(), BaiDustatistic.repaynotice, "", 1);//事件统计
                break;
            case R.id.se_ll_about_us://关于我们
                SeAboutUsFragment();
                StatService.onEvent(getContext(), BaiDustatistic.aboutus, "", 1);//事件统计
                break;
            case R.id.se_ll_help_center://帮助中心
                SeHelpCenterFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter, "", 1);//事件统计
                break;
            case R.id.se_ll_give_score://给我评分(服务电话)
                doShowDialog("温馨提示", "您确定要拨打服务电话吗?");
                break;
            case R.id.se_ll_feed_back://意见反馈
                SeFeedBackFragment();
                break;
            //基本信息
            case R.id.se_ll_essential_information:
                setEssentialinformation();
                break;
            //治理信息
            case R.id.se_ll_administer_information:
                setAdministerInrmation();
                break;
            //平台报告
            case R.id.se_ll_platform_information:
                setPlatformInformation();
                break;
            //重大事项
            case R.id.se_ll_matter_information:
                setMatterInformation();
                break;
            //网贷知识
            case R.id.se_ll_Person_information:
                setPersonInformation();
                break;
            //政策法规
            case R.id.se_ll_Legislation_information:
                setLegislationLnformation();
                break;
            //新手指南
            case R.id.se_ll_help_Guide:
                setHelpGuide();
                break;
            default:

                break;
        }
    }


    private void setPersonInformation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "网贷知识";
        bannerModel.url = Constant.Legislation_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    private void setLegislationLnformation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "政策法规";
        bannerModel.url = Constant.Person_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));

    }

    private void setMatterInformation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "重大事项";
        bannerModel.url = Constant.matter_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));

    }

    private void setAdministerInrmation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "治理信息";
        bannerModel.url = Constant.administer_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));

    }

    private void setHelpGuide() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "新手指南";
        bannerModel.url = Constant.se_ll_help_Guide;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    private void setPlatformInformation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "平台报告";
        bannerModel.url = Constant.platform_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    private void setEssentialinformation() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "基本信息";
        bannerModel.url = Constant.essential_information;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        seMainFragmentPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        stopRefresh(semain_sr_swipe_refresh);
        mServiceBaseModel = (ServiceBaseModel) model;
        if (Constant.STATUS_SUCCESS.equals(mServiceBaseModel.status)) {
            setData(mServiceBaseModel);
        }
    }

    /**
     * 填充数据
     */
    private void setData(ServiceBaseModel serviceBaseModel) {
        se_tv_financial_bond.setText(serviceBaseModel.result.guaranteeMoney);
        se_tv_be_overdue.setText(serviceBaseModel.result.failBill + "/" + serviceBaseModel.result.overdueBill);
        mSeTvTotalMoney.setText(serviceBaseModel.result.totalMoneyApp);
        mSeTvTotalYonghuMoney.setText(serviceBaseModel.result.totalProfitApp);
        /**
         * 判断红点显示逻辑
         */
        Long lastTimeRepay = (Long) S.get(getActivity(), "lastTimeRepay", Long.parseLong("0"));
        Long lastTimeNews = (Long) S.get(getActivity(), "lastTimeNews", Long.parseLong("0"));
        long initNumber = Long.parseLong("0");
        if (serviceBaseModel.result.lastTimeRepay > lastTimeRepay || lastTimeRepay == initNumber) {
            mSeLlRepayments.setVisibility(View.VISIBLE);
        }

        if (serviceBaseModel.result.lastTimeNews > lastTimeNews || lastTimeNews == initNumber) {
            mSeLlNew.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getDataFail(String msg) {
        stopRefresh(semain_sr_swipe_refresh);
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        dialog.setCancelable(true);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008005098"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
     * 服务意见反馈
     */
    public void SeFeedBackFragment() {

        Intent it = new Intent(getActivity(), SeFeedBackActivity.class);
        startActivity(it);
    }


    /**
     * 服务帮助中心
     */
    public void SeHelpCenterFragment() {

        Intent it = new Intent(getActivity(), SeHelpCenterActivity.class);
        startActivity(it);
    }

    /**
     * 服务关于我们
     */
    public void SeAboutUsFragment() {
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "关于我们";
        bannerModel.url = Constant.help_about_us;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));

    }

    /**
     * 服务最新公告
     */
    public void SeNewNoticeFragment() {
        setStatusNew();
        NoticeModel model = new NoticeModel();
        model.title = "newNotice";

        Intent it = new Intent(getActivity(), SeNewNoticeActivity.class);
        it.putExtra("noticeModel", model);
        startActivity(it);
    }

    private void setStatusNew() {
        if (mServiceBaseModel != null) {
            if (mServiceBaseModel.result.lastTimeNews != null) {
                Long lastTimeNews = mServiceBaseModel.result.lastTimeNews;
                S.put(getActivity(), "lastTimeNews", lastTimeNews);
                mSeLlNew.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 服务还款公告
     */
    public void SePaymentNoticeFragment() {
        setRepayments();
        NoticeModel model = new NoticeModel();
        model.title = "paymentNotice";

        Intent it = new Intent(getActivity(), SeNewNoticeActivity.class);
        it.putExtra("noticeModel", model);
        startActivity(it);
    }

    private void setRepayments() {
        if (mServiceBaseModel != null) {
            if (mServiceBaseModel.result.lastTimeRepay != null) {
                Long lastTimeRepay = mServiceBaseModel.result.lastTimeRepay;
                S.put(getActivity(), "lastTimeRepay", lastTimeRepay);
                mSeLlRepayments.setVisibility(View.INVISIBLE);

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "服务页面");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "服务页面");//(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
