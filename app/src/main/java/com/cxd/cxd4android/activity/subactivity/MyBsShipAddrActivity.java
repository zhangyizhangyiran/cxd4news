package com.cxd.cxd4android.activity.subactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ILuckConfirmCallBack;
import com.cxd.cxd4android.model.BsShipAddrModel;
import com.cxd.cxd4android.model.RequestResultModel;
import com.cxd.cxd4android.presenter.BounsShipAddrFragmentPresenter;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.widget.dialog.AbNotifiDialog;
import com.cxd.cxd4android.widget.share.CopyUtils;
import com.cxd.cxd4android.widget.share.ShareContentCustomizeDemo;
import com.google.gson.Gson;
import com.micros.utils.X;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * ClassName:li收货地址
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */
public class MyBsShipAddrActivity extends BaseActivity implements View.OnClickListener,LoadingView {

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
     * shou收货人
     **/
    @Bind(R.id.ship_addr_name)
    EditText ship_addr_name;
    /**
     * 手机号码》》
     **/
    @Bind(R.id.ship_addr_phone)
    EditText ship_addr_phone;
    /**
     * xiang详细地址
     **/
    @Bind(R.id.ship_addr_details)
    EditText ship_addr_details;
    /**
     * 积分价格
     **/
    @Bind(R.id.ship_addr_btn)
    TextView ship_addr_btn;
    /**
     * 收件人-提示
     **/
    @Bind(R.id.ship_addr_name_hint)
    TextView ship_addr_name_hint;
    /**
     * 手机号-提示
     **/
    @Bind(R.id.ship_addr_phone_hint)
    TextView ship_addr_phone_hint;
    /**
     * 地址-提示
     **/
    @Bind(R.id.ship_addr_details_hint)
    TextView ship_addr_details_hint;

    private LocalUserModel userModel;

    private boolean isFirst = true;
    /**
     * 保存的礼品数据信息
     */
    //SaveGiftModel saveGiftModel;
    private String uphId="";
    private String page_id="";
    public static final String POINTDETAIL="PointsDetail",DRAWPRIZE="DrawPrize",LUCKDRAW="LuckDraw";
    private int tage=-1;

