package com.thinkgem.jeesite.modules.wxshop.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.dao.GoodsDao;
import com.thinkgem.jeesite.modules.wxshop.dao.ItemDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Goods;
import com.thinkgem.jeesite.modules.wxshop.utils.GoodsUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GoodsService extends BaseService implements InitializingBean {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private IdentityService identityService;


    public List<Goods> findAllGoods(List<Integer> gids){
        return goodsDao.findAllByGid(gids) ;
    }

    /**
     * 获取用户
     *
     * @param gid
     * @return
     */
    public Goods getGoods(String gid) {
        return GoodsUtils.get(gid);
    }

    @Transactional(readOnly = false)
    public Goods showGoods(String gid) {
        Goods goods = new Goods();
        goods = GoodsUtils.get(gid);
        goodsDao.doUpdateBow(goods.getGid());
        return goods;
    }

    public String getTitle(String gid) {
        return GoodsUtils.get(gid).getTitle();
    }

    public Page<Goods> findGoods(Page<Goods> page, Goods goods) {
        // 设置分页参数
        goods.setPage(page);
        // 执行分页查询
        page.setList(goodsDao.findList(goods));
        return page;
    }

    public Page<Goods> frontFindGoods(Page<Goods> page, Goods goods) {
        // 设置分页参数
        goods.setPage(page);
        // 执行分页查询
        page.setList(goodsDao.findAllList(goods));
        return page;
    }


    @Transactional(readOnly = false)
    public void deleteGoods(Goods goods) {
        goodsDao.delete(goods);
        // 同步到Activiti
        deleteActivitiGoods(goods);
    }

    private void deleteActivitiGoods(Goods goods) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (goods != null) {
            String goodsGid = goods.getTitle();//ObjectUtils.toString(user.getId());
            identityService.deleteUser(goodsGid);
        }
    }

    @Transactional(readOnly = false)
    public void saveGoods(Goods goods) {
        if (goods.getGid() == null) {
            //增加用户
            goodsDao.insert(goods);
        } else {
            // 更新用户数据
            goods.preUpdate();
            goodsDao.update(goods);
        }
        if (goods.getGid() != null) {
            // 将当前用户同步到Activiti
            saveActivitiGoods(goods);

        }
    }

    private void saveActivitiGoods(Goods goods) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        String goodsMid = goods.getTitle();//ObjectUtils.toString(goods.getId());
        User activitiGoods = identityService.createUserQuery().userId(goodsMid).singleResult();
        if (activitiGoods == null) {
            activitiGoods = identityService.newUser(goodsMid);
        }
        activitiGoods.setFirstName(goods.getTitle());
        activitiGoods.setLastName(StringUtils.EMPTY);
        activitiGoods.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiGoods);

    }

    /**
     * 是需要同步Activiti数据，如果从未同步过，则同步数据。
     */
    private static boolean isSynActivitiIndetity = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (isSynActivitiIndetity) {
            isSynActivitiIndetity = false;
            // 同步用户数据
            List<User> goodsList = identityService.createUserQuery().list();
            if (goodsList.size() == 0) {
                Iterator<Goods> goodss = goodsDao.findAllList(new Goods()).iterator();
                while (goodss.hasNext()) {
                    saveActivitiGoods(goodss.next());
                }
            }
        }
    }
}
