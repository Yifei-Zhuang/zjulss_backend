package com.example.zjulss.utils;


import com.example.zjulss.error.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final String JWT_SECERT = "cereshuzhitingnizhenbangcereshuzhitingnizhenbangqhwhfqklwfhqlkcnlq";

    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis 有效时间
     * @return String
     */
    public static String createJWT(String id, int userId, Long ttlMillis) throws IOException {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id) // 是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setSubject("" + userId)   // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志
                .setIssuer("zjulss2")     // 颁发者是使用 HTTP 或 HTTPS 方案的 URL（区分大小写），其中包含方案、主机及（可选的）端口号和路径部分
                .setIssuedAt(now)      // jwt的签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey); // 设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return "Bearer " + builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static int validateJWT(String jwtStr) throws IOException {
        String userId = parseJWT(jwtStr).getSubject();
        return Integer.parseInt(userId);
    }

    private static SecretKey generalKey() throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encodedKey = decoder.decode(JWT_SECERT);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) throws IOException {
        SecretKey secretKey = generalKey();
        jwt = jwt.split(" ")[1].trim();
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();

            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "jwt parse fail,you're unauthorized");
        }
    }
}