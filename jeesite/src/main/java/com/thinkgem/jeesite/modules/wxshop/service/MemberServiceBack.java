package com.thinkgem.jeesite.modules.wxshop.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wxshop.dao.MemberDao;
import com.thinkgem.jeesite.modules.wxshop.entity.Member;
import com.thinkgem.jeesite.modules.wxshop.utils.MemberUtils;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberServiceBack extends BaseService implements InitializingBean {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private IdentityService identityService;


    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    /**
     * 获取用户
     * @param mid
     * @return
     */
    public Member getMember(String mid) {
        return MemberUtils.get(mid);
    }


    public String getName(String mid) {
        return MemberUtils.get(mid).getName();
    }

    public Page<Member> findMember(Page<Member> page, Member member) {
        // 设置分页参数
        member.setPage(page);
        // 执行分页查询
        page.setList(memberDao.findList(member));
        return page;
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allMembers", memberDao.findAllSplit(currentPage, lineSize, column, keyWord));
        map.put("memberCount", memberDao.getAllCount(column, keyWord));
        return map;
    }

    @Transactional(readOnly = false)
    public void deleteMember(Member member) {
        memberDao.delete(member);
        // 同步到Activiti
        deleteActivitiMember(member);
    }

    private void deleteActivitiMember(Member member) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        if(member!=null) {
            String memberMid = member.getName();//ObjectUtils.toString(user.getId());
            identityService.deleteUser(memberMid);
        }
    }

    @Transactional(readOnly = false)
    public void updateMember(Member member){
        if (StringUtils.isBlank(member.getName())){
            memberDao.doUpdateMember(member);
        }
    }




    @Transactional(readOnly = false)
    public void saveMember(Member member) {
        if (StringUtils.isBlank(member.getName())){
            //增加用户
            member.setName(IdGen.uuid());
            memberDao.insert(member);
        }else{
            // 更新用户数据
            member.preUpdate();
            memberDao.update(member);
        }
        if (StringUtils.isNotBlank(member.getMid())){
            // 将当前用户同步到Activiti
            saveActivitiMember(member);

        }
    }

    private void saveActivitiMember(Member member) {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        String memberMid = member.getName();//ObjectUtils.toString(member.getId());
        org.activiti.engine.identity.User activitiMember = identityService.createUserQuery().userId(memberMid).singleResult();
        if (activitiMember == null) {
            activitiMember = identityService.newUser(memberMid);
        }
        activitiMember.setFirstName(member.getName());
        activitiMember.setLastName(StringUtils.EMPTY);
        activitiMember.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiMember);

    }


    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        System.out.println(Encodes.encodeHex(salt)+ Encodes.encodeHex(hashPassword));
        return Encodes.encodeHex(salt)+ Encodes.encodeHex(hashPassword);
    }

    /**
     * 是需要同步Activiti数据，如果从未同步过，则同步数据。
     */
    private static boolean isSynActivitiIndetity = true;
    public void afterPropertiesSet() throws Exception {
        if (!Global.isSynActivitiIndetity()){
            return;
        }
        if (isSynActivitiIndetity){
            isSynActivitiIndetity = false;
            // 同步用户数据
            List<org.activiti.engine.identity.User> memberList = identityService.createUserQuery().list();
            if (memberList.size() == 0){
                Iterator<Member> members = memberDao.findAllList(new Member()).iterator();
                while(members.hasNext()) {
                    saveActivitiMember(members.next());
                }
            }
        }
    }

//    public Member show(Set<String> ids) throws Exception {
//        return memberDao.findById(ids);
//    }

}
