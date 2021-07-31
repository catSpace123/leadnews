package com.feng.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.Login;
import com.feng.common.pojo.UserToken;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.common.ulits.BusinessConstants;
import com.feng.wemedia.mapper.WmUserMapper;
import com.feng.wemedia.pojo.WmUser;
import com.feng.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自媒体用户信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

        @Autowired
        private WmUserMapper wmUserMapper;
    /**
     * 用来啊添加自媒体账号
     * @param wmUser
     * @return
     */
    @Override
    public void saveWmUser(WmUser wmUser) {
        //先查询是否有自媒体账号
        QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ap_user_id",wmUser.getApUserId());
        WmUser selectOne = wmUserMapper.selectOne(queryWrapper);

        /**
         * 判断是否有账号存在   不存在就添加
         */
        if(selectOne == null){
            int insert = wmUserMapper.insert(wmUser);
        }

    }


    /**
     * 自媒体用户登录
     * @return
     */
    @Override
    public Map<String, Object> LoginMedia(Login login) {

        /**
         * 1判断用户名密码是否为空
         */
        String loginName = login.getName();
        String loginPassword = login.getPassword();
        String loginCode = login.getCode();

        if(StringUtils.isEmpty(loginName)|| StringUtils.isEmpty(loginPassword)){
            throw  new LeadnewsException("用户名或密码不能为空");
        }
        /**
         * 2验证码是否为空
         */
        if(StringUtils.isEmpty(loginCode)){
            throw  new LeadnewsException("验证码不能为空");

        }

        //3根据用户名查询user表
        QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginName);
        WmUser wmUser = wmUserMapper .selectOne(queryWrapper);

        //4判断是否查询到用户记录
        if(wmUser == null){
            throw  new LeadnewsException("用户名或密码不正确");
        }

        //5获取数据库的密码和盐
        String dbPassword = wmUser.getPassword();
        String salt = wmUser.getSalt();
        /**
         * 6将客户端的密码和盐进行加密  得到加密后的密码
         */

        String passwordForWeb = DigestUtils.md5DigestAsHex((salt + loginPassword).getBytes());
        /**
         * 7将加密后的密码和数据库密码比较
         */

        if(!passwordForWeb.equals(dbPassword)){
            throw  new LeadnewsException("用户名或密码不正确");
        }

        /**
         * 8生成token
         */
        UserToken userToken = new UserToken();
        userToken.setUserId(wmUser.getApUserId().longValue());
        userToken.setRole(BusinessConstants.TokenInfo.WM_USER_ROLE);
        userToken.setExpiration(System.currentTimeMillis()+(1000 * 60 *60));
        String token = AppJwtUtil.createToken(JSON.toJSONString(userToken));
        Map<String, Object> map = new HashMap<>();

        map.put("token",token);

        return map;
    }

    public static void main(String[] args) {
        String str = "123"+"123456";
        String s = DigestUtils.md5DigestAsHex(str.getBytes());
        System.out.println(s);
    }
}
