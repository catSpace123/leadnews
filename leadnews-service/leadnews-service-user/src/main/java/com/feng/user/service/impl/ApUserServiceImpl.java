package com.feng.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.UserToken;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.common.ulits.BusinessConstants;
import com.feng.user.mapper.ApUserMapper;
import com.feng.user.pojo.ApUser;
import com.feng.user.pojo.LoginDto;
import com.feng.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Autowired
    private ApUserMapper apUserMapper;


    /**
     *  用户登录
     * @param loginDto
     * @return
     */
    @Override
    public Map<String, Object> appLogin(LoginDto loginDto) {

        //根据用户电话号码获取用户信息
        String phone = loginDto.getPhone();
        //用户密码
        String passwordFromWeb = loginDto.getPassword();
        //判断是游客还是真实用户
        Integer flag = loginDto.getFlag();
        //手机识别号
        Integer equipmentId = loginDto.getEquipmentId();
        //表示游客直接生成token返回
        if (flag == 0){
            UserToken userToken = new UserToken();
            userToken.setUserId(0L);
            String token = AppJwtUtil.createToken(JSON.toJSONString(userToken));
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("token",token);
            return hashMap;
        }

        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(passwordFromWeb)){
            throw new LeadnewsException("用户名或电话号不能为空");
        }


        QueryWrapper<ApUser> querywarpper = new QueryWrapper<>();
        querywarpper.eq("phone",phone);
        ApUser apUser = apUserMapper.selectOne(querywarpper);

        if(apUser == null){
            throw new LeadnewsException("用户不存在");
        }
        //数据库密码
        String passwordFromDb = apUser.getPassword();
        //加密盐值
        String salt = apUser.getSalt();
        String NewPassword = DigestUtils.md5DigestAsHex((passwordFromWeb+salt).getBytes());
        if(!NewPassword.equals(passwordFromDb)){
            throw new LeadnewsException("用户名或密码不相等");
        }
        UserToken userToken = new UserToken();
        userToken.setUserId(apUser.getId().longValue());
        userToken.setName(apUser.getName());
        userToken.setExpiration(System.currentTimeMillis());
        userToken.setRole(BusinessConstants.TokenInfo.USER_ROLE);
        String token = AppJwtUtil.createToken(JSON.toJSONString(userToken));

        //生成token返回前端用户
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("token",token);

        return hashMap;
    }


}
