package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.BounsSaveProductModel;
import com.cxd.cxd4android.model.BsShipAddrModel;
import com.cxd.cxd4android.model.RequestResultModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class BounsShipAddrFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public BounsShipAddrFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);
    /**获取用户地址信息**/
    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserAddress(strn),
                new SubscriberCallBack<BsShipAddrModel>(new ApiCallback<BsShipAddrModel>() {
                    @Override
                    public void onSuccess(BsShipAddrModel model) {
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
    /**保存用户地址信息**/
    public void loadsaveUserAddrData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getSaveAddress(strn),
                new SubscriberCallBack<RequestResultModel>(new ApiCallback<RequestResultModel>() {
                    @Override
                    public void onSuccess(RequestResultModel model) {
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
  /**获取用户商品信息**/
    public void loadsaveProductData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getSaveProduct(strn),
                new SubscriberCallBack<BounsSaveProductModel>(new ApiCallback<BounsSaveProductModel>() {
                    @Override
                    public void onSuccess(BounsSaveProductModel model) {
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
