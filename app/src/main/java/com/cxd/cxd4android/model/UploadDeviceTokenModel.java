package com.cxd.cxd4android.model;

/**
 * Created by John on 2016/12/2.
 */

public class UploadDeviceTokenModel {
    /**
     * 结果信息/错误信息
     */
    public String msg = "";
    /**
     * status分为以下状态:
     * 200: 调用成功
     * 500: 预期失败
     * 404: 系统错误
     */
    public String status = "";
    /**
     * 数据
     */
    public  OtherModel result;
    class OtherModel {
    }
}
