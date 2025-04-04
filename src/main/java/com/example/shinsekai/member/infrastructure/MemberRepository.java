package com.example.shinsekai.member.infrastructure;

import com.example.shinsekai.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberUuid(String uuid);
    Optional<Member> findByLoginId(String loginId);
}
