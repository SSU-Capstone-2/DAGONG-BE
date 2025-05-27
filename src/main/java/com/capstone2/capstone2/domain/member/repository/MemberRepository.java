package com.capstone2.capstone2.domain.member.repository;

import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
