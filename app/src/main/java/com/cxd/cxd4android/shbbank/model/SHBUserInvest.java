package com.cxd.cxd4android.shbbank.model;

/**
 * Created by administrator on 17/9/3.
 */

public class SHBUserInvest {

    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : {"serviceName":"USER_PRE_TRANSACTION","platformNo":"9100000705","userDevice":"MOBILE","reqData":"{\"amount\":\"100\",\"bizType\":\"TENDER\",\"expired\":\"20170903174818\",\"platformUserNo\":\"1503904034563\",\"projectNo\":\"2017083100000004\",\"redirectUrl\":\"http://dev.api.51cxd.cn/shh5c/callback?type=invest\",\"requestNo\":\"1504430298864\",\"timestamp\":\"20170903171818\"}","keySerial":"1","sign":"leIfJ1vVNSlFuBdLmaMbHtsKVV/ICg06gG4th3tIb7YYIYjGztAhqAZ4nm7f5MKBmRFh8/Gtr3I/13RNMkAQ785IoIWCmvU2dNQhxAbMgnWrLmeWIf+jsNdZNbknpmdnvnvMio5o2ZtoiYoEsrlkH2i86lVhY2iBdMJ0CJUedpqdu3DTs3LsuGUKMfWos6I1E14cr+8fcbmnLBIZCEWn6/MOISG4x6JyWjqSoPs5a4pSC9U2oBzzhQFcVWNJCqfPvzkvMJD7Ek1HNnEPnOyub3f0tk4J3Gi3E+nAvM4IQgJsRvM7tvL6RDGqmw9eCiHE6PZtNJ5oQPLQHzH2wVi2eA==","url":"https://shbk.lanmaoly.com/bha-neo-app/lanmaotech/gateway"}
     */

    private String status;
    private String code;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * serviceName : USER_PRE_TRANSACTION
         * platformNo : 9100000705
         * userDevice : MOBILE
         * reqData : {"amount":"100","bizType":"TENDER","expired":"20170903174818","platformUserNo":"1503904034563","projectNo":"2017083100000004","redirectUrl":"http://dev.api.51cxd.cn/shh5c/callback?type=invest","requestNo":"1504430298864","timestamp":"20170903171818"}
         * keySerial : 1
         * sign : leIfJ1vVNSlFuBdLmaMbHtsKVV/ICg06gG4th3tIb7YYIYjGztAhqAZ4nm7f5MKBmRFh8/Gtr3I/13RNMkAQ785IoIWCmvU2dNQhxAbMgnWrLmeWIf+jsNdZNbknpmdnvnvMio5o2ZtoiYoEsrlkH2i86lVhY2iBdMJ0CJUedpqdu3DTs3LsuGUKMfWos6I1E14cr+8fcbmnLBIZCEWn6/MOISG4x6JyWjqSoPs5a4pSC9U2oBzzhQFcVWNJCqfPvzkvMJD7Ek1HNnEPnOyub3f0tk4J3Gi3E+nAvM4IQgJsRvM7tvL6RDGqmw9eCiHE6PZtNJ5oQPLQHzH2wVi2eA==
         * url : https://shbk.lanmaoly.com/bha-neo-app/lanmaotech/gateway
         */

        private String serviceName;
        private String platformNo;
        private String userDevice;
        private String reqData;
        private String keySerial;
        private String sign;
        private String url;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getPlatformNo() {
            return platformNo;
        }

        public void setPlatformNo(String platformNo) {
            this.platformNo = platformNo;
        }

        public String getUserDevice() {
            return userDevice;
        }

        public void setUserDevice(String userDevice) {
            this.userDevice = userDevice;
        }

        public String getReqData() {
            return reqData;
        }

        public void setReqData(String reqData) {
            this.reqData = reqData;
        }

        public String getKeySerial() {
            return keySerial;
        }

        public void setKeySerial(String keySerial) {
            this.keySerial = keySerial;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "SHBUserInvest{" +
                "status=" + status +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
