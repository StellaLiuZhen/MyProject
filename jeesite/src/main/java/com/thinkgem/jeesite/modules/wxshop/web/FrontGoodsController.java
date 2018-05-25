package com.thinkgem.jeesite.modules.wxshop.web;


import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wxshop.entity.*;
import com.thinkgem.jeesite.modules.wxshop.filter.CookieUtil;
import com.thinkgem.jeesite.modules.wxshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping(value = "front")
public class FrontGoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private MemberServiceBack memberService;

    @Autowired
    private OrdersService ordersService;


    @RequestMapping(value = {"index"})
    public String index(Goods goods, Model model) {
        return "modules/wxshop/login";
    }


    @RequestMapping(value = {"login"})
    public String login(Member member, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(member.getMid())) {
            member = memberService.getMember(member.getMid());
            if (member != null) {
                request.getSession().setAttribute("mid", member.getMid());
                if (request.getParameter("reme") != null) {  //表示选择了复选框
                    int expiry = Integer.parseInt(request.getParameter("reme"));
                    CookieUtil.save(response, request, "mid", member.getMid(), expiry);
                }
                if (member.getDelFlag().equals("1")) {
                    model.addAttribute("member", member);
                    return "modules/wxshop/front/index";
                } else {
                    request.getSession().invalidate();
                    request.setAttribute("msg","账号已被锁定");
                    return "modules/wxshop/login";
                }
            } else {
                return "modules/wxshop/login";
            }
        } else if (request.getSession().getAttribute("mid") != null) {
            String mid = (String) request.getSession().getAttribute("mid");
            member.setMid(mid);
            member = memberService.getMember(member.getMid());
            model.addAttribute("member", member);
            return "modules/wxshop/front/index";
        } else {
            return "modules/wxshop/login";
        }
    }


    @RequestMapping(value = {"logout", ""})
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "modules/wxshop/login";
    }


    @RequestMapping(value = {"frontList", ""})
    public String frontList(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Goods> page = goodsService.frontFindGoods(new Page<Goods>(request, response), goods);
        List<Item> items = itemService.findList();
        model.addAttribute("items", items);
        model.addAttribute("page", page);
        return "modules/wxshop/front/frontGoodsList";
    }


    @RequestMapping(value = "frontGoodsView")
    public String frontView(Goods goods, Model model) {
        if (goods.getGid() != null) {
            goods = goodsService.showGoods(Integer.toString(goods.getGid()));
        }
        model.addAttribute("goods", goods);
        return "modules/wxshop/front/frontGoodsView";
    }

    @RequestMapping(value = "addCar")
    public String addCar(Goods goods, Member member, Model model, HttpServletRequest request, HttpServletResponse response) {
        String mid = (String) request.getSession().getAttribute("mid");
        member.setMid(mid);
        if (StringUtils.isNotBlank(member.getMid())) {
            if (goods.getGid() != null) {
                Shopcar shopcar = new Shopcar();
                shopcar.setMember(member);
                shopcar.setGoods(goods);
                if (shopcarService.findMidAndGid(shopcar)) {
                    shopcarService.updateShopcar(shopcar);
                } else {
                    shopcarService.addShopcar(shopcar);
                }
            }
        } else {
            return "modules/wxshop/login";
        }
        Page<Goods> page = goodsService.frontFindGoods(new Page<Goods>(request, response), goods);
        model.addAttribute("page", page);
        return "modules/wxshop/front/frontGoodsList";
    }

    @RequestMapping(value = {"shopcarList", ""})
    public String shopcarList(HttpServletRequest request, Model model) {
        String mid = (String) request.getSession().getAttribute("mid");
        Shopcar shopcar = new Shopcar();
        Member member = new Member();
        member.setMid(mid);
        shopcar.setMember(member);
        List<Shopcar> shopcars = shopcarService.findShopcar(shopcar);
        List<Integer> gids = shopcarService.findByMid(shopcar);
        if (!gids.isEmpty()) {
            List<Goods> allGoods = goodsService.findAllGoods(gids);
            model.addAttribute("allGoods", allGoods);
            model.addAttribute("shopcars", shopcars);
            return "modules/wxshop/front/frontShopcarList";
        } else if (request.getSession().getAttribute("mid") != null) {
            member.setMid(mid);
            member = memberService.getMember(member.getMid());
            model.addAttribute("member", member);
            return "modules/wxshop/front/index";
        } else {
            return "modules/wxshop/login";
        }
    }


    @RequestMapping(value = "deleteShopcar")
    public String deleteShopcar(Shopcar shopcar, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/wxshop/goods/list?repage";
        }
        String mid = (String) request.getSession().getAttribute("mid");
        Member member = new Member();
        member.setMid(mid);
        shopcar.setMember(member);
        shopcarService.deleteShopcar(shopcar);
        addMessage(redirectAttributes, "删除用户成功");
        return shopcarList(request, model);
    }

    @RequestMapping(value = "updateShopcar")
    public String updateShopcar(Shopcar shopcar, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        String mid = (String) request.getSession().getAttribute("mid");
        Member member = new Member();
        member.setMid(mid);
        shopcar.setMember(member);
        int i = 0;
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String temp = enu.nextElement();
            int gid = Integer.parseInt(temp);
            int count = Integer.parseInt(request.getParameter(temp));
            shopcar.setAmount(count);
            Goods goods = new Goods();
            goods.setGid(gid);
            shopcar.setGoods(goods);
            shopcarService.changeShopcar(shopcar);
            i++;
        }
        return shopcarList(request, model);
    }

    @RequestMapping(value = "showMember")
    public String showMember(Member member, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        String mid = (String) request.getSession().getAttribute("mid");
        member.setMid(mid);
        member = memberService.getMember(member.getMid());
        model.addAttribute("member", member);
        return "modules/wxshop/front/showInformation";
    }

    @RequestMapping(value = "updateMember")
    public String updateMember(Member member, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        String mid = (String) request.getSession().getAttribute("mid");
        member.setMid(mid);
        memberService.updateMember(member);
        return login(member, request, response, model);
    }

    @RequestMapping(value = "insertOrders")
    public String insertOrders(Orders orders, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        String mid = (String) request.getSession().getAttribute("mid");
        String msg = ordersService.insertOrders(mid);
        request.setAttribute("msg",msg);
        return ordersList(orders, request, response, model);
    }

    @RequestMapping(value = {"ordersList", ""})
    public String ordersList(Orders orders, HttpServletRequest request, HttpServletResponse response, Model model) {
        String mid = (String) request.getSession().getAttribute("mid");
        Member member = new Member();
        member.setMid(mid);
        orders.setMember(member);
        Page<Orders> page = ordersService.findOrdersByMid(new Page<Orders>(request, response), orders);
        model.addAttribute("page", page);
        return "modules/wxshop/front/frontOrdersList";
    }


    @RequestMapping(value = {"detailsList", ""})
    public String detailsList(HttpServletRequest request) {

        int oid = Integer.parseInt(request.getParameter("oid"));
        String mid = (String) request.getSession().getAttribute("mid");
        request.setAttribute("orders", ordersService.findByIdAndMid(mid, oid));
        return "modules/wxshop/front/frontDetailsList";
    }
}
