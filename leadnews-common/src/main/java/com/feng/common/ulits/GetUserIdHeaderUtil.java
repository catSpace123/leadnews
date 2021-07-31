package com.feng.common.ulits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;

/**
 * 到请求头中获取用户id
 */
public class GetUserIdHeaderUtil {

   

    public static String getUserId(){
        /**
         * 获取上游的请求头中的用户id
         */
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String currentUserId = requestAttributes.getRequest().getHeader("currentUserId");
        return  currentUserId;
    }

    /**
     * 是否是匿名用户
     * @return
     */
    public static boolean isAnonymous(){
        return "0".equals(getUserId());
    }

}
