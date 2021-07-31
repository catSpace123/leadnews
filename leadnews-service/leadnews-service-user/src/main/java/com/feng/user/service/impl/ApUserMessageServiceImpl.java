package com.feng.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.user.mapper.ApUserMessageMapper;
import com.feng.user.pojo.ApUserMessage;
import com.feng.user.service.ApUserMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户消息通知信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApUserMessageServiceImpl extends ServiceImpl<ApUserMessageMapper, ApUserMessage> implements ApUserMessageService {

}
