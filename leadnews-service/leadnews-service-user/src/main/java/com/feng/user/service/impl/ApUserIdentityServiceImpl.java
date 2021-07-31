package com.feng.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.user.mapper.ApUserIdentityMapper;
import com.feng.user.pojo.ApUserIdentity;
import com.feng.user.service.ApUserIdentityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP身份认证信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApUserIdentityServiceImpl extends ServiceImpl<ApUserIdentityMapper, ApUserIdentity> implements ApUserIdentityService {

}
