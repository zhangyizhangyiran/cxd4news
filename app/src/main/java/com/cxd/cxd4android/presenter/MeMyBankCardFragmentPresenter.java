package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.UnIdentityBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:MeMyBankCardFragmentPresenter
 * Description：绑定银行卡与解绑
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeMyBankCardFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeMyBankCardFragmentPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    /*public void loadBindBankCard(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getBindBankCard(strn),
                new SubscriberCallBack<BindBankCardBaseModel>(new ApiCallback<BindBankCardBaseModel>() {
                    @Override
                    public void onSuccess(BindBankCardBaseModel model) {
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
    }*/

    public void loadUnBindBankCard(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUnBindBankCard(strn),
                new SubscriberCallBack<UnIdentityBaseModel>(new ApiCallback<UnIdentityBaseModel>() {
                    @Override
                    public void onSuccess(UnIdentityBaseModel model) {
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
