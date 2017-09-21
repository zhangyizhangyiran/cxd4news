package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class BindCardQueryModel implements Serializable{

    /**
     * bankName         银行名称
     * status           银行绑定状态
     * iconUrl          银行卡图片地址
     * cardNo           银行卡号
     * realname         姓名
     * idCard           身份证号
     * baofoo_limit_des 限额描述
     * baofoo_limit_money 限额
     */
    public String bankName = "";

    public String status = "";

    public String iconUrl = "";

    public String cardNo = "";

    public String realname = "";

    public String idCard = "";

    public String baofoo_limit_des = "";

    public String baofoo_limit_money = "";

    @Override
    public String toString() {
        return "BindCardQueryModel{" +
                "bankName='" + bankName + '\'' +
                ", status='" + status + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", realname='" + realname + '\'' +
                ", idCard='" + idCard + '\'' +
                ", baofoo_limit_des='" + baofoo_limit_des + '\'' +
                ", baofoo_limit_money='" + baofoo_limit_money + '\'' +
                '}';
    }
}
