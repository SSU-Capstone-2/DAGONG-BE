package com.capstone2.capstone2.global.oauth.service;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.oauth.converter.AuthConverter;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        Long kakaoId = kakaoProfile.getId();
        String email = kakaoProfile.getKakao_account().getEmail();
        String nickname = kakaoProfile.getKakao_account().getProfile().getNickname();
        String profileImageUrl = kakaoProfile.getKakao_account().getProfile().getProfile_image_url();


        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> createNewMember(kakaoId, email, nickname, profileImageUrl));

        String token = jwtUtil.createAccessToken(member.getEmail());
        httpServletResponse.setHeader("Authorization", token);

        return member;
    }
//
//    private Member createNewMember(KakaoDTO.KakaoProfile kakaoProfile) {
//        Member newMember = AuthConverter.toMember(
//                kakaoProfile.getKakao_account().getEmail(),
//                kakaoProfile.getKakao_account().getProfile().getNickname()
//        );
//        return memberRepository.save(newMember);
//    }

    private Member createNewMember(
            Long kakaoId,
            String email,
            String nickname,
            String profileImageUrl
    ){
        Member newMember = AuthConverter.toMember(kakaoId, email, nickname, profileImageUrl);

        return memberRepository.save(newMember);
    }


}
//@Transactional
//public Member oAuthLogin(String accessCode, HttpServletResponse response) {
//    System.out.println("🔥 AuthService: requestToken 호출 전");
//    KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
//    System.out.println("🔥 AuthService: requestToken 반환됨, access_token=" + oAuthToken.getAccess_token());
//
//    // 2) AccessToken으로 프로필(사용자 정보) 요청 전에 로그
//    System.out.println("🔥 AuthService: requestProfile 호출 전");
//    KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
//    System.out.println("🔥 AuthService: requestProfile 반환됨, kakaoId=" + kakaoProfile.getId());
//
//    // 3) 프로필에서 카카오 고유 ID와 이메일 가져오기
//    Long kakaoId = kakaoProfile.getId();
//    String email = kakaoProfile.getKakao_account().getEmail();
//    String nickname = kakaoProfile.getProperties().getNickname();
//
//    // 4) DB에서 Member 조회 → 없으면 새로 생성
//    Member member = memberRepository.findByKakaoId(kakaoId)
//            .orElseGet(() -> {
//                // 이메일이 null이면 예외 처리하거나, 임시 이메일로 채울 수도 있습니다.
//                if (email == null) {
//                    throw new AuthException(ErrorStatus.AUTH_INVALID_CODE);
//                }
//                Member newMember = Member.builder()
//                        .kakaoId(kakaoId)
//                        .email(email)
//                        .nickname(nickname)
//                        .build();
//                return memberRepository.save(newMember);
//            });
//
//    // 5) JWT AccessToken 생성 후 응답 헤더에 담기 (예시: 토큰 만료 시간, 리프레시 로직 등은 생략)
//    String jwt = jwtUtil.createAccessToken(String.valueOf(member.getId()));
//    response.setHeader("Authorization", "Bearer " + jwt);
//
//    return member;
//}}