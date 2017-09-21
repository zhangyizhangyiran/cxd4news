package com.cxd.cxd4android.shbbank.model;

import java.io.Serializable;

/**
 * Created by administrator on 17/9/2.
 */

public class SHBBankPersonInfo implements Serializable {


    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : {"bindcard_status":"APPLY","user_id":"wlsnow","bindcard_id":"14","mobile":"12345678901","id_card":"140881198931270047","real_name":"测试用户","create_time":"2017-09-01 15:33:26","bank_card_no":"6228480402564890018","is_bind_card":"NONE","id":"25","id_card_type":"PRC_ID"}
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

    public static class ResultBean implements Serializable {
        /**
         * bindcard_status : APPLY
         * user_id : wlsnow
         * bindcard_id : 14
         * mobile : 12345678901
         * id_card : 140881198931270047
         * real_name : 测试用户
         * create_time : 2017-09-01 15:33:26
         * bank_card_no : 6228480402564890018
         * is_bind_card : NONE
         * id : 25
         * id_card_type : PRC_ID
         */

        private String bindcard_status;
        private String user_id;
        private String bindcard_id;
        private String mobile;
        private String id_card;
        private String real_name;
        private String create_time;
        private String bank_card_no;
        private String is_bind_card;
        private String id;
        private String id_card_type;

        public String getBindcard_status() {
            return bindcard_status;
        }

        public void setBindcard_status(String bindcard_status) {
            this.bindcard_status = bindcard_status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBindcard_id() {
            return bindcard_id;
        }

        public void setBindcard_id(String bindcard_id) {
            this.bindcard_id = bindcard_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getBank_card_no() {
            return bank_card_no;
        }

        public void setBank_card_no(String bank_card_no) {
            this.bank_card_no = bank_card_no;
        }

        public String getIs_bind_card() {
            return is_bind_card;
        }

        public void setIs_bind_card(String is_bind_card) {
            this.is_bind_card = is_bind_card;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_card_type() {
            return id_card_type;
        }

        public void setId_card_type(String id_card_type) {
            this.id_card_type = id_card_type;
        }

    }
}
