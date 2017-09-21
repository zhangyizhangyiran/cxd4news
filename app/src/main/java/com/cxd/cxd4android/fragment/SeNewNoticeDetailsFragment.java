package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.NewNoticeBaseModel;
import com.cxd.cxd4android.model.NewNoticeListModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.SeNewNoticeFragmentPresenter;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.cxd.cxd4android.widget.X5WebView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * ClassName:公告详情页面（最新，还款）
 * Description：
 * Author：XiaoFa
 * Date：2016/3/22 13:40
 * version：V1.0
 */

public class SeNewNoticeDetailsFragment extends BaseFragment implements LoadingView {


    private String WEBVIEW_CONTENT = "<html><head></head><body style=\"background-color:#FFFFFF;line-height:24px;letter-spacing:0px;text-align:justify;font-size:15px;color:#323232;text-indent:2em;\">%s</body></html>";
    private String webView_style = "<div style='background-color: #ffffff;margin-top:2px;color:#323232;text-align:justify;font-size:15px;'><h3>%s</h3><p style='margin-top: -15px;'>%s</p></div>%s";
    /**
     * 活动详情正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 活动详情左上角返回键
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;
    /**
     * 活动详情活动详情
     **/
    @Bind(R.id.details_wb_newnotice_details)
    X5WebView details_wb_newnotice_details;

    private String noticeId, notice, page;
    /**
     * x详情列表数据
     **/
    List<NewNoticeListModel> list = new ArrayList<NewNoticeListModel>();
    SeNewNoticeFragmentPresenter noticeFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_se_newnotice_details, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeFragmentPresenter = new SeNewNoticeFragmentPresenter(this);
        noticeId = getArguments().getString("noticeId");
        notice = getArguments().getString("notice");
        page = getArguments().getString("page");
        Logger.i("page===page2==" + page);
        //q请求通知详情页数据，使用noticeID区分每条信息
        initData();

        if (!Q.isEmpty(noticeId)) {
            if ("newNotice".equals(notice)) {
                tv_title.setText("最新公告详情");
            } else if ("paymentNotice".equals(notice)) {
                tv_title.setText("还款通知详情");
            } else {
                tv_title.setText(notice);
            }
        }
        Btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 请求数据
     */
    private void initData() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("noticeId", noticeId);//每页条数
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        noticeFragmentPresenter.loadData(strn);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void getDataSuccess(Object model) {
        NewNoticeBaseModel newNoticeModel = (NewNoticeBaseModel) model;

        if (Constant.STATUS_SUCCESS.equals(newNoticeModel.status)) {
            list = newNoticeModel.result;
            if (list.size() > 0) {
                details_wb_newnotice_details.getSettings().setUseWideViewPort(false);
                details_wb_newnotice_details.loadData(String.format(webView_style, list.get(0).title, list.get(0).create_time, list.get(0).body) + " ", "text/html; charset=UTF-8", null);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    //点击
    @OnClick(R.id.Btn_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                Logger.i("page=====" + page);

                if (details_wb_newnotice_details.canGoBack()) {
                    details_wb_newnotice_details.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    if ("notice".equals(page)) {
                        getActivity().finish();//结束退出程序
                    } else {
                        remove("SeNewNoticeDetailsFragment");
                    }
                }
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
        StatService.onPageStart(getActivity(), "公告详情");//(this);
        remove("SeNewNoticeDetailsFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "公告详情");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
