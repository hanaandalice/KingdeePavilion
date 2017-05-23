package com.zipingfang.jindiexuan.module_home.model;

/**
 * Created by Administrator on 2017/5/23.
 */

public class CateGoodsModel {

    /**
     * gid : 1
     * cate_id : 1
     * goods_name : 蛋糕
     * weight : 2.00
     * pic : /data/upload/images/2017-05-23/222.png
     * is_new : 1
     * is_hot : 1
     */

    private String gid;
    private String cate_id;
    private String goods_name;
    private String weight;
    private String pic;
    private String is_new;
    private String is_hot;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }
}
