package com.mjay.myHome.security.service;

import com.mjay.myHome.mjay.entity.Member;
import com.mjay.myHome.mjay.repository.MemberRepository;
import com.mjay.myHome.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// 시큐리티 설정에서 loginProcessingUrl("/securityLogin");
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행됨 (규칙임)
@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository MemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername : " + username);

        Optional<Member> reault = MemberRepository.findByEmail(username, false);

        if(reault.isEmpty()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = reault.get();

        log.info("---------------------------------");
        log.info(member);

        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
                );

        authMember.setName(member.getName());
        authMember.setFromSocial(member.isFromSocial());
        log.info("---------------- authMember -----------------");
        log.info(authMember);

        return authMember;
    }


}
