package com.thinkgem.jeesite.modules.wxshop.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.wxshop.dao.GoodsDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Goods;

public class GoodsUtils {

    private static GoodsDao goodsDao = SpringContextHolder.getBean(GoodsDao.class);

    /**
     * 根据ID获取用户
     *
     * @param gid
     * @return 取不到返回null
     */
    public static Goods get(String gid) {
        Goods goods = new Goods();
        goods = goodsDao.get(gid);
        if (goods == null) {
            return null;
        }
        return goods;
    }
}
