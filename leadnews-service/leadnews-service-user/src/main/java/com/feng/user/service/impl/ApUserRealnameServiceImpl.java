package com.feng.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.article.feign.ApAuthorFegin;
import com.feng.article.pojo.ApAuthor;
import com.feng.common.pojo.Result;
import com.feng.common.ulits.BusinessConstants;
import com.feng.user.mapper.ApUserMapper;
import com.feng.user.mapper.ApUserRealnameMapper;
import com.feng.user.pojo.ApUser;
import com.feng.user.pojo.ApUserRealname;
import com.feng.user.service.ApUserRealnameService;
import com.feng.wemedia.feign.WmUserFeign;
import com.feng.wemedia.pojo.WmUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * APP实名认证信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {

    @Autowired
    private ApUserRealnameMapper realnameMapper;

    @Autowired
    private  ApUserMapper apUserMapper;

    @Autowired
    private WmUserFeign wmUserFeign;

    @Autowired
    private ApAuthorFegin apAuthorFegin;

    /**
     * 用来审核通过实名认证
     * @param id
     * @return
     */
    @Override
    public void realname(int id) {

        /**
         * 根据id修改通过的状态
         */
        ApUserRealname entity = new ApUserRealname();
        entity.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_SUCESS);
        entity.setId(id);
        realnameMapper.updateById(entity);

        /**
         * 审核通过后调用自媒体服务创建自媒体中账号
         *
         * 现根据id查询实名表，
         */
        ApUserRealname apUserRealname = realnameMapper.selectById(id);
        /**
         * 在根据里面的用户id查询用户信息
         */
        ApUser apUser = apUserMapper.selectById(apUserRealname.getUserId());

        /**
         * 创建自媒体账户
         */
        WmUser wmUser = new WmUser();

        BeanUtils.copyProperties(apUser,wmUser);

        wmUser.setApUserId(apUser.getId());                //用户id
        wmUser.setCreatedTime(LocalDateTime.now());      //当前时间
        wmUser.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_SUCESS);                            //账号状态
        wmUser.setNickname(apUser.getName());           //昵称

        /**
         * 远程调用fegin创建自媒体账号
         */
        Result<WmUser> wmUserResult = wmUserFeign.saveWmUser(wmUser);
        /**
         * 获取自媒体账号id
         */
        Integer wmId = wmUserResult.getData().getId();

        /**
         * 创建文章作者账号
         */

        ApAuthor apAuthor = new ApAuthor();
        apAuthor.setName(apUser.getName());
        apAuthor.setType(BusinessConstants.ApAuthorConstants.A_MEDIA_USER);    //设置类型
        apAuthor.setUserId(apUser.getId());
        apAuthor.setWmUserId(wmId);
        apAuthor.setCreatedTime(LocalDateTime.now());
        /**
         * 调用文章微服务（fegin 创建作者）
         */
        apAuthorFegin.saveAuthor(apAuthor);
    }

    /**
     * 审核驳回
     * @param id
     * @param reason  驳回原因
     */
    @Override
    public void reject(int id, String reason) {

        ApUserRealname entity = new ApUserRealname();
        entity.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_FAILE);
        entity.setReason(reason);
        entity.setId(id);
        realnameMapper.updateById(entity);
    }
}
