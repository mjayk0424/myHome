package com.mjay.myHome.security.handler;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiLoginFailHandler implements AuthenticationFailureHandler {
    
    // 로그인 인증 실패 처리
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("login fail handler.......................");
        log.info(exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //	401 - 요청이 HTTP 인증을 필요로 한다.
        //json리턴
        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        String message = exception.getMessage();
        json.put("code", "401");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.println(json);
    }
}
