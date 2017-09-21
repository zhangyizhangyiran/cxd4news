package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName: 奖品兑换保存
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/27 10:43
 */
public class SaveGiftsModel implements Serializable {

    private String markFragment;
    private String giftNum;
    private String giftProductId;
    private String giftProductName;

    public String getMarkFragment() {
        return markFragment;
    }

    public void setMarkFragment(String markFragment) {
        this.markFragment = markFragment;
    }

    public String getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(String giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(String giftProductId) {
        this.giftProductId = giftProductId;
    }

    public String getGiftProductName() {
        return giftProductName;
    }

    public void setGiftProductName(String giftProductName) {
        this.giftProductName = giftProductName;
    }
}
