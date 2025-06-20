package com.capstone2.capstone2.global.oauth.service;

import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import com.capstone2.capstone2.global.oauth.JwtTokenProvider;
import com.capstone2.capstone2.global.oauth.converter.AuthConverter;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.oauth.exception.AuthException;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {
    // 기존 의존성, jwtUtil 등 그대로 유지
    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /** 1) 인가 코드만 받아 회원 조회/생성 후 Member 반환 */
    @Transactional
    public Member oAuthLogin(String accessCode) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile profile = kakaoUtil.requestProfile(oAuthToken);
        return findOrCreateMember(profile);
    }

    /** 2) 기존 방식: 쿠키나 헤더 세팅용 */
    @Transactional
    public Member oAuthLogin(String accessCode, HttpServletResponse response) {
        Member member = oAuthLogin(accessCode);
        String token = jwtUtil.createAccessToken(member.getEmail());
        response.setHeader("Authorization", token);
        return member;
    }

    private Member findOrCreateMember(KakaoDTO.KakaoProfile profile) {
        String email    = profile.getKakao_account().getEmail();
        Long kakaoId    = profile.getId();
        String nickname = profile.getKakao_account().getProfile().getNickname();
        String url      = profile.getKakao_account().getProfile().getProfile_image_url();

        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(
                        AuthConverter.toMember(kakaoId, email, nickname, url)
                ));
    }

    /** JWT 생성 로직 */
    public String createToken(Member member) {
        return jwtUtil.createAccessToken(member.getEmail());
    }


    // test위해서 추가
    public MemberResponseDTO.JoinResultDTO toCurrentUserDto(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }


    public Member getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 익명 사용자라면 예외 처리
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
        }

        String email = authentication.getName();

        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}

//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final KakaoUtil kakaoUtil;
//    private final MemberRepository memberRepository;
//    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;
//
//    @Transactional
//    public Member oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
//        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
//        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
//
//        Long kakaoId = kakaoProfile.getId();
//        String email = kakaoProfile.getKakao_account().getEmail();
//        String nickname = kakaoProfile.getKakao_account().getProfile().getNickname();
//        String profileImageUrl = kakaoProfile.getKakao_account().getProfile().getProfile_image_url();
//
//
//        Member member = memberRepository.findByEmail(email)
//                .orElseGet(() -> createNewMember(kakaoId, email, nickname, profileImageUrl));
//
//        String token = jwtUtil.createAccessToken(member.getEmail());
//        httpServletResponse.setHeader("Authorization", token);
//
//        return member;
//    }
//
//    private Member createNewMember(
//            Long kakaoId,
//            String email,
//            String nickname,
//            String profileImageUrl
//    ){
//        Member newMember = AuthConverter.toMember(kakaoId, email, nickname, profileImageUrl);
//
//        return memberRepository.save(newMember);
//    }
//
//
//    @Transactional
//    public Member loginOrJoinWithKakaoData(
//            KakaoTokenResponseDTO.TokenAndProfile kakaoData,
//            HttpServletResponse response
//    ) {
//        String email = kakaoData.getEmail();
//        Long kakaoId = kakaoData.getKakaoId();
//        String nickname = kakaoData.getNickname();
//        String profileUrl = kakaoData.getProfileImageUrl();
//
//        if (email == null) {
//            throw new AuthException(ErrorStatus.AUTH_INVALID_CODE);
//        }
//
//        // 이메일 기준으로 회원 조회 → 없으면 신규 생성
//        return memberRepository.findByEmail(email)
//                .orElseGet(() -> {
//                    Member newMember = Member.builder()
//                            .kakaoId(kakaoId)
//                            .email(email)
//                            .nickname(nickname)
//                            .profile_url(profileUrl)
//                            .build();
//                    return memberRepository.save(newMember);
//                });
//    }
//
//    public Member getLoginUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 인증 정보가 없거나 익명 사용자라면 예외 처리
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
//        }
//
//        String email = authentication.getName();
//
//        return memberRepository.findByEmail(email)
//                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));
//    }
//
//    public MemberResponseDTO.JoinResultDTO toCurrentUserDto(Member member) {
//        return MemberResponseDTO.JoinResultDTO.builder()
//                .id(member.getId())
//                .email(member.getEmail())
//                .nickname(member.getNickname())
//                .build();
//    }
//    private final JwtTokenProvider jwtTokenProvider;  // 이 줄 추가
////    public String createToken(Member member) {
////        return jwtTokenProvider.generateToken(member.getId());
////    }
//    /** 인가 코드 처리 후 JWT 생성 */
//    public String createToken(Member member) {
//        // 이메일 또는 고유 식별자를 토큰 subject로 사용
//        return jwtUtil.createAccessToken(member.getEmail());
//    }
//}