package com.cxd.cxd4android.model;

import java.util.List;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/16 10:35
 * version：V1.0
 */
public class InvesListBaseModel {

    /**
     * 结果信息/错误信息
     */
    public String msg = "";
    /**
     * status分为以下状态:
     * 200: 调用成功
     * 500: 预期失败
     * 404: 系统错误
     */
    public String status = "";
    /**
     * 数据
     */
    public List<InvesListModel> result ;
}
