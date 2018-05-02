package com.thinkgem.jeesite.modules.wxshop.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.wxshop.dao.OrdersDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Orders;

public class OrdersUtils {

    private static OrdersDao ordersDao = SpringContextHolder.getBean(OrdersDao.class);

    /**
     * 根据ID获取用户
     *
     * @param iid
     * @return 取不到返回null
     */
    public static Orders get(String iid) {
        Orders orders = new Orders();
        orders = ordersDao.get(iid);
        if (orders == null) {
            return null;
        }
        return orders;
    }
}
