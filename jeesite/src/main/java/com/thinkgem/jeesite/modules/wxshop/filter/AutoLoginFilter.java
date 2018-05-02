package com.thinkgem.jeesite.modules.wxshop.filter;


import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;
import com.thinkgem.jeesite.modules.wxshop.service.MemberServiceBack;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "AutoLoginFilter", urlPatterns = {"/front/login","/front/frontList","/front/addCar","/front/frontGoodsView/*","/front/shopcarList","/front/deleteShopcar","/front/updateShopcar","/front/insertOrders","/front/ordersList","/front/detailsList"})
public class AutoLoginFilter implements Filter {

    @Autowired
    private MemberServiceBack memberService = SpringContextHolder.getBean(MemberServiceBack.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest ;
        HttpSession ses = request.getSession();
        if(ses.getAttribute("mid") != null) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            request.setAttribute("msg","您还没有登录，请先进行登录后操作！");
            request.setAttribute("url","/front/login");
            request.getRequestDispatcher("/front/login").forward(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
