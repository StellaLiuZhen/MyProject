package com.thinkgem.jeesite.modules.wxshop.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

public class Item extends DataEntity<Item> {
    private Integer iid ;
    private String title ;

    private List<Goods> allGoods ;

    public List<Goods> getAllGoods() {
        return allGoods;
    }

    public void setAllGoods(List<Goods> allGoods) {
        this.allGoods = allGoods;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
