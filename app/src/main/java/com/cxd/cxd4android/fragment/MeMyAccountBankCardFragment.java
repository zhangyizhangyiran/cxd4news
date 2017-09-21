package com.cxd.cxd4android.fragment;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.YeePayActivity;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.model.UnIdentityBaseModel;
import com.cxd.cxd4android.model.YeePayModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyBankCardFragmentPresenter;

import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.google.gson.Gson;
import com.cxd.cxd4android.widget.X5WebView;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeMyAccountBankCardFragment extends BaseFragment implements LoadingView{

    /**
     * 银行卡正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 银行卡左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 银行卡认证状态
     **/
    @Bind(R.id.bankcard_tv_real_type)
     TextView bankcard_tv_real_type;
    /**
     * 银行卡真实姓名
     **/
    @Bind(R.id.bankcard_et_real_name)
     EditText bankcard_et_real_name;
    /**
     * 银行卡银行
     **/
    @Bind(R.id.bankcard_et_card_bank)
     EditText bankcard_et_card_bank;
    /**
     * 银行卡卡号
     **/
    @Bind(R.id.bankcard_et_card_num)
     EditText bankcard_et_card_num;
    /**
     * 银行卡拨打电话
     **/
    @Bind(R.id.bankcard_tv_call_phone)
     TextView bankcard_tv_call_phone;
    /**
     * 易宝绑定银行卡
     **/
    @Bind(R.id.bankcard_wv_bank_card)
    X5WebView bankcard_wv_bank_card;
    /**
     * 绑定银行卡
     **/
    @Bind(R.id.bankcard_rl_bankcard)
     LinearLayout bankcard_rl_bankcard;

    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 易宝绑定银行卡
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

    /**
     * 发送易宝日志网页源码
     **/
    public String html = "";

    /**
     * 发送易宝日志method
     **/
    public String method = "";

    /**
     * 发送易宝日志body
     **/
    public String data = "";

    public String YeePayurl = "";
    public boolean isFirst = false;
    MeMyBankCardFragmentPresenter bankCardFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myaccount_bankcard, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("银行卡");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        bankCardFragmentPresenter = new MeMyBankCardFragmentPresenter(this);


        if (("绑卡成功").equals(userModel.getBankCardStatus()) ||("审核中").equals(userModel.getBankCardStatus())) {//!Q.isEmpty(userModel.getcardNo())
            bankcard_rl_bankcard.setVisibility(View.VISIBLE);
            bankcard_wv_bank_card.setVisibility(View.GONE);

            if (!Q.isEmpty(userModel.getrealname())) {
                bankcard_et_real_name.setHint(userModel.getrealname().substring(0, 1) + "**");
            } else {
                bankcard_et_real_name.setHint("");
            }

            bankcard_et_card_bank.setHint(userModel.getbankName());
            bankcard_et_card_num.setHint(userModel.getcardNo());
            bankcard_et_real_name.setFocusable(false);
            bankcard_et_card_bank.setFocusable(false);
            bankcard_et_card_num.setFocusable(false);
            bankcard_tv_real_type.setText("已认证");
            bankcard_tv_real_type.setBackgroundResource(R.drawable.shape_layout_circle_identity);

            if (("审核中").equals(userModel.getBankCardStatus())) {
                bankcard_tv_call_phone.setText("审核中");
                bankcard_tv_call_phone.setFocusable(false);
                bankcard_tv_call_phone.setClickable(false);
//                bankcard_ll_call_phone.setFocusable(false);
            }

        } /*else {
            bankcard_rl_bankcard.setVisibility(View.GONE);
            bankcard_wv_bank_card.setVisibility(View.VISIBLE);
            //请求
//            initData();
        }*/

    }



    @OnClick( {R.id.Btn_left, R.id.bankcard_tv_call_phone})//,R.id.bankcard_ll_call_phone
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                remove("MeMyAccountBankCardFragment");
                break;

            case R.id.bankcard_tv_call_phone://拨打电话

                doShowDialog("温馨提示", "您确定要解绑银行卡吗?");
                break;
            default:
                break;
        }
    }

