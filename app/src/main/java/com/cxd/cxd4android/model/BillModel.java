package com.cxd.cxd4android.model;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class BillModel {

    /**
     * balance              账号余额
     * detail
     * frozenMoney          冻结金额
     * id
     * money                金额
     * seqNum               序号
     * time
     * type
     * userId
     * typeInfo         类型：说明如下
     *
     投资成功			invest_success
     管理员干预			admin_operation
     充值成功			recharge_success
     申请借款			apply_loan
     借款申请未通过		refuse_apply_loan
     申请提现			apply_withdraw
     提现申请未通过		refuse_apply_withdraw
     正常还款			normal_repay
     提前还款			advance_repay
     逾期还款			overdue_repay
     借款流标			cancel_loan
     借款撤标			cancel_loan
     借款撤标			withdraw_loan
     借款放款			give_money_to_borrower
     借款放款			GIVE_MONEY_TO_BORROWER
     提现成功			withdraw_success
     投资流标			cancel_invest
     债权转让成功		    transfer
     债权购买成功		    transfer_buy
     众筹投资			raise_invest
     众筹放款			raise_give_money_to_borrower
     众筹流标			raise_cancel_loan
     */
    public String balance = "";

    public String detail = "";

    public String frozenMoney = "";

    public String id = "";

    public String money = "";

    public String seqNum = "";

    public String time = "";

    public String type = "";

    public String typeInfo = "";

    public String userId = "";

    public boolean Clicked = false;

}
