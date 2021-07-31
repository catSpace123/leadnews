package com.feng.common.ulits;

import com.alibaba.fastjson.JSON;
import com.feng.common.pojo.UserToken;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AppJwtUtil {

    // TOKEN的有效期一小时（S）
    public static final int TOKEN_TIME_OUT = 3600;
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;
    /**
     * 生成token的key
     */
    public static final String USER_TOKEN = "userToken";

    // 生产ID
    public static String createToken(String userToken) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put(USER_TOKEN, userToken);

        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())   //token 的唯一标识
                .setIssuedAt(new Date(currentTime))  //签发时间
                .setSubject("system")  //说明
                .setIssuer("fengSer") //签发者信息
                .setAudience("all")  //接收用户
                .compressWith(CompressionCodecs.GZIP)  //数据压缩方式
                .signWith(SignatureAlgorithm.HS256, generalKey()) //加密方式
                //设置token过期一个小时
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .addClaims(claimMaps) //cla信息
                .compact();
    }

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()   ///解析token
                .setSigningKey(generalKey())   //先校验签名是否正确
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 校验token
     *
     * @param token
     * @return 1 有效  0 无效  2 已过期
     */
    public static Integer verifyToken(String token) {

        try {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            if (claims == null) {
                return SystemConstants.JWT_FAIL;   //表示已失效，
            }
            return SystemConstants.JWT_OK;  //有效
        } catch (ExpiredJwtException ex) {
            return SystemConstants.JWT_EXPIRE;  //已过期
        } catch (Exception e) {
            return SystemConstants.JWT_FAIL;   //无效的
        }
    }

    /**
     * 由字符串生成加密key    加密签名
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
       /* Map map = new HashMap();
        map.put("id","11");*/
        UserToken userToken = new UserToken();
        userToken.setHead("http");
        userToken.setName("zhangsan");
        userToken.setExpiration(new Date().getTime());
        userToken.setRole(BusinessConstants.TokenInfo.ADMIN_ROLE);
        userToken.setNickName("ss");
        userToken.setUserId(1007L);
        String string1 = JSON.toJSONString(userToken);
        String token = AppJwtUtil.createToken(string1);
        System.out.println(token);

        Claims claims = AppJwtUtil.getClaimsBody(token);

        String string = claims.get(USER_TOKEN).toString();

        UserToken userToken1 = JSON.parseObject(string,UserToken.class);

        System.out.println(userToken1+"====");
        /*Integer integer = AppJwtUtil.verifyToken("token");
        System.out.println(integer);
        System.out.println(claims);*/

    }
}