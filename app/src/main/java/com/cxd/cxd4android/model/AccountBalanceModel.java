package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class AccountBalanceModel implements Serializable{
   
    /**
     * totalMoney                   资产总额
     * balance                      账户总额
     * balanceAvaliable             可用余额
     * collectCorpusAndInterest     待收本息
     * freezeAmount                 冻结金额
     * totalInvestAmount            累计投资金额
     * collectProfit                预计总收益
     * receivedCorpus               已收本金
     * receviedInterest             已收利息
     * collectCorpus                待收本金
     * collectInterest              待收利息
     */



    public String totalMoney = "";

    public String balance = "";

    public String balanceAvaliable  = "";

    public String collectCorpusAndInterest = "";

    public String freezeAmount = "";

    public String totalInvestAmount = "";

    public String collectProfit = "";

    public String receivedCorpus = "";

    public String receviedInterest = "";

    public String collectCorpus = "";

    public String collectInterest = "";
    /**
     *     获取用户余额接口增加
     * 是否在华兴提现过
     * 1 提现过
     * 0 未提现
     */
    private String isGhbWithdraw;

    /**
     * N未激活 Y已激活，未激活调用激活按钮，Y状态判断是否开户逻辑
     */
    public String active = "";


    @Override
    public String toString() {
        return "AccountBalanceModel{" +
                "active='" + active + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", balance='" + balance + '\'' +
                ", balanceAvaliable='" + balanceAvaliable + '\'' +
                ", collectCorpusAndInterest='" + collectCorpusAndInterest + '\'' +
                ", freezeAmount='" + freezeAmount + '\'' +
                ", totalInvestAmount='" + totalInvestAmount + '\'' +
                ", collectProfit='" + collectProfit + '\'' +
                ", receivedCorpus='" + receivedCorpus + '\'' +
                ", receviedInterest='" + receviedInterest + '\'' +
                ", collectCorpus='" + collectCorpus + '\'' +
                ", collectInterest='" + collectInterest + '\'' +
                ", isGhbWithdraw='" + isGhbWithdraw + '\'' +
                '}';
    }
}
