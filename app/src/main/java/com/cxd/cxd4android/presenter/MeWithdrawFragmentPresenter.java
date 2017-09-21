package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbWithdrawModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.WithdrawBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:MeWithdrawFragmentPresenter
 * Description：提现 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeWithdrawFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeWithdrawFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadWithdrawalFee(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getWithdrawalFee(strn),
                new SubscriberCallBack<WithdrawBaseModel>(new ApiCallback<WithdrawBaseModel>() {
                    @Override
                    public void onSuccess(WithdrawBaseModel model) {
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
    public void loadWithdraw(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getwithdraw(strn),
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

    public void loadGhbWithdraw(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbWithdraw(strn),
                new SubscriberCallBack<GhbWithdrawModel>(new ApiCallback<GhbWithdrawModel>() {
                    @Override
                    public void onSuccess(GhbWithdrawModel model) {
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
