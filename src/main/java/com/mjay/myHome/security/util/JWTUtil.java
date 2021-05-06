package com.mjay.myHome.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
    //스프링 환경이 아닌 곳에서 사용할 수 있도록 간단한 유틸리티 클래스로 설계
    private String secretKey = "zerock12345678";

    //1month 만료 기간
    private long expire = 60 * 24 * 30;

    //JWT 토큰 생성
    public String generateToken(String content) throws Exception {
        log.info("-------------------generateToken--------------------------");
        log.info("expire ::: "+expire);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))  //유효기한 1초로 설정
                .claim("sub", content)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    }

    //JWT 토큰 검증 - 인코딩된 문자열에서 원하는 값을 추출
    public String validateAndExtract(String tokenStr) throws Exception{
        log.info("---------------validateAndExtract-----------------");
        String contentValue = null;

        try{
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(tokenStr);

            log.info("defaultJwt ::: "+defaultJws);
            log.info("defaultJwt.getBody().getClass() ::: "+defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("--------------------------------");

            contentValue = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}