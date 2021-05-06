package com.mjay.myHome.security.service;

import com.mjay.myHome.mjay.entity.Member;
import com.mjay.myHome.mjay.entity.MemberRole;
import com.mjay.myHome.mjay.repository.MemberRepository;
import com.mjay.myHome.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("------------------------------------------");
        log.info("userRequest : "+ userRequest); //org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@6c0af023 객체

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : "+ clientName); // clientName : Google
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=====================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info("oAuth2User.getAttributes() ::: "+k +":"+v);//sub, picture, email, email_verified 의 값이 출력
        });

        String email = null;

        if(clientName.equals("Google")){ //Google을 사용하는 경우
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL : "+email);

//        Member member = saveSocialMember(email);
//
//        return oAuth2User;

        Member member = saveSocialMember(email);

        log.info("member : "+ member.getClass().getName());
        log.info("member : "+ member);
        log.info("member.toString() : "+ member.toString());
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMember.setName(member.getName());

        log.info("authMember : "+ authMember.getClass().getName());
        log.info("authMember : "+ authMember);
        log.info("authMember.toString() : "+ authMember.toString());

        return authMember;
    }

    private Member saveSocialMember(String email) {
        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Optional<Member> result = repository.findByEmail(email, true);  //Optional 인스턴스는 모든 타입의 참조 변수를 저장

        if(result.isPresent()) { //null 체크
            log.info("result : " + result.toString());
            log.info("result.get() : " + result.get().getClass().getName());
            log.info("result.get() : " + result.get());
            return result.get();
        }

        //없다면 회원 추가 패스워드는 0000 이름은 그냥 이메일 주소로
        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("0000"))
                .fromSocial(true)
                .build();

        member.addMemberRole(MemberRole.USER);
        repository.save(member);

        return member;
    }

}
