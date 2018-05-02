package com.thinkgem.jeesite.modules.wxshop.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.wxshop.dao.ShopcarDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Shopcar;

public class ShopcarUtils {

    private static ShopcarDao itemDao = SpringContextHolder.getBean(ShopcarDao.class);

    /**
     * 根据ID获取用户
     *
     * @param iid
     * @return 取不到返回null
     */
    public static Shopcar get(String iid) {
        Shopcar item = new Shopcar();
        item = itemDao.get(iid);
        if (item == null) {
            return null;
        }
        return item;
    }
}
