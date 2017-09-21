package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.GhbInvestModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.MakeMoneyModel;
import com.cxd.cxd4android.model.NewerBaseModel;
import com.cxd.cxd4android.model.RedMessageModel;
import com.cxd.cxd4android.model.RedPaperModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rx.internal.operators.BlockingOperatorToIterator;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 */
public class BuyProductLoanPresenter extends MicroBasePresenter<LoadingView> {

    public BuyProductLoanPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getLoan(strn),
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
     * 易宝 投资
     *
     * @param strn
     */
    public void loadDataInvest(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getInvest(strn),
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
     * 华兴 详情投资
     *
     * @param strn
     */
    public void loadDataGhbInvest(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbInvest(strn),
                new SubscriberCallBack<GhbInvestModel>(new ApiCallback<GhbInvestModel>() {
                    @Override
                    public void onSuccess(GhbInvestModel model) {
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

    //红包数据

    public void loadDataRedPaper(Object strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getRedPaper((Map<String, String>) strn),
                new SubscriberCallBack<RedPaperModel>(new ApiCallback<RedPaperModel>() {
                    @Override
                    public void onSuccess(RedPaperModel model) {
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

    //红包返回数据
    public void laadRedMessage(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getRedMessage(strn),
                new SubscriberCallBack<RedMessageModel>(new ApiCallback<RedMessageModel>() {
                    @Override
                    public void onSuccess(RedMessageModel model) {
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

    //新增风险投资接口
    public void laadMakeMoney(Object strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getMakeMoney((Map<String, String>) strn),
                new SubscriberCallBack<MakeMoneyModel>(new ApiCallback<MakeMoneyModel>() {
                    @Override
                    public void onSuccess(MakeMoneyModel model) {
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
