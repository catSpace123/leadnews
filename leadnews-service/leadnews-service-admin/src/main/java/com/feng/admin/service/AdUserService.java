package com.feng.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.admin.pojo.AdUser;
import com.feng.common.pojo.Login;
import org.springframework.util.DigestUtils;

import java.util.Map;

/**
 * <p>
 * 管理员用户信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-08
 */
public interface AdUserService extends IService<AdUser> {
    /**
     * 用户登录
     * @return
     */

    Map<String, Object> login(Login login) ;
}
