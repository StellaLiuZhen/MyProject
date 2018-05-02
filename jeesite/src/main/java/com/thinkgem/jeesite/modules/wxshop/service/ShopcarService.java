package com.thinkgem.jeesite.modules.wxshop.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.dao.ShopcarDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Shopcar;
import com.thinkgem.jeesite.modules.wxshop.utils.ShopcarUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ShopcarService extends BaseService implements InitializingBean {

    @Autowired
    private ShopcarDao shopcarDao;

    @Autowired
    private IdentityService identityService;

    public boolean findMidAndGid(Shopcar shopcar){
        shopcar = shopcarDao.get(shopcar);
        if(shopcar==null){
            return false;
        } else{
            return true;
        }
    }

    @Transactional(readOnly = false)
    public void addShopcar(Shopcar shopcar) {
        shopcarDao.insert(shopcar);
    }

    @Transactional(readOnly = false)
    public void updateShopcar(Shopcar shopcar) {
        shopcarDao.update(shopcar);
    }

    @Transactional(readOnly = false)
    public void changeShopcar(Shopcar shopcar) {
        shopcarDao.changeAmount(shopcar);
    }

    @Transactional(readOnly = false)
    public List<Shopcar> findShopcar(Shopcar shopcar) {
        return shopcarDao.findList(shopcar);
    }












    /**
     * 获取用户
     * @param iid
     * @return
     */
    public Shopcar getShopcar(String iid) {
        return ShopcarUtils.get(iid);
    }

    public String getTitle(String iid) {
        return ShopcarUtils.get(iid).getId();
    }

    public Page<Shopcar> findShopcar(Page<Shopcar> page, Shopcar shopcar) {
        // 设置分页参数
        shopcar.setPage(page);
        // 执行分页查询
        page.setList(shopcarDao.findList(shopcar));
        return page;
    }

    public List<Shopcar> findshopcarList(Shopcar shopcar) {
        List<Shopcar> list = shopcarDao.findList(shopcar);
        return list ;
    }

    public List<Integer> findByMid(Shopcar shopcar) {
        List<Integer> list = shopcarDao.findByMid(shopcar);
        return list ;
    }


    @Transactional(readOnly = false)
    public void deleteShopcar(Shopcar shopcar) {
        shopcarDao.delete(shopcar);
        // 同步到Activiti
        deleteActivitiShopcar(shopcar);
    }

    private void deleteActivitiShopcar(Shopcar shopcar) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        if(shopcar!=null) {
            String shopcarIid = shopcar.getId();//ObjectUtils.toString(user.getId());
            identityService.deleteUser(shopcarIid);
        }
    }

    @Transactional(readOnly = false)
    public void saveShopcar(Shopcar shopcar) {
        if (shopcar.getMember().getMid()==null){
            //增加用户
            shopcarDao.insert(shopcar);
        }else{
            // 更新用户数据
            shopcar.preUpdate();
            shopcarDao.update(shopcar);
        }
        if (shopcar.getMember().getMid()!=null){
            // 将当前用户同步到Activiti
            saveActivitiShopcar(shopcar);

        }
    }

    private void saveActivitiShopcar(Shopcar shopcar) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        String shopcarMid = shopcar.getMember().getMid();//ObjectUtils.toString(shopcar.getId());
        User activitiShopcar = identityService.createUserQuery().userId(shopcarMid).singleResult();
        if (activitiShopcar == null) {
            activitiShopcar = identityService.newUser(shopcarMid);
        }
        activitiShopcar.setFirstName(shopcar.getId());
        activitiShopcar.setLastName(StringUtils.EMPTY);
        activitiShopcar.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiShopcar);

    }

    /**
     * 是需要同步Activiti数据，如果从未同步过，则同步数据。
     */
    private static boolean isSynActivitiIndetity = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        if (isSynActivitiIndetity){
            isSynActivitiIndetity = false;
            // 同步用户数据
            List<User> shopcarList = identityService.createUserQuery().list();
            if (shopcarList.size() == 0){
                Iterator<Shopcar> shopcars = shopcarDao.findAllList(new Shopcar()).iterator();
                while(shopcars.hasNext()) {
                    saveActivitiShopcar(shopcars.next());
                }
            }
        }
    }



}
