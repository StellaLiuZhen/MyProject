package com.thinkgem.jeesite.modules.wxshop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Item;
import com.thinkgem.jeesite.modules.wxshop.entity.Orders;

import java.util.List;


@MyBatisDao
public interface OrdersDao extends CrudDao<Orders> {

    /**
     * 调用last_insert_id()函数取得当前增长后的订单编号，为订单详情准备
     * @return 返回最后的自动增长编号
     * @Date: 3:09 2018/5/1
     */
    public Integer findLastInsertId() ;

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<Orders> findOrders(Orders entity);

}
