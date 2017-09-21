package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.GhbBindCardModel;
import com.cxd.cxd4android.model.GhbIdentityModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.UploadDeviceTokenModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：华兴账户页面 中的实名认证
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeMyGhbAccountPresenter extends MicroBasePresenter<LoadingView> {

    public MeMyGhbAccountPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadGhbIdentity(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbRealnameAuthentication(strn),
                new SubscriberCallBack<GhbIdentityModel>(new ApiCallback<GhbIdentityModel>() {
                    @Override
                    public void onSuccess(GhbIdentityModel model) {
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

    public void loadGhbIsRealName(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getisRealnameAuthent(strn),
                new SubscriberCallBack<IsRealNameBaseModel>(new ApiCallback<IsRealNameBaseModel>() {
                    @Override
                    public void onSuccess(IsRealNameBaseModel model) {
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

    public void loadGhbBankCard(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getGhbBindBankCard(strn),
                new SubscriberCallBack<GhbBindCardModel>(new ApiCallback<GhbBindCardModel>() {
                    @Override
                    public void onSuccess(GhbBindCardModel model) {
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

    public void loadUpdateBankCardStatus(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getUpdateBankCardStatus(strn),
                new SubscriberCallBack<UploadDeviceTokenModel>(new ApiCallback<UploadDeviceTokenModel>() {
                    @Override
                    public void onSuccess(UploadDeviceTokenModel model) {
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
     * 加载图文指引 信息数据
     *
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

    /**
     * 加载userInfo信息数据
     *
     * @param strn
     */
    public void loadUserInfo(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserInfo(strn),
                new SubscriberCallBack<UserInfoModel>(new ApiCallback<UserInfoModel>() {
                    @Override
                    public void onSuccess(UserInfoModel model) {
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
     * 加载E账户信息和绑卡状态
     *
     * @param strn
     */
    public void loadGhbUserExt(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbUserExt(strn),
                new SubscriberCallBack<GhbUserExtModel>(new ApiCallback<GhbUserExtModel>() {
                    @Override
                    public void onSuccess(GhbUserExtModel model) {
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
