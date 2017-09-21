package com.cxd.cxd4android.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import com.cxd.cxd4android.R;


public  class PromptDialog extends Dialog{



	public String content="";

	public String title="";

	Context context = null;

	View mView;

	public TextView dialog_sure;

	public TextView dialog_confirm;

	public TextView dialog_content;

	public TextView dialog_title;

	public ImageView dialog_imageView;

	public RelativeLayout dialog_rl_title;

	public RelativeLayout dialog_rl_content;
    public View mDialog_view;

    public PromptDialog(Context context) {
		super(context);
		this.context=context;
		initView();

	}
	public PromptDialog(Context context, int theme){
		super(context, theme);
		this.context = context;
		initView();
	}

	void initView(){
		mView=LayoutInflater.from(this.context).inflate(R.layout.widget_layout_prompt_dialog, null);

        mDialog_view = mView.findViewById(R.id.dialog_view);
        dialog_imageView =  (ImageView) mView.findViewById(R.id.dialog_imageView);
		dialog_title = (TextView) mView.findViewById(R.id.dialog_title);
		dialog_content = (TextView) mView.findViewById(R.id.dialog_content);
		dialog_sure = (TextView) mView.findViewById(R.id.dialog_sure);
		dialog_confirm = (TextView) mView.findViewById(R.id.dialog_confirm);
		dialog_rl_title = (RelativeLayout) mView.findViewById(R.id.dialog_rl_title);
		dialog_rl_content = (RelativeLayout) mView.findViewById(R.id.dialog_rl_content);
		
		dialog_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		dialog_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
			}
		});

		setContentView(mView);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		dialog_title.setText(title+"");
		dialog_content.setText(content+"");
	}
	
	
}
