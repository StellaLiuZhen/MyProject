package com.thinkgem.jeesite.modules.wxshop.web;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wxshop.entity.Item;
import com.thinkgem.jeesite.modules.wxshop.service.ItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/wxshop/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @RequiresPermissions("wxshop:item:view")
    @RequestMapping(value = {"index"})
    public String index(Item item, Model model) {
        return "modules/wxshop/itemIndex";
    }


    @RequiresPermissions("wxshop:item:view")
    @RequestMapping(value = {"list", ""})
    public String list(Item item, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Item> page = itemService.findItem(new Page<Item>(request, response), item);
        model.addAttribute("page", page);
        return "modules/wxshop/itemList";
    }

    @RequiresPermissions("wxshop:item:view")
    @RequestMapping(value = "form")
    public String form(Item item, Model model) {
        if (item.getIid() != null) {
            item = itemService.getItem(Integer.toString(item.getIid()));
        }
        model.addAttribute("item", item);
        return "modules/wxshop/itemForm";
    }

    @RequiresPermissions("wxshop:item:edit")
    @RequestMapping(value = "save")
    public String save(Item item, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (!beanValidator(model, item)) {
            return form(item, model);
        }
        // 保存用户信息
        itemService.saveItem(item);
        addMessage(redirectAttributes, "保存用户'" + item.getIid() + "'成功");
        return "redirect:" + adminPath + "/wxshop/item/list?repage";
    }

    @RequiresPermissions("wxshop:item:edit")
    @RequestMapping(value = "delete")
    public String delete(Item item, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/wxshop/item/list?repage";
        }
        itemService.deleteItem(item);
        addMessage(redirectAttributes, "删除用户成功");
        return "redirect:" + adminPath + "/wxshop/item/list?repage";
    }

    /**
     * 获取机构JSON数据。
     *
     * @param extId    排除的ID
     * @param type     类型（1：公司；2：部门/小组/其它：3：用户）
     * @param grade    显示级别
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Item> list = itemService.findList();
        for (int i = 0; i < list.size(); i++) {
            Item e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getIid());
                map.put("name", e.getTitle());
                mapList.add(map);

        }
        return mapList;
    }

}
