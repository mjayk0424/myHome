package com.mjay.myHome.config;

import com.mjay.myHome.security.handler.LoginSuccessHandler;
import com.mjay.myHome.security.service.UserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //정적 리소스는 모두 접근 허용
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/").permitAll(); //test 시 모든 경로 허용
//                .antMatchers("/mjay/board/list").hasRole("USER");

        //인가/인증에 문제시 로그인 화면으로
        http.formLogin()
            .loginPage("/mjay/login/login")
            .loginProcessingUrl("/securityLogin")
            .defaultSuccessUrl("/")
            .successHandler(successHandler())
            .permitAll();

        //csrf 토큰을 사용할 시 post, 사용 안할 시 get 방식으로 로그아웃
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll();
        //소셜 로그인
        http.oauth2Login()
//            .loginPage("/mjay/login/login")
//            .loginProcessingUrl("/oAuthSecurityLogin")
//            .defaultSuccessUrl("/")
            .successHandler(successHandler())
            .permitAll();
//        http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService()); //7days
    }
//시큐리티 세션 내부에 Authentication 객체가 들어가고 Authentication 객체는 내부에 UserDetails를 들고 있다. 이렇게 하면 로그인이 완료된다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService);
//    }

    @Bean
    public LoginSuccessHandler successHandler(){
        return new LoginSuccessHandler(passwordEncoder());
    }


}
