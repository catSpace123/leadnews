package com.feng.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.user.pojo.ApUser;
import com.feng.user.pojo.LoginDto;

import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApUserService extends IService<ApUser> {

    /**
     *  用户登录
     * @param loginDto
     * @return
     */
    Map<String, Object> appLogin(LoginDto loginDto);
}
