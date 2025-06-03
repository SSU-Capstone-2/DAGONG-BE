package com.capstone2.capstone2.global.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

public class KakaoDTO {

    @Getter
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class KakaoProfile {
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Properties {
            private String nickname;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class KakaoAccount {
            private String email;
            private Boolean is_email_verified;
            private Boolean has_email;
            private Boolean profile_nickname_needs_agreement;
            private Boolean email_needs_agreement;
            private Boolean is_email_valid;
            private Profile profile;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @Getter
            public static class Profile {
                private String nickname;
                private Boolean is_default_nickname;
                private String profile_image_url;
            }
        }
    }

}