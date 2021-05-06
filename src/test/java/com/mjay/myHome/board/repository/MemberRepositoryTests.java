package com.mjay.myHome.board.repository;


import com.mjay.myHome.mjay.entity.Member;
import com.mjay.myHome.mjay.entity.MemberRole;
import com.mjay.myHome.mjay.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("USER" + i)
                    .fromSocial(false)
                    .build();
            member.addMemberRole(MemberRole.USER);
            if(i > 80){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if (i > 90){
                member.addMemberRole(MemberRole.ADMIN);
            }
            memberRepository.save(member); // CrudRepository<T, ID>.save(S entity)
        });
    }
}