package com.thinkgem.jeesite.modules.wxshop.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

public class Orders extends DataEntity<Orders> {
    private Integer oid ;
    private Member member ;
    private String phone ;
    private String address;
    private Date credate ;
    private Double pay ;
    private List<Details> allDetails ;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCredate() {
        return credate;
    }

    public void setCredate(Date credate) {
        this.credate = credate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public List<Details> getAllDetails() {
        return allDetails;
    }

    public void setAllDetails(List<Details> allDetails) {
        this.allDetails = allDetails;
    }
}