//    /**
//     * 请求数据
//     */
//    private void initData() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userId", userModel.getid());
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);
//        MicroRequestParams params = new MicroRequestParams();
//        params.put("value", strn);
//
//        TypeToken<List<IdentityBaseModel>> typeToken = new TypeToken<List<IdentityBaseModel>>() {
//        };
//        FinalFetch<IdentityBaseModel> fetch = new FinalFetch<IdentityBaseModel>(new IFetch<IdentityBaseModel>() {
//            @Override
//            public void onSuccess(List<IdentityBaseModel> datas) {
//                IdentityBaseModel model = datas.get(0);
//                if (Constant.STATUS_SUCCESS.equals(model.status)) {
//                    url = Constant.createAutoSubmitForm(model.result.sign, model.result.reqContent, model.result.actionUrl);
//                    Constant.actionUrl = model.result.actionUrl;
//                    isIntercept = model.result.isIntercept;
//                    callbackUrl = model.result.callbackUrl;
//
//                    //设置数据
////                    setData();
////                    bankcard_wv_bank_card.loadDataWithBaseURL(Constant.WEBBASEAPI, url, "text/html", "utf-8", null);
//
//                    WebViewFragment();
//                } else if (Constant.STATUS_FAILED.equals(model.status)) {
//
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
//        }, params, typeToken, Constant.myaccountc_bindBankCard);
//    }
    /**
     * 请求数据
     */
    private void initData() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userId", userModel.getid());
//        Gson gson = new Gson();
//        String strn = gson.toJson(map);

