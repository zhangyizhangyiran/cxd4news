package com.cxd.cxd4android.shbbank.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cxd.cxd4android.R;

/**
 * Created by administrator on 17/8/29.
 */

public class SHBRenewalBankDialog extends Dialog {

    private final Context mContext;

    public TextView mShbDialogTvFanhui;
    public TextView mShbDialogText;
    public Button mShbDialogBtn;


    View mView;
    public TextView mShb_dia_tv_explain;


    public SHBRenewalBankDialog(Context context) {
        super(context, R.style.coupon_style);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(this.mContext).inflate(R.layout.shb_dialog_renewal_bank, null);

        mShbDialogTvFanhui = (TextView) mView.findViewById(R.id.shb_dialog_tv_fanhui);
        mShbDialogText = (TextView) mView.findViewById(R.id.shb_dialog_text);
        mShbDialogBtn = (Button) mView.findViewById(R.id.shb_dialog_btn);
//        对话框头部说明
        mShb_dia_tv_explain = (TextView) mView.findViewById(R.id.shb_dia_tv_explain);

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

        setContentView(mView);
        mShbDialogTvFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}
