package com.cxd.cxd4android.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IGiftExSucCallBack;
import com.cxd.cxd4android.model.BsDetailsModel;
import com.cxd.cxd4android.model.RequestResultModel;
import com.cxd.cxd4android.model.SaveGiftsModel;
import com.cxd.cxd4android.presenter.BounsConfirmFragmentPresenter;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.widget.dialog.AbNotifiDialog;
import com.cxd.cxd4android.widget.share.CopyUtils;
import com.cxd.cxd4android.widget.share.ShareContentCustomizeDemo;
import com.cxd.cxd4android.widget.share.ShareMall;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * ClassName:兑换确认页面
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */
public class BounsConfirmFragment extends BaseFragment implements View.OnClickListener,LoadingView {


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
     * 下一步
     **/
    @Bind(R.id.bonus_confirm_btn)
     TextView bonus_confirm_btn;
    /**
     * 兑换标题
     **/
    @Bind(R.id.bonus_confirm_title)
     TextView bonus_confirm_title;
    /**
     * 兑换价格
     **/
    @Bind(R.id.bonus_confirm_price)
     TextView bonus_confirm_price;
    /**
     * du兑换数量提示
     **/
    @Bind(R.id.bonus_confirm_edit_hint)
     TextView bonus_confirm_edit_hint;
    /**
     * du兑换总价提示
     **/
    @Bind(R.id.bonus_confirm_all_hint)
     TextView bonus_confirm_all_hint;
    /**
     * 库存
     **/
    @Bind(R.id.bonus_confirm_goods_count)
     TextView bonus_confirm_goods_count;
    /**
     * 兑换总价
     **/
    @Bind(R.id.bonus_confirm_all)
     TextView bonus_confirm_all;
    /**
     * 现有积分
     **/
    @Bind(R.id.bonus_confirm_exist)
     TextView bonus_confirm_exist;
    /**
     * 剩余积分
     **/
    @Bind(R.id.bonus_confirm_remain)
     TextView bonus_confirm_remain;
    /**
     * 商品图片
     **/
    @Bind(R.id.bonus_confirm_img)
     ImageView bonus_confirm_img;
    /**
     * 减号
     **/
    @Bind(R.id.bonus_confirm_minus)
     ImageView bonus_confirm_minus;
    /**
     * 编辑
     **/
    @Bind(R.id.bonus_confirm_edit)
     EditText bonus_confirm_edit;
    /**
     * 加号
     **/
    @Bind(R.id.bonus_confirm_add)
     ImageView bonus_confirm_add;

    private int num = 1;//数量
    private String type = "1";//0位虚拟物品，1为实物
    private BsDetailsModel.ResultBean bsDetailsResult;
    /**
     * 奖品ID
     */
    private String productId;
    /**
     * 用户信息
     */
    private LocalUserModel userModel;
    /**
     * 保存礼品兑换页面数据
     */
    private SaveGiftsModel saveGiftModel;

    private String url = "";
    private String contents = "";
    private String sharePath = "";
    private String bannerPah = "";
    private String title = "诚信贷";

