package com.cxd.cxd4android.model;

/**
 * @version V1.0
 * @ClassName:请求结果model
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/25 20:22
 */
public class RequestResultModel {

    /**
     * status : ​200
     * msg : 成功
     */

    private String status;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
