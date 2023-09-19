package com.hoshogi.onlyonepick.domain.auth.dto.request;

import com.hoshogi.onlyonepick.domain.member.entity.Authority;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .deleted(false)
                .build();
    }
}