    private int allBsPrice = 0, remainBsPrice = 0 ,goodsNum = 0;
    BounsConfirmFragmentPresenter confirmFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView ==null) {
            contentView = inflater.inflate(R.layout.fragment_bonus_confirm, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userModel = new LocalUserModel();
        confirmFragmentPresenter=new BounsConfirmFragmentPresenter(this);

        //初始化状态栏title
        initStatusBar();
        //init初始化控件
        initView();
        //兑换数量加减监听
        setViewListener();
    }

    private void initView() {

        bsDetailsResult = (BsDetailsModel.ResultBean) getArguments().getSerializable("BsDetailsModel");

        if (bsDetailsResult != null) {

            productId = bsDetailsResult.getId();//奖品id
            bonus_confirm_title.setText(bsDetailsResult.getName());
            bonus_confirm_price.setText(bsDetailsResult.getIntegration());
            bonus_confirm_goods_count.setText(bsDetailsResult.getInventory());
            bonus_confirm_all.setText(bsDetailsResult.getIntegration());
            bonus_confirm_exist.setText(bsDetailsResult.getUserPoint());

            Glide.with(context).load(bsDetailsResult.getPicList().get(0)).into(bonus_confirm_img);

            if ("0".equals(bsDetailsResult.getType())) {
                bonus_confirm_btn.setText("确认兑换");
                type = "0";
            } else if ("1".equals(bsDetailsResult.getType())) {
                bonus_confirm_btn.setText("下一步");
                type = "1";
            }
            //j计算积分
            allBsPrice = num * Integer.parseInt(bsDetailsResult.getIntegration());
            remainBsPrice = Integer.parseInt(bsDetailsResult.getUserPoint()) - allBsPrice;
            //库存
            goodsNum = Integer.parseInt(bsDetailsResult.getInventory());

            bonus_confirm_remain.setText(remainBsPrice + "");

            if (remainBsPrice < 0) {
                bonus_confirm_all_hint.setVisibility(View.VISIBLE);
            } else {
                bonus_confirm_all_hint.setVisibility(View.GONE);
            }
        }
    }

    private void initStatusBar() {

        tv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("兑换确认");
    }

    @OnClick({R.id.Btn_left,R.id.bonus_confirm_btn})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Btn_left:
                // getActivity().finish();
                remove("BounsConfirmFragment");
                break;

            case R.id.bonus_confirm_btn:
                if (num > 0 && remainBsPrice >= 0) {
                    if (num<=goodsNum) {
                        if (type.equals("1")) {
                            BounsShipAddrFragment();//跳转到收货地址页面
                        } else if (type.equals("0")) {
                            saveProductData();
                        }
                    }else {
                        Toast.makeText(getActivity(), "库存不足！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    return;
                }
                break;

        }
    }

    /**
     * 收货地址
     */
    public void BounsShipAddrFragment() {

        saveGiftModel = new SaveGiftsModel();
        saveGiftModel.setMarkFragment("BounsConfirmFragment");
        saveGiftModel.setGiftNum(num + "");
        saveGiftModel.setGiftProductId(bsDetailsResult.getId());
        saveGiftModel.setGiftProductName(bsDetailsResult.getName());

        BounsShipAddrFragment bounsShipAddrFragment = new BounsShipAddrFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("saveGiftModel", saveGiftModel);
        bounsShipAddrFragment.setArguments(bundle);

        add(R.id.fragment_mybouns, bounsShipAddrFragment, "BounsShipAddrFragment", null);
    }

    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private void doShowDialog(String title, String text) {
        final AbNotifiDialog dialog = new AbNotifiDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.dialog_title.setBackgroundResource(R.mipmap.cuntermark);
        dialog.dialog_confirm.setText("分享");
        dialog.dialog_sure.setText("继续兑换");

        dialog.setCancelable(false);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //dialog.dismiss();
                //设置礼品兑换成功的回调
                EventBus.getDefault().post(new IGiftExSucCallBack("BounsConfirmFragment"));
                showShare();
            }
        });
        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                //设置礼品兑换成功的回调
                EventBus.getDefault().post(new IGiftExSucCallBack("BounsConfirmFragment"));
                remove("BounsConfirmFragment");
                getActivity().finish();
            }
        });
        dialog.show();
    }

    //请求数据,保存 兑换的礼品
    private void saveProductData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());//
        map.put("productNum", num + "");//
        map.put("productId", productId);//

        Gson gson = new Gson();
        String strn = gson.toJson(map);
        confirmFragmentPresenter.loadData(strn);
    }
    @Override
    public void getDataSuccess(Object model) {
        RequestResultModel models = (RequestResultModel) model;
        if (Constant.STATUS_SUCCESS.equals(models.getStatus())) {

            doShowDialog("", "礼品兑换成功！");
            bonus_confirm_btn.setEnabled(false);
            bonus_confirm_btn.setText("已兑换");
            bonus_confirm_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);

        } else if (Constant.STATUS_FAILED.equals(models.getStatus())) {
            Toast.makeText(getActivity(), "" + models.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (Constant.STATUS_ERROR.equals(models.getStatus())) {
            Toast.makeText(getActivity(), "" + models.getMsg(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "" + models.getMsg(), Toast.LENGTH_SHORT).show();
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
     * 设置文本变化相关监听事件
     */
    private void setViewListener() {
        bonus_confirm_add.setTag("-");
        bonus_confirm_minus.setTag("+");
        bonus_confirm_edit.setText(String.valueOf(num));
        bonus_confirm_edit.setMaxWidth(10000);

        bonus_confirm_add.setOnClickListener(new OnButtonClickListener());
        bonus_confirm_minus.setOnClickListener(new OnButtonClickListener());
        bonus_confirm_edit.addTextChangedListener(new OnTextChangeListener());
    }


    /**
     * 加减按钮事件监听器
     */
    class OnButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String numString = bonus_confirm_edit.getText().toString();
            if (numString.equals("")) {
                num = 0;
                bonus_confirm_edit.setText("0");
            } else {

                if (v.getTag().equals("-")) {
                    if (num < 999) {
                        if (++num < 0)  //先加，再判断
                        {
                            num--;
//                            Toast.makeText(getActivity(), "请输入一个大于0的数字",
//                                    Toast.LENGTH_SHORT).show();
                            setEditHintText(true);
                            bonus_confirm_minus.setImageResource(R.mipmap.minus);
                        } else {
                            setEditHintText(false);
                            bonus_confirm_edit.setText(String.valueOf(num));
                            bonus_confirm_minus.setImageResource(R.mipmap.minus_blue);//减号换为绿色
                        }
                    }
                } else if (v.getTag().equals("+")) {
                    if (num <= 999) {
                        if (--num < 0)  //先减，再判断
                        {
                            num++;
//                            Toast.makeText(getActivity(), "请输入一个大于0的数字",
//                                    Toast.LENGTH_SHORT).show();
                            setEditHintText(true);
                            bonus_confirm_minus.setImageResource(R.mipmap.minus);
                        } else {
                            setEditHintText(false);
                            bonus_confirm_edit.setText(String.valueOf(num));
                            bonus_confirm_minus.setImageResource(R.mipmap.minus_blue);//减号换为绿色
                        }
                    }
                }
                setBsAllPrice();
            }
        }
    }

    /**
     * 计算积分
     **/
    private void setBsAllPrice() {
        allBsPrice = num * Integer.parseInt(bsDetailsResult.getIntegration());
        remainBsPrice = Integer.parseInt(bsDetailsResult.getUserPoint()) - allBsPrice;

        bonus_confirm_all.setText(allBsPrice + "");
        bonus_confirm_exist.setText(bsDetailsResult.getUserPoint());
        bonus_confirm_remain.setText(remainBsPrice + "");
        if (num>goodsNum){
            bonus_confirm_edit_hint.setVisibility(View.VISIBLE);
            bonus_confirm_edit_hint.setText("*礼品兑换数量不能大于库存");
            bonus_confirm_btn.setEnabled(false);
            bonus_confirm_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
        }else {
            bonus_confirm_all_hint.setVisibility(View.GONE);
        }
        if (remainBsPrice < 0 ) {
            bonus_confirm_all_hint.setVisibility(View.VISIBLE);
            bonus_confirm_btn.setEnabled(false);
            bonus_confirm_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
        } else {
            bonus_confirm_all_hint.setVisibility(View.GONE);
//            bonus_confirm_btn.setEnabled(true);
//            bonus_confirm_btn.setBackgroundResource(R.drawable.activity_button_selector_recharge);
        }
    }

    /**
     * 设置兑换数量提示文本
     */
    private void setEditHintText(boolean flag) {
        if (flag) {
            bonus_confirm_edit_hint.setVisibility(View.VISIBLE);
            bonus_confirm_edit_hint.setText("*礼品兑换数量不能小于1");
            bonus_confirm_btn.setEnabled(false);
            bonus_confirm_btn.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
        } else {
            bonus_confirm_edit_hint.setVisibility(View.GONE);
            bonus_confirm_btn.setEnabled(true);
            bonus_confirm_btn.setBackgroundResource(R.drawable.activity_button_selector_recharge);
        }
    }

    /**
     * EditText输入变化事件监听器
     */
    private int MIN_MARK = 0;
    private int MAX_MARK = 999;

    class OnTextChangeListener implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            String numString = s.toString();
            if (numString == null || numString.equals("")) {
                num = 0;
            } else {
                int numInt = Integer.parseInt(numString);

                if (numInt < 1) {
                    setEditHintText(true);
//                    Toast.makeText(getActivity(), "请输入一个大于0的数字",
//                            Toast.LENGTH_SHORT).show();
                    bonus_confirm_minus.setImageResource(R.mipmap.minus);
                } else {
                    setEditHintText(false);
                    //设置EditText光标位置 为文本末端
                    bonus_confirm_edit.setSelection(bonus_confirm_edit.getText().toString().length());
                    bonus_confirm_minus.setImageResource(R.mipmap.minus_blue);//减号换为绿色
                    num = numInt;
                }
                setBsAllPrice();//计算积分价格
            }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (start > 1) {
                if (MIN_MARK != -1 && MAX_MARK != -1) {
                    int num = Integer.parseInt(s.toString());
                    if (num > MAX_MARK) {
                        s = String.valueOf(MAX_MARK);
                        bonus_confirm_edit.setText(s);
                        bonus_confirm_minus.setImageResource(R.mipmap.minus_blue);//减号换为绿色

                    } else if (num < MIN_MARK) {
                        setEditHintText(true);
                        s = String.valueOf(MIN_MARK);
                        bonus_confirm_edit.setText(s);
                        bonus_confirm_minus.setImageResource(R.mipmap.minus_blue);//减号换为绿色

                    }
                    return;
                }

            }
        }

    }

    /**
     * 弹出分享
     */
    private void showShare() {
        url= ShareMall.shareMallUrl;
        contents = "我在诚信贷积分商城兑换了“ "+bsDetailsResult.getName()+"”，积分换礼品，这不是传说。";
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
        oks.setText(contents + ""+url);//
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
        StatService.onPageStart(getActivity(), "兑换确认");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "兑换确认");//(this);
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
        if (!getActivity().isFinishing()){
//            getActivity().getSupportFragmentManager().beginTransaction().remove(BounsConfirmFragment.this).commit();
            remove("BounsConfirmFragment");
        }
    }
}
