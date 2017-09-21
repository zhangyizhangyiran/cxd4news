package com.cxd.cxd4android.shbbank.ossupload;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengelhaltud on 17/8/28.
 */
public class MultipartUploadSamples {

    private String asyncLog = "asyncMultipartUpload";
    private String syncLog = "MultipartUpload";

    private OSS oss;
    private String testBucket;
    private String testObject;
    private String uploadFilePath;

    //目前有多少个task
    private int asyncTaskCount = 0;
    //异步处理需要的锁对象
    private Object lock = new Object();

    public MultipartUploadSamples(OSS client, String testBucket, String testObject, String uploadFilePath) {
        this.oss = client;
        this.testBucket = testBucket;
        this.testObject = testObject;
        this.uploadFilePath = uploadFilePath;
    }

    public void multipartUpload() throws ClientException, ServiceException, IOException {

        long startTime  = System.currentTimeMillis();

        String uploadId;

        InitiateMultipartUploadRequest init = new InitiateMultipartUploadRequest(testBucket, testObject);
        InitiateMultipartUploadResult initResult = oss.initMultipartUpload(init);

        uploadId = initResult.getUploadId();

        //分片大小为2m
        long partSize = 10 * 1024 * 1024;

        int currentIndex = 1;

        File uploadFile = new File(uploadFilePath);
        InputStream input = new FileInputStream(uploadFile);
        long fileLength = uploadFile.length();

        long uploadedLength = 0;
        List<PartETag> partETags = new ArrayList<PartETag>();
        while (uploadedLength < fileLength) {
            int partLength = (int)Math.min(partSize, fileLength - uploadedLength);
            byte[] partData = IOUtils.readStreamAsBytesArray(input, partLength);

            UploadPartRequest uploadPart = new UploadPartRequest(testBucket, testObject, uploadId, currentIndex);
            uploadPart.setPartContent(partData);
            UploadPartResult uploadPartResult = oss.uploadPart(uploadPart);
            partETags.add(new PartETag(currentIndex, uploadPartResult.getETag()));

            uploadedLength += partLength;
            currentIndex++;
        }

        CompleteMultipartUploadRequest complete = new CompleteMultipartUploadRequest(testBucket, testObject, uploadId, partETags);
        CompleteMultipartUploadResult completeResult = oss.completeMultipartUpload(complete);

        Log.d(syncLog, "multipart upload success!success Location: " + completeResult.getLocation());

        Log.d(syncLog, "multipartUpload end spend time " + (System.currentTimeMillis() - startTime));
    }


    public void asyncMultipartUpload() throws ClientException, ServiceException, IOException {

        long startTime  = System.currentTimeMillis();
        Log.d(asyncLog, "asyncMultipartUpload start");

        String uploadId;

        InitiateMultipartUploadRequest init = new InitiateMultipartUploadRequest(testBucket, testObject);
        InitiateMultipartUploadResult initResult = oss.initMultipartUpload(init);

        uploadId = initResult.getUploadId();


        //分片大小
        long partSize = 10 * 1024 * 1024;

        int currentIndex = 1;



        File uploadFile = new File(uploadFilePath);
        InputStream input = new FileInputStream(uploadFile);
        long fileLength = uploadFile.length();


        long chucklength = fileLength % partSize == 0 ? fileLength / partSize : fileLength / partSize + 1;

        Log.d(asyncLog, "chucklength : " + chucklength);

        long uploadedLength = 0;
        final List<PartETag> partETags = new ArrayList<PartETag>();

        while (uploadedLength < fileLength) {
            int partLength = (int)Math.min(partSize, fileLength - uploadedLength);
            byte[] partData = IOUtils.readStreamAsBytesArray(input, partLength);

            UploadPartRequest uploadPart = new UploadPartRequest(testBucket, testObject, uploadId, currentIndex);
            uploadPart.setPartContent(partData);


            // 设置成功、失败回调，发送异步请求
            OSSAsyncTask task = oss.asyncUploadPart(uploadPart, new OSSCompletedCallback<UploadPartRequest, UploadPartResult>() {
                @Override
                public void onSuccess(UploadPartRequest request, UploadPartResult result) {
                    //onSuccess 也是在异步线程中调用，对于partETags的操作需要同步
                    synchronized(lock){
                        Log.d(asyncLog, "PartNumber ： " + request.getPartNumber() + " Success! \n" + " ETag ：" + result.getETag());
                        int partNumber = request.getPartNumber();
                        int index = -1;
                        for (int i = 0; i < partETags.size() ; i++){
                            PartETag partETag = partETags.get(i);
                            if (partNumber < partETag.getPartNumber()){
                                index = i;
                                break;
                            }
                        }
                        if(index > 0){
                            partETags.add(index,new PartETag(partNumber, result.getETag()));
                        }else {
                            partETags.add(new PartETag(partNumber, result.getETag()));
                        }
                        asyncTaskCount --;
                    }
                }

                @Override
                public void onFailure(UploadPartRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常
                    if (clientExcepion != null) {
                        // 本地异常如网络异常等
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        // 服务异常
                        Log.e("ErrorCode", serviceException.getErrorCode());
                        Log.e("RequestId", serviceException.getRequestId());
                        Log.e("HostId", serviceException.getHostId());
                        Log.e("RawMessage", serviceException.getRawMessage());
                    }
                }
            });

            uploadedLength += partLength;
            currentIndex++;
            asyncTaskCount ++;
            Log.d(asyncLog, "currentIndex : " + currentIndex);

            try{
                while (asyncTaskCount > OSSConstants.DEFAULT_BASE_THREAD_POOL_SIZE * 2){
                    Log.d(asyncLog, "asyncTaskCount : " + asyncTaskCount);
                    synchronized(lock) {
                        lock.wait(1000);
                    }
                }
            }catch(Exception e){

            }
        }
        try{
            while (partETags.size() < chucklength){
                Log.d(asyncLog, "partETags.size() : " + partETags.size());
                synchronized(lock) {
                    lock.wait(500);
                }
            }
        }catch(Exception e){

        }

        Log.d(asyncLog, "all task Success!");


        CompleteMultipartUploadRequest complete = new CompleteMultipartUploadRequest(testBucket, testObject, uploadId, partETags);
        CompleteMultipartUploadResult completeResult = oss.completeMultipartUpload(complete);

        Log.d(asyncLog, "multipart upload success! Location: " + completeResult.getLocation());
        Log.d(asyncLog, "asyncUploadPart end spend time " + (System.currentTimeMillis() - startTime));
    }

}
