package com.thinkgem.jeesite.modules.wxshop.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.wxshop.dao.ItemDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Item;

public class ItemUtils {

    private static ItemDao itemDao = SpringContextHolder.getBean(ItemDao.class);

    /**
     * 根据ID获取用户
     *
     * @param iid
     * @return 取不到返回null
     */
    public static Item get(String iid) {
        Item item = new Item();
        item = itemDao.get(iid);
        if (item == null) {
            return null;
        }
        return item;
    }
}
