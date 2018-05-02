/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wxshop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;

import java.util.List;
import java.util.Set;

/**
 * 测试DAO接口
 * @author ThinkGem
 * @version 2013-10-17
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {

    /**
     * 更新指定用户的状态操作
     * @param member
     * @return
     * @throws Exception
     * @Date: 22:18 2018/3/13
     */
    public boolean doUpdateMember(Member member);

}
