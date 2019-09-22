package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.domain.UserInfo;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户表 数据层
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo, String> {

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    UserInfo findByUsername(String username);

}
