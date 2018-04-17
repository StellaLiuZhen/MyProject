package com.thinkgem.jeesite.modules.wxshop.web;


import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wxshop.entity.Goods;
import com.thinkgem.jeesite.modules.wxshop.service.GoodsService;
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
@RequestMapping(value = "${adminPath}/wxshop/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @RequiresPermissions("wxshop:goods:view")
    @RequestMapping(value = {"index"})
    public String index(Goods goods, Model model) {
        return "modules/wxshop/goodsIndex";
    }


    @RequiresPermissions("wxshop:goods:view")
    @RequestMapping(value = {"list", ""})
    public String list(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Goods> page = goodsService.findGoods(new Page<Goods>(request,response),goods) ;
        model.addAttribute("page", page);
        return "modules/wxshop/goodsList";
    }

    @RequiresPermissions("wxshop:goods:view")
    @RequestMapping(value = "form")
    public String form(Goods goods, Model model) {
        if (goods.getGid()!=null){
            goods = goodsService.getGoods(Integer.toString(goods.getGid()));
        }
        model.addAttribute("goods", goods);
        return "modules/wxshop/goodsForm";
    }

    @RequiresPermissions("wxshop:goods:view")
    @RequestMapping(value = {"frontList", ""})
    public String frontList(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Goods> page = goodsService.frontFindGoods(new Page<Goods>(request,response),goods) ;
        model.addAttribute("page", page);
        return "modules/wxshop/frontGoodsList";
    }

    @RequiresPermissions("wxshop:goods:view")
    @RequestMapping(value = "frontView")
    public String frontView(Goods goods, Model model) {
        if (goods.getGid()!=null){
            goods = goodsService.getGoods(Integer.toString(goods.getGid()));
        }
        model.addAttribute("goods", goods);
        return "modules/wxshop/frontGoodsView";
    }

    @RequiresPermissions("wxshop:goods:edit")
    @RequestMapping(value = "save")
    public String save(Goods goods, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(Global.isDemoMode()){
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (goods.getPubdate()==null){
            goods.setPubdate(new Date());
        }
        if (!beanValidator(model, goods)){
            return form(goods, model);
        }
        // 保存用户信息
        goodsService.saveGoods(goods);
        addMessage(redirectAttributes, "保存用户'" + goods.getGid() + "'成功");
        return "redirect:" + adminPath + "/wxshop/goods/list?repage";
    }

    @RequiresPermissions("wxshop:goods:edit")
    @RequestMapping(value = "delete")
    public String delete(Goods goods, RedirectAttributes redirectAttributes) {
        if(Global.isDemoMode()){
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/wxshop/goods/list?repage";
        }
        goodsService.deleteGoods(goods);
        addMessage(redirectAttributes, "删除用户成功");
        return "redirect:" + adminPath + "/wxshop/goods/list?repage";
    }



}
