package com.cxd.cxd4android.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseApplication;
import com.cxd.cxd4android.global.LocalUserModel;
import com.micros.ui.webview.SecurityJsBridgeBundle;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName:騰訊X5內核自定義webview類
 * Description：
 * Author：XiaoFa
 * Date：2016/6/29 16:40
 * version：V1.0
 */
public class X5WebView extends X5BaseWebview {

    public static final int FILE_CHOOSER = 0;

    private static boolean isSmallWebViewDisplayed = false;
    private Map<String, Object> mJsBridges;
    TextView title;
    private ProgressBar mPageLoadingProgressBar = null;
    private static final String TAG = "SdkDemo";

    private Context mContext;
    LocalUserModel userModel;

    /**
     * x5webview构造函数
     *
     * @param arg0
     */
    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(0);
    }

    /**
     * x5webview构造函数
     *
     * @param arg0
     * @param arg1
     */
    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        mContext = arg0;

        if (userModel == null) {
            userModel = new LocalUserModel();
        }

        setBackgroundColor(0);
        initProgressBar();     //设置加载进度条
        this.setWebViewClientExtension(new X5WebViewEventHandler(this));// 配置X5webview的事件处理
        this.setWebChromeClient(chromeClient);
        this.setVisibility(VISIBLE);  // 加载完之后进行设置显示，以免加载时初始化效果不好看
        WebStorage webStorage = WebStorage.getInstance();
        this.getView().setClickable(true);
        this.getView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }


    /**
     * 设置WebChromeClient事件
     */
    private WebChromeClient chromeClient = new WebChromeClient() {




        @Override
        public void onReceivedTitle(WebView view, String title) {
            TbsLog.d(TAG, "title: " + title);
        }


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            mPageLoadingProgressBar.setProgress(newProgress);
            if (mPageLoadingProgressBar != null && newProgress != 100) {
                mPageLoadingProgressBar.setVisibility(View.VISIBLE);
            } else if (mPageLoadingProgressBar != null) {
                mPageLoadingProgressBar.setVisibility(View.GONE);
            }
        }



        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;

        ///////////////////////////////////////////////////////////
        //

        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
//			FrameLayout normalView = (FrameLayout) ((Activity) getContext()).findViewById(R.id.web_filechooser);
//			ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//			viewGroup.removeView(normalView);
//			viewGroup.addView(view);
            myVideoView = view;
//			myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }



        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            ((Activity) (X5WebView.this.getContext())).startActivityForResult(Intent.createChooser(i, "choose files"),
                    X5WebView.FILE_CHOOSER);
            super.openFileChooser(uploadFile, acceptType, captureType);
        }






        /**
         * webview 的窗口转移
         */
        @Override
        public boolean onCreateWindow(WebView arg0, boolean arg1, boolean arg2, Message msg) {
            // TODO Auto-generated method stub
            if (X5WebView.isSmallWebViewDisplayed) {

                WebViewTransport webViewTransport = (WebViewTransport) msg.obj;
                WebView webView = new WebView(X5WebView.this.getContext()) {

                    protected void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        Paint paint = new Paint();
                        paint.setColor(Color.GREEN);
                        paint.setTextSize(15);
                        canvas.drawText("新建窗口", 10, 10, paint);
                    }

                    ;
                };
                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                        Map<String, String> requestHeaders = webResourceRequest.getRequestHeaders();
                        requestHeaders.put("TOKEN", userModel.getHeader());
                        requestHeaders.put("sourceCode", BaseApplication.mApp.getApplicationMetaData("CXD_CHANNEL_ID"));
                        requestHeaders.put("sourceType", "1");
                        requestHeaders.put("useVersion", userModel.getAppVersionName());
                        requestHeaders.put("thirdPay", userModel.getThirdPayType());//判断账户类型--华兴或易宝
                        requestHeaders.put("clientid", "android-" + userModel.getAppVersionName());
                        Logger.i(webResourceRequest.getRequestHeaders().get(0));

                        return super.shouldInterceptRequest(webView, webResourceRequest);
                    }


                    public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
                        arg0.loadUrl(arg1);
                        return true;
                    }


                });
                LayoutParams lp = new LayoutParams(400, 600);
                lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
                X5WebView.this.addView(webView, lp);
                webViewTransport.setWebView(webView);
                msg.sendToTarget();
            }
            return true;
        }

        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
            /**
             * 这里写入你自定义的window alert
             */
             /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
             builder.setTitle("X5内核");
			 builder.setPositiveButton("确定", new
			 DialogInterface.OnClickListener() {

			 @Override
			 public void onClick(DialogInterface dialog, int which) {
			 // TODO Auto-generated method stub
			 dialog.dismiss();
			 }
			 });
			 builder.show();
			 arg3.confirm();
			 return true;*/
