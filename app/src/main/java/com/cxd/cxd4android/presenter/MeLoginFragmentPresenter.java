package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.LoginBaseModel;
import com.cxd.cxd4android.model.UploadDeviceTokenModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class MeLoginFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeLoginFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getLogin(strn),
                new SubscriberCallBack<LoginBaseModel>(new ApiCallback<LoginBaseModel>() {
                    @Override
                    public void onSuccess(LoginBaseModel model) {
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
    public void loadBindCardQuery(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getBindCardQuery(strn),
                new SubscriberCallBack<BindCardQueryBaseModel>(new ApiCallback<BindCardQueryBaseModel>() {
                    @Override
                    public void onSuccess(BindCardQueryBaseModel model) {
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

    public void loadUploadDeviceToken(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getUploadDeviceToken(strn),
                new SubscriberCallBack<UploadDeviceTokenModel>(new ApiCallback<UploadDeviceTokenModel>() {
                    @Override
                    public void onSuccess(UploadDeviceTokenModel model) {
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
