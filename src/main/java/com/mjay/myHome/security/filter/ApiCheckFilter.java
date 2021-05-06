package com.mjay.myHome.security.filter;

import com.mjay.myHome.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher; //스프링에서 어느 특정한 경로와 Ant 경로 패턴이 일치하는 지를 확인할 때 사용할 수 있는 클래스

    private String pattern;

    //JWT를 생성
    private JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil){
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
        log.info("this.antPathMatcher : "+this.antPathMatcher);
        log.info("this.pattern : "+this.pattern); /// notes/**/*
    }

    public ApiCheckFilter(String pattern){
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        log.info("this.antPathMatcher : "+this.antPathMatcher);
        log.info("this.pattern : "+this.pattern); /// notes/**/*
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("REQUESTURI :: " +  request.getRequestURI());
        log.info("pathCatche :: " +  antPathMatcher.match(pattern, request.getRequestURI())); //boolean

        if(antPathMatcher.match(pattern, request.getRequestURI())){ //해당 경로에만 필터가 동작
            log.info("ApiCheckFilter................................................");
            log.info("ApiCheckFilter................................................");
            log.info("ApiCheckFilter................................................");

            boolean checkHeader = checkAuthHeader(request);

            if(checkHeader){
                filterChain.doFilter(request, response);
                return;
            }else{
//                response.setHeader("Authorization", "12345678");  //응답 헤더 주입
//                filterChain.doFilter(request, response);

                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 - 서버가 요청을 이해했지만, 실행하는 것은 거부하였다.
                // json 리턴 및 한글깨짐 수정.
                response.setContentType("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.println(json);
                return;
            }

        }
        
        filterChain.doFilter(request, response); //다음 필터의 단계로 넘어가는 역할
    }

    private boolean checkAuthHeader(HttpServletRequest request){

        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            log.info("Authorization exist : " + authHeader);

            try {
                //JWT를 이용하여 인증 값 확인
                String email = jwtUtil.validateAndExtract(authHeader.substring(7));
                log.info("validate result : " + email); // user100@zerock.org
                checkResult = email.length() > 0;
                log.info("checkResult : " + checkResult); // boolean
            }catch (Exception e) {
                e.printStackTrace();
            }
//            if(authHeader.equals("12345678")){
//                checkResult = true;
//            }

        }
        return checkResult;
    }
}


