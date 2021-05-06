package com.mjay.myHome.security.handler;

import com.mjay.myHome.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;


@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler { //AuthenticationSuccessHandler 로그인 성공 처리 핸들러가 상속받는 인터페이스

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException , ServletException {
        log.info("-------------------------------------------------");
        log.info("authenticationSuccess authentication :  " +authentication);
        HttpSession session = request.getSession();

        // IP, 세션 ID
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        log.info("IP : " + web.getRemoteAddress());
        log.info("Session ID : " + web.getSessionId());

        // 인증 ID
        log.info("name : " + authentication.getName());

        // 권한 리스트
        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
        log.info("권한 : ");
        for(int i = 0; i< authList.size(); i++) {
            log.info("권한 : " + authList.get(i).getAuthority() + " ");
        }

        // Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String uri = "";
        // 있을 경우 URI 등 정보를 가져와서 사용
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();

            // 세션에 저장된 객체를 다 사용한 뒤에는 지워줘서 메모리 누수 방지
            requestCache.removeRequest(request, response);

            System.out.println(uri);
            // 세션 Attribute 확인
            Enumeration<String> list = request.getSession().getAttributeNames();
            while (list.hasMoreElements()) {
                System.out.println("세션 Attribute 확인 :: "+list.nextElement());
            }
        }else{
            uri = "/";
            log.info("*********************** savedRequest null ****************************");
        }


        AuthMemberDTO authMember = (AuthMemberDTO)authentication.getPrincipal();
        log.info("*********************** authMember");
        log.info("*********************** authMember : "+authMember);
        //session에 사용자 정보 담기
        session.setAttribute("authMember", authMember);

        log.info("request.getSession() : " +request.getSession().getAttribute("authMember") );

        boolean fromSocioal = passwordEncoder.matches("0000", authMember.getPassword());
        boolean passwordResult = passwordEncoder.matches("0000", authMember.getPassword());

        if(fromSocioal && passwordResult){
            log.info("redirect : /mjay/member/modify?from=social");
//            redirectStrategy.sendRedirect(request, response, "redirect : /mjay/member/modify?from=social");
            redirectStrategy.sendRedirect(request, response, uri);
            requestCache.removeRequest(request, response);
        }else{
            redirectStrategy.sendRedirect(request, response, uri);
            requestCache.removeRequest(request, response);
        }

    }
}
