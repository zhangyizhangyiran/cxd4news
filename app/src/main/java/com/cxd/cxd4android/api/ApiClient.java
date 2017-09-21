package com.cxd.cxd4android.api;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IRepestLoginCallBack;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.micros.data.server.MicroClient;
import com.micros.utils.Q;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * ClassName: ApiClient
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/7/4 10:10
 * version：V1.0
 */
public class ApiClient extends MicroClient {
    public static Retrofit mRetrofit;

//    private static boolean isReLogin = false , isReFailed = false , isReError = false;//避免重复执行,

    static LocalUserModel userModel;

    public static Retrofit retrofit() {
        if (userModel == null) {
            userModel = new LocalUserModel();
        }
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newReq = request.newBuilder()
                            .header("TOKEN", userModel.getHeader())
                            .header("sourceCode", BaseApplication.mApp.getApplicationMetaData("CXD_CHANNEL_ID"))
                            .header("sourceType", "1")
                            .header("useVersion", userModel.getAppVersionName())
                            .header("thirdPay", userModel.getThirdPayType())//判断账户类型--华兴或易宝
                            .header("clientid", "android-" + userModel.getAppVersionName())
                            .build();

                    Log.i("third_pay", userModel.getThirdPayType());

                    Response response = chain.proceed(newReq);
                    ResponseBody body = response.body();
                    String string = body.string();


                    Logger.i(newReq.headers().toString() + "\n" + request + "\n" + response + "\n" + string);
                    if (!Q.isEmpty(string)) {
                        if (CheckRepeatLogin(string)) {
                            return response;
                        }
                    }
                    return response.newBuilder()
                            .body(ResponseBody.create(body.contentType(), string))
                            .build();
//                    return response;
                }
            });

            /*OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newReq = request.newBuilder()
                            .header("TOKEN", userModel.getHeader())
                            .header("sourceCode", BaseApplication.mApp.getApplicationMetaData("CXD_CHANNEL_ID"))
                            .header("sourceType", "1")
                            .header("useVersion",userModel.getAppVersionName())
                            .build();

                    Response response = chain.proceed(newReq);
                    Logger.i(request+"\n" + response);
//                    if (CheckRepeatLogin(String.valueOf(response.body().string()))){
//                        return null;
//                    }
//                    return response.newBuilder()
//                            .body(ResponseBody.create(response.body().contentType(), response.body().string()))
//                            .build();
                    return response;
                }
            });*/

//            if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
//            }

            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.WEBBASEAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 检查重复登录
     */
    private static boolean CheckRepeatLogin(String content) {

        // TODO Auto-generated method stub
        try {
            JSONObject jsonObject = new JSONObject(content);
            String string = jsonObject.getString("status");
            String msg = jsonObject.getString("msg");
            if (Constant.STATUS_FAILED.equals(string)) {
                doShowDialog("温馨提示", msg + "", 4);
//                isReFailed = true;
                return true;
            }
            if (Constant.STATUS_ERROR.equals(string)) {
                doShowDialog("温馨提示", msg + "", 4);
//                isReError = true;
                return true;
            } else if (Constant.STATUS_UNLOGIN.equals(string)) {
                if (LocalUserModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {
                    userModel.setLOGIN_STATE(LocalUserModel.LOGIN_STATE_OFFLINE);
                    EventBus.getDefault().post(new IRepestLoginCallBack(msg));
//                    isReLogin = true;
                }
                userModel.clear();
                return true;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private static void doShowDialog(final String title, final String text, final int type) {
        Thread thread = new Thread() {

            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        doShowDialogThread(title, text, type);
                    }
                });

                Looper.loop();
            }
        };
        thread.start();

    }

    /**
     * 弹出对话框
     *
     * @param
     * @param
     */
    private static void doShowDialogThread(String title, String text, final int type) {
        final PromptDialog dialog = new PromptDialog(BaseApplication.TopAct, R.style.mydialog);
        dialog.title = title;
        dialog.content = text;
        dialog.setCancelable(true);
        if (4 == type) {
            dialog.dialog_imageView.setVisibility(View.INVISIBLE);
        }
        dialog.dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });
        dialog.dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.dialog_imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

