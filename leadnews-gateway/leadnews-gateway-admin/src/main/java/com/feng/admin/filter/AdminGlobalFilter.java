package com.feng.admin.filter;

import com.alibaba.fastjson.JSON;
import com.feng.common.pojo.UserToken;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.common.ulits.BusinessConstants;
import com.feng.common.ulits.SystemConstants;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @description: 全局过滤器，用来拦截请求处理token
 * @author: fengSre
 * @date: 2021/7/9 16:58
 */

@Component
public class AdminGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /**
         * 获取请求和响应参数
         */
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        /**
         * 获取请求路径，经行拦截和放行处理
         */
        String path = request.getURI().getPath();

        /**
         * 判断请求路径是否包含下面的 登录路径
         */
        if (path.contains("/admin/login") || path.endsWith("/v2/api-docs")) {
            return chain.filter(exchange);  //放行
        }
        /**
         * 其他路径都拦截获取token
         */
        String token = request.getHeaders().getFirst("Authorization");

        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示完成响应
            return response.setComplete();
        }

        /**
         * 校验token
         */
        if (AppJwtUtil.verifyToken(token) != SystemConstants.JWT_OK) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示完成相应
            return response.setComplete();
        }
        /**
         *  获取token中的值
         */
        Claims claimsBody = AppJwtUtil.getClaimsBody(token);
        String string = claimsBody.get(AppJwtUtil.USER_TOKEN).toString();
        UserToken userToken = JSON.parseObject(string, UserToken.class);
        Long userId = userToken.getUserId();

        /**
         * 判断校色是否正确
         */
        if (!BusinessConstants.TokenInfo.ADMIN_ROLE.equals(userToken.getRole())) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示完成相应
            return response.setComplete();
        }


        /**
         * 到redis中获取   token
         */
        String redisToken = stringRedisTemplate.opsForValue().get(BusinessConstants.TokenInfo.ADMIN_ROLE+userId);

        //如果redis的token为空，或者，和前端传入的不一致，表示已过期没有权限，或者token是伪造的
        if (StringUtils.isEmpty(redisToken) || !token.equals(redisToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        /**
         * 获取过期时间是否小于5分钟，小于就重新生成token
         */
        //当前时间
        long currentTimeMillis = System.currentTimeMillis();
        //过期时间
        Long expiration = userToken.getExpiration();
        if ((expiration - currentTimeMillis) <= 1000 * 60 * 5) {
            String tokenNew = AppJwtUtil.createToken(string);
            //替换redis中的值
            stringRedisTemplate.opsForValue().set(BusinessConstants.TokenInfo.ADMIN_ROLE+userId,tokenNew,1, TimeUnit.HOURS);
            response.getHeaders().add("tokenNew",tokenNew);
        }


        /**
         * 如果校验通过表示可以放行
         */
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;    //用来设置启动的优先级
    }
}
