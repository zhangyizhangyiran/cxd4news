package com.cxd.cxd4android.model;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/15 20:44
 * version：V1.0
 */
public class NewersModel {

//{"result":{"dictLoanStatus":"马上抢","progress":689700,"investTime":null,"status":"投标中","loanPurpose":"诚信理财计划CXD15112601","minInvestMoney":100,"cardinalNumber":100,"businessType":"个人借款","repayType":null,"newerLoanTip":"[新手专享]每人限投3次","loanSYTime":"1天6时39分","id":"20151126002463","loanMoney":"100","unit":"天","exceptTime":"2015-11-26","rate":"20","remainTime":"1天6时39分","description":"项目名称诚信理财计划CXD15112601项目说明   为实现用户收益稳健增值，诚信贷自主推出“诚信理财计划”帮助中小企业改善并缓解短期经营融资困难(企业\\个人短期银行过桥业务、电商供应链等)进行分散筛选，协助投资人将加入资金出借给优质借款客户，为投资理财用户量身定制的一项分散投资理财计划，用户加入计划，即可享受安全、便捷的理财服务。  “诚信理财计划”所有标的均适用于诚信贷本金安全保障计划，满足用户更便捷灵活安全的投资需求。投资人购买“诚信理财计划”产品之后，标的投满审核通过后，即进入投资锁定期，在投资锁定期间内，用户不能提前退出诚信理财计划。投资到期后，系统将自动退出并一次性返还本息至投资人账户。投资范围提供100％本息担保的借款项目计息方式到期还本付息，按天计息年息收益率20%项目计息时间8天  自2015年11月27日0：00开始计算利息——2015年12月4日23:59结束。                  \r\n提示:凡是投资8天理财项目的客户，计息时间为T+1计算；理财收益是根据自己的投资实际天数来确定的；项目还款时间2015年12月5日(当天24小时内)：本金+利息，还款到投资理财个人账户中，会有短信提示。费用加入费用：0元管理费用：0元退出费用：0元开始加入时间2015年11月26日 项目风控100%本息担保所有理财用户的每笔出借资金均在诚信贷100%本息保障计划覆盖之内，一旦出现逾期坏账，诚信贷通过风险备用金1000万优先垫付，保证理财人的资金安全。诚信贷平台上参与交易的各方资金都通过第三方托管账户(易宝支付)全程托管，账户资金都必须经过本人确认才能转出，真正做到安全透明，既可以从法律的层面保障交易的合法性，又能使用户账户的资金100%安全。","userId":"wangsheng1975","name":"诚信理财计划CXD15112601","money":0,"remainMoney":"-689600.0","deadline":8,"loanTZProgress":689700},"status":200,"msg":"成功"}

    /**
     * 投标进度
     */
//    public String dictLoanStatus;
    /**
     *
     */
    public String progress = "";
    /**
     *
     */
    public String investTime = "";
    /**
     *
     */
    public String status = "";
    /**
     * 借款用途
     */
    public String loanPurpose = "";
    /**
     * 最小投资金额
     */
    public String minInvestMoney = "";
    /**
     * 投资递增金额
     */
    public String cardinalNumber = "";
    /**
     * 商业类型（企业、个人）
     */
    public String businessType = "";
    /**
     * 还款方式
     */
    public String repayType = "";
    /**
     *
     */
    public String newerLoanTip = "";
    /**
     * 标状态
     */
//    public String loanSYTime;
    /**
     * Fields
     */
    public String id = "";
    /**
     * 预计的借款金额
     */
    public String loanMoney = "";
    /**
     * 期限单位
     */
    public String unit = "";
    /**
     * 期限单位
     */
    public String exceptTime = "";
    /**
     * 利率(如果是竞标借款，则为最高能接受利率)，注意，此处存储的不是百分比利率
     */
    public String rate = "";
    /**
     * 期限单位
     */
    public String remainTime = "";
    /**
     * 企业详述
     */
    public String description = "";
    /**
     *
     */
    public String userId = "";
    /**
     * 借款名称
     */
    public String name = "";
    /**
     * 实际借到的金额（放款金额），放款后才有值
     */
    public String money = "";
    /**
     * 实际借到的金额（放款金额），放款后才有值
     */
    public String remainMoney = "";
    /**
     * 借款期限
     */
    public String deadline = "";
    /**
     *
     */
//    public String loanTZProgress;
}
