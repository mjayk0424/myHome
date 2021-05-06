package com.mjay.myHome.mjay.repository;

import com.mjay.myHome.mjay.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> { //JpaRepository<T, ID>

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select m from Member m where m.fromSocial = :social and m.email =:email")
    Optional<Member> findByEmail(String email, boolean social);


}
