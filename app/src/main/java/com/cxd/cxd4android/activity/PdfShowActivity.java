package com.cxd.cxd4android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.model.UserInvestsModel;
import com.cxd.cxd4android.pdfviewer.PDFView;
import com.cxd.cxd4android.pdfviewer.listener.OnLoadCompleteListener;
import com.cxd.cxd4android.pdfviewer.listener.OnPageChangeListener;
import com.cxd.cxd4android.pdfviewer.scroll.DefaultScrollHandle;
import com.cxd.cxd4android.widget.dialog.PromptDialog;
import com.cxd.cxd4android.widget.pdffile.DownloadFile;
import com.cxd.cxd4android.widget.pdffile.DownloadFileUrlConnectionImpl;
import com.cxd.cxd4android.widget.pdffile.FileUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PdfShowActivity extends BaseActivity implements DownloadFile.Listener, OnPageChangeListener, OnLoadCompleteListener {
    @Bind(R.id.Btn_left)
    TextView mBtnLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.Pdf_show_view)
    PDFView mPdfShowView;
    private File mFile;
    private String mAnCunUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_show);
        ButterKnife.bind(this);

        mBtnLeft.setVisibility(View.VISIBLE);
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText("借款合同");
        Bundle extras = getIntent().getExtras();
        UserInvestsModel pdfIntent = (UserInvestsModel) extras.get("PdfIntent");
        String pdfShowIntent = (String) extras.get("PdfShowIntent");
        if ("showPdf".equals(pdfShowIntent)) {
            mAnCunUrl = "http://cxd-pdf.oss-cn-beijing.aliyuncs.com/operationReport/16/operationReport.pdf";
            mTvTitle.setText("审计报告");
        } else {
            //合同的URL
            mAnCunUrl = pdfIntent.contractUrl;

        }

        //新增sd卡权限申请
        //判断手机版本号是否需要申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //新增SD卡读写权限申请
            getPermission();
        } else {
            //下载PDF文件
            if (mAnCunUrl != null) {
                downloadPdf(mAnCunUrl);

            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void getPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100
            );

        } else {

            //下载PDF文件
            if (mAnCunUrl != null) {
                downloadPdf(mAnCunUrl);

            }

        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);

    }

    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == 100) {

            //权限申请成功
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //下载PDF文件
                if (mAnCunUrl != null) {
                    downloadPdf(mAnCunUrl);

                }

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                doShowPermission();
            }
        }
    }


    private void openPdf(File file) {

        mPdfShowView.fromFile(file)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(6)
                .load();

    }

    //风险评估提示
    private void doShowPermission() {

        final PromptDialog makemoney = new PromptDialog(this, R.style.Make_Money);
        makemoney.setCanceledOnTouchOutside(false);
        makemoney.mDialog_view.setVisibility(View.VISIBLE);
        //标题 mResult
        makemoney.dialog_title.setTextSize(16);
        makemoney.title = "权限设置提示";
        makemoney.dialog_imageView.setVisibility(View.INVISIBLE);
        makemoney.dialog_confirm.setVisibility(View.VISIBLE);
        makemoney.dialog_confirm.setText("放弃设置");
        makemoney.dialog_confirm.setTextColor(getResources().getColor(R.color.gray));
        makemoney.dialog_confirm.setBackgroundResource(R.drawable.shape_layout_make_money_promptdialog);
        makemoney.dialog_sure.setVisibility(View.VISIBLE);
        makemoney.dialog_sure.setText("去设置");
        makemoney.dialog_sure.setBackgroundResource(R.drawable.shape_layout_make_money_right_promptdialog);
        makemoney.dialog_sure.setTextColor(getResources().getColor(R.color.btg_global_text_blue));
        makemoney.dialog_content.setVisibility(View.VISIBLE);
        makemoney.content = "尊敬的用户,为了您正常使用,请同意存储空间权限";

        makemoney.dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //放弃赚钱
                makemoney.dismiss();
                finish();
            }
        });

        makemoney.dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去设置
                openAppDetails(PdfShowActivity.this, "com.cxd.cxd4android");

                makemoney.dismiss();
                finish();


            }
        });
        makemoney.show();

    }

    private void downloadPdf(String inPdfUrl) {
        try {
            DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(this, new Handler(), this);
            downloadFile.download(inPdfUrl, new File(Environment.getExternalStorageDirectory(), FileUtil.extractFileNameFromURL(inPdfUrl)).getAbsolutePath());
        } catch (Exception e) {
            finish();
        }
    }


    @Override
    public void onSuccess(String url, final String destinationPath) {

        mFile = new File(destinationPath);
        openPdf(mFile);

    }

    @Override
    public void onFailure(Exception e) {
        T.D("文件下载失败");
        finish();


    }

    @Override
    public void onProgressUpdate(int progress, int total) {


    }

    @OnClick(R.id.Btn_left)
    public void onClick() {
        this.finish();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }


    /**
     * 打开应用详情
     *
     * @param context
     * @param packageName
     */
    public static void openAppDetails(Context context, String packageName) {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + packageName);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);

    }

}
