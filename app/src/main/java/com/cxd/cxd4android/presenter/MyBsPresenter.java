package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.InvestModel;
import com.cxd.cxd4android.model.UphAddrDataModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MyBsPresenter extends MicroBasePresenter<LoadingView> {

    public MyBsPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadModeInves(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getInvestByInvestId(strn),
                new SubscriberCallBack<InvestModel>(new ApiCallback<InvestModel>() {
                    @Override
                    public void onSuccess(InvestModel model) {
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

    public void loadModeUphData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getProductByUphId(strn),
                new SubscriberCallBack<UphAddrDataModel>(new ApiCallback<UphAddrDataModel>() {
                    @Override
                    public void onSuccess(UphAddrDataModel model) {
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
