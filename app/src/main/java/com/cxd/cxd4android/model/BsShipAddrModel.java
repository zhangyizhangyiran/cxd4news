package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName:保存用户收货地址
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/25 20:04
 */
public class BsShipAddrModel implements Serializable{


    /**
     * id : 123eer
     * username : 王晓发
     * address : 万达广场
     * userId : wlsnow
     * mobileNumber : ​18600360000
     */

    private ResultBean result;
    /**
     * result : {"id":"123eer","username":"王晓发","address":"万达广场","userId":"wlsnow","mobileNumber":"\u200b18600360000"}
     * status : ​200
     * msg : 成功
     */

    private String status;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean implements Serializable{
        private String id;
        private String username;
        private String address;
        private String userId;
        private String mobileNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }
    }
}
