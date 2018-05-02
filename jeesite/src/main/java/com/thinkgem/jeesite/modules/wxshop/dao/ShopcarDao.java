package com.thinkgem.jeesite.modules.wxshop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Goods;
import com.thinkgem.jeesite.modules.wxshop.entity.Shopcar;

import java.util.List;
import java.util.Map;
import java.util.Set;

@MyBatisDao
public interface ShopcarDao extends CrudDao<Shopcar> {


    /**
     * 批量保存新的购物车记录
     * @param shopcar
     * @return
     * @Date: 17:09 2018/4/24
     */
    public boolean changeAmount(Shopcar shopcar) ;



    /**
     * 一个用户购买的所有商品的信息
     * @param shopcar
     * @return
     * @Date: 17:13 2018/4/24
     */
    public List<Integer> findByMid(Shopcar shopcar) ;

    /**
     * 一个用户购买的所有商品的信息
     * @param mid
     * @return
     * @Date: 17:13 2018/4/24
     */
    public List<Shopcar> findByMember(String mid) ;

    /**
     * 购买成功后清空购物车信息
     * @param mid
     * @return
     * @Date: 17:09 2018/4/24
     */
    public boolean deleteAll(String mid) ;
}
