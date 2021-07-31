package com.feng.user.filter;

import com.alibaba.fastjson.JSON;
import com.feng.common.pojo.UserToken;
import com.feng.common.ulits.AppJwtUtil;
import com.feng.common.ulits.SystemConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自媒体全局过滤器
 */
@Component
public class MediaGlobeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        /**
         * 获取请求路径
         */
        String string = request.getURI().getPath();

        /**
         * 如果是登录，和接口文档的请求路径，直接放行
         */
        if(string.contains("/user/app/loginDto") || string.endsWith("/v2/api-docs")){
          return chain.filter(exchange);
        }

        //获取请求头中的token
        String token = request.getHeaders().getFirst("Authorization");
        if(StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示相应完成
            return response.setComplete();
        }

        /**
         * 校验token
         */

        if(AppJwtUtil.verifyToken(token) != SystemConstants.JWT_OK){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示相应完成
            return response.setComplete();
        }
        /**
         * 获取用户id传到下游微服务
         */

        String string1 = AppJwtUtil.getClaimsBody(token).get(AppJwtUtil.USER_TOKEN).toString();
        UserToken userToken = JSON.parseObject(string1, UserToken.class);
        Long userId = userToken.getUserId();

        exchange.getRequest().mutate().header("currentUserId",userId.toString());
        /**
         * 校验通过放行
         */
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
