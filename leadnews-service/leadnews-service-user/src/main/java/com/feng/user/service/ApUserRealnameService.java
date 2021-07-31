package com.feng.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.user.pojo.ApUserRealname;

/**
 * <p>
 * APP实名认证信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 用来审核通过实名认证
     * @param id
     * @return
     */
    void realname(int id);

    /**
     * 审核驳回
     * @param id
     * @param reason  驳回原因
     * @return
     */
    void reject(int id, String reason);
}
