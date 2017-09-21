package com.cxd.cxd4android.shbbank.accountmanagement.shbrevise;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.shbbank.html.SHBHTML;
import com.cxd.cxd4android.shbbank.html.WebViewActivity;
import com.cxd.cxd4android.shbbank.model.SHBReaetBankCard;
import com.cxd.cxd4android.shbbank.model.WebViewModel;
import com.cxd.cxd4android.shbbank.presenter.SHBPresenter;
import com.micros.ui.widget.MicroAVLIDialog;
import com.micros.utils.Q;
import com.micros.utils.T;
import com.micros.utils.X;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by administrator on 17/8/29.
 */

public class SHBReviseInfoFragment extends Fragment implements LoadingView {


    private static final int REQUEST_CODE_CHOOSE = 23;
    @Bind(R.id.fragment_revise_ed_name)
    EditText mFragmentReviseEdName;
    @Bind(R.id.fragment_revise_ed_card)
    EditText mFragmentReviseEdCard;
    @Bind(R.id.shb_fragment_updated_z)
    ImageView mShbFragmentUpdatedZ;
    @Bind(R.id.shb_fragment_updated_f)
    ImageView mShbFragmentUpdatedF;
    @Bind(R.id.shb_fragment_but_open)
    Button mShbFragmentButOpen;
    public String statue = "1";


    @Bind(R.id.shb_fragment_tv_dele)
    TextView mShbFragmentTvDele;
    @Bind(R.id.shb_fragment_tv_dele_two)
    TextView mShbFragmentTvDeleTwo;
    @Bind(R.id.fragment_revise_ed_card_number)
    EditText mFragmentReviseEdCardNumber;
    // oss
    private OSS oss;
    int number;
    public List<String> mPaths;
    // 图片名称
    public List<String> mName;

    // 运行sample前需要配置以下字段为有效的值
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String uploadFilePath = "/storage/emulated/0/DCIM/套图/A_90.jpg";//"<upload_file_path>";

    private static final String testBucket = "cxd-id-card";
    public String uploadObject = "";
    private static final String downloadObject = "cuimeng";
    private SHBReviseBankActivity mShbReviseBankActivity;
    private List<String> mStrings;
    private LocalUserModel mLocalUserModel;
    private SHBPresenter mShbPresenter;
    private MicroAVLIDialog mMicroAVLIDialog;
    Map<String, String> map = new HashMap<>();
    Map<String, String> NameMap = new HashMap<>();
    private String mNameBank;
    private String mIdentity;
    private String mBank;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shb_frament_revise_info, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShbPresenter = new SHBPresenter(this);

        mMicroAVLIDialog = new MicroAVLIDialog(getActivity());
        SetOssPAth();
        mPaths = new ArrayList<>();
        mName = new ArrayList<>();
        mShbReviseBankActivity = (SHBReviseBankActivity) getActivity();
        mLocalUserModel = new LocalUserModel();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.shb_fragment_updated_z, R.id.shb_fragment_updated_f, R.id.shb_fragment_but_open, R.id.shb_fragment_tv_dele, R.id.shb_fragment_tv_dele_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shb_fragment_updated_z:
                statue = "1";
                setPlayPoto();
                break;
            case R.id.shb_fragment_updated_f:
                statue = "2";
                setPlayPoto();
                break;
            case R.id.shb_fragment_but_open:

                if (map.size() < 2) {
                    Toast.makeText(getActivity(), "请上传身份证正反面以便审核", Toast.LENGTH_SHORT).show();
                    return;
                }
                isBoolean();
//                图片上传到后台
                setOssPtoto();
//                换卡接口请求
                setBankCard();
//                判断输入是否有误
                mMicroAVLIDialog.show();

                break;
            case R.id.shb_fragment_tv_dele:
                Glide.with(getActivity()).load("").into(mShbFragmentUpdatedZ);
                mShbFragmentTvDele.setVisibility(View.GONE);
                map.remove("one");

