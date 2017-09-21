package com.cxd.cxd4android.shbbank.password;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.micros.utils.Q;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SHBPassWordActivity extends BaseActivity {

    @Bind(R.id.viewtop_toplly)
    LinearLayout mViewtopToplly;
    @Bind(R.id.Btn_left)
    TextView mBtnLeft;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    @Bind(R.id.Btn_right)
    TextView mBtnRight;
    @Bind(R.id.title_bar)
    RelativeLayout mTitleBar;
    @Bind(R.id.shb_pwslogin_et_old_pws)
    EditText mShbPwsloginEtOldPws;
    @Bind(R.id.shb_pwslogin_tv_old_pws)
    TextView mShbPwsloginTvOldPws;
    @Bind(R.id.shb_pwslogin_et_new_pws)
    EditText mShbPwsloginEtNewPws;
    @Bind(R.id.shb_pwslogin_tv_new_pws)
    TextView mShbPwsloginTvNewPws;
    @Bind(R.id.shb_pwslogin_et_new_pwsconfirm)
    EditText mShbPwsloginEtNewPwsconfirm;
    @Bind(R.id.shb_pwslogin_tv_new_pwsconfirm)
    TextView mShbPwsloginTvNewPwsconfirm;
    @Bind(R.id.shb_pwslogin_bt_submit_info)
    Button mShbPwsloginBtSubmitInfo;
    private String mOld_pws = "";
    private String mNew_pws = "";
    private String mMew_pwsconfirm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shbpass_word);
        ButterKnife.bind(this);
        mTvTitle.setText("修改登录密码");
        mBtnLeft.setVisibility(View.VISIBLE);

        initListener();
    }


    private void initListener() {

        //旧密码
        mShbPwsloginEtOldPws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShbPwsloginTvOldPws.setVisibility(View.INVISIBLE);

                if (!Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtNewPwsconfirm.getText().toString().trim())) {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    mShbPwsloginBtSubmitInfo.setClickable(true);
                } else {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    mShbPwsloginBtSubmitInfo.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //    新密码
        mShbPwsloginEtNewPwsconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mShbPwsloginTvNewPws.setVisibility(View.INVISIBLE);
                if (!Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtNewPwsconfirm.getText().toString().trim())) {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    mShbPwsloginBtSubmitInfo.setClickable(true);
                    mShbPwsloginBtSubmitInfo.setFocusable(true);
                } else {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    mShbPwsloginBtSubmitInfo.setClickable(false);
                    mShbPwsloginBtSubmitInfo.setFocusable(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //确认新密码
        mShbPwsloginEtNewPwsconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShbPwsloginTvNewPwsconfirm.setVisibility(View.INVISIBLE);

                if (!Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtOldPws.getText().toString().trim()) && !Q.isEmpty(mShbPwsloginEtNewPwsconfirm.getText().toString().trim())) {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbutton);
                    mShbPwsloginBtSubmitInfo.setClickable(true);

                } else {
                    mShbPwsloginBtSubmitInfo.setBackgroundResource(R.drawable.shape_layout_circle_loginbuttonno);
                    mShbPwsloginBtSubmitInfo.setClickable(false);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @OnClick({R.id.Btn_left, R.id.shb_pwslogin_bt_submit_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left:
                finish();
                break;
            case R.id.shb_pwslogin_bt_submit_info:
                //旧密码
                mOld_pws = mShbPwsloginEtOldPws.getText().toString().trim();
//                新密码
                mNew_pws = mShbPwsloginEtNewPws.getText().toString().trim();
//                确认新密码
                mMew_pwsconfirm = mShbPwsloginEtNewPwsconfirm.getText().toString().trim();

                setResetPassword();
                break;
        }
    }

    private void setResetPassword() {

    }
}
