package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.UserInvestsModel;
import com.cxd.cxd4android.widget.X5WebView;
import com.tencent.smtt.sdk.WebSettings;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * ClassName:保全凭证
 * Description：
 * Author：XiaoFa
 * Date：2016/5/6 16:20
 * version：V1.0
 */
public class MeMySaveVoucherFragment extends BaseFragment {


    /**
     * 保全凭证正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 保全凭证左上角返回键
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 保全凭证webview
     **/
    @Bind(R.id.contract_wb_loan_contract)
    X5WebView contract_wb_loan_contract;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    private UserInvestsModel UserInvestsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myinves_contract, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("保全凭证");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();

        UserInvestsModel = (UserInvestsModel) getArguments().getSerializable("AnCunModel");


        //请求数据
//        initData();
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);

        WebSettings webSettings = contract_wb_loan_contract.getSettings();
        webSettings.setUseWideViewPort(true);//设置适应手机屏幕
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true);//设置支持js交互
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//默认不使用缓存！

        //判段url中是否包含 ‘？’
        String webUrl = UserInvestsModel.anCunUrl;
        if (webUrl.contains("?")) {
//            webUrl =webUrl + "&token="+userModel.getHeader();
            webUrl = webUrl + "";
        } else {
//            webUrl =webUrl + "?token="+userModel.getHeader();
            webUrl = webUrl + "";
        }
        contract_wb_loan_contract.loadUrl(webUrl);
    }

    //点击
    @OnClick({R.id.Btn_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                remove("MeMySaveVoucherFragment");
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "保全凭证");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "保全凭证");//(this);
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
