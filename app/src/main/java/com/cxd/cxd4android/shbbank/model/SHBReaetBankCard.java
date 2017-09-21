package com.cxd.cxd4android.shbbank.model;

/**
 * Created by administrator on 17/9/3.
 */

public class SHBReaetBankCard {

    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : {"serviceName":"PERSONAL_BIND_BANKCARD_EXPAND","platformNo":"9100000705","userDevice":"MOBILE","reqData":"{\"bindType\":\"UPDATE_BANKCARD\",\"checkType\":\"LIMIT\",\"platformUserNo\":\"1503904034563\",\"redirectUrl\":\"http://dev.api.51cxd.cn/shh5c/callback?type=invest\",\"requestNo\":\"1504406007895\",\"timestamp\":\"20170903103327\"}","keySerial":"1","sign":"YA+wBG+bXkd9RLbEuqRn+xWeM94nzzl8HKej7F4QYabPh/Q6mrW9qXhKLnP/rPZe6mhqWB7qeF7oXPqqzBL694+Bmhdi7SWDj8gZQTgNNBFJa+vcAZ8cGWZ0ORGKlauoTLqfnGM7k4vmTQ+DBGppMzORtzmAbHwb3GEewRQciu6aaqDrZoUWfW2IXRKMgBueJgPlxF5TefLhMffgyyzrUu0At8NYI5e4c4mKKKExI+j9jGQiT52nPX8J5g74rxMPo+mJvSRBf+6XEVa/4x2wIRpk7Fceer0zrHI6p2yN6+KsdezkXgxkHR+g8n7YwoVJhpRvAbhJBtHW90t/efp1MA==","url":"https://shbk.lanmaoly.com/bha-neo-app/lanmaotech/gateway"}
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
         * serviceName : PERSONAL_BIND_BANKCARD_EXPAND
         * platformNo : 9100000705
         * userDevice : MOBILE
         * reqData : {"bindType":"UPDATE_BANKCARD","checkType":"LIMIT","platformUserNo":"1503904034563","redirectUrl":"http://dev.api.51cxd.cn/shh5c/callback?type=invest","requestNo":"1504406007895","timestamp":"20170903103327"}
         * keySerial : 1
         * sign : YA+wBG+bXkd9RLbEuqRn+xWeM94nzzl8HKej7F4QYabPh/Q6mrW9qXhKLnP/rPZe6mhqWB7qeF7oXPqqzBL694+Bmhdi7SWDj8gZQTgNNBFJa+vcAZ8cGWZ0ORGKlauoTLqfnGM7k4vmTQ+DBGppMzORtzmAbHwb3GEewRQciu6aaqDrZoUWfW2IXRKMgBueJgPlxF5TefLhMffgyyzrUu0At8NYI5e4c4mKKKExI+j9jGQiT52nPX8J5g74rxMPo+mJvSRBf+6XEVa/4x2wIRpk7Fceer0zrHI6p2yN6+KsdezkXgxkHR+g8n7YwoVJhpRvAbhJBtHW90t/efp1MA==
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

        @Override
        public String toString() {
            return "ResultBean{" +
                    "serviceName='" + serviceName + '\'' +
                    ", platformNo='" + platformNo + '\'' +
                    ", userDevice='" + userDevice + '\'' +
                    ", reqData='" + reqData + '\'' +
                    ", keySerial='" + keySerial + '\'' +
                    ", sign='" + sign + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
