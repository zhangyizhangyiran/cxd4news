package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeMyBinPhoneFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeMyBinPhoneFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getResetMobile(strn),
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

}
