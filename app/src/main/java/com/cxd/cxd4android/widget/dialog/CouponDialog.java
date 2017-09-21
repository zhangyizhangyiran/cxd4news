package com.cxd.cxd4android.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.CouponAdapter;
import com.cxd.cxd4android.model.RedPaperModel;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangyi on 17/2/13.
 * 优惠券dialog
 */

public class CouponDialog extends Dialog {
    private final Context context;
    public RedPaperModel redPape;
    @Bind(R.id.coupon_dialog_view)
    RecyclerView couponDialogView;
    @Bind(R.id.img_coupon)
    ImageView imgCoupon;
    @Bind(R.id.coupon_dialog_tv)
    TextView couponDialogTv;

    public CouponDialog(Context context, RedPaperModel redPaperModel) {
        super(context, R.style.coupon_style);
        this.redPape = redPaperModel;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_dialog);
        ButterKnife.bind(this);
        //获取屏幕宽高
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        //屏幕宽
        int widthPixels = displayMetrics.widthPixels;
        //屏幕高
        int heightPixels = displayMetrics.heightPixels;
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //设置给attributes
        attributes.width = (int) (widthPixels * 0.95);
        attributes.height = (int) (heightPixels * 0.7);
        //让Dialog位置居中
        attributes.gravity = Gravity.CENTER;
        getWindow().setAttributes(attributes);
        //点击空白处不dismiss
        setCanceledOnTouchOutside(false);
        CouponAdapter couponAdapter = new CouponAdapter(redPape, this, context);
        couponAdapter.setHasStableIds(false);
        RecyclerView.LayoutManager mm = new LinearLayoutManager(context);
        couponDialogView.setLayoutManager(mm);
        couponDialogView.setAdapter(couponAdapter);
        //判断是否有优惠券  弹出不同内容
        if (redPape.getResult().size() < 1) {
            couponDialogTv.setVisibility(View.VISIBLE);
            couponDialogView.setVisibility(View.INVISIBLE);
        }


    }

    @OnClick(R.id.img_coupon)
    public void onClick() {
        EventBus.getDefault().post("zy");
        this.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EventBus.getDefault().post("zy");
        return super.onKeyDown(keyCode, event);
    }
}
