package com.cxd.cxd4android.global;

import android.content.Context;

import com.cxd.cxd4android.model.ReturnYeePayMsgBaseModel;
import com.cxd.cxd4android.model.SendYeePayMsgBaseModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.YeePayMsgPresenter;
import com.google.gson.Gson;
import com.micros.utils.A;
import com.micros.utils.D;
import com.micros.utils.N;
import com.orhanobut.logger.Logger;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:
 * Description：发送易宝回调地址及其他相关信息
 * Author：Chengel_HaltuD
 * Date：2016/5/20 9:18
 * version：V1.0
 */
public class SendYeePayMsg implements LoadingView {

    static String myUUID = "";
    static YeePayMsgPresenter payMsgPresenter;

    public SendYeePayMsg() {
        payMsgPresenter = new YeePayMsgPresenter(this);
    }

    public void sendYeePayMsg(Context context, String operation, String url, String method, String data, String state, String html) {

        LocalUserModel userModel = new LocalUserModel();
        Map<String, String> map = new HashMap<String, String>();
        if ("loading".equals(state)) {
            myUUID = A.getMyUUID(BaseApplication.getInstance());
            map.put("operation", operation);//页面title
            map.put("operation_sn", myUUID);
            map.put("userId", userModel.getid());
            map.put("url", url);
            map.put("mobile_time", D.getTimeStamp());
            map.put("method", method);

            map.put("state", state);//loading.开始加载 complete.加载完成 return.界面返回
            map.put("platform", "Android");
            map.put("brand", getOsDisplay(android.os.Build.MODEL));
            map.put("systemVersion",  getOsDisplay(android.os.Build.VERSION.RELEASE));
            map.put("netWork", N.getNetworkState(context));
            map.put("extra", "");
            if (("READ_PHONE_STATE").equals(userModel.getREAD_PHONE_STATE())) {
                map.put("mobile_sn", A.getMyDeviceID(context));
            } else {
                map.put("mobile_sn", "用户未授权");
            }
            if ("get".equalsIgnoreCase(method)) {
                map.put("data", url);
            } else {
                map.put("data", "");
            }
        } else if ("complete".equals(state)) {
            map.put("operation_sn", myUUID);
            map.put("state", state);//loading.开始加载 complete.加载完成 return.界面返回
            map.put("html", html);
            map.put("userId", userModel.getid());
            map.put("extra", "");
            if (("READ_PHONE_STATE").equals(userModel.getREAD_PHONE_STATE())) {
                map.put("mobile_sn", A.getMyDeviceID(context));
            } else {
                map.put("mobile_sn", "用户未授权");
            }
        } else if ("return".equals(state)) {
            map.put("userId", userModel.getid());
            map.put("operation", operation);//页面title
            map.put("state", state);//loading.开始加载 complete.加载完成 return.界面返回
            map.put("extra", "");
            if (("READ_PHONE_STATE").equals(userModel.getREAD_PHONE_STATE())) {
                map.put("mobile_sn", A.getMyDeviceID(context));
            } else {
                map.put("mobile_sn", "用户未授权");
            }
        }
        Gson gson = new Gson();
        String strn = gson.toJson(map);
        Logger.i("----加密----" + strn);

        if (strn != null) {
            String encryptString = RSAUtils.rsaEncrypt(strn);
            Logger.i("==========EncryptString=========" + encryptString);
            payMsgPresenter.loadSendYeePayMsg(encryptString);

//            MicroRequestParams params = new MicroRequestParams();
//            params.put("value", encryptString);
//
//            TypeToken<List<SendYeePayMsgBaseModel>> typeToken = new TypeToken<List<SendYeePayMsgBaseModel>>() {
//            };
//            FinalFetch<SendYeePayMsgBaseModel> fetch = new FinalFetch<SendYeePayMsgBaseModel>(new IFetch<SendYeePayMsgBaseModel>() {
//                @Override
//                public void onSuccess(List<SendYeePayMsgBaseModel> datas) {
//                    SendYeePayMsgBaseModel model = datas.get(0);
//                    if (Constant.STATUS_SUCCESS.equals(model.status)) {
//
//
//                    } else if (Constant.STATUS_FAILED.equals(model.status)) {
//
//                    }
//
//                }
//
//                @Override
//                public void onFail(ResultModel result) {
//                    L.I(Constant.CONNECT_FAILURE);
//                }
//
//                @Override
//                public void onFetching(int proccess) {
//                }
//
//                @Override
//                public void onPrevious() {
//                }
//            }, params, typeToken, Constant.otherc_appLog);
        }

    }

    /**
     * 易宝页面返回时请求
     **/
    public void returnYeePayMsg() {


        LocalUserModel userModel = new LocalUserModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());//http://api.51cxd.com/这个用userId, 测试

        Gson gson = new Gson();
        String strn = gson.toJson(map);


//        MicroRequestParams params = new MicroRequestParams();
//        params.put("value", strn);
//
//        TypeToken<List<ReturnYeePayMsgBaseModel>> typeToken = new TypeToken<List<ReturnYeePayMsgBaseModel>>() {
//        };
//        FinalFetch<ReturnYeePayMsgBaseModel> fetch = new FinalFetch<ReturnYeePayMsgBaseModel>(new IFetch<ReturnYeePayMsgBaseModel>() {
//            @Override
//            public void onSuccess(List<ReturnYeePayMsgBaseModel> datas) {
//                ReturnYeePayMsgBaseModel model = datas.get(0);
//                if (Constant.STATUS_SUCCESS.equals(model.status)) {
//
//
//                } else if (Constant.STATUS_FAILED.equals(model.status)) {
//
//                }
//
//            }
//
//            @Override
//            public void onFail(ResultModel result) {
//                L.I(Constant.CONNECT_FAILURE);
//            }
//
//            @Override
//            public void onFetching(int proccess) {
//            }
//
//            @Override
//            public void onPrevious() {
//            }
//        }, params, typeToken, Constant.logc_operation_tip_log);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(SendYeePayMsgBaseModel.class)) {
            SendYeePayMsgBaseModel sendYeePayMsg = (SendYeePayMsgBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(sendYeePayMsg.status)) {


            } else if (Constant.STATUS_FAILED.equals(sendYeePayMsg.status)) {

            }
        } else if (model.getClass().equals(ReturnYeePayMsgBaseModel.class)) {
            ReturnYeePayMsgBaseModel returnYeePay = (ReturnYeePayMsgBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(returnYeePay.status)) {


            } else if (Constant.STATUS_FAILED.equals(returnYeePay.status)) {

            }
        }
    }

    @Override
    public void getDataFail(String msg) {

        Logger.e(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    /**解决OKHttp-header中不能传中文问题**/
    private String getOsDisplay(String str) {
        if (str.length() < str.getBytes().length) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return str;
        }
    }


}
