package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.CheckMobileBaseModel;
import com.cxd.cxd4android.model.SendCodeModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class MeForgetVerifiFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeForgetVerifiFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadIsHaveUser(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getIsHaveUser(strn),
                new SubscriberCallBack<CheckMobileBaseModel>(new ApiCallback<CheckMobileBaseModel>() {
                    @Override
                    public void onSuccess(CheckMobileBaseModel model) {
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

    public void loadSendCode(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getRegisterSendCode(strn),
                new SubscriberCallBack<SendCodeModel>(new ApiCallback<SendCodeModel>() {
                    @Override
                    public void onSuccess(SendCodeModel model) {
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
