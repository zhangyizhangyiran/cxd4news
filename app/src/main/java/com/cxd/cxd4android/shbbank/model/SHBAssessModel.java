package com.cxd.cxd4android.shbbank.model;

import java.io.Serializable;

/**
 * Created by chengelhaltud on 17/9/3.
 */

public class SHBAssessModel implements Serializable {


    /**
     * result : {"assessTimes":"1","assessType":"保守型","assessTypeDesc":"您的风险承受能力较低，投资时你较为保守，谨慎尝试，对投资的稳定性和资产的流动性要求比较高。","lastAssessTime":"2017-07-21 11:35:21"}
     * status : 200
     * msg : SUCCESS
     */

    private ResultBean result;
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

    public static class ResultBean implements Serializable {
        /**
         * assessTimes : 1
         * assessType : 保守型
         * assessTypeDesc : 您的风险承受能力较低，投资时你较为保守，谨慎尝试，对投资的稳定性和资产的流动性要求比较高。
         * lastAssessTime : 2017-07-21 11:35:21
         */

        private String assessTimes;
        private String assessType;
        private String assessTypeDesc;
        private String lastAssessTime;

        public String getAssessTimes() {
            return assessTimes;
        }

        public void setAssessTimes(String assessTimes) {
            this.assessTimes = assessTimes;
        }

        public String getAssessType() {
            return assessType;
        }

        public void setAssessType(String assessType) {
            this.assessType = assessType;
        }

        public String getAssessTypeDesc() {
            return assessTypeDesc;
        }

        public void setAssessTypeDesc(String assessTypeDesc) {
            this.assessTypeDesc = assessTypeDesc;
        }

        public String getLastAssessTime() {
            return lastAssessTime;
        }

        public void setLastAssessTime(String lastAssessTime) {
            this.lastAssessTime = lastAssessTime;
        }
    }
}
