package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AppBoutMiddleModel;
import com.cxd.cxd4android.model.BannerBaseModel;
import com.cxd.cxd4android.model.CheckVersionBaseModel;
import com.cxd.cxd4android.model.NewerBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class BounsMainFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public BounsMainFragmentPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getLoadNewerLoan(strn),
                new SubscriberCallBack<NewerBaseModel>(new ApiCallback<NewerBaseModel>() {
                    @Override
                    public void onSuccess(NewerBaseModel model) {
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

    public void loadBanner(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getLoadBanners(strn),
                new SubscriberCallBack<BannerBaseModel>(new ApiCallback<BannerBaseModel>() {
                    @Override
                    public void onSuccess(BannerBaseModel model) {
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

    public void loadCheckVersion(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getLoadVersion(strn),
                new SubscriberCallBack<CheckVersionBaseModel>(new ApiCallback<CheckVersionBaseModel>() {
                    @Override
                    public void onSuccess(CheckVersionBaseModel model) {
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
    public void loadBountMiddleH5(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAppBountMiddle(strn),
                new SubscriberCallBack<AppBoutMiddleModel>(new ApiCallback<AppBoutMiddleModel>() {
                    @Override
                    public void onSuccess(AppBoutMiddleModel model) {
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
