package com.thinkgem.jeesite.modules.wxshop.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.dao.ItemDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Item;
import com.thinkgem.jeesite.modules.wxshop.utils.ItemUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class ItemService extends BaseService implements InitializingBean {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private IdentityService identityService;

    /**
     * 获取用户
     * @param iid
     * @return
     */
    public Item getItem(String iid) {
        return ItemUtils.get(iid);
    }

    public String getTitle(String iid) {
        return ItemUtils.get(iid).getTitle();
    }

    public Page<Item> findItem(Page<Item> page, Item item) {
        // 设置分页参数
        item.setPage(page);
        // 执行分页查询
        page.setList(itemDao.findList(item));
        return page;
    }

    public List<Item> findList() {
        List<Item> list = itemDao.findAll();
        return list ;
    }


    @Transactional(readOnly = false)
    public void deleteItem(Item item) {
        itemDao.delete(item);
        // 同步到Activiti
        deleteActivitiItem(item);
    }

    private void deleteActivitiItem(Item item) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        if(item!=null) {
            String itemIid = item.getTitle();//ObjectUtils.toString(user.getId());
            identityService.deleteUser(itemIid);
        }
    }

    @Transactional(readOnly = false)
    public void saveItem(Item item) {
        if (item.getIid()==null){
            //增加用户
            itemDao.insert(item);
        }else{
            // 更新用户数据
            item.preUpdate();
            itemDao.update(item);
        }
        if (item.getIid()!=null){
            // 将当前用户同步到Activiti
            saveActivitiItem(item);

        }
    }

    private void saveActivitiItem(Item item) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        String itemMid = item.getTitle();//ObjectUtils.toString(item.getId());
        org.activiti.engine.identity.User activitiItem = identityService.createUserQuery().userId(itemMid).singleResult();
        if (activitiItem == null) {
            activitiItem = identityService.newUser(itemMid);
        }
        activitiItem.setFirstName(item.getTitle());
        activitiItem.setLastName(StringUtils.EMPTY);
        activitiItem.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiItem);

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
            List<User> itemList = identityService.createUserQuery().list();
            if (itemList.size() == 0){
                Iterator<Item> items = itemDao.findAllList(new Item()).iterator();
                while(items.hasNext()) {
                    saveActivitiItem(items.next());
                }
            }
        }
    }



}
