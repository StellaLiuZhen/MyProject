package com.thinkgem.jeesite.modules.wxshop.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.wxshop.dao.MemberDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;

public class MemberUtils {

    private static MemberDao memberDao = SpringContextHolder.getBean(MemberDao.class);

    /**
     * 根据ID获取用户
     *
     * @param mid
     * @return 取不到返回null
     */
    public static Member get(String mid) {
        Member member = new Member();
        member = memberDao.get(mid);
        if (member == null) {
            return null;
        }
        return member;
    }


}
