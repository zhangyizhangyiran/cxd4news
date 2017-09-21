package com.cxd.cxd4android.widget.share;

import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.ShareMallModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.ShareMallPresenter;
import com.orhanobut.logger.Logger;

/**
 * @version V1.0
 * @ClassName: 积分商城分享
 * @Description:
 * @Author：xiaofa
 * @Date：2016/6/29 9:44
 */
public class ShareMall implements LoadingView{

    public static  String shareMallUrl="";
    public static  String app_logo="";
    public ShareMall getInstance(){
        ShareMallPresenter mallPresenter =  new ShareMallPresenter(ShareMall.this);
        mallPresenter.loadData("");//获取分享数据
        return new ShareMall();
    }

    @Override
    public void getDataSuccess(Object model) {
        ShareMallModel mallModel = (ShareMallModel) model;

        if (Constant.STATUS_SUCCESS.equals(mallModel.status)) {//
            shareMallUrl = mallModel.result.point_mall_list;
            app_logo = mallModel.result.app_logo;
        } else {//失败
            shareMallUrl = "";
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        shareMallUrl = "";
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
