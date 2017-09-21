package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.InvesListBaseModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class MainInvestFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MainInvestFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getInvestMent(strn),
                new SubscriberCallBack<InvesListBaseModel>(new ApiCallback<InvesListBaseModel>() {
                    @Override
                    public void onSuccess(InvesListBaseModel model) {
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
    public void loadScreenData(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getCxdAppDict(strn),
                new SubscriberCallBack<ScreenBaseModel>(new ApiCallback<ScreenBaseModel>() {
                    @Override
                    public void onSuccess(ScreenBaseModel model) {
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
