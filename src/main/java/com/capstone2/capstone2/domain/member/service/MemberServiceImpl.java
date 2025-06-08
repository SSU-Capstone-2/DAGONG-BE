package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO.InfoDTO getMemberInfo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));
        return MemberConverter.toInfoDTO(member);
    }
    @Override
    public MemberResponseDTO.InfoDTO updateNickname(Long id, String nickname) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        member.setNickname(nickname);
        Member saved = memberRepository.save(member);

        return MemberConverter.toInfoDTO(saved);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }
}
