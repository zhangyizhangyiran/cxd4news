package com.cxd.cxd4android.shbbank.model;

import java.util.List;

/**
 * Created by administrator on 17/9/1.
 */

public class SHBQueryBankList {


    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : [{"bankName":"广发银行","bankCode":"GDBK","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/GDB.png"},{"bankName":"民生银行","bankCode":"MSBC","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/CMBC.png"},{"bankName":"光大银行","bankCode":"EVER","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/CEB.png"},{"bankName":"华夏银行","bankCode":"HXBK","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/HX.png"},{"bankName":"中信银行","bankCode":"CIBK","bankDescription":"单笔限额5千元,单日限额5千元","bankUrl":"http://api.51cxd.cn/public/images/bank/ECITIC.png"},{"bankName":"北京银行","bankCode":"BJCN","bankDescription":"单笔限额5千元,单日限额5千元","bankUrl":"http://api.51cxd.cn/public/images/bank/BCCB.png"},{"bankName":"浦发银行","bankCode":"SPDB","bankDescription":"单笔限额10万元,单日限额30万元","bankUrl":"http://api.51cxd.cn/public/images/bank/SPDB.png"},{"bankName":"邮政银行","bankCode":"PSBC","bankDescription":"单笔限额5千元,单日限额5千元","bankUrl":"http://api.51cxd.cn/public/images/bank/PSBC.png"},{"bankName":"工商银行","bankCode":"ICBK","bankDescription":"单笔限额5万元,单日限额5万元","bankUrl":"http://api.51cxd.cn/public/images/bank/ICBC.png"},{"bankName":"平安银行","bankCode":"SZDB","bankDescription":"单笔限额5万元,单日限额5万元","bankUrl":"http://api.51cxd.cn/public/images/bank/SDB.png"},{"bankName":"招商银行","bankCode":"CMBC","bankDescription":"单笔限额5万元,单日限额5万元","bankUrl":"http://api.51cxd.cn/public/images/bank/CMBCHINA.png"},{"bankName":"兴业银行","bankCode":"FJIB","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/CIB.png"},{"bankName":"交通银行","bankCode":"COMM","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/BOCO.png"},{"bankName":"中国银行","bankCode":"BKCH","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/BOC.png"},{"bankName":"建设银行","bankCode":"PCBC","bankDescription":"单笔限额10万元,单日限额50万元","bankUrl":"http://api.51cxd.cn/public/images/bank/CBC.png"},{"bankName":"上海银行","bankCode":"BOSH","bankDescription":"单笔限额5千元,单日限额5千元","bankUrl":"http://api.51cxd.cn/public/images/bank/SHYH.png"},{"bankName":"农业银行","bankCode":"ABOC","bankDescription":"单笔限额10万元,单日限额20万元","bankUrl":"http://api.51cxd.cn/public/images/bank/ABC.png"}]
     */

    private String status;
    private String code;
    private String msg;
    private List<SHBBankLIstModel> result;

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

    public List<SHBBankLIstModel> getResult() {
        return result;
    }

    public void setResult(List<SHBBankLIstModel> result) {
        this.result = result;
    }

    public static class SHBBankLIstModel {
        /**
         * bankName : 广发银行
         * bankCode : GDBK
         * bankDescription : 单笔限额10万元,单日限额50万元
         * bankUrl : http://api.51cxd.cn/public/images/bank/GDB.png
         */

        private String bankName;
        private String bankCode;
        private String bankDescription;
        private String bankUrl;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankDescription() {
            return bankDescription;
        }

        public void setBankDescription(String bankDescription) {
            this.bankDescription = bankDescription;
        }

        public String getBankUrl() {
            return bankUrl;
        }

        public void setBankUrl(String bankUrl) {
            this.bankUrl = bankUrl;
        }
    }

    @Override
    public String toString() {
        return "SHBQueryBankList{" +
                "status=" + status +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
