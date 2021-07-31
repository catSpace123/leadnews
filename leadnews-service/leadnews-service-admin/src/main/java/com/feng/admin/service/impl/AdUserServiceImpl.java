package com.feng.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.admin.mapper.AdUserMapper;
import com.feng.admin.pojo.AdUser;
import com.feng.admin.service.AdUserService;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.Login;
import com.feng.common.pojo.UserToken;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.common.ulits.BusinessConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 管理员用户信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-08
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {

    @Autowired
    private AdUserMapper adUserMapper;

    @Autowired
    private  RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @return
     */
    @Override
    public Map<String, Object> login(Login login) {
        /**
         * 1判断用户名密码是否为空
         */
        String loginName = login.getName();
        String loginPassword = login.getPassword();
        String loginCode = login.getCode();

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPassword)) {
            throw new LeadnewsException("用户名或密码不能为空");
        }
        /**
         * 2验证码是否为空
         */
        if (StringUtils.isEmpty(loginCode)) {
            throw new LeadnewsException("验证码不能为空");

        }

        //3根据用户名查询user表
        QueryWrapper<AdUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginName);
        AdUser adUser = adUserMapper.selectOne(queryWrapper);

        //4判断是否查询到用户记录
        if (adUser == null) {
            throw new LeadnewsException("用户名或密码不正确");
        }

        //5获取数据库的密码和盐
        String dbPassword = adUser.getPassword();
        String salt = adUser.getSalt();
        /**
         * 6将客户端的密码和盐进行加密  得到加密后的密码
         */

        String passwordForWeb = DigestUtils.md5DigestAsHex((salt + loginPassword).getBytes());
        /**
         * 7将加密后的密码和数据库密码比较
         */

        if (!passwordForWeb.equals(dbPassword)) {
            throw new LeadnewsException("用户名或密码不正确");
        }

        /**
         * 8生成token  admin  用来标记是管理员，用来区分不同自媒体能相互登录的问题
         * 过期时间一小时
         */
        long expiration = System.currentTimeMillis() + (1000 * 60 * 60);

        UserToken userToken = new UserToken(adUser.getId().longValue(),
                adUser.getImage(),
                adUser.getNickname(),
                adUser.getName(),
                BusinessConstants.TokenInfo.ADMIN_ROLE,
                expiration);

        String string = JSON.toJSONString(userToken);

        String token = AppJwtUtil.createToken(string);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);


        //把token上传到redis中
        redisTemplate.opsForValue().set(BusinessConstants.TokenInfo.ADMIN_ROLE + adUser.getId(), token, 3600 + 180 , TimeUnit.SECONDS);
        return map;
    }


}
