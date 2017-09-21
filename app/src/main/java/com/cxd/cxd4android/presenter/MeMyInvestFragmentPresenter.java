package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.model.UserInvestsBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:MeMyInvestFragmentPresenter
 * Description：借款合同 数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeMyInvestFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeMyInvestFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserInvests(strn),
                new SubscriberCallBack<UserInvestsBaseModel>(new ApiCallback<UserInvestsBaseModel>() {
                    @Override
                    public void onSuccess(UserInvestsBaseModel model) {
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
