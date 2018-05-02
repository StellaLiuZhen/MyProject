package com.thinkgem.jeesite.modules.wxshop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Goods;
import com.thinkgem.jeesite.modules.wxshop.entity.Shopcar;

import java.util.List;
import java.util.Set;


@MyBatisDao
public interface GoodsDao extends CrudDao<Goods> {
    /**
     * 更新访问次数，每次调用访问次数加1
     * @param id
     * @return
     * @Date: 16:04 2018/4/23
     */
    public boolean doUpdateBow(Integer id);

    /**
     * 查询指定编号的所有商品信息
     * @param
     * @return
     * @Date: 18:54 2018/4/24
     */
    public List<Goods> findAllByGid(List<Integer> gids) ;

    /**
     * 更新库存
     * @param gid
     * @param num
     * @return
     * @Date: 16:04 2018/4/23
     */
    public boolean doUpdateAmount(Integer gid ,Integer num);

    /**
     * 获取当前商品库存
     * @param gid
     * @return
     * @Date: 16:04 2018/4/23
     */
    public Goods getAmount(Integer gid);
}
