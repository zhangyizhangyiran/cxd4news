package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.ReturnYeePayMsgBaseModel;
import com.cxd.cxd4android.model.SendYeePayMsgBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class YeePayMsgPresenter extends MicroBasePresenter<LoadingView> {

    public YeePayMsgPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadSendYeePayMsg(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAppLog(strn),
                new SubscriberCallBack<SendYeePayMsgBaseModel>(new ApiCallback<SendYeePayMsgBaseModel>() {
                    @Override
                    public void onSuccess(SendYeePayMsgBaseModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }
    public void loadReturnYeePayMsg(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getTiplog(strn),
                new SubscriberCallBack<ReturnYeePayMsgBaseModel>(new ApiCallback<ReturnYeePayMsgBaseModel>() {
                    @Override
                    public void onSuccess(ReturnYeePayMsgBaseModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }

}
