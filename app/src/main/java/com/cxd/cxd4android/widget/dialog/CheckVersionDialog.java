package com.cxd.cxd4android.widget.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.manager.fileload.FileCallback;
import com.cxd.cxd4android.manager.fileload.FileResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;


public class CheckVersionDialog extends Dialog {


    private AlertDialog dialog;

    public String content = "";

    public String title = "";

    public String version = "";

    public String isUpdate = "";

    Context context = null;

    View mView;

    public TextView widget_dialog_tv_title;

    public TextView widget_dialog_tv_version;

    public TextView widget_dialog_tv_content;

    public Button widget_dialog_bt_load;

    public Button widget_dialog_bt_next;


    //返回的安装包url
    private String apkUrl = "http://www.51cxd.com/";

    private boolean interceptFlag = false;

    private int progress;

    private Thread downLoadThread;

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;

    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/ChengXinDai/";

    private static final String saveFileName = savePath + "ChengXinDai.apk";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:

//					installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    public CheckVersionDialog(Context context, String apkUrl, String title, String content, String version, String isUpdate) {
        super(context, R.style.mydialog);
        this.context = context;
        this.apkUrl = apkUrl;
        this.isUpdate = isUpdate;
        this.title = title;
        this.version = version;
        this.content = content;
        initView();

    }

    void initView() {
        mView = LayoutInflater.from(this.context).inflate(R.layout.widget_layout_checkversion_dialogs, null);

        widget_dialog_tv_title = (TextView) mView.findViewById(R.id.widget_dialog_tv_title);
        widget_dialog_tv_version = (TextView) mView.findViewById(R.id.widget_dialog_tv_version);
        widget_dialog_tv_content = (TextView) mView.findViewById(R.id.widget_dialog_tv_content);
        widget_dialog_bt_load = (Button) mView.findViewById(R.id.widget_dialog_bt_load);
        widget_dialog_bt_next = (Button) mView.findViewById(R.id.widget_dialog_bt_next);
        mProgress = (ProgressBar) mView.findViewById(R.id.widget_dialog_pg_progress);
        mProgress.setVisibility(View.GONE);

        widget_dialog_bt_load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				mProgress.setVisibility(View.VISIBLE);
//				widget_dialog_bt_load.setClickable(false);
                setCancelable(false);
//				downloadApk();

                checkPhoneGoodsAndBrowser();

//				loadFile();
            }
        });

        widget_dialog_bt_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                interceptFlag = true;
                dismiss();

            }
        });

        if (("1").equals(isUpdate)) {//强制更新
            widget_dialog_bt_next.setVisibility(View.GONE);
            setCancelable(false);
        } else {
            widget_dialog_bt_next.setVisibility(View.VISIBLE);
            setCancelable(true);

        }

        setContentView(mView);
    }

    private Retrofit.Builder retrofit;


    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
            .separator + "M_DEFAULT_DIR";
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "cxd4android.apk";

    public interface IFileLoad {
        @POST("downloads/cxd4android.apk")
        Call<ResponseBody> loadFile();
    }

    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        context.startActivity(install);
    }

    /**
     * 初始化OkHttpClient
     *
     * @return
     */
    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100000, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }

    /**
     * 下载文件
     */
    private void loadFile() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }
        retrofit.baseUrl("http://www.51cxd.com/")
                .client(initOkHttpClient())
                .build()
                .create(IFileLoad.class)
                .loadFile()
                .enqueue(new FileCallback(destFileDir, destFileName, context.getCacheDir()) {

                    @Override
                    public void onSuccess(File file) {
                        Log.e("zs", "请求成功");
                        // 安装软件
//						cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("zs", progress + "----" + total);
//						updateNotification(progress * 100 / total);
                        progress = (int) (((float) progress / total) * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
//						if(progress <= 0){
//							下载完成通知安装
//							mHandler.sendEmptyMessage(DOWN_OVER);
//						}
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("zs", "请求失败");
//						cancelNotification();
                    }
                });
    }

    /**
     * 查看手机中所有的应用市场：
     */
    private void checkPhoneGoodsAndBrowser() {

//        /**  **/
        Uri uris = Uri.parse(apkUrl);
        Intent browser = new Intent(Intent.ACTION_VIEW, uris);
        browser.setData(uris);
        browser.addCategory("android.intent.category.BROWSABLE");
        context.startActivity(browser);

        /** 打开应用商店下载**/
//		Intent startintent = new Intent("android.intent.action.MAIN");
//		startintent.addCategory("android.intent.category.APP_MARKET");
//		startintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(startintent);

        /** 跳转到应用商店下载**/
//		String str = "market://details?id=" + context.getPackageName();
//		Intent localIntent = new Intent("android.intent.action.VIEW");
//		localIntent.setData(Uri.parse(str));
//		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		Log.i("intent--->",localIntent.getPackage()+localIntent.getType()+localIntent.getFlags());
//		if (localIntent.getType()!=null)
//		context.startActivity(localIntent);


        /**调用手机上所有下载器下载**/
//        Uri uri = Uri.parse(apkUrl);
//        Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
//        context.startActivity(downloadIntent);
    }


/*
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.widget_dialog_bt_load:
				break;
			case R.id.widget_dialog_bt_load:
				alertdialog.dismiss();
				break;


			default:
				break;
		}
	}*/

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        widget_dialog_tv_title.setText(title + "");
        widget_dialog_tv_version.setText(version + "");
        widget_dialog_tv_content.setText(content + "");
    }

    /**
     * 下载apk
     *
     * @param
     */

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

//				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				conn.connect();
                int length = (int) response.body().contentLength();
                InputStream is = response.body().byteStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 安装apk
     * @param
     */
//	private void installApk(){
//		File apkfile = new File(saveFileName);
//		if (!apkfile.exists()) {
//			return;
//		}
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(i);
//
//	}
}
