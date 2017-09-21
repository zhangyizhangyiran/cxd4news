//package com.cxd.cxd4android.widget.dialog;
//
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.cxd.cxd4android.R;
//
///**
// * ClassName:积分抽奖弹框
// * Description：
// * Author：XiaoFa
// * Date：2016/5/4 16:00
// * version：V1.0
// */
//public  class LuckyDialog extends Dialog{
//
//
//	private AlertDialog dialog;
//
//	public String content="";
//
////	public String title="";
//
//	Context context = null;
//
//	View mView;
//
//
//	public TextView dialog_sure;
//
//	public TextView dialog_confirm;
//
//	public ImageView dialog_colsed;
//
//	public TextView dialog_content;
//
//	public TextView dialog_title;
//
//
//
//	public LuckyDialog(Context context) {
//		super(context);
//		this.context=context;
//		initView();
//
//	}
//	public LuckyDialog(Context context, int theme){
//		super(context, theme);
//		this.context = context;
//		initView();
//	}
//
//	void initView(){
//		mView=LayoutInflater.from(this.context).inflate(R.layout.widget_layout__lucky_dialog, null);
//
//		dialog_title = (TextView) mView.findViewById(R.id.dialog_title);
//		dialog_content = (TextView) mView.findViewById(R.id.dialog_content);
//		dialog_sure = (TextView) mView.findViewById(R.id.dialog_sure);
//		dialog_confirm = (TextView) mView.findViewById(R.id.dialog_confirm);
//		dialog_colsed = (ImageView) mView.findViewById(R.id.dialog_lucky_closed);
//
//		dialog_confirm.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});
//
//		dialog_sure.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});
//
//		dialog_colsed.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});
//
//		setContentView(mView);
//	}
//	public void initInfoMation(String title, String content, String phone,
//			Context context) {
//
//
//	}
//	@Override
//	public void show() {
//		// TODO Auto-generated method stub
//		super.show();
////		dialog_title.setText(title + "");
//		dialog_content.setText(content+"");
//	}
//
//
//}
