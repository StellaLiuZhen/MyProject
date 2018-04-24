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
     * 判断给定的mid与给定的code是否相同
     * @param mid 要激活的用户id
     * @param code 设置好的激活码
     * @return 如果用户id与激活码匹配则可以返回true，否则返回false
     * @throws Exception
     * @Date: 22:03 2018/3/13
     */
    public boolean findByCode(String mid, String code) throws Exception;

    /**
     * 更新指定用户的状态操作
     * @param mid 用户id
     * @param status 用状态（0表示锁定、1表示激活、2表示等待激活）
     * @return
     * @throws Exception
     * @Date: 22:18 2018/3/13
     */
    public boolean doUpdateStatus(String mid, Integer status) throws Exception;

    /**
     * 用户的登录检查，正常登陆后可以查询出用户的照片信息，
     * 由于参数接收的是Member对象，所以可以直接将照片信息保存在Member对象中
     * @param vo 包含了mid与password的VO类对象
     * @return 登录成功返回true，否则返回false
     * @throws Exception
     * @Date: 23:13 2018/3/13
     */
    public boolean findLogin(String mid) ;

    /**
     * 根据用户的状态来进行数据的列表操作
     * @param status
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     * @Date: 18:06 2018/3/27
     */
    public List<Member> findAllByStatus(Integer status, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;

    /**
     * 根据用户的状态统计所有的数据量
     * @param status
     * @param column
     * @param keyWord     * @return
     * @throws Exception
     * @Date: 18:09 2018/3/27
     */
    public Integer getAllCountByStatus(Integer status, String column, String keyWord) throws Exception;

    /**
     * 是进行数据的批量更新,状态由外部进行
     * @param ids
     * @param status
     * @return
     * @throws Exception
     * @Date: 18:43 2018/3/27
     */
    public boolean doUpdateStatus(Set<String> ids, Integer status) throws Exception ;


}
