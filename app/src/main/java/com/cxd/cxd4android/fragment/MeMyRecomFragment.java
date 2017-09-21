package com.cxd.cxd4android.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.MeMyRecomSimpleAdapter;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.RecomBaseModel;
import com.cxd.cxd4android.model.RecomListModel;
import com.cxd.cxd4android.model.RecomObjectModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeMyRecomFragmentPresenter;
import com.cxd.cxd4android.widget.share.CopyUtils;
import com.cxd.cxd4android.widget.share.ShareContentCustomizeDemo;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;
import com.micros.utils.M;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeMyRecomFragment extends BaseFragment implements LoadingView {


    /**
     * 我的推荐正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 我的推荐左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView Btn_left;

    /**
     * 我的推荐立即推荐
     **/
    @Bind(R.id.merecom_bt_recom_now)
    Button merecom_bt_recom_now;

    /**
     * 推荐列表刷新
     **/
    @Bind(R.id.myrecom_sr_fraswipe_refresh)
    SwipeRefreshLayout myrecom_sr_fraswipe_refresh;
    /**
     * 推荐列表控件
     **/
    @Bind(R.id.myrecom_rv_recycler_view)
    RecyclerView myrecom_rv_recycler_view;
    /**
     * webview展示控件
     **/
    @Bind(R.id.myrecom_rv_webview_view)
    WebView mMyrecomRvWebviewView;


    private MeMyRecomSimpleAdapter mAdapter;
    /**
     * 推荐列表数据
     **/
    List<RecomListModel> list = new ArrayList<RecomListModel>();
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    RecomObjectModel rPathInfo;
    /**
     * 第几页
     **/
    private int currentPage = 1;

    /**
     * 数据类型
     **/
    private String DATATYPE = "REQUEST";


    public String url = "";
    public String contents = "";
    public String sharePath = "";
    public String bannerPah = "";
    public String title = "";
    public String bannerTitle = "";

    MeMyRecomFragmentPresenter recomFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_myrecom, container, false);
            return contentView;
        }
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("邀请好友");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        recomFragmentPresenter = new MeMyRecomFragmentPresenter(this);
        //开启刷新
        startRefresh(myrecom_sr_fraswipe_refresh);
        //请求数据
        initData();
        //设置监听
        setListener();
        //h5展示
        mMyrecomRvWebviewView.loadUrl(Constant.HShow);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MeMyRecomSimpleAdapter(getActivity());
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        myrecom_rv_recycler_view.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myrecom_rv_recycler_view.setLayoutManager(linearLayoutManager);
        //设置加载圈圈的颜色(设置四种颜色)

        myrecom_sr_fraswipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DATATYPE = "REFRESH";
                currentPage = 1;
                initData();
            }
        });


        myrecom_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                currentPage++;
                initData();
            }
        });
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("pageSize", "50");//每页条数
        map.put("currentPage", currentPage + "");//第几页

        /**
         * recharge_success   	充值成功
         * invest_success 		投资成功
         * withdraw_success 	体现成功
         * admin_operation 		管理员干预
         * repay_success		还款
         */
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        recomFragmentPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        stopRefresh(myrecom_sr_fraswipe_refresh);
        RecomBaseModel recomBaseModel = (RecomBaseModel) model;
        list = recomBaseModel.result.reInfos;
        rPathInfo = recomBaseModel.result.rPathInfo;

        if (rPathInfo != null) {
            contents = rPathInfo.contents + "";
            sharePath = rPathInfo.sharePath + "";
            bannerPah = rPathInfo.bannerPah + "";
            title = rPathInfo.title + "";
            bannerTitle = rPathInfo.bannerTitle + "";
        }

        if (list.size() > 0) {
            if (DATATYPE.equals("REFRESH")) {//下拉刷新
                mAdapter.clear();
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();
            } else if (DATATYPE.equals("REQUEST")) {//请求
                mAdapter.clear();
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();
            } else if (DATATYPE.equals("LODE")) {//加载
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();
            } else if (DATATYPE.equals("SCREEN")) {//筛选
                mAdapter.clear();
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.setHasMoreDataAndFooter(true, false);
        } else {
            if (DATATYPE.equals("SCREEN")) {//筛选
                mAdapter.clear();
                mAdapter.appendToList(list);
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.setHasMoreDataAndFooter(false, true);
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        stopRefresh(myrecom_sr_fraswipe_refresh);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 弹出分享
     */
    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title + "");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(contents + "" + url + "");//
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("诚信贷理财产品真的很棒!");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

//        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));


        // 参考代码配置章节，设置分享参数
        //通过OneKeyShareCallback来修改不同平台分享的内容
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo(getActivity(), contents));

//        oks.setFilePath("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");
//        oks.setImageUrl("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");

        // 启动分享GUI
//        oks.show(getActivity());

// 参考代码配置章节，设置分享参数
// 构造一个图标
        Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_recom_copy);
//        Bitmap disableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ssdk_oks_logo_qq);
        String label = "复制链接";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                CopyUtils.copy(url, getActivity());
            }
        };
        oks.setCustomerLogo(enableLogo, label, listener);

        oks.show(getActivity());
    }


    @OnClick({R.id.Btn_left,R.id.merecom_bt_recom_now})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
                getActivity().finish();
                break;
            case R.id.merecom_bt_recom_now://立即推荐
                if (!Q.isEmpty(sharePath)) {

                    url = sharePath + "?userId=" + M.getBase64(userModel.getid());
                    showShare();
                } else {
                    T.D("未获取到分享链接");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myrecomed_nowrecomed, "", 1);//事件统计
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
        StatService.onPageStart(getActivity(), "我的推荐");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我的推荐");//(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
