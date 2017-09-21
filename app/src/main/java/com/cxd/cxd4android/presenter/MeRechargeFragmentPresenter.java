package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbRechargeModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class MeRechargeFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeRechargeFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getRecharge(strn),
                new SubscriberCallBack<IdentityBaseModel>(new ApiCallback<IdentityBaseModel>() {
                    @Override
                    public void onSuccess(IdentityBaseModel model) {
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

    /**
     * 加载账户金额数据
     * @param strn
     */
    public void loadDataAccount(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAccountBalance(strn),
                new SubscriberCallBack<AccountBalanceBaseModel>(new ApiCallback<AccountBalanceBaseModel>() {
                    @Override
                    public void onSuccess(AccountBalanceBaseModel model) {
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

    /**
     * 加载华兴充值数据
     * @param strn
     */
    public void loadGhbRecharge(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbRecharge(strn),
                new SubscriberCallBack<GhbRechargeModel>(new ApiCallback<GhbRechargeModel>() {
                    @Override
                    public void onSuccess(GhbRechargeModel model) {
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

    /**
     * 加载提示信息数据
     * @param strn
     */
    public void loadGhbGuidePage(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbGuidePage(strn),
                new SubscriberCallBack<GuideModel>(new ApiCallback<GuideModel>() {
                    @Override
                    public void onSuccess(GuideModel model) {
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
