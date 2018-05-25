package com.thinkgem.jeesite.modules.wxshop.web;


import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;
import com.thinkgem.jeesite.modules.wxshop.entity.Orders;
import com.thinkgem.jeesite.modules.wxshop.service.OrdersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = "${adminPath}/wxshop/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    @RequiresPermissions("wxshop:orders:view")
    @RequestMapping(value = {"index"})
    public String index(Orders orders, Model model) {
        return "modules/wxshop/ordersIndex";
    }


    @RequiresPermissions("wxshop:orders:view")
    @RequestMapping(value = {"list", ""})
    public String list(Orders orders, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Orders> page = ordersService.findOrders(new Page<Orders>(request,response),orders) ;
        model.addAttribute("page", page);
        return "modules/wxshop/ordersList";
    }

    @RequiresPermissions("wxshop:orders:view")
    @RequestMapping(value = "form")
    public String form(Orders orders, Model model) {
        if (orders.getOid()!=null){
            orders = ordersService.getOrders(orders.getOid());
        }
        model.addAttribute("orders", orders);
        return "modules/wxshop/ordersForm";
    }

    @RequiresPermissions("wxshop:orders:edit")
    @RequestMapping(value = "formOrders")
    public String formOrders(Orders orders, Model model) {
        if (orders.getOid()!=null){
            orders = ordersService.getOrders(orders.getOid());
        }
        model.addAttribute("orders", orders);
        return "modules/wxshop/ordersUpdate";
    }

    @RequiresPermissions("wxshop:orders:edit")
    @RequestMapping(value = "ordersUpdate")
    public String ordersUpdate(Orders orders, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(Global.isDemoMode()){
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }

        // 保存用户信息
        ordersService.saveOrders(orders);
        addMessage(redirectAttributes, "保存用户'" + orders.getOid() + "'成功");
        return "redirect:" + adminPath + "/wxshop/orders/list?repage";
    }

    @RequestMapping(value = "upDelivery")
    public String upDelivery(Orders orders, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        ordersService.upDelivery(orders);
        if (orders.getOid()!=null){
            orders = ordersService.getOrders(orders.getOid());
        }
        model.addAttribute("orders", orders);
        return "modules/wxshop/ordersForm";
    }

    @RequestMapping(value = {"ordersList", ""})
    public String ordersList(Member member, HttpServletRequest request, HttpServletResponse response, Model model) {
        Orders orders = new Orders();
        orders.setMember(member);
        Page<Orders> page = ordersService.findOrdersByMid(new Page<Orders>(request, response), orders);
        model.addAttribute("page", page);
        return "modules/wxshop/ordersList";
    }

}
