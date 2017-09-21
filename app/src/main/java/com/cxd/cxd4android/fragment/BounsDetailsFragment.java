package com.cxd.cxd4android.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.MeMyBonusPointsActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.BsDetailsModel;
import com.cxd.cxd4android.model.GiftRecommendModel;
import com.cxd.cxd4android.presenter.BounsDetailsFragmentPresenter;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.widget.share.CopyUtils;
import com.cxd.cxd4android.widget.share.ShareContentCustomizeDemo;
import com.cxd.cxd4android.widget.share.ShareMall;
import com.cxd.cxd4android.widget.view.WebScrollview;
import com.cxd.cxd4android.widget.viewpager.CycleViewPager;
import com.cxd.cxd4android.widget.viewpager.ViewFactory;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebSettings;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * ClassName:li礼品详情
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */
public class BounsDetailsFragment extends BaseFragment implements View.OnClickListener, WebScrollview.OnScrollChangedCallback, LoadingView {


    /**
     * 我的投资正中间标题
     **/
    @Bind(R.id.tv_title)
    TextView tv_title;
    /**
     * 我的投资左上角返回箭头
     **/
    @Bind(R.id.Btn_left)
    TextView tv_back;
    /**
     * 我的投资右上角全部
     **/
    @Bind(R.id.tv_right)
    TextView tv_right;
    /**
     * 积分兑换
     **/
    @Bind(R.id.bonus_details_btn)
    TextView bonus_details_btn;
    /**
     * 赚取积分》》
     **/
    @Bind(R.id.bonus_details_getbonus)
    TextView bonus_details_getbonus;
    /**
     * 礼品标题
     **/
    @Bind(R.id.bonus_details_title)
    TextView bonus_details_title;
    /**
     * 兑换价格
     **/
    @Bind(R.id.bonus_details_price)
    TextView bonus_details_price;
    /**
     * 现有积分
     **/
    @Bind(R.id.bonus_details_bonus_count)
    TextView bonus_details_bonus_count;
    /**
     * 库存
     **/
    @Bind(R.id.bonus_details_goods_count)
    TextView bonus_details_goods_count;
    /**
     * scroll_view
     **/
    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    /**
     * webview
     **/
    @Bind(R.id.bouns_detail_webview)
    WebScrollview bouns_detail_webview;

    private GiftRecommendModel.ResultBean giftRecommendResult;
    private BsDetailsModel.ResultBean bsDetailsResult;
    /**
     * 奖品ID
     */
    private String productId;
    /**
     * 用户信息
     */
    LocalUserModel userModel;

