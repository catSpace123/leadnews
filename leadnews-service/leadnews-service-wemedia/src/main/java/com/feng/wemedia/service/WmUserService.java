package com.feng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.pojo.Login;
import com.feng.wemedia.pojo.WmUser;

import java.util.Map;

/**
 * <p>
 * 自媒体用户信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface WmUserService extends IService<WmUser> {


    /**
     * 用来啊添加自媒体账号
     * @param wmUser
     * @return
     */
    void saveWmUser(WmUser wmUser);


    /**
     * 自媒体用户登录
     * @return
     */
    Map<String, Object> LoginMedia(Login login);
}
