package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.SeFeeBackFragmentPresenter;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.google.gson.Gson;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */

public class SeFeedBackFragment extends BaseFragment implements LoadingView{

    /**
     * 意见反馈正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 意见反馈左上角返回键
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 意见反馈左上角返回键
     **/
    @Bind(R.id.feedback_et_submit_feedback)
     EditText feedback_et_submit_feedback;
    /**
     * 意见反馈左上角返回键
     **/
    @Bind(R.id.feedback_bt_submit_feedback)
     Button feedback_bt_submit_feedback;
    /**
     * 用户信息
     **/
    LocalUserModel userModel;
    /**
     * 用户输入内容
     **/
    private String content = "";
    SeFeeBackFragmentPresenter feeBackFragmentPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_se_feedback, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText("意见反馈");
        Btn_left.setVisibility(View.VISIBLE);

        userModel = new LocalUserModel();
        feeBackFragmentPresenter = new SeFeeBackFragmentPresenter(this);
        //设置监听
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        feedback_et_submit_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Q.isEmpty(feedback_et_submit_feedback.getText().toString().trim())) {
                    feedback_bt_submit_feedback.setBackgroundResource(R.drawable.shape_layout_circle_submitred);
                    feedback_bt_submit_feedback.setClickable(true);
                } else {
                    feedback_bt_submit_feedback.setBackgroundResource(R.drawable.shape_layout_circle_submitredno);
                    feedback_bt_submit_feedback.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //点击
    @OnClick( {R.id.Btn_left, R.id.feedback_bt_submit_feedback})
     void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_left://返回
//                getFragmentManager().popBackStack();

//                remove("SeFeedBackFragment");
                getActivity().finish();
                break;
            case R.id.feedback_bt_submit_feedback://提交
                content = feedback_et_submit_feedback.getText().toString().trim();
                if (Q.isEmpty(content)){
                    T.D("请输入您的内容");
                    return;
                }
                if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {//已登录
                    //请求数据
                    initData();
                } else {//未登录
                    doShowDialog("温馨提示", "您处于未登录状态,请先登录");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 请求数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("content", content);
        map.put("email", "");
        Gson gson = new Gson();
        String strn = gson.toJson(map);

        feeBackFragmentPresenter.loadData(strn);
    }
    @Override
    public void getDataSuccess(Object model) {
        SimpleModel simpleModel = (SimpleModel) model;
        if (Constant.STATUS_SUCCESS.equals(simpleModel.status)) {
            T.D("提交成功");
            getFragmentManager().popBackStack();
            getActivity().finish();
        } else if (Constant.STATUS_FAILED.equals(simpleModel.status)) {
            T.D("提交失败");
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
    private void doShowDialog(String title, String text) {
        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(false);
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Constant.INTENT_JUMP = "SeFeedBackFragment";
                EventBus.getDefault().post(new ILoginCallBack(4));//跳转我
                dialog.dismiss();
                getActivity().finish();
            }
        });
        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.dialog_imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "意见反馈");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "意见反馈");//(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