//        bankCardFragmentPresenter.loadBindBankCard(strn);
    }

    /**
     * 请求数据(解绑银行卡)
     */
    private void initDataUnBind() {
        P.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        bankCardFragmentPresenter.loadUnBindBankCard(strn);
    }


    @Override
    public void getDataSuccess(Object model) {
        P.cancel();
        /*if (model.getClass().equals(BindBankCardBaseModel.class)){
            BindBankCardBaseModel  identityBaseModel= (BindBankCardBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(identityBaseModel.status)) {
                url = Constant.createAutoSubmitForm(identityBaseModel.result.sign, identityBaseModel.result.reqContent, identityBaseModel.result.actionUrl);
                Constant.actionUrl = identityBaseModel.result.actionUrl;
                isIntercept = identityBaseModel.result.isIntercept;
                callbackUrl = identityBaseModel.result.callbackUrl;

                //设置数据
//                setData();
//                bankcard_wv_bank_card.loadDataWithBaseURL(Constant.WEBBASEAPI, url, "text/html", "utf-8", null);
                WebViewFragment();
            } else if (Constant.STATUS_FAILED.equals(identityBaseModel.status)) {

            }
        }else*/ if (model.getClass().equals(UnIdentityBaseModel.class)){
            UnIdentityBaseModel unIdentityBaseModel = (UnIdentityBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(unIdentityBaseModel.status)) {
                T.D("解绑审核中(最长两个工作日,请耐心等待)");
                remove("MeMyAccountBankCardFragment");
            } else if (Constant.STATUS_FAILED.equals(unIdentityBaseModel.status)) {
                T.D("解绑失败");
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        P.cancel();
        Logger.e(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (!Q.isEmpty(userModel.getcardNo())) {
//
//        } else {
//            //易宝返回前请求
//            ReturnYeePayMsg.returnYeePayMsg();
//            //发送易宝app日志
//            SendYeePayMsg.sendYeePayMsg(context, "银行卡", YeePayurl, method, data, "return", html);
//        }

        EventBus.getDefault().post(new IUpdataInfoCallBack(""));
    }
    @Override
    public void showLoading() {

    }
    @Override
    public void hideLoading() {

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
        payModel.title = "银行卡";
        payModel.url = url;
        payModel.isIntercept = isIntercept;
        payModel.callbackUrl = callbackUrl;

        startActivity(new Intent(getActivity(), YeePayActivity.class).putExtra("YeePayModel", payModel));

    }

    /**
     * 易宝绑定银行卡
     */
//    @SuppressLint("JavascriptInterface")
//    public void setData() {
//
//        WebSettings webSettings = bankcard_wv_bank_card.getSettings();
//        webSettings.setSupportZoom(true);
////        webSettings.setDisplayZoomControls(true);
//
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettings.setLoadWithOverviewMode(true);//设置屏幕自适应
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        bankcard_wv_bank_card.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
////        bankcard_wv_bank_card.addJavascriptInterface(new InJavaScripthandler(), "handler");
//
//        bankcard_wv_bank_card.setWebViewClient(new WebViewClient() {
//
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//
//                method = request.getMethod();
//
//                return super.shouldInterceptRequest(view, request);
//
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (("true").equals(isIntercept)) {
//                    if ((callbackUrl).equals(url)) {
//                        return false;
//                    }
//                }
//                view.loadUrl(url);
//                L.I("------shouldOverrideUrlLoading----" + url);
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                view.loadUrl(String.format("javascript:window.handler.showss(document.body.innerHTML);"));
//                super.onPageStarted(view, url, favicon);
//
//
//                if (("true").equals(isIntercept)) {
//                    if ((callbackUrl).equals(url)) {
//                        EventBus.getDefault().post(new IUpdataInfoCallBack(""));
//                        remove("MeMyAccountBankCardFragment");
//                    }
//                }
//
//                L.I("------onPageStarted----" + url);
//
//
//                YeePayurl = url;
//
//                if (url.contains(Constant.fillterStr)) {
//                    if (isFirst) {
//                        L.I("------发送----loading");
//                        //发送易宝app日志
//                        SendYeePayMsg.sendYeePayMsg(context, "银行卡", url, method, data, "loading", html);
//                    } else {
//                        if (Constant.actionUrl.equals(url)) {
//                            L.I("------发送----loading");
//                            //发送易宝app日志
//                            SendYeePayMsg.sendYeePayMsg(context, "银行卡", url, method, data, "loading", html);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//                YeePayurl = url;
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//
//                super.onPageFinished(view, url);
//
//                L.I("------onPageFinished----" + url);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                L.I("-----错误failingUrl-----" + failingUrl);
//                L.I("-----错误description-----" + description);
//            }
//        });
//
//    }

    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private  void doShowDialog(String title, String text) {
        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(true);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initDataUnBind();
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

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "银行卡");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "银行卡");//(this);
    }

//    final class InJavaScriptLocalObj {
//        public void showSource(String htmlA) {
//            L.I("HTML:" + htmlA);
//            html = htmlA;
//            if (YeePayurl.contains(Constant.fillterStr)) {
//                if (isFirst) {
//                    L.I("------发送----complete");
//                    //发送易宝app日志儿童
//                    SendYeePayMsg.sendYeePayMsg(context, "银行卡", YeePayurl, method, data, "complete", html);
//                } else {
//                    if (Constant.actionUrl.equals(YeePayurl)) {
//                        L.I("------发送----complete");
//                        //发送易宝app日志儿童
//                        SendYeePayMsg.sendYeePayMsg(context, "银行卡", YeePayurl, method, data, "complete", html);
//                        isFirst = true;
//                    }
//                }
//            }
//
//        }
//    }

//    final class InJavaScripthandler {
//        @JavascriptInterface
//        public void showss(String html) {
//            L.I("HTML:body:" + html);
//            data = html;
//            if (bodyBegin) {
//                //发送易宝app日志
//                SendYeePayMsg.sendYeePayMsg(context, "银行卡", sendYeePayUrl, method, data, "loading", html);
//                onPageStartedOK = true;
//            }
//
//        }
//    }
}