    private String url = "";
    private String contents = "";
    private String title = "诚信贷";
    private List<ImageView> views = new ArrayList<ImageView>();
    private CycleViewPager cycleViewPager;
    BounsDetailsFragmentPresenter detailsFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_bonus_details, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsFragmentPresenter = new BounsDetailsFragmentPresenter(this);
        //初始化状态栏title
        initStatusBar();
        //init初始化控件
        initView();

    }

    private void initView() {
        userModel = new LocalUserModel();
        giftRecommendResult = (GiftRecommendModel.ResultBean) getActivity().getIntent().getSerializableExtra("GiftRecommendModel");
        productId = giftRecommendResult.getId();
        //请求数据
        initData();

        //库存不足，提示
        int inventory = Integer.parseInt(giftRecommendResult.getInventory());
        if (inventory <= 0) {
            bonus_details_btn.setEnabled(false);
            bonus_details_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
            bonus_details_btn.setText("库存不足");
        }

        bonus_details_title.setText(giftRecommendResult.getName());
        bonus_details_price.setText(giftRecommendResult.getIntegration());
        bonus_details_goods_count.setText("库存：" + giftRecommendResult.getInventory());

    }

    private void initStatusBar() {

        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("礼品详情");
        tv_right.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv_right.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.share, 0);
        }
    }

    //请求数据,获取礼品详情页数据
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("productId", productId);//
        map.put("userId", userModel.getid());//

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        detailsFragmentPresenter.loadData(strn);
    }

    @Override
    public void getDataSuccess(Object model) {
        BsDetailsModel detailsModel = (BsDetailsModel) model;
        if (Constant.STATUS_SUCCESS.equals(detailsModel.getStatus())) {
            bsDetailsResult = detailsModel.getResult();
            if (bsDetailsResult != null) {
                setData();
                List<String> imgUrlList = bsDetailsResult.getPicList();
                //加载轮播图
                initialize(imgUrlList);
            } else {
                Toast.makeText(getActivity(), "没有获取到礼品数据", Toast.LENGTH_SHORT).show();
            }
        } else if (Constant.STATUS_FAILED.equals(detailsModel.getStatus())) {

        }
    }

    @Override
    public void getDataFail(String msg) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    /**
     * 设置数据到view
     **/
    private void setData() {

        bonus_details_bonus_count.setText("现有积分：" + bsDetailsResult.getUserPoint());
        int userPoint = Integer.parseInt(bsDetailsResult.getUserPoint());
        int giftPoint = Integer.parseInt(bsDetailsResult.getIntegration());
        if (userPoint < giftPoint) {
            bonus_details_btn.setEnabled(false);
            bonus_details_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
            bonus_details_btn.setText("积分不足");
        }

        //自适应屏幕
        bouns_detail_webview.getSettings().setUseWideViewPort(true);
        bouns_detail_webview.getSettings().setLoadWithOverviewMode(true);
        bouns_detail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        bouns_detail_webview.loadUrl(bsDetailsResult.getDetailImgpath());
        bouns_detail_webview.setOnScrollChangedCallback(this);//设置webview的事件回调

    }

    @SuppressLint("NewApi")
    private void initialize(List<String> imgListUrl) {

        cycleViewPager = (CycleViewPager) (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_bsdetails_viewpager);

        Logger.i("长度==" + imgListUrl.size());
        List<BannerModel> list = new ArrayList<BannerModel>();

        for (int i = 0; i < imgListUrl.size(); i++) {
            BannerModel model = new BannerModel();
            model.imgRootUrl = imgListUrl.get(i);
            list.add(model);
        }
        if (imgListUrl.size() > 1) { /** Modify By Gele **/
            // 将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(context, imgListUrl.get(imgListUrl.size() - 1)));

            for (int i = 0; i < imgListUrl.size(); i++) {
                views.add(ViewFactory.getImageView(context, imgListUrl.get(i)));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(context, imgListUrl.get(0)));
            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(true);
        } else {

            for (int i = 0; i < imgListUrl.size(); i++) {
                views.add(ViewFactory.getImageView(context, imgListUrl.get(i)));
            }

            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(false);
            Logger.i("views" + views.size() + "imgListUrl" + imgListUrl.size());
        }


        // 在加载数据前设置是否循环
        cycleViewPager.setDataScalType(views, list, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(3000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

        //cycleViewPager.disableParentViewPagerTouchEvent();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerModel model, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Toast.makeText(getActivity(), "position-->" + model.url, Toast.LENGTH_SHORT).show();
            }

        }

    };

    /**
     * 回调接口
     **/
    @OnClick({R.id.Btn_left, R.id.tv_right, R.id.bonus_details_btn, R.id.bonus_details_getbonus})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Btn_left:
                getActivity().finish();
                break;
            case R.id.tv_right://分享按钮
                if (bsDetailsResult != null) {
                    showShare();
                } else {
                    T.D("没有获取数据");
                }
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_detail_share, "", 1);//事件统计
                break;
            case R.id.bonus_details_btn://积分兑换
                if (bsDetailsResult != null) {
                    int userPoint = Integer.parseInt(bsDetailsResult.getUserPoint());
                    int giftPoint = Integer.parseInt(giftRecommendResult.getIntegration());
                    //判断用户积分大于礼品兑换积分，跳转到礼品确认页面
                    if (userPoint >= giftPoint) {
                        bounsConfirmFragment();
                    }
                } else {
                    Toast.makeText(getActivity(), "没有获取到商品数据！", Toast.LENGTH_SHORT).show();
                }
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_detail_exchange, "", 1);//事件统计
                break;
            case R.id.bonus_details_getbonus://赚取积分
                EventBus.getDefault().post(new ILoginCallBack(1));//跳转投资

                MeMyBonusPointsActivity.MeMyBonusPointsActivity.finish();
                getActivity().finish();
                StatService.onEvent(getContext(), BaiDustatistic.myintegral_detail_makeintegrl, "", 1);//事件统计
                break;
        }
    }

    /**
     * 积分确认
     */
    private void bounsConfirmFragment() {

        BounsConfirmFragment bounsConfirmFragment = new BounsConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BsDetailsModel", bsDetailsResult);
        bounsConfirmFragment.setArguments(bundle);

        add(R.id.fragment_mybouns, bounsConfirmFragment, "BounsConfirmFragment", null);
    }


    /**
     * 弹出分享
     */
    private void showShare() {
        contents = "当时我就震惊了，我在诚信贷积分商城竟然发现了“" + bsDetailsResult.getName() + "”！！";
        url = ShareMall.shareMallUrl;
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();


        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("诚信贷");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(contents + " " + url);//
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

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "礼品详情");//(this);

    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "礼品详情");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!getActivity().isFinishing()) {
            remove("BounsDetailsFragment");
        }
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onScroll(int deltaY) {
        // TODO Auto-generated method stub
        int y = scroll_view.getScrollY() + deltaY;
        scroll_view.smoothScrollTo(0, y);
    }
}