                break;
            case R.id.shb_fragment_tv_dele_two:
                Glide.with(getActivity()).load("").into(mShbFragmentUpdatedF);
                mShbFragmentTvDeleTwo.setVisibility(View.GONE);
                map.remove("two");
                break;
        }
    }

    private void setBankCard() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", mLocalUserModel.getid());
        map.put("idCardDownUrl", NameMap.get("one"));
        map.put("idCardFrontUrl", NameMap.get("two"));
        map.put("realname", mNameBank);
        map.put("idCard", mIdentity);
        map.put("bankcardNo", mBank);
        mShbPresenter.shbREsetBankCard(map);


    }


    //图片上传oss
    public void asyncPutObjectFromLocalFile(int size) {

        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(testBucket, mName.get(size), mPaths.get(size));

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {


            }


        });

        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                number++;

                if (number <= 2) {
                    asyncPutObjectFromLocalFile(1);


                }


            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {

                }
            }
        });


    }


    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(SHBReaetBankCard.class)) {
            mMicroAVLIDialog.dismiss();
            SHBReaetBankCard shbReaetBankCard = (SHBReaetBankCard) model;
            SHBReaetBankCard.ResultBean result = shbReaetBankCard.getResult();
            String autoSubmitForm = SHBHTML.createAutoSubmitForm(result.getUrl(), result.getServiceName(), result.getPlatformNo(), result.getUserDevice(), result.getReqData(), result.getKeySerial(), result.getSign());
            WebViewModel webViewModel = new WebViewModel();
            webViewModel.setType("type_shb_loaddata");
            webViewModel.setTitle("绑定新银行卡");
            webViewModel.setIdentify("shresetBankCard");
            webViewModel.setUrl(autoSubmitForm);
            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("WebViewModel", webViewModel));
            getActivity().finish();

        }

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    //初始化Oss
    public void SetOssPAth() {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("LTAI236ICbqtrc6W", "SxBfAuxrd2VmfRTTs1kmWiIv80bxWI", "");

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getContext(), endpoint, credentialProvider, conf);
    }

    //开始上传图片
    public void setOssPtoto() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                asyncPutObjectFromLocalFile(0);


            }
        }).start();

    }


    //图片选择回调
    void setPtotoPath(int requestCode, int resultCode, Intent data) {
        mStrings = Matisse.obtainPathResult(data);
        if (statue.equals("1")) {
//            图片本地路径
            map.put("one", mStrings.get(0));
//            上传后台图片名称编号
            NameMap.put("one", mLocalUserModel.getid() + "_z");

            Glide.with(getActivity()).load(new File(mStrings.get(0))).into(mShbFragmentUpdatedZ);
            mName.add(mLocalUserModel.getid() + "_z");


            mPaths.add(mStrings.get(0));
            mShbFragmentTvDele.setVisibility(View.VISIBLE);

        } else if (statue.equals("2")) {
//            图片本地路径
            map.put("two", mStrings.get(0));
//            上传后台图片名称编号
            NameMap.put("two", mLocalUserModel.getid() + "_f");
            Glide.with(getActivity()).load(new File(mStrings.get(0))).into(mShbFragmentUpdatedF);
            mPaths.add(mStrings.get(0));
            mName.add(mLocalUserModel.getid() + "_f");
            mShbFragmentTvDeleTwo.setVisibility(View.VISIBLE);
        }


    }


    //吊起相机拍照
    public void setPlayPoto() {
        Matisse.from(getActivity())
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    public void isBoolean() {
//        名字
        mNameBank = mFragmentReviseEdName.getText().toString().trim();
//        身份证
        mIdentity = mFragmentReviseEdCard.getText().toString().trim();

//        银行卡号
        mBank = mFragmentReviseEdCardNumber.getText().toString().trim();
        if (Q.isEmpty(mNameBank)) {
            new T(getActivity()).D("请输入真实姓名");
            return;
        }
        if (Q.isEmpty(mIdentity)) {
            new T(getActivity()).D("请输入身份证号码");
            return;
        }
        if (Q.isEmpty(mBank)) {
            new T(getActivity()).D("请输入银行卡号");
            return;

        }
        if (!Q.isAllChinese(mNameBank)) {
            new T(getActivity()).D("请输入正确姓名");
            return;
        }
        if (!X.checkBankCard(mBank)) {
            new T(getActivity()).D("请输入正确银行卡号");
            return;
        }
        if (!X.isIDCardVerify(mIdentity)) {
            new T(getActivity()).D("请输入正确身份证号");
            return;

        }


    }
}