    public String url = "";
    public String contents = "";
    public String title = "";
    BounsShipAddrFragmentPresenter addrFragmentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ship_addr);
        ButterKnife.bind(this);

        addrFragmentPresenter=new BounsShipAddrFragmentPresenter(this);
        userModel = new LocalUserModel();
        //初始化状态栏title
        initStatusBar();
        //init初始化控件
        initView();
        initData();
    }

    private void initView() {

        ship_addr_btn.setOnClickListener(this);
        uphId = getIntent().getExtras().getString("uphId");
        page_id = getIntent().getExtras().getString("page_id");
        if (POINTDETAIL.equals(page_id)){
            tage=1;
        }else if (DRAWPRIZE.equals(page_id)){
            tage=2;
        }else if (LUCKDRAW.equals(page_id)){
            tage=3;
        }
    }

    private void initStatusBar() {

        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("收货地址");
    }

    @OnClick({R.id.Btn_left,R.id.ship_addr_btn})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Btn_left:
                doShowDialog(0, "温馨提示", "您还未确认收件信息，确定要离开吗？");
                break;
            case R.id.ship_addr_btn:
                //doShowDialog(1,"快将中将消息分享给小伙伴们吧~","收件信息可在“积分抽奖-积分记录”或“我的积分-积分明细”中确认。");
                if (isFirst) {
                    isFirst=false;
                    if (!uphId.equals("")) {
                        setAddrData();//设置收货地址信息
                    }
                }
                break;
        }
    }

    /**
     * she设置收货地址内容
     */
    private void setAddrData() {

        String addr_name = ship_addr_name.getText().toString();
        String addr_phone = ship_addr_phone.getText().toString();
        String addr_details = ship_addr_details.getText().toString();
        //Toast.makeText(getActivity(),addr_name,Toast.LENGTH_LONG).show();

        if (addr_name.equals("")) {
            ship_addr_name_hint.setVisibility(View.VISIBLE);
            ship_addr_name_hint.setText("*收件人不能为空");
        } else {
            ship_addr_name_hint.setVisibility(View.GONE);
        }
        if (addr_phone.equals("")) {
            //ship_addr_phone_hint.setText("*手机号格式不正确");
            ship_addr_phone_hint.setVisibility(View.VISIBLE);
        } else {
            if (!X.isMobilePhoneVerify(addr_phone)) {
                ship_addr_phone_hint.setText("*手机号格式不正确");
                ship_addr_phone_hint.setVisibility(View.VISIBLE);
            } else
                ship_addr_phone_hint.setVisibility(View.GONE);
        }
        if (addr_details.equals("")) {
            ship_addr_details_hint.setText("*地址不能为空");
            ship_addr_details_hint.setVisibility(View.VISIBLE);
        } else {
            ship_addr_details_hint.setVisibility(View.GONE);
        }
        if (!addr_name.equals("") && !addr_phone.equals("") && !addr_details.equals("")) {
            if (X.isMobilePhoneVerify(addr_phone)) {
                ship_addr_name_hint.setVisibility(View.GONE);
                ship_addr_phone_hint.setVisibility(View.GONE);
                ship_addr_details_hint.setVisibility(View.GONE);
                saveUserAddrData();//设置（保存）用户收货地址

            } else {
                ship_addr_phone_hint.setText("*手机号格式不正确");
                ship_addr_phone_hint.setVisibility(View.VISIBLE);
                return;
            }

        }
    }

    /**
     * 请求数据,获取地址信息
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        addrFragmentPresenter.loadData(strn);
    }

    /**
     * 设置（保存）用户收货地址
     ***/
    private void saveUserAddrData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("username", ship_addr_name.getText().toString());
        map.put("mobileNumber", ship_addr_phone.getText().toString());
        map.put("address", ship_addr_details.getText().toString());
        map.put("uphId", uphId);

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        addrFragmentPresenter.loadsaveUserAddrData(strn);

    }
    @Override
    public void getDataSuccess(Object model) {
        if (model.getClass().equals(BsShipAddrModel.class)) {
            BsShipAddrModel bsShipAddrModel = (BsShipAddrModel) model;
            if (Constant.STATUS_SUCCESS.equals(bsShipAddrModel.getStatus())) {
                if (bsShipAddrModel.getResult() != null) {
                    ship_addr_name.setText(bsShipAddrModel.getResult().getUsername());
                    ship_addr_phone.setText(bsShipAddrModel.getResult().getMobileNumber());
                    ship_addr_details.setText(bsShipAddrModel.getResult().getAddress());
                }
            }

        }else if (model.getClass().equals(RequestResultModel.class)){
            RequestResultModel resultModel = (RequestResultModel) model;
            if (Constant.STATUS_SUCCESS.equals(resultModel.getStatus())) {
                // saveProductData();//保存礼品兑换数据
                doShowDialog(tage, "收件信息确认成功！", "奖品将在近期寄出，请注意查收，更多详细信息可通过“积分抽奖->积分记录” 或 “我的积分->积分明细”查看");
                ship_addr_btn.setEnabled(false);
                ship_addr_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);

            } else if (Constant.STATUS_FAILED.equals(resultModel.getStatus())) {

            } else if (Constant.STATUS_ERROR.equals(resultModel.getStatus())) {
                Toast.makeText(MyBsShipAddrActivity.this, "收货地址含特殊字符，请重新输入！", Toast.LENGTH_SHORT).show();
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
    /**
     * 弹出对话框
     *
     * @param
     * @param
     */

    private void doShowDialog(final int tage, String title, String text) {
        final AbNotifiDialog dialog = new AbNotifiDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(false);
        if (tage == 1){
            dialog.dialog_confirm.setText("返回积分明细");
            dialog.dialog_sure.setVisibility(View.GONE);
        }else if (tage==2) {
            dialog.dialog_confirm.setText("返回抽奖记录");
            dialog.dialog_sure.setVisibility(View.GONE);
        }else if (tage==3) {
            dialog.dialog_confirm.setText("返回积分抽奖");
            dialog.dialog_sure.setVisibility(View.GONE);

            EventBus.getDefault().post(new ILuckConfirmCallBack());
        }
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();

                finish();
            }
        });
        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 弹出分享
     */
    private void showShare() {
        ShareSDK.initSDK(MyBsShipAddrActivity.this);
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
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo(MyBsShipAddrActivity.this, contents));

//        oks.setFilePath("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");
//        oks.setImageUrl("http://p4.so.qhimg.com/bdr/_240_/t0136bf73e425b527a4.jpg");

        // 启动分享GUI
//        oks.show(MyBsShipAddrActivity.this);

// 参考代码配置章节，设置分享参数
// 构造一个图标
        Bitmap enableLogo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_recom_copy);
//        Bitmap disableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ssdk_oks_logo_qq);
        String label = "复制链接";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                CopyUtils.copy(url, MyBsShipAddrActivity.this);
            }
        };
        oks.setCustomerLogo(enableLogo, label, listener);

        oks.show(MyBsShipAddrActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(MyBsShipAddrActivity.this, "收货地址");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(MyBsShipAddrActivity.this, "收货地址");//(this);
    }


}
