package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.SendCodeModel;
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:MeRegisterVerifiFragmentPresenter
 * Description：注册 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeRegisterVerifiFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeRegisterVerifiFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getIsUsePhone(strn),
                new SubscriberCallBack<SimpleModel>(new ApiCallback<SimpleModel>() {
                    @Override
                    public void onSuccess(SimpleModel model) {
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
