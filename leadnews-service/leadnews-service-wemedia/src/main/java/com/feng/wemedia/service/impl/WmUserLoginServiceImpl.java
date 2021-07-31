package com.feng.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.Login;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.wemedia.mapper.WmUserLoginMapper;
import com.feng.wemedia.mapper.WmUserMapper;
import com.feng.wemedia.pojo.WmUserLogin;
import com.feng.wemedia.service.WmUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自媒体用户登录行为信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class WmUserLoginServiceImpl extends ServiceImpl<WmUserLoginMapper, WmUserLogin> implements WmUserLoginService {



}
