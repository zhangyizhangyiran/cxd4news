package com.cxd.cxd4android.shbbank.model;

/**
 * Created by administrator on 17/9/11.
 */

public class SHBBankResultInfo {

    /**
     * status : 200
     * code : 200
     * msg : 成功
     * result : {"apply_aging_desc":null,"apply_aging_user":null,"apply_bankcard":null,"apply_create_time":"2017-09-05 10:43:54","origunal_bankcard":"1235677896546321345","apply_status":"PASSED","id_card_down_url":"zh_z","apply_id":"wlsnow","apply_user_name":"王统江","device":"MOBILE","request_no":"0","apply_describe":null,"id_card_front_url":"zh_f","id":"29","apply_aging_time":null}
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
         * apply_aging_desc : null
         * apply_aging_user : null
         * apply_bankcard : null
         * apply_create_time : 2017-09-05 10:43:54
         * origunal_bankcard : 1235677896546321345
         * apply_status : PASSED
         * id_card_down_url : zh_z
         * apply_id : wlsnow
         * apply_user_name : 王统江
         * device : MOBILE
         * request_no : 0
         * apply_describe : null
         * id_card_front_url : zh_f
         * id : 29
         * apply_aging_time : null
         */

        private String apply_aging_desc;
        private String apply_aging_user;
        private String apply_bankcard;
        private String apply_create_time;
        private String origunal_bankcard;
        private String apply_status;
        private String id_card_down_url;
        private String apply_id;
        private String apply_user_name;
        private String device;
        private String request_no;
        private String apply_describe;
        private String id_card_front_url;
        private String id;
        private String apply_aging_time;

        public String getApply_aging_desc() {
            return apply_aging_desc;
        }

        public void setApply_aging_desc(String apply_aging_desc) {
            this.apply_aging_desc = apply_aging_desc;
        }

        public Object getApply_aging_user() {
            return apply_aging_user;
        }

        public void setApply_aging_user(String apply_aging_user) {
            this.apply_aging_user = apply_aging_user;
        }

        public Object getApply_bankcard() {
            return apply_bankcard;
        }

        public void setApply_bankcard(String apply_bankcard) {
            this.apply_bankcard = apply_bankcard;
        }

        public String getApply_create_time() {
            return apply_create_time;
        }

        public void setApply_create_time(String apply_create_time) {
            this.apply_create_time = apply_create_time;
        }

        public String getOrigunal_bankcard() {
            return origunal_bankcard;
        }

        public void setOrigunal_bankcard(String origunal_bankcard) {
            this.origunal_bankcard = origunal_bankcard;
        }

        public String getApply_status() {
            return apply_status;
        }

        public void setApply_status(String apply_status) {
            this.apply_status = apply_status;
        }

        public String getId_card_down_url() {
            return id_card_down_url;
        }

        public void setId_card_down_url(String id_card_down_url) {
            this.id_card_down_url = id_card_down_url;
        }

        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

        public String getApply_user_name() {
            return apply_user_name;
        }

        public void setApply_user_name(String apply_user_name) {
            this.apply_user_name = apply_user_name;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getRequest_no() {
            return request_no;
        }

        public void setRequest_no(String request_no) {
            this.request_no = request_no;
        }

        public Object getApply_describe() {
            return apply_describe;
        }

        public void setApply_describe(String apply_describe) {
            this.apply_describe = apply_describe;
        }

        public String getId_card_front_url() {
            return id_card_front_url;
        }

        public void setId_card_front_url(String id_card_front_url) {
            this.id_card_front_url = id_card_front_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getApply_aging_time() {
            return apply_aging_time;
        }

        public void setApply_aging_time(String apply_aging_time) {
            this.apply_aging_time = apply_aging_time;
        }
    }
}
