package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AllPointsModel;
import com.cxd.cxd4android.model.GiftRecommendModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeMyBounsFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeMyBounsFragmentPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserPoint(strn),
                new SubscriberCallBack<AllPointsModel>(new ApiCallback<AllPointsModel>() {
                    @Override
                    public void onSuccess(AllPointsModel model) {
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

    public void loadGoodsData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGiftRecommend(strn),
                new SubscriberCallBack<GiftRecommendModel>(new ApiCallback<GiftRecommendModel>() {
                    @Override
                    public void onSuccess(GiftRecommendModel model) {
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