//            com.orhanobut.logger.Logger.i("yuanhaizhou" + "setX5webview = null");
            Logger.i("onJsAlert","arg1="+arg1+"arg2"+arg2+"arg3="+arg3);
            return super.onJsAlert(arg0, arg1, arg2, arg3);
        }

        /**
         * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
         */
        @Override
        public boolean onJsPrompt(WebView arg0, String arg1, String arg2, String arg3, JsPromptResult arg4) {
            // 在这里可以判定js传过来的数据，用于调起android native 方法
            if (X5WebView.this.isMsgPrompt(arg1)) {
                if (X5WebView.this.onJsPrompt(arg2, arg3)) {
                    return true;
                } else {
                    return false;
                }
            }
            return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
        }
    };


    /**
     * 初始化加载条
     */
    private void initProgressBar() {

        //生成水平进度条
        mPageLoadingProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mPageLoadingProgressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, 3)));
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));
        addView(mPageLoadingProgressBar);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setSmallWebViewEnabled(boolean enabled) {
        isSmallWebViewDisplayed = enabled;
    }


    public void addJavascriptBridge(SecurityJsBridgeBundle jsBridgeBundle) {
        if (this.mJsBridges == null) {
            this.mJsBridges = new HashMap<String, Object>(5);
        }

        if (jsBridgeBundle != null) {
            String tag = SecurityJsBridgeBundle.BLOCK + jsBridgeBundle.getJsBlockName() + "-"
                    + SecurityJsBridgeBundle.METHOD + jsBridgeBundle.getMethodName();
            this.mJsBridges.put(tag, jsBridgeBundle);
        }
    }

    /**
     * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
     *
     * @param methodName 方法名称
     * @param blockName  区块名称
     * @return true ：调用成功 ； false ：调用失败
     */
    private boolean onJsPrompt(String methodName, String blockName) {
        String tag = SecurityJsBridgeBundle.BLOCK + blockName + "-" + SecurityJsBridgeBundle.METHOD + methodName;

        if (this.mJsBridges != null && this.mJsBridges.containsKey(tag)) {
            ((SecurityJsBridgeBundle) this.mJsBridges.get(tag)).onCallMethod();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判定当前的prompt消息是否为用于调用native方法的消息
     *
     * @param msg 消息名称
     * @return true 属于prompt消息方法的调用
     */
    private boolean isMsgPrompt(String msg) {
        if (msg != null && msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)) {
            return true;
        } else {
            return false;
        }
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_dispatchTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_dispatchTouchEvent(ev);
        return r;
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_onInterceptTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_onInterceptTouchEvent(ev);
        return r;
    }

    protected void tbs_onScrollChanged(int l, int t, int oldl, int oldt, View view) {
        super_onScrollChanged(l, t, oldl, oldt);
    }

    protected void tbs_onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {
        super_onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    protected void tbs_computeScroll(View view) {
        super_computeScroll();
    }

    protected boolean tbs_overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {
        return super_overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    public void setTitle(TextView title) {
        this.title = title;


    }

    protected boolean tbs_onTouchEvent(MotionEvent event, View view) {

        return super_onTouchEvent(event);
    }




}
