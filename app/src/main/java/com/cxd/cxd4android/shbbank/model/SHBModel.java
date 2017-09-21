package com.cxd.cxd4android.shbbank.model;

/**
 * Created by chengelhaltud on 17/9/1.
 */

public class SHBModel {

    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : {"serviceName":"PERSONAL_REGISTER_EXPAND","platformNo":"9100000705","userDevice":null,"reqData":"{\"bankcardNo\":\"www\",\"checkType\":\"LIMIT\",\"idCardNo\":\"ww\",\"idCardType\":\"PRC_ID\",\"mobile\":\"ww\",\"platformUserNo\":\"1503904034563\",\"realName\":\"ww\",\"redirectUrl\":\"http://www.baidu.com\",\"requestNo\":\"1504165763579\",\"timestamp\":\"20170831154923\",\"userRole\":\"INVESTOR\"}","keySerial":"1","sign":"LRNLUNDc/DNcjhZnLw1secfRjiMqPvGRmOxlJOIgGTcSyJMQkW9KenGpgOsIGOtPEFTJ9XioOI38PEyK4343c9Wk6rju/2yAjj1rXkINOKFKLP2/DcZ7TnVXPU+2zaFVHjXXgs5AddeM2ZfHKD5SA9sZiAlc2r3yIbcX8Vslc39OVo3kxiWIwhXpmKfW4eIwT5gG91YFLIwZhVAR7Di1GRyzCsmyhgF+jmdyhDjjOc+cEpbYjS7joO3oNqeSHMP0qwBeXbEblVT0D32kps7C06mAWz0XTgxhUONjjeZ7wIeAnpc6NrCZ04ExmjWV16KjghwQ8BWcl4G3zk0E1vkeUA==","url":"https://shbk.lanmaoly.com/bha-neo-app/lanmaotech/gateway"}
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
         * serviceName : PERSONAL_REGISTER_EXPAND
         * platformNo : 9100000705
         * userDevice : null
         * reqData : {"bankcardNo":"www","checkType":"LIMIT","idCardNo":"ww","idCardType":"PRC_ID","mobile":"ww","platformUserNo":"1503904034563","realName":"ww","redirectUrl":"http://www.baidu.com","requestNo":"1504165763579","timestamp":"20170831154923","userRole":"INVESTOR"}
         * keySerial : 1
         * sign : LRNLUNDc/DNcjhZnLw1secfRjiMqPvGRmOxlJOIgGTcSyJMQkW9KenGpgOsIGOtPEFTJ9XioOI38PEyK4343c9Wk6rju/2yAjj1rXkINOKFKLP2/DcZ7TnVXPU+2zaFVHjXXgs5AddeM2ZfHKD5SA9sZiAlc2r3yIbcX8Vslc39OVo3kxiWIwhXpmKfW4eIwT5gG91YFLIwZhVAR7Di1GRyzCsmyhgF+jmdyhDjjOc+cEpbYjS7joO3oNqeSHMP0qwBeXbEblVT0D32kps7C06mAWz0XTgxhUONjjeZ7wIeAnpc6NrCZ04ExmjWV16KjghwQ8BWcl4G3zk0E1vkeUA==
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
}
