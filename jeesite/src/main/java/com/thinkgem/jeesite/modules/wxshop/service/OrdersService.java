package com.thinkgem.jeesite.modules.wxshop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.dao.*;
import com.thinkgem.jeesite.modules.wxshop.entity.*;
import com.thinkgem.jeesite.modules.wxshop.utils.OrdersUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrdersService extends BaseService implements InitializingBean {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ShopcarDao shopcarDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private DetailsDao detailsDao;

    @Autowired
    private IdentityService identityService;

    /**
     * 获取用户
     *
     * @param iid
     * @return
     */
    public Orders getOrders(Integer iid) {
        Orders orders = new Orders();
        orders = OrdersUtils.get(iid.toString());
        orders.setAllDetails(detailsDao.findDetails(iid));
        return orders;
    }

    public String getOid(String iid) {
        return Integer.toString(OrdersUtils.get(iid).getOid());
    }


    @Transactional(readOnly = false)
    public void deleteOrders(Orders orders) {
        ordersDao.delete(orders);
        // 同步到Activiti
        deleteActivitiOrders(orders);
    }

    private void deleteActivitiOrders(Orders orders) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (orders != null) {
            String ordersOid = Integer.toString(orders.getOid());//ObjectUtils.toString(user.getId());
            identityService.deleteUser(ordersOid);
        }
    }

    @Transactional(readOnly = false)
    public void saveOrders(Orders orders) {

        // 更新用户数据
        ordersDao.updateOrders(orders);

//        if (orders.getOid() != null) {
//            // 将当前用户同步到Activiti
//            saveActivitiOrders(orders);
//
//        }
    }

    private void saveActivitiOrders(Orders orders) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        String ordersMid = Integer.toString(orders.getOid());//ObjectUtils.toString(orders.getId());
        User activitiOrders = identityService.createUserQuery().userId(ordersMid).singleResult();
        if (activitiOrders == null) {
            activitiOrders = identityService.newUser(ordersMid);
        }
        activitiOrders.setFirstName(Integer.toString(orders.getOid()));
        activitiOrders.setLastName(StringUtils.EMPTY);
        activitiOrders.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiOrders);

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
            List<User> ordersList = identityService.createUserQuery().list();
            if (ordersList.size() == 0) {
                Iterator<Orders> orderss = ordersDao.findAllList(new Orders()).iterator();
                while (orderss.hasNext()) {
                    saveActivitiOrders(orderss.next());
                }
            }
        }
    }


    @Transactional(readOnly = false)
    public String insertOrders(String mid) {
        boolean flag = true;
        String msg = null;
        //1、首先判断出当前用户信息是否完整，要根据mid查询一个用户的完整信息
        Member member = memberDao.get(mid);
        if (StringUtils.isBlank(member.getPhone()) || StringUtils.isBlank(member.getAddress()) || StringUtils.isBlank(member.getName()) ) {
            msg = "用户信息不完整,请补充完整！";
            flag = false;
            return msg;
        }
        //2、如果信息完整，判断数据库中是否包含购物车信息
        List<Shopcar> shopcars = shopcarDao.findByMember(mid);
        if (shopcars.isEmpty()) {
            msg = "购物车没有商品";
            flag = false;
            return msg;
        }
        //3、如果现在要是有购物信息，则查寻出所有的商品信息
        Shopcar shopcar = new Shopcar();
        shopcar.setMember(member);
        List<Integer> gids = shopcarDao.findByMid(shopcar);
        List<Goods> allGoods = goodsDao.findAllByGid(gids);
        //4、判断要购买的商品库存是否存在
        Iterator<Shopcar> iterCars = shopcars.iterator();
        //5、同时计算出总的花费金额
        double pay = 0.0;
        while (iterCars.hasNext()) {
            Shopcar car = iterCars.next();
            //获取当前商品信息
            Integer gid = car.getGoods().getGid();
            Goods good = goodsDao.getAmount(gid);
            Integer amount = good.getAmount();
            double price = good.getPrice();
            if (amount - car.getAmount() < 0) {
                msg = "没有足够的出售数量";
                flag = false;
                return msg;
            } else {
                System.out.println("找到商品信息和购物车信息");
                //计算总价格
                pay += price * car.getAmount();
            }
        }
        //6、创建订单对象
        Orders orders = new Orders();
        orders.setMember(member);
        System.out.println("======="+orders.getMember().getName());
        orders.setPhone(member.getPhone());
        orders.setAddress(member.getAddress());
        orders.setCredate(new Date());
        orders.setPay(pay);
        orders.setDelivery("未发货");
        ordersDao.insert(orders);
        //7、取得当前增长的订单编号，因为订单详情需要这个编号
        orders.setOid(ordersDao.findLastInsertId());
        //8、创建订单详情信息
        iterCars = null;
        iterCars = shopcars.iterator();
        List<Details> all = new ArrayList<Details>();
        while (iterCars.hasNext()) {
            Details detail = new Details();
            Shopcar car = iterCars.next();
            //获取当前商品信息
            Integer gid = car.getGoods().getGid();
            Goods good = goodsDao.getAmount(gid);
            Integer amount = good.getAmount();
            double price = good.getPrice();
            //订单详情与商品的关系
            detail.setGoods(good);
            //订单详情与订单的关系
            detail.setOrders(orders);
            detail.setAmount(car.getAmount());
            detail.setTitle(good.getTitle());
            detail.setPrice(good.getPrice());
            all.add(detail);
            //9、更新商品库存
            flag = goodsDao.doUpdateAmount(good.getGid(), car.getAmount());
            System.out.println(flag);
        }
        flag = detailsDao.insertDetails(all);
        //10、清空购物车信息
        flag = shopcarDao.deleteAll(mid);
        msg = "下单成功";
        return msg;
    }

    /**
     * 根据用户编号，分页列出所有的订单信息
     *
     * @param
     * @return
     * @Date: 23:15 2018/5/1
     */
    public Page<Orders> findOrdersByMid(Page<Orders> page, Orders orders) {
        // 设置分页参数
        orders.setPage(page);
        // 执行分页查询
        page.setList(ordersDao.findList(orders));
        return page;
    }

    /**
     * @param mid
     * @param oid
     * @return
     * @Date: 23:44 2018/5/1
     */
    public Orders findByIdAndMid(String mid, Integer oid) {
        Orders orders = new Orders();
        orders.setOid(oid);
        Member member = new Member();
        member.setMid(mid);
        orders.setMember(member);
        orders = ordersDao.get(orders);
        orders.setAllDetails(detailsDao.findDetails(oid));
        return orders;
    }


    /**
     * 管理员查询所有订单
     *
     * @param
     * @return
     * @Date: 3:10 2018/5/2
     */
    public Page<Orders> findOrders(Page<Orders> page, Orders orders) {
        // 设置分页参数
        orders.setPage(page);
        // 执行分页查询
        page.setList(ordersDao.findOrders(orders));
        return page;
    }

    @Transactional(readOnly = false)
    public boolean upDelivery(Orders orders) {
        boolean flag = true;
        orders.setDelivery("已发货");
        ordersDao.update(orders);
        return flag;
    }

}
