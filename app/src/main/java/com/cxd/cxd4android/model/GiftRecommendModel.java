package com.cxd.cxd4android.model;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @ClassName:兑换奖品列表
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/24 15:42
 */
public class GiftRecommendModel implements Serializable {


    /**
     * result : [{"position":"\u200b0","id":"17e37a9c69bb4a8da2ac114d65ec24a5","inventory":"\u200b1000","integration":"\u200b100","name":"11（虚拟、兑换）","type":"0","detailImgpath":"/upload/20160924/92886d5499554c7cbcc1d68bee1bb1cc.jpg"},{"position":"\u200b0","id":"c93e9ac356ae45db9437b2cfbfe20e26","inventory":"\u200b100","integration":"\u200b700","name":"5","type":"1","detailImgpath":"/upload/20160524/626e4fe8d76443759709002c7d9c2963.jpg"},{"position":"\u200b0","id":"e237b05e821444c7a2611722d284778b","inventory":"\u200b100","integration":"\u200b600","name":"随意图","type":"1","detailImgpath":"/upload/20160524/ee00f3cc3ae74012adb8ac1e2da3b1c1.jpg"},{"position":"\u200b0","id":"5f9fc7466eb040d8be9e82a37dbc70f3","inventory":"\u200b100","integration":"\u200b500","name":"340*340","type":"1","detailImgpath":"/upload/20160524/34ca720eee124a1eae782eaeb7c05830.png"},{"position":"\u200b0","id":"99620b56a52d44a9b1b471f0cb3b1a12","inventory":"\u200b1000","integration":"\u200b130","name":"剑圣一族 游戏键盘鼠标套装（实物、兑换）","type":"1","detailImgpath":"/upload/20160524/7e9c7ade6b4142898b1dfce8c679561e.jpg"},{"position":"\u200b0","id":"ebbacbcc2c45406185a562572aaf4cc5","inventory":"\u200b1000","integration":"\u200b260","name":"Moikit麦开智能净水壶（实物、兑换）","type":"1","detailImgpath":"/upload/20160524/6df682df3cb34030a54c7dc07497fcdf.jpg"},{"position":"\u200b0","id":"8ccf3b2133e648f981aa808720ceb24b","inventory":"\u200b100","integration":"\u200b2000","name":"欧式油烟机灶具套装（实物、兑换）","type":"1","detailImgpath":"/upload/20160524/9686af12f140473685b19e6dd8099bc2.jpg"},{"position":"\u200b0","id":"467d850c7b1f404793e507d934ea9a49","inventory":"\u200b100","integration":"\u200b1000","name":"努比亚手机（实物、兑换）","type":"1","detailImgpath":"/upload/20160524/12967ae2231c49bbb4638e9a98aef2ca.jpg"},{"position":"\u200b0","id":"d2cb69a90c644471a8d8bb85bd6697c8","inventory":"\u200b5","integration":"\u200b5","name":"魅族1","type":"0","detailImgpath":"/upload/20160524/1c7491d3994b4966b1172120910a270d.png"}]
     * status : ​200
     * msg : 成功
     */

    private String status;
    private String msg;
    /**
     * position : ​0
     * id : 17e37a9c69bb4a8da2ac114d65ec24a5
     * inventory : ​1000
     * integration : ​100
     * name : 11（虚拟、兑换）
     * type : 0
     * detailImgpath : /upload/20160924/92886d5499554c7cbcc1d68bee1bb1cc.jpg
     */

    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private String position;
        private String id;
        private String inventory; //库存
        private String integration;//积分
        private String name;
        private String type;
        private String detailImgpath;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getIntegration() {
            return integration;
        }

        public void setIntegration(String integration) {
            this.integration = integration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDetailImgpath() {
            return detailImgpath;
        }

        public void setDetailImgpath(String detailImgpath) {
            this.detailImgpath = detailImgpath;
        }
    }
}
