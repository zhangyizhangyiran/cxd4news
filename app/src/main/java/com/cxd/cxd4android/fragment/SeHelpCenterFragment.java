package com.cxd.cxd4android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.BannerModel;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */

public class SeHelpCenterFragment extends BaseFragment {


    /** 帮助中心正中间标题 **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /** 帮助中心左上角返回键 **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_se_helpcenter, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("帮助中心");
        Btn_left.setVisibility(View.VISIBLE);
    }

    //点击
    @OnClick( {R.id.Btn_left,R.id.helpcenter_ll_newer_guide,R.id.helpcenter_ll_tariffde_scription,R.id.helpcenter_ll_login_register,R.id.helpcenter_ll_change_withdraw,R.id.helpcenter_ll_inves_record,R.id.helpcenter_ll_points_rule,R.id.helpcenter_ll_debt_rule})
     void onClick(View view) {
        switch (view.getId()){
            case R.id.Btn_left://返回
                getActivity().finish();
                break;
            case R.id.helpcenter_ll_newer_guide://新手指引
                SeHelpCenterNewerGuideFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_newer, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_tariffde_scription://(投资问题)资费说明
                SeHelpCenterTraiScriptionFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_invest, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_login_register://登录/注册
                SeHelpCenterLoginRegisterFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_loginregister, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_change_withdraw://充值/提现
                SeHelpCenterChangeWithdrawFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_rechangewidthraw, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_inves_record://(安全问题)投资/交易记录
                SeHelpCenterInvesRecordFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_safe, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_points_rule://(积分规则)了解投资产品
                SeHelpCenterUndersProductFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_integralrule, "", 1);//事件统计
                break;
            case R.id.helpcenter_ll_debt_rule://债权转让
                SeHelpCenterDebtTransferFragment();
                StatService.onEvent(getContext(), BaiDustatistic.helpcenter_transferrules, "", 1);//事件统计
                break;
            default:
                break;
        }
    }
    /**
     * 新手指引
     */
    public void SeHelpCenterNewerGuideFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "新手指引";
        bannerModel.url = Constant.help_new_user;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * (投资问题)资费说明
     */
    public void SeHelpCenterTraiScriptionFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "投资问题";
        bannerModel.url = Constant.help_invest;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * 登录/注册
     */
    public void SeHelpCenterLoginRegisterFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "登录/注册";
        bannerModel.url = Constant.help_reg;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * 充值/提现
     */
    public void SeHelpCenterChangeWithdrawFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "充值/提现";
        bannerModel.url =  Constant.help_withdraw;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * (安全问题)投资/交易记录
     */
    public void SeHelpCenterInvesRecordFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "安全问题";
        bannerModel.url = Constant.help_safe;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * 积分规则
     */
    public void SeHelpCenterUndersProductFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "积分规则";
        bannerModel.url =  Constant.integral_rules;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }
    /**
     * 债权转让规则
     */
    public void SeHelpCenterDebtTransferFragment(){
        BannerModel bannerModel = new BannerModel();
        bannerModel.title = "债权转让规则";
        bannerModel.url =  Constant.integral_rules;
        bannerModel.location = "";
        bannerModel.imgRootUrl = "";

        startActivity(new Intent(getActivity(), BoutBannerActivity.class).putExtra("BannerModel", bannerModel));
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "帮助中心");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "帮助中心");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
