package com.capstone2.capstone2.domain.member.repository;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.entity.MemberFavoriteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberFavoriteCategoryRepository extends JpaRepository<MemberFavoriteCategory, Long> {
    List<MemberFavoriteCategory> findAllByMember(Member member);
    void deleteAllByMember(Member member);
}
