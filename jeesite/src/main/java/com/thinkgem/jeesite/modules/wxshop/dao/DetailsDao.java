package com.thinkgem.jeesite.modules.wxshop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Details;
import com.thinkgem.jeesite.modules.wxshop.entity.Item;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.annotation.Order;

import java.util.List;


@MyBatisDao
public interface DetailsDao extends CrudDao<Details> {

    /**
     * 批量增加订单详情
     * @param all
     * @return
     * @Date: 18:54 2018/4/24
     */
    public boolean insertDetails(List<Details> all) ;

    /**
     * 根据订单编号查询所有订单详情
     * @param oid
     * @return
     */
    public List<Details> findDetails(Integer oid);
}
