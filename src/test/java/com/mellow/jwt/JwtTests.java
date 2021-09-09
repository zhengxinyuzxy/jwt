package com.mellow.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTests {
    // 过期时间
    private static long tokenExpiration = 1000 * 60 * 60 * 24;
    private static String tokenSignKey = "mellow123456";

    /**
     * 使用JWT（JSON WEB TOKEN）生成token
     */
    @Test
    public void testCreateToken() {

        JwtBuilder jwtBuilder = Jwts.builder();

        String token = jwtBuilder
                // JWT头 HEADER
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")

                // JWT有效载荷 PAYLOAD 默认字段
                .setSubject("srb-user")
                .setIssuer("zhengxinyu")
                .setAudience("zhengixnyu")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20))
                // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())

                // JWT有效载荷自定义私有字段
                .claim("nickname", "mellow")
                .claim("avatar", "image01.jpg")

                // 签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)
                // 转换为字符串
                .compact();

        System.out.println(token);

    }

    /**
     * 解析Token
     */
    @Test
    public void testGetUserInfo() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzcmItdXNlciIsImlzcyI6InpoZW5neGlueXUiLCJhdWQiOiJ6aGVuZ2l4bnl1IiwiaWF0IjoxNjMxMTcwOTgxLCJleHAiOjE2MzEyNTczODEsIm5iZiI6MTYzMTE3MTAwMSwianRpIjoiYjA5ODhkNDItODFkNC00YWI2LTgxYzctNGE0OTBmOWNkOTI5Iiwibmlja25hbWUiOiJtZWxsb3ciLCJhdmF0YXIiOiJpbWFnZTAxLmpwZyJ9.4iFluuKAAMJrYGiMN0epJ4pKBtejxB3LoZPVD-thczE";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        // 拿到有效载荷
        Claims claims = claimsJws.getBody();

        String subject = claims.getSubject();
        String issuer = claims.getIssuer();
        String claimsAudience = claims.getAudience();
        Date issuedAt = claims.getIssuedAt();
        Date claimsExpiration = claims.getExpiration();
        Date claimsNotBefore = claims.getNotBefore();
        String claimsId = claims.getId();

        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");

        System.out.println("subject");
        System.out.println("issuer");
        System.out.println("claimsAudience");
        System.out.println("issuedAt");
        System.out.println("claimsExpiration");
        System.out.println("claimsNotBefore");
        System.out.println("claimsId");

        System.out.println(nickname);
        System.out.println(avatar);


    }
}
