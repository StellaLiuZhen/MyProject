package com.thinkgem.jeesite.modules.wxshop.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;
import com.thinkgem.jeesite.modules.wxshop.service.MemberServiceBack;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/wxshop/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberServiceBack memberServiceB;

    @RequiresPermissions("wxshop:member:view")
    @RequestMapping(value = {"index"})
    public String index(Member member, Model model) {
        return "modules/wxshop/memberIndex";
    }


    @RequiresPermissions("wxshop:member:view")
    @RequestMapping(value = {"list", ""})
    public String list(Member member, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Member> page = memberServiceB.findMember(new Page<Member>(request,response),member) ;
        model.addAttribute("page", page);
        return "modules/wxshop/memberList";
    }

    @RequiresPermissions("wxshop:member:view")
    @RequestMapping(value = "form")
    public String form(Member member, Model model) {
        if (StringUtils.isNotBlank(member.getMid())){
            member = memberServiceB.getMember(member.getMid());
        }
        model.addAttribute("member", member);
        return "modules/wxshop/memberForm";
    }

    @RequiresPermissions("wxshop:member:edit")
    @RequestMapping(value = "save")
    public String save(Member member, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(Global.isDemoMode()){
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (member.getRegdate()==null){
            member.setRegdate(new Date());
        }
        // 如果密码不为空，则更换密码
        if (StringUtils.isNotBlank(member.getPassword())) {
            member.setPassword(MemberServiceBack.entryptPassword(member.getPassword()));
        } else {
            System.out.println(member.getMid());

        }
        if (!beanValidator(model, member)){
            return form(member, model);
        }
        // 保存用户信息
        memberServiceB.saveMember(member);
        addMessage(redirectAttributes, "保存用户'" + member.getMid() + "'成功");
        return "redirect:" + adminPath + "/wxshop/member/list?repage";
    }

    @RequiresPermissions("wxshop:member:edit")
    @RequestMapping(value = "delete")
    public String delete(Member member, RedirectAttributes redirectAttributes) {
        if(Global.isDemoMode()){
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/wxshop/member/list?repage";
        }
        memberServiceB.deleteMember(member);
        addMessage(redirectAttributes, "删除用户成功");
        return "redirect:" + adminPath + "/wxshop/member/list?repage";
    }

}
