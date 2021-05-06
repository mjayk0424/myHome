package com.mjay.myHome.security.filter;

import com.mjay.myHome.security.dto.AuthMemberDTO;
import com.mjay.myHome.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    // 로그인 인증 관련 필터

    //JWT토큰 사용한 생성자
    //JWTUtil 생성
    private JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil){
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
        log.info("defaultFilterProcessesUrl jwtUtil :: " + defaultFilterProcessesUrl);
    }

    //기본 생성자
    public ApiLoginFilter(String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
        log.info("defaultFilterProcessesUrl :: " + defaultFilterProcessesUrl);
    }

    // 인증 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("-------------------------------ApiLoginFilter---------------------------------");
        log.info("attemptAuthentication  ");

        String email = request.getParameter("email");
        String pw = "0000";

//        if (email == null){
//            throw new BadCredentialsException("email cannot be null");
//        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw); //email, pw 파라미터로 받아서 실제 인증을 처리
        log.info("..................ApiLoginFilter...............................");
        log.info("authToken :: "+ authToken);

        return getAuthenticationManager().authenticate(authToken);
    }

    // 인증 실패 :  ApiLoginFailHandler.class 에서 처리
    // 인증 성공 처리 (별도의 클래스로 작성하여 처리가능)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("..................ApiLoginFilter...............................");
        log.info("authResult :: "+ authResult); // 성공한 사용자의 인증 정보를 가지고 있는 Authentication 객체
        log.info("authResult.getPrincipal :: "+ authResult.getPrincipal());

        //email address
        //JWT 인증 토큰 발행
        String email = ((AuthMemberDTO)authResult.getPrincipal()).getUsername();

        log.info("email : " + email);
        log.info("getAuthorities() : " + ((AuthMemberDTO)authResult.getPrincipal()).getAuthorities());
        String token = null;
        
        try{
            token = jwtUtil.generateToken(email);
            
            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info("token :: " + token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //JWTUtil을 이용해서 successfulAuthentication() 내에서 문자열을 발행 해줌
}
